// Icon converter for leJOS graphic menus
// Converts JPG to byte string
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.image.DataBufferByte;

public class IconConverter
{
	public static void main(String[] args) throws IOException
	{
		//String inputFile = "icon.png";
		if(args.length == 0)
		{
			System.out.println("Need input image!");
			return;
		}
		String inputFile = args[0];
		
		File file = new File(inputFile);
		BufferedImage image = ImageIO.read(file);
		
		if(image.getHeight() != 32 || image.getWidth() != 32)
		{
			System.out.println("Image needs to be 32x32px!")
			return;
		}
		
		int[] pixels = convertToArray(image);
		for(int x = 0; x < pixels.length; x += 8)
		{
			System.out.print("\\u" + String.format("%04x", convert8Bytes(pixels, x)));
		}
	}
	
	public static short convert8Bytes(int[] array, int pos)
	{
		String temp = "";
		
		for(int i = pos + 7; i >= pos; i--)
		{
			temp += array[i] == 1 ? 0 : 1; // Inverts the colors (1 = white, 0 = black)
		}
		return Short.parseShort(temp, 2);
	}
	
	/**
	* This returns a true bitmap where each element in the grid is either a 0
	* or a 1. A 1 means the pixel is white and a 0 means the pixel is black.
	* 
	* If the incoming image doesn't have any pixels in it then this method
	* returns null;
	* 
	* @param image
	* @return
	*/
	public static int[] convertToArray(BufferedImage image)
	{

		if (image == null || image.getWidth() == 0 || image.getHeight() == 0)
			return null;

		// This returns bytes of data starting from the top left of the bitmap
		// image and goes down.
		// Top to bottom. Left to right.
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

		final int width = image.getWidth();
		final int height = image.getHeight();

		int[] result = new int[height*width];

		boolean done = false;
		boolean alreadyWentToNextByte = false;
		int byteIndex = 0;
		int pos = 0;
		int numBits = 0;
		byte currentByte = pixels[byteIndex];
		while (!done)
		{
			alreadyWentToNextByte = false;

			result[pos] = (currentByte & 0x80) >> 7;
			currentByte = (byte) (((int) currentByte) << 1);
			numBits++;

			if (pos == result.length-1)
			{
				done = true;
			}
			else
			{
				pos++;

				if (numBits == 8)
				{
					currentByte = pixels[++byteIndex];
					numBits = 0;
					alreadyWentToNextByte = true;
				}
			}
		}

		return result;
	}
}