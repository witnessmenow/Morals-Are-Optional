package com.ladinc.core.cards;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;



import com.ladinc.core.McpCah;

public class CardParser 
{
	
	public static void loadCards()
	{
		loadWhiteCards();
		loadBlackCards();
	}

	public static void loadWhiteCards()
	{
		if(McpCah.AVAILABLE_WHITE_CARDS == null)
		{
			McpCah.AVAILABLE_WHITE_CARDS = new ArrayList<String>();
		}
		
		try 
		{
			InputStream is = CardParser.class.getResourceAsStream("/cards/white/base.json");
			JSONTokener tokener = new JSONTokener(new InputStreamReader(is));
			JSONObject obj = new JSONObject(tokener);
			
			JSONArray arr = obj.getJSONArray("cards");
			for (int i = 0; i < arr.length(); i++)
			{
			    String cardText = arr.getJSONObject(i).getString("text");
			    cardText = cardText.replace("[", "");
			    cardText = cardText.replace("]", "");
			    
			    if(!McpCah.AVAILABLE_WHITE_CARDS.contains(cardText))
			    {
			    	McpCah.AVAILABLE_WHITE_CARDS.add(cardText);
			    }
			    
			}
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadBlackCards()
	{
		if(McpCah.AVAILABLE_BLACK_CARDS == null)
		{
			McpCah.AVAILABLE_BLACK_CARDS = new ArrayList<String>();
		}
		
		try 
		{
			InputStream is = CardParser.class.getResourceAsStream("/cards/black/base.json");
			JSONTokener tokener = new JSONTokener(new InputStreamReader(is));
			JSONObject obj = new JSONObject(tokener);
			
			JSONArray arr = obj.getJSONArray("cards");
			for (int i = 0; i < arr.length(); i++)
			{
			    String cardText = arr.getJSONObject(i).getString("text");
			    cardText = cardText.replace("[", "");
			    cardText = cardText.replace("]", "");
			    
			    if(!McpCah.AVAILABLE_BLACK_CARDS.contains(cardText))
			    {
			    	McpCah.AVAILABLE_BLACK_CARDS.add(cardText);
			    }
			    
			}
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
