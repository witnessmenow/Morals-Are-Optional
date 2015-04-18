package com.ladinc.core.controllers.listeners.android;

import com.badlogic.gdx.controllers.mappings.Ouya;
import com.ladinc.core.controllers.listeners.GenericControllerListener;

public class OuyaListener extends GenericControllerListener {
	
	public OuyaListener() {
		
		// this.StartButton = Ouya.BUTTON_MENU;82
		this.StartButton = 108;
		this.StartButtonSecondry = 82;
		this.ConfirmButton = Ouya.BUTTON_O;
		this.BackButton = Ouya.BUTTON_A;
		
		this.topFaceButton = Ouya.BUTTON_Y;
		this.leftFaceButton = Ouya.BUTTON_U;
		
		this.LeftAxisX = Ouya.AXIS_LEFT_X;
		this.LeftAxisY = Ouya.AXIS_LEFT_Y;
		
		// Fix this!
		this.RightAxisX = Ouya.AXIS_RIGHT_X;
		this.RightAxisY = Ouya.AXIS_RIGHT_Y;
		
		this.DpadLeft = Ouya.BUTTON_DPAD_LEFT;
		this.DpadRight = Ouya.BUTTON_DPAD_RIGHT;
		this.DpadUp = Ouya.BUTTON_DPAD_UP;
		this.DpadDown = Ouya.BUTTON_DPAD_DOWN;
	}
	
}