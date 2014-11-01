package com.ladinc.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ladinc.core.McpCah;
import com.ladinc.core.objects.Player;

public class GameScreen implements Screen
{	
	public static enum State {
		endOfRound, playersChooseCard, judgeChoosesAnswer, gameOver
	};
	
	
	public McpCah game;
	private final OrthographicCamera camera;
	private final int screenHeight;
	private final int screenWidth;
	private final SpriteBatch spriteBatch;
	
	public State currentState = State.endOfRound;
	
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
	public void dispose() 
	{
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
	public void render(float arg0) 
	{
		if(currentState == State.endOfRound)
		{
			moveToNextJudge();
			repopulateHands();
			clearSelectedCards();
			currentState = State.playersChooseCard;
			
		}
		
		if(currentState == State.playersChooseCard)
		{
			if(haveAllNonJudgesSelectedACard())
			{
				currentState = State.judgeChoosesAnswer;
			}
		}
		
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
	
	public void moveToNextJudge()
	{
		int indexOfCurrentJudge = -1;
		for(int i = 0; i < game.PlayersList.size(); i++)
		{
			if(game.PlayersList.get(i).isJudge)
			{
				game.PlayersList.get(i).isJudge = false;
				indexOfCurrentJudge = i;
				break;
			}
		}
		
		int indexOfNextJudge = indexOfCurrentJudge + 1;
		
		if(indexOfNextJudge >= game.PlayersList.size())
		{
			indexOfNextJudge = 0;
		}
		
		game.PlayersList.get(indexOfNextJudge).isJudge = true;
	}
	
	public void repopulateHands()
	{
		for(Player p : this.game.PlayersList)
		{
			p.populateHand();
		}
	}
	
	public void clearSelectedCards()
	{
		for(Player p : this.game.PlayersList)
		{
			p.selectedCard = null;
		}
	}
	
	private boolean haveAllNonJudgesSelectedACard()
	{
		
		for(Player p : this.game.PlayersList)
		{
			if(!p.isJudge)
			{
				if(p.selectedCard == null)
				{
					return false;
				}
			}
		}
		
		//If we got here all players have selected cards
		return true;
	}

}

