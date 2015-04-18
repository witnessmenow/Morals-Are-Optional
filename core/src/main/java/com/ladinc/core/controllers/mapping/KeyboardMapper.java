package com.ladinc.core.controllers.mapping;

import com.badlogic.gdx.Input.Keys;

public class KeyboardMapper {
	public static final String id = "Desktop Controls";
	
	public static boolean isAccelerateButton(int keyCode)
	{
		return keyCode == Keys.W || keyCode == Keys.UP;
	}
	
	public static boolean isDeaccelerateButton(int keyCode)
	{
		return keyCode == Keys.S || keyCode == Keys.DOWN;
	}
	
	public static boolean isRotateLeftButton(int keyCode)
	{
		return keyCode == Keys.A || keyCode == Keys.LEFT;
	}
	
	public static boolean isRotateRightButton(int keyCode)
	{
		return keyCode == Keys.D || keyCode == Keys.RIGHT;
	}
	
	public static boolean isHandbrakeButton(int keyCode)
	{
		return keyCode == Keys.SPACE;
	}
	
	public static boolean isStartButton(int keyCode)
	{
		return keyCode == Keys.ESCAPE;
	}
	
	public static boolean isConfirmButton(int keyCode)
	{
		return keyCode == Keys.ENTER;
	}
	
	public static boolean isBackButton(int keyCode)
	{
		return keyCode == Keys.BACKSPACE;
	}
	
	public static boolean isExtraButton1(int keyCode)
	{
		return keyCode == Keys.NUM_9;
	}
	
	public static boolean isExtraButton2(int keyCode)
	{
		return keyCode == Keys.NUM_0;
	}
}