package ua.com.myjava.article;

import ua.com.myjava.model.Article;

import java.io.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleHelper {
    Logger log = Logger.getLogger(ArticleHelper.class.toString());
    private String articlesPath;

    public String getArticle(String filename) {

        StringBuilder contents = new StringBuilder();
        File aFile;
        try {
            aFile = new File(articlesPath + filename);

            // use buffering, reading one line at a time
            // FileReader always assumes default encoding is OK!
            BufferedReader input = new BufferedReader(new FileReader(aFile));
            try {
                String line = null; // not declared within while loop

                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return contents.toString();

    }

    public void saveInFileSystem(Article article) {
        FileWriter writer = null;
        try {
            File file = new File(articlesPath + article.getFilename());
            file.createNewFile();
            writer = new FileWriter(file);

            writer.write(article.getText() == null ? "" : article.getText());
            writer.flush();
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
        } finally {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e) {
                    log.log(Level.SEVERE, e.getMessage());
                }
        }

    }


    public void setArticlesPath(String articlesPath) {
        this.articlesPath = articlesPath;
    }
}

