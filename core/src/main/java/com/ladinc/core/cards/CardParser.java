package com.ladinc.core.cards;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.ladinc.core.McpCah;

public class CardParser 
{
	
	private static String DEFAULT_CARD_FILE = "cards/cards.json";
	
	public static Map<Integer, Card> masterCards;
	
	public static List<String> selectedPacks;
	
	public static List<String> availalbePacks;
	
	public static void loadCards()
	{
		//DEFAULT_CARD_FILE = "cards/test.json";
		
		readNewCards();
		populateSelectedPacks();
		parseCards();
//		loadWhiteCards();
//		loadBlackCards();
	}
	
	public static void populateSelectedPacks()
	{
		selectedPacks = new ArrayList<String>();
		
		selectedPacks.add("Base");
		selectedPacks.add("CAHe1");
		selectedPacks.add("CAHe2");
		selectedPacks.add("CAHxmas");
		selectedPacks.add("CAHe3");
		selectedPacks.add("CAHe4");
		selectedPacks.add("christmas2013");
		selectedPacks.add("CAHe5");
		selectedPacks.add("90s");
	}
	
	public static void readNewCards()
	{
		masterCards = new HashMap<Integer, Card>();
		
		try 
		{
			InputStream is = Gdx.files.internal(DEFAULT_CARD_FILE).read();
			//InputStream is = Gdx.files.internal("cards/test.json").read();
			
			 String jsonTxt = IOUtils.toString(is); 
					            
            CardCollection col = new Gson().fromJson(jsonTxt, CardCollection.class);
            for(Card c : col.masterCards)
            {
            	masterCards.put(c.id, c);
            }
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void parseCards()
	{
		if(masterCards != null && masterCards.size() > 0)
		{
			for(Card c : masterCards.values())
			{
				//Check is card wanted
				if(selectedPacks.contains(c.expansion))
				{
					if(c.cardType.contains("Q"))
					{
						//Currently can't deal with multi answer cards
						if( c.numAnswers == 1)
						{
							String text = c.text.replace("_", "____");
							McpCah.AVAILABLE_BLACK_CARDS.add(parseText(text));
						}
						
					}
					else if(c.cardType.contains("A"))
					{
						McpCah.AVAILABLE_WHITE_CARDS.add(new SimpleWhiteCard(c.id, parseText(c.text)));
					}
				}
			}
		}
	}
	
	public static String parseText(String text)
	{
		String t = text.replace("\\", "");
		//t = t.replace("\\", "");
		return t;
	}

	
	public static boolean containsTwoUnderlines(String cardText)
	{
		int count = cardText.length();
		String updatedText = cardText.replace("___", "");
		
		return (count > updatedText.length() + 3);
	}
	
}
