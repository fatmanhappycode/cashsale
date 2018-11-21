//data=[{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 223,"imageUrl": "www.abc.com","isBargain": 12,"label": "223","title": "b2c"}];

var flag="";
isLoading=false;
//定义全局变量currentPage，方便下拉时使用
currentPage="1";
//定义全局变量title，方便下拉时使用
var title;

//猜你喜欢
function whatULike() {
    var saveData={"username":getCookie("username")};
    var play = document.getElementById("play");
    $.ajax({
        url:"/recommend",
        type:"get",
        headers:{
            "token":token,
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            data1=result.data.data;
            whatlike(data1);
            if(data1==""||data1==null||data1==undefined){
                whatlike(data);
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}
//渲染猜你喜欢
function whatlike(data) {
    $.each(data,function(index,obj){
        //获取容器
        var play = document.getElementById("play");
        //生成盒子
        var likeGoods= document.createElement('div');
        var smallImg = document.createElement('img');
        var mytitle= document.createElement('ul');
        var li1= document.createElement('li');
        var li2= document.createElement('li');
        var li3= document.createElement('li');
        var li4= document.createElement('li');

        //设置属性
        likeGoods.setAttribute("class", "likeGoods");
        likeGoods.setAttribute("onclick", "goodsclick(this)");
        smallImg.setAttribute("id", "smallImg");
        mytitle.setAttribute("id", "mytitle");
        li1.setAttribute("id", "liketitle");
        li2.setAttribute("id", "likeprice1");
        li3.setAttribute("id", "likeprice2");
        li4.setAttribute("id", "likegetGoods");


        // 加入内容
        smallImg.src = obj.imageUrl;
        li1.innerHTML=obj.title;
        li2.innerHTML="价格：￥"+obj.price;
        if(obj.isBargain=="1"){
            li3.innerHTML="可否议价：可议价";
        }else if(obj.isBargain=="0"){
            li3.innerHTML="可否议价：不可议价";
        }
        if(obj.tradeMethod=="1"){
            li4.innerHTML="交货方式：上门自提";
        }else if(obj.tradeMethod=="0"){
            li4.innerHTML="交货方式：送货上门";
        }

        mytitle.appendChild(li1);
        mytitle.appendChild(li2);
        mytitle.appendChild(li3);
        mytitle.appendChild(li4);
        likeGoods.appendChild(smallImg);
        likeGoods.appendChild(mytitle);
        play.appendChild(likeGoods);

        //商品id
        var goodsId= document.createElement('input');
        var goodsId1= document.createElement('input');
        goodsId.setAttribute("id","goodsId");
        // goodsId.setAttribute("type","hidden");
        goodsId.setAttribute("value",obj.productId);
        likeGoods.appendChild(goodsId1);
        likeGoods.appendChild(goodsId);
    });
}
function innerGoods(data) {
    $.each(data,function(index,obj){
        var main = document.getElementById("main");
        var goods= document.createElement('div');
        var img = document.createElement('img');
        var h4= document.createElement('h4');
        var p= document.createElement('p');

        goods.setAttribute("class", "goods");
        goods.setAttribute("onclick", "goodsclick(this)");

        h4.setAttribute("class", "myH");
        p.setAttribute("class", "price");
        img.src = obj.imageUrl;
        h4.innerHTML=obj.title;
        p.innerHTML="￥"+obj.price;
        goods.appendChild(img);
        goods.appendChild(h4);
        goods.appendChild(p);
        main.appendChild(goods);
        //商品id
        var goodsId= document.createElement('input');
        goodsId.setAttribute("id","goodsId");
        goodsId.setAttribute("type","hidden");
        goodsId.setAttribute("value",obj.productId);
        goods.appendChild(goodsId);
    });
}

//是否验证
function innerConfirm(data) {
    var isConfirm = document.getElementById("Confirm");
    if (data == "1") {
        isConfirm.innerHTML = "已认证";
        var img = document.createElement('img');
        img.src = "../img/confirm.png";
        var confirm = document.getElementById("isConfirm");
        confirm.appendChild(img);
    } else {
        isConfirm.innerHTML = "未认证";
        var img = document.createElement('img');
        img.src = "../img/noconfirm.png";
        var confirm = document.getElementById("isConfirm");
        confirm.appendChild(img);
    }
}

//加载时返回最新商品
window.onload =function init() {
    currentPage="1";
    token='';
    flag="";
    whatULike();
    var saveData={"time":"desc","currentPage":currentPage};
    $("#main").html("");
    $.ajax({
        url:"/searchByTime",
        type:"get",
        headers:{
            "token":token,
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            currentPage=result.data.currentPage;
            console.log("页数："+currentPage);
            data=result.data.data;
            //渲染
            innerGoods(data);
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！"+e+"\n"+errrorMessage);
        }
    });
    ismessage();
    var saveData2 = {"username":getCookie("username")}
    $.ajax({
        url:"/GetUserData",
        type:"get",
        headers:{
            "token":token,
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData2,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            var username=result.data;
            //渲染
            innerConfirm(username);
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！"+e+"\n"+errrorMessage);
        }
    });


    var oDiv = document.getElementById("species"),
        H = 0,
        Y = oDiv
    i=0;
    while (Y) {
        H += Y.offsetTop;
        if(i==0){
            H=H+200;
        }
        Y = Y.offsetParent;
        i++;
    }
    window.onscroll = function()
    {
        var s = document.body.scrollTop || document.documentElement.scrollTop
        if(s+280>H) {
            oDiv.style = "position:fixed;top:17px;"
        } else {
            oDiv.style = ""
        }
    }


}
function IsInload() {
    token='';
    if(currentPage==""){
        alert("没有更多数据！");
        return;
    }
    var saveData={"time":"asc","currentPage":currentPage};
    $.ajax({
        url:"/searchByTime",
        type:"get",
        headers:{
            "token":token,
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            currentPage=result.data.currentPage;
            console.log("页数："+currentPage);
            data=result.data.data;
            if(data==""){
                alert("没有更多数据！");
                return;
            }
            //渲染
            innerGoods(data);
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}



//搜索时调用
function loadXMLDoc()
{
    title = $(".mySearch").val();
    if(title==""){
        //无内容时刷新页面
        location.reload();
        return;
    }
    //将flag赋值为search
    flag="search";
    token=getCookie("token");
    currentPage="1";
    var saveData={"title":title,"currentPage":currentPage};
    $("#main").html("");
    $.ajax({
        url:"/search",
        type:"get",
        headers:{
            "token":token
        },
        data:saveData,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            currentPage=result.data.currentPage;
            console.log("页数："+currentPage);
            data=result.data.data;
            if(result.code== "200"){
                $(window).scrollTop(250);
                innerGoods(data);
                document.documentElement.scrollTop = 800;
            }else{
                console.log("查询失败！");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}
//下拉时currentPage不为0或空
function loadXMLDoc_1()
{
    token=getCookie("token");
    console.log(currentPage);
    var saveData={"title":title,"currentPage":currentPage};
    $.ajax({
        url:"/search",
        type:"get",
        headers:{
            "token":token,
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            currentPage=result.data.currentPage;
            console.log("页数："+currentPage);
            data=result.data.data;
            if(result.code== "200"){
                innerGoods(data);
            }else{
                console.log("查询失败！");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}

//下拉是返回新页
$(window).scroll(function(){
    var docHeight=$(document).height();//整个窗体的高度
    var winHeight=$(window).height();//当前窗体高度
    var winScrollHeight=$(window).scrollTop();//滚动条滚动距离
    // console.log(docHeight+"  "+winHeight+"  "+winScrollHeight);
    if(docHeight==winHeight+winScrollHeight|docHeight<=winHeight+winScrollHeight|docHeight-0.5<=winHeight+winScrollHeight) {
        if (flag == "") {
            IsInload();
        } else if (flag == "search") {
            setTimeout("loadXMLDoc_1()","0");
        } else if (flag == "screen") {
            Selectspecies_1();
        }
    }
});


function Selectspecies() {
    //是否可议价myCheckbox0
    //是否自提myCheckbox1
    //交易地点myCheckbox2
    //价格排序myCheckbox5
    var myCheckbox0="";
    var myCheckbox1="";
    var myCheckbox2="";
    var myCheckbox5="";
    var label="";
    //可否议价
    if($("#myCheckbox01").prop('checked'))
        myCheckbox0 ="1";
    else if($("#myCheckbox02").prop('checked'))
        myCheckbox0 ="0";


    //是否自提
    if($("#myCheckbox11").prop('checked'))
        myCheckbox1 ="1";
    else if($("#myCheckbox12").prop('checked'))
        myCheckbox1 = "2";
    else
        myCheckbox1 = "0";

    //交易地点
    if($("#myCheckbox21").prop('checked'))
        myCheckbox2 ="南苑";
    else if($("#myCheckbox22").prop('checked'))
        myCheckbox2 = "北苑";

    //价格排序
    if($("#myCheckbox51").prop('checked'))
        myCheckbox5 ="1";
    else if($("#myCheckbox52").prop('checked'))
        myCheckbox5 = "0";
    //获取价格
    var price1 = $("#price1").val();
    var price2 = $("#price2").val();

    //	复选框加入字符串label
    if($("#myCheckbox31").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox31").val();
        else
            label=label+$("#myCheckbox31").val();
    }
    if($("#myCheckbox32").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox32").val();
        else
            label=label+$("#myCheckbox32").val();
    }
    if($("#myCheckbox33").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox33").val();
        else
            label=label+$("#myCheckbox33").val();
    }
    if($("#myCheckbox34").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox34").val();
        else
            label=label+$("#myCheckbox34").val();
    }

    if($("#myCheckbox35").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox35").val();
        else
            label=label+$("#myCheckbox35").val();
    }
    if($("#myCheckbox36").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox36").val();
        else
            label=label+$("#myCheckbox36").val();
    }
    if($("#myCheckbox37").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox37").val();
        else
            label=label+$("#myCheckbox37").val();
    }

    //alert("是否可议价"+myCheckbox0+"\n"+"是否自提"+myCheckbox1+"\n"+"交易地点"+myCheckbox2+"\n"+"价格排序"+myCheckbox5+"\n"+"价格区间"+price1+"\n"+price2+"\n"+"标签"+label);


    all = ""+myCheckbox0+myCheckbox1+myCheckbox2+myCheckbox5+price1+price2+label;
    if(all==""){
        return;
    }
    //将flag赋值为screen
    flag="screen";
    token=getCookie("token");
    currentPage="1";
    //定义全局变量saveData，方便下拉时使用
    saveData={"label":label,"currentPage":currentPage,"price":price1+";"+price2,"tradeMethod":myCheckbox1,"trandPlace":myCheckbox2,"isBargain":myCheckbox0};
    $("#main").html("");
    $.ajax({
        url:"/screen",
        type:"get",
        headers:{
            "token":token,
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            currentPage=result.data.currentPage;
            //alert("result.data.page="+currentPage);

            data=result.data.data;
            if(result.code== "200"){
                innerGoods(data);
                document.documentElement.scrollTop = 800;
            }else{
                console.log("查询失败！");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}

function Selectspecies_1() {
    token=getCookie("token");
    //var saveData={"label":label,"page":currentPage,"price":price1,"tradeMethod":myCheckbox1,"trandPlace":myCheckbox2,"isBargain":myCheckbox0};
    //alert("second="+currentPage);
    saveData.currentPage=currentPage;
    if(currentPage == "1"){
        return;
    }
    $.ajax({
        url:"/screen",
        type:"get",
        headers:{
            "token":token,
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            currentPage=result.data.currentPage;
            console.log("页数："+currentPage);
            data=result.data.data;
            if(result.code== "200"){
                innerGoods(data);
            }else{
                console.log("查询失败！");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}

function ismessage() {
    var saveData = {"username": getCookie("username")}
    $.ajax({
        url: "/isMessage",
        type: "get",
        headers: {
            "token": token,
            contentType: "application/json;charset=UTF-8"
        },
        data: saveData,
        contentType: "application/json",
        success: function (result, testStatus) {
            if (result.code == "200") {
                var img = document.getElementById("youlike");
                img.setAttribute("style","background:#fff  url('../img/nav(1)3.jpg') no-repeat;") ;
            }
        },
        error: function (xhr, errrorMessage, e) {
            alert("系统异常！");
        }
    });
}