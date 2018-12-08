/*
*注册页面功能性函数
*作者：lu
*/

//表单有空提示语
//移入提示
function Ver(){
	var sp=document.getElementById("passwordItem1");
	var username = $("#username").val();
	var nickname = $("#nickname").val();
	var password = $("#password").val();
	var Cpassword = $("#Cpassword").val();
	if(username==""||username==null||nickname==""||nickname==null||password==""||password==null||Cpassword==""||Cpassword==null)
	{
		sp.style.display='block';
		return false;
	}else{
		sp.style.display='none';
		return true;
	}
}
//移出消失
function Ver_1(){
	var sp=document.getElementById("passwordItem1");
	sp.style.display='none';
}
//密码不同提示语
function Verification() {
	var cPassword = $("#Cpassword").val();
    var password = $("#password").val();
    if(cPassword == "" || cPassword == null) {
        document.getElementById("passwordItem").style.display='none';
        return false;
    }else if(cPassword.length >= password.length){
        var sp = document.getElementById("passwordItem");
        if (password != cPassword) {
            sp.style.display = 'block';
            return false;
        }
        else {
            sp.style.display = 'none';
            return true;
        }
    }else {
        $("#Cpassword").blur(function () {
            var sp = document.getElementById("passwordItem");
            var password = $("#password").val();
            var Cpassword = $("#Cpassword").val();
            if (password != Cpassword) {
                sp.style.display = 'block';
                return false;
            }
            else {
                sp.style.display = 'none';
                return true;
            }
        });
	}
}
//密码输入验证
function Verpassword() {
	var password = $("#password").val();
    if(password == "" || password == null) {
        document.getElementById("passwordItem3").style.display='none';
        return false;
    }else if(password.length > 16) {
        var pattern = new RegExp("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{6,16}$");
        var pattern1 = new RegExp("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
        if (pattern.test(password)) {
            document.getElementById("passwordItem3").style.display = 'none';
            return true;
        }
        else {
            document.getElementById("passwordItem3").style.display = 'block';
            return false;
        }
    }else{
        $("#password").blur(function () {
            var pattern = new RegExp("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{6,16}$");
            var pattern1 = new RegExp("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
            if (pattern.test(password)) {
                document.getElementById("passwordItem3").style.display = 'none';
                return true;
            }
            else {
                document.getElementById("passwordItem3").style.display = 'block';
                return false;
            }
        });
	}
}
//邮箱验证
function E_mail() {
	Verification();
	var reg = new RegExp("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$"); 
	var email = $('#email').val();
    if(email == "" || email == null) {
        document.getElementById("passwordItem2").style.display='none';
        return false;
    }else {
        $("#email").blur(function () {
            if (email == '') {
                return false;
            } else if (!reg.test(email)) {
                document.getElementById("passwordItem2").style.display = 'block';
                return false;
            }
            else {
                document.getElementById("passwordItem2").style.display = 'none';
                return true;
            }
        });
    }
}

//手机输入验证
function Verusername() {
	var username = $("#username").val();
	//表示以1开头，第二位可能是3/4/5/7/8等的任意一个，在加上后面的\d表示数字[0-9]的9位
	//var pattern = new RegExp("^1[34578]\d{9}$");
    if(username == "" || username == null) {
        document.getElementById("passwordItem4").style.display='none';
        return false;
    }else if(/^1[34578]\d{9}$/.test(username)){
        document.getElementById("passwordItem4").style.display='none';
        return true;
    }else {
        document.getElementById("passwordItem4").style.display = 'block';
    }
}
//昵称输入验证
function Vernickname() {
	var password = $("#nickname").val();
	//表示以1开头，第二位可能是3/4/5/7/8等的任意一个，在加上后面的\d表示数字[0-9]的9位
	
}
//清除Item
function clearItem() {
	var username = $("#username").val();
	var nickname = $("#nickname").val();
	var password = $("#password").val();
	var Cpassword = $("#Cpassword").val();
	if(username==""&&username==null&&nickname==""&&nickname==null&&password==""&&password==null&&Cpassword==""&&Cpassword==null)
	{
		document.getElementById("passwordItem").style.display='none';
		document.getElementById("passwordItem1").style.display='none';
		document.getElementById("passwordItem2").style.display='none';
		document.getElementById("passwordItem3").style.display='none';
		document.getElementById("passwordItem4").style.display='none';
		document.getElementById("passwordItem5").style.display='none';
	}
}