package com.ladinc.core.cards;

import org.json.simple.JSONObject;

public class SimpleWhiteCard 
{
	public String text;
	public int id;
	
	public SimpleWhiteCard(int id, String text)
	{
		this.id = id;
		this.text = text;
	}
	
	public SimpleWhiteCard(Card c)
	{
		this.id = c.id;
		this.text = c.text;
	}
	
	public JSONObject getJsonObj()
	{
		JSONObject obj = new JSONObject();
		obj.put("id", this.id);
		obj.put("text", this.text);
		
		return obj;
	}

}
