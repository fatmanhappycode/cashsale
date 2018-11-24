/*
* 登录时的异步请求
* 作者：lu
* */

// 点击登录时调用
/*
* 获取value后请求后台，若存储在此用户且密码正确，则登录成功
* */
function loadXMLDoc()
{
    var username = $("#username").val();
    var password = $("#password").val();
    var saveData={"username":username,"password":password};
    $.ajax({
        url:"/login",
        type:"post",
        dataType:"json",
        data:JSON.stringify(saveData),
        contentType:"application/json;charset=UTF-8",
        success:function(result,testStatus)
        {
            token=result.data;
            setCookie("token",token);
            //alert(result.msg);
            if(result.code== "200"&& token!=""){
                // 存入token和username
                setCookie("token",token);
                setCookie("username",username)
                window.location.href="index.html";
            }else if(result.code== "401"){
                alert(result.msg);
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}
//登录回车事件
function enterSubmit(obj) {
    var button = document.getElementById('login');
    //enter按键的keyCode编码为13
    if (obj.keyCode == 13) {
        button.click();
    }
}

//input获取焦点
window.onload=function setFocus()
{
    document.getElementById('username').focus();
}