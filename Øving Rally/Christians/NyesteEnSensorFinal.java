import lejos.robotics.SampleProvider;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.sensor.NXTLightSensor;
import javax.swing.Timer;

public class NyesteEnSensorFinal{
	private static final double SVART_EV3 = 0.04; // Alle sampler under dette er svarte
	private static final double SVART_NXT = 0.39; // Alle sampler under dette er svarte
	private static final long tid2 = (System.currentTimeMillis()/1000) + 50;

	public static void main (String[] args)  throws Exception{

		Brick brick = BrickFinder.getDefault();
		Port s1 = brick.getPort("S1"); //nxt
		Port s4 = brick.getPort("S4"); //ev3

		NXTRegulatedMotor venstreMotor = Motor.A;
		NXTRegulatedMotor hoyreMotor = Motor.D;

		NXTLightSensor lysSensor = new NXTLightSensor(s1); // NXT LYS
		EV3ColorSensor fargeSensor = new EV3ColorSensor(s4); // EV3 LYS

		//COLOR-NXT----------------------------------------------------------------
		SampleProvider fargeSample = fargeSensor.getRedMode();
		float[] colorVerdi = new float[fargeSample.sampleSize()];

		//LYS-EV3------------------------------------------------------------------
		SampleProvider lysSample = lysSensor;
		float[] lysVerdi = new float[lysSample.sampleSize()];

		boolean harSnudd = false;

		while (true){
			long tid1 = System.currentTimeMillis()/1000;
			long tida = tid2 - tid1; // Tid igjen til vi reverserer
			System.out.println(tida);

			lysSensor.fetchSample(lysVerdi, 0);
			fargeSample.fetchSample(colorVerdi, 0);
			float ev3Verdi = colorVerdi[0];
			float nxtVerdi = lysVerdi[0];

			if (tida > 0) { // Vanlig

				if (ev3Verdi < SVART_EV3){
					hoyreMotor.setSpeed(280);
					venstreMotor.setSpeed(350);
					hoyreMotor.forward();
					venstreMotor.forward();
					System.out.println("MIIIIIIDT I!!!!");
				} else if (ev3Verdi > SVART_EV3 && nxtVerdi > SVART_NXT){ // Ingen svart (beige)
					hoyreMotor.setSpeed(400);
					venstreMotor.setSpeed(200);
					hoyreMotor.forward();
					venstreMotor.forward();
					System.out.println("HOYRE!" + ev3Verdi);
				} else if (ev3Verdi > SVART_EV3 && nxtVerdi < SVART_NXT){ // Kun NXT svart
					hoyreMotor.stop();
					venstreMotor.setSpeed(550);
					hoyreMotor.forward();
					venstreMotor.forward();
					System.out.println("HARDT VENSTRE!!!!");

				} else if (ev3Verdi < SVART_EV3){ // EV3 svart
					hoyreMotor.setSpeed(200);
					venstreMotor.setSpeed(250);
					hoyreMotor.forward();
					venstreMotor.forward();
					System.out.println("VENSTRE!" + ev3Verdi);
				} else if (ev3Verdi < SVART_EV3 && nxtVerdi < SVART_NXT){ // Begge svart
					hoyreMotor.setSpeed(600);
					venstreMotor.setSpeed(600);
					hoyreMotor.forward();
					venstreMotor.forward();
					System.out.println("BUUURN OUT!");
				}

				/*if(ev3Verdi > 0.1 && nxtVerdi > 0.4){
						fartD = 300;
						fartA = 300;
						hoyreMotor.setSpeed(fartD);
						venstreMotor.setSpeed(fartA);
						hoyreMotor.forward();
						venstreMotor.forward();
						System.out.println("FRAM!");
					}*/
			}
			else {
				if (!harSnudd){ // Utfør manøver for å komme på rett side av streken
					hoyreMotor.setSpeed(600);
					venstreMotor.setSpeed(300);
					hoyreMotor.forward();
					venstreMotor.forward();
					Thread.sleep(300);
					harSnudd = true;
					System.out.println("TIIIIIID SNUUUUUUUUUUUUUUUUU!");
				}
				else if (ev3Verdi < SVART_EV3 && nxtVerdi < SVART_NXT){ // Begge svart
					venstreMotor.stop();
					hoyreMotor.setSpeed(600);
					hoyreMotor.forward();
					venstreMotor.forward();
					Thread.sleep(300);
					System.out.println("BUUURN OUT!");

				} else if (nxtVerdi < SVART_NXT && ev3Verdi > SVART_EV3){ // NXT svart, EV3 hvit
					hoyreMotor.setSpeed(350);
					venstreMotor.setSpeed(300);
					hoyreMotor.forward();
					venstreMotor.forward();
					System.out.println("S --- MIIIIIIDT I!!!!");
				} else if (nxtVerdi > SVART_NXT && ev3Verdi > SVART_EV3){ // Ingen svart (beige)
					hoyreMotor.setSpeed(200);
					venstreMotor.setSpeed(400);
					hoyreMotor.forward();
					venstreMotor.forward();
					System.out.println("S --- HOYRE!" + ev3Verdi);
				} else if (nxtVerdi > SVART_NXT && ev3Verdi < SVART_EV3){ // Kun EV3 svart
					venstreMotor.stop();
					hoyreMotor.setSpeed(550);
					hoyreMotor.forward();
					venstreMotor.forward();
					System.out.println("S --- HARDT VENSTRE!!!!");

				}
			}
		}
	}
}