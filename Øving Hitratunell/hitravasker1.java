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


public class hitravasker1{
	 public static void main (String[] args)  throws Exception{


		 Brick brick = BrickFinder.getDefault();
		 Port s3 = brick.getPort("S3"); // fargesensor
 		 Port s2 = brick.getPort("S2"); // trykk
 		 Port s4 = brick.getPort("S4"); // lyd

		 DifferentialPilot pilot = new DifferentialPilot(28f, 115f, Motor.A, Motor.B); //MOTOR
		 pilot.setTravelSpeed(60);

		 EV3ColorSensor fargesensor = new EV3ColorSensor(s3); // LYS
		 SampleProvider fargeLeser = fargesensor.getMode("RGB");

		 NXTSoundSensor lydsensor = new NXTSoundSensor(s4);
		 SampleProvider lyd = lydsensor.getDBAMode();

		 int teller = 0;

		 while (!Button.ESCAPE.isDown()){

			 float[] lydSample = new float[lyd.sampleSize()]; // LYD
			 lyd.fetchSample(lydSample, 0);
			 float lyden = lydSample[0] * 10;

			 if (lyden > 5){
				 pilot.stop();
				 System.out.println("LYD" + lyden);
				 Thread.sleep(1000);
			 }

			 float[] fargeSample = new float[fargeLeser.sampleSize()];
			 fargeLeser.fetchSample(fargeSample, 0);
			 float lyset = fargeSample[0] * 10;

			 System.out.println(lyset);
			 if (lyset > 1){
				 pilot.forward();
			 } else {
				 pilot.rotate(90.5);
			 }
			 teller++;
		 }
		 System.out.println("AVSLUTTET");
	 }
 }