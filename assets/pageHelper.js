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
	if(document.getElementById("addRando").checked){
		registerRandoWithServer();
	}
	sendStartToServer();
}
function goToPage2(){
	hideAllPages();
	$("#page2").addClass("show").removeClass("hide");
	document.getElementById("whiteCardChooseButton").className = "btn btn-default btn-lg disabled buttonDisable";
	document.getElementById("madeVoteWaitingForJudgement").className = "hide";
	clearSelectedCard();
}
function goToJudgeVote(){
	hideAllPages();
	resetJudgePage();
}
function goToJudgeWait(){
	hideAllPages();
	document.getElementById("judgeWait").className = "show";
}
function goToEndOfRoundScreen(){
	hideAllPages();
	document.getElementById("endOfRoundScreen").className = "show";
	
	document.getElementById("chooseButton").className = "btn btn-default btn-lg disabled buttonDisable";
	setTimeout(function(){
		document.getElementById("chooseButton").className = "btn btn-success btn-lg";
	},2000);
}
function hideAllPages(){
	document.getElementById("page1").className = "hide";
	document.getElementById("page1Dot5").className = "hide";
	$("#page2").addClass("hide").removeClass("show");
	document.getElementById("judgeVote").className = "hide";
	document.getElementById("judgeWait").className = "hide";
	document.getElementById("endOfRoundScreen").className = "hide";
}