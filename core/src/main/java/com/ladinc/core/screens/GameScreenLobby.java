package com.ladinc.core.screens;

import java.util.Map;

import org.json.simple.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    
    private static final String TITLE = "MCP Cards Against Humanity";
	
	public GameScreenLobby(McpCah g)
	{
		this.game = g;

		initializeFont();
		this.screenWidth = 1920;
		this.screenHeight = 1080;
		spriteBatch = new SpriteBatch();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.screenWidth, screenHeight);
		

	}

	private void initializeFont()
	{		
		
    	font = new BitmapFont(Gdx.files.internal("fonts/Swis-721-50.fnt"), Gdx.files.internal("fonts/Swis-721-50.png"), false);
    	//Make text black
    	font.setColor(Color.WHITE);
	}
    
	@Override
	public void render(float delta) {
		
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		drawSprites();
		sendPlayerHeartbeats();
		
	}
	private void drawSprites()
	{
    	Gdx.gl.glClearColor(0, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		DisplayHeadingText(spriteBatch);
		DisplayMCPAddressText(spriteBatch);
		displayPlayers(spriteBatch);
		spriteBatch.end();
	}
	
	private void DisplayHeadingText(SpriteBatch sb)
	{
		float xPos = (this.screenWidth/2) -  font.getBounds(TITLE).width/2;
		float yPos = (this.screenHeight) -  (this.screenHeight/7);
		
		font.draw(sb, TITLE, xPos, yPos);
	}
	
	private void DisplayMCPAddressText(SpriteBatch sb)
	{
		String text = "Connect To: " + this.game.mcp.getAddressForClients();
		
		float xPos = (this.screenWidth/2) - font.getBounds(text).width/2;
		float yPos = (this.screenHeight) -  (this.screenHeight/7)*2;
		
		font.draw(sb, text, xPos, yPos);
	}
	
	private void displayPlayers(SpriteBatch sb)
	{
		float yPos = (this.screenHeight) - (this.screenHeight/7)*3;
		
		String playerName = "";
		
		int i = this.game.players.size();
		
		for(Map.Entry<String, Player> entry : this.game.players.entrySet())
		{		
			playerName = (i) +") " +entry.getValue().getName();
			
			float xPos = (this.screenWidth/2) -  font.getBounds(playerName).width/2;
			float yPosAdjusted = yPos -  (i-1)*font.getBounds(playerName).height*2;
			
			font.draw(sb, playerName, xPos, yPosAdjusted);
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
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
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

