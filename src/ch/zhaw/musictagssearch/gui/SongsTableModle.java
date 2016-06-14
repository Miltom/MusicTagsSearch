package ch.zhaw.musictagssearch.gui;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.lucene.document.Document;

public class SongsTableModle extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Document> songs;
	private final String[] COLUMN_NAMES = new String[] { "Genre", "Album", "Artist", "Title", "Track", "Year" };

	public SongsTableModle() {
		this.songs = new Vector<Document>();
	}

	public void update(Vector<Document> songs) {
		this.songs = songs;
		fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(int arg0) {
		return COLUMN_NAMES[arg0];
	}

	@Override
	public int getRowCount() {
		return songs.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {

		Document song = songs.get(arg0);
		if (song == null) {
			return "";
		}

		switch (arg1) {
		case 0:
			return song.get("genre");
		case 1:
			return song.get("album");
		case 2:
			return song.get("artist");
		case 3:
			return song.get("title");
		case 4:
			return song.get("track");
		case 5:
			return song.get("year");
		}

		return song;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	public Document getSong(int row) {
		return songs.get(row);
	}

}
