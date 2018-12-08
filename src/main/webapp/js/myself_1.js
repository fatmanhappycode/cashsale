
/*获取购物车的商品id*/
var saveData1={"username":getCookie("username")};
$.ajax({
    url:"/cashsale/getShoppingTrolley",
    type:"post",
    dataType:"json",
    data:JSON.stringify(saveData1),
    contentType:"application/json;charset=UTF-8",
    success:function(result,testStatus)
    {
        if(result.code=="124"){
            var data=result.data;
            if(data!=null&&data!=""&&data!=undefined){
                document.getElementById("main2").innerHTML="";
            }
            // 通过id获取商品
            for(var i=0;i<data.length;i++){
                loadgoods(data[i]);
            }
        }
    },
    error:function(xhr,errrorMessage,e){
        alert("系统异常！");
    }
});
// 通过id请求返回购物车商品
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
            if(result.code==200){
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
// 渲染购物车商品
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

    //商品id
    var goodsId= document.createElement('input');
    goodsId.setAttribute("id","goodsId");
    goodsId.setAttribute("type","hidden");
    goodsId.setAttribute("value",data.productId);
    goods.appendChild(goodsId);

    //删除商品
    var but=document.createElement('input');


    main.appendChild(goods);
}









/*获取我发布的商品id*/
var saveData2={"username":getCookie("username")};
$.ajax({
    url:"/cashsale/getMyProduct",
    type:"get",
    dataType:"json",
    data:saveData2,
    contentType:"application/json;charset=UTF-8",
    success:function(result,testStatus)
    {
        if(result.currentPage > 0){
            var data=result.data;
            if(data!=null&&data!=""&&data!=undefined){
                document.getElementById("main1").innerHTML="";
                document.getElementById("myProduct").innerHTML=result.currentPage;
            }
            // 根据id获取商品
            for(var i=0;i<data.length;i++){
                loadMyGoods(data[i].productId);
                console.log(i);
            }
        }
    },
    error:function(xhr,errrorMessage,e){
        alert("系统异常！");
    }
});

// 根据我发布的商品id获取商品
function loadMyGoods(productId) {
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
            if(result.code==200){
                var data=result.data;
                innerMyGoods(data,productId);

            }else{
                console.log(result.msg);
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！");
        }
    });
}
// 渲染我发布的商品
function innerMyGoods(data,id) {
    var main = document.getElementById("main1");
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
    goodsId.setAttribute("value",id);
    goods.appendChild(goodsId);
}




/*// 个人信息
$.ajax({
    url:"/cashsale/getPersonInfo",
    type:"get",
    data:saveData,
    contentType:"application/json",
    success:function(result,testStatus)
    {
        if(result.code==200){
            data=result.data;
            if(data.nickname!=undefined&&data.nickname!=""){
                document.getElementById("nickname").innerHTML=data.nickname;
            }
            document.getElementById("dataThreeA").innerHTML=data.credit;
            document.getElementById("concern").innerHTML=data.myFans;
        }else{
            console.log(result.msg);
        }
    },
    error:function(xhr,errrorMessage,e){
        alert("系统异常！");
    }
});*/
// 关注的人
var saveData={"username":getCookie("username")};
$.ajax({
    url: "/cashsale/getMyConcern",
    type: "get",
    data: saveData,
    contentType: "application/json",
    success: function (result, testStatus) {
        if (result.data.concernNumber != 0) {
            document.getElementById("main3").innerHTML="";
            var str = JSON.stringify(result.data.concernsProductNumber);
            var str1 = str.replace("{","");
            str1 = str1.replace("}","");
            str1 = str1.split(",");
            for(var i = 0; i < str1.length; i++){
                var user = str1[i].substr(1,11);
                var concernNum = str1[i].substring(14);
                innerPerson(user, concernNum);
            }
        } else {
            // alert("没有关注的人");
        }
    },
    error: function (xhr, errrorMessage, e) {
        alert("系统异常！");
    }
});
// 渲染我关注的人
function innerPerson(user, number) {
    var main = document.getElementById("main3");
    var people= document.createElement('div');
    var img = document.createElement('img');
    var name= document.createElement('span');
    var a= document.createElement('a');


    people.setAttribute("id", "people");
    /*people.setAttribute("onclick", "goodsclick(this)");

    img.src = data.imageUrl;*/
    name.innerHTML = user;
    a.innerHTML = "发布商品数："+number;
    people.appendChild(img);
    people.appendChild(name);
    people.appendChild(a);

    //我关注的
    var myConcernName= document.createElement('input');
    myConcernName.setAttribute("id","myConcernName");
    myConcernName.setAttribute("type","hidden");
    myConcernName.setAttribute("value",user);
    people.appendChild(myConcernName);

    //取消关注
    var but=document.createElement('input');

    main.appendChild(people);
}