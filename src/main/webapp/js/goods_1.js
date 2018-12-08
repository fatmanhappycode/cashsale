/*
* 渲染页面内容
* 作者：lu
* */

//获取容器
var smallImg = document.getElementById("smallImg");
var bigImg = document.getElementById("bigImg");
var title = document.getElementById("title");
var price1 = document.getElementById("price1");
var price2 = document.getElementById("price2");
var getGoods = document.getElementById("getGoods");
var Description = document.getElementById("Description");
var noticeP = document.getElementById("noticeP");
// 加载时加入内容
window.onload =function loadgoods() {

    var string=window.location.href;
    var str=string.split("?")[1];
    var s=str.split("=")[0];
    productId=s.split("=")[1];
    if(s=="demandId"){
        getDemand();
        return ;
    }
    var saveData={"productId":productId};
    // alert(productId);
    $.ajax({
        url:"/cashsale/GetDetailProduct",
        type:"get",
        headers:{
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            if(result.code==200){

                data=result.data;
                goodsUsername=data.username;
                setCookie("goodsUsername",data.username);


                var likeNumber = document.getElementById("likeNumber");
                var shareNumber = document.getElementById("shareNumber");
                likeNumber.innerHTML=result.data.likeNumber;
                shareNumber.innerHTML=result.data.shareNumber;

                // 加入内容
                smallImg.setAttribute("src",data.imageUrl)
                bigImg.setAttribute("src",data.imageUrl);
                title.innerHTML=data.title;
                price1.innerHTML="价格：￥"+data.price;
                if(data.isBargain=="1"){
                    price2.innerHTML="可否议价：可议价";
                }else if(data.isBargain=="0"){
                    price2.innerHTML="可否议价：不可议价";
                }
                if(data.tradeMethod=="1"){
                    getGoods.innerHTML="交货方式：上门自提";
                }else if(data.tradeMethod=="0"){
                    getGoods.innerHTML="交货方式：送货上门";
                }
                if(data.pdDescription!=undefined&&data.pdDescription!=null){
                    // 富文本编辑器加入内容
                    Description.innerHTML+="更多内容："+"<br>"+data.pdDescription;
                }else{
                    Description.innerHTML+="更多内容："+"<br>"+"该卖家很佛系，没有发布更多内容";
                }

                // 用户名 发布者
                if(data.username!="undefined"||data.username!=""||data.username!=null){
                    $("#username").html(data.username);
                    var goodsUserName = getCookie("goodsUsername");

                    //判断是否是不能收藏的人
                    if(data.username==getCookie("username")){
                        document.getElementById("chat").disabled=true;
                        document.getElementById("JOshopping").disabled=true;

                        document.getElementById("lichat").style.display="none";
                        document.getElementById("liJOshopping").style.display="none";
                        document.getElementById("Lidelete").style.display="block";
                    }
                }else{
                    $("#username").html("匿名");
                }

            }else{
                console.log(result.msg);
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });

}
// 渲染需求内容
function getDemand(){
    var saveData={"productId":productId};
    // alert(productId);
    $.ajax({
        url:"/cashsale/searchDemand",
        type:"get",
        headers:{
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            if(result.code==200){

                data=result.data;
                goodsUsername=data.username;
                setCookie("goodsUsername",data.username);


                // 加入内容
                smallImg.setAttribute("src",data.imageUrl)
                bigImg.setAttribute("src",data.imageUrl);
                title.innerHTML=data.organization;
                noticeP.innerHTML=data.title;
                $("#chat").val("联系组织");
                $("#delete").val("撤销此发布");
                document.getElementById("noticeP").style.display="block";
                if(data.pdDescription!=undefined&&data.pdDescription!=null){
                    // 富文本编辑器加入内容
                    Description.innerHTML+="更多内容："+"<br>"+data.pdDescription;
                }else{
                    Description.innerHTML+="更多内容："+"<br>"+"该卖家很佛系，没有发布更多内容";
                }

                // 用户名 发布者
                if(data.username!="undefined"||data.username!=""||data.username!=null){
                    $("#username").html(data.username);

                    //判断是否是不能收藏的人
                    if(data.username==getCookie("username")){
                        document.getElementById("chat").disabled=true;
                        document.getElementById("JOshopping").disabled=true;

                        document.getElementById("lichat").style.display="none";
                        document.getElementById("liJOshopping").style.display="none";
                        document.getElementById("Lidelete").style.display="block";
                    }
                }else{
                    $("#username").html("匿名");
                }

            }else{
                console.log(result.msg);
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}


/*var allimg=document.getElementsByTagName("img");
for(var i=0;i<allimg.length;i++){
    if(allimg[i].getAttribute("alt")!=null){
        allimg[i].style="width:16px;height:16px;"
    }
}*/
// 点击关注按钮
city.onclick= function(){
    $("#city").val("已关注");
}

// 点击加入购物车按钮
JOshopping.onclick=function () {
    var saveData = {"productId":window.location.href.split("=")[1],"scoreCode":"C"};
    $.ajax({
        url:"/cashsale/score",
        type:"post",
        dataType:"json",
        data:JSON.stringify(saveData),
        contentType:"application/json;charset=UTF-8",
        headers:{
            contentType:"application/json;charset=UTF-8"
        },
        success:function(result,testStatus)
        {
            if (result.code == "200") {
                $("#JOshopping").val("已加入购物车");
                var JOshopping = document.getElementById("JOshopping");
                JOshopping.setAttribute("style","background-color:#da7370ad");
            } else if (result.code == "401") {
                alert("加入失败");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！"+e+"\n"+errrorMessage);
        }
    });
}

//是否已经加入购物车
isInTrolley();
function isInTrolley() {
    var saveData = {"productId":window.location.href.split("=")[1]};
    $.ajax({
        url:"/cashsale/isInTrolley",
        type:"get",
        headers:{
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            if (result.code == "401") {
                $("#JOshopping").val("已加入购物车");
                var JOshopping = document.getElementById("JOshopping");
                JOshopping.setAttribute("style","background-color:#da7370ad");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！"+e+"\n"+errrorMessage);
        }
    });
}



/*
var userName = document.getElementById("username").value;
var goodsUserName = getCookie("goodsUsername");
alert(userName==goodsUserName);
alert("\n"+userName+"\n"+goodsUserName);
if(userName==goodsUserName){
    document.getElementById("chat").style="disabled='disabled';";
    document.getElementById("JOshopping").style="disabled='disabled';";
}*/
