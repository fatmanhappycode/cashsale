/*
* 图片放大功能
* 作者：lu
* */
var all= document.getElementById("all");
var box = document.getElementById("box");
var smallBox = document.getElementById("smallBox");
var smallImg = document.getElementById("smallImg");
var bigImg = document.getElementById("bigImg");
var bigBox = document.getElementById("bigBox");
var mask = document.getElementById("mask");

smallBox.onmouseover = function(){
    bigBox.style.display = "block";
    mask.style.display = "block";
}

smallBox.onmouseout = function(){
    bigBox.style.display = "none";
    mask.style.display = "none";
}


document.onmousemove = function (event) {
    var event = event || window.event;
    //获取鼠标手移动到的x轴，y轴位置
    var pageX = event.pageX || event.clientX + document.documentElement.scrollLeft;
    var pageY = event.pageY || event.clientY + document.documentElement.scrollTop;

    var boxX = pageX - box.offsetLeft - all.offsetLeft;
    var boxY = pageY - box.offsetTop - all.offsetTop;

    //为了能以中心点移动，向左挪动遮罩层宽度的一半
    var targetX = boxX - mask.offsetWidth/2;
    var targetY = boxY - mask.offsetHeight/2;

    if (targetX < 0) {
        targetX = 0;
    }

    if (targetX > smallBox.offsetWidth - mask.offsetWidth) {
        targetX = smallBox.offsetWidth - mask.offsetWidth;
    }

    if(targetY < 0){
        targetY = 0;
    }

    if(targetY > smallBox.offsetHeight - mask.offsetHeight){
        targetY = smallBox.offsetHeight - mask.offsetHeight;
    }

    mask.style.left = targetX + "px";
    mask.style.top = targetY + "px";

    //按比例显示右侧图片
    //小遮罩层／小图片可移动范围 ＝ 大遮罩层／大图片可移动范围
    var smallMove = smallImg.offsetWidth - mask.offsetWidth;//250px
    var bigMove = bigImg.offsetWidth - bigBox.offsetWidth;//400px

    var smallMove_1 = smallImg.offsetHeight - mask.offsetHeight;//250px
    var bigMove_1 = bigImg.offsetHeight - bigBox.offsetHeight;//400px

    //算出比率，span移动1px bigImg移动rate倍
    var rate = bigMove/smallMove;
    var rate_1 =bigMove_1/smallMove_1;

    var bigtargetX = -rate*targetX;
    var bigtargetY = -rate_1*targetY;

    bigImg.style.left = bigtargetX + "px";
    bigImg.style.top = bigtargetY + "px";
}

if(smallImg.offsetWidth<smallImg.offsetHeight){
    smallImg.style.width="350px";
    smallImg.style.height="350px";
}else{
    smallImg.style.width="350px";
    smallImg.style.height="350px";
}
