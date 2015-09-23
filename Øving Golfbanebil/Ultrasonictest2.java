import lejos.hardware.BrickFinder;
import lejos.hardware.Brick;
import lejos.hardware.port.Port;
import lejos.hardware.Button;
import lejos.hardware.motor.*;
import lejos.robotics.navigation.*;

class Ultrasonictest2
{
	public static void main (String[] args) throws Exception
	{
		Brick brick = BrickFinder.getDefault();
		Port sPortEV3 = brick.getPort("S4"); //EV3.  -- ultrasonic sensor på port 1
		// Port sPortNxt = brick.getPort("S1");//NXT

		RaveUltrasonicSensor ultra = new RaveUltrasonicSensor(sPortEV3);
		//RaveUltrasonicSensorNXT ultra2 = new RaveUltrasonicSensorNXT(sPortNxt);

		DifferentialPilot pilot = new DifferentialPilot(56f, 126f, Motor.A, Motor.B);
		pilot.setTravelSpeed(100);
		pilot.setRotateSpeed(150);

		//Prøv om dette virker ;)
		//Egen klasse for å spille av lyd
		SpillAvLyd sLyd = new SpillAvLyd();
		//sLyd.startLyd();

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
					// float ultra2Avstand = ultra2.getDistance();
					// if(ultraAvstand < avstand && ultraAvstand < ultra2Avstand)
					// {
					// System.out.println("ROTER VENSTRE");
					pilot.stop();
					// pilot.rotate(45);
					// teller++;
					// }
					// else if (ultra2Avstand < avstand && ultra2Avstand < ultraAvstand)
					// {
					// System.out.println("ROTER HØYRE");
					pilot.stop();
					// pilot.rotate(-30);
					// teller++;
					// }
					
					if(ultraAvstand < avstand)
					{
						
						//pilot.stop();
						if(teller++ > 2)
						{
							System.out.println("ROTER VENSTRE");
							pilot.rotateLeft();
						}
						else
						{
							System.out.println("ROTER HØYRE");
							pilot.rotateRight();
							teller = 0;
						}
						Thread.sleep(500);
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