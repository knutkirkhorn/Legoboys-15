import lejos.hardware.Sound;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class MusicPlayer {

	private String fileName;
	private boolean playList = false;

	//Constructor which takes filename and soundvolume as argument
	public MusicPlayer(String fileName, int soundVolume) {
		this.fileName = fileName;
		Sound.setVolume(soundVolume);
	}

	public MusicPlayer(int soundVolume, boolean playList) {
		Sound.setVolume(soundVolume);
		this.playList = playList;
	}

	public void playMusic() {
		//Check if you want to play list or not
		if (playList) {
			//Find all songs
			loopSongs();
			//Play all songs
			for (String song: songList) {
				playSongInThread(song);
			}
		} else {
			//Thread to multitask sound so the music play while the program goes.
			playSongInThread(fileName);
		}
	}

	private void playSongInThread(String songFileName){
		final String sFileName = songFileName;
		Runnable task = new Runnable() {
			public void run() {
				try {
					File fileToPlay = new File(sFileName);
					int wavFileLength = makeNoise(fileToPlay);
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		};
		new Thread(task).start();
	}

	private static int makeNoise(File file) throws Exception {
		return Sound.playSample(file);
	}

	private List<String> songList = new ArrayList <String>();
	//Loop through songs (.wav) in lejos ev3 robot and put them in a playlist (array)
	private void loopSongs() {
		//List all files in the lejosFolder in an array
		File[] files = new File(".").listFiles();

		//Loop through files in ev3 robot
		for (File file: files) {
			if (!file.isDirectory() && file.getAbsolutePath().contains(".wav")) {
				//Put them in the list
				songList.add(file.getAbsolutePath());
			}
		}
	}
}
