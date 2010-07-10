package ua.com.myjava.search;

import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ua.com.myjava.model.Article;

public class SearchEngine extends HibernateDaoSupport {

	private static final int BATCH_SIZE = 50;

	public void updateIndex() {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		FullTextSession fts = org.hibernate.search.Search
				.getFullTextSession(session);
		// Delete all Article indexes
		fts.beginTransaction();
		fts.purgeAll(Article.class);
		fts.getTransaction().commit();

		fts.setFlushMode(FlushMode.MANUAL);
		fts.setCacheMode(CacheMode.IGNORE);

		fts.beginTransaction();

		ScrollableResults results = session.createCriteria(Article.class)
				.scroll(ScrollMode.FORWARD_ONLY);
		int index = 0;
		while (results.next()) {
			index++;
			fts.index(results.get(0));
			if ((index % BATCH_SIZE) == 0) {
				fts.flushToIndexes();
				fts.clear();
			}
		} 
		
		fts.getTransaction().commit();
	}
}
