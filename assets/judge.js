function removeJudgeWhiteCards()
{
	var parentElement = document.getElementById("whiteCardContainer");
	var judgesWhiteCards = document.getElementsByName("judgesWhiteCard");
	
	while( 0 < judgesWhiteCards.length) 
	{
        if(judgesWhiteCards[0] && judgesWhiteCards[0].parentElement) 
        {
        	parentElement.removeChild(judgesWhiteCards[0]);
        }
    }
}
function generateJudgeWhiteCards(cards)
{
	removeJudgeWhiteCards();
	cardVotingDisabled = true;
	
	var arrayLength = cards.length;
	
	var divtest = document.createElement("div");
	var ptest = document.createElement("p");
	divtest.setAttribute("id","singleShowCard");
	ptest.setAttribute("id","singleCardText");
	divtest.appendChild(ptest);
	document.getElementById("dummyWhiteCardContainer").appendChild(divtest);
	for (var i = 0; i < arrayLength; i++) 
	{
		var id = cards[i].selectedCard.id;
		
		var div = document.createElement("div");
		div.id = "winningWhiteCard" + id;
		div.setAttribute("name", "judgesWhiteCard");
		div.setAttribute('class', 'hide');
		
		var div2 = document.createElement("div");
		div2.setAttribute('class', 'ui-radio');
		
		var input = document.createElement("input");
		input.id = "winningWhiteCardInput" + id;
		input.type = "radio";
		input.name = "chosenCard";
		input.value = id;
		
		input.setAttribute('onclick','winningWhiteCardPicker("'+ id + '")');
				
		var para = document.createElement("p");
		para.innerHTML = cards[i].selectedCard.text;
		para.id = "winningWhiteCardPara" + id;
		
		div2.appendChild(input);
		div.appendChild(div2);
		div.appendChild(para);
		
		div.setAttribute('onclick','clicker("winningWhiteCardInput'+ id + '")');
		
		document.getElementById("whiteCardContainer").appendChild(div);
	}
	
	var dummyWhiteCard = document.createElement("div");
	dummyWhiteCard.id = "dummyWhiteCard"; 
	dummyWhiteCard.setAttribute("name", "judgesWhiteCard");
	dummyWhiteCard.setAttribute("class", "hide");
	document.getElementById("dummyWhiteCardContainer").appendChild(dummyWhiteCard);
	
	document.getElementById("dummyWhiteCardContainer").className = "whiteCardContainer";
}
function resetJudgePage()
{
	document.getElementById("judgeChooseButton").className = "btn btn-success btn-lg";
	document.getElementById("judgeChooseButton").innerHTML = "Reveal Next";
	document.getElementById("startButton").className = "hide";
	document.getElementById("judgeVote").className = "show";
	document.getElementById("JudgeScreenMessage").className = "hide";
	document.getElementById("whiteCardContainer").className = "hide";
}

function next()
{
	var whiteCardDivs = document.getElementsByName("judgesWhiteCard");
	
	
	//White Card Div is made up of all available white cards plus one dummy
	for(var i = 0; i < whiteCardDivs.length; i++) 
	{
		if(whiteCardDivs[i].className == "hide")
		{
			document.getElementById("singleShowCard").className = "whiteCard noBorder whiteCardInline";
			whiteCardDivs[i].className = "hide2";
			
			var children = whiteCardDivs[i].childNodes;
			for(var j = 0; j < whiteCardDivs.length; j++)
			{
				if(children[j] != undefined)
				{
					if (children[j].nodeName == "P")
					{
						$("#singleCardText").html(children[j].innerHTML);
					}
					else if(children[j].nodeName == "DIV")
					{
						var subChildren = children[j].childNodes;
						for(var k = 0; k < subChildren.length; k++)
						{
							if(subChildren[k] != undefined && subChildren[k].nodeName == "INPUT")
							{
								revealCard(subChildren[k].value);
							}
						}
					}
				}
			}
			
			//The this is the last real white card, change inner text of the button
			if(i == whiteCardDivs.length - 2)
			{
				document.getElementById("judgeChooseButton").innerHTML = "Show All";
			}
			
			//If this is the last card (the dummy card) show all real cards for voting.
			if(i == whiteCardDivs.length - 1)
			{
				document.getElementById("judgeChooseButton").className = "hide";
				document.getElementById("startButton").className = "btn btn-default btn-lg disabled buttonDisable";
				document.getElementById("JudgeScreenMessage").className = "bg-success centerText";
				
				
				$("#singleShowCard").remove();
				$("#dummyWhiteCard").remove();
				for(var i = 0; i < whiteCardDivs.length; i++) {
					if(whiteCardDivs[i].className == "hide2"){
						whiteCardDivs[i].className = "whiteCard noBorder whiteCardInline";
					}
				}
				
				document.getElementById("whiteCardContainer").className = "whiteCardContainer";
				document.getElementById("dummyWhiteCardContainer").className = "hide";
				
				cardVotingDisabled = false;
			}
			else
			{
				//not the dummy card, so we want to disable the reveal button for 2 seconds
				
				document.getElementById("judgeChooseButton").className = "btn btn-default btn-lg disabled buttonDisable";
				
				setTimeout(function(){
					document.getElementById("judgeChooseButton").className = "btn btn-success btn-lg";
					},2000);
			}
			
			//We only want to show one at a time;
			return;
		}
		cardVotingDisabled = false;
	}	
}