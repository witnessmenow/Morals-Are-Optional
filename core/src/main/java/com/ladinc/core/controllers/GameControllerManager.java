package com.ladinc.core.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.ladinc.core.McpCah;
import com.ladinc.core.controllers.listeners.MCPListenerClient;
import com.ladinc.core.controllers.listeners.android.FireTVListener;
import com.ladinc.core.controllers.listeners.android.OuyaListener;
import com.ladinc.core.controllers.listeners.desktop.XboxListener;
import com.ladinc.mcp.CustomResource;
import com.ladinc.mcp.MCP;
import com.ladinc.mcp.RedirectOption;

public class GameControllerManager 
{
	public MCP mcp;
	public MCPListenerClient mcpListener;
	public Boolean usingMCPRocks;
	public String mcpConnectionAddress;
	
	public String topFaceButtonText = "";
	public String leftFaceButtonText = "";
	public String backButtonText = "";
	public boolean useOptionButtonText = false;
	
	public IControls commonController;
	
	public GameControllerManager(McpCah g)
	{
		setUpMCP(g);
		setUpControls(g);
		setUpButtonText();
	}
	
	private void setUpButtonText()
	{
		if(Ouya.isRunningOnOuya())
		{
			topFaceButtonText = "(Y)";
			leftFaceButtonText = "(U)";
			backButtonText = "(A)";
			
			useOptionButtonText = true;
		}
		else if(FireTV.isRunningOnFireTV())
		{
			topFaceButtonText = "(Y)";
			leftFaceButtonText = "(X)";
			backButtonText = "(B)";
			
			useOptionButtonText = true;
		}
	}
	
	private void setUpControls(McpCah g)
	{
		switch (Gdx.app.getType())
		{
			case Desktop:
				Gdx.app.debug("ControllerManager",
						"addControllerToList - Desktop");
				
				Gdx.app.debug("ControllerManager",
						"Added Listener for Windows Xbox Controller");
				
				XboxListener xboxListener = new XboxListener();
				Controllers.addListener(xboxListener);
				
				commonController = xboxListener.controls;
				
				
				break;
			case Android:
				Gdx.app.debug("ControllerManager",
						"addControllerToList - Android");
				if (Ouya.runningOnOuya)
				{
					Gdx.app.debug("ControllerManager",
							"Added Listener for Ouya Controller");
					
					OuyaListener ouyaListener = new OuyaListener();
					Controllers.addListener(ouyaListener);
					
					commonController = ouyaListener.controls;
				}
				else if(FireTV.runningOnFireTV)
				{	
					Gdx.app.debug("ControllerManager",
							"Added Listener for FireTV Controller");
					
					FireTVListener fireTvListener = new FireTVListener();
					Controllers.addListener(fireTvListener);
					
					commonController = fireTvListener.controls;
				}
				break;
			case WebGL:
				Gdx.app.debug("ControllerManager",
						"addControllerToList - WebGL/HTML5");
				break;
			default:
				Gdx.app.error("ControllerManager", "Format not supported");
				break;
		}
	}
	
	public void setUpMCP(McpCah g)
	{
		//Create MCP, try use port 8888
		this.mcp = MCP.tryCreateAndStartMCPWithPort(8888);
		
		mcp.baseMCPRocksURL = "http://mcp.rocks";
		//mcp.baseMCPRocksURL = "http://192.168.1.200/mcp";
		
		try 
		{	
			mcp.registerWithMCPRocks("Morals Are Optional");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Users IP addresses used as IDS
		MCP.USE_IP_ADDRESS_AS_ID = true;
		
		//Add a listener so the game can recieve events
		mcpListener = new MCPListenerClient(g);
		this.mcp.addMCPListener(mcpListener);
		
		//Set Debug logging to false
		MCP.SHOW_DEBUG_LOGGING = false;
		
		mcpConnectionAddress = mcp.getAddressForClients();
        
        if(mcpConnectionAddress.equals(":8888"))
        {
        	mcpConnectionAddress = "No Network";
        }
        
        usingMCPRocks = mcp.mcpRocksVerified;
        
        Gdx.app.log("Main-MCP", "Connection Address: " + mcpConnectionAddress);	
        
        this.mcp.defaultStartPage = "moralsAreOptional.html";
        
        this.mcp.customLinkDirect = new ArrayList<CustomResource>();
		this.mcp.customLinkDirect.add(new CustomResource("moralsAreOptional.html", getFileContents("moralsAreOptional.html")));
		this.mcp.customLinkDirect.add(new CustomResource("jquery-1.11.1.min.js", getFileContents("jquery-1.11.1.min.js")));
		this.mcp.customLinkDirect.add(new CustomResource("bootstrap.min.js", getFileContents("bootstrap.min.js")));
		this.mcp.customLinkDirect.add(new CustomResource("bootstrap.min.css", getFileContents("bootstrap.min.css")));
		this.mcp.customLinkDirect.add(new CustomResource("bootbox.min.js", getFileContents("bootbox.min.js")));
		this.mcp.customLinkDirect.add(new CustomResource("bootstrap-theme.min.css", getFileContents("bootstrap-theme.min.css")));
		this.mcp.customLinkDirect.add(new CustomResource("table.html", getFileContents("table.html")));
		this.mcp.customLinkDirect.add(new CustomResource("heartbeatHelper.js", getFileContents("heartbeatHelper.js")));
		this.mcp.customLinkDirect.add(new CustomResource("judge.js", getFileContents("judge.js")));
		this.mcp.customLinkDirect.add(new CustomResource("pageHelper.js", getFileContents("pageHelper.js")));
		this.mcp.customLinkDirect.add(new CustomResource("serviceCalls.js", getFileContents("serviceCalls.js")));
		this.mcp.customLinkDirect.add(new CustomResource("normalize.css", getFileContents("normalize.css")));
		this.mcp.customLinkDirect.add(new CustomResource("mao.css", getFileContents("mao.css")));
		this.mcp.customLinkDirect.add(new CustomResource("gameFunctions.js", getFileContents("gameFunctions.js")));
	}
	
	public void toggleMCPAddressType()
	{
		if(mcp.mcpRocksVerified)
		{
			if(usingMCPRocks)
			{
				usingMCPRocks = false;
				mcpConnectionAddress = mcp.getIpAddressForClients();
			}
			else
			{
				usingMCPRocks = true;
				mcpConnectionAddress = mcp.getAddressForClients();
				
			}
		}
		else
		{
			usingMCPRocks = false;
			mcpConnectionAddress = mcp.getIpAddressForClients();
		}
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
	
}
