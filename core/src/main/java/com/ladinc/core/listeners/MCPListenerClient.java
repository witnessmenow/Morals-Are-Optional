package com.ladinc.core.listeners;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.ladinc.core.McpCah;
import com.ladinc.core.cards.CardCollection;
import com.ladinc.core.cards.CardParser;
import com.ladinc.core.cards.SimpleWhiteCard;
import com.ladinc.core.objects.Player;
import com.ladinc.core.objects.Rando;
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
				Gdx.app.log("MCPListenerClient", "pass: " + params.get("event"));
				
				if(params.get("event").equals("register"))
				{
					registerPlayer(params.get("id"), params.get("name"));
				}
				else if(params.get("event").equals("registerRando"))
				{
					registerRando();
				}
				else if(params.get("event").contains("start"))
				{
					Gdx.app.debug("MCPListenerClient", "Got Event to start Game");
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
					winnerSelected(params.get("card"));
				}
				else if(params.get("event").contains("nextRound"))
				{
					GameScreen.startNextFlag = true;
				}
			}
		}
		
	}
	
	private void winnerSelected(String card)
	{
		int winningCard = Integer.parseInt(card);
		String winnerId = null;
		for(Player p: this.game.players.values())
		{
			
			if (p.selectedCard != null && p.selectedCard.id == winningCard)
			{
				p.score++;
				winnerId = p.id;
			}
			
			GameScreen.lastWiningWhiteCard = new SimpleWhiteCard(CardParser.masterCards.get(winningCard));
			GameScreen.lastWiningId = winnerId;
		}
	}
	
	private void revealCard(String card)
	{
		GameScreen.lastRevealedWhiteCard = new SimpleWhiteCard(CardParser.masterCards.get(Integer.parseInt(card)));
	}
	
	private void cardSelected(String id, String card)
	{
		if(this.game.players.containsKey(id))
		{
			Player p = this.game.players.get(id);
			p.selectCardAction(Integer.parseInt(card));
		}
	}
	
	
	private void registerPlayer(String controllerId, String name)
	{
		if(!this.game.players.containsKey(controllerId))
		{
			this.game.players.put(controllerId, new Player(name, controllerId));
		}
		
	}
	
	private void registerRando(){
		this.game.players.put("rando", new Rando());
	}

}
