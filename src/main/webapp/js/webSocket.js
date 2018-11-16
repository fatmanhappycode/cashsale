var url = "localhost:8080";
var websocket = null;
var to = "";
var username = getCookie("username");
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
    //to = $('input:radio[name="users"]:checked').val();
    to = getCookie("goodsUsername");
});


function onOpen(openEvent) {
    document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML + "OPEN<br/>";
}

function onMessage(event) {
    var message = eval('(' + event.data + ')');
    var date = message.date;
    var content = message.content;
    var contact = message.data;
    document.getElementById("users").innerHTML =
        "<input type=radio name='users' value='" + message.sender + "' />" + ":" + message.sender + "<br/>";

    // if (undefined == content) {
    //     to=message.sender;
    // }
    if (undefined == content) {
        if (getCookie("goodsUsername") != null) {
            document.getElementById("users").innerHTML =
                "<input type=radio name='users' value='" + getCookie("goodsUsername") + "' />" + ":" + getCookie("goodsUsername") + "<br/>";
        } else {
            document.getElementById("users").innerHTML ="<p></p><br>";
        }
    }
    if (undefined != content) {
        to=message.sender;
        // for (var i = 0; i < contact.length; i++) {
        //     if (contact[i] != username) {
        //         document.getElementById("users").innerHTML =
        //             "<input type=radio name='users' value='" + contact[i] + "' />" + i + ":" + contact[i] + "<br/>";
        //     }
        // }

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

function getmessage() {
    var saveData = {"username": getCookie("username")}
    $.ajax({
        url: "/getMessage",
        type: "get",
        headers: {
            contentType: "application/json;charset=UTF-8"
        },
        data: saveData,
        contentType: "application/json",
        success: function (result, testStatus) {
            if (result.code == "124") {
                data=result.data;
                innermessagedata(data);
            }
        },
        error: function (xhr, errrorMessage, e) {
            alert("系统异常！");
        }
    });
}
function innermessagedata(data) {
    $.each(data,function(index,obj) {
        document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML +"<p class='time'>"+obj.date+"</p>"+obj.content ;
    })
    document.getElementById("users").innerHTML =
        "<input type=radio name='users' value='" + data[0].sender + "' />" + ":" + data[0].sender + "<br/>";
    to = data[0].sender;
}


window.onload = function getmessag() {
    getmessage();
}