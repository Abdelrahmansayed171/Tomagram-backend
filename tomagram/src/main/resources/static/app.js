const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/chat'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/66fe954cbb35835311fc6f6d', (message) => {
        console.log(message);
        showMessage(JSON.parse(message.body).content);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

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
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

// Send a message to the WebSocket server
function sendMessage() {
    const conversationId = $("#conversationId").val();  // Get the conversation ID dynamically
    const sender = $("#sender").val();  // Get the sender's name or ID
    const content = $("#messageContent").val();  // Get the message content

    // Publish the message to the server's /app/chat/{conversationId} endpoint
    stompClient.publish({
        destination: `/app/chat/${conversationId}`,
        body: JSON.stringify({ 'sender': sender, 'content': content }),
    });
}

// Display incoming messages
function showMessage(message) {
    console.log(message);
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendMessage());
});