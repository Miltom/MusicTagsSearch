package ch.zhaw.musictagssearch.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import ch.zhaw.musictagssearch.main.MainMusicTagsSearch;

public class SearchFrame {

	private JFrame frame;
	private GridBagManager guiManager;
	private JLabel lbSearchDesc;
	private JTextField tfSearchField;
	private JTextArea ta;
	private JButton btSearch;
	private JLabel lbResult;
	private MainMusicTagsSearch mainMusicTagsSearch;

	public SearchFrame() {
		this.frame = new JFrame();
		this.guiManager = new GridBagManager(frame);
		this.lbSearchDesc = new JLabel("Suchtext eingeben");
		this.lbResult = new JLabel("Ergebnis");
		this.tfSearchField= new JTextField();
		this.ta = new JTextArea();
		this.btSearch = new JButton("Suche");
		this.mainMusicTagsSearch = mainMusicTagsSearch;
	
		init();
	}
	
	private void show(){
		frame.setVisible(true);
	}
	
	public void showResultOnArea(StringBuilder builder){
		ta.setText(builder.toString());
	}
	
	private void init(){
		frame.getContentPane().setBackground(new Color(30,30,30));
		lbSearchDesc.setForeground(Color.WHITE);
		lbResult.setForeground(Color.WHITE);
		
		guiManager.setX(0).setY(0).setWeightY(0).setHeight(1).setComp(lbSearchDesc);
		guiManager.setX(0).setY(1).setWeightY(0).setWeightX(4).setHeight(1).setComp(tfSearchField);
		guiManager.setX(4).setY(1).setWeightY(0).setHeight(1).setComp(btSearch);
		guiManager.setX(0).setY(2).setWeightY(2).setHeight(2).setComp(new JLabel(""));
		guiManager.setX(0).setY(4).setWidth(5).setWeightY(0).setHeight(1).setComp(lbResult);
		guiManager.setX(0).setY(5).setWidth(5).setWeightY(8).setHeight(8).setScrollPanel().setComp(ta);

		frame.setAlwaysOnTop(true);
		frame.setTitle("Music Tags Search");
		frame.setSize(700, 450);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		//frame.setUndecorated(true);
	}
}
