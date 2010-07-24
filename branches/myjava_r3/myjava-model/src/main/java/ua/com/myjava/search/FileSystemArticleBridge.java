package ua.com.myjava.search;

import java.io.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;

import net.htmlparser.jericho.CharacterReference;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.MasonTagTypes;
import net.htmlparser.jericho.MicrosoftTagTypes;
import net.htmlparser.jericho.PHPTagTypes;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

public class FileSystemArticleBridge implements FieldBridge {
    public void set(String name, Object value, Document document,
                    LuceneOptions luceneOptions) {
        Field field;
        String path = "";
        try {
            InputStream is = getClass().getResourceAsStream("/config.properties");
            Properties props = new Properties();
            props.load(is);
            path = props.getProperty("articles.path") + value;
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            field = new Field("articleText", parseHTML(path).toLowerCase(),
                    luceneOptions.getStore(), luceneOptions.getIndex(),
                    luceneOptions.getTermVector());
            field.setBoost(luceneOptions.getBoost());
            document.add(field);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String parseHTML(String sourceUrlString)
            throws MalformedURLException, IOException {
        MicrosoftTagTypes.register();
        PHPTagTypes.register();
        PHPTagTypes.PHP_SHORT.deregister(); // remove PHP short tags for this
        // example otherwise they override
        // processing instructions
        MasonTagTypes.register();
        Source source = new Source(new InputStreamReader(new FileInputStream(sourceUrlString), "Cp1251"));

        // Call fullSequentialParse manually as most of the source will be
        // parsed.
        source.fullSequentialParse();

        System.out.println("Document title:");
        String title = getTitle(source);
        System.out.println(title == null ? "(none)" : title);

        System.out.println("\nDocument description:");
        String description = getMetaValue(source, "description");
        System.out.println(description == null ? "(none)" : description);

        System.out.println("\nDocument keywords:");
        String keywords = getMetaValue(source, "keywords");
        System.out.println(keywords == null ? "(none)" : keywords);

        System.out.println("\nLinks to other documents:");
        List<Element> linkElements = source.getAllElements(HTMLElementName.A);
        for (Element linkElement : linkElements) {
            String href = linkElement.getAttributeValue("href");
            if (href == null)
                continue;
            // A element can contain other tags so need to extract the text from
            // it:
            String label = linkElement.getContent().getTextExtractor()
                    .toString();
            System.out.println(label + " <" + href + '>');
        }

        System.out
                .println("\nAll text from file (exluding content inside SCRIPT and STYLE elements):\n");
        System.out.println(source.getTextExtractor()
                .setIncludeAttributes(false).toString());
        return source.getTextExtractor().setIncludeAttributes(false).toString();
    }

    private static String getTitle(Source source) {
        Element titleElement = source.getFirstElement(HTMLElementName.TITLE);
        if (titleElement == null)
            return null;
        // TITLE element never contains other tags so just decode it collapsing
        // whitespace:
        return CharacterReference.decodeCollapseWhiteSpace(titleElement
                .getContent());
    }

    private static String getMetaValue(Source source, String key) {
        for (int pos = 0; pos < source.length();) {
            StartTag startTag = source.getNextStartTag(pos, "name", key, false);
            if (startTag == null)
                return null;
            if (startTag.getName() == HTMLElementName.META)
                return startTag.getAttributeValue("content"); // Attribute
            // values are
            // automatically
            // decoded
            pos = startTag.getEnd();
        }
        return null;
    }

}
