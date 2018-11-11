var url = "localhost:8080";
var websocket = null;
var username = this.getCookie("username");
document.getElementById("messageView").innerHTML = "getCookie(username)="+username+"<br/>";
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://" + url + "/socket?username="+username);
} else {
    alert("该浏览器不支持websocket!");
}
websocket.onopen = onOpen;
websocket.onmessage = onMessage;
websocket.onerror = onError;
websocket.onclose = onClose;

function onOpen(openEvent) {
    document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML + "OPEN<br/>";

}

function onMessage(event) {
    /*document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML+"event="+ event+"<br/>";
    element.innerHTML=event.data;
    document.getElementById("messageView").appendChild(element);*/
    eval("var message="+event.data+";");
    var date = message.getDate;
    var content = message.getContent;
    document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML+date+"<br/>"+ content+"<br/>";
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
        /* 获取消息的接收者 */
        var to = "12";
        var date=new Date();
        var year=date.getFullYear(); //获取当前年份
        var mon=date.getMonth()+1; //获取当前月份
        var da=date.getDate(); //获取当前日
        var h=date.getHours(); //获取小时
        var m=date.getMinutes(); //获取分钟
        var s=date.getSeconds(); //获取秒
        var d=document.getElementById('Date');
        var time=year+'-'+mon+'-'+da+' '+h+':'+m+':'+s;
        if(msg && to !=null){
            /* 定义json字符串 */
            var messageData={"sender":username,
                "receiver":to,
                "content":msg,
                "date":time
            }
            var messageJson = JSON.stringify(messageData);
            document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML+time + "<br/>";
            document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML+msg + "<br/>";
            websocket.send(messageJson);
        }
        else if(to == null && msg){
            document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML+time + "<br/>";
            document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML+msg + "<br/>";
        }else{
            alert("消息输入不能为空！");
        }
        document.getElementById("message").value="";
    } else {
        alert("链接断开，消息发送失败，请重新链接！");
    }
}