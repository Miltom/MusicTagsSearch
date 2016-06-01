package ch.zhaw.musictagssearch.main;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.lucene.queryparser.classic.ParseException;

import ch.zhaw.musictagssearch.example.CommandWindow;
import ch.zhaw.musictagssearch.gui.SearchFrame;
import ch.zhaw.musictagssearch.lucene.LuceneHelper;

public class MainMusicTagsSearch {

	public static void main(String[] args) {

		
		/*
		ProcessBuilder pb = new ProcessBuilder("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe", "filepfad");
		try {
			Process start = pb.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            LuceneHelper lh;
			if (args.length==0) {
				SearchFrame sf = new SearchFrame();
				sf.show();
				lh = new LuceneHelper(sf);
				sf.setLH(lh);

			} else {

				lh = new LuceneHelper(new CommandWindow());
				lh.analyze( args[2]);
			}

		} catch (IOException | ParseException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

	}

}
