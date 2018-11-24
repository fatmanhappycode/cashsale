/*
*注册ajax请求
*作者：lu
*/
var isLoading=false;
function clickregister() {
	//获取节点、值
	sp=document.getElementById("passwordItem1");
	var username = $("#username").val();
	var nickname = $("#nickname").val();
	var password = $("#password").val();
	var email = $("#email").val();
	if(!Verusername()||!E_mail()||!Verpassword()||!Verification())
	{
        alert("请填写完内容！");
		//如果没填完数据，则弹出指示
	}
	else
	{
		alert("请到邮箱进行验证");
        $("#register").html("注册中…");
		if(isLoading){
			alert("请不要重复点击");
			return;
		}else{
			isLoading=true;
		}
		//定义json串
		var saveData={"username":username,"nickname":nickname,"password":password,"email":email};
		$.ajax({
			url:"/register",
			type:"post",
			dataType:"json",
			data:JSON.stringify(saveData),
            headers: {
                ContentType: "application/json; charset=UTF-8"
            },
			success:function(result,testStatus)
			{
				if(result.code==200)
				{
					alert("激活成功，请登录！")
					window.location.href="login.html";
				}else{
					alert(result.msg);
				}
			},
			error:function(xhr,errrorMessage,e){
				alert("系统异常！请稍后重试。");
			}
		});
		isLoading=false;
	}
};