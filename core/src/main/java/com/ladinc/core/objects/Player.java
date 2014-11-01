package com.ladinc.core.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ladinc.core.McpCah;

public class Player 
{
	public static final int CARDS_IN_FULL_HAND = 7;
	
	public String name;
	public String id;
	
	public boolean isJudge = false;
	
	public List<String> hand = new ArrayList<String>();
	
	public String selectedCard = null;
	
	public void populateHand()
	{
		while(hand.size() < CARDS_IN_FULL_HAND)
		{
			if(McpCah.AVAILABLE_WHITE_CARDS.size() > 0)
			{
				Random r = new Random();
				int i = r.nextInt(McpCah.AVAILABLE_WHITE_CARDS.size());
				
				//Add random card to players hand
				hand.add(McpCah.AVAILABLE_WHITE_CARDS.get(i));
				
				//Remove card from available
				McpCah.AVAILABLE_WHITE_CARDS.remove(i);
				
			}
			else
			{
				//Out of cards, game over
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
