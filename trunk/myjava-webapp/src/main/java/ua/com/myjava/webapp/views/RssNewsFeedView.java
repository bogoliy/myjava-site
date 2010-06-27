package ua.com.myjava.webapp.views;

import java.util.*;   
import javax.servlet.http.*;   
import org.springframework.beans.factory.annotation.Required;   
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;   
import com.sun.syndication.feed.rss.Channel;   
import com.sun.syndication.feed.rss.Description;   
import com.sun.syndication.feed.rss.Item;   
import ua.com.myjava.model.Article;   
  
public final class RssNewsFeedView extends AbstractRssFeedView {           // 1   
    private String feedTitle;                                              // 2   
    private String feedDesc;   
    private String feedLink;   
       
    @Required   
    public void setFeedTitle(String feedTitle) {   
        this.feedTitle = feedTitle;   
    }   
       
    @Required   
    public void setFeedDescription(String feedDesc) {   
        this.feedDesc = feedDesc;   
    }   
       
    @Required   
    public void setFeedLink(String feedLink) {   
        this.feedLink = feedLink;   
    }   
  
    @Override   
    protected void buildFeedMetadata(   
            Map model, Channel feed, HttpServletRequest request) {         // 3   
           
        feed.setTitle(feedTitle);   
        feed.setDescription(feedDesc);   
        feed.setLink(feedLink);   
    }   
       
    @Override   
    protected List<Item> buildFeedItems(   
            Map model,   
            HttpServletRequest request,   
            HttpServletResponse response)   
        throws Exception {                                                 // 4   
           
        @SuppressWarnings("unchecked")   
        List<Article> newsItems =   
            (List<Article>) model.get("newsItemList");                    // 5   
           
        List<Item> feedItems = new ArrayList<Item>();   
        for (Article newsItem : newsItems) {                              // 6   
            Item feedItem = new Item();   
            feedItem.setTitle(newsItem.getTitle());   
            feedItem.setAuthor(newsItem.getUser().getName());   
            feedItem.setPubDate(newsItem.getDate());   
               
            Description desc = new Description();   
            desc.setType("text/html");   
            desc.setValue(newsItem.getText());   
            feedItem.setDescription(desc);   
               
            feedItem.setLink("http:\\"+newsItem.getId());   
            feedItems.add(feedItem);   
        }   
           
        return feedItems;   
    }   
}   