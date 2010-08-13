import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

import ua.com.myjava.model.Article;
import ua.com.myjava.search.SearchEngine;
import ua.com.myjava.webapp.controllers.ArticleListController;

@SpringApplicationContext({"myjava-servlet.xml"})
public class ArticleListControllerTest extends UnitilsJUnit4 {
    @SpringApplicationContext
    private ApplicationContext applicationContext;
    @SpringBeanByName
    public SearchEngine searchEngine;

    @SpringBeanByName
    public ArticleListController articleListController;
    ApplicationContext ctx;

    @SuppressWarnings("unchecked")
    @Test
    public void testIndexUpdate() throws Exception {
        try {
            ctx = new ClassPathXmlApplicationContext(
                    "myjava-servlet.xml");
            searchEngine = (SearchEngine) ctx.getBean("searchEngine");
            searchEngine.updateIndex();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        Assert.assertTrue(true);
    }

    @SuppressWarnings("unchecked")
    // @Test
    public void testLuceneSearch() throws Exception {
        try {
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setParameter("searchString", "articleText:измени");
            MockHttpServletResponse response = new MockHttpServletResponse();

            ModelAndView modelAndView = articleListController.handleRequest(
                    request, response);
            List<Article> articles = (List<Article>) modelAndView.getModel()
                    .get("articles");
            Assert.assertTrue(articles.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testLuceneSearchByRoot() throws Exception {
        try {
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setParameter("searchString", "articleText:Spring");
            request.setParameter("additionalResults", "true");
            MockHttpServletResponse response = new MockHttpServletResponse();
            ctx = new ClassPathXmlApplicationContext(
                    "myjava-servlet.xml");
            articleListController = (ArticleListController) ctx.getBean("articleListController");
            ModelAndView modelAndView = articleListController.handleRequest(
                    request, response);
            List<Article> articles = (List<Article>) modelAndView.getModel()
                    .get("articles");
            Assert.assertTrue(articles.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

    }
}
