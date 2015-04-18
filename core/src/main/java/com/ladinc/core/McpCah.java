package com.ladinc.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ladinc.core.cards.CardParser;
import com.ladinc.core.cards.SimpleWhiteCard;
import com.ladinc.core.controllers.GameControllerManager;
import com.ladinc.core.controllers.listeners.MCPListenerClient;
import com.ladinc.core.objects.Player;
import com.ladinc.core.objects.Rando;
import com.ladinc.core.screens.GameScreen;
import com.ladinc.core.screens.GameScreenLobby;
import com.ladinc.mcp.CustomResource;
import com.ladinc.mcp.MCP;
import com.ladinc.mcp.RedirectOption;

public class McpCah extends Game 
{
	public GameControllerManager gcm;
	
	//How often the game should refresh when running the background
	public static int BACKGROUND_REFRESH_TIME = 10;
	
	public static Map<String, String> ALL_WHITE_CARDS = new HashMap<String, String>();
	public static Map<String, String> ALL_BLACK_CARDS = new HashMap<String, String>();
	
	public static List<SimpleWhiteCard> AVAILABLE_WHITE_CARDS = new ArrayList<SimpleWhiteCard>();
	public static List<String> AVAILABLE_BLACK_CARDS = new ArrayList<String>();
	
	public Map<String, Player> players = new HashMap<String, Player>();
	
	public Sprite backgorund;
	
	public boolean startGame = false; 
	
	public GameScreen gameScreen;
	public GameScreenLobby gameScreenLobby;
	
	@Override
	public void create() 
	{
		Gdx.input.setCatchBackKey(true);
		
		Gdx.app.setLogLevel(Application.LOG_ERROR);
		//Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		//Assets.load();
		CardParser.loadCards();
		
		if(AVAILABLE_WHITE_CARDS.size() == 0 || AVAILABLE_BLACK_CARDS.size() == 0)
		{
			Gdx.app.error("Main-MCP", "Failed to load cards");
			Gdx.app.exit();
		}
		
		gcm = new GameControllerManager(this);
		
		//mockPlayers();
		
		this.backgorund = new Sprite(new Texture(Gdx.files.internal("background.jpg")));
		
		gameScreenLobby = new GameScreenLobby(this);
		gameScreen = new GameScreen(this);
		
		this.setScreen(gameScreenLobby);
		//this.setScreen(new GameScreen(this));
		
	}
	
	public void addRando()
	{
		if(this.players != null)
		{
			if(!this.players.containsKey(Rando.ID))
			{
				this.players.put(Rando.ID, new Rando());
			}
		}
	}
	
	public void remmoveRando()
	{
		if(this.players != null)
		{
			if(this.players.containsKey(Rando.ID))
			{
				this.players.remove(Rando.ID);
			}
		}
	}
		
	private void mockPlayers()
	{
		players.clear();
		
		Player p = new Player("Brian", "1");
		players.put(p.id, p);
		
		p = new Player("Gary", "2");
		players.put(p.id, p);
		
		p = new Player("Kieran", "3");
		players.put(p.id, p);
	}
}
