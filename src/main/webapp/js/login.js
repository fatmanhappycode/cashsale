

function loadXMLDoc()
{

    /*
        str=document.getElementById("form-divv").value;
        var xmlhttp;
      if (str.length==0)
      {
        document.getElementById("form-divv").innerHTML="";
        return;
      }
      //创建对象
      if (window.XMLHttpRequest)
      {
        // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlhttp=new XMLHttpRequest();
      }
      else
      {
        // IE6, IE5 浏览器执行代码
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
      }




      //定义回调函数
      xmlhttp.onreadystatechange=function()
      {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {alert('aaa');
          document.getElementById("form-divv").innerHTML=xmlhttp.responseText;
        }
      }
      xmlhttp.open("post","http://localhost:8080/login",true);
      xmlhttp.setRequestHeader()
      xmlhttp.send("username="+"abc"&"password="+"123");

      */



    /*
    var username = $("#username").val();
    var password = $("#password").val();
    alert("bbb"+username+"bbb");
    $.getJson(
            "http://localhost:8080/login",
            {"username":username,"password":password},
            function (result)
            {
                alert("bbb"+result.username+"bbb");
                if(result.password== "123"  &&  result.username== "abc"){
                    alert("登陆成功");
                }else{
                    alert("密码或用户名错误");
                }
            }
    );

    */

    var username = $("#username").val();
    var password = $("#password").val();
    var saveData={"username":username,"password":password};

    $.ajax({
        url:"login",
        type:"post",
        dataType:"json",
        data:JSON.stringify(saveData),
        contentType:"application/json",
        success:function(result,testStatus)
        {
            alert(result.msg+""+result.code+result.data);
            if(result.password== "123"  &&  result.username== "abc"){
                alert("已存在！注册失败！");
            }else{
                alert("注册成功！");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }


    });

}