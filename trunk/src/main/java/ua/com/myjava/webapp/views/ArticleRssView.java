package ua.com.myjava.webapp.views;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedOutput;
import org.springframework.web.servlet.view.AbstractView;
import ua.com.myjava.model.Article;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ArticleRssView extends AbstractView {
	/**
     * Default constructor
     */
	
	private static final String DEFAULT_FEED_TYPE = "atom_0.3";
	private static final String FEED_TYPE = "type";
	private static final String ARTICLE_COLLECTION = "articles";
	
    public ArticleRssView() {
    	setContentType("application/xml; charset=UTF-8");
	}
    
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
    	
    	String baseUrl = "http://"+ request.getServerName() +":"+ request.getServerPort() +
    		request.getContextPath() + "/";
    	
    	List<Article> articles = (List)model.get(ARTICLE_COLLECTION);
    	SyndFeed feed = buildFeed(articles,baseUrl);
    	String feedType = request.getParameter(FEED_TYPE);
        feedType = (feedType!=null) ? feedType : DEFAULT_FEED_TYPE;
        feed.setFeedType(feedType);
		
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
		
		feed.setTitle("LAST WEEK ARTICLES");
		feed.setDescription("Here present articles dedicated to the Java EE " +
				"site development process");
		feed.setLanguage("en-us" );
		feed.setLink(contextPath);
		feed.setPublishedDate(publishDate);
					
		for (Article article : articles) {
			entries.add(buildEntry(article,contextPath));
		}
		
		feed.setEntries(entries);
		return feed;
	}
	
	/**
	 * Creates an entry from the provided data
	 * @param article
	 * @return the new create entry
	 */
	private SyndEntry buildEntry (Article article, String contextPath) {
		SyndEntry entry = new SyndEntryImpl();
		
		entry.setTitle(article.getTitle());
		entry.setPublishedDate(article.getDate());
		
		String entryLink = contextPath + "articles.htm?id=" + article.getId();
		entry.setLink(entryLink);
		
		SyndContent description = new SyndContentImpl();
		description.setType("text/plain");
		description.setValue(article.getText());
		entry.setDescription(description);
				
		return entry;
	}

}