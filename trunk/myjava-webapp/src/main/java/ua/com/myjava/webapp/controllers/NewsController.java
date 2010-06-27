package ua.com.myjava.webapp.controllers;


import javax.servlet.http.HttpServletResponse;   
import org.springframework.beans.factory.annotation.Required;   
import org.springframework.stereotype.Controller;   
import org.springframework.ui.Model;   
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.myjava.model.Article;  
import ua.com.myjava.model.User;  
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import ua.com.myjava.persist.ArticleDAO;
   
  
@Controller   
public class NewsController {   

	@Autowired
	private ArticleDAO articleDAO;
	   
    @RequestMapping("/news.rss")
    public String rss(HttpServletResponse res, Model model) {   
        List<Article> newsItems = articleDAO.getArticles();
        model.addAttribute("newsItemList",newsItems);                  
        return "rssNewsFeedView";                                        
    }  
}