/*
* 登录时的异步请求
* 作者：lu
* */


/*
* 获取value后请求后台，若存储在此用户且密码正确，则登录成功
* */
var string=window.location.href;
var flag=string.split("=")[1];
console.log(flag);
if(flag=="1"){
    document.getElementById('one').style.display="none";
    document.getElementById('tow').style.display="block";

    document.getElementById('step2').style.borderColor="#3190e8";
    document.getElementById('step3').style.background="#3190e8";
    document.getElementById('step4').style.borderColor="#3190e8";
    document.getElementById('step5').style.background="#3190e8";
}
function loadXMLDoc()
{
    document.getElementById('step2').style.borderColor="#3190e8";
    document.getElementById('step3').style.background="#3190e8";

    var email = $("#Email").val();
    var username = $("#username").val();
    var saveData={"email":email,"username":username};
    $.ajax({
        url:"/cashsale/forgetPassword",
        type:"post",
        dataType:"json",
        headers:{
            contentType:"application/json;charset=UTF-8"
        },
        data:JSON.stringify(saveData),
        contentType:"application/json;charset=UTF-8",
        success:function(result,testStatus)
        {
            if(result.code == "200"){
                alert("请到邮箱完成验证！");
            }else if(result.code == "401"){
                alert("用户名或邮箱错误！");
            }else{
                alert("未知错误！");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}

function loadXMLDoc_1()
{
    var password = $("#password").val();
    var Cpassword = $("#Cpassword").val();
    var saveData={"password":password,"Cpassword":Cpassword};
    $.ajax({
        url:"/cashsale/setPassword",
        type:"post",
        dataType:"json",
        data:JSON.stringify(saveData),
        contentType:"application/json;charset=UTF-8",
        success:function(result,testStatus)
        {
            if(result.code == "200"){
                document.getElementById('step6').style.borderColor="#3190e8";
                document.getElementById('step7').style.background="#3190e8";
                window.location.href="login.html";
            }else{
                alert("设置密码失败！");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}