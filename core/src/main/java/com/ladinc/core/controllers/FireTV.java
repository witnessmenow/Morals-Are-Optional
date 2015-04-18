package com.ladinc.core.controllers;

import java.lang.reflect.Field;

public class FireTV 
{
	
	/** whether the app is running on a real FireTV device **/
	public static final boolean runningOnFireTV;
	
	/** To allow users to simulate running on FireTV **/
	public static boolean simulateRunningOnFireTV = false;
	
	public static final String ID = "Amazon Fire Game Controller";
	public static final int BUTTON_A = 96;
	public static final int BUTTON_X = 99;
	public static final int BUTTON_Y = 100;
	public static final int BUTTON_B = 97;
	public static final int BUTTON_MENU = 82;
	public static final int BUTTON_BACK = 4;
	
	public static final int BUTTON_L1 = 102;
	public static final int BUTTON_L3 = 106;
	public static final int BUTTON_R1 = 103;
	public static final int BUTTON_R3 = 107;
	
	public static final int BUTTON_REWIND = 89;
	public static final int BUTTON_PLAY_PAUSE = 85;
	public static final int BUTTON_FAST_FOREWARD = 90;
	
	public static final int AXIS_LEFT_X = 0;
	public static final int AXIS_LEFT_Y = 1;
	public static final int AXIS_L2_TRIGGER = 5;
	public static final int AXIS_RIGHT_X = 2;
	public static final int AXIS_RIGHT_Y = 3;
	public static final int AXIS_R2_TRIGGER = 4;
	
	public static final int AXIS_DPAD_X = 7;
	public static final int AXIS_DPAD_Y = 6;
	
	public static final float STICK_DEADZONE = 0.25F;

	static {
		boolean isAFT= false;
		try {
			Class<?> buildClass = Class.forName("android.os.Build");
			Field manufacturerField = buildClass.getDeclaredField("MANUFACTURER");
			String manufacturer = manufacturerField.get(null).toString();
			
			Field modelField = buildClass.getDeclaredField("MODEL");
			String model = modelField.get(null).toString();
			
			//Documentation states to only check first 3 letters for forward compatibility
			model = model.substring(0, 3);
			
			isAFT = "Amazon".equals(manufacturer) && "AFT".equals(model);
		} catch (Exception e) {
		}
		
		runningOnFireTV = isAFT;
	}
	
	/** To allow users to simulate running on FireTV **/
	public static boolean isRunningOnFireTV()
	{
		return runningOnFireTV || simulateRunningOnFireTV;
	}
}
