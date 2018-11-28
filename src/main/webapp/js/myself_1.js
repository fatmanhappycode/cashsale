var n=0;
window.onscroll = function()
{
    oDiv=document.getElementById("self_left")
    var H =document.getElementById("self_left").offsetTop;
    var s = document.body.scrollTop || document.documentElement.scrollTop;
    console.log(H+"  "+s);
    if(s>=304) {
        oDiv.style = "position:fixed;top:-100px;left:40px;";
    } else{
        oDiv.style = "";
    }
}
test.onclick = function(){
     document.body.scrollTop = document.documentElement.scrollTop = 0;
}





var saveData1={"username":getCookie("username")};
$.ajax({
    url:"/cashsale/getShoppingTrolley",
    type:"post",
    dataType:"json",
    data:JSON.stringify(saveData1),
    contentType:"application/json;charset=UTF-8",
    success:function(result,testStatus)
    {
        if(result.code=="200"){
            var data=result.data;
            if(data!=null&&data!=""&&data!=undefined){
                document.getElementById("main2").innerHTML="";
            }
            for(var i=0;i<data.length;i++){
                loadgoods(data[i]);
            }
        }
    },
    error:function(xhr,errrorMessage,e){
        alert("系统异常！");
    }
});
function innerGoods(data) {
        var main = document.getElementById("main2");
        var goods= document.createElement('div');
        var img = document.createElement('img');
        var h4= document.createElement('h4');
        var p= document.createElement('p');

        goods.setAttribute("class", "goods");
        goods.setAttribute("onclick", "goodsclick(this)");

        h4.setAttribute("class", "myH");
        p.setAttribute("class", "price");
        img.src = data.imageUrl;
        h4.innerHTML=data.title;
        p.innerHTML="￥"+data.price;
        goods.appendChild(img);
        goods.appendChild(h4);
        goods.appendChild(p);
        main.appendChild(goods);
        //商品id
        var goodsId= document.createElement('input');
        goodsId.setAttribute("id","goodsId");
        goodsId.setAttribute("type","hidden");
        goodsId.setAttribute("value",data.productId);
        goods.appendChild(goodsId);
}

function loadgoods(productId) {
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
            if(result.code=="200"){
                var data=result.data;
                innerGoods(data);
            }else{
                console.log(result.msg);
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}



var saveData={"username":getCookie("username")};
$.ajax({
    url:"/cashsale/getPersonInfo",
    type:"get",
    data:saveData,
    contentType:"application/json",
    success:function(result,testStatus)
    {
        if(result.code==200){
            data=result.data;
            document.getElementById("nickname").innerHTML=data.nickname;
            document.getElementById("dataThreeA").innerHTML=data.credit;
        }else{
            console.log(result.msg);
        }
    },
    error:function(xhr,errrorMessage,e){
        alert("系统异常！"+e+"\n"+errrorMessage);
    }
});