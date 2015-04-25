package com.ladinc.core.controllers.listeners.android;

import tv.ouya.console.api.OuyaController;

import com.badlogic.gdx.controllers.mappings.Ouya;
import com.ladinc.core.controllers.listeners.GenericControllerListener;

public class OuyaListener extends GenericControllerListener {
	
	public OuyaListener() {
		
		// this.StartButton = Ouya.BUTTON_MENU;82
		//this.StartButton = 108;
		this.StartButton = OuyaController.BUTTON_MENU;
		this.StartButtonSecondry = 82;
		this.ConfirmButton = OuyaController.BUTTON_O;
		this.BackButton = OuyaController.BUTTON_A;
		
		this.topFaceButton = OuyaController.BUTTON_Y;
		this.leftFaceButton = OuyaController.BUTTON_U;
		
		this.LeftAxisX = OuyaController.AXIS_LS_X;
		this.LeftAxisY = OuyaController.AXIS_LS_Y;
		
		// Fix this!
		this.RightAxisX = OuyaController.AXIS_RS_X;
		this.RightAxisY = OuyaController.AXIS_RS_Y;
		
		this.DpadLeft = OuyaController.BUTTON_DPAD_LEFT;
		this.DpadRight = OuyaController.BUTTON_DPAD_RIGHT;
		this.DpadUp = OuyaController.BUTTON_DPAD_UP;
		this.DpadDown = OuyaController.BUTTON_DPAD_DOWN;
	}
	
}