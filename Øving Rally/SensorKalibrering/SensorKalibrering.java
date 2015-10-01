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

public class SensorKalibrering
{
	// Standardverdier, endres i kalibrering
	private static double SVART_EV3 = 0.06;
	private static double SVART_NXT = 0.45;

	public static void main (String[] args) throws Exception
	{
		Brick brick = BrickFinder.getDefault();
		TextLCD lcd = brick.getTextLCD();
		
		Port s1 = brick.getPort("S1"); //
		Port s4 = brick.getPort("S4"); //

		NXTLightSensor lysSensor = new NXTLightSensor(s1); // NXT LYS
		EV3ColorSensor fargeSensor = new EV3ColorSensor(s4); // EV3 LYS

		//COLOR-NXT----------------------------------------------------------------
		SampleProvider fargeSample = fargeSensor.getRedMode();
		float[] colorVerdi = new float[fargeSample.sampleSize()];

		//LYS-EV3------------------------------------------------------------------
		SampleProvider lysSample = lysSensor;
		float[] lysVerdi = new float[lysSample.sampleSize()];
		
		lcd.drawString("EV3-kalibrering", 0, 0);
		
		while(!Button.ENTER.isDown())
		{
			fargeSample.fetchSample(colorVerdi, 0); //LYS
			
			lcd.clear(5);
			lcd.drawString("Verdi: " + colorVerdi[0], 0, 5);
			Thread.sleep(100);
		}
		SVART_EV3 = colorVerdi[0];
		Thread.sleep(1000);
		lcd.drawString("NXT-kalibrering", 0, 0);
		
		while(!Button.ENTER.isDown())
		{
			lysSensor.fetchSample(lysVerdi, 0); //LYS
			
			lcd.clear(5);
			lcd.drawString("Verdi: " + lysVerdi[0], 0, 5);
			Thread.sleep(100);
		}
		SVART_NXT = lysVerdi[0];
		
		// Vis frem resultat før vi begynner
		lcd.clear();
		lcd.drawString("Kalibrering fullfoert!", 0, 0);
		lcd.drawString("EV3: " + SVART_EV3, 0, 1);
		lcd.drawString("NXT: " + SVART_NXT, 0, 2);
		Thread.sleep(4000);
		lcd.clear();
	}
}