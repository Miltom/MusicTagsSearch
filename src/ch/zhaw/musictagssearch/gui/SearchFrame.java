package ch.zhaw.musictagssearch.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.lucene.queryparser.classic.ParseException;

import ch.zhaw.musictagssearch.example.Output;
import ch.zhaw.musictagssearch.lucene.LuceneHelper;

public class SearchFrame implements Output{

	private JFrame frame;
	private GridBagManager guiManager;
	private JLabel lbSearchDesc;
	private JTextField tfAttribut;
	private JTextField tfValue;
	private JTextArea ta;
	private JButton btSearch;
	private JLabel lbResult;
	private LuceneHelper lh;

	public SearchFrame() {
		this.frame = new JFrame();
		this.guiManager = new GridBagManager(frame);
		this.lbSearchDesc = new JLabel("Suchtext eingeben");
		this.lbResult = new JLabel("Ergebnis");
		this.tfAttribut= new JTextField("genre");
		this.tfValue= new JTextField("Metal");
		this.ta = new JTextArea();
		this.btSearch = new JButton("Suche");
	
		init();
	}
	
	public void show(){
		frame.setVisible(true);
	}
	
	private void init(){
		frame.getContentPane().setBackground(new Color(30,30,30));
		lbSearchDesc.setForeground(Color.WHITE);
		lbResult.setForeground(Color.WHITE);
		
		guiManager.setX(0).setY(0).setWeightY(0).setHeight(1).setComp(lbSearchDesc);
		guiManager.setX(0).setY(1).setWeightY(0).setWeightX(2).setHeight(1).setComp(tfAttribut);
		guiManager.setX(1).setY(1).setWeightY(0).setWeightX(2).setHeight(1).setComp(tfValue);
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
				try {
					lh.analyze(tfAttribut.getText(), tfValue.getText());
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		//frame.setUndecorated(true);
	}

	@Override
	public void outputResult(StringBuilder builder) {
		ta.setText(builder.toString());		
		System.out.println("builder.toString() "+builder.toString());
	}

	@Override
	public void outputInfo(String info) {
		ta.setText(ta.getText()+"\n"+info);
	}

	@Override
	public void clearOutput() {
		ta.setText("");		
	}

	public void setLH(LuceneHelper lh) {
		this.lh = lh;
	}
}
