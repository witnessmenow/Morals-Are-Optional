package com.ladinc.core.listeners;

import java.util.Map;

import com.ladinc.core.McpCah;
import com.ladinc.core.objects.Player;
import com.ladinc.mcp.interfaces.MCPContorllersListener;

public class MCPListenerClient implements  MCPContorllersListener
{
	public McpCah game;
	
	public MCPListenerClient(McpCah game)
	{
		this.game = game;
	}
	
	@Override
	public void analogMove(int arg0, String arg1, float x, float y) 
	{		
		
	}

	@Override
	public void buttonDown(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buttonUp(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void orientation(int arg0, float arg1, float arg2, float arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pass(Map<String, String> header, Map<String, String> params,
			Map<String, String> files) 
	{
		
		//We will be using the pass event
		if(params != null)
		{
			if(params.containsKey("event"))
			{
				if(params.get("event").contains("register"))
				{
					registerPlayer(params.get("id"), params.get("name"));
				}
			}
		}
		
	}
	
	private void registerPlayer(String controllerId, String name)
	{
		if(!this.game.players.containsKey(controllerId))
		{
			this.game.players.put(controllerId, new Player(name, controllerId));
		}
		
	}

}
