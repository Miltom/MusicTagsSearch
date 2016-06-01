package ch.zhaw.musictagssearch.lucene;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import ch.zhaw.musictagssearch.analyze.SynonymAnalyzer;
import ch.zhaw.musictagssearch.example.Output;
import ch.zhaw.musictagssearch.file.MusicTagsParser;

public class LuceneHelper {
	private static File file = new File("D:\\Studium\\4.Studienjahr\\MusicTagsSearch\\Music\\test.txt");

	private static final int HITS_PER_PAGE = 1000;

	private MusicTagsParser mtp;
	private Output output;

	public LuceneHelper(Output output) throws IOException {
		this.mtp = new MusicTagsParser();
		this.output = output;
	}

	
	public void analyze(String searchValue) throws ParseException, IOException {

		Directory index = new RAMDirectory();
		Map<String, Analyzer> analyzerPerField = new HashMap<String, Analyzer>();
		analyzerPerField.put("title", new StandardAnalyzer());
		analyzerPerField.put("artist", new StandardAnalyzer());
		analyzerPerField.put("album", new StandardAnalyzer());
		analyzerPerField.put("track", new StandardAnalyzer());
		analyzerPerField.put("year", new StandardAnalyzer());
		analyzerPerField.put("genre", new SynonymAnalyzer());
		
		PerFieldAnalyzerWrapper analyzer = new PerFieldAnalyzerWrapper(
				new StandardAnalyzer(), analyzerPerField);
		
		IndexWriterConfig config = new IndexWriterConfig( analyzer)
		.setOpenMode(OpenMode.CREATE);
		
		IndexWriter indexWriter = new IndexWriter(index, config);
		output.clearOutput();
		mtp.parse(indexWriter, file);

		StringBuilder builder = new StringBuilder();
		String querystr = !searchValue.equals("")  ? searchValue : "Metal";
			
		IndexReader reader = DirectoryReader.open(index);
		QueryParser parser= new QueryParser("genre", analyzer);

        Query query = parser.parse(querystr);
        System.out.println("query.toString(): "+query.toString());
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs docs = searcher.search(query, HITS_PER_PAGE);
		 
		Document d ;
		System.out.println("Total: "+docs.totalHits);
		
				
		for (final ScoreDoc scoreDoc : docs.scoreDocs) {
			 d =  searcher.doc(scoreDoc.doc);
			builder.append(d.get("title") + "\t" + d.get("artist") + "\t" + d.get("album") + "\t" + d.get("genre") + "\t" + d.get("track") + "\t" + d.get("year")+"\n");
		}
		System.out.println("builder.toString() "+builder.toString());
		
		output.outputResult(builder);

		reader.close();
		index.close();
	}
	
	
	
}
