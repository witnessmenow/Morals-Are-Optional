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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    
    private static final String TITLE = "Morals Are Optional";
    
    private Sprite bg;
	
	public GameScreenLobby(McpCah g)
	{
		this.game = g;

		initializeFont();
		this.screenWidth = 1920;
		this.screenHeight = 1080;
		spriteBatch = new SpriteBatch();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.screenWidth, screenHeight);
		
		this.bg = this.game.backgorund;

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
	}
    
	int counter = 0;
	private Timer timer;
	
	@Override
	public void render(float delta) {
		
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		drawSprites();
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
	private void drawSprites()
	{
    	Gdx.gl.glClearColor(0, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		
		this.bg.draw(spriteBatch);
		
		DisplayHeadingText(spriteBatch);
		DisplayMCPAddressText(spriteBatch);
		displayPlayers(spriteBatch);
		spriteBatch.end();
	}
	
	private void DisplayHeadingText(SpriteBatch sb)
	{
		float xPos = (this.screenWidth/2) -  titleFont.getBounds(TITLE).width/2;
		float yPos = (this.screenHeight) -  (this.screenHeight/16);
		
		titleFont.setColor(Color.WHITE);
		titleFont.draw(sb, TITLE, xPos, yPos);
		titleFont.setColor(Color.WHITE);
		
		xPos = (this.screenWidth/2) -  smallFont.getBounds("A digital rip-off of a popular card game!").width/2;
		yPos = yPos - 75f;
		
		smallFont.draw(sb, "A digital rip-off of a popular card game!", xPos, yPos);
	}
	
	private void DisplayMCPAddressText(SpriteBatch sb)
	{
		String text = "Game Address:";
		
		float xPos = (this.screenWidth/3)*2 - font.getBounds(text).width/2;
		float yPos = (this.screenHeight) - 250f;
		
		font.draw(sb, text, xPos, yPos);
		
		xPos = (this.screenWidth/3)*2 - titleFont.getBounds(this.game.mcp.getAddressForClients()).width/2;
		yPos = (this.screenHeight) - (this.screenHeight/7)*2;
		
		titleFont.setColor(Color.YELLOW);
		titleFont.draw(sb, this.game.mcp.getAddressForClients(), xPos, yPos);
		titleFont.setColor(Color.WHITE);
		
		String instructionText = "1: Each player needs a Phone/Tablet/Laptop.";
		
		xPos = (this.screenWidth/3)*2 -  smallFont.getBounds(instructionText).width/2;
		yPos = yPos - 125f;
		
		smallFont.draw(sb, instructionText, xPos, yPos);
		
		instructionText = "2: Using your device's web browser, connect to the game address.";
		
		xPos = (this.screenWidth/3)*2 -  smallFont.getBounds(instructionText).width/2;
		yPos = yPos - 75f;
		
		smallFont.draw(sb, instructionText, xPos, yPos);
		
		instructionText = "3: When 3 or more people have joined, the game can be started.";
		
		xPos = (this.screenWidth/3)*2 -  smallFont.getBounds(instructionText).width/2;
		yPos = yPos - 75f;
		
		smallFont.draw(sb, instructionText, xPos, yPos);
		
		instructionText = "4: Additional players can join at anytime after the game starts.";
		
		xPos = (this.screenWidth/3)*2 -  smallFont.getBounds(instructionText).width/2;
		yPos = yPos - 75f;
		
		smallFont.draw(sb, instructionText, xPos, yPos);
		
		if(this.game.players.size() < 3)
		{
			text = "Waiting for players";
		}
		else
		{
			text = "Ready to start";
		}
		
		xPos = (this.screenWidth/2) - font.getBounds(text).width/2;
		yPos = 250f;
		
		font.draw(sb, text, xPos, yPos);
	}
	
	private void displayPlayers(SpriteBatch sb)
	{

		float yPos = (this.screenHeight) - 250f;
		float xPos = (this.screenWidth/14);
		
		font.draw(sb, "Connected Players", xPos - 40f, yPos);
		
		String playerText = "";
		
		int i = this.game.players.size() + 1;
		
		for(Map.Entry<String, Player> entry : this.game.players.entrySet())
		{		
			playerText = entry.getValue().getName();
			
			float yPosAdjusted = (float) (yPos -  (i-1)*font.getBounds(playerText).height*(1.8));
			
			font.draw(sb, playerText, xPos, yPosAdjusted);
			
			i--;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void sendPlayerHeartbeats()
	{
		JSONObject obj = new JSONObject();
		obj.put("numberOfPlayers", this.game.players.size());
		for(Player p : this.game.players.values()){
			this.game.mcp.hearbeatResponses.put(p.id,obj);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() 
	{
		Gdx.app.error("pause", "entering Pause");
		
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
				  Gdx.app.error("pause", "fake loop");
				  
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
		Gdx.app.error("resume", "entering Resume");
		
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

