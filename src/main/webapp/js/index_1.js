/*
* 所有ajax请求
* 作者：lu
* */



//判断下拉类型
var flag="";
//判断是否正在加载
isLoading=false;
//定义全局变量currentPage，方便下拉时使用
currentPage="1";
//定义全局变量title，方便下拉时使用
var title;

var datas="";

//加载时返回最新商品
window.onload =function init() {
    currentPage="1";
    token='';
    flag="";

    //请求猜你喜欢
    whatULike();

    var saveData={"time":"desc","currentPage":currentPage};
    $("#main").html("");
    $.ajax({
        url:"/cashsale/searchByTime",
        type:"get",
        headers:{
            "token":token,
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData,
        contentType:"application/json",
        async:false,
        success:function(result,testStatus)
        {
            currentPage=result.data.currentPage;
            console.log("页数："+currentPage);
            datas=result.data.data;
            //渲染
            innerGoods(datas);
            whatlike(datas);
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！"+e+"\n"+errrorMessage);
        }
    });

    //请求是否有新消息
    ismessage();

    // 请求是否认证
    var saveData2 = {"username":getCookie("username")}
    $.ajax({
        url:"/cashsale/GetUserData",
        type:"get",
        headers:{
            "token":token,
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData2,
        async:false,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            var username=result.data;
            //渲染函数
            innerConfirm(username);
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！"+e+"\n"+errrorMessage);
        }
    });


    //筛选下拉时的变化
    var oDiv = document.getElementById("species");
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
            oDiv.style = "position:fixed;top:17px;";
        } else {
            oDiv.style = "";
        }
    }


}

//下拉是返回新页
$(window).scroll(function(){
    var docHeight=$(document).height();//整个窗体的高度
    var winHeight=$(window).height();//当前窗体高度
    var winScrollHeight=$(window).scrollTop();//滚动条滚动距离
    // console.log(docHeight+"  "+winHeight+"  "+winScrollHeight);
    if(docHeight==winHeight+winScrollHeight|docHeight<=winHeight+winScrollHeight|docHeight-0.5<=winHeight+winScrollHeight) {
        if(document.getElementById("loadingSpan").style.display=="block"){
            console.log("数据完了");
            return ;
        }
        document.getElementById("loadingSpan").style.display="none";
        document.getElementById("loadingImg").style.display="block";
        if (flag == "") {
            window.setTimeout('IsInload()',300);
            isLoading=false;
        } else if (flag == "search") {
            window.setTimeout('loadXMLDoc_1()',300);
            isLoading=false;
        } else if (flag == "screen") {
            window.setTimeout('Selectspecies_1()',300);
            isLoading=false;
        }
    }
});





//猜你喜欢专用页数
var currentPage1;
//猜你喜欢
function whatULike() {
    var saveData={"username":getCookie("username"),"currentPage":currentPage1};
    var play = document.getElementById("play");
    $.ajax({
        url:"/cashsale/recommend",
        type:"get",
        headers:{
            "token":token,
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            if(getCookie("username")!=""&&getCookie("username")!=null&&getCookie("username")!=undefined){
                if(result.code==200){
                    if(result.currentPage!=0){
                        document.getElementById("Refresh").style.display="block";
                    }
                    console.log(result.currentPage);
                    var data1=result.data.data;
                    currentPage1=result.currentPage+1;
                    if(data1==""||data1==undefined||data1==null){

                    }else{
                        whatlike(data1);
                    }
                }
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}
//渲染猜你喜欢
function whatlike(data) {
    $("#play").html("");
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

//渲染下拉的商品
function innerGoods(data) {
    $.each(data,function(index,obj){
        var main = document.getElementById("main");
        var goods= document.createElement('div');
        var img = document.createElement('img');
        var h4= document.createElement('h4');
        var p= document.createElement('p');

        goods.setAttribute("class", "goods");
        goods.setAttribute("title", "点击查看详情");
        goods.setAttribute("onclick", "goodsclick(this)");

        h4.setAttribute("class", "myH");
        p.setAttribute("class", "price");
        img.src = obj.imageUrl;
        if(obj.highLight==undefined||obj.highLight==null||obj.highLight==""){
            h4.innerHTML=obj.title;
        }else {
            h4.innerHTML=obj.highLight;
        }
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

        var div= document.createElement('div');
        var img1 = document.createElement('img');
        var img2 = document.createElement('img');
        var img3 = document.createElement('img');
        var span1 = document.createElement('span');
        var span2 = document.createElement('span');
        var span3 = document.createElement('span');

        div.setAttribute("class", "Hoverdiv");
        img1.src = "img/index/share.png";
        img2.src = "img/index/commend.png";
        img3.src = "img/index/noLike.png";
        img1.setAttribute("title", "转发数");
        img2.setAttribute("title", "评论数");
        img3.setAttribute("title", "点赞数");
        span1.innerHTML= obj.shareNumber;
        span2.innerHTML= obj.commentsNumber;
        span3.innerHTML= obj.likeNumber;
        span1.setAttribute("title", "转发数");
        span2.setAttribute("title", "评论数");
        span3.setAttribute("title", "点赞数");

        div.appendChild(img1);
        div.appendChild(span1);
        div.appendChild(img2);
        div.appendChild(span2);
        div.appendChild(img3);
        div.appendChild(span3);
        goods.appendChild(div);
    });
}

//是否验证，加载时调用
function innerConfirm(data) {
    var isConfirm = document.getElementById("Confirm");
    // data为1时则为已验证
    if (data == "1") {
        // 渲染到页面
        isConfirm.innerHTML = "已认证";
        var img = document.createElement('img');
        img.src = "img/confirm.png";
        var confirm = document.getElementById("isConfirm");
        confirm.appendChild(img);
    } else {
        // 渲染到页面
        isConfirm.innerHTML = "未认证";
        var img = document.createElement('img');
        img.src = "img/noconfirm.png";
        var confirm = document.getElementById("isConfirm");
        confirm.appendChild(img);
    }
}

//直接下拉时调用
function IsInload() {
    console.log(isLoading);
    if(isLoading){
        console.log("重复页数："+currentPage);
        return ;
    }else{
        isLoading=true;
    }
    token='';
    if(currentPage==""){
        alert("没有更多数据！");
        return ;
    }
    var saveData={"time":"asc","currentPage":currentPage};
    $.ajax({
        url:"/cashsale/searchByTime",
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
                console.log("没有更多数据！");
                document.getElementById("loadingImg").style.display="none";
                document.getElementById("loadingSpan").style.display="block";
                return;
            }
            //渲染
            innerGoods(data);
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
    document.getElementById("loadingImg").style.display="none";
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
        url:"/cashsale/search",
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
//搜索完下拉时调用，currentPage不为0或空
function loadXMLDoc_1()
{
    if(isLoading){
        console.log("重复页数："+currentPage);
        return ;
    }else{
        isLoading=true;
    }
    console.log(isLoading);
    token=getCookie("token");
    console.log(currentPage);
    var saveData={"title":title,"currentPage":currentPage};
    $.ajax({
        url:"/cashsale/search",
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
                if(data==""){
                    console.log("没有更多数据！");
                    document.getElementById("loadingImg").style.display="none";
                    document.getElementById("loadingSpan").style.display="block";
                    return;
                }else{
                    innerGoods(data);
                }
            }else{
                console.log("查询失败！");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
    document.getElementById("loadingImg").style.display="none";
}

//筛选时调用
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
        url:"/cashsale/screen",
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
            }else{
                console.log("查询失败！");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}
//筛选完下拉时调用
function Selectspecies_1() {
    if(isLoading){
        console.log("重复页数："+currentPage);
        return ;
    }else{
        isLoading=true;
    }

    console.log(isLoading);
    token=getCookie("token");
    //var saveData={"label":label,"page":currentPage,"price":price1,"tradeMethod":myCheckbox1,"trandPlace":myCheckbox2,"isBargain":myCheckbox0};
    //alert("second="+currentPage);
    saveData.currentPage=currentPage;
    if(currentPage == "1"){
        return;
    }
    $.ajax({
        url:"/cashsale/screen",
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
                if(data==""){
                    console.log("没有更多数据！");
                    document.getElementById("loadingImg").style.display="none";
                    document.getElementById("loadingSpan").style.display="block";
                    return;
                }else{
                    innerGoods(data);
                }
            }else{
                console.log("查询失败！");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
    document.getElementById("loadingImg").style.display="none";
}
//是否有新消息，加载时调用
function ismessage() {
    var saveData = {"username": getCookie("username")}
    $.ajax({
        url: "/cashsale/isMessage",
        type: "get",
        headers: {
            "token": token,
            contentType: "application/json;charset=UTF-8"
        },
        data: saveData,
        contentType: "application/json",
        async:false,
        success: function (result, testStatus) {
            if (result.data == "1") {
                var img = document.getElementById("youlike");
                img.setAttribute("style","background:#fff  url('/cashsale/img/haveMessage.png') no-repeat center center;") ;
            }
        },
        error: function (xhr, errrorMessage, e) {
            alert("系统异常！");
        }
    });
}