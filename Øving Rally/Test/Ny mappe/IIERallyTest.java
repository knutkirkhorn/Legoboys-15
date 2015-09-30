import lejos.robotics.SampleProvider;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.navigation.*;
import lejos.hardware.Button;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.hardware.lcd.TextLCD;
//import lejos.robotics.Color;

public class IIERallyTest{
	private static final int SNU_AKS = 25;

	public static void main (String[] args)  throws Exception{

		Brick brick = BrickFinder.getDefault();
		Port s1 = brick.getPort("S1"); //
		Port s4 = brick.getPort("S4"); //

		TextLCD lcd = brick.getTextLCD();

		NXTLightSensor lysSensor = new NXTLightSensor(s1); // NXT LYS
		EV3ColorSensor fargeSensor = new EV3ColorSensor(s4); // EV3 LYS

		//COLOR-NXT----------------------------------------------------------------
		SampleProvider fargeSample = fargeSensor.getRedMode();//getMode("RGB");//getColorIDMode();
		float[] colorVerdi = new float[fargeSample.sampleSize()];

		//LYS-EV3------------------------------------------------------------------
		SampleProvider lysSample = lysSensor;
		float[] lysVerdi = new float[lysSample.sampleSize()];

		//Boolean move = true; // Sett til false hvis roboten ikke skal kjøre videre
		//System.out.println("start lokke");

		int fartD = 300;
		int fartA = 300;

		while (true){
			lysSensor.fetchSample(lysVerdi, 0);
			fargeSample.fetchSample(colorVerdi, 0);
			float ev3Verdi = colorVerdi[0];
			float nxtVerdi = lysVerdi[0];

			if (ev3Verdi < 0.1){ //svart på ev3-sensor
				fartD += SNU_AKS;
				fartA -= SNU_AKS;
				Motor.D.setSpeed(fartD);
				Motor.A.setSpeed(fartA);
				Motor.D.forward();
				Motor.A.forward();
				System.out.println("HOYRE!");
			}
			if (nxtVerdi < 0.4){//svart på nxt-sensor
				fartD -= SNU_AKS;
				fartA += SNU_AKS;
				Motor.D.setSpeed(fartD);
				Motor.A.setSpeed(fartA);
				Motor.D.forward();
				Motor.A.forward();
				System.out.println("VENSTRE!");
			}
			if(ev3Verdi > 0.1 && nxtVerdi > 0.4){
				fartD = 300;
				fartA = 300;
				Motor.D.setSpeed(fartD);
				Motor.A.setSpeed(fartA);
				Motor.D.forward();
				Motor.A.forward();
				System.out.println("FRAM!");
				}
			}
		}

	}
