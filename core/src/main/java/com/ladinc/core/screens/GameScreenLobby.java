package com.ladinc.core.screens;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ladinc.core.McpCah;
import com.ladinc.core.objects.Player;
import com.ladinc.core.screens.GameScreen.State;

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
    
    private static final String TITLE = "Morals Are Optional";
    
    private Sprite bg;
    
    private Stage stage;
	
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
		//stage = new Stage(new ScreenViewport());
	    Gdx.input.setInputProcessor(stage);
	    
	    spriteBatch = (SpriteBatch) stage.getBatch();


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
		
		table = createMainTable();
		
		//table.debug();
		
		table.setFillParent(true);
	    stage.addActor(table);
		
		stage.act(delta);
		
		spriteBatch.begin();
		this.bg.draw(spriteBatch);
		spriteBatch.end();
	    stage.draw();
	    
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
	
	private Table createMainTable()
	{
		Table mainTable = new Table();
		
		mainTable.setPosition(0, 70f);
		
		mainTable.setWidth(screenWidth);
		mainTable.add(createTitleTable()).colspan(2).padBottom(50f);;
		mainTable.row();
		mainTable.add(createStepTable()).padBottom(60f).width(Value.percentWidth(0.75f, mainTable));
		mainTable.add(createConnectedPlayersTable()).align(Align.top).expandX();
		mainTable.row();

		String text;
		
		if(this.game.players.size() < 3)
		{
			text = "Waiting for more players to connect";
		}
		else
		{
			text = "Ready to start";
		}
		mainTable.add(new Label(text, new Label.LabelStyle(font, Color.WHITE))).colspan(2).padTop(70f);
		
		return mainTable;
	}
	
	private Table createStepTable()
	{
		Table stepTable = new Table();
		
		stepTable.add(new Label("To Join The Game:", new Label.LabelStyle(font, Color.ORANGE))).colspan(2).padBottom(60f);
		stepTable.row();

		stepTable.add(createStep1Table()).align(Align.top).width(Value.percentWidth(0.45f, stepTable));

		stepTable.add(createStep2Table()).align(Align.top| Align.right).width(Value.percentWidth(0.55f, stepTable));
		
		return stepTable;
	}
	
	private Table createTitleTable()
	{
		Table titleTable = new Table();
		
		titleTable = new Table();
		titleTable.add(new Label("Morals Are Optional", new Label.LabelStyle(boldTitleFont, Color.WHITE)));
		titleTable.row();
		titleTable.add(new Label("A digital rip-off of a popular card game!", new Label.LabelStyle(smallFont, Color.WHITE)));
		
		return titleTable;
	}
	
	private Table createStep1Table()
	{
		Table stepOne = new Table();
		
		stepOne.add(new Label("Step One:", new Label.LabelStyle(boldFont, Color.GREEN))).padBottom(20f);
		stepOne.row();
		
		Label step1Message = new Label("Connect your phone to the Wi-Fi Network.", new Label.LabelStyle(font, Color.WHITE));
		step1Message.setAlignment(Align.center | Align.top);
		step1Message.setWrap(true);
		step1Message.setWidth(500f);
		
		stepOne.add(step1Message).width(500f);
		
		return stepOne;
	}
	
	private Table createStep2Table()
	{
		Table stepTwo = new Table();
		stepTwo.add(new Label("Step Two:", new Label.LabelStyle(boldFont, Color.GREEN))).padBottom(20f);
		stepTwo.row();
		
		Label step2Message = new Label("Type the following into your phones web browser:", new Label.LabelStyle(font, Color.WHITE));
		step2Message.setWrap(true);
		step2Message.setAlignment(Align.center | Align.top);
		stepTwo.add(step2Message).width(700f);
		stepTwo.row();
		
		stepTwo.add(new Label(this.game.ipAddr, new Label.LabelStyle(titleFont, Color.YELLOW)));
		
		if(this.game.usingMCPRocks)
		{
			stepTwo.row();
		
			Label step2Warning = new Label("Tip: Click here to reveal the IP address", new Label.LabelStyle(smallFont, Color.WHITE));
			step2Warning.setWrap(true);
			step2Warning.setAlignment(Align.center | Align.top);
			stepTwo.add(step2Warning).width(600f);
		}
		
		stepTwo.setBounds(stepTwo.getX(), stepTwo.getY(), stepTwo.getWidth(), stepTwo.getHeight());
		
		stepTwo.addListener(new InputListener() 
		{
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        game.toggleMCPAddressType();
		        return true;
		    }
		});
		
		return stepTwo;
		
	}
	
	private Table createConnectedPlayersTable()
	{
		Table connectedPlayers = new Table();
		
		connectedPlayers.add(new Label("Connected Players:", new Label.LabelStyle(font, Color.ORANGE))).padBottom(20f).colspan(2);
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
		JSONObject obj = new JSONObject();
		obj.put("numberOfPlayers", this.game.players.size());
		for(Player p : this.game.players.values()){
			this.game.mcp.hearbeatResponses.put(p.id,obj);
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
		
		obj.put("address", this.game.ipAddr);
		
		this.game.mcp.hearbeatResponses.put("table",obj);
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
			}, 100, 100);
		
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
	public void show() {
		spriteBatch = new SpriteBatch();
	
		
	}
}

