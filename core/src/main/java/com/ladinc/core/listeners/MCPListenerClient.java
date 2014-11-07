package com.ladinc.core.listeners;

import java.util.Map;

import com.ladinc.core.McpCah;
import com.ladinc.core.objects.Player;
import com.ladinc.core.screens.GameScreen;
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
				else if(params.get("event").contains("start"))
				{
					this.game.startGame = true;
				}
				else if(params.get("event").contains("cardSelect"))
				{
					cardSelected(params.get("id"), params.get("card"));
				}
				else if(params.get("event").contains("revealCard"))
				{
					revealCard(params.get("card"));
				}
				else if(params.get("event").contains("winnerSelect"))
				{
					winnerSelected(params.get("winnerId"), params.get("card"));
				}
				else if(params.get("event").contains("nextRound"))
				{
					GameScreen.startNextFlag = true;
				}
			}
		}
		
	}
	
	private void winnerSelected(String winnerId, String card)
	{
		if(this.game.players.containsKey(winnerId))
		{
			Player p = this.game.players.get(winnerId);
			p.score++;
			
			GameScreen.lastWiningWhiteCard = card;
		}
	}
	
	private void revealCard(String card)
	{
		GameScreen.lastRevealedWhiteCard = card;
	}
	
	private void cardSelected(String id, String card)
	{
		if(this.game.players.containsKey(id))
		{
			Player p = this.game.players.get(id);
			p.selectCardAction(card);
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
