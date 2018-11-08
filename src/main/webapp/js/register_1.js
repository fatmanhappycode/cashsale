var isLoading=false;
function clickregister() {
	//获取节点、值
	sp=document.getElementById("passwordItem1");

	var username = $("#username").val();
	var nickname = $("#nickname").val();
	var password = $("#password").val();
	var email = $("#email").val();
	if(!Verusername()||!E_mail()||!Verpassword()||!Verification()||!Ver())
	{
		//如果没填完数据，则弹出指示
		sp.style.display='block';
	}
	else
	{
		if(isLoading){
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
				if(result.code==101)
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