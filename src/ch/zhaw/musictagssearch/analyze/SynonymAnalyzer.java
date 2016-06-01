package ch.zhaw.musictagssearch.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.ClassicTokenizer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.CharsRef;

public class SynonymAnalyzer extends Analyzer {
	private SynonymMap synonymMap;

	public SynonymAnalyzer() {
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void init() throws IOException  {
		SynonymMap.Builder sbuilder = new SynonymMap.Builder(true);
		sbuilder.add(new CharsRef("Salsa"), new CharsRef("Latin"), false);
		sbuilder.add(new CharsRef("Mitsingen"), new CharsRef("Pop"), false);
		sbuilder.add(new CharsRef("Tanzmusik"), new CharsRef("Funk"), false);
		synonymMap = sbuilder.build();
	}
	
	@Override
	protected TokenStreamComponents createComponents(String text) {
		Tokenizer source = new ClassicTokenizer();
		source.setReader(new BufferedReader(new StringReader(text)));
		

		TokenStream filter = new StandardFilter(source);
		//filter = new LowerCaseFilter(filter);
		filter = new SynonymFilter(filter, synonymMap, false);
		return new TokenStreamComponents(source, filter);
	}


}
