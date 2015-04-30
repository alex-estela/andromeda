package fr.estela.andromeda.feedly.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.estela.andromeda.feedly.api.Feed;

public class FeedlyConsumer extends ScheduledPollConsumer {
	
	private FeedlyEndpoint endpoint;
	
	private static Logger logger = LoggerFactory.getLogger(FeedlyConsumer.class);
			
	private long nextStartingTimestamp = 0;

	public FeedlyConsumer(FeedlyEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
        this.setDelay(endpoint.getConfiguration().getDelay());
	}

	@Override
	protected int poll() throws Exception {
		
		String operationPath = endpoint.getOperationPath();

		if (operationPath.equals("search/feeds")) return processSearchFeeds();
		
		if (operationPath.equals("streams/contents")) return processStreamContents();
		
		throw new IllegalArgumentException("Incorrect operation: " + operationPath);
	}
	
	private JsonObject performGetRequest(String uri, String authorizationKey) throws ClientProtocolException, IOException {
		
		HttpClient client = HttpClientBuilder.create().build();	
		
		HttpGet request = new HttpGet("http://cloud.feedly.com/v3/" + uri);
		if (authorizationKey != null) request.setHeader(HttpHeaders.AUTHORIZATION, authorizationKey);		
		
		HttpResponse response = client.execute(request);

		if (response.getStatusLine().getStatusCode() != 200)
			throw new RuntimeException("Feedly API error " + response.getStatusLine().getStatusCode() + ": " + response.getStatusLine().getReasonPhrase());
				
		JsonParser parser = new JsonParser();
		InputStreamReader sr = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
		BufferedReader br = new BufferedReader(sr);
		JsonObject json = (JsonObject) parser.parse(br);
		br.close();
		sr.close();
		
		return json;
	}

	private int extractAndProcessFeeds(JsonArray feeds) throws Exception {
		
		List<Feed> feedList = new ArrayList<Feed>();
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		for (JsonElement f : feeds) {
			//logger.debug(gson.toJson(i));
			Feed feed = gson.fromJson(f, Feed.class);
			feedList.add(feed);		
		}		
		
        Exchange exchange = getEndpoint().createExchange();
        exchange.getIn().setBody(feedList, ArrayList.class);
        getProcessor().process(exchange);
        
        return feedList.size();
	}
	
	private int processSearchFeeds() throws Exception {
		
		String query = endpoint.getConfiguration().getQuery();	
		
		String uri = String.format("search/feeds?query=%s", query);
		JsonObject json = performGetRequest(uri, null);
		
		JsonArray feeds = (JsonArray) json.get("results");		
		extractAndProcessFeeds(feeds);    
		
        return 1;
	}
	
	private int processStreamContents() throws Exception {
		
		String authorizationKey = endpoint.getConfiguration().getAuthorizationKey();
		String streamId = endpoint.getConfiguration().getStreamId();
		
		String uri = String.format("streams/contents?streamId=%s&newerThan=%s", streamId, nextStartingTimestamp);
		JsonObject json = performGetRequest(uri, authorizationKey);
		
		long updated = json.get("updated").getAsLong();
		
		JsonArray feeds = (JsonArray) json.get("items");		
		int count = extractAndProcessFeeds(feeds);

		logger.debug("-- most recent entry: " + updated);
		if (count > 0) nextStartingTimestamp = updated + 1;
		
        return 1;
	}
}