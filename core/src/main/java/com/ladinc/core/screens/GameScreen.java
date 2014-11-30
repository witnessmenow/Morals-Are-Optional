package com.ladinc.core.screens;


import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ladinc.core.McpCah;
import com.ladinc.core.objects.Player;

public class GameScreen implements Screen
{	
	public static enum State {
		startOfRound, endOfRound, playersChooseCard, judgeChoosesAnswer, gameOver
	};
	
	public static String lastWiningWhiteCard = null;
	public static String lastWiningId = null;
	public static String lastRevealedWhiteCard = null;
	
	public static Boolean startNextFlag = false;
	
	private String blackCard;
	
	public McpCah game;
	private final OrthographicCamera camera;
	private final int screenHeight;
	private final int screenWidth;
	private final SpriteBatch spriteBatch;
	
	public State currentState = State.startOfRound;
	
	private int roundNumber = 0;
	
	private Boolean gameOver = false;
	
	public BitmapFont font;
	public BitmapFont labelFont;
	
	public Label blackCardLabel;
	
	public Sprite blackCardSprite;
	
	public Label whiteCardLabel;
	
	public Sprite whiteCardSprite;
	
	public Sprite tickSprite;
	public Sprite judgeSprite;
	public Sprite timerSprite;
	
	Timer timer = null;
	
	private Sprite bg;
	
	public GameScreen(McpCah g)
	{
		Gdx.app.debug("GameScreen", "Start of constructor");
		
		this.game = g;
		initializeFont();
		this.screenWidth = 1920;
		this.screenHeight = 1080;
		
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.screenWidth, screenHeight);

		this.spriteBatch = new SpriteBatch();
		
		tickSprite = new Sprite(new Texture(Gdx.files.internal("tick.png")));
		
		tickSprite.setColor(Color.WHITE);
		
		judgeSprite = new Sprite(new Texture(Gdx.files.internal("judge.png")));
		judgeSprite.setColor(Color.WHITE);
		
		timerSprite = new Sprite(new Texture(Gdx.files.internal("timer.png")));
		timerSprite.setColor(Color.WHITE);
		
		blackCardSprite = new Sprite(new Texture(Gdx.files.internal("blankCard.png")));
		blackCardSprite.setColor(Color.BLACK);
		
		whiteCardSprite = new Sprite(new Texture(Gdx.files.internal("blankCard.png")));
		whiteCardSprite.setColor(Color.WHITE);
		
		blackCardLabel = new Label("", new Label.LabelStyle(labelFont, Color.WHITE));
		
		whiteCardLabel = new Label("", new Label.LabelStyle(labelFont, Color.BLACK));
		
		this.bg = this.game.backgorund;
		
		Gdx.app.debug("GameScreen", "End of constructor");
	}
	
	public boolean checkForSystemBackButton()
	{
		if (Gdx.input.isKeyPressed(Keys.BACK))
		{
			return true;
		}
		
		return false;
	}
	
	private void initializeFont()
	{		
    	font = new BitmapFont(Gdx.files.internal("fonts/Swis-721-50.fnt"), Gdx.files.internal("fonts/Swis-721-50.png"), false);
    	//Make text black
    	font.setColor(Color.BLACK);
    	
    	labelFont = new BitmapFont(Gdx.files.internal("fonts/Swis-721-32.fnt"), Gdx.files.internal("fonts/Swis-721-32.png"), false);
    	//Make text black
    	labelFont.setColor(Color.WHITE);
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
	public void pause() 
	{
		Gdx.app.debug("pause", "entering Pause");
		
		if(this.timer != null)
		{
			timer.cancel();
			timer.purge();
		}
		
		this.timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() 
			  {
				  Gdx.app.debug("Game Screen - pause", "fake loop");
				  handleGameState();
				  populateHearbeats();
			  }
			}, 100, 100);
		
	}

	private boolean buttonJustPressCoolDown = false;
	
	@Override
	public void render(float arg0) 
	{
		Gdx.app.debug("GameScreen", "game loop");
		
		handleGameState();
		
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		
		populateHearbeats();
		
		drawSprites();
		
		if(Gdx.input.isKeyPressed(Input.Keys.N))
		{
			if(!buttonJustPressCoolDown)
			{
				getNewBlackCard();
			}
			buttonJustPressCoolDown = true;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
		{
			if(!buttonJustPressCoolDown)
			{
				this.currentState = State.startOfRound;
			}
			buttonJustPressCoolDown = true;
		}
		else if(buttonJustPressCoolDown)
		{
			buttonJustPressCoolDown = false;
		}
		
	}
	
	private void handleGameState()
	{
		Gdx.app.debug("GameScreen", "handleGameState");
		
		if(currentState == State.startOfRound)
		{
			Gdx.app.debug("GameScreen", "State.startOfRound");
			
			roundNumber ++;
			
			lastWiningWhiteCard = null;
			lastRevealedWhiteCard = null;
			selectedWhiteCards = null;
			getNewBlackCard();
			moveToNextJudge();
			repopulateHands();
			//clearSelectedCards();
			
			if(Player.OUT_OF_WHITE_CARDS)
			{
				this.gameOver = true;
			}
			
			if(this.gameOver)
			{
				currentState = State.gameOver;
				Gdx.app.debug("GameScreen", "State.gameOver");
			}
			else
			{
				currentState = State.playersChooseCard;
			}
			
		}
		
		if(currentState == State.playersChooseCard)
		{
			Gdx.app.debug("GameScreen", "State.playersChooseCard");
			if(haveAllNonJudgesSelectedACard())
			{
				currentState = State.judgeChoosesAnswer;
			}
		}
		
		if(currentState == State.judgeChoosesAnswer)
		{
			if(lastWiningWhiteCard != null)
			{
				currentState = State.endOfRound;
			}
		}
		
		if(currentState == State.endOfRound)
		{
			if(GameScreen.startNextFlag)
			{
				this.currentState = State.startOfRound;
				GameScreen.startNextFlag = false;
			}
		}
		
		Gdx.app.debug("GameScreen", "handleGameState end");
	}
	
	private void drawSprites()
	{
    	Gdx.gl.glClearColor(0f, 0f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.spriteBatch.begin();
		this.bg.draw(spriteBatch);
		displayPlayers(spriteBatch);
		DisplayMCPAddressText(spriteBatch);
		drawBlackCard(spriteBatch);
		if(lastRevealedWhiteCard != null || lastWiningWhiteCard != null)
		{
			drawWhiteCard(spriteBatch);
		}
		
		this.spriteBatch.end();
	}
	
	private void drawBlackCard(SpriteBatch sb)
	{
		float blackCardYBase = screenHeight/2 + 100f;
		
		blackCardSprite.setPosition(screenWidth/2, blackCardYBase - 100f);
		blackCardSprite.draw(sb);

		this.blackCardLabel.setAlignment(Align.left | Align.top);
		this.blackCardLabel.setText(blackCard);
		this.blackCardLabel.setColor(Color.WHITE);
		this.blackCardLabel.setWrap(true);
		this.blackCardLabel.setWidth(320f);
		this.blackCardLabel.setPosition(screenWidth/2 + 15f, blackCardYBase + 300f);
		this.blackCardLabel.draw(sb, 1);
	}
	
	private void drawWhiteCard(SpriteBatch sb)
	{
		float whiteCardYBase = screenHeight/2 - 400f;
		
		whiteCardSprite.setPosition(screenWidth/2, whiteCardYBase - 100f);
		whiteCardSprite.draw(sb);

		this.whiteCardLabel.setAlignment(Align.left | Align.top);
		if(this.currentState == State.judgeChoosesAnswer)
		{
			this.whiteCardLabel.setText(lastRevealedWhiteCard);
		}
		else
		{
			this.whiteCardLabel.setText(lastWiningWhiteCard);
			
			String name = this.game.players.get(lastWiningId).name;
			
			float xPos = 1625f - font.getBounds("Winner:").width/2;
			float yPos = 450f;
			
			font.draw(spriteBatch, "Winner:", xPos, yPos);
			
			xPos = 1625f - font.getBounds(name).width/2;
			yPos = 350f;
			
			font.draw(spriteBatch, name, xPos, yPos);
		}
		
		this.whiteCardLabel.setColor(Color.BLACK);
		this.whiteCardLabel.setWrap(true);
		this.whiteCardLabel.setWidth(320f);
		this.whiteCardLabel.setPosition(screenWidth/2 + 15f, whiteCardYBase + 300f);
		this.whiteCardLabel.draw(sb, 1);
	}
	
	private void displayPlayers(SpriteBatch sb)
	{
		
		float yPos = (this.screenHeight) - (this.screenHeight/7);
		
		String playerText = "";
		
		int i = this.game.players.size();
		
		font.setColor(Color.WHITE);
		
		for(Map.Entry<String, Player> entry : this.game.players.entrySet())
		{		
			playerText = entry.getValue().getName() + ": " + entry.getValue().score;
			
			float xPos = (this.screenWidth/14);
			float yPosAdjusted = (float) (yPos -  (i-1)*font.getBounds(playerText).height*(1.8));
			
			font.draw(sb, playerText, xPos, yPosAdjusted);
			
			xPos = xPos - tickSprite.getWidth() - 10f;
			
			//entry.getValue().selectedCard = "card";
			
			if(entry.getValue().isJudge)
			{
				yPosAdjusted = yPosAdjusted - tickSprite.getHeight() + 3f;
				judgeSprite.setPosition(xPos, yPosAdjusted);
				judgeSprite.draw(sb);
			}
			else if(entry.getValue().selectedCard != null)
			{
				yPosAdjusted = yPosAdjusted - tickSprite.getHeight() + 10f;
				tickSprite.setPosition(xPos, yPosAdjusted);
				tickSprite.draw(sb);
			}
			else if(!entry.getValue().dealtIn)
			{
				yPosAdjusted = yPosAdjusted - timerSprite.getHeight() + 3f;
				timerSprite.setPosition(xPos, yPosAdjusted);
				timerSprite.draw(sb);
			}
			
			i--;
		}
	}
	
	private void DisplayMCPAddressText(SpriteBatch sb)
	{
		String text = this.game.mcp.getAddressForClients();
		
		float xPos = (this.screenWidth) - font.getBounds(text).width - 50f;
		float yPos = (this.screenHeight) - 20f;
		
		font.setColor(Color.YELLOW);
		font.draw(sb, text, xPos, yPos);
		font.setColor(Color.WHITE);
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() 
	{	
		Gdx.app.debug("resume", "entering Resume");
		
		if(this.timer != null)
		{
			this.timer.cancel();
			this.timer.purge();
		}
		
	}

	@Override
	public void show() 
	{
		Gdx.app.debug("show", "entering show");
		
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
			this.gameOver = true;
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
	
//	public void clearSelectedCards()
//	{
//		for(Player p : this.game.players.values())
//		{
//			p.selectedCard = null;
//		}
//	}
	
	private boolean haveAllNonJudgesSelectedACard()
	{
		
		for(Player p : this.game.players.values())
		{
			if(!p.isJudge && p.dealtIn)
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
		Gdx.app.debug("GameScreen", "populateHearbeats");
		
		this.game.mcp.hearbeatResponses.clear();
		for(Player p : this.game.players.values())
		{
			JSONObject obj = new JSONObject();
		
			if(!p.isJudge)
			{
				obj.put("cards", p.cardsToJsonArray());
				
				if(p.selectedCard != null)
				{
					obj.put("selectedCard", p.selectedCard);
				}
				
				if(!p.dealtIn)
				{
					obj.put("notDealtIn", "waiting");
				}
			}
			else
			{
				if(currentState == State.playersChooseCard)
				{
					obj.put("judge", "wait");
				}
				else if(currentState == State.judgeChoosesAnswer)
				{
					if(selectedWhiteCards == null)
					{
						selectedWhiteCards = generateSelectedWhiteCards();
					}
					obj.put("judge", "vote");
					obj.put("selected", selectedWhiteCards);
				}
			}
			
			obj.put("blackCard", this.blackCard);
			
			if(currentState == State.endOfRound)
			{
				obj.put("winningCard", GameScreen.lastWiningWhiteCard);
				obj.put("score", p.score);
				Array<JSONObject> arr = new Array<JSONObject>();
				for (Player p2 : this.game.players.values()) {
					JSONObject obj2 = new JSONObject();
					obj2.put("name", p2.name);
					obj2.put("score", p2.score);
					arr.add(obj2);
				}
				obj.put("scores", arr);
				obj.put("playerCount", this.game.players.size());
			}

			this.game.mcp.hearbeatResponses.put(p.id, obj);
		}
	}
	
	private JSONArray selectedWhiteCards = null;
	
	public JSONArray generateSelectedWhiteCards()
	{
		JSONArray array = new JSONArray();
		
		//Populate answers from players
		for(Player NewP : this.game.players.values())
		{			
			if(!NewP.isJudge && NewP.dealtIn)
			{
				JSONObject innerObj = new JSONObject();
				innerObj.put("playerID", NewP.id);
				innerObj.put("selectedCard", NewP.selectedCard);
				array.add(innerObj);
			}	
		}
		
		Collections.shuffle(array);
		
		return array;
	}
	

}

