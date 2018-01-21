(function () {
  angular
    .module('app')
    .component('chat', {
      templateUrl: 'app/components/chat/chat.html',
      controller: ChatController,
      controllerAs: 'chatCtrl'
    });

  function ChatController($http, $rootScope, $state, $route, $routeParams) {
    var vm = this;

    var messages = [];
    var message = document.getElementById("message");
    var sendButton = document.getElementById("send");
    q
    var header = document.getElementById("chat-header");

    var name = "Admin";

    var socket = io.connect('http://localhost:3700', {
      'reconnection delay': 2000,
      'force new connection': true
    });

    socket.on('connect', function () {
      console.log('connected');
    });

    socket.on('message', function (data) {
      if (data.message) {
        messages.push(data);
        var html = '';
        for (var i = 0; i < messages.length; i++) {
          html += '<b>' + (messages[i].name ? messages[i].name : 'Server') + ': </b>';
          html += messages[i].message + '<br />';
        }
        content.innerHTML = html;
        content.scrollTop = content.scrollHeight;
      } else {
        console.log("There is a problem:", data);
      }
    });

    header.onclick = function() {
        let chat = document.getElementById('chat');
        if(Number(chat.style.bottom.slice(0,-2)) == 0){
            chat.style.bottom = "-370px";
        } else {
            chat.style.bottom = "0px";
        }
    }

    message.onkeypress = function(ev) {
      if (ev.key === "Enter") {
        sendButton.click();
        ev.target.value = "";
      } 
    };

    sendButton.onclick = function () {
      var text = message.value;
      console.log(name + ': ' + text);
      socket.emit('send', {
        message: text,
        name: name
      });
    };

  }
})();
