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
	public static void main (String[] args)  throws Exception{
		DifferentialPilot pilot = new DifferentialPilot(56f, 126f, Motor.D, Motor.A);
		pilot.setTravelSpeed(150);

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

		while (!Button.ESCAPE.isDown()){ // Kjører så lenge vi ikke trykker på exit-knappen
			lysSensor.fetchSample(lysVerdi, 0); //LYS  - NXT
			float lys = lysVerdi[0];

			fargeSample.fetchSample(colorVerdi, 0); //COLOR  - EV3
			float color = colorVerdi[0];

			/*
			lcd.clear();
			lcd.drawString("NXT " + lys, 0, 0);
			lcd.drawString("EV3 " + color, 0, 1);
			*/

			if (color < 0.1){//2){//== 7){
				pilot.rotate(10); // VENSTRE
				//lcd.drawString("ROTER HOYRE" + color, 0, 2);
			} else if (lys < 0.4){// 48){ //0.4
				pilot.rotate(-10);
				//lcd.drawString("ROTER VENSTRE" + color, 0, 3);
			} else {
				if(!pilot.isMoving()){
					pilot.forward();
					//lcd.drawString("FRAM", 0, 4);
				}
			}


		}//while
		System.out.println("AVSLUTTET");
	}//main
}//class