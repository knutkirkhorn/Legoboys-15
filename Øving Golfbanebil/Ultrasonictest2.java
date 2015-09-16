import lejos.hardware.BrickFinder;
import lejos.hardware.Brick;
import lejos.hardware.port.Port;
import lejos.hardware.Button;
import lejos.hardware.motor.*;
import lejos.robotics.navigation.*;
import java.io.File;
import lejos.hardware.Sound;

class Ultrasonictest2
{

	public static int LagLyd(File fil) throws Exception{
		return Sound.playSample(fil, 50);
	}

	 public static void main (String[] args) throws Exception
	 {
		 Brick brick = BrickFinder.getDefault();
		 Port sPort = brick.getPort("S4"); // ultrasonic sensor på port 1
		 Port sPortNxt = brick.getPort("S1");

		 RaveUltrasonicSensor ultra = new RaveUltrasonicSensor(sPort);
		 RaveUltrasonicSensorNXT ultra2 = new RaveUltrasonicSensorNXT(sPortNxt);

		 DifferentialPilot pilot = new DifferentialPilot(56f, 126f, Motor.A, Motor.B);
		 pilot.setTravelSpeed(200);

		 Sound.setVolume(100);
		 File fil = new File("MorganJ_-_Madda_Fakka_Original_Mix_.wav");//Darude_Sandstorm.wav");
		 //int wavfilelength = LagLyd(fil);

		 float avstand = 0.2f;
		 boolean venstreSist = false;

		 while (!Button.ESCAPE.isDown()) {
			 System.out.println(ultra2.getDistance());

			 if(ultra.getDistance() < avstand){
				 System.out.println("ROTER");
				 if(venstreSist){
					 	pilot.stop();
					 	pilot.rotate(30);
					 } else {
						 pilot.stop();
						 pilot.rotate(-60);
			 	}
			 } else {
				 System.out.println("KJØR FRAM");
				 if(!pilot.isMoving()){
					 pilot.forward();
				 }
			 }
		 }
	 }
}