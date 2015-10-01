import lejos.robotics.SampleProvider;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.navigation.*;
//import lejos.hardware.Button;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.hardware.lcd.TextLCD;
//import lejos.robotics.Color;

public class IIERallyTest{
	private static final int SNU_AKS = 10;
	private static final int STANDARD_FART = 300;

	public static void main (String[] args)  throws Exception{

		Brick brick = BrickFinder.getDefault();
		Port s1 = brick.getPort("S1"); //
		Port s4 = brick.getPort("S4"); //

		TextLCD lcd = brick.getTextLCD();

		NXTLightSensor lysSensor = new NXTLightSensor(s1); // NXT LYS(lyssensor)
		EV3ColorSensor fargeSensor = new EV3ColorSensor(s4); // EV3 LYS(fargesensor)

		//COLOR-NXT----------------------------------------------------------------
		SampleProvider fargeSample = fargeSensor.getRedMode();
		float[] colorVerdi = new float[fargeSample.sampleSize()];

		//LYS-EV3------------------------------------------------------------------
		SampleProvider lysSample = lysSensor;
		float[] lysVerdi = new float[lysSample.sampleSize()];

		//Boolean move = true; // Sett til false hvis roboten ikke skal kjøre videre
		//System.out.println("start lokke");

		int fartD = STANDARD_FART;
		int fartA = STANDARD_FART;
		float ev3Verdi = 0;
		float nxtVerdi = 0;

		while (true){
			lysSensor.fetchSample(lysVerdi, 0);
			fargeSample.fetchSample(colorVerdi, 0);
			ev3Verdi = colorVerdi[0];
			nxtVerdi = lysVerdi[0];

			if (ev3Verdi < 0.1){ //svart på ev3-sensor
				fartD += SNU_AKS;
				if(fartD > 400)
					fartD = STANDARD_FART;
				fartA -= SNU_AKS;
				Motor.D.setSpeed(fartD);
				Motor.A.setSpeed(fartA);
				Motor.D.forward();
				Motor.A.forward();
				//System.out.println("HOYRE!");
			}
			if (nxtVerdi < 0.4){//svart på nxt-sensor
				fartD -= SNU_AKS;
				fartA += SNU_AKS;
				if(fartA > 400)
					fartA = STANDARD_FART;
				Motor.D.setSpeed(fartD);
				Motor.A.setSpeed(fartA);
				Motor.D.forward();
				Motor.A.forward();
				//System.out.println("VENSTRE!");
			}
			if(ev3Verdi > 0.1 && nxtVerdi > 0.4){
				fartD = STANDARD_FART;
				fartA = STANDARD_FART;
				Motor.D.setSpeed(fartD);
				Motor.A.setSpeed(fartA);
				Motor.D.forward();
				Motor.A.forward();
				//System.out.println("FRAM!");
				}
			}
		}

	}
