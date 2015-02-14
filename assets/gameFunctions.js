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
	document.getElementById("whiteCardChooseButton").className = "btn btn-default btn-lg disabled buttonDisable";
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

function initMCP() {
	setInterval(sendHeartbeat, 1000);
	sendHeartbeat();
}

function clicker(clickString) {
	document.getElementById(clickString).click();
	if (clickString.indexOf("winningWhiteCardInput") > -1) {
		document.getElementById("startButton").className = "btn btn-success btn-lg";
	}
}

function whiteCardPicker(value) {
	if (!cardSelectionDisabled) {
		for (var i = 0; i < 5; i++) {
			var v = document.getElementById("whiteCard" + i).value;
			if (v == value) {
				$("#parentWhiteCard" + i).addClass("redBorder").removeClass(
						"noBorder");
				document.getElementById("whiteCardChooseButton").className = "btn btn-success btn-lg";
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
				whiteCardDivs[i].className = "whitecard redBorder";
				document.getElementById("startButton").className = "btn btn-success btn-lg";
			} else {
				whiteCardDivs[i].className = "whitecard noBorder";
			}
		}
	}
}