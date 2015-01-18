function removeJudgeWhiteCards()
{
	var parentElement = document.getElementById("judgeVoteCards");
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
	document.getElementById("judgeVoteCards").appendChild(divtest);
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
		
		winningWhiteCardPicker
		
		var para = document.createElement("p");
		para.innerHTML = cards[i].selectedCard.text;
		para.id = "winningWhiteCardPara" + id;
		
		div2.appendChild(input);
		div.appendChild(div2);
		div.appendChild(para);
		
		div.setAttribute('onclick','clicker("winningWhiteCardInput'+ id + '")');
		
		document.getElementById("judgeVoteCards").appendChild(div);
	}
	
	var dummyWhiteCard = document.createElement("div");
	dummyWhiteCard.id = "dummyWhiteCard"; 
	dummyWhiteCard.setAttribute("name", "judgesWhiteCard");
	dummyWhiteCard.setAttribute("class", "hide");
	document.getElementById("judgeVoteCards").appendChild(dummyWhiteCard);

}
function next()
{
	var whiteCardDivs = document.getElementsByName("judgesWhiteCard");
	
	for(var i = 0; i < whiteCardDivs.length; i++) 
	{
		if(whiteCardDivs[i].className == "hide")
		{
			document.getElementById("singleShowCard").className = "whiteCard noBorder";
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
			
			//If this is the last card enable the button
			if(i == whiteCardDivs.length - 1)
			{
				document.getElementById("judgeChooseButton").className = "btn btn-default btn-lg disabled buttonDisable";
				
				$("#singleShowCard").remove();
				$("#dummyWhiteCard").remove();
				for(var i = 0; i < whiteCardDivs.length; i++) {
					if(whiteCardDivs[i].className == "hide2"){
						whiteCardDivs[i].className = "whiteCard noBorder";
					}
				}
				cardVotingDisabled = false;
			}
			
			//We only want to show one at a time;
			return;
		}
		
		cardVotingDisabled = false;
	}
	
}