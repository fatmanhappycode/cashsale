/*
* 认证ajax请求
* 作者：lu
* */
var isRight = false;
var mobileCode;
// 点击认证时调用
function f() {
    var code = $("#Xpassword").val();
    if(code != null && code != "") {
        if (code == mobileCode) {
            document.getElementById("verifyCodeIsNull").style.display='none';
            document.getElementById("verifyCodeItem").style.display='none';
            window.location.href = "verifysuccess.html";
        } else {
            document.getElementById("verifyCodeItem").style.display='block';
            document.getElementById("verifyCodeIsNull").style.display='none';
        }
    }else{
        document.getElementById("verifyCodeIsNull").style.display='block';
    }
}

//认证回车事件
function enterSubmit(obj) {
    var button = document.getElementById('confirm');
    //enter按键的keyCode编码为13
    if (obj.keyCode == 13) {
        button.click();
    }
}
//倒计时
var countdown=60;
function settime(val) {
    if (countdown == 0) {
        val.removeAttribute("disabled");
        val.value="获取验证码";
        countdown = 60;
        return false;
    } else {
        val.setAttribute("disabled", true);
        val.value="重新发送(" + countdown + ")";
        countdown--;
    }
    setTimeout(function() {
        settime(val);
    },1000);

}
function sendCode() {
    var username = $("#Xusername").val();
    var saveData={"username":username};

    $.ajax({
        url:"/cashsale/mobileConfirm",
        type:"get",
        dataType:"json",
        data:saveData,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            if(result.code== "200"){
                mobileCode = result.data;
            }else{
                isRight = false;
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}
//刷新input获取焦点
window.onload=function setFocus()
{
    document.getElementById('Xusername').focus();
    document.getElementById("Xusername").value= getCookie("username");
    $(function(){
        $('#Xusername').attr('disabled',true);
    });
}