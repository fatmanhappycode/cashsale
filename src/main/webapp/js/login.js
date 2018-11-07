function loadXMLDoc()
{
	var username = $("#username").val();
	var password = $("#password").val();
	var saveData={"username":username,"password":password};
	$.ajax({
		url:"../img/jj.json",
		type:"post",
		dataType:"json",
		headers:{
			contentType:"application/json;charset=UTF-8",
		},
		data:JSON.stringify(saveData),
		contentType:"application/json",
		success:function(result,testStatus)
		{
			token=result.data;
			setCookie("token",token);
			if(result.code== "105"){
				
				//返回昵称用户名等用户信息
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