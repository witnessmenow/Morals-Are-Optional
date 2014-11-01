package com.ladinc.core.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;

import com.ladinc.core.McpCah;

public class Player 
{
	public static final int CARDS_IN_FULL_HAND = 7;
	
	public String name;
	public String id;
	public int score = 0;
	
	public boolean isJudge = false;
	
	public List<String> hand = new ArrayList<String>();
	
	public String selectedCard = null;
	
	public static Boolean OUT_OF_WHITE_CARDS = false;
	
	public Player(String name, String id){
		this.name = name;
		this.id = id;
	}
	public JSONArray cardsToJsonArray()
	{
		JSONArray cards = new JSONArray();
		for(String s: hand)
		{
			cards.add(s);
		}
		
		return cards;
	}
	
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
				OUT_OF_WHITE_CARDS = true;
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
