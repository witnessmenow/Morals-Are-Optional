package com.ladinc.core.cards;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.apache.commons.io.IOUtils;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ladinc.core.McpCah;

public class CardParser 
{
	
	private static String DEFAULT_CARD_FILE = "cards/cards.json";
	
	public static List<Card> masterCards;
	
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
	}
	
	public static void readNewCards()
	{
		masterCards = new ArrayList<Card>();
		
		try 
		{
			InputStream is = Gdx.files.internal(DEFAULT_CARD_FILE).read();
			
			 String jsonTxt = IOUtils.toString(is); 
			
			 //Not working with Android for some reason
//			JSONTokener tokener = new JSONTokener(new InputStreamReader(is));
//			JSONObject obj = new JSONObject(tokener);
			
			//JSONObject obj = new JSONObject(jsonTxt);
			
			//JSONArray arr = obj.getJSONArray("masterCards");
			
			Type listType = new TypeToken<List<Card>>() {
            }.getType();
			
            
            CardCollection col = new Gson().fromJson(jsonTxt, CardCollection.class);
			masterCards = col.masterCards;
			
			int i = 0;
			
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
			for(Card c : masterCards)
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
						McpCah.AVAILABLE_WHITE_CARDS.add(parseText(c.text));
					}
				}
			}
		}
	}
	
	public static String parseText(String text)
	{
		String t = text.replace("&reg;", "");
		t = t.replace("\\", "");
		return t;
	}

//	public static void loadWhiteCards()
//	{
//		if(McpCah.AVAILABLE_WHITE_CARDS == null)
//		{
//			McpCah.AVAILABLE_WHITE_CARDS = new ArrayList<String>();
//		}
//		
//		try 
//		{
//			InputStream is = Gdx.files.internal("cards/white/base.json").read();
//			
//			 String jsonTxt = IOUtils.toString(is); 
//			
//			 //Not working with Android for some reason
////			JSONTokener tokener = new JSONTokener(new InputStreamReader(is));
////			JSONObject obj = new JSONObject(tokener);
//			
//			JSONObject obj = new JSONObject(jsonTxt);
//			
//			JSONArray arr = obj.getJSONArray("cards");
//			for (int i = 0; i < arr.length(); i++)
//			{
//			    String cardText = arr.getJSONObject(i).getString("text");
//			    cardText = cardText.replace("[", "");
//			    cardText = cardText.replace("]", "");
//			    
//			    if(!McpCah.AVAILABLE_WHITE_CARDS.contains(cardText))
//			    {
//			    	McpCah.AVAILABLE_WHITE_CARDS.add(cardText);
//			    }
//			    
//			}
//			
//		} 
//		catch (Exception e) 
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public static void loadBlackCards()
//	{
//		if(McpCah.AVAILABLE_BLACK_CARDS == null)
//		{
//			McpCah.AVAILABLE_BLACK_CARDS = new ArrayList<String>();
//		}
//		
//		try 
//		{
//			InputStream is = Gdx.files.internal("cards/black/base.json").read();
//			
//			String jsonTxt = IOUtils.toString(is); 
//			
////			JSONTokener tokener = new JSONTokener(new InputStreamReader(is));
////			JSONObject obj = new JSONObject(tokener);
//			
//			JSONObject obj = new JSONObject(jsonTxt);
//			
//			JSONArray arr = obj.getJSONArray("cards");
//			for (int i = 0; i < arr.length(); i++)
//			{
//			    String cardText = arr.getJSONObject(i).getString("text");
//			    cardText = cardText.replace("[", "");
//			    cardText = cardText.replace("]", "");
//			    
//			    if(!containsTwoUnderlines(cardText))
//			    {
//			    	if(!McpCah.AVAILABLE_BLACK_CARDS.contains(cardText))
//				    {
//				    	McpCah.AVAILABLE_BLACK_CARDS.add(cardText);
//				    }
//			    }
//			    
//			    
//			}
//			
//		} 
//		catch (Exception e) 
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public static boolean containsTwoUnderlines(String cardText)
	{
		int count = cardText.length();
		String updatedText = cardText.replace("___", "");
		
		return (count > updatedText.length() + 3);
	}
	
}
