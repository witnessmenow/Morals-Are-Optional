package com.ladinc.core.screens;


import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ladinc.core.McpCah;
import com.ladinc.core.objects.Player;

public class GameScreen implements Screen
{	
	public static enum State {
		endOfRound, playersChooseCard, judgeChoosesAnswer, gameOver
	};
	
	public static String lastWiningWhiteCard = null;
	
	private String blackCard;
	
	public McpCah game;
	private final OrthographicCamera camera;
	private final int screenHeight;
	private final int screenWidth;
	private final SpriteBatch spriteBatch;
	
	public State currentState = State.endOfRound;
	
	private int roundNumber = 0;
	
	private Boolean gameOver = false;
	
	public BitmapFont font;
	
	public GameScreen(McpCah g)
	{
		this.game = g;
		initializeFont();
		this.screenWidth = 1920;
		this.screenHeight = 1080;
		
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.screenWidth, screenHeight);

		this.spriteBatch = new SpriteBatch();
	}
	
	private void initializeFont()
	{		
    	font = new BitmapFont(Gdx.files.internal("fonts/Swis-721-50.fnt"), Gdx.files.internal("fonts/Swis-721-50.png"), false);
    	//Make text black
    	font.setColor(Color.WHITE);
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
			roundNumber ++;
			
			getNewBlackCard();
			moveToNextJudge();
			repopulateHands();
			clearSelectedCards();
			
			if(Player.OUT_OF_WHITE_CARDS)
			{
				this.gameOver = true;
			}
			
			if(this.gameOver)
			{
				currentState = State.gameOver;
			}
			else
			{
				currentState = State.playersChooseCard;
			}
			
		}
		
		if(currentState == State.playersChooseCard)
		{
			if(haveAllNonJudgesSelectedACard())
			{
				currentState = State.judgeChoosesAnswer;
			}
		}
		
		populateHearbeats();
		
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
		int i = 0;
		for(Player p : this.game.players.values())
		{
			if(p.isJudge)
			{
				p.isJudge = false;
				indexOfCurrentJudge = i;
				break;
			}
			
			i++;
		}
		
		int indexOfNextJudge = indexOfCurrentJudge + 1;
		
		if(indexOfNextJudge >= game.players.size())
		{
			indexOfNextJudge = 0;
		}
		
		i = 0;
		for(Player p : this.game.players.values())
		{
			
			if(i == indexOfNextJudge)
			{
				p.isJudge = true;
			}
			i++;
		}
	}
	
	private void getNewBlackCard()
	{
		if(McpCah.AVAILABLE_BLACK_CARDS.size() == 0)
		{
			
		}
		else
		{
			Random r = new Random();
			int index = r.nextInt(McpCah.AVAILABLE_BLACK_CARDS.size());
		
			blackCard = McpCah.AVAILABLE_BLACK_CARDS.get(index);
			McpCah.AVAILABLE_BLACK_CARDS.remove(index);
		}
	}
	
	public void repopulateHands()
	{
		for(Player p : this.game.players.values())
		{
			p.populateHand();
		}
	}
	
	public void clearSelectedCards()
	{
		for(Player p : this.game.players.values())
		{
			p.selectedCard = null;
		}
	}
	
	private boolean haveAllNonJudgesSelectedACard()
	{
		
		for(Player p : this.game.players.values())
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
	
	@SuppressWarnings("unchecked")
	private void populateHearbeats()
	{
		this.game.mcp.hearbeatResponses.clear();
		for(Player p : this.game.players.values())
		{
			JSONObject obj = new JSONObject();
		
			if(!p.isJudge)
			{
				obj.put("cards", p.cardsToJsonArray());
			}
			else
			{
				if(currentState == State.playersChooseCard)
				{
					obj.put("judge", "wait");
				}
				else if(currentState == State.judgeChoosesAnswer)
				{
					obj.put("selected", selectedWhiteCards());
				}
			}
			
			obj.put("blackCard", this.blackCard);

			this.game.mcp.hearbeatResponses.put(p.id, obj);
		}
	}
	
	public JSONArray selectedWhiteCards()
	{
		JSONArray array = new JSONArray();
		
		//Populate answers from players
		for(Player NewP : this.game.players.values())
		{			
			if(!NewP.isJudge)
			{
				JSONObject innerObj = new JSONObject();
				innerObj.put("playerID", NewP.id);
				innerObj.put("selectedCard", NewP.selectedCard);
				array.add(innerObj);
			}	
		}
		
		return array;
	}
	

}

