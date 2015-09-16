import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.BrickFinder;
import lejos.hardware.Brick;
import lejos.hardware.port.Port;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;
import lejos.hardware.motor.*;
import lejos.robotics.navigation.*;
import java.io.File;
import lejos.hardware.Sound;

class RettFram
{
	 public static void main (String[] args) throws Exception
	 {
		 DifferentialPilot pilot = new DifferentialPilot(56f, 126f, Motor.A, Motor.B);
		 pilot.setTravelSpeed(600);
		 pilot.forward();
		 Thread.sleep(10000);
	 }
}