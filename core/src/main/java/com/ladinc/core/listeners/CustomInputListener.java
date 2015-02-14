package com.ladinc.core.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.ladinc.core.objects.Player;
import com.ladinc.core.screens.GameScreen;

public class CustomInputListener extends InputListener
{
	private Player player;

    public CustomInputListener(Player player) 
    {
    	super();
        this.player = player;
    }
    
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
    {
    	player.isPaused = !player.isPaused;
    	
    	GameScreen.CHANGES_TO_PLAYERS = true;
    	
        return true;
    }
}
