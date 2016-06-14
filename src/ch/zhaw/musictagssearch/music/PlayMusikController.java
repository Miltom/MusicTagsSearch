package ch.zhaw.musictagssearch.analyze;

import java.io.IOException;

public class PlayMusikController {
	
	public static void playMusic(String path){
	
		ProcessBuilder pb = new ProcessBuilder("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe", path);
		try {
			Process start = pb.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	

}
