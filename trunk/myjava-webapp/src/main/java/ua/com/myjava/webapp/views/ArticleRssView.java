package ua.com.myjava.webapp.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractView;

import ua.com.myjava.model.Article;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

public class ArticleRssView extends AbstractView {
	/**
     * Default constructor
     */
    public ArticleRssView() {
    	setContentType("application/xml; charset=UTF-8");
	}
    
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
    	
    	String baseUrl = "http://"+ request.getServerName() +":"+ request.getServerPort() +
    		request.getContextPath() + "/";
    	
    	List<Article> articles = (List)model.get("articles");
    	SyndFeed feed = buildFeed(articles,baseUrl);
        
    	response.setContentType(getContentType());
        SyndFeedOutput output = new SyndFeedOutput();
        output.output(feed,response.getWriter());
	}
	
	/**
	 * Creates a list with entries from the provided list of articles
	 * @param articles
	 * @param contextPath
	 * @return
	 */
	private SyndFeed buildFeed(List<Article> articles,String contextPath) { 
		SyndFeed feed = new SyndFeedImpl(); 
		List<SyndEntry> entries = new ArrayList<SyndEntry>();
		Date publishDate = new Date(System.currentTimeMillis());
		
		feed.setTitle("article.feed.title");
		feed.setDescription("article.feed.text");
		feed.setLink(contextPath);
		feed.setPublishedDate(publishDate);
		feed.setLanguage("en-us" );
		feed.setFeedType("rss_2.0"); 
					
		for (Article article : articles) {
			entries.add(buildEntry(article));
		}
		
		feed.setEntries(entries);
		return feed;
	}
	
	/**
	 * Creates an entry from the provided data
	 * @param article
	 * @param link
	 * @return the new create entry
	 */
	private SyndEntry buildEntry (Article article) {
		SyndEntry entry = new SyndEntryImpl();
		
		entry.setTitle(article.getTitle());
		entry.setPublishedDate(article.getDate());
		entry.setLink(article.getLink());
		
		SyndContent description = new SyndContentImpl();
		description.setType("text/plain");
		description.setValue(article.getText());
		entry.setDescription(description);
				
		return entry;
	}

}