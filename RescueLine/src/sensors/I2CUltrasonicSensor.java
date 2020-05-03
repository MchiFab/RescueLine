package sensors;

import lejos.hardware.port.I2CPort;
import lejos.hardware.sensor.I2CSensor;

/**
 * Class used to handle an EV3-Ultrasonic-Sensor using an EV3-Sensor-Multiplexer
 * 
 * @author michi
 *
 */
public class I2CUltrasonicSensor extends I2CSensor {

	private int distance;
	private int byteCount;
	private int realDistance;

	public I2CUltrasonicSensor(I2CPort port, int address) {
		super(port, address);
		sendData(EV3SensorMux.REG_SETMODE, EV3SensorMux.MODE_MM);

		distance = 0;
		byteCount = 0;
		realDistance = 0;
	}

	/**
	 * Calculates a distance
	 * 
	 * @return Distance in millimeters
	 */
	public int getDistance() {
		distance = getDistanceByte()[0];
		byteCount = getDistanceByte()[1];

		if (distance < 0)
			distance = 256 + distance;

		realDistance = (distance + 256 * byteCount);

		return realDistance;
	}

	/**
	 * Gets raw distance byte values
	 * 
	 * @return Byte array, first entry is distance from -128 to 127, second is count
	 *         of whole bytes from 0 to 9
	 */
	private int[] getDistanceByte() {
		byte[] buf = new byte[2];
		getData(0x54, buf, 2);

		int[] re = new int[buf.length];

		for (int i = 0; i < re.length; i++)
			re[i] = buf[i];

		return re;
	}
}
