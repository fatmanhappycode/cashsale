// <<<<<<< HEAD
// // data=[{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 223,"imageUrl": "www.abc.com","isBargain": 12,"label": "223","title": "b2c"},{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 223,"imageUrl": "www.abc.com","isBargain": 12,"label": "223","title": "b2c"},{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 223,"imageUrl": "www.abc.com","isBargain": 12,"label": "223","title": "b2c"}];
// // var isLoading=false;
// // function innerGoods() {
// // 	if(isLoading){
// // 		return;
// // 	}else{
// // 		isLoading=true;
// // 	}
// // 	$.each(data,function(index,obj){
// // 		var main = document.getElementById("main");
// // 		var goods= document.createElement('div');
// // 		var img = document.createElement('img');
// // 		var h4= document.createElement('h4');
// // 		var p= document.createElement('p');
// // 		goods.setAttribute("class", "goods");
// // 		h4.setAttribute("class", "myH");
// // 		p.setAttribute("class", "price");
// // 	    img.src = obj.imageUrl;
// // 	    h4.innerHTML=obj.title;
// // 	    p.innerHTML=obj.price;
// // 	    goods.appendChild(img);
// // 	    goods.appendChild(h4);
// // 	    goods.appendChild(p);
// // 	    main.appendChild(goods);
// // 	});
// // 	isLoading=false;
// // }
// //
// // function loadXMLDoc()
// // {
// // 	var title = $(".mySearch").val();
// // 	currentPage=getCookie("currentPage");
// // 	token=getCookie("token");
// // 	var saveData={"title":title,"currentPage":currentPage};
// // 	$("#main").html("");
// // 	$.ajax({
// // 		url:"/screen",
// // 		type:"get",
// // 		dataType:"json",
// // 		headers:{
// // 			"token":token,
// //             contentType:"application/json;charset=UTF-8"
// // 		},
// // 		data:JSON.stringify(saveData),
// // 		contentType:"application/json",
// // 		success:function(result,testStatus)
// // 		{
// // 			currentPage=result.currentPage;
// // 			data=result.data;
// // 			if(result.code== "117"){
// // 				alert("查询！");
// // 				innerGoods(data);
// // 			}else{
// // 				console.log("查询失败！");
// // 				alert("查询失败！");
// // 			}
// // 		},
// // 		error:function(xhr,errrorMessage,e){
// // 			alert("系统异常！");
// // 		}
// // 	});
// // }
// //
// // $(window).scroll(function(){
// // 	var docHeight=$(document).height();//整个窗体的高度
// // 	var winHeight=$(window).height();//当前窗体高度
// // 	var winScrollHeight=$(window).scrollTop();//滚动条滚动距离
// // 	console.log(winScrollHeight);
// // 	if(docHeight-30<=winHeight+winScrollHeight){
// // 		innerGoods();
// // 	}
// // });
// // /*function bindScrollevent() {
// // 	//添加滚动监听事件
// // 	$(window).scroll(function(){
// // 		var docHeight=$(document).height();//整个窗体的高度
// // 		var winHeight=$(window).height();//当前窗体高度
// // 		var winScrollHeight=$(window).scrollTop();//滚动条滚动距离
// // 		if(docHeight==winHeight+winScrollHeight){
// // 			alert(docHeight+""+winHeight);
// // 		}
// // 		alert()
// // 	});
// // }*/
// //
// //
// //
// // /*$(document).on('mousewheel DOMMouseScroll', onMouseScroll);
// // function onMouseScroll(e){
// // //    e.preventDefault();
// // 	//添加滚动监听事件
// // 	$(window).scroll(function(){
// // 		var docHeight=$(document).height();//整个窗体的高度
// // 		var winHeight=$(window).height();//当前窗体高度
// // 		var winScrollHeight=$(window).scrollTop();//滚动条滚动距离
// // 		console.log(winScrollHeight);
// // 		if(docHeight-5<=winHeight+winScrollHeight){
// // 			innerGoods();
// // 		}
// // //		alert(docHeight+"   "+winHeight);
// // 	});
// // }*/
// //
// //
// //
// //
// // function Selectspecies() {
// // 	//是否自提myCheckbox0
// // 	//是否可议价myCheckbox1
// // 	var myCheckbox0="";
// // 	var myCheckbox1="";
// // 	var myCheckbox2="";
// // 	var myCheckbox5="";
// //
// // 	//可否议价
// // 	if($("#myCheckbox01").prop('checked'))
// // 		myCheckbox0 ="1";
// // 	else if($("#myCheckbox02").prop('checked'))
// // 		myCheckbox0 ="2";
// // 	else
// // 		myCheckbox0="0";
// //
// // 	//是否自提
// // 	if($("#myCheckbox11").prop('checked'))
// // 		myCheckbox1 ="1";
// // 	else if($("#myCheckbox12").prop('checked'))
// // 		myCheckbox1 = "0";
// //
// // 	//交易地点
// // 	if($("#myCheckbox21").prop('checked'))
// // 		myCheckbox2 ="1";
// // 	else if($("#myCheckbox22").prop('checked'))
// // 		myCheckbox2 = "0";
// //
// // 	//交易地点
// // 	if($("#myCheckbox51").prop('checked'))
// // 		myCheckbox5 ="1";
// // 	else if($("#myCheckbox52").prop('checked'))
// // 		myCheckbox5 = "0";
// //
// // 	var price1 = $("#price1").val();
// // 	var price2 = $("#price2").val();
// //
// // 	alert(myCheckbox0+"\n"+myCheckbox1+"\n"+myCheckbox2+"\n"+myCheckbox5+"\n"+price1+"\n"+price1);
// // }
// //
// =======
// data=[{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 223,"imageUrl": "www.abc.com","isBargain": 12,"label": "223","title": "b2c"},{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 223,"imageUrl": "www.abc.com","isBargain": 12,"label": "223","title": "b2c"},{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 223,"imageUrl": "www.abc.com","isBargain": 12,"label": "223","title": "b2c"}];
// var isLoading=false;
// function innerGoods() {
// 	if(isLoading){
// 		return;
// 	}else{
// 		isLoading=true;
// 	}
// 	$.each(data,function(index,obj){
// 		var main = document.getElementById("main");
// 		var goods= document.createElement('div');
// 		var img = document.createElement('img');
// 		var h4= document.createElement('h4');
// 		var p= document.createElement('p');
// 		goods.setAttribute("class", "goods");
// 		h4.setAttribute("class", "myH");
// 		p.setAttribute("class", "price");
// 	    img.src = "img/nav (2).gif";
// 	    h4.innerHTML=obj.title;
// 	    p.innerHTML=obj.price;
// 	    goods.appendChild(img);
// 	    goods.appendChild(h4);
// 	    goods.appendChild(p);
// 	    main.appendChild(goods);
// 	});
// 	isLoading=false;
// }
//
// function moreGoods()
// {
// 	currentPage=getCookie("currentPage");
// 	var saveData={"currentPage":currentPage};
// 	$.ajax({
// 		url:"/screen",
// 		type:"get",
// 		dataType:"json",
// 		headers:{
//             contentType:"application/json;charset=UTF-8"
// 		},
// 		data:JSON.stringify(saveData),
// 		contentType:"application/json",
// 		success:function(result,testStatus)
// 		{
// 			currentPage=result.data.currentPage;//是否需要加一
// 			data=result.data.data;
// 			setCookie("currentPage",currentPage);
// 			if(result.code== "117"){
// 				innerGoods(data);
// 			}else{
// 				console.log("查询失败！");
// 			}
// 		},
// 		error:function(xhr,errrorMessage,e){
// 			alert("系统异常！");
// 		}
// 	});
// }
//
// $(window).scroll(function(){
// 	var docHeight=$(document).height();//整个窗体的高度
// 	var winHeight=$(window).height();//当前窗体高度
// 	var winScrollHeight=$(window).scrollTop();//滚动条滚动距离
// 	console.log(winScrollHeight);
// 	if(docHeight-30<=winHeight+winScrollHeight){
// 		innerGoods();
// 	}
//
//
// 	//回到顶部是图标消失
// 	if(winScrollHeight>=100){
// 		document.getElementById("test").style.display="block";
// 	}else{
// 		document.getElementById("test").style.display="none";
// 	}
// });
/*function bindScrollevent() {
	//添加滚动监听事件
	$(window).scroll(function(){
		var docHeight=$(document).height();//整个窗体的高度
		var winHeight=$(window).height();//当前窗体高度
		var winScrollHeight=$(window).scrollTop();//滚动条滚动距离
		if(docHeight==winHeight+winScrollHeight){
			alert(docHeight+""+winHeight);
		}
		alert()
	});
}*/



/*$(document).on('mousewheel DOMMouseScroll', onMouseScroll);
function onMouseScroll(e){
//    e.preventDefault();
	//添加滚动监听事件
	$(window).scroll(function(){
		var docHeight=$(document).height();//整个窗体的高度
		var winHeight=$(window).height();//当前窗体高度
		var winScrollHeight=$(window).scrollTop();//滚动条滚动距离
		console.log(winScrollHeight);
		if(docHeight-5<=winHeight+winScrollHeight){
			innerGoods();
		}
//		alert(docHeight+"   "+winHeight);
	});    
}*/

//价格输入框自动获取焦点
function setFocus1()
{
    document.getElementById('price1').focus();
}
function setFocus2()
{
    document.getElementById('price2').focus();
}


function Selectspecies() {
	//是否自提myCheckbox0
	//是否可议价myCheckbox1
	var myCheckbox0="";
	var myCheckbox1="";
	var myCheckbox2="";
	var myCheckbox5="";
	var label="";
	//可否议价
	if($("#myCheckbox01").prop('checked'))
		myCheckbox0 ="1";
	else if($("#myCheckbox02").prop('checked'))
		myCheckbox0 ="2";
	else
		myCheckbox0="0";

	//是否自提
	if($("#myCheckbox11").prop('checked'))
		myCheckbox1 ="1";
	else if($("#myCheckbox12").prop('checked'))
		myCheckbox1 = "0";

	//交易地点
	if($("#myCheckbox21").prop('checked'))
		myCheckbox2 ="1";
	else if($("#myCheckbox22").prop('checked'))
		myCheckbox2 = "0";

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

    alert(myCheckbox0+"\n"+myCheckbox1+"\n"+myCheckbox2+"\n"+myCheckbox5+"\n"+price1+"\n"+price2+"\n"+label);

    flag="/Screen";
    token=getCookie("token");
    var saveData={"label":title,"currentPage":currentPage};
    $("#goodsmain").html("");
    $.ajax({
        url:"/screen",
        type:"get",
        dataType:"json",
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

