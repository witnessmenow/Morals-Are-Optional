package com.ladinc.core.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ladinc.core.McpCah;
import com.ladinc.core.cards.SimpleWhiteCard;

public class Player 
{
	public static final int CARDS_IN_FULL_HAND = 5;
	
	public String name;
	public String id;
	public int score = 0;
	
	public boolean isJudge = false;
	
	public List<SimpleWhiteCard> hand = new ArrayList<SimpleWhiteCard>();
	
	public SimpleWhiteCard selectedCard = null;
	
	public boolean dealtIn = false;
	public boolean isPaused = false;
	
	public boolean isRando = false;
	
	public static Boolean OUT_OF_WHITE_CARDS = false;
	
	public Player(String name, String id){
		this.name = name;
		this.id = id;
	}
	
	public void selectCardAction(int cardId)
	{
		for(SimpleWhiteCard s: hand)
		{
			if(s.id == cardId)
			{
				selectedCard = s;
				return;
			}
		}
		
		//If we get here the user selected a card they do not have in their hand?
	}
	
	public JSONArray cardsToJsonArray()
	{
		JSONArray cards = new JSONArray();
		JSONObject obj;
		for(SimpleWhiteCard s: hand)
		{
			cards.add(s.getJsonObj());
		}
		
		return cards;
	}
	
	public void populateHand()
	{
		if(selectedCard != null && hand.contains(selectedCard))
		{
			hand.remove(selectedCard);
			this.selectedCard = null;
		}
		
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
				
				dealtIn = true;
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
