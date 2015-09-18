import lejos.hardware.Sound;
import java.io.File;

class SpillAvLyd {

	public static int LagLyd(File fil) throws Exception{
			return Sound.playSample(fil, 50);
	}

	public void startLyd(){
		Runnable task = new Runnable() {//en tråd som spiller av sangen/musikken. vi kan legge til spillelister senere..
			public void run() {
				try{
					File fil = new File("musikk.wav");
					int wavfilelength = LagLyd(fil);
					} catch (Exception ex){
						System.out.println(ex);
						}
					}
					};
			new Thread(task).start();
	}

	public SpillAvLyd(){
		Sound.setVolume(100);
	}
}