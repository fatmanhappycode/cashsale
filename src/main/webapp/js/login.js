function loadXMLDoc()
{
    var username = $("#username").val();
    var password = $("#password").val();
    setCookie("username",username);
    var saveData={"username":username,"password":password};

    $.ajax({
        url:"/login",
        type:"post",
        dataType:"json",
        data:JSON.stringify(saveData),
        contentType:"application/json",
        success:function(result,testStatus)
        {
            token=result.data;
            setCookie("token",token);
            //alert(result.msg);
            if(result.code== "105"&& token!=""){
                window.location.href="index.html";
            }else if(result.code== "106"){
                alert(result.msg);
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}
function enterSubmit(obj) {
    var button = document.getElementById('login');
    //enter按键的keyCode编码为13
    if (obj.keyCode == 13) {
        button.click();
    }
}