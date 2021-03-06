package com.ladinc.core.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ladinc.core.McpCah;
import com.ladinc.core.controllers.IControls;
import com.ladinc.core.objects.Player;

public class GameScreenLobby implements Screen
{	
	public static enum State {
		endOfRound, playersChooseCard, judgeChoosesAnswer, gameOver
	};	
	
	public McpCah game;
	private final OrthographicCamera camera;
	private final int screenHeight;
	private final int screenWidth;
	private SpriteBatch spriteBatch;
	
	public State currentState = State.endOfRound;
    
    public BitmapFont font;
    public BitmapFont titleFont;
    public BitmapFont smallFont;
    
    public BitmapFont boldFont;
    public BitmapFont boldTitleFont;
    public BitmapFont boldSmallFont;
    
    public Table table;
    public Table mainCreditsTable;
    
    private static final String TITLE = "Morals Are Optional";
    
    private Sprite bg;
    
    private Stage stage;
    
    //private Table stepTwo;
	public TextureRegionDrawable buttomPressedDrawable;
	public TextureRegionDrawable buttomDrawable;
	
	public TextureRegionDrawable credits;
	public Sprite creditsSprite;
	
	private boolean displayCredits = false;
	
	public GameScreenLobby(McpCah g)
	{
		this.game = g;

		initializeFont();
		this.screenWidth = 1920;
		this.screenHeight = 1080;
		//spriteBatch = new SpriteBatch();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.screenWidth, screenHeight);
		
		this.bg = this.game.backgorund;
		
		stage = new Stage(new ExtendViewport(screenWidth, screenHeight));  
	    spriteBatch = (SpriteBatch) stage.getBatch();
	    
	    buttomPressedDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/pressed.png"))));
		buttomDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/raised.png"))));
		
		credits = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("credits.png"))));
		
		creditsSprite = new Sprite(new Texture(Gdx.files.internal("credits.png")));
	}

	private void initializeFont()
	{		
		
    	font = new BitmapFont(Gdx.files.internal("fonts/Swis-721-50.fnt"), Gdx.files.internal("fonts/Swis-721-50.png"), false);
    	//Make text black
    	font.setColor(Color.WHITE);
    	
    	titleFont = new BitmapFont(Gdx.files.internal("fonts/Swis-721-75.fnt"), Gdx.files.internal("fonts/Swis-721-75.png"), false);
    	//Make text black
    	titleFont.setColor(Color.WHITE);
    	
    	smallFont = new BitmapFont(Gdx.files.internal("fonts/Swis-721-32.fnt"), Gdx.files.internal("fonts/Swis-721-32.png"), false);
    	//Make text black
    	smallFont.setColor(Color.WHITE);
    	
    	boldFont = new BitmapFont(Gdx.files.internal("fonts/Swis-721-50-Bold.fnt"), Gdx.files.internal("fonts/Swis-721-50-Bold.png"), false);
    	//Make text black
    	boldFont.setColor(Color.WHITE);
    	
    	boldTitleFont = new BitmapFont(Gdx.files.internal("fonts/Swis-721-75-Bold.fnt"), Gdx.files.internal("fonts/Swis-721-75-Bold.png"), false);
    	//Make text black
    	boldTitleFont.setColor(Color.WHITE);
    	
    	boldSmallFont = new BitmapFont(Gdx.files.internal("fonts/Swis-721-32-Bold.fnt"), Gdx.files.internal("fonts/Swis-721-32-Bold.png"), false);
    	//Make text black
    	boldSmallFont.setColor(Color.WHITE);
	}
    
	int counter = 0;
	private Timer timer;
	
	@Override
	public void render(float delta) 
	{
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		
		if(table != null)
		{
			table.remove();
		}
		
		if(mainCreditsTable != null)
		{
			mainCreditsTable.remove();
		}
		
		table = createMainTable();
		if(displayCredits)
	    {
			mainCreditsTable = displayCredits();
	    }
		//table.debug();
		
		table.setFillParent(true);
	    stage.addActor(table);
		if(displayCredits)
	    {
			stage.addActor(mainCreditsTable);
	    }
	    
		
		stage.act(delta);
		
		spriteBatch.begin();
		this.bg.draw(spriteBatch);
		spriteBatch.end();
	    stage.draw();
	    
	    handleControllerInput(delta);
	    
		sendPlayerHeartbeats();
		
		if(this.game.startGame)
		{
			Gdx.app.debug("GameScreenLobby", "Start flag is true, moving to new screen");
			this.game.setScreen(game.gameScreen);
		}
		else
		{
			counter ++;
			if(counter > 50)
			{
				Gdx.app.debug("GameScreenLobby", "Start flag is false");
				counter = 0;
			}
		}
		
	}
	
	private float coolDownTime = 0.5f;
	
	private void handleControllerInput(float delta)
	{
		IControls controller = this.game.gcm.commonController;
		
		if(controller != null)
		{
			if(!controller.isCoolDownActive(delta))
			{
				if(controller.getBackStatus())
				{
					displayCredits = false;
					controller.setCoolDown(coolDownTime);
				}
				
				if(controller.getTopFaceButtonStatus())
				{
					game.gcm.toggleMCPAddressType();
					controller.setCoolDown(coolDownTime);
				}
				
				if(controller.getLeftFaceButtonStatus())
				{
					this.displayCredits = true;
					controller.setCoolDown(coolDownTime);
				}
			}
		}
	}
	
	private TextButton closeCreditsButton = null;
	
	private Table displayCredits()
	{
		Table creditsTable = new Table();
		
		Image card = new Image(creditsSprite.getTexture());
		creditsTable.background(card.getDrawable());
		
		//creditsTable.background(credits);
		
		creditsTable.add(new Label("Credits", new Label.LabelStyle(titleFont, Color.ORANGE))).padBottom(20f);
		creditsTable.row();
		creditsTable.add(new Label("Developers", new Label.LabelStyle(smallFont, Color.GREEN)));
		creditsTable.row();
		creditsTable.add(new Label("Brian Lough", new Label.LabelStyle(smallFont, Color.WHITE)));
		creditsTable.row();
		creditsTable.add(new Label("Kieran Nestor", new Label.LabelStyle(smallFont, Color.WHITE)));
		creditsTable.row();
		creditsTable.add(new Label("Gary Cregan", new Label.LabelStyle(smallFont, Color.WHITE)));
		creditsTable.row();
		creditsTable.add(new Label("Paul O'Flaherty", new Label.LabelStyle(smallFont, Color.WHITE)));
		
		creditsTable.row();
		creditsTable.add(new Label("Artwork", new Label.LabelStyle(smallFont, Color.GREEN))).padTop(30f);
		creditsTable.row();
		creditsTable.add(new Label("Buttons & Icons - kenney.nl", new Label.LabelStyle(smallFont, Color.WHITE)));
		creditsTable.row();
		creditsTable.add(new Label("Background (Modified) - laurakerbyson.com", new Label.LabelStyle(smallFont, Color.WHITE)));
		creditsTable.row();
		creditsTable.add(new Label("Written In Libgdx", new Label.LabelStyle(smallFont, Color.WHITE))).padTop(30f);
		creditsTable.row();
		
		creditsTable.add(new Label("Source Code of Game availalbe at:", new Label.LabelStyle(smallFont, Color.WHITE))).padTop(25f);
		creditsTable.row();
		creditsTable.add(new Label("mcp.rocks/mao", new Label.LabelStyle(smallFont, Color.WHITE)));
		creditsTable.row();
		creditsTable.add(new Label("CAH cards used under the Creative Commons License", new Label.LabelStyle(smallFont, Color.WHITE))).padTop(25f);
		creditsTable.row();

		if(closeCreditsButton == null)
		{
		
			TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
	        style.up = buttomDrawable;
	        style.down = buttomPressedDrawable;
	        style.font = smallFont;
	        style.fontColor = Color.GRAY;
	        
	        String buttonText = "Close";
	        
	        if(this.game.gcm.useOptionButtonText)
	        {
	        	buttonText = buttonText + " " + this.game.gcm.backButtonText;
	        }
	
	        closeCreditsButton = new TextButton(buttonText, style);
			
	        closeCreditsButton.setBounds(closeCreditsButton.getX(), closeCreditsButton.getY(), closeCreditsButton.getWidth(), closeCreditsButton.getHeight());
			
	        closeCreditsButton.addListener(new InputListener() 
			{
			    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			    {
			    	displayCredits = false;
			        return true;
			    }
			});
			
	        closeCreditsButton.padLeft(8f);
	        closeCreditsButton.padRight(8f);
	        closeCreditsButton.padTop(5f);
	        closeCreditsButton.padBottom(5f);
		}
		
		creditsTable.add(closeCreditsButton).padTop(30f).padBottom(20f);
		
		creditsTable.setPosition(screenWidth/2 - creditsSprite.getWidth()/2, screenHeight/2 -  creditsSprite.getHeight()/2);
		
		//creditsSprite.setPosition(screenWidth/2 - creditsSprite.getWidth()/2, screenHeight/2 -  creditsSprite.getHeight()/2);
		
//		spriteBatch.begin();
//		creditsSprite.draw(spriteBatch);
//		creditsTable.draw(spriteBatch, 1);
//		spriteBatch.end();
		
		creditsTable.pack();
		
		return creditsTable;
	}
	
	private Table createMainTable()
	{
		Table mainTable = new Table();
		
		//mainTable.debug();
		
		mainTable.setWidth(screenWidth);
		mainTable.add(createTitleTable()).colspan(2).padTop(50f).padBottom(70f).align(Align.top);
		mainTable.row();
		mainTable.add(createStepTable()).padBottom(60f).width(Value.percentWidth(0.75f, mainTable)).expandY().align(Align.top);
		mainTable.add(createConnectedPlayersTable()).align(Align.top).expandX();
		mainTable.row();
		createBottomRow(mainTable);
		
		return mainTable;
	}
	
	private Table createStepTable()
	{
		Table stepTable = new Table();
		
		stepTable.add(new Label("To Join The Game", new Label.LabelStyle(font, Color.ORANGE))).colspan(2).padBottom(60f);
		stepTable.row();

		stepTable.add(createStep1Table()).align(Align.top).width(Value.percentWidth(0.45f, stepTable));

		stepTable.add(createStep2Table()).align(Align.top| Align.right).width(Value.percentWidth(0.55f, stepTable));
		
		return stepTable;
	}
	
	private TextButton creditsButton = null;
	
	private void createBottomRow(Table table)
	{
		Table bottomTable = table;
		String text;
		
		if(creditsButton == null)
		{
			TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
	        style.up = buttomDrawable;
	        style.down = buttomPressedDrawable;
	        style.font = smallFont;
	        style.fontColor = Color.GRAY;
	        
	        String buttonText = "Credits";
	        
	        if(this.game.gcm.useOptionButtonText)
	        {
	        	buttonText = buttonText + " " + this.game.gcm.leftFaceButtonText;
	        }
	
	        creditsButton = new TextButton(buttonText, style);
		
	        creditsButton.setBounds(creditsButton.getX(), creditsButton.getY(), creditsButton.getWidth(), creditsButton.getHeight());
			
	        creditsButton.addListener(new InputListener() 
			{
			    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			    {
			    	displayCredits = true;
			        return true;
			    }
			});
			
	        creditsButton.padLeft(8f);
	        creditsButton.padRight(8f);
	        creditsButton.padTop(5f);
	        creditsButton.padBottom(5f);
		}
		
		
		if(this.game.players.size() < 3)
		{
			text = "Waiting for players to connect ( " + (3 - this.game.players.size()) + " more needed )";
		}
		else
		{
			text = "Ready to start";
		}
		bottomTable.add(new Label(text, new Label.LabelStyle(font, Color.WHITE))).padBottom(70f);
		bottomTable.add(creditsButton).align(Align.top).padTop(10f);
	}
	
	private Table createTitleTable()
	{
		Table titleTable = new Table();
		
		titleTable = new Table();
		titleTable.add(new Label("Morals Are Optional", new Label.LabelStyle(boldTitleFont, Color.WHITE)));
		titleTable.row();
		titleTable.add(new Label("A digital clone of a hilarious party game", new Label.LabelStyle(smallFont, Color.WHITE)));
		
		return titleTable;
	}
	
	private Table createStep1Table()
	{
		Table stepOne = new Table();
		
		stepOne.add(new Label("Step One", new Label.LabelStyle(boldFont, Color.GREEN))).padBottom(20f);
		stepOne.row();
		
		Label step1Message = new Label("Connect your phone to the Wi-Fi Network.", new Label.LabelStyle(font, Color.WHITE));
		step1Message.setAlignment(Align.center | Align.top);
		step1Message.setWrap(true);
		step1Message.setWidth(500f);
		
		stepOne.add(step1Message).width(500f);
		
		stepOne.row();
		
		Label step1Instruction = new Label("Note: All devices must be on the same Wi-Fi network.", new Label.LabelStyle(smallFont, Color.WHITE));
		step1Instruction.setAlignment(Align.center | Align.top);
		step1Instruction.setWrap(true);
		step1Instruction.setWidth(500f);
		
		stepOne.add(step1Instruction).width(500f).padTop(30f);
		
		return stepOne;
	}
	
	private Table createStep2Table()
	{
		Table stepTwo = new Table();
		stepTwo.add(new Label("Step Two", new Label.LabelStyle(boldFont, Color.GREEN))).padBottom(20f);
		stepTwo.row();
		
		Label step2Message = new Label("Type the following into your phones web browser:", new Label.LabelStyle(font, Color.WHITE));
		step2Message.setWrap(true);
		step2Message.setAlignment(Align.center | Align.top);
		stepTwo.add(step2Message).width(700f);
		stepTwo.row();
		
		stepTwo.add(new Label(this.game.gcm.mcpConnectionAddress, new Label.LabelStyle(titleFont, Color.YELLOW)));
		
		if(this.game.gcm.usingMCPRocks)
		{
			stepTwo.row();
		
			Label step2Warning;
			if(this.game.gcm.useOptionButtonText)
			{
				step2Warning = new Label("Tip: Press " + this.game.gcm.topFaceButtonText + " to reveal the IP address", new Label.LabelStyle(smallFont, Color.WHITE));
			}
			else
			{
				step2Warning = new Label("Tip: Click here to reveal the IP address", new Label.LabelStyle(smallFont, Color.WHITE));
			}
			step2Warning.setWrap(true);
			step2Warning.setAlignment(Align.center | Align.top);
			stepTwo.add(step2Warning).width(600f);
		}
		
		stepTwo.setBounds(stepTwo.getX(), stepTwo.getY(), stepTwo.getWidth(), stepTwo.getHeight());
		
		stepTwo.addListener(new InputListener() 
		{
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        game.gcm.toggleMCPAddressType();
		        return true;
		    }
		});
		
		return stepTwo;
		
	}
	
	private Table createConnectedPlayersTable()
	{
		Table connectedPlayers = new Table();
		
		connectedPlayers.add(new Label("Players", new Label.LabelStyle(font, Color.ORANGE))).padBottom(20f).colspan(2);
		connectedPlayers.row();
		
		Table playerList = new Table();
		int i = 0;
		for(Map.Entry<String, Player> entry : this.game.players.entrySet())
		{	
			if(i != 0)
			{
				playerList.row();
			}
			
			i++;
			
			playerList.add(new Label(i + ":", new Label.LabelStyle(smallFont, Color.WHITE))).spaceRight(20f);
			
			String playerText = entry.getValue().getName();
			Label name = new Label(playerText, new Label.LabelStyle(smallFont, Color.WHITE));
			name.setAlignment(Align.left);
			name.setWrap(true);
			playerList.add(name).width(400f);
			
		}
		
		connectedPlayers.add(playerList);
		
		return connectedPlayers;
	}
	
	@SuppressWarnings("unchecked")
	private void sendPlayerHeartbeats()
	{

		List<JSONObject> array = new ArrayList<JSONObject>();
		
		for(Player p : this.game.players.values()){
			JSONObject obj2 = new JSONObject();
			obj2.put("name", p.name);
			array.add(obj2);
		}
		
		for(Player p : this.game.players.values())
		{
			JSONObject obj = new JSONObject();
			obj.put("connectedPlayers", array);
			obj.put("numberOfPlayers", this.game.players.size());
			
			obj.put("playerName", p.name);
			
			this.game.gcm.mcp.hearbeatResponses.put(p.id,obj);
		}
		
		sendTableHeartbeats();
	}
	
	@SuppressWarnings("unchecked")
	private void sendTableHeartbeats()
	{
		JSONObject obj = new JSONObject();
		
		Array<JSONObject> arr = new Array<JSONObject>();
		for (Player p2 : this.game.players.values()) {
			JSONObject obj2 = new JSONObject();
			obj2.put("name", p2.name);
			obj2.put("score", "");
			arr.add(obj2);
		}
		obj.put("scores", arr);
		obj.put("playerCount", this.game.players.size());
		
		obj.put("address", this.game.gcm.mcpConnectionAddress);
		
		this.game.gcm.mcp.hearbeatResponses.put("table",obj);
	}

	@Override
	public void resize(int width, int height) 
	{
		stage.getViewport().update(width, height, true);
		
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
				  Gdx.app.debug("pause", "fake loop");
				  
					sendPlayerHeartbeats();
					
					if(game.startGame)
					{
						Gdx.app.debug("GameScreenLobby - pause", "Start flag is true, moving to new screen");
						
						if(timer != null)
						{
							timer.cancel();
							timer.purge();
						}
						
						game.setScreen(game.gameScreen);
						game.pause();
					}
			  }
			}, McpCah.BACKGROUND_REFRESH_TIME, McpCah.BACKGROUND_REFRESH_TIME);
		
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
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() 
	{
		Gdx.input.setInputProcessor(stage);
	}
}

