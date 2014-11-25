package com.ladinc.core;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ladinc.core.cards.CardParser;
import com.ladinc.core.listeners.MCPListenerClient;
import com.ladinc.core.objects.Player;
import com.ladinc.core.screens.GameScreen;
import com.ladinc.core.screens.GameScreenLobby;
import com.ladinc.mcp.CustomResource;
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
	
	public Map<String, Player> players = new HashMap<String, Player>();
	
	public Sprite backgorund;
	
	public boolean startGame = false; 
	@Override
	public void create() 
	{
		Gdx.input.setCatchBackKey(true);
		
		//Assets.load();
		CardParser.loadCards();
		
		//Create MCP, try use port 8888
		this.mcp = MCP.tryCreateAndStartMCPWithPort(8888);
		
		//Users IP addresses used as IDS
		MCP.USE_IP_ADDRESS_AS_ID = true;
		
		//Add a listener so the game can recieve events
		mcpListener = new MCPListenerClient(this);
		this.mcp.addMCPListener(mcpListener);
		
		//Set Debug logging to false
		MCP.SHOW_DEBUG_LOGGING = false;
		
        ipAddr = mcp.getAddressForClients();
        if(ipAddr.equals(":8888"))
        {
        	ipAddr = "No Network";
        }
        
        Gdx.app.error("Main-MCP", "Connection Address: " + ipAddr);
		
        if(Gdx.app.getType() == ApplicationType.Android)
        {
        	MCP.FILE_LOCATION_PREFIX = "/assets/";
        }
		
		//All files that are added to the resources folder must be added to this Array List
//		this.mcp.customLinks = new ArrayList<String>();
//		this.mcp.customLinks.add("moralsAreOptional.html");
//		this.mcp.customLinks.add("jquery-1.11.1.min.js");
//		this.mcp.customLinks.add("bootstrap.min.js");
//		this.mcp.customLinks.add("bootstrap.min.css");
//		this.mcp.customLinks.add("bootstrap-theme.min.css");
//		
        this.mcp.customLinkDirect = new ArrayList<CustomResource>();
		this.mcp.customLinkDirect.add(new CustomResource("moralsAreOptional.html", getFileContents("moralsAreOptional.html")));
		this.mcp.customLinkDirect.add(new CustomResource("jquery-1.11.1.min.js", getFileContents("jquery-1.11.1.min.js")));
		this.mcp.customLinkDirect.add(new CustomResource("bootstrap.min.js", getFileContents("bootstrap.min.js")));
		this.mcp.customLinkDirect.add(new CustomResource("bootstrap.min.css", getFileContents("bootstrap.min.css")));
		this.mcp.customLinkDirect.add(new CustomResource("bootstrap-theme.min.css", getFileContents("bootstrap-theme.min.css")));
		
		//This controls which controllers are visible in the initial MCP drop down
		this.mcp.redirectOptions = new ArrayList<RedirectOption>(); //This clears the defaults
		this.mcp.redirectOptions.add(new RedirectOption("moralsAreOptional.html", "MCP CAH"));
		
		//mockPlayers();
		
		this.backgorund = new Sprite(new Texture(Gdx.files.internal("background.jpg")));
		
		this.setScreen(new GameScreenLobby(this));
		//this.setScreen(new GameScreen(this));
		
	}
	
	//TODO this is awful there must be a better way!
	private String getFileContents(String fileName)
	{
		InputStream is = Gdx.files.internal(fileName).read();
		Scanner filesScanner = null;
		try {
			filesScanner = new Scanner( is );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fileContents = filesScanner.useDelimiter("\\A").next();
		filesScanner.close();
		return fileContents;
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
