package ua.com.myjava.model;

import java.util.Collection;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.solr.analysis.NGramFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.apache.solr.analysis.StopFilterFactory;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import ua.com.myjava.search.FileSystemArticleBridge;

@javax.persistence.TableGenerator(name = "ART_GEN", table = "GENERATOR_TABLE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VALUE", pkColumnValue = "ARTICLE")
@Entity 
@AnalyzerDefs( {
		@AnalyzerDef(name = "phonetic", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
				@TokenFilterDef(factory = StandardFilterFactory.class),
				@TokenFilterDef(factory = StopFilterFactory.class,
						params = {@Parameter(name="words", value="stopwords.txt"),
					@Parameter(name="ignoreCase", value="true")}),
				@TokenFilterDef(factory = NGramFilterFactory.class, params = {
						@Parameter(name = "maxGramSize", value = "3"),
						@Parameter(name = "maxGramSize", value = "3") }) }),
		@AnalyzerDef(name = "rassianSnowball", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
				@TokenFilterDef(factory = StandardFilterFactory.class),
				@TokenFilterDef(factory = StopFilterFactory.class, params = {@Parameter(name="words", value="stopwords.txt"),
					@Parameter(name="ignoreCase", value="true")}),
				@TokenFilterDef(factory = StopFilterFactory.class),
				@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = @Parameter(name = "language", value = "Russian"))

		}) 
 
})
@Analyzer(definition = "phonetic")
@Indexed
@BatchSize(size=100)
@Table(name="article", schema="myjava")
public class Article {

	private int id;

	private String title;

	private String filename;

	private Date date;

	private String text;

	@Column(name = "ar_text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private User user;

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
	@Fields( {
			@Field(name = "title", store = Store.YES, index = Index.TOKENIZED),
			@Field(name = "title_stemmer", analyzer = @Analyzer(definition = "rassianSnowball")) })
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

	@Column(name = "ar_date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@ManyToOne
	@JoinColumn(name = "us_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private Collection<Opinion> opinions;

	@CollectionOfElements
	@IndexedEmbedded
	@OneToMany(mappedBy = "article")
	public Collection<Opinion> getOpinions() {
		return opinions;
	}

	public void setOpinions(Collection<Opinion> opinions) {
		this.opinions = opinions;
	}

}
