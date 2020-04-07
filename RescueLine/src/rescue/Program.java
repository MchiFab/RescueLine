package rescue;

import assistants.*;
import sensors.*;

import java.text.*;

import lejos.hardware.lcd.*;
import lejos.hardware.motor.*;
import lejos.hardware.port.I2CPort;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.Battery;

import java.io.Console;
import java.util.HashMap;

import lejos.hardware.*;
import lejos.hardware.device.SensorMux;
import lejos.hardware.ev3.EV3;
import lejos.hardware.sensor.*;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;
import lejos.robotics.*;

public class Program {

	private static DecimalFormat d = new DecimalFormat("0.00");
	private static float[] colorL;
	private static float[] colorM;
	private static float[] colorR;
	private static Navigation nav;

	public static void main(String[] args) {
		try {
			if (isBatteryLow()) {
				LCD.drawString("Battery low", 0, 0);
				System.exit(0);
			}

			initialize();

			Thread t = new Thread() {
				public void run() {
					while (true) {
						int btn = Button.waitForAnyPress(1000);
						if (btn == Button.ID_ESCAPE) {
							System.out.println("ev3 is kil");
							System.out.println("no");
							Motors.MotorStop();
							Motors.Dispose();
							SensorManager.getInstance().dispose();
							System.exit(0);
						}
					}
				}
			};
			t.start();

			// wait for input before launch
			LCD.clear();
			LCD.drawString("ok buddy", 2, 2);
			Button.waitForAnyPress();
			LCD.clear();

			while (true) {

				if (Button.DOWN.isDown()) {
					System.out.println(Battery.getBatteryCurrent());
					System.out.println(Battery.getVoltage());
				}

				if (Button.ENTER.isDown() || isBatteryLow()) {
					Motors.MotorStop();
					break;
				}

				if (Button.RIGHT.isDown())
					nav.Turn90Left();

				if (Button.LEFT.isDown())
					nav.Turn90Right();
//
//			colorL = ColorSensor.getColorLeft();
//			colorM = ColorSensor.getColorMiddle();
//			colorR = ColorSensor.getColorRight();
//
//			nav.check(colorL, colorM, colorR);
//
//			if (!nav.isAllowForward()) {
//				if (Colors.IsBlack(colorR) && nav.getOffset() != Offset.Left) {
//					Motors.RotateRight(2500);
//				}
//				if (Colors.IsBlack(colorL) && nav.getOffset() != Offset.Right) {
//					Motors.RotateLeft(2500);
//				}
//			}
//
//			if (nav.isAllowForward()) {
//				Motors.MotorForward(200);
//			} 
			}

			Motors.Dispose();
			SensorManager.getInstance().dispose();
			System.out.close();
			System.err.close();
			System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printRGB() {
		LCD.clear();
		System.out.println("L: " + d.format(SensorManager.getInstance().getColor().getColorLeft()[0]) + " "
				+ d.format(SensorManager.getInstance().getColor().getColorLeft()[1]) + " "
				+ d.format(SensorManager.getInstance().getColor().getColorLeft()[2]));
		System.out.println("M: " + d.format(SensorManager.getInstance().getColor().getColorMiddle()[0]) + " "
				+ d.format(SensorManager.getInstance().getColor().getColorMiddle()[1]) + " "
				+ d.format(SensorManager.getInstance().getColor().getColorMiddle()[2]));
		System.out.println("R: " + d.format(SensorManager.getInstance().getColor().getColorRight()[0]) + " "
				+ d.format(SensorManager.getInstance().getColor().getColorRight()[1]) + " "
				+ d.format(SensorManager.getInstance().getColor().getColorRight()[2]));
	}

	/**
	 * Initialize variables necessary for the program
	 */
	private static void initialize() {
		LCD.drawString("Loading...", 2, 2);
		Motors.initialize(MotorPort.D, MotorPort.B, MotorPort.A);
		SensorManager.getInstance().initialize(SensorPort.S1, SensorPort.S2, SensorPort.S3, SensorPort.S4);

		colorL = new float[3];
		colorM = new float[3];
		colorR = new float[3];
		nav = new Navigation();

		colorL = SensorManager.getInstance().getColor().getColorLeft();
		colorM = SensorManager.getInstance().getColor().getColorMiddle();
		colorR = SensorManager.getInstance().getColor().getColorRight();
	}

	/**
	 * Check if battery level is low
	 * 
	 * @return True if battery level is below 5.5V
	 */
	private static boolean isBatteryLow() {
		return Battery.getVoltage() < 5.5f;
	}
}
