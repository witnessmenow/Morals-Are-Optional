//Methods for sending and handling heartbeats

var handledPlayerName = false;

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
	
	if(obj.hasOwnProperty('state'))
	{
	
		// Waiting for card submission from player - Move to playerCardSelect
		if(obj.state === "waitingForCardPlayer")
		{
			if (document.getElementById("playerCardSelect").className != "show")
			{
				//fix border
				noBorderFunction();
				goToPlayerCardSelect();
			}
			
			if(cardSelectionDisabled)
			{
				clearSelectedCard();
			}
			
			
			// Text of cards is getting re populate every heartbeat,
			// this is not needed, and a bit wastful
			// TODO: visit this.
			
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
		// Card is submitted by player
		else if(obj.state === "cardSubmittedPlayer")
		{
			var cardSubmittedMessage = "You have submitted your card.";
		
			if(!checkIsPageHidden("messagePage") || getMessagePageMessage() != cardSubmittedMessage)
			{
				goToMessagePage(cardSubmittedMessage);
			}
		}
		else if(obj.state === "waitingForSubmissionsJudge")
		{
			if (document.getElementById("judgeWait").className != "show")
			{
				goToJudgeWait();
			}
		}
		else if(obj.state === "waitingForVoteJudge")
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
		else if(obj.state === "endOfRound")
		{
			handleEndOfRoundHeartbeat(obj);
		}
		else if(obj.state === "paused")
		{
			if(!checkIsPageHidden("pausedPage"))
			{
				goToPausedPage();
				handledPlayerName = false;
			}
			
			//If paused no need to do anything else
			return;
		}
		else if(obj.state === "notDealt")
		{
			var notDealtMessage = "You will be dealt in the next round.";
		
			if(!checkIsPageHidden("messagePage") || getMessagePageMessage() != notDealtMessage)
			{
				goToMessagePage(notDealtMessage);
			}
		}
	}
	
	if(obj.hasOwnProperty('numberOfPlayers'))
	{
		if(document.getElementById("page1Dot5").className == "hide")
		{
			goToPage1Dot5();
		}
	
		if(obj.hasOwnProperty('connectedPlayers')){
			document.getElementById("connectedPlayers").innerHTML = "";
			for(var i in obj.connectedPlayers){
				var divHolder = document.createElement("p");
				divHolder.id = "scoreHolder" + i;
				divHolder.innerHTML = obj.connectedPlayers[i].name;
				divHolder.className = "lead";
				
				//var para = document.createElement("span");
				//para.innerHTML = obj.connectedPlayers[i].name;
				//para.id = obj.connectedPlayers[i].name;
				//para.className = "marginPt5em";
				
				//divHolder.appendChild(para);
				document.getElementById("connectedPlayers").appendChild(divHolder);
			}
		}
		
		if(obj.numberOfPlayers > 2){
			enableStartButton();
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
				blackCards[i].innerHTML = "<p>" + currentBlackCardText + "</p>";
			}
			
		}
		
	}
	
	if(!handledPlayerName)
	{
		if(obj.hasOwnProperty('playerName'))
		{
			handledPlayerName = true;
			$("#navBarRight").prop("class" , "nav navbar-nav navbar-right");
			$("#navDropDownText").text(obj.playerName);
		}
	}
}
function handleEndOfRoundHeartbeat(obj)
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
				para.id = obj.scores[i].name + "Score";
				if(obj.hasOwnProperty('winner') && obj.winner == obj.scores[i].name){
					para.innerHTML = "<span class=\"stars\">&#9733;</span>"+obj.scores[i].name + ": " + obj.scores[i].score;
					para.style = "font-weight:bold";
					//removing for initial release
					//showWinnerBanner(obj.scores[i].name);
				}else{
					para.innerHTML = obj.scores[i].name + ": " + obj.scores[i].score;
				}
				
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
function showWinnerBanner(winnerName){
	document.getElementById("winnerOfRoundName").innerHTML = winnerName;
	$("#winnerOfRoundDiv").fadeIn(100, function() {
		$("#winnerOfRoundDiv").fadeOut(4000, function() {
		});
	});
}