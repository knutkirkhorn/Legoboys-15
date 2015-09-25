import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.NXTSoundSensor;
import java.io.File;

import java.lang.Object;
import lejos.hardware.Device;
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.AnalogSensor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.navigation.*;
import lejos.hardware.Button;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.hardware.lcd.TextLCD;

public class IIERally{
	public static void main (String[] args)  throws Exception{



		DifferentialPilot pilot = new DifferentialPilot(56f, 126f, Motor.D, Motor.A);
		pilot.setTravelSpeed(150);


		Brick brick = BrickFinder.getDefault();
		Port s1 = brick.getPort("S1"); //
		Port s4 = brick.getPort("S4"); //

		TextLCD lcd = brick.getTextLCD();

		NXTLightSensor lysSensor = new NXTLightSensor(s1); // NXT LYS
		EV3ColorSensor fargeSensor = new EV3ColorSensor(s4); // EV3 LYS

		//COLOR-----------------------------------------------------------------
		SampleProvider fargeSample = fargeSensor.getMode("RGB");
		float[] colorVerdi = new float[fargeSample.sampleSize()];


		//----------------------------------------------------------------------

		//LYS-------------------------------------------------------------------
		SampleProvider lysSample = lysSensor;
		float[] lysVerdi = new float[lysSample.sampleSize()];


		//----------------------------------------------------------------------

		Boolean move = true; // Sett til false hvis roboten ikke skal kj�re videre
		//System.out.println("start lokke");

		while (!Button.ESCAPE.isDown()){ // Kj�rer s� lenge vi ikke trykker p� exit-knappen
			// Kj�r framover hvis vi ikke allerede gj�r det
			if(!pilot.isMoving() && move) {
				pilot.forward();
			}

			lysSensor.fetchSample(lysVerdi, 0); //LYS
			float lys = lysVerdi[0];

			fargeSensor.fetchSample(colorVerdi, 0); //COLOR
			float color = colorVerdi[0];

			lcd.clear();
			lcd.drawString("NXT " + lys, 0, 0);
			lcd.drawString("EV3 " + color, 0, 1);

			if (color < 10){

				pilot.rotate(-10); // VENSTRE
			} else if (lys < 48){

				//pilot.arcForward(-150);
				pilot.rotate(10);
			}
			// Elsen under trengs ikke, da forward kj�res p� starten uansett
			// else {
				// pilot.forward();
			// }

		}//while
		System.out.println("AVSLUTTET");
	}//main
}//class