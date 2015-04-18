package com.ladinc.core.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.ladinc.core.controllers.listeners.GenericControllerListener;

public class GamePadControls implements IControls {
	
	private final float TRIGGER_DEADZONE = 0.350f;
	
	private final Vector2 leftMovement;
	
	private final GenericControllerListener listener;
	
	private final Vector2 rightMovement;
	
	private boolean handbreakPressed;
	private boolean acceleratePressed;
	private boolean reversePressed;
	
	public boolean active;
	
	private boolean startButtonPressed;
	private boolean confirmButtonPressed;
	private boolean backButtonPressed;
	
	private boolean topFaceButtonPressed;
	private boolean leftFaceButtonPressed;
	
	private boolean interestedInMenuPresses = true;
	
	private float coolDownTimer;
	
	public GamePadControls(GenericControllerListener listen) {
		this.listener = listen;
		this.leftMovement = new Vector2(0, 0);
		this.rightMovement = new Vector2(0, 0);
	}
	
	private void activateController()
	{
		active = true;
	}
	
	public static enum AnalogStick {
		left, right
	};
	
	public void setAnalogMovementX(AnalogStick stick, float x)
	{
		switch (stick)
		{
			case left:
				leftMovement.x = processAndActivate(x, TRIGGER_DEADZONE);
				break;
			case right:
				rightMovement.x = processAndActivate(x, TRIGGER_DEADZONE);
				break;
		
		}
	}
	
	public void setAnalogMovementY(AnalogStick stick, float y)
	{
		// inverting direction so up is up
		
		switch (stick)
		{
			case left:
				leftMovement.y = (-1)
						* (processAndActivate(y, TRIGGER_DEADZONE));
				break;
			case right:
				rightMovement.y = (-1)
						* (processAndActivate(y, TRIGGER_DEADZONE));
				break;
		
		}
	}
	
	public void setDpadMovementX(int value)
	{
		leftMovement.x = value;
	}
	
	public void setDpadMovementY(int value)
	{
		leftMovement.y = value;
	}
	
	
	public float processAndActivate(float movement, float deadzone)
	{
		float processedMovement = processMovment(movement, deadzone);
		
		if (!active && processedMovement > 0)
		{
			activateController();
		}
		
		return processedMovement;
	}
	
	public float processMovment(float movement, float deadzone)
	{
		// Overall power ignoring direction
		float power = movement;
		
		if (movement < 0)
			power = movement * (-1);
		
		if (power <= deadzone)
		{
			// The direction being moved is not greater than the deadzone,
			// ignoring movement
			return 0f;
		}
		else
		{
			// if(!active)
			// activateController();
			
			// have a meaningful value for X
			float movementAbovePower = power - deadzone;
			float availablePower = 1f - deadzone;
			movementAbovePower = movementAbovePower / availablePower;
			
			if (movement > 0)
			{
				return movementAbovePower;
			}
			else
			{
				// convert result to take into account direction
				return (-1) * movementAbovePower;
			}
		}
		
	}
	
	public void setAccelerateButton(boolean pressed)
	{
		if (!active && pressed)
		{
			activateController();
		}
		
		this.acceleratePressed = pressed;
	}
	
	public void setReverseButton(boolean pressed)
	{
		if (!active && pressed)
		{
			activateController();
		}
		
		this.reversePressed = pressed;
	}
	
	public void setHandBreakButton(boolean pressed)
	{
		if (!active && pressed)
		{
			activateController();
		}
		
		this.handbreakPressed = pressed;
	}

	public void setStartStatus(boolean pressed)
	{
		if (!active && pressed)
		{
			activateController();
		}
		
		this.startButtonPressed = pressed;
	}
	
	public void setConfirmStatus(boolean pressed)
	{
		if(interestedInMenuPresses && pressed)
		{
			if (!active)
			{
				activateController();
			}
			
			this.confirmButtonPressed = true;
		}
		else
		{
			this.confirmButtonPressed = false;
		}
	}
	
	public void setBackStatus(boolean pressed)
	{
		//if(interestedInMenuPresses && pressed)
		if(pressed)
		{
			if (!active)
			{
				activateController();
			}
			
			this.backButtonPressed = true;
		}
		else
		{
			this.backButtonPressed = false;
		}
	}
	
	@Override
	public boolean getStartStatus() 
	{
		if(this.startButtonPressed)
		{
			//Dont allow one press be considered multiple
			this.startButtonPressed = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean getConfirmStatus() 
	{
		Gdx.app.debug(
				"getConfirmStatus",
				"getConfirmStatus =" + String.valueOf(confirmButtonPressed));
		
		if(this.confirmButtonPressed)
		{
			//Dont allow one press be considered multiple
			this.confirmButtonPressed = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean getBackStatus() 
	{
		if(this.backButtonPressed)
		{
			//Dont allow one press be considered multiple
			this.backButtonPressed = false;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean getTopFaceButtonStatus() 
	{
		if(this.topFaceButtonPressed)
		{
			//Dont allow one press be considered multiple
			this.topFaceButtonPressed = false;
			return true;
		}
		return false;
	}

	public void setTopFaceButtonPressed(boolean extraButton1Pressed) 
	{
		if (!active && extraButton1Pressed)
		{
			activateController();
		}
		
		this.topFaceButtonPressed = extraButton1Pressed;
		
	}

	@Override
	public boolean getLeftFaceButtonStatus() 
	{
		if(this.leftFaceButtonPressed)
		{
			//Dont allow one press be considered multiple
			this.leftFaceButtonPressed = false;
			return true;
		}
		return false;
	}

	public void setLeftFaceButtonPressed(boolean extraButton2Pressed) 
	{
		
		if (!active && extraButton2Pressed)
		{
			activateController();
		}
		
		this.leftFaceButtonPressed = extraButton2Pressed;
	}

	@Override
	public int getMenuXDireciton() 
	{
		if(leftMovement.x > 0)
		{
			return 1;
		}
		else if (leftMovement.x < 0)
		{
			return -1;
		}

		return 0;
	}

	@Override
	public int getMenuYDireciton() 
	{
		if(leftMovement.y > 0)
		{
			return 1;
		}
		else if (leftMovement.y < 0)
		{
			return -1;
		}
		
		return 0;
	}
	
	@Override
	public boolean isCoolDownActive(float delta)
	{
		if(coolDownTimer > 0)
		{
			coolDownTimer -= delta;
		}
		
		return coolDownTimer > 0;
		
	}
	
	@Override
	public void setCoolDown(float coolDownTime)
	{
		coolDownTimer = coolDownTime;
	}
	
}
