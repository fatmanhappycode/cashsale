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

    var saveData={"title":Otitle};
    // $("#main").html("");
    $.ajax({
        url:"/cashsale/searchhint",
        type:"get",
        dataType:"json",
        data:saveData,
        contentType:"application/x-www-form-urlencoded;charset=UTF-8",
        headers:{
            "title":Otitle,
            contentType:"application/x-www-form-urlencoded;charset=UTF-8"
        },
        success:function(result,testStatus)
        {
            if(result.code== "200"){
                data=result.data;
                console.log(data);
                var ul = document.getElementById("tips");
                ul.innerHTML="";
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
    var ul = document.getElementById("tips");
    for(var i=0;i<data.length;i++){
        var li= document.createElement('li');
        li.setAttribute("onclick", "");
        li.innerHTML=data[i];
        ul.appendChild(li);
        console.log(data[i]);
    }
}