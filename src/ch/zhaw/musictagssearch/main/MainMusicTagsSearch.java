package ch.zhaw.musictagssearch.main;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ch.zhaw.musictagssearch.gui.SearchFrame;
import ch.zhaw.musictagssearch.lucene.LuceneHelper;

public class MainMusicTagsSearch {

	public static void main(String[] args) {

		try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            LuceneHelper lh;
				SearchFrame sf = new SearchFrame();
				sf.show();
				lh = new LuceneHelper(sf);
				sf.setLH(lh);

		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

	}

}
