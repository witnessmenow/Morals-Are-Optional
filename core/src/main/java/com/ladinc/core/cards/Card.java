package com.ladinc.core.cards;

public class Card 
{
	public int id;
	public String cardType;
	public String text;
	public int numAnswers;
	public String expansion;
	
	public static String filterText(String input)
	{
		String output = input;
		output = output.replace("&reg;", "(R)");
		output = output.replace("&trade;", " (TM)");
		output = output.replace("&copy;", " (C)");
		output = output.replace("&#34;;", " \"");
		return output;
	}

}
