package ch.zhaw.musictagssearch.example;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
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

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import java.io.IOException;

public class HelloLucene {
  public static void main(String[] args) throws IOException, ParseException {
    // 0. Specify the analyzer for tokenizing text.
    //    The same analyzer should be used for indexing and searching
    StandardAnalyzer analyzer = new StandardAnalyzer();

    // 1. create the index
    Directory index = new RAMDirectory();

    IndexWriterConfig config = new IndexWriterConfig( analyzer);

    IndexWriter w = new IndexWriter(index, config);
    addDoc(w, "Musictags", "About Music");
    addDoc(w, "Musictags 22", "About Tags");
    addDoc(w, "Musictags","About Semianararbeit");
    addDoc(w, "a","About Tags");
    addDoc(w, "b","About Tags");
    w.close();

    // 2. query
    String querystr = args.length > 0 ? args[0] : "Musictags";

    // the "title" arg specifies the default field to use
    // when no field is explicitly specified in the query.
    Query q = null;
    
	try {
		q = new QueryParser("title", analyzer).parse(querystr);
	} catch (org.apache.lucene.queryparser.classic.ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    // 3. search
    int hitsPerPage = 10;
    IndexReader reader = DirectoryReader.open(index);
    IndexSearcher searcher = new IndexSearcher(reader);
    
    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
    searcher.search(q, collector);
    ScoreDoc[] hits = collector.topDocs().scoreDocs;
    
    // 4. display results
    System.out.println("Found " + hits.length + " hits.");
    for(int i=0;i<hits.length;++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      System.out.println((i + 1) + ". " + "\t" + d.get("title")+ "\t" + d.get("a"));
    }

    // reader can only be closed when there
    // is no need to access the documents any more.
    reader.close();
  }

  private static void addDoc(IndexWriter w, String title, String a) throws IOException {
    Document doc = new Document();
    doc.add(new TextField("title", title, Field.Store.YES));
    doc.add(new TextField("a", a, Field.Store.YES));
    w.addDocument(doc);
  }
}
