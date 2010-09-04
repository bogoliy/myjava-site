import junit.framework.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;
import ua.com.myjava.model.Article;
import ua.com.myjava.search.SearchEngine;
import ua.com.myjava.webapp.controllers.ArticleListController;

import java.util.List;

@SpringApplicationContext( { "application-context.xml" })
public class ArticleListControllerTest extends UnitilsJUnit4 {
	@SpringApplicationContext
	private ApplicationContext applicationContext;
	@SpringBeanByName
	public SearchEngine searchEngine;

	@SpringBeanByName
	public ArticleListController articleListController;

	@SuppressWarnings("unchecked")
    @Test
	public void testIndexUpdate() throws Exception {
		try {
			searchEngine.updateIndex();
		} catch (Exception e) {
			e.printStackTrace();
			junit.framework.Assert.fail();
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
			request.setParameter("searchString", "articleText:измени OR помощи");
			request.setParameter("additionalResults", "true");
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
}
