package ua.com.myjava.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.thoughtworks.xstream.XStream;

import ua.com.myjava.model.Article;
import ua.com.myjava.persist.ArticleDAO;

@Path("/articles")
public class RestArticleService {
    @Autowired
    private ArticleDAO articleDAO;

    @GET
    @Path("{id}")
    @Produces("application/xml")
    public StreamingOutput getArticle(@PathParam("id") int id) {
        

        final Article article = articleDAO.load(id);
        if (article == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new StreamingOutput() {

            public void write(OutputStream out) throws IOException,
                    WebApplicationException {
                XStream stream = new XStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                stream.processAnnotations(Article.class);
                String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
                xmlData += stream.toXML(article);
                out.write(xmlData.getBytes("UTF-8"));
            }

        };
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @GET
    @Produces("application/xml")
    public StreamingOutput getArticles(@QueryParam("start") int start, @QueryParam("end") int end) {
        if (!(start > 0 && end > 0 && end > start)) {
            start = 0;
            end = 0;
        }
        final List<Article> articles = articleDAO.getArticles(start, end);


        if (articles == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new StreamingOutput() {

            public void write(OutputStream out) throws IOException,
                    WebApplicationException {
                XStream stream = new XStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                stream.processAnnotations(Article.class);
                String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
                xmlData += "<articles>";
                for (Article article : articles) {
                    xmlData += stream.toXML(article);
                }
                xmlData += "</articles>";
                out.write(xmlData.getBytes("UTF-8"));
            }

        };
    }


}
