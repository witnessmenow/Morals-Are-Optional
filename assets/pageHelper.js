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
	bootbox.dialog({
	  message: "Are you sure you want to start? (All players might not have joined yet!)<br/><br/>"
	  +" Rando is a random player which will submit a card each round.",
	  buttons: {
	  	danger: {
	      label: "No",
	      className: "btn-danger",
	      callback: function() {
	        //Do Nothing
	      }
	    },
	    main: {
	      label: "Start game with Rando",
	      className: "btn-primary",
	      callback: function() {
			registerRandoWithServer();
			sendStartToServer();
	      }
	    },
	    success: {
	      label: "Start Game",
	      className: "btn-success",
	      callback: function() {
			sendStartToServer();
	      }
	    }
	  }
	});
}
function goToPage2(){
	hideAllPages();
	$("#page2").addClass("show").removeClass("hide");
	disableButton($("#whiteCardChooseButton"));
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
function goToPausedPage()
{
	hideAllPages();
	hideRightNavBar();
	document.getElementById("pausedPage").className = "show";
}
function goToMessagePage(message)
{
	hideAllPages();
	document.getElementById("messagePageMessage").innerHTML = message;
	document.getElementById("messagePage").className = "show";
}
function hideAllPages(){
	document.getElementById("page1").className = "hide";
	document.getElementById("page1Dot5").className = "hide";
	$("#page2").addClass("hide").removeClass("show");
	document.getElementById("judgeVote").className = "hide";
	document.getElementById("judgeWait").className = "hide";
	document.getElementById("endOfRoundScreen").className = "hide";
	document.getElementById("pausedPage").className = "hide";
	document.getElementById("messagePage").className = "hide";
}
function checkIsPageHidden(pageId)
{
	if(document.getElementById(pageId).className == "hide")
	{
		return false;
	}
	else
	{
		return true;
	}
}
function hideRightNavBar()
{
	$("#navBarRight").prop("class" , "hide");
}
function getMessagePageMessage()
{
	return document.getElementById("messagePageMessage").innerHTML;
}
function disableButton(buttonJqueryObj)
{
	buttonJqueryObj.prop("class" , "btn btn-default btn-lg disabled");
}

function enableButton(buttonJqueryObj)
{
	buttonJqueryObj.prop("class" , "btn btn-success btn-lg");
}