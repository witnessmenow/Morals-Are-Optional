package com.ladinc.core.objects;

import java.util.Random;

import org.json.simple.JSONArray;

import com.ladinc.core.McpCah;

public class Rando extends Player
{
	public static String ID = "rando";

	public Rando() 
	{
		super("Rando Cardrissian", ID);
		
		this.isRando = true;
	}
	
	@Override
	public void populateHand()
	{
		if(McpCah.AVAILABLE_WHITE_CARDS.size() > 0)
		{
			Random r = new Random();
			int i = r.nextInt(McpCah.AVAILABLE_WHITE_CARDS.size());
			
			//Just take a random white card and play it straight away
			selectedCard = McpCah.AVAILABLE_WHITE_CARDS.get(i);
			
			//Remove card from available
			McpCah.AVAILABLE_WHITE_CARDS.remove(i);
			
			dealtIn = true;
		}
		else
		{
			OUT_OF_WHITE_CARDS = true;
		}
	}
	
	@Override
	public JSONArray cardsToJsonArray()
	{
		return null;
	}

}
