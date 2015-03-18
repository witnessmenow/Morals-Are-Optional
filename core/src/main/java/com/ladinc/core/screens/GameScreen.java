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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ladinc.core.McpCah;
import com.ladinc.core.cards.Card;
import com.ladinc.core.cards.SimpleWhiteCard;
import com.ladinc.core.listeners.CustomInputListener;
import com.ladinc.core.objects.Player;

public class GameScreen implements Screen
{	
	public static enum State {
		startOfRound, endOfRound, playersChooseCard, judgeChoosesAnswer, gameOver, needMorePlayers
	};
	
	public static SimpleWhiteCard lastWiningWhiteCard = null;
	public static String lastWiningId = null;
	public static SimpleWhiteCard lastRevealedWhiteCard = null;
	
	public static Boolean startNextFlag = false;
	
	private String blackCard;
	private String filteredBlackCard;
	
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
	public BitmapFont smallFont;
	
	public Label blackCardLabel;
	
	public Sprite largeblackCardSprite;
	public Sprite largeWhiteCardSprite;
	
	public Sprite blackCardSprite;
	
	public Label whiteCardLabel;
	
	public Sprite whiteCardSprite;
	
	public Sprite tickSprite;
	public Sprite judgeSprite;
	public Sprite timerSprite;
	public Sprite pausedSprite;
	public Sprite deleteSprite;
	public Sprite plusSprite;
	
	public TextureRegionDrawable buttomPressedDrawable;
	public TextureRegionDrawable buttomDrawable;
	
	private boolean playerEditMode = false;
	
	public static boolean CHANGES_TO_PLAYERS = false;
	
	Timer timer = null;
	
	private Sprite bg;
	
	public Table table;
	private Stage stage;
	
	public GameScreen(McpCah g)
	{
		Gdx.app.debug("GameScreen", "Start of constructor");
		
		this.game = g;
		initializeFont();
		this.screenWidth = 1920;
		this.screenHeight = 1080;
		
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.screenWidth, screenHeight);
		
		tickSprite = new Sprite(new Texture(Gdx.files.internal("icons/checkmark.png")));
		
		tickSprite.setColor(Color.WHITE);
		
		judgeSprite = new Sprite(new Texture(Gdx.files.internal("icons/medal2.png")));
		judgeSprite.setColor(Color.WHITE);
		
		timerSprite = new Sprite(new Texture(Gdx.files.internal("icons/exitRight.png")));
		timerSprite.setColor(Color.WHITE);
		
		pausedSprite = new Sprite(new Texture(Gdx.files.internal("icons/pause.png")));
		pausedSprite.setColor(Color.WHITE);
		
		deleteSprite = new Sprite(new Texture(Gdx.files.internal("icons/cross.png")));
		deleteSprite.setColor(Color.WHITE);
		
		plusSprite = new Sprite(new Texture(Gdx.files.internal("icons/plus.png")));
		plusSprite.setColor(Color.WHITE);
		
		blackCardSprite = new Sprite(new Texture(Gdx.files.internal("blankCard.png")));
		blackCardSprite.setColor(Color.BLACK);
		
		whiteCardSprite = new Sprite(new Texture(Gdx.files.internal("blankCard.png")));
		whiteCardSprite.setColor(Color.WHITE);
		
		largeblackCardSprite = new Sprite(new Texture(Gdx.files.internal("LargeBlackBlankCard.png")));
		largeWhiteCardSprite = new Sprite(new Texture(Gdx.files.internal("LargeBlankCard.png")));
		//largeblackCardSprite.setColor(Color.WHITE);
		
		blackCardLabel = new Label("", new Label.LabelStyle(labelFont, Color.WHITE));
		
		whiteCardLabel = new Label("", new Label.LabelStyle(labelFont, Color.BLACK));
		
		buttomPressedDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/pressed.png"))));
		buttomDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/raised.png"))));
			
		this.bg = this.game.backgorund;
		
		stage = new Stage(new ExtendViewport(screenWidth, screenHeight));
		//stage = new Stage(new ScreenViewport());
	    //Gdx.input.setInputProcessor(stage);
	    
	    this.spriteBatch = (SpriteBatch) stage.getBatch();
		
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
    	
    	smallFont = new BitmapFont(Gdx.files.internal("fonts/Swis-721-32.fnt"), Gdx.files.internal("fonts/Swis-721-32.png"), false);
    	//Make text black
    	smallFont.setColor(Color.WHITE);
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
			}, McpCah.BACKGROUND_REFRESH_TIME, McpCah.BACKGROUND_REFRESH_TIME);
		
	}

	private boolean buttonJustPressCoolDown = false;
	
	@Override
	public void render(float delta) 
	{
		Gdx.app.debug("GameScreen", "game loop");
		
		handleGameState();
		
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		
		populateHearbeats();
		
		
		if(table != null)
		{
			table.remove();
		}
		
		table = createMainTable();
		
		//table.debug();
		
		table.setFillParent(true);
	    stage.addActor(table);
		
		stage.act(delta);
		
		spriteBatch.begin();
		this.bg.draw(spriteBatch);
		spriteBatch.end();
	    stage.draw();
	    
		//drawSprites();
		
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
	
	private Table createMainTable()
	{
		Table mainTable = new Table();
		
		//mainTable.setPosition(0, 0f);
		
		//mainTable.setWidth(screenWidth);
		mainTable.add().align(Align.center | Align.top).padTop(50f).padBottom(70f);
		mainTable.add(new Label("Round " + roundNumber, new Label.LabelStyle(font, Color.ORANGE))).align(Align.center | Align.top).padTop(50f).padBottom(70f);
		mainTable.add(new Label("New players: " + this.game.ipAddr, new Label.LabelStyle(smallFont, Color.YELLOW))).align(Align.right | Align.bottom).padRight(100f).padTop(50f).padBottom(70f);
		mainTable.row();
		mainTable.add(createCardTable()).padBottom(60f).colspan(2).width(Value.percentWidth(0.7f, mainTable)).expandY().align(Align.top);
		mainTable.add(createConnectedPlayersTable()).align(Align.top | Align.left).expandX();
		mainTable.row();

		String text = "";
		
		if(this.game.players.size() < 3)
		{
			text = "Waiting for players to connect ( " + (3 - this.game.players.size()) + " more needed )";
		}
		else
		{
			if(this.currentState == State.playersChooseCard)
			{
				text = "Waiting for players answers";
			}
			else if(currentState == State.judgeChoosesAnswer)
			{
				text = "Judge is revealing cards";
			}
			else if(currentState == State.endOfRound)
			{
				text = "Winner: " + this.game.players.get(lastWiningId).name;
			}
			else if(currentState == State.needMorePlayers)
			{
				text = "Need more active players to continue!";
			}
			else if(currentState == State.gameOver)
			{
				text = "Game Over!";
			}
		}
		mainTable.add(new Label(text, new Label.LabelStyle(font, Color.WHITE))).colspan(3).padBottom(50f);
		
		return mainTable;
	}
	
	private Table createCardTable()
	{
		Table cardTable = new Table();
		cardTable.add(createBlackCard()).padLeft(100f);
		if(lastRevealedWhiteCard != null || lastWiningWhiteCard != null)
		{
			cardTable.add(createWhiteCard()).expandX();
		}
		else
		{
			cardTable.add().expandX();
		}
		
		//cardTable.debug();
		
		return cardTable;
		
	}
	
	private Actor createBlackCard()
	{
		Table blackCardTable = new Table();
		Image card = new Image(largeblackCardSprite.getTexture());
		blackCardTable.background(card.getDrawable());
		
		blackCardLabel = new Label("", new Label.LabelStyle(font, Color.WHITE));
		this.blackCardLabel.setText(filteredBlackCard);
		this.blackCardLabel.setWrap(true);
		
		blackCardTable.add(this.blackCardLabel).align(Align.left | Align.top).width(card.getWidth() - 20f).pad(10f).expandY();
		
		return blackCardTable;
		
	}
	
	private Actor createWhiteCard()
	{
		Table whiteCardTable = new Table();
		
		Image card = new Image(largeWhiteCardSprite.getTexture());
		whiteCardTable.background(card.getDrawable());
		
		whiteCardLabel = new Label("", new Label.LabelStyle(font, Color.BLACK));
		if(this.currentState == State.judgeChoosesAnswer)
		{
			this.whiteCardLabel.setText(lastRevealedWhiteCard.filteredText);
		}
		else
		{
			this.whiteCardLabel.setText(lastWiningWhiteCard.filteredText);
		}

		this.whiteCardLabel.setWrap(true);
		
		whiteCardTable.add(this.whiteCardLabel).align(Align.left | Align.top).width(card.getWidth() - 20f).pad(10f).expandY();
		
		return whiteCardTable;
		
	}
	
	private Table createConnectedPlayersTable()
	{
		Table playerList = new Table();
		int i = 0;
		for(Map.Entry<String, Player> entry : this.game.players.entrySet())
		{	
			if(i != 0)
			{
				playerList.row();
			}
			
			i++;
			
			Image image = null;
			if(playerEditMode)
			{
				if(!entry.getValue().isPaused)
				{
					image = new Image(deleteSprite.getTexture());
				}
				else
				{
					image = new Image(plusSprite.getTexture());
				}
				
				image.setBounds(image.getX(), image.getY(), image.getWidth(), image.getHeight());
				
				image.addListener(new CustomInputListener(entry.getValue()));
				
			}
			else
			{
				if(entry.getValue().isJudge)
				{
					image = new Image(judgeSprite.getTexture());
				}
				else if(entry.getValue().selectedCard != null)
				{
					image = new Image(tickSprite.getTexture());
				}
				else if(!entry.getValue().dealtIn)
				{
					image = new Image(timerSprite.getTexture());
				}
				else if(entry.getValue().isPaused)
				{
					image = new Image(pausedSprite.getTexture());
				}
				
			}
			
			if(image != null)
			{
				playerList.add(image).spaceRight(20f).align(Align.left);
			}
			else
			{
				playerList.add().height(50f);
			}
			
			String playerText = entry.getValue().getName();
			String playerScore = String.valueOf(entry.getValue().score);
			
			Color color = Color.WHITE;
			
			
			if(entry.getValue().isPaused)
			{
				color = Color.GRAY;
			}
			
			Label name = new Label(playerText, new Label.LabelStyle(smallFont, color));
			
			name.setAlignment(Align.left);
			name.setWrap(true);
			playerList.add(name).width(360f);
			playerList.add(new Label(playerScore, new Label.LabelStyle(smallFont, color))).align(Align.right).padLeft(20f);
			
		}
		
		TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
        style.up = buttomDrawable;
        style.down = buttomPressedDrawable;
        style.font = smallFont;
        style.fontColor = Color.GRAY;
        
        String text = "";
        if(playerEditMode)
        {
        	text = "Done";
        }
        else
        {
        	text = "Remove Player";
        }
        
        TextButton button = new TextButton(text, style);
		
		button.setBounds(button.getX(), button.getY(), button.getWidth(), button.getHeight());
		
		button.addListener(new InputListener() 
		{
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		    {
		    	playerEditMode = !playerEditMode;
		        return true;
		    }
		});
		
		button.padLeft(8f);
		button.padRight(8f);
		button.padTop(5f);
		button.padBottom(5f);
		
		playerList.row();
		playerList.add(button).colspan(3).padTop(50f);
		
		
		return playerList;
	}
	
	private boolean checkForEnoughPlayers()
	{
		int i = 0;
		for(Player p : this.game.players.values())
		{
			if(!p.isPaused)
			{
				i++;
			}
		}
		
		return (i >= 3);
	}
	
	private void handleGameState()
	{
		Gdx.app.debug("GameScreen", "handleGameState");
		
		if(!this.playerEditMode && GameScreen.CHANGES_TO_PLAYERS)
		{
			GameScreen.CHANGES_TO_PLAYERS = false;
			//Starting a new round as we may not have a judge or enough players
			currentState = State.startOfRound;
			
		}
		
		if(currentState == State.startOfRound)
		{
			Gdx.app.debug("GameScreen", "State.startOfRound");
			
			if(!checkForEnoughPlayers())
			{
				currentState = State.needMorePlayers;
			}
			else
			{
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
			
		}
		
		if(currentState == State.needMorePlayers)
		{
			if(!checkForEnoughPlayers())
			{
				currentState = State.needMorePlayers;
			}
			else
			{
				if(!this.playerEditMode)
				{
					this.currentState = State.startOfRound;
				}
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

	@Override
	public void resize(int width, int height) 
	{
		stage.getViewport().update(width, height, true);
		
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
		Gdx.input.setInputProcessor(stage);
		
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
		
		
		//we will add to this shortly
		int indexOfNextJudge = indexOfCurrentJudge;
		Player newJudge = null;
		
		while(newJudge == null)
		{
			indexOfNextJudge++;
			if(indexOfNextJudge >= game.players.size())
			{
				indexOfNextJudge = 0;
			}
			
			i = 0;
			for(Player p : this.game.players.values())
			{
				
				if(i == indexOfNextJudge)
				{
					if(!p.isPaused && !p.isRando)
					{
						//this player is paused or is rando and cant be the judge
						p.isJudge = true;
						newJudge = p;
					}
					break;
				}
				i++;
			}
			
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
			filteredBlackCard = Card.filterText(blackCard);
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
			if(!p.isJudge && p.dealtIn && !p.isPaused)
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
		
			//rando doesnt need hearbeats
			if(!p.isRando)
			{
				if(!p.isJudge)
				{
					
					obj.put("cards", p.cardsToJsonArray());
					
					if(p.selectedCard != null)
					{
						obj.put("selectedCard", p.selectedCard.getJsonObj());
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
					obj.put("winningCard", GameScreen.lastWiningWhiteCard.getJsonObj());
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
		
		populateTableHeartbeat();
	}
	
	@SuppressWarnings("unchecked")
	private void populateTableHeartbeat()
	{
		JSONObject obj = new JSONObject();
		
		obj.put("blackCard", this.blackCard);
		
		
		if(lastRevealedWhiteCard != null || lastWiningWhiteCard != null)
		{
			if(this.currentState == State.judgeChoosesAnswer)
			{
				obj.put("whiteCard", lastRevealedWhiteCard.getJsonObj());
				obj.put("gameScreenMessage", "Judge is choosing");
			}
			else
			{
				obj.put("whiteCard", lastWiningWhiteCard.getJsonObj());
				obj.put("gameScreenMessage", "Winner - " + this.game.players.get(lastWiningId).name);
			}
		}
		else
		{
			if(this.currentState == State.playersChooseCard)
			{
				obj.put("gameScreenMessage", "Waiting for players submissions");
			}
			else if(this.currentState == State.judgeChoosesAnswer)
			{
				obj.put("gameScreenMessage", "Judge is choosing");
			}
		}
		
		Array<JSONObject> arr = new Array<JSONObject>();
		for (Player p2 : this.game.players.values()) {
			JSONObject obj2 = new JSONObject();
			if(p2.isJudge)
			{
				obj2.put("name", "(J) " + p2.name);
			}
			else
			{
				obj2.put("name", p2.name);
			}
			obj2.put("score", p2.score);
			arr.add(obj2);
		}
		obj.put("scores", arr);
		obj.put("playerCount", this.game.players.size());
		
		this.game.mcp.hearbeatResponses.put("table", obj);
	}
	
	private JSONArray selectedWhiteCards = null;
	
	public JSONArray generateSelectedWhiteCards()
	{
		JSONArray array = new JSONArray();
		
		//Populate answers from players
		for(Player NewP : this.game.players.values())
		{			
			if(!NewP.isJudge && NewP.dealtIn && !NewP.isPaused)
			{
				JSONObject innerObj = new JSONObject();
				innerObj.put("playerID", NewP.id);
				innerObj.put("selectedCard", NewP.selectedCard.getJsonObj());
				array.add(innerObj);
			}	
		}
		
		Collections.shuffle(array);
		
		return array;
	}

}

