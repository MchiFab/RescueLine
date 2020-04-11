package sensors;

import lejos.hardware.port.I2CException;
import java.util.*;

import lejos.hardware.port.I2CPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.I2CSensor;
import lejos.internal.ev3.EV3I2CPort;

/**
 * Class to handle multiplexer
 * 
 * @author michi
 *
 */
public class EV3SensorMux {

	private I2CPort port;

	public static final int MUX_PORT_C1 = 0xA0;
	public static final int MUX_PORT_C2 = 0xA2;
	public static final int MUX_PORT_C3 = 0xA4;

	public static final int REG_GETDATA = 0x54;
	public static final int REG_SETMODE = 0x52;
	public static final byte MODE_ANGLE = 0;
	public static final byte MODE_CM = 0;

	public I2CPort getPort() {
		return port;
	}

	public EV3SensorMux(Port p) {
		if (port == null)
			port = p.open(I2CPort.class);
		port.setType(10); // TYPE_LOWSPEED
	}

	public void dispose() {
		if (port != null)
			port.close();
	}
}
