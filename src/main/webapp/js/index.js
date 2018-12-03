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

function mySearcfOnload() {
    Otitle = $(".mySearch").val();
    console.log(Otitle);

    var saveData={
        "suggest":{
            "prefix":Otitle,
            "completion":{
                "field":"title.suggest"
            }
        }
    };
    // $("#main").html("");
    $.ajax({
        url:"http://localhost:9200/cashsale3/doc/_search",
        type:"post",
        dataType:"json",
        data:JSON.stringify(saveData),
        contentType:"application/json;charset=UTF-8",
        headers:{
            contentType:"application/json;charset=UTF-8"
        },
        success:function(result,testStatus)
        {
            if(result.timed_out== "200"){
                data=result.suggest.my-suggest.options;
                if(data!=undefined&&data!=null&&data!="") {
                    innerTips(data);
                }
            }else{
                console.log("查询失败！");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}

function innerTips(data) {
    $.each(data,function(index,obj){
        var flag=false;
        var ul = document.getElementById("tips");
        var ulLi = document.getElementById("tips").childNodes;
        for(var i=0;i<ulLi.length;i++){
            if(obj.text==ulLi[i]){
                flag=true;
                break;
            }
        }
        if(flag){
            console.log("重复");
        }else {
            var li= document.createElement('li');
            li.setAttribute("onclick", "");
            li.innerHTML=obj.text;
            ul.appendChild(li);
        }
    });
}