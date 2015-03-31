//Contains the events that are passed to the Java Game

function makeUrlSafe(text)
{
	var t = text.replace(/%/g,'%25');
	
	return t;
}
function playCard(cardText)
{
	var myRand=parseInt(Math.random()*99999999);
	
	var url = "passEvent?id=" + getId()  +  "&event=cardSelect&card="	+ cardText;
	url = makeUrlSafe(url);
	
	var xmlhttp
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	
	xmlhttp.open("GET",url,true);
	
	xmlhttp.onreadystatechange = function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200) 
		{ 
		
		}
	}
	
	xmlhttp.send();
}
function voteCard(selectedCard)
{
	
	var url = "passEvent?id=" + getId()  +  "&event=winnerSelect&card="	+ selectedCard;
	
	url = makeUrlSafe(url);
	
	var xmlhttp
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	
	xmlhttp.open("GET",url,true);
	
	xmlhttp.onreadystatechange = function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200) 
		{ 
		
		}
	}
	
	xmlhttp.send();
}
function revealCard(cardText)
{
	
	var url = "passEvent?&event=revealCard&card=" + cardText;
	
	url = makeUrlSafe(url);
	
	var xmlhttp
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	
	xmlhttp.open("GET",url,true);
	
	xmlhttp.onreadystatechange = function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200) 
		{ 
		
		}
	}
	
	xmlhttp.send();
}
function startNext()
{
	var url = "passEvent?&event=nextRound";
	
	var xmlhttp
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	
	xmlhttp.open("GET",url,true);
	
	xmlhttp.onreadystatechange = function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200) 
		{ 
		
		}
	}
	
	xmlhttp.send();
}
function sendStartToServer()
{
	var myRand=parseInt(Math.random()*99999999);
	
	var url = "passEvent?&event=start";
	
	var xmlhttp
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	
	xmlhttp.open("GET",url,true);
	
	xmlhttp.onreadystatechange = function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200) 
		{ 
			//Don't Care :)					
		}
	}
	
	xmlhttp.send();
}
function registerRandoWithServer()
{
	var myRand=parseInt(Math.random()*99999999);
	
	var url = "passEvent?&event=registerRando";
	
	var xmlhttp
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	
	xmlhttp.open("GET",url,true);
	
	xmlhttp.onreadystatechange = function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200) 
		{ 
			
		}
	}
	
	xmlhttp.send();
}
function registerNameWithServer(name)
{
	var myRand=parseInt(Math.random()*99999999);
	
	var url = "passEvent?id=" + getId()  +  "&event=register&name="	+ name;
	
	url = makeUrlSafe(url);
	
	var xmlhttp
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	
	xmlhttp.open("GET",url,true);
	
	xmlhttp.onreadystatechange = function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200) 
		{ 
			//When name is registered. redirect to wait screen
			goToPage1Dot5();						
		}
	}
	
	xmlhttp.send();
}
function togglePlayerPause()
{
	
	var url = "passEvent?id=" + getId()  +  "&event=pausePlayer";
	
	url = makeUrlSafe(url);
	
	var xmlhttp
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	
	xmlhttp.open("GET",url,true);
	
	xmlhttp.onreadystatechange = function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200) 
		{ 
			sendHeartbeat();					
		}
	}
	
	xmlhttp.send();
}