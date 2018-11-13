//data=[{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 223,"imageUrl": "www.abc.com","isBargain": 12,"label": "223","title": "b2c"}];

var flag="";
isLoading=false;
//定义全局变量currentPage，方便下拉时使用
currentPage='';
//定义全局变量title，方便下拉时使用
var title;
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

function innerGoods_1(data) {
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
        goodsId.setAttribute("value",obj.productId);
        goods.appendChild(goodsId);
    });
}

//加载时返回最新商品
window.onload =function init() {
    currentPage='';
    token='';
    flag="";
    var saveData={"time":"asc","currentPage":currentPage};
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
            data=result.data.data;
            //渲染
            innerGoods(data);
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
            data=result.data.data;
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
    currentPage="";
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
			data=result.data.data;
			if(result.code== "107"){
                $(window).scrollTop(250);
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
            data=result.data.data;
            if(result.code== "107"){
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
    console.log(docHeight+"  "+winHeight+"  "+winScrollHeight);
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

    alert("是否可议价"+myCheckbox0+"\n"+"是否自提"+myCheckbox1+"\n"+"交易地点"+myCheckbox2+"\n"+"价格排序"+myCheckbox5+"\n"+"价格区间"+price1+"\n"+price2+"\n"+"标签"+label);


    all = ""+myCheckbox0+myCheckbox1+myCheckbox2+myCheckbox5+price1+price2+label;
    if(all==""){
        return;
    }
    //将flag赋值为search
    flag="screen";
    token=getCookie("token");
    currentPage="";
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
            currentPage=result.data.page;
            data=result.data.data;
            if(result.code== "107"){
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

function Selectspecies_1() {
    token=getCookie("token");
    //var saveData={"label":label,"page":currentPage,"price":price1,"tradeMethod":myCheckbox1,"trandPlace":myCheckbox2,"isBargain":myCheckbox0};
    alert(currentPage);
    saveData.currentPage=currentPage;
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
            data=result.data.data;
            if(result.code== "107"){
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