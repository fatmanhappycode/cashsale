//点击提交时
function f() {
    var commodity="";
    var name="";
    commodity = $("#commodity").val();
    name= $("#name").val();
    var image = $("#image").val();

    if(name=="" ||commodity==""||document.getElementById("myfile").files.length==0){
        alert("请填写完信息再提交，否则将清空！");
    }else{
        document.getElementById("passwordItem");
        var saveData={"title":commodity,"pdDescription":editor.txt.html(),"imageUrl":image};
        $.ajax({
            url:"/cashsale/publishDemand",
            type:"post",
            dataType:"json",
            async: false,
            contentType:"application/json;charset=UTF-8",
            data:JSON.stringify(saveData),
            headers: {
                contentType:"application/json;charset=UTF-8"
            },
            success:function(result,testStatus)
            {
                if(result.code==200)
                {
                    //发布成功，转跳到页面
                    alert(result.msg);
                    window.location.href="/cashsale/index.html";
                }
                else if(result.code==500){
                    //失败
                    alert(result.msg);
                }else if (result.code==409) {
                    alert(result.msg);
                } else{
                    //请先登录
                    alert(result.msg);
                    window.location.href="login.html";
                }
            },
            error:function(xhr,errrorMessage,e){
                console.log(errrorMessage);
            }
        });
    }

}
//不能有空 提示语
function item() {
    var commodity="";
    var name="";
    commodity = $("#commodity").val();
    name= $("#name").val();

    if(name=="" ||commodity==""||document.getElementById("myfile").files.length==0){
        document.getElementById("passwordItem").style.display="block";
    }else{
        document.getElementById("passwordItem").style.display="none";
    }
}
//重置时
function clearimg() {
    //获取展示图片的容器
    var container = document.getElementById("container");
    //置空
    container.innerHTML = "";
}