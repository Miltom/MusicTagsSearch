package ch.zhaw.musictagssearch.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;

public class MusicTagsParser {

	private final static int TAGS_COUNT = 6;

	public void parse(IndexWriter w, File file) {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String line = null;
			int lines = 0;

			while ((line = reader.readLine()) != null) {
				String[] splittedTAgs = line.split("-");
				System.out.println(line = reader.readLine());
				tags(w, splittedTAgs);
				lines++;
			}
			System.out.println(lines + " songs");
		    w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tags(IndexWriter w, String[] splittedTAgs) throws IOException {
		//  %title%  -  %artist%  -  %album%   -   %genre%  -  %track%  -  %year%
		if (splittedTAgs.length == TAGS_COUNT) {
			Document doc = new Document();
			doc.add(new TextField("title", splittedTAgs[0], Field.Store.YES));
			doc.add(new TextField("artist", splittedTAgs[1], Field.Store.YES));
			doc.add(new TextField("album", splittedTAgs[2], Field.Store.YES));
			doc.add(new TextField("genre", splittedTAgs[3], Field.Store.YES));
			doc.add(new TextField("track", splittedTAgs[4], Field.Store.YES));
			doc.add(new TextField("year", splittedTAgs[5], Field.Store.YES));
			w.addDocument(doc);
		}
	}

}
