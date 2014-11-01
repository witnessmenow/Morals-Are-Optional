package com.ladinc.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ladinc.core.cards.CardParser;
import com.ladinc.core.listeners.MCPListenerClient;
import com.ladinc.core.objects.Player;
import com.ladinc.core.screens.GameScreen;
import com.ladinc.mcp.MCP;
import com.ladinc.mcp.RedirectOption;

public class McpCah extends Game 
{
	public MCP mcp;
	public MCPListenerClient mcpListener;
	
	public String ipAddr;
	
	public static Map<String, String> ALL_WHITE_CARDS = new HashMap<String, String>();
	public static Map<String, String> ALL_BLACK_CARDS = new HashMap<String, String>();
	
	public static List<String> AVAILABLE_WHITE_CARDS = new ArrayList<String>();
	public static List<String> AVAILABLE_BLACK_CARDS = new ArrayList<String>();
	
	public List<Player> PlayersList = new ArrayList<Player>();
	
	@Override
	public void create() 
	{
		//Assets.load();
		CardParser.loadCards();
		
		//Create MCP, try use port 8888
		this.mcp = MCP.tryCreateAndStartMCPWithPort(8888);
		
		//Add a listener so the game can recieve events
		mcpListener = new MCPListenerClient();
		this.mcp.addMCPListener(mcpListener);
		
		//Set Debug logging to false
		MCP.SHOW_DEBUG_LOGGING = false;
		
        ipAddr = mcp.getAddressForClients();
        if(ipAddr.equals(":8888"))
        {
        	ipAddr = "No Network";
        }
        
        Gdx.app.error("Main-MCP", "Connection Address: " + ipAddr);
		
		
		//All files that are added to the resources folder must be added to this Array List
		this.mcp.customLinks = new ArrayList<String>();
		this.mcp.customLinks.add("mergedCahPage.html");
		
		//This controls which controllers are visible in the initial MCP drop down
		this.mcp.redirectOptions = new ArrayList<RedirectOption>(); //This clears the defaults
		this.mcp.redirectOptions.add(new RedirectOption("mergedCahPage.html", "MCP CAH"));
		
		this.setScreen(new GameScreen(this));
		
	}
	
	private void mockPlayers()
	{
		PlayersList.clear();
		
		Player p = new Player();
		p.name = "Brian";
		p.id = "1";
		
		PlayersList.add(p);
		
		p = new Player();
		p.name = "Gary";
		p.id = "2";
		
		PlayersList.add(p);
		
		p = new Player();
		p.name = "Kieran";
		p.id = "3";
		
		PlayersList.add(p);
	}
}
