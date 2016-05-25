package ch.zhaw.musictagssearch.main;

import java.io.File;

import javax.swing.UIManager;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import ch.zhaw.musictagssearch.file.MusicTagsParser;
import ch.zhaw.musictagssearch.gui.SearchFrame;

public class MainMusicTagsSearch {

	private static File file = new File("D:\\Studium\\4.Studienjahr\\MusicTagsSearch\\Music\\test.txt");

	public void read() {

	}
	
	public void analyze() {

	}
	
	public static void main(String[] args) {

		// Look and Feel vom System übernehmen
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SearchFrame sf = new SearchFrame();
			StandardAnalyzer analyzer = new StandardAnalyzer();
			Directory index = new RAMDirectory();
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			IndexWriter w = new IndexWriter(index, config);
			MusicTagsParser mtp = new MusicTagsParser();

			mtp.parse(w, file);

			String querystr = args.length > 0 ? args[0] : "Metal";

			Query q = null;

			try {
				q = new QueryParser("genre", analyzer).parse(querystr);
			} catch (org.apache.lucene.queryparser.classic.ParseException e) {
				e.printStackTrace();
			}

			int hitsPerPage = 100;
			IndexReader reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);

			TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
			searcher.search(q, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;

			System.out.println("Found " + hits.length + " hits.");
			
			for (int i = 0; i < hits.length; ++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				//System.out.println((i + 1) + ". " + "\t" + d.get("title") + "\t" + d.get("artist") + "\t" + d.get("album") + "\t" + d.get("genre") + "\t" + d.get("track") + "\t" + d.get("year"));
				System.out.println((i + 1) + ". " + "\t" + d.get("title") + "\t" +d.get("genre"));

			}
			
			

			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
