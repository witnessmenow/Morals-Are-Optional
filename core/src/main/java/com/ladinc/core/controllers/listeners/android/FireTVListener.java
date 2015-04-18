package com.ladinc.core.controllers.listeners.android;

import com.badlogic.gdx.controllers.mappings.Ouya;
import com.ladinc.core.controllers.FireTV;
import com.ladinc.core.controllers.listeners.GenericControllerListener;

public class FireTVListener extends GenericControllerListener 
{
	
	public FireTVListener()
	{
		
		this.StartButton = FireTV.BUTTON_MENU;
		this.StartButtonSecondry = FireTV.BUTTON_PLAY_PAUSE;
		this.ConfirmButton = FireTV.BUTTON_A;
		this.BackButton = FireTV.BUTTON_B;
		
		this.topFaceButton = FireTV.BUTTON_Y;
		this.leftFaceButton = FireTV.BUTTON_X;
		
		this.LeftAxisX = FireTV.AXIS_LEFT_X;
		this.LeftAxisY = FireTV.AXIS_LEFT_Y;
		
		this.AlternateLeftAxisX = FireTV.AXIS_DPAD_X;
		this.AlternateLeftAxisY = FireTV.AXIS_DPAD_Y;
		
		// Fix this!
		this.RightAxisX = FireTV.AXIS_RIGHT_X;
		this.RightAxisY = FireTV.AXIS_RIGHT_Y;
		

	}

}
