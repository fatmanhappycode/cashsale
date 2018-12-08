
/*切换背景*/
function myselfBG(s,s1,s2) {
    var img1 = document.getElementById("myself_back"+s);
    var img2 = document.getElementById("myself_back"+s1);
    var img3 = document.getElementById("myself_back"+s2);
    img1.style.opacity="1";
    img2.style.opacity="0";
    img3.style.opacity="0";
}

/*切换main*/
function selectCard(s,s1,s2,s3) {
    var card1 = document.getElementById("main"+s);
    var card2 = document.getElementById("main"+s1);
    var card3 = document.getElementById("main"+s2);
    var card4 = document.getElementById("main"+s3);
    card1.style.display="block";
    card2.style.display="none";
    card3.style.display="none";
    card4.style.display="none";

    var card1 = document.getElementById("card"+s);
    var card2 = document.getElementById("card"+s1);
    var card3 = document.getElementById("card"+s2);
    var card4 = document.getElementById("card"+s3);
    card1.style="color:#fff;background:rgba(107,199,176,.5);";
    card2.style="color:#000;background:#FFF;";
    card3.style="color:#000;background:#FFF;";
    card4.style="color:#000;background:#FFF;";
}

/*回到顶部显示与隐藏*/
$(window).scroll(function(){
    var winScrollHeight=$(window).scrollTop();//滚动条滚动距离
    if(winScrollHeight>=100){
        document.getElementById("test").style.display="block";
    }else{
        document.getElementById("test").style.display="none";
    }
});
test.onclick = function(){
    document.body.scrollTop = document.documentElement.scrollTop = 0;
}

/*判断是否查看购物车*/
var string=window.location.href;
var flag=string.split("=")[1];
if(flag=="goods"){
    selectCard('2','1','3','4');
}


// 点击商品调用
function goodsclick(str) {
    //goods为其子标签
    var goods=str.childNodes;
    // 获取第四个自类
    var productId=goods[3].value;
    // alert(productId);
    // 把id拼接到URL
    var string="goods.html?productId="+productId;

    window.open(string);
}