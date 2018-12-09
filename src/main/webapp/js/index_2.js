
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
    document.getElementById('userImg').setAttribute("href","personalData.html");
    document.getElementById('userImg').setAttribute("target","_self");
    // 已登录时显示退出按钮
    document.getElementById('exit').style="display:block;";
    document.getElementById('deploy').style="display:block;";
}

//退出登录
exit.onclick=function(){
    if(confirm("确定要退出吗？")){
        //清除所有cookie函数
        var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
        if(keys) {
            for(var i = keys.length; i--;)
                document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString()
        }
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
        document.getElementById("testB").style.display="block";
    }else{
        document.getElementById("testB").style.display="none";
    }
});
//点击图标回到顶部  id名加事件直接调用函数
testB.onclick = function(){
    document.documentElement.scrollTop = 0;
}




//显示当前时间 ( setInterval() 函数会每秒执行一次函数，类似手表)。使用 clearInterval() 来停止执行
var timer1 = null;
var isLaoding1=false;
function imgPlay(str) {
    if(isLaoding1){
        return ;
    }else {
        isLaoding1=true;
    }
    p=str;
    window.setTimeout("imgPlay_1('45',p)",0);
    window.setTimeout("imgPlay_1('90',p)",500);
    window.setTimeout("imgPlay_1('135',p)",1000);
    window.setTimeout("imgPlay_1('180',p)",1500);
    window.setTimeout("imgPlay_1('225',p)",2000);
    window.setTimeout("imgPlay_1('270',p)",2500);
    window.setTimeout("imgPlay_1('315',p)",3000);
    window.setTimeout("imgPlay_1('360',p)",3500);
    timer1=setInterval(function () {
        p=str;
        window.setTimeout("imgPlay_1('45',p)",0);
        window.setTimeout("imgPlay_1('90',p)",500);
        window.setTimeout("imgPlay_1('135',p)",1000);
        window.setTimeout("imgPlay_1('180',p)",1500);
        window.setTimeout("imgPlay_1('225',p)",2000);
        window.setTimeout("imgPlay_1('270',p)",2500);
        window.setTimeout("imgPlay_1('315',p)",3000);
        window.setTimeout("imgPlay_1('360',p)",3500);
    },4000);
}
function noimgPlay() {
    clearInterval(timer1);
    isLaoding1=false;
}
function imgPlay_1(s,s1) {
    if(s==45){
        s1.style="-moz-transform:rotate(45deg);-webkit-transform:rotate(45deg);transform:rotate(45deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=0.5);"
    }else if(s==90){
        s1.style="-moz-transform:rotate(90deg);-webkit-transform:rotate(90deg);transform:rotate(90deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=1);"
    }else if(s==135){
        s1.style="-moz-transform:rotate(1350deg);-webkit-transform:rotate(1350deg);transform:rotate(1350deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=1.5);"
    }else if(s==180){
        s1.style="-moz-transform:rotate(180deg);-webkit-transform:rotate(180deg);transform:rotate(180deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=2);"
    }else if(s==225){
        s1.style="-moz-transform:rotate(225deg);-webkit-transform:rotate(225deg);transform:rotate(225deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=2.5);"
    }else if(s==270){
        s1.style="-moz-transform:rotate(270deg);-webkit-transform:rotate(270deg);transform:rotate(270deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=3);"
    }else if(s==315){
        s1.style="-moz-transform:rotate(315deg);-webkit-transform:rotate(315deg);transform:rotate(315deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=3.5);"
    }else if(s==360){
        s1.style="-moz-transform:rotate(360deg);-webkit-transform:rotate(360deg);transform:rotate(360deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=4);"
    }
/*
    if(s==45){
        s1.style="-moz-transform:rotate(45deg);-webkit-transform:rotate(45deg);transform:rotate(45deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=0.5);transition-duration:0.2s;"
    }else if(s==90){
        s1.style="-moz-transform:rotate(90deg);-webkit-transform:rotate(90deg);transform:rotate(90deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=1);transition-duration:0.2s;"
    }else if(s==135){
        s1.style="-moz-transform:rotate(1350deg);-webkit-transform:rotate(1350deg);transform:rotate(1350deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=1.5);transition-duration:0.2s;"
    }else if(s==180){
        s1.style="-moz-transform:rotate(180deg);-webkit-transform:rotate(180deg);transform:rotate(180deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=2);transition-duration:0.2s;"
    }else if(s==225){
        s1.style="-moz-transform:rotate(225deg);-webkit-transform:rotate(225deg);transform:rotate(225deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=2.5);transition-duration:0.2s;"
    }else if(s==270){
        s1.style="-moz-transform:rotate(270deg);-webkit-transform:rotate(270deg);transform:rotate(270deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=3);transition-duration:0.2s;"
    }else if(s==315){
        s1.style="-moz-transform:rotate(315deg);-webkit-transform:rotate(315deg);transform:rotate(315deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=3.5);transition-duration:0.2s;"
    }else if(s==360){
        s1.style="-moz-transform:rotate(360deg);-webkit-transform:rotate(360deg);transform:rotate(360deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=4);transition-duration:0.2s;"
    }*/
}

/*
* function imgPlay(str) {
    p=str;
    window.setTimeout("imgPlay_1(p)",0);
}
function imgPlay_1(s1) {
    for(var i=0;;i++){
        if(i==360){
            i=0;
        }
        s1.style="-moz-transform:rotate("+i+"deg);-webkit-transform:rotate("+i+"deg);transform:rotate("+i+"deg);filter:progid:DXImageTransform.Microsoft.BasicImage(rotation="+(i/90)+");";
        s1.onmouseout=function () {
            return ;
            break;
        }
    }
}
* */




// 公告
testC.onclick = function(){
    document.getElementById("noticeDiv").style.display="block";
}
function closeNoticeX(){
    document.getElementById("noticeDiv").style.display="none";
}

if(getCookie("code")!=undefined&&getCookie("code")!=""&&getCookie("code")!=null){
    document.getElementById("testA").style.display="block";
}
function getnotice() {
    $.ajax({
        url:"/cashsale/searchDemand",
        type:"get",
        headers:{
            contentType:"application/json;charset=UTF-8"
        },
        contentType:"application/json",
        success:function(result,testStatus)
        {
            if(result.code==200){
                var dataQ=result.data;
                innernotice(dataQ)
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}
function innernotice(dataQ) {
    var img = document.getElementById('noticeImg');
    var h4= document.getElementById('noticeH4');
    var p= document.getElementById('noticeP');
    var id= document.getElementById('noticeId');

    img.src = dataQ.imgUrl;
    p.innerHTML=dataQ.title;
    h4.innerHTML=dataQ.organization;
    // id.setAttribute("value",dataQ.demandId);
    id.value=dataQ.demandId;
}
function goodsnotice() {
    //goods为其子标签
    var demandId=$("#noticeId").val();
    // alert(demandId);
    // 把id拼接到URL
    var string="goods.html?demandId="+demandId;

    window.open(string);
}