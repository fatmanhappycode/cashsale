/*
* 链接商品详情
* 作者：lu
* */
// 点击猜你喜欢、最新商品调用
function goodsclick(str) {
    //goods为其子标签
    var goods=str.childNodes;
    // 获取第四个自类
    var productId=goods[3].value;
    // alert(productId);
    // 把id拼接到URL
    var string="goods.html?productId="+productId;

    //浏览商品事件
    var saveData = {"productId":productId,"scoreCode":"A"};
    $.ajax({
        url:"/cashsale/score",
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

