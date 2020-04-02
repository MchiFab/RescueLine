package sensors;

import lejos.hardware.sensor.*;
import lejos.hardware.port.*;

/**
 * Class used to handle an EV3-Gyro-Sensor using an EV3-Sensor-Multiplexer
 * 
 * @author michi
 *
 */
public class I2CGyroSensor extends I2CSensor {

	private int prevAngle;
	private int angle;
	private int realAngle;

	private static final byte REG_GETDATA = 0x54;
	private static final int REG_SETMODE = 0x52;
	private static final byte MODE_ANGLE = 0;

	public I2CGyroSensor(I2CPort port, int address) {
		super(port, address);
		sendData(REG_SETMODE, MODE_ANGLE);
		angle = -1;
		realAngle = 0;
	}

	/**
	 * Reset the current angle to 0 degrees
	 */
	public void resetAngleMin() {
		realAngle = 0;
		angle = -1;
	}

	/**
	 * Reset the current angle to 360 degrees
	 */
	public void resetAngleMax() {
		realAngle = 360;
		angle = -1;
	}

	/**
	 * Gets the current angle from the Gyro-Sensor
	 * 
	 * @return The current angle
	 */
	public int getAngle() {
		byte[] buf = new byte[1];
		getData(REG_GETDATA, buf, 1); // Get degrees as byte (-128 - 127)

		prevAngle = angle;
		angle = buf[0] + 128; // Turn to natural byte (0 - 255)
		int diff = angle - prevAngle;

		/*
		 * As angle goes up until 255 and then continues at 0, diff has to be altered
		 * manually
		 */
		if ((diff > 0 || (prevAngle == 255 && angle == 0)) && prevAngle != -1)
			diff = 1;
		else if ((diff < 0 || (prevAngle == 0 && angle == 255)) && prevAngle != -1)
			diff = -1;
		else
			diff = 0;

		// Jump from 0 to 360 and vice versa
		if (realAngle > 0) {
			if (realAngle == 360 && diff == 1)
				realAngle = 0;
			else
				realAngle += diff;
		} else {
			if (diff == -1)
				realAngle = 360;
			else
				realAngle += diff;
		}

		return realAngle;
	}
}
