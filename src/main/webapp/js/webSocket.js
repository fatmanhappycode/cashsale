var url = "localhost:8080";
var websocket = null;
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://" + url + "/socket?username='123'");
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
    document.getElementById("messageView").innerHTML = document.getElementById("messageView").innerHTML+"event="+ event+"<br/>";
    element.innerHTML=event.data;
    document.getElementById("messageView").appendChild(element);

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
            var messageData={"sender":"123",
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
/*
        function sendFile(isWithText){
            var inputElement = document.getElementById("file");
            var fileList = inputElement.files;
            var file=fileList[0];
            if(!file) return;
            websocket.send(file.name+":fileStart");
            var reader = new FileReader();
            //以二进制形式读取文件
            reader.readAsArrayBuffer(file);
            //文件读取完毕后该函数响应
            reader.onload = function loaded(evt) {
                var blob = evt.target.result;
                //发送二进制表示的文件
                websocket.send(blob);
                if(isWithText){
                    websocket.send(file.name+":fileFinishWithText");
                }else{
                    websocket.send(file.name+":fileFinishSingle");
                }
                console.log("finnish");
            };
            inputElement.outerHTML=inputElement.outerHTML; //清空<input type="file">的值
        }

        function disconnect(){
            if (websocket != null) {
                websocket.close();
                websocket = null;
            }
        }*/

/* function reconnect(){
     if (websocket != null) {
         websocket.close();
         websocket = null;
     }
     if ('WebSocket' in window) {
         websocket = new WebSocket("ws://" + url + "/socket?username='123'");
     } else {
         alert("该浏览器不支持websocket!");
     }
     websocket.onopen = onOpen;
     websocket.onmessage = onMessage;
     websocket.onerror = onError;
     websocket.onclose = onClose;
 }*/
function clean(){
    document.getElementById("message").value = "";
}