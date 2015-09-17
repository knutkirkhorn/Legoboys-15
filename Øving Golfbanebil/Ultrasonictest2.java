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
		 pilot.setTravelSpeed(100);
		 pilot.setRotateSpeed(150);

		 Sound.setVolume(100);

		 Runnable task = new Runnable() {
			 public void run() {
				 try{
				 	File fil = new File("musikk.wav");
				 	int wavfilelength = LagLyd(fil);
				 	} catch (Exception ex){
						System.out.println(ex);
					}
				}
				 };
		new Thread(task).start();

		 //File fil = new File("MorganJ_-_Madda_Fakka_Original_Mix_.wav");//Darude_Sandstorm.wav");
		 //int wavfilelength = LagLyd(fil);

		 float avstand = 0.13f;
		 int teller = 0;

		 while (!Button.ESCAPE.isDown())
		 {
			 try
			 {
				 if(teller >= 5){
					 pilot.backward();
					 Thread.sleep(100);
					 teller = 0;
				 } else {

			float ultraAvstand = ultra.getDistance();
			float ultra2Avstand = ultra2.getDistance();

			if(ultraAvstand < avstand && ultraAvstand < ultra2Avstand)
			{
				 System.out.println("ROTER VENSTRE");
				 //pilot.stop();
				 pilot.rotate(45);
				 teller++;
			 }
			 else if (ultra2Avstand < avstand && ultra2Avstand < ultraAvstand)
			 {
				 //pilot.stop();
				 pilot.rotate(-30);
				 System.out.println("ROTER HØYRE");
				 teller++;
			 }
			 else
			 {
				 System.out.println("KJØR FRAM");
				 if(!pilot.isMoving())
				 {
					 pilot.forward();
					 teller = 0;
				 }
			 }
		 }
		 } catch (Exception ex) {
			 System.out.println(ex);
	}
}
	 }
}