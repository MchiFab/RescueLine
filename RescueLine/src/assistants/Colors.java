package assistants;

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
	public static boolean isWhite(float[] rgbSample) {
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
	public static boolean isBlack(float[] rgbSample) {
		red = rgbSample[0] >= 0.02f && rgbSample[0] < 0.05f;
		green = rgbSample[1] >= 0.03f && rgbSample[1] < 0.06f;
		blue = rgbSample[2] >= 0.02f && rgbSample[2] <= 0.04f;

		return red && green && blue;
	}

	/**
	 * Check red green and blue values of passed color to identify it as yellow or
	 * not
	 * 
	 * @param rgbSample The color to be checked
	 * @return True if color is yellow, otherwise false
	 */
	public static boolean isYellow(float[] rgbSample) {
		red = rgbSample[0] > 0.07f && rgbSample[0] < 0.12;
		green = rgbSample[1] > 0.05 && rgbSample[1] < 0.10;
		blue = rgbSample[2] > 0.01 && rgbSample[2] < 0.07;

		return red && green && blue;
	}
}
