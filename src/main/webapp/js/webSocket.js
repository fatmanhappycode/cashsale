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
    document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML + "<br/>";
    var txt = document.getElementById("messageView");
    txt.scrollTop=txt.scrollHeight;
}

function onMessage(event) {
    var message = eval('(' + event.data + ')');
    var date = message.date;
    var content = message.content;
    var contact = message.data;
    document.getElementById("users").innerHTML =
        "<input checked='checked' type=radio name='users' value='" + message.sender + "' />" + "<p class='usersID'>" + message.sender + "</p><br/>";

    // if (undefined == content) {
    //     to=message.sender;
    // }
    if (undefined == content) {
        if (getCookie("goodsUsername") != null) {
            document.getElementById("users").innerHTML =
                "<input checked='checked' type=radio name='users' value='" + getCookie("goodsUsername") + "' />" + "<p class='usersID'>" + getCookie("goodsUsername") + "</p><br/>";
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
            "<p class='time'>"+date+"</p><br/><p class='receiver'>"+content+"</p>"+"<br/>";
    }
    var txt = document.getElementById("messageView");
    txt.scrollTop=txt.scrollHeight;
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
                "<p class='time'>"+time+"</p><br/><p class='send'>"+msg+"</p>"+"<br/>";
            websocket.send(messageJson);
        }
        else if(to == null && msg){
            document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML+
                "<p class='time'>"+time+"</p><br/><p class='send'>"+msg+"</p>"+"<br/>";
            websocket.send(messageJson);
        }else{
            alert("消息输入不能为空！");
        }
        document.getElementById("message").value="";
    } else {
        alert("链接断开，消息发送失败，请重新链接！");
    }
    var txt = document.getElementById("messageView");
    txt.scrollTop=txt.scrollHeight;
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
                if(data!=null&&data!=""&&data!=undefined){
                    innermessagedata(data);
                }
            }
        },
        error: function (xhr, errrorMessage, e) {
            alert("系统异常！");
        }
    });



}
function innermessagedata(data) {
    $.each(data,function(index,obj) {
        if(document.getElementById("messageView").innerHTML !=""){
            document.getElementById("messageView").innerHTML +="<p class='time'>"+obj.date+"</p><p class='receiver'>"+obj.content +"</p>";
        }else{
            document.getElementById("messageView").innerHTML ="<p class='time'>"+obj.date+"</p><p class='receiver'>"+obj.content +"</p>";
        }
        })
    document.getElementById("users").innerHTML =
        "<input checked='checked' type=radio name='users' value='" +data[0].sender + "' />" + "<p class='usersID'>" + data[0].sender + "</p><br/>";
    to = data[0].sender;
    var txt = document.getElementById("messageView");
    txt.scrollTop=txt.scrollHeight;
}


window.onload = function getmessag() {
    getmessage();
}

//回车事件
function enterSubmit(obj) {
    var button = document.getElementById('send');
    //enter按键的keyCode编码为13
    if (obj.keyCode == 83) {
        button.click();
    }
}