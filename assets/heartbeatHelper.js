//Methods for sending and handling heartbeats

function sendHeartbeat()
{
	var url = "heartbeat?id=" + getId();
	
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
			handleHeartbeat(xmlhttp.responseText);
		}
	}
	
	xmlhttp.send();
}

function handleHeartbeat(respText)
{
	var obj = JSON.parse(respText);
	
	if ($.isEmptyObject(obj))
	{
		//Currently doesnt work right as state of pages is not reset
		//goToPage1();
	}
	
	if(obj.hasOwnProperty('numberOfPlayers'))
	{
		if(obj.hasOwnProperty('connectedPlayers')){
			document.getElementById("connectedPlayers").innerHTML = "";
			for(var i in obj.connectedPlayers){
				var divHolder = document.createElement("h4");
				divHolder.id = "scoreHolder" + i;
				
				var para = document.createElement("span");
				para.innerHTML = obj.connectedPlayers[i].name;
				para.id = obj.connectedPlayers[i].name;
				para.className = "marginPt5em";
				
				divHolder.appendChild(para);
				document.getElementById("connectedPlayers").appendChild(divHolder);
			}
		}
		
		if(obj.numberOfPlayers > 2){
			enableStartButton();
		}
	}
	
	
	if(obj.hasOwnProperty('winningCard'))
	{
		if (document.getElementById("endOfRoundScreen").className != "show")
		{
			goToEndOfRoundScreen();
			if(obj.hasOwnProperty('scores')){
				document.getElementById("scoreboard").innerHTML = "";
				for(var i in obj.scores){
					var divHolder = document.createElement("h1");
					divHolder.id = "scoreHolder" + i;
					
					var para = document.createElement("small");
					para.innerHTML = obj.scores[i].name + " " + obj.scores[i].score;
					para.id = obj.scores[i].name + "Score";
					
					divHolder.appendChild(para);
					document.getElementById("scoreboard").appendChild(divHolder);
				}
			}
		}

		if(document.getElementById("whiteCardEndOfRoundText").innerHTML != obj.winningCard.text)
		{
			document.getElementById("whiteCardEndOfRoundText").innerHTML = obj.winningCard.text;
		}
	}
	else
	{
		if(obj.hasOwnProperty('cards'))
		{
			if (document.getElementById("page2").className != "show")
			{
				//fix border
				noBorderFunction();
				goToPage2();
			}
			
			var arrayLength = obj.cards.length;
			for (var i = 0; i < arrayLength; i++) 
			{
				if (document.getElementById("whiteCardText" + i).innerHTML != obj.cards[i].text)
				{
					document.getElementById("whiteCardText" + i).innerHTML = obj.cards[i].text;
				}
				
				if (document.getElementById("whiteCard" + i).value != obj.cards[i].id)
				{
					document.getElementById("whiteCard" + i).value = obj.cards[i].id;
				}
			}
		}
		
		if(obj.hasOwnProperty('selectedCard'))
		{
			if(!cardSelectionDisabled)
			{
				disableCardSelection();
				if(document.getElementById("madeVoteWaitingForJudgement").className != "show")
				{
					document.getElementById("whiteCardChooseButton").className = "btn btn-default btn-lg disabled buttonDisable";
					document.getElementById("madeVoteWaitingForJudgement").className = "bg-success";
				}
			}
		}
		else
		{
			//If we dont have a card selected the cardSelection should be enabled
			if(cardSelectionDisabled)
			{
				clearSelectedCard();
			}
		}
		
		if(obj.hasOwnProperty('judge'))
		{
			if(obj.judge == "wait")
			{
				if (document.getElementById("judgeWait").className != "show")
				{
					goToJudgeWait();
				}
			}
			
			if(obj.judge == "vote")
			{
				if (document.getElementById("judgeVote").className != "show")
				{
					goToJudgeVote();
					if(obj.hasOwnProperty('selected'))
					{
						generateJudgeWhiteCards(obj.selected);	
					}
				}
			}

		}
	}
	
	if(obj.hasOwnProperty('blackCard'))
	{
		if(currentBlackCardText != obj.blackCard)
		{
			currentBlackCardText = obj.blackCard; 
			
			var blackCards = document.getElementsByName("blackCardText");
			
			var arrayLength = blackCards.length;
			for (var i = 0; i < arrayLength; i++) {
				blackCards[i].innerHTML = currentBlackCardText;
			}
			
		}
		
	}
}