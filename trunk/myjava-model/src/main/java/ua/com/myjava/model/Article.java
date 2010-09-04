package ua.com.myjava.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.apache.solr.analysis.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.search.annotations.*;
import ua.com.myjava.search.FileSystemArticleBridge;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

@javax.persistence.TableGenerator(name = "ART_GEN", table = "GENERATOR_TABLE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VALUE", pkColumnValue = "ARTICLE")
@Entity
@AnalyzerDefs({
        @AnalyzerDef(name = "phonetic", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
                @TokenFilterDef(factory = StandardFilterFactory.class),
                @TokenFilterDef(factory = StopFilterFactory.class,
                        params = {@Parameter(name = "words", value = "stopwords.txt"),
                                @Parameter(name = "ignoreCase", value = "true")}),
                @TokenFilterDef(factory = NGramFilterFactory.class, params = {
                        @Parameter(name = "maxGramSize", value = "3"),
                        @Parameter(name = "maxGramSize", value = "3")})}),
        @AnalyzerDef(name = "rassianSnowball", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
                @TokenFilterDef(factory = StandardFilterFactory.class),
                @TokenFilterDef(factory = StopFilterFactory.class, params = {@Parameter(name = "words", value = "stopwords.txt"),
                        @Parameter(name = "ignoreCase", value = "true")}),
                @TokenFilterDef(factory = StopFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = @Parameter(name = "language", value = "Russian"))

        })

})
@XStreamAlias("article")
@Analyzer(definition = "phonetic")

@Indexed
@BatchSize(size = 100)
@Table(name = "article", schema = "myjava")
public class Article {

    private int id;

    private String title;

    private String text;

    @XStreamOmitField
    private String filename;

    @XStreamAlias("date")
    @XStreamConverter(ua.com.myjava.xstream.converter.MyJavaDateConverter.class)
    private java.util.Date date;

    @Column(name = "ar_text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /*@XStreamOmitField
    private User user;*/

    @Id
    @Column(name = "ar_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ART_GEN")
    @DocumentId
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "ar_title")
    @Fields({
            @Field(name = "title", store = Store.YES, index = Index.TOKENIZED),
            @Field(name = "title_stemmer", analyzer = @Analyzer(definition = "rassianSnowball"))})
    @Boost(2.0f)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "ar_filename")
    @Field(store = Store.YES, index = Index.TOKENIZED)
    @FieldBridge(impl = FileSystemArticleBridge.class)
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @XmlElement
    @Column(name = "ar_date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /*  @ManyToOne
    @JoinColumn(name = "us_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/

    /*@XStreamOmitField
        private Collection<Comment> comments;

        @CollectionOfElements
        @IndexedEmbedded
        @OneToMany(mappedBy = "article")
        public Collection<Comment> getOpinions() {
            return comments;
        }

        public void setOpinions(Collection<Comment> comments) {
            this.comments = comments;
        }
    */


    private static class Adapter extends XmlAdapter<String, String> {

        @Override
        public String marshal(String v) throws Exception {
            return "<![CDATA[" + v + "]]>";
        }

        @Override
        public String unmarshal(String v) throws Exception {
            return v;
        }

    }
}
