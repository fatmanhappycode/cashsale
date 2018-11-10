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
            //alert(result.msg);
            if(result.code== "105"&& token!=""){
                setCookie("token",token);
                window.location.href="index.html"+"?username="+username;
            }else if(result.code== "106"){
                alert(result.msg);
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}