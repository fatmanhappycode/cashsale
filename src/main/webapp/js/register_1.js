
$(".register").one("click",
function register() {
	//获取节点、值
	sp=document.getElementById("passwordItem1");
	var username = $("#username").val();
	var nickname = $("#nickname").val();
	var password = $("#password").val();
	var email = $("#email").val();
	var Cpassword = $("#Cpassword").val();
	if(username==""||username==null||nickname==""||nickname==null||password==""||password==null||Cpassword==""||Cpassword==null)
	{
		//如果没填完数据，则弹出指示
		sp.style.display='block';
	}
	else
	{
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
				if(result.code==110)
				{
					//转跳到页面
					alert("该邮箱已被注册！");
					window.location.href="login.html";
				} else if (result.code == 102) {
					alert("注册失败");
				} else if(result.code==103){
					alert("该手机号已被注册！");
				} else if(result.code==109) {
					alert("请到邮箱进行账号激活")
				}
				else{
					alert("注册失败！");
				}
			},
			error:function(xhr,errrorMessage,e){
				alert("系统异常！请稍后重试。");
			}
		});
	}
});