var url = "localhost:8080";
var websocket = null;
var to = "";
/*var username = "123";*/
var username = "123";
document.getElementById("messageView").innerHTML = "getCookie(name)="+username+"<br/>";
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://" + url + "/socket?username="+username);
} else {
    alert("该浏览器不支持websocket!");
}
websocket.onopen = onOpen;
websocket.onmessage = onMessage;
websocket.onerror = onError;
websocket.onclose = onClose;

$(function(){
    /*to = $('input:radio[name="users"]:checked').val();*/
    to = "12";
});


function onOpen(openEvent) {
    document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML + "OPEN<br/>";
    /*document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML + openEvent;*/
    /*var person = eval('('+openEvent.data+')');
    if(undefined != person){
        var contact = person.data;
        for(var i = 0; i < contact.length; i++){
            document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML + username;
            if(contact[i] != username) {
                document.getElementById("users").innerHTML =
                    "<input type=radio name='users' value='" + contact[i] + "' />" + i + ":" + contact[i] + "<br/>";
            }
        }
    }*/
}

function onMessage(event) {
    /*eval("var message="+event.data+";");*/
    var message = eval('(' + event.data + ')');
    var date = message.date;
    var content = message.content;
    var contact = message.data;
    if (undefined != contact){
        for(var i = 0; i < contact.length; i++){
            if(contact[i] != username) {
                document.getElementById("users").innerHTML =
                    "<input type=radio name='users' value='"+ contact[i] + "' />" + i + ":" + contact[i] + "<br/>";
            }
        }
    }
    if (undefined != content) {
        document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML +
            "<p class='time'>"+date+"<br/></p><p class='receiver'>"+content+"<br/>"+"</p>";
    }
}
function onError() {
    alert("链接失败，请重新链接！")
}
function onClose(event) {
    console.log(event.reason)
    document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML+ "CLOSE<br/>";
}

function doSend() {
    if (websocket.readyState == 1) {
        var msg = document.getElementById("message").value;
        var date=new Date();
        //获取当前年份
        var year=date.getFullYear();
        //获取当前月份
        var mon=date.getMonth()+1;
        //获取当前日
        var da=date.getDate();
        //获取小时
        var h=date.getHours();
        //获取分钟
        var m=date.getMinutes();
        //获取秒
        var s=date.getSeconds();
        var d=document.getElementById('Date');
        var time=year+'-'+mon+'-'+da+' '+h+':'+m+':'+s;
        /* 定义json字符串 */
        var messageData={"sender":username,
            "receiver":to,
            "content":msg,
            "date":time
        }
        document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML+to;
        var messageJson = JSON.stringify(messageData);
        if(msg && to !=null){
            document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML+
                "<p class='time'>"+time+"<br/></p><p class='send'>"+msg+"<br/>"+"</p>";
            websocket.send(messageJson);
        }
        else if(to == null && msg){
            document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML+
                "<p class='time'>"+time+"<br/></p><p class='send'>"+msg+"<br/>"+"</p>";
            websocket.send(messageJson);
        }else{
            alert("消息输入不能为空！");
        }
        document.getElementById("message").value="";
    } else {
        alert("链接断开，消息发送失败，请重新链接！");
    }
}

function reConnect() {
    if (websocket != null) {
        websocket.close();
        websocket = null;
    }
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://" + url + "/socket?username="+12);
        to = "123";
        document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML+"username=12<br/>";
    } else {
        alert("该浏览器不支持websocket!");
    }
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;
    websocket.onclose = onClose;
}