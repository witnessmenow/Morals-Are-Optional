//Contains all the methods for changing pages

function goToPage1(){
	hideAllPages();
	document.getElementById("page1").className = "show";
}
function goToPage1Dot5(){
	hideAllPages();
	document.getElementById("page1Dot5").className = "show";
}
function startButtonPressed(){
	sendStartToServer();
}
function goToPage2(){
	hideAllPages();
	document.getElementById("page2").className = "show";
	document.getElementById("whiteCardChooseButton").className = "btn btn-default btn-lg disabled buttonDisable";
	document.getElementById("madeVoteWaitingForJudgement").className = "hide";
	clearSelectedCard();
}
function goToJudgeVote(){
	hideAllPages();
	document.getElementById("judgeChooseButton").className = "btn btn-success btn-lg";
	document.getElementById("startButton").className = "btn btn-default btn-lg disabled buttonDisable";
	document.getElementById("judgeVote").className = "show";
}
function goToJudgeWait(){
	hideAllPages();
	document.getElementById("judgeWait").className = "show";
}
function goToEndOfRoundScreen(){
	hideAllPages();
	document.getElementById("endOfRoundScreen").className = "show";
	setTimeout(function(){
		document.getElementById("chooseButton").className = "btn btn-success btn-lg";
	},2000);
}
function hideAllPages(){
	document.getElementById("page1").className = "hide";
	document.getElementById("page1Dot5").className = "hide";
	document.getElementById("page2").className = "hide";
	document.getElementById("judgeVote").className = "hide";
	document.getElementById("judgeWait").className = "hide";
	document.getElementById("endOfRoundScreen").className = "hide";
}