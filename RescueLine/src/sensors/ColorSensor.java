package sensors;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;

/**
 * Class used to handle Color-Sensors
 * 
 * @author michi
 *
 */
public class ColorSensor {
	private static EV3ColorSensor colorL;
	private static EV3ColorSensor colorR;
	private static EV3ColorSensor colorM;

	/**
	 * Get color of left Color-Sensor
	 * 
	 * @return Float array containing three values (red, green, blue)
	 */
	public float[] getColorLeft() {
		float[] sample = new float[3];

		if (colorL != null) {
			colorL.getRGBMode().fetchSample(sample, 0);
			return sample;
		}
		return null;
	}

	/**
	 * Get color of right Color-Sensor
	 * 
	 * @return Float array containing three values (red, green, blue)
	 */
	public float[] getColorRight() {
		float[] sample = new float[3];

		if (colorR != null) {
			colorR.getRGBMode().fetchSample(sample, 0);
			return sample;
		}
		return null;
	}

	/**
	 * Get color of middle Color-Sensor
	 * 
	 * @return Float array containing three values (red, green, blue)
	 */
	public float[] getColorMiddle() {
		float[] sample = new float[3];

		if (colorM != null) {
			colorM.getRGBMode().fetchSample(sample, 0);
			return sample;
		}
		return null;
	}

	/**
	 * Initialize Color-Sensors to RGB-mode
	 * 
	 * @param right  Port of right Color-Sensor
	 * @param middle Port of middle Color-Sensor
	 * @param left   Port of left Color-Sensor
	 */
	public void initialize(Port right, Port middle, Port left) {
		colorR = new EV3ColorSensor(right);
		colorM = new EV3ColorSensor(middle);
		colorL = new EV3ColorSensor(left);
		colorL.getRGBMode();
		colorM.getRGBMode();
		colorR.getRGBMode();
	}

	/**
	 * Closes ports to Color-Sensors
	 */
	public void dispose() {
		if (colorL != null)
			colorL.close();
		if (colorR != null)
			colorR.close();
		if (colorM != null)
			colorM.close();
	}
}
