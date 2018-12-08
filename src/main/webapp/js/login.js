/*
* 登录时的异步请求
* 作者：lu
* */

// 点击登录时调用
var verify = false;
/*
* 获取value后请求后台，若存储在此用户且密码正确，则登录成功
* */
function loadXMLDoc()
{
    var username = $("#username").val();
    var password = $("#password").val();
    var saveData={"username":username,"password":password};
    if(verify == true) {
        $.ajax({
            url:"/cashsale/login",
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
                }else if(result.code== "401") {
                    alert(result.msg);
                    changeVerifyCode();
                    $("#verifyCode").val('');
                    $("#password").val('');
                }else if(result.code=="201"){
                    // 存入token和username
                    setCookie("code","true");
                    setCookie("token",token);
                    setCookie("username",username)
                    window.location.href="index.html";
                }
            },
            error: function (xhr, errrorMessage, e) {
                alert("系统异常！");
            }
        });
    }else if($("#verifyCode").val() == null || $("#verifyCode").val() == ""){
        document.getElementById("verifyCodeIsNull").style.display='block';
        document.getElementById("verifyCodeItem").style.display='none';
    }else{
        document.getElementById("verifyCodeItem").style.display='block';
        document.getElementById("verifyCodeIsNull").style.display='none';
    }
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

//点击切换验证码
function changeVerifyCode(){
    $("#verifyImg").attr("src","Kaptcha.jpg?"+Math.floor(Math.random()*100));
    var verifyCodeValue = $("#verifyCode").val('');
    document.getElementById("verifyCodeItem").style.display='none';
    document.getElementById("verifyCodeIsNull").style.display='none';
}

//验证验证码
function verCode() {
    var verifyCodeValue = $("#verifyCode").val();
    if(verifyCodeValue == "" || verifyCodeValue == null) {
        document.getElementById("verifyCodeItem").style.display='none';
        document.getElementById("verifyCodeIsNull").style.display='block';
        verify = false;
    }else {
        document.getElementById("verifyCodeItem").style.display = 'none';
        document.getElementById("verifyCodeIsNull").style.display = 'none';
        $.ajax({
            type: "GET",
            url: "/cashsale/verifyCode?verifyCode=" + verifyCodeValue,
            success: function (returnData) {
                if (returnData == "200") {
                    document.getElementById("verifyCodeItem").style.display = 'none';
                    document.getElementById("verifyCodeIsNull").style.display = 'none';
                    verify = true;
                } else {
                    document.getElementById("verifyCodeItem").style.display = 'block';
                    document.getElementById("verifyCodeIsNull").style.display = 'none';
                    verify = false;
                }
            },
            error: function (e) {
                alert(e);
            }
        });
    }
}