/*
* 链接商品详情
* 作者：lu
* */
// 点击猜你喜欢、最新商品调用
function goodsclick(str) {
    //goods为其子标签
    var goods=str.childNodes;
    // 获取第四个自类
    var productId=goods[3].value;
    // alert(productId);
    // 把id拼接到URL
    var string="goods.html?productId="+productId;

    //浏览商品事件
    var saveData = {"productId":productId,"scoreCode":"A"};
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
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！"+e+"\n"+errrorMessage);
        }
    });

    window.open(string);
}
/*搜索下拉提示*/
function mySearcfOnload() {
    Otitle = $(".mySearch").val();
    console.log(Otitle);

    var saveData={
        "suggest":{
            "prefix":Otitle,
            "completion":{
                "field":"title.suggest"
            }
        }
    };
    // $("#main").html("");
    $.ajax({
        url:"http://localhost:9200/cashsale3/doc/_search",
        type:"post",
        dataType:"json",
        data:JSON.stringify(saveData),
        contentType:"application/json;charset=UTF-8",
        headers:{
            contentType:"application/json;charset=UTF-8"
        },
        success:function(result,testStatus)
        {
            if(result.timed_out== "200"){
                data=result.suggest.my-suggest.options;
                if(data!=undefined&&data!=null&&data!="") {
                    innerTips(data);
                }
            }else{
                console.log("查询失败！");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}

function innerTips(data) {
    
}


/*轮播图*/
var play = document.getElementById("play");
//下一张
var next = document.getElementById("arrow_right");
//上一张
var prev = document.getElementById("arrow_left");
//按钮
next.onclick = function () {
    next_pic();
}
prev.onclick = function () {
    prev_pic();
}
function next_pic () {
    index++;
    if(index > 3){
        index = 0;
    }
    showCurrentDot();
    var newLeft;
    var String=document.defaultView.getComputedStyle(play).marginLeft+"";
    if(String === "-2100px"){
        newLeft = 0;
    }else{
        newLeft = parseInt(String.split("px")[0])-700;
        // console.log(newLeft);
    }
    play.style.marginLeft = newLeft + "px";
    console.log(">>>");
}
function prev_pic () {
    index--;
    console.log(index);
    if(index < 0){
        index = 3;
    }
    showCurrentDot();
    var newLeft;
    var String=document.defaultView.getComputedStyle(play).marginLeft+"";
    if(String === "0px"){
        newLeft = -2100;
    }else{
        newLeft = parseInt(String.split("px")[0])+700;
    }
    play.style.marginLeft = newLeft + "px";
    console.log("<<<");
}



//显示当前时间 ( setInterval() 函数会每秒执行一次函数，类似手表)。使用 clearInterval() 来停止执行
var timer = null;
function autoPlay () {
    timer = setInterval(function () {
        next_pic();
    },2500);
}
autoPlay();

// 鼠标悬停
var container = document.querySelector(".frame");
container.onmouseenter = function () {
    clearInterval(timer);
}
container.onmouseleave = function () {
    autoPlay();
}


//设置页数样式
var index = 0;
// var dots = document.getElementById("buttons").childNodes;
var dots = document.getElementById("buttons").getElementsByTagName("span");
console.log(dots);
function showCurrentDot () {
    for(var i = 0, len = dots.length; i < len; i++){
        dots[i].setAttribute("id", "");
    }
    dots[index].setAttribute("id","on");
}

//点击数字
function spanOnclick(s) {
    index=parseInt(s);
    play.style.marginLeft = -index*700+"px";
    showCurrentDot ();
}




