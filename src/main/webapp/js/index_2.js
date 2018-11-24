
/*
* 一些页面功能性函数
* 作者：lu
* */
//筛选价格输入框自动获取焦点样式
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
    // 已登录时，点击头像跳转到修改资料
    document.getElementById('userImg').setAttribute("href","#");
    // 已登录时显示退出按钮
    document.getElementById('exit').style="display:block;";
}

//退出登录
exit.onclick=function(){
    if(confirm("确定要退出吗？")){
        delCookie("username");
        // 清除cookie，然后刷新
        delCookie("token");
        //window.location.href="index.html";
        location.replace("index.html");
    }
}


//搜索回车事件
function enterSubmit(obj) {
    var button = document.getElementById('mySearch_img');
    //enter按键的keyCode编码为13
    if (obj.keyCode == 13) {
        button.click();
    }
}

//回到顶部图标显示与隐藏函数
$(window).scroll(function(){
    var winScrollHeight=$(window).scrollTop();//滚动条滚动距离
    if(winScrollHeight>=100){
        document.getElementById("test").style.display="block";
    }else{
        document.getElementById("test").style.display="none";
    }
});
//点击图标回到顶部id名加事件直接调用函数
test.onclick = function(){
    document.documentElement.scrollTop = 0;
}