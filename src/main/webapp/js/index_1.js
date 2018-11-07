//data=[{"price": 23,"imageUrl": "www.abc.com","isBargain": 1,"label": "23","title": "bc"},{"price": 223,"imageUrl": "www.abc.com","isBargain": 12,"label": "223","title": "b2c"}];
var currentPage="";
function innerGoods(data) {
	$.each(data,function(index,obj){
		var goodsmain = document.getElementById("goodsmain");
		var goods= document.createElement('div');
		var img = document.createElement('img');
		var h4= document.createElement('h4');
		var p= document.createElement('p');
		goods.setAttribute("class", "goods");
		h4.setAttribute("class", "myH");
		p.setAttribute("class", "price");
	    img.src = "img/nav (2).gif";
	    h4.innerHTML=obj.title;
	    p.innerHTML=obj.price;
	    goods.appendChild(img);
	    goods.appendChild(h4);
	    goods.appendChild(p);
	    goodsmain.appendChild(goods);
	});
}
function loadXMLDoc()
{
	var title = $(".mySearch").val();
	token=getCookie("token");
	var saveData={"title":title,"currentPage":currentPage};
	$("#goodsmain").html("");
	$.ajax({
		url:"/search",
		type:"get",
		dataType:"json",
		headers:{
			"token":token,
            contentType:"application/json;charset=UTF-8"
		},
		data:JSON.stringify(saveData),
		contentType:"application/json",
		success:function(result,testStatus)
		{
			currentPage=result.data.currentPage;//是否需要加一
			data=result.data.data;
			if(result.code== "117"){
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