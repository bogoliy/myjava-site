import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

import ua.com.myjava.webapp.controllers.ArticleListController;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.mysql.jdbc.AssertionFailedException;
import ua.com.myjava.model.Article;

@SpringApplicationContext( { "application-context.xml" })
public class ArticleListControllerTest extends UnitilsJUnit4 {
	@SpringApplicationContext
	private ApplicationContext applicationContext;

	@SpringBeanByName
	public ArticleListController articleListController;

	@SuppressWarnings("unchecked")
	@Test
	public void testArticlesSize() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		org.springframework.web.servlet.ModelAndView modelAndView = articleListController
				.handleRequest(request, response);

		List<Article> articles = (List<Article>) modelAndView.getModel().get(
				"articles");
		Assert.assertNotNull(articles);
		Assert.assertTrue(articles.size() > 0);

	}
}
