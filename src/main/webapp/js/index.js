function goodsclick(str) {
    var goods=str.childNodes;
    var productId=goods[3].value;
    var saveData={"productId":productId};
    alert(productId);
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
            data=result.data;
            //渲染
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}

