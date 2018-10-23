function Ver(){
	sp=document.getElementById("passwordItem1");
	var username = $("#username").val();
	var nickname = $("#nickname").val();
	var password = $("#password").val();
	var Cpassword = $("#Cpassword").val();
	if(username==""||username==null||nickname==""||nickname==null||password==""||password==null||Cpassword==""||Cpassword==null)
	{
		sp.style.display='block';
	}else{
		sp.style.display='none';
	}
}
function Verification() {
	sp=document.getElementById("passwordItem");
	var password = $("#password").val();
	var Cpassword = $("#Cpassword").val();
	if(password!=Cpassword){
		sp.style.display='block';
	}
	else{
		sp.style.display='none';
	}
}
