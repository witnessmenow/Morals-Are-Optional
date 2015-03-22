var nextToShow = 0;
var numPlayers = 3

var currentBlackCardText = "";
var cardSelectionDisabled = false;
var cardVotingDisabled = true;

function enableStartButton() {
	document.getElementById("beginButton").className = "btn btn-success btn-lg";
}
function clearSelectedCard() {
	var cards = document.getElementsByName("whiteCard");
	var selectedCard = "";
	for (var i = 0; i < cards.length; i++) {
		cards[i].checked = false;
		cards[i].className = "whitecard noBorder";
		cards[i].disabled = false;
	}

	cardSelectionDisabled = false;

}
function disableCardSelection() {
	var cards = document.getElementsByName("whiteCard");
	var selectedCard = "";
	for (var i = 0; i < cards.length; i++) {
		cards[i].disabled = true;
	}

	cardSelectionDisabled = true;

}
function choose() {
	var cards = document.getElementsByName("whiteCard");
	var selectedCard = "";
	for (var i = 0; i < cards.length; i++) {
		if (cards[i].checked == true) {
			selectedCard = cards[i].value;
			disableCardSelection();
		}
	}

	playCard(selectedCard);
	disableButton($("#whiteCardChooseButton"));
	document.getElementById("madeVoteWaitingForJudgement").className = "bg-success";

	//alert(selectedCard);
	//goToPage3();
}
function castVote() {
	var cards = document.getElementsByName("chosenCard");
	var selectedCard = "";
	for (var i = 0; i < cards.length; i++) {
		if (cards[i].checked == true) {
			selectedCard = cards[i].value;
		}
	}

	voteCard(selectedCard);
}
function registerPlayer() {
	registerNameWithServer(document.getElementById("name").value);
}

function validateName(){
	var name = $("#name").val();
	
	name = name.replace(/ /g, "");
	
	//First check is it empty
	if(name === "")
	{
		disableButton($("#nameButton"));
	}
	else
	{
    	var patt = new RegExp("^[a-zA-Z0-9]+$");
    	var res = patt.test(name);
    	
    	//name only contains alpha numeric or spaces
    	if(res)
    	{
    		enableButton($("#nameButton"));
    		$("#nameErrorMessage").prop("class" , "hide");
    	}
    	else
    	{
    		disableButton($("#nameButton"));
    		$("#nameErrorMessage").prop("class" , "bg-danger");
    	}
	}		
}

function initMCP() {
	setInterval(sendHeartbeat, 1000);
	sendHeartbeat();
}

function clicker(clickString) {
	document.getElementById(clickString).click();
	if (clickString.indexOf("winningWhiteCardInput") > -1) {
		enableButton($("#startButton"));
	}
}

function whiteCardPicker(value) {
	if (!cardSelectionDisabled) {
		for (var i = 0; i < 5; i++) {
			var v = document.getElementById("whiteCard" + i).value;
			if (v == value) {
				$("#parentWhiteCard" + i).addClass("redBorder").removeClass(
						"noBorder");
				enableButton($("#whiteCardChooseButton"));
			} else {
				$("#parentWhiteCard" + i).addClass("noBorder").removeClass(
						"redBorder");
			}
		}
	}
}

function noBorderFunction() {
	for (var i = 0; i < 5; i++) {
		$("#parentWhiteCard" + i).addClass("noBorder").removeClass("redBorder");
	}
}

function winningWhiteCardPicker(index) {

	if (!cardVotingDisabled) {
		var whiteCardDivs = document.getElementsByName("judgesWhiteCard");

		for (var i = 0; i < whiteCardDivs.length; i++) {
			if (whiteCardDivs[i].id == "winningWhiteCard" + index) {
				whiteCardDivs[i].className = "whitecard whiteCardInline redBorder";
				enableButton($("#startButton"));
			} else {
				whiteCardDivs[i].className = "whitecard whiteCardInline noBorder";
			}
		}
	}
}