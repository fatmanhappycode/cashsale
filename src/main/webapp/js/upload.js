var label="";
function f() {
	var commodity = $("#commodity").val();
	var price = $("#price").val();
	var place = $("#place").val();
	var and = $("#and").val();
	var image = $("#image").val();
	//alert(commodity+"\n"+price +"\n"+ place +"\n"+ and);
	//是否自提myCheckbox0
	//是否可议价myCheckbox1
	var myCheckbox0="";
	var myCheckbox1="";
	
	if($("#myCheckbox01").prop('checked'))
		myCheckbox0 ="1";
	else if($("#myCheckbox02").prop('checked'))
		myCheckbox0 ="2";
	else
		myCheckbox0="0";
	
	if($("#myCheckbox11").prop('checked'))
		myCheckbox1 ="1";
	else if($("#myCheckbox12").prop('checked'))
		myCheckbox1 = "0";
	//alert(myCheckbox0);
	//alert(myCheckbox1);

//	复选框加入字符串label
	var label="";
	if($("#myCheckbox21").prop('checked')){
		if(label!="")
			label=label+";"+$("#myCheckbox21").val();
		else
            label=label+$("#myCheckbox21").val();
	}
	if($("#myCheckbox22").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox22").val();
        else
            label=label+$("#myCheckbox22").val();
	}
	if($("#myCheckbox23").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox23").val();
        else
            label=label+$("#myCheckbox23").val();
	}
	if($("#myCheckbox24").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox24").val();
        else
            label=label+$("#myCheckbox24").val();
	}
	
	if($("#myCheckbox25").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox25").val();
        else
            label=label+$("#myCheckbox25").val();
	}
	if($("#myCheckbox26").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox26").val();
        else
            label=label+$("#myCheckbox26").val();
	}
	if($("#myCheckbox27").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox27").val();
        else
            label=label+$("#myCheckbox27").val();
	}

	//alert(label);
	if(label=="" || myCheckbox0=="" || myCheckbox1=="" ||commodity==""||price==""|| place==""|| and==""){
		alert("请填写完信息再提交，否则将清空！");
	}else{
        document.getElementById("passwordItem");
		var token=getCookie("token");
		var saveData={"title":commodity,"label":label,"price":price,"tradeMethod":myCheckbox0,"isBargain":myCheckbox1,"tradePlace":place,"pdDescription":editor.txt.html(), "imageUrl":image};
		$.ajax({
			url:"/publish",
			type:"post",
			dataType:"json",
            async: false,
			data:JSON.stringify(saveData),
			headers: {
				"token":token,
                contentType:"application/json;charset=UTF-8"
			},
			success:function(result,testStatus)
			{
				console.log("123");
				if(result.code==107)
				{
				    alert("234");
					//发布成功，转跳到页面
					alert(result.msg);
				    window.location.href="/index.html";
				}
				else if(result.code==108){
					//失败
					alert(result.msg);
				}else if (result.code==109) {
					alert(result.msg);
				} else{
					//请先登录
					alert(result.msg);
					window.location.href="login.html";
				}
			},
			error:function(xhr,errrorMessage,e){
				console.log(errrorMessage);
			}
		});
	}
	
}
//提示语
function item() {
	var commodity = $("#commodity").val();
	var price = $("#price").val();
	var place = $("#place").val();
	var and = $("#and").val();
	var myCheckbox0="";
	var myCheckbox1="";
	if($("#myCheckbox01").prop('checked'))
		myCheckbox0 ="1";
	else if($("#myCheckbox02").prop('checked'))
		myCheckbox0 ="2";
	else
		myCheckbox0="0";
	
	
	if($("#myCheckbox11").prop('checked'))
		myCheckbox1 ="1";
	else if($("#myCheckbox12").prop('checked'))
		myCheckbox1 = "0";
	
//	复选框加入字符串label
	if($("#myCheckbox21").prop('checked')){
		label=label+";"+$("#myCheckbox21").val();
	}
	if($("#myCheckbox22").prop('checked')){
		label=label+";"+$("#myCheckbox22").val();
	}
	if($("#myCheckbox23").prop('checked')){
		label=label+";"+$("#myCheckbox23").val();
	}
	if($("#myCheckbox24").prop('checked')){
		label=label+";"+$("#myCheckbox24").val();
	}
	
	if($("#myCheckbox25").prop('checked')){
		label=label+";"+$("#myCheckbox25").val();
	}
	if($("#myCheckbox26").prop('checked')){
		label=label+";"+$("#myCheckbox26").val();
	}
	if($("#myCheckbox27").prop('checked')){
		label=label+";"+$("#myCheckbox27").val();
	}
	
	if(label=="" || myCheckbox0=="" || myCheckbox1=="" ||commodity==""||price==""|| place==""|| and=="" ||document.getElementById("myfile").files.length==0){
		document.getElementById("passwordItem").style.display="block";
	}else{
		document.getElementById("passwordItem").style.display="none";
	}
}
function clearimg() {
	//获取展示图片的容器
    var container = document.getElementById("container");
    //置空
    container.innerHTML = "";
}


function goodsclick_1(str) {
    var goods=str.childNodes;
    var productId=goods[3].value;
    var saveData={"productId":productId};
    // alert(productId);
    $.ajax({
        url:"/GetDetailProduct",
        type:"get",
        headers:{
            contentType:"application/json;charset=UTF-8"
        },
        data:saveData,
        contentType:"application/json",
        success:function(result,testStatus)
        {
            if(result.code==124){
                data=result.data;
                setCookie("imageUrl",data.imageUrl);
                setCookie("title",data.title);
                setCookie("price",data.price);
                setCookie("isBargain",data.isBargain);
                setCookie("tradeMethod",data.tradeMethod);
                setCookie("goodsUsername",data.username);
                setCookie("pdDescription",data.pdDescription);
                // alert(data.username);
                window.open("goods.html");
            }else{
                console.log(result.msg);
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}

