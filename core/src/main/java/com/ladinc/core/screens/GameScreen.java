package com.ladinc.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ladinc.core.McpCah;

public class GameScreen implements Screen
{	
	public static enum State {
		waitingForStart, playersChooseCard, judgeChoosesAnswer, gameOver
	};
	
	
	public McpCah game;
	private final OrthographicCamera camera;
	private final int screenHeight;
	private final int screenWidth;
	private final SpriteBatch spriteBatch;
	
	public State currentState = State.waitingForStart;
	
	public GameScreen(McpCah g)
	{
		this.game = g;
		
		this.screenWidth = 1920;
		this.screenHeight = 1080;
		
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.screenWidth, screenHeight);

		this.spriteBatch = new SpriteBatch();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
}

