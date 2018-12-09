function InChat() {
    var username1=document.getElementById("username");
    var username2=getCookie("username");
}


// 获取评论内容
getComments();
function getComments() {
    page=1;
    var saveData = {"productId":window.location.href.split("=")[1],"page":page};
    $.ajax({
        url:"/cashsale/getInteract",
        type:"get",
        data:saveData,
        contentType:"application/json;charset=UTF-8",
        headers:{
            contentType:"application/json;charset=UTF-8"
        },
        success:function(result,testStatus)
        {
            if (result.data2.comments != "") {
                data1=result.data2.comments;
                innerComments(data1);


            } else if (result.code == "401") {
                alert("加入失败");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！"+e+"\n"+errrorMessage);
        }
    });
}
// 渲染内容
function innerComments(data) {
    $("#myComment").html("");
    for(var i=0;i<data.length;i++){
        //获取容器
        var ul = document.getElementById("myComment");
        //生成盒子
        var li= document.createElement('li');
        var img = document.createElement('img');
        var h4= document.createElement('h4');
        var p= document.createElement('p');
        var span= document.createElement('span');

        // img.src = obj.imageUrl;
        img.src ="img/icon.jpg";
        h4.innerHTML=data[i].userName;
        p.innerHTML=data[i].comments;
        span.innerHTML=data[i].commentsTime;


        li.appendChild(img);
        li.appendChild(h4);
        li.appendChild(span);
        li.appendChild(p);
        ul.appendChild(li);
    }
}

// 发布评论
function setComments() {
    page=1;
    var comments=document.getElementById("message").value;
    if(comments==""||comments==undefined||comments==null){
        alert("内容不能为空！");
        return ;
    }
    var saveData = {"productId":window.location.href.split("=")[1],"scoreCode":"F","comments":comments};
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
            if (result.code == "200") {
                getComments();
            } else if (result.code == "401") {
                alert("加入失败");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！"+e+"\n"+errrorMessage);
        }
    });
}
// 点赞时
function clicklikeNumber() {
    var saveData = {"productId":window.location.href.split("=")[1],"scoreCode":"F","comments":comments};
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
            if (result.code == "200") {
                getComments();
            } else if (result.code == "401") {
                alert("加入失败");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！"+e+"\n"+errrorMessage);
        }
    });
}
// 转发时
function clickshareNumber() {
    var saveData = {"productId":window.location.href.split("=")[1],"scoreCode":"F","comments":comments};
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
            if (result.code == "200") {
                getComments();
            } else if (result.code == "401") {
                alert("加入失败");
            }
        },
        error:function(xhr,errrorMessage,e){
            alert("系统异常！"+e+"\n"+errrorMessage);
        }
    });
}