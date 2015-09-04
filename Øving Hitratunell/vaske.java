import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;

public class vaske{
	 public static void main (String[] args)  throws Exception{


		 Brick brick = BrickFinder.getDefault();
		 Port s3 = brick.getPort("S3");
		 Port s2 = brick.getPort("S2");

		 EV3ColorSensor fargesensor = new EV3ColorSensor(s3); // ev3-fargesensor
		 SampleProvider fargeLeser = fargesensor.getMode("RGB");  // svart = 0.01..
		 float[] fargeSample = new float[fargeLeser.sampleSize()];  // tabell som innholder avlest verdi

		 /* Definerer en trykksensor */
		 SampleProvider trykksensor = new EV3TouchSensor(s2);
		 float[] trykkSample = new float[trykksensor.sampleSize()]; // tabell som inneholder avlest verdi

		 // Beregn verdi for svart
		 	int farge = 0;
		 	for (int i = 0; i<10; i++){
		 	fargeLeser.fetchSample(fargeSample, 0);
		 	farge += fargeSample[0]* 100;
		 	farge = farge / 100 + 5;
		 	System.out.println(farge);

				int svart = 0;
				for (int k = 0; k<10; k++){
					fargeLeser.fetchSample(fargeSample, 0);
					svart += fargeSample[0]* 100;
				}
				svart = svart / 100 + 5;
	System.out.println("Svart: " + svart);
		 	}
		 	if (farge > 7){
				System.out.println("HVIT");
			} else {
				System.out.println("BLACK");

		}
	 }
 }
