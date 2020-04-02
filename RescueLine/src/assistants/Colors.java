package assistants;

import lejos.robotics.Color;

/**
 * Class used to handle RGB-values returned from Color-Sensors
 * 
 * @author michi
 *
 */
public class Colors {
	private static boolean red;
	private static boolean green;
	private static boolean blue;

	/**
	 * Check red green and blue values of passed color to identify it as white or
	 * not
	 * 
	 * @param rgbSample The color to be checked
	 * @return True if color is white, otherwise false
	 */
	public static boolean IsWhite(float[] rgbSample) {
		red = rgbSample[0] >= 0.06f && rgbSample[0] <= 0.11f;
		green = rgbSample[1] >= 0.07f && rgbSample[1] <= 0.13f;
		blue = rgbSample[2] > 0.06f && rgbSample[2] <= 0.17f;

		return red && green && blue;
	}

	/**
	 * Check red green and blue values of passed color to identify it as black or
	 * not
	 * 
	 * @param rgbSample The color to be checked
	 * @return True if color is black, otherwise false
	 */
	public static boolean IsBlack(float[] rgbSample) {
		red = rgbSample[0] >= 0.00f && rgbSample[0] < 0.06f;
		green = rgbSample[1] >= 0.01f && rgbSample[1] < 0.07f;
		blue = rgbSample[2] >= 0.02f && rgbSample[2] <= 0.06f;

		return red && green && blue;
	}
}
