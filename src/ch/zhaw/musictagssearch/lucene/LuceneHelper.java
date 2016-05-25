package ch.zhaw.musictagssearch.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import ch.zhaw.musictagssearch.example.Output;
import ch.zhaw.musictagssearch.file.MusicTagsParser;

public class LuceneHelper {
	private static File file = new File("D:\\Studium\\4.Studienjahr\\MusicTagsSearch\\Music\\test.txt");

	private static final int HITS_PER_PAGE = 1000;

	private StandardAnalyzer analyzer;
	private MusicTagsParser mtp;
	private Output output;

	public LuceneHelper(Output output) throws IOException {
		this.analyzer = new StandardAnalyzer();
		this.mtp = new MusicTagsParser();
		this.output = output;
	}

	public void analyze(String searchAttribut, String searchValue) throws ParseException, IOException {
		Directory index = new RAMDirectory();
		IndexWriterConfig config= new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = new IndexWriter(index, config);
		output.clearOutput();
		mtp.parse(indexWriter, file);

		StringBuilder builder = new StringBuilder();
		String querystr = searchValue != null ? searchValue : "Metal";
		Query q = new QueryParser(searchAttribut, analyzer).parse(querystr);
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(HITS_PER_PAGE);
		
		searcher.search(q, collector);

		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		output.outputInfo("Found " + hits.length + " hits.");

		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			builder.append((i + 1) + ". " + "\t" + d.get("title") + "\t" + d.get("artist") + "\t" + d.get("album") + "\t" + d.get("genre") + "\t" + d.get("track") + "\t" + d.get("year")+"\n");
		}
		System.out.println("builder.toString() "+builder.toString());
		
		output.outputResult(builder);

		reader.close();
	}

}
