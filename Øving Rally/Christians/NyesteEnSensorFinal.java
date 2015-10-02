import lejos.robotics.SampleProvider;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.sensor.NXTLightSensor;
//import javax.swing.Timer;

public class NyesteEnSensorFinal{
	private static final float SVART_EV3 = 0.13f; // Alle sampler under dette er svarte (0.14?)
	private static final float SVART_NXT = 0.37f; // Alle sampler under dette er svarte
	private static final long tid2 = (System.currentTimeMillis()/1000) + 66;

	public static void main(String[] args) throws Exception{

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

			try
			{
				lysSensor.fetchSample(lysVerdi, 0);
				fargeSample.fetchSample(colorVerdi, 0);
			}
			catch(Exception x)
			{
				System.out.println(x.toString());
				continue;
			}
			float ev3Verdi = colorVerdi[0];
			float nxtVerdi = lysVerdi[0];

			if (tida > 0) { // Vanlig
				System.out.println(tida);

				if (erEV3Svart(ev3Verdi) && !erNXTSvart(nxtVerdi)){ // Møter stripen (kun EV3), sving til høyre
					venstreMotor.setSpeed(250);
					hoyreMotor.setSpeed(200);
					hoyreMotor.forward();
					venstreMotor.forward();
					System.out.println("Vanlig helling høyre!");
				} else if (!erEV3Svart(ev3Verdi) && !erNXTSvart(nxtVerdi)){ // Ingen svart (beige), kjør rett fram, men hell til venstre
					venstreMotor.setSpeed(200);
					hoyreMotor.setSpeed(400);
					venstreMotor.forward();
					hoyreMotor.forward();
					System.out.println("Vanlig helling venstre!");
				} else if (/*!erEV3Svart(ev3Verdi) && */erNXTSvart(nxtVerdi)){ // Kun NXT svart, nødrotasjon
					venstreMotor.setSpeed(550);
					hoyreMotor.stop();
					venstreMotor.forward();
					hoyreMotor.forward();
					System.out.println("Nødsving venstre!");
				} 
				// else if (erEV3Svart(ev3Verdi) && erNXTSvart(nxtVerdi)){ // Begge svart
				// venstreMotor.setSpeed(600);
				// hoyreMotor.setSpeed(600);
				// venstreMotor.forward();
				// hoyreMotor.forward();
				// System.out.println("Fuck alt!");
				// }
			}
			else {
				if (!harSnudd){ // Utfør manøver for å komme på rett side av streken
					venstreMotor.setSpeed(200);
					hoyreMotor.setSpeed(250);
					hoyreMotor.forward();
					venstreMotor.forward();
					Thread.sleep(750);
					harSnudd = true;
					System.out.println("Snudde til rett side!");
				}
				
				// Vanlig sensorlesing
				if (!erEV3Svart(ev3Verdi) && erNXTSvart(nxtVerdi)){ // // Møter stripen (kun NXT), sving til høyre
					venstreMotor.setSpeed(200);
					hoyreMotor.setSpeed(250);
					venstreMotor.forward();
					hoyreMotor.forward();
					System.out.println("-Vanlig venstre");
				} else if (!erEV3Svart(ev3Verdi) && !erNXTSvart(nxtVerdi)){ // Ingen svart (beige)
					venstreMotor.setSpeed(400);
					hoyreMotor.setSpeed(200);
					hoyreMotor.forward();
					venstreMotor.forward();
					System.out.println("-Vanlig hoyre" + ev3Verdi);
				} else if (erEV3Svart(ev3Verdi) /*&& !erNXTSvart(nxtVerdi)*/){ // Kun EV3 svart
					venstreMotor.stop();
					hoyreMotor.setSpeed(550);
					venstreMotor.forward();
					hoyreMotor.forward();
					System.out.println("-Nodrotasjon");
				}
				// else if (erEV3Svart(ev3Verdi) && erNXTSvart(nxtVerdi)){ // Begge svart
					// venstreMotor.stop();
					// hoyreMotor.setSpeed(600);
					// venstreMotor.forward();
					// hoyreMotor.forward();
					// Thread.sleep(300);
					// System.out.println("Fuck alt!");
				// }
			}
		}
	}
	private static boolean erEV3Svart(float sample) {
		return sample < SVART_EV3;
	}
	private static boolean erNXTSvart(float sample) {
		return sample < SVART_NXT;
	}
}