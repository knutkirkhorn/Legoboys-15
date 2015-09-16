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
		 pilot.setTravelSpeed(50);

		 Sound.setVolume(100);

		 Runnable task = new Runnable() {
			 public void run() {
				 try{
				 	File fil = new File("MorganJ_-_Madda_Fakka_Original_Mix_.wav");
				 	int wavfilelength = LagLyd(fil);
				 	} catch (Exception ex){
						System.out.println(ex);
					}
				}
				 };
		new Thread(task).start();

		 //File fil = new File("MorganJ_-_Madda_Fakka_Original_Mix_.wav");//Darude_Sandstorm.wav");
		 //int wavfilelength = LagLyd(fil);

		 float avstand = 0.2f;
		 boolean venstreSist = false;

		 while (!Button.ESCAPE.isDown()) {
			 if(ultra.getDistance() < avstand){
				 System.out.println("ROTER VENSTRE");
				 pilot.stop();
				 pilot.rotate(30);
			 } else if (ultra2.getDistance() < avstand) {
				 pilot.stop();
				 pilot.rotate(-30);
				 System.out.println("ROTER HØYRE");
			 } else {
				 System.out.println("KJØR FRAM");
				 if(!pilot.isMoving()){
					 pilot.forward();
				 }
			 }
		 }
	 }
}