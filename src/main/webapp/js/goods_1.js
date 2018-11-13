//获取容器
var smallImg = document.getElementById("smallImg");
var bigImg = document.getElementById("bigImg");
var title = document.getElementById("title");
var price1 = document.getElementById("price1");
var price2 = document.getElementById("price2");
var getGoods = document.getElementById("getGoods");
var Description = document.getElementById("Description");
// 加入内容
smallImg.innerHTML=getCookie("imageUrl");
bigImg.innerHTML=getCookie("imageUrl");
title.innerHTML=getCookie("title");
price1.innerHTML="价格：￥"+getCookie("price");
if(getCookie("isBargain")=="1"){
    price2.innerHTML="可否议价：可议价";
}else if(getCookie("isBargain")=="0"){
    price2.innerHTML="可否议价：不可议价";
}
if(getCookie("tradeMethod")=="1"){
    getGoods.innerHTML="交货方式：上门自提";
}else if(getCookie("tradeMethod")=="0"){
    getGoods.innerHTML="交货方式：送货上门";
}
// 富文本编辑器加入内容
Description.innerHTML+="更多内容："+"<br>"+getCookie("pdDescription");

// 用户名 发布者
if(getCookie("goodsUsername")=="undefined"){
    $("#username").html(getCookie("goodsUsername"));
}else{
    $("#username").html("匿名");
}
var allimg=document.getElementsByTagName("img");
for(var i=0;i<allimg.length;i++){
    /*
    var str=allimg[0].getAttribute("alt");
    alert(str);
    str=str.split("");
    alert(allimg[i].getAttribute("alt")+"\n"+allimg[0].getAttribute("src")+"\n"+str[0]);
    */
    if(allimg[i].getAttribute("alt")!=null){
        allimg[i].style="width:16px;height:16px;"
    }
}
// 点击关注按钮
city.onclick= function(){
    $("#city").val("已关注");
}