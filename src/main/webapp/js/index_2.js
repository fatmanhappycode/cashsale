
//筛选自动获取焦点样式
function setFocus1()
{
    document.getElementById('price1').focus();
}
function setFocus2()
{
    document.getElementById('price2').focus();
}

//已登录时，ost内容
if(getCookie("usernme")!=""&&getCookie("username")!=null) {
    $("#username").html(getCookie("username"));
    document.getElementById('userImg').setAttribute("title","点击修改资料");
    document.getElementById('userImg').setAttribute("href","#");
    document.getElementById('exit').style="display:block;";
}

//退出登录
exit.onclick=function(){
    if(confirm("确定要退出吗？")){
        delCookie("username");
        //window.location.href="index.html";
        location.replace("index.html");
    }
}


//回车事件
function enterSubmit(obj) {
    var button = document.getElementById('mySearch_img');
    //enter按键的keyCode编码为13
    if (obj.keyCode == 13) {
        button.click();
    }
}

//回到顶部函数
$(window).scroll(function(){
    var winScrollHeight=$(window).scrollTop();//滚动条滚动距离
    if(winScrollHeight>=100){
        document.getElementById("test").style.display="block";
    }else{
        document.getElementById("test").style.display="none";
    }
});
//id名加事件直接调用函数
test.onclick = function(){
    document.documentElement.scrollTop = 0;
}