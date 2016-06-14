package ch.zhaw.musictagssearch.example;

import java.util.Vector;

import org.apache.lucene.document.Document;

public interface Output {
	
	public void outputResult(Vector<Document> resultDocs);
	public void outputInfo(String info);
	public void clearOutput();

}
