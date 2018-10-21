

function register() {
	//获取节点、值
	sp=document.getElementById("passwordItem1");
	var username = $("#username").val();
	var nickname = $("#nickname").val();
	var password = $("#password").val();
	var Cpassword = $("#Cpassword").val();
	if(username==""||username==null||nickname==""||nickname==null||password==""||password==null||Cpassword==""||Cpassword==null)
	{
		//如果没填完数据，则弹出指示
		sp.style.display='block';
	}
	else
	{
		//定义json串
		var saveData={"username":username,"nickname":nickname,"password":password};
		$.ajax({
			url:"",
			type:"post",
			dataType:"json",
			data:JSON.stringify(saveData),
			contentType:"application/json",
			success:function(result,testStatus)
			{
				if(result.code==101)
				{
					//转跳到页面
					alert("注册成功，请登录！");
					window.location.href="login.html";
				}
				else if(result.code==103){
					alert("用户已存在，注册失败！");
				}else{
					alert("注册失败！");
				}
			},
			error:function(xhr,errrorMessage,e){
				alert("系统异常！请稍后重试。");
			}
		});
	}
}