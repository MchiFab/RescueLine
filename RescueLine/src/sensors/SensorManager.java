package sensors;

import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

/**
 * Class used to handle all the Sensors
 * 
 * @author michi
 *
 */
public class SensorManager {
	private ColorSensor color;
	private EV3SensorMux mux;
	private I2CGyroSensor gyro;
	private static SensorManager instance;

	/**
	 * Static instance so other members may be non-static
	 * 
	 * @return A static instance of class SensorManager
	 */
	public static SensorManager getInstance() {
		if (instance == null)
			instance = new SensorManager();
		return instance;
	}

	public ColorSensor getColor() {
		return color;
	}

	/**
	 * Gets Gyro-Sensor from Multiplexer
	 * 
	 * @return
	 */
	public I2CGyroSensor getGyro() {
		return gyro;
	}

	/**
	 * Initialize all sensors
	 * 
	 * @param colorRight  Port of right Color-Sensor
	 * @param colorMiddle Port of middle Color-Sensor
	 * @param colorLeft   Port of left Color-Sensor
	 * @param muxPort     Port of Multiplexer
	 */
	public void initialize(Port colorRight, Port colorMiddle, Port colorLeft, Port muxPort) {
		color = new ColorSensor();
		mux = new EV3SensorMux(muxPort);
		gyro = new I2CGyroSensor(mux.getPort(), EV3SensorMux.MUX_PORT_C2);

		color.initialize(colorRight, colorMiddle, colorLeft);
	}

	/**
	 * Dispose all sensors
	 */
	public void dispose() {
		color.dispose();
		mux.dispose();
	}
}
