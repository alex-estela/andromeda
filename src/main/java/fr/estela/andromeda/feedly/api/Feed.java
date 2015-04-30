package fr.estela.andromeda.feedly.api;

import java.util.List;

/**
 * Feedly Feed Item as of v3 API on 15/04/2015
 * @author Alexandre Estela
 */
public class Feed {

	public class Content {
		private String content;
		private String direction;
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getDirection() {
			return direction;
		}
		public void setDirection(String direction) {
			this.direction = direction;
		}
	}

	public class Alternate {
		private String href;
		private String type;
		public String getHref() {
			return href;
		}
		public void setHref(String href) {
			this.href = href;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}

	public class Canonical {
		private String href;
		private String type;
		public String getHref() {
			return href;
		}
		public void setHref(String href) {
			this.href = href;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}

	public class Origin {
		private String streamId;
		private String title;
		private String htmlUrl;
		public String getStreamId() {
			return streamId;
		}
		public void setStreamId(String streamId) {
			this.streamId = streamId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getHtmlUrl() {
			return htmlUrl;
		}
		public void setHtmlUrl(String htmlUrl) {
			this.htmlUrl = htmlUrl;
		}
	}

	public class Categorie {
		private String id;
		private String label;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
	}
	
	public class Summary {
		private String content;
		private String direction;
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getDirection() {
			return direction;
		}
		public void setDirection(String direction) {
			this.direction = direction;
		}
	}
	
	public class Tag {
		private String id;
		private String label;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
	}
	
	private String id;
	private String[] keywords;
	private String fingerprint;
	private String originId;
	private Summary summary;
	private Content content;
	private String title;
	private long updated;
	private long published;
	private long crawled;
	private String author;
	private List<Alternate> alternate;
	private List<Canonical> canonical;
	private Origin origin;
	private boolean unread;
	private List<Categorie> categories;
	private int engagement;
	private double engagementRate;
	private List<Tag> tags;

	@Override
	public String toString() {
		return author + ": " + title;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getKeywords() {
		return keywords;
	}
	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}
	public String getFingerprint() {
		return fingerprint;
	}
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}
	public String getOriginId() {
		return originId;
	}
	public void setOriginId(String originId) {
		this.originId = originId;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getUpdated() {
		return updated;
	}
	public void setUpdated(long updated) {
		this.updated = updated;
	}
	public long getPublished() {
		return published;
	}
	public void setPublished(long published) {
		this.published = published;
	}
	public long getCrawled() {
		return crawled;
	}
	public void setCrawled(long crawled) {
		this.crawled = crawled;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<Alternate> getAlternate() {
		return alternate;
	}
	public void setAlternate(List<Alternate> alternate) {
		this.alternate = alternate;
	}
	public List<Canonical> getCanonical() {
		return canonical;
	}
	public void setCanonical(List<Canonical> canonical) {
		this.canonical = canonical;
	}
	public Origin getOrigin() {
		return origin;
	}
	public void setOrigin(Origin origin) {
		this.origin = origin;
	}
	public boolean isUnread() {
		return unread;
	}
	public void setUnread(boolean unread) {
		this.unread = unread;
	}
	public List<Categorie> getCategories() {
		return categories;
	}
	public void setCategories(List<Categorie> categories) {
		this.categories = categories;
	}
	public int getEngagement() {
		return engagement;
	}
	public void setEngagement(int engagement) {
		this.engagement = engagement;
	}
	public double getEngagementRate() {
		return engagementRate;
	}
	public void setEngagementRate(double engagementRate) {
		this.engagementRate = engagementRate;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public Summary getSummary() {
		return summary;
	}
	public void setSummary(Summary summary) {
		this.summary = summary;
	}
	
}