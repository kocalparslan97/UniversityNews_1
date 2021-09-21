package readNews;

import java.util.Date;

public class BirgunNewsFeed {

	public String title;
	public String guId;
	public Date pubDate;
	public String link;
	public String content;

	public String getTitle() {
		return title;
	}

	public String getGuId() {
		return guId;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public String getLink() {
		return link;
	}

	public String getContent() {
		return content;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setGuId(String guId) {
		this.guId = guId;
	}

	public void setPubDate(Date date) {
		this.pubDate = date;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
