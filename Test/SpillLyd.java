import java.util.*;

import lejos.hardware.Sound;
import lejos.hardware.motor.*;
import lejos.hardware.Sound.*;
import lejos.hardware.lcd.*;

import java.io.File;
class SpillLyd{
	public static void main(String[] args){
		File file = new File("untitled.wav");
		int wavfilelength = sing(file);
	}

	public static int sing(File file){
			return Sound.playSample(file, 50);
		}

	    //Method created to speak in R2D2 Language
	    public static void speak() throws Exception{
	        Random rnd = new Random();

	        for(int i=0; i<=20; i++){
	            int frequency = rnd.nextInt(1000);
	            int duration = rnd.nextInt(1000);
	            Sound.playTone(i*frequency, duration);
	            Thread.sleep(100);
	        }
    }
}