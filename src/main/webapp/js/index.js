function goodsclick(str) {
    var goods=str.childNodes;
    var productId=goods[3].value;
    // alert(productId);
    var string="goods.html?productId="+productId;

    //浏览商品事件
    var saveData = {"productId":productId,"scoreCode":"A"};
    $.ajax({
        url:"/score",
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

