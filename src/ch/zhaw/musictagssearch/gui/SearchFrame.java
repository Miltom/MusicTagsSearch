package ch.zhaw.musictagssearch.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

import ch.zhaw.musictagssearch.analyze.PlayMusikController;
import ch.zhaw.musictagssearch.example.Output;
import ch.zhaw.musictagssearch.lucene.LuceneHelper;

public class SearchFrame implements Output {

	private JFrame frame;
	private GridBagManager guiManager;
	private JLabel lbSearchDesc;
	private JTextField tfValue;
	private JTable tableSongs;
	private JButton btSearch;
	private JLabel lbResult;
	private LuceneHelper lh;
	private SongsTableModle songsTableModle = new SongsTableModle();

	public SearchFrame() {
		this.frame = new JFrame();
		this.songsTableModle = new SongsTableModle();
		this.guiManager = new GridBagManager(frame);
		this.lbSearchDesc = new JLabel("Suchtext eingeben");
		this.lbResult = new JLabel("Ergebnis");
		this.tfValue = new JTextField("Metal");
		this.tableSongs = new JTable(songsTableModle);
		this.btSearch = new JButton("Suche");

		init();
	}

	public void show() {
		frame.setVisible(true);
	}

	private void init() {

		frame.getContentPane().setBackground(new Color(30, 30, 30));

		lbSearchDesc.setForeground(Color.WHITE);
		lbResult.setForeground(Color.WHITE);

		tableSongs.setShowGrid(false);
		
		tableSongs.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				
				if(arg0.getClickCount()==2){
					PlayMusikController.playMusic(songsTableModle.getSong(tableSongs.getSelectedRow()).get("musicpath"));
					System.out.println("-"+songsTableModle.getSong(tableSongs.getSelectedRow()).get("musicpath")+"-");
				}
				
			};
		});

		guiManager.setX(0).setY(0).setWeightY(0).setHeight(1).setComp(lbSearchDesc);

		guiManager.setX(0).setY(1).setWeightY(0).setWeightX(3).setHeight(1).setComp(tfValue);
		guiManager.setX(4).setY(1).setWeightY(0).setHeight(1).setComp(btSearch);

		guiManager.setX(0).setY(2).setWeightY(2).setHeight(2).setComp(new JLabel(""));
		guiManager.setX(0).setY(4).setWidth(5).setWeightY(0).setHeight(1).setComp(lbResult);
		guiManager.setX(0).setY(5).setWidth(5).setWeightY(8).setHeight(8).setScrollPanel().setComp(tableSongs);

		frame.setAlwaysOnTop(true);
		frame.setTitle("Music Tags Search");
		frame.setSize(700, 450);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tfValue.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER)
					try {
						lh.analyze(tfValue.getText());
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		});

		btSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					lh.analyze(tfValue.getText());
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	public void outputResult(Vector<Document> resultDocs) {
		songsTableModle.update(resultDocs);
	}

	@Override
	public void outputInfo(String info) {
		// ta.setText(ta.getText()+"\n"+info);
	}

	@Override
	public void clearOutput() {
		songsTableModle.update(new Vector<Document>());
	}

	public void setLH(LuceneHelper lh) {
		this.lh = lh;
	}

}
