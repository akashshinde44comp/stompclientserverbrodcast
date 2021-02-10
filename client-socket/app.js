var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
//reading from input text form
var agentId = $("#name").val();
var socket = new SockJS('http://localhost:8080/server-ws');
if(agentId == "" || agentId === 'undefined'){
alert("Please enter username to connect !");
}else{
stompClient = Stomp.over(socket);
stompClient.connect({'Login':agentId}, function (frame) {
    setConnected(true);
    console.log('Connected to subscription');
    stompClient.subscribe("/user/queue/client", function (response) {
        console.log(response.body);
        showGreeting(response.body);
    });
});
}

}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/server/chat", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
 $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});
