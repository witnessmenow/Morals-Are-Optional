package com.ladinc.core.controllers;

public interface IControls {
	
	public boolean getStartStatus();
	
	public boolean getConfirmStatus();
	
	public boolean getBackStatus();
	
	public boolean getTopFaceButtonStatus();
	
	public boolean getLeftFaceButtonStatus();
	
	public int getMenuXDireciton();
	public int getMenuYDireciton();
	
	public boolean isCoolDownActive(float delta);
	
	public void setCoolDown(float coolDownTime);
	
}
