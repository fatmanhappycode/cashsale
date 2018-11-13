function goodsclick(str) {
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

