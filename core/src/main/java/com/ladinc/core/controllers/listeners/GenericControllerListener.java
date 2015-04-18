package com.ladinc.core.controllers.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.ladinc.core.controllers.GamePadControls;
import com.ladinc.core.controllers.GamePadControls.AnalogStick;

public class GenericControllerListener implements ControllerListener {
	public GamePadControls controls;
	
	public GenericControllerListener() {
		this.controls = new GamePadControls(this);
	}
	
	public int LeftAxisX;
	public int LeftAxisY;
	
	public int AlternateLeftAxisX = -1;
	public int AlternateLeftAxisY = -1;
	
	public int RightAxisX;
	public int RightAxisY;
	
	public int StartButton;
	public int StartButtonSecondry;
	public int ConfirmButton;
	public int BackButton;
	
	public int topFaceButton;
	public int leftFaceButton;
	
	public int DpadLeft = -1;
	public int DpadRight = -1;
	public int DpadUp = -1;
	public int DpadDown = -1;
	
	
	@Override
	public boolean accelerometerMoved(Controller arg0, int arg1, Vector3 arg2)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean axisMoved(Controller arg0, int arg1, float arg2)
	{
		
		Gdx.app.debug(
				"GenericControllerListener",
				"Axis moved: arg1=" + String.valueOf(arg1) + " arg2="
						+ String.valueOf(arg2));
		
		// Cant Use switch, arguements must be constants
		
		if (arg1 == LeftAxisX || arg1 == AlternateLeftAxisX)
		{
			controls.setAnalogMovementX(AnalogStick.left, arg2);
		}
		else if (arg1 == LeftAxisY || arg1 == AlternateLeftAxisY)
		{
			controls.setAnalogMovementY(AnalogStick.left, arg2);
		}
		else if (arg1 == RightAxisX)
		{
			controls.setAnalogMovementX(AnalogStick.right, arg2);
		}
		else if (arg1 == RightAxisY)
		{
			controls.setAnalogMovementY(AnalogStick.right, arg2);
		}
		
		return false;
	}
	
	@Override
	public boolean buttonDown(Controller arg0, int arg1)
	{
		
		Gdx.app.debug("GenericControllerListener",
				"buttonDown: arg1=" + String.valueOf(arg1));
		
		handleButtonPresses(arg1, true);
		
		return false;
	}
	
	@Override
	public boolean buttonUp(Controller arg0, int arg1)
	{
		Gdx.app.debug("GenericControllerListener",
				"buttonUp: arg1=" + String.valueOf(arg1));
		
		handleButtonPresses(arg1, false);
		
		return false;
	}
	
	private void handleButtonPresses(int buttonId, Boolean set)
	{
		
		if(buttonId == StartButton)
		{
			controls.setStartStatus(set);
		}
		else if (buttonId == StartButtonSecondry && set)
		{
			//Ouya menu button causes issues, have to ignore button releases from it.
			controls.setStartStatus(set);
		}
		else if(buttonId == ConfirmButton)
		{
			controls.setConfirmStatus(set);
		}
		else if(buttonId == BackButton)
		{
			controls.setBackStatus(set);
		}
		
		if(buttonId == topFaceButton)
		{
			controls.setTopFaceButtonPressed(set);
		}
		else if(buttonId == leftFaceButton)
		{
			controls.setLeftFaceButtonPressed(set);
		}
		
		if(buttonId == DpadLeft)
		{
			if(set)
			{
				controls.setDpadMovementX(-1);
			}
			else
			{
				controls.setDpadMovementX(0);
			}
		}
		else if(buttonId == DpadRight)
		{
			if(set)
			{
				controls.setDpadMovementX(1);
			}
			else
			{
				controls.setDpadMovementX(0);
			}
		}
		
		if(buttonId == DpadUp)
		{
			if(set)
			{
				controls.setDpadMovementY(1);
			}
			else
			{
				controls.setDpadMovementY(0);
			}
		}
		else if(buttonId == DpadDown)
		{
			if(set)
			{
				controls.setDpadMovementY(-1);
			}
			else
			{
				controls.setDpadMovementY(0);
			}
		}
	}
	
	@Override
	public void connected(Controller arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void disconnected(Controller arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean povMoved(Controller arg0, int arg1, PovDirection arg2)
	{
		// TODO Auto-generated method stub
		Gdx.app.debug(
				"GenericControllerListener",
				"povMoved: arg1=" + String.valueOf(arg1) + " arg2="
						+ String.valueOf(arg2));
		return false;
	}
	
	@Override
	public boolean xSliderMoved(Controller arg0, int arg1, boolean arg2)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean ySliderMoved(Controller arg0, int arg1, boolean arg2)
	{
		// TODO Auto-generated method stub
		return false;
	}
}