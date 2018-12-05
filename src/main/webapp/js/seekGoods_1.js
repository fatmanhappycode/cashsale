//点击提交时
var label="";
function f() {
    var commodity = $("#commodity").val();
    var image = $("#image").val();

//	复选框加入字符串label
    var label="";
    if($("#myCheckbox21").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox21").val();
        else
            label=label+$("#myCheckbox21").val();
    }
    if($("#myCheckbox22").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox22").val();
        else
            label=label+$("#myCheckbox22").val();
    }
    if($("#myCheckbox23").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox23").val();
        else
            label=label+$("#myCheckbox23").val();
    }
    if($("#myCheckbox24").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox24").val();
        else
            label=label+$("#myCheckbox24").val();
    }

    if($("#myCheckbox25").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox25").val();
        else
            label=label+$("#myCheckbox25").val();
    }
    if($("#myCheckbox26").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox26").val();
        else
            label=label+$("#myCheckbox26").val();
    }
    if($("#myCheckbox27").prop('checked')){
        if(label!="")
            label=label+";"+$("#myCheckbox27").val();
        else
            label=label+$("#myCheckbox27").val();
    }

    //alert(label);
    if(label=="" ||commodity==""||document.getElementById("myfile").files.length==0){
        alert("请填写完信息再提交，否则将清空！");
    }else{
        document.getElementById("passwordItem");
        var token=getCookie("token");
        var saveData={"title":commodity,"label":label,"price":price,"tradeMethod":myCheckbox0,"isBargain":myCheckbox1,"tradePlace":place,"pdDescription":editor.txt.html(), "imageUrl":image};
        $.ajax({
            url:"/cashsale/publish",
            type:"post",
            dataType:"json",
            async: false,
            contentType:"application/json;charset=UTF-8",
            data:JSON.stringify(saveData),
            headers: {
                "token":token,
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
    var commodity = $("#commodity").val();

//	复选框加入字符串label
    if($("#myCheckbox21").prop('checked')){
        label=label+";"+$("#myCheckbox21").val();
    }
    if($("#myCheckbox22").prop('checked')){
        label=label+";"+$("#myCheckbox22").val();
    }
    if($("#myCheckbox23").prop('checked')){
        label=label+";"+$("#myCheckbox23").val();
    }
    if($("#myCheckbox24").prop('checked')){
        label=label+";"+$("#myCheckbox24").val();
    }

    if($("#myCheckbox25").prop('checked')){
        label=label+";"+$("#myCheckbox25").val();
    }
    if($("#myCheckbox26").prop('checked')){
        label=label+";"+$("#myCheckbox26").val();
    }
    if($("#myCheckbox27").prop('checked')){
        label=label+";"+$("#myCheckbox27").val();
    }

    if(label=="" ||commodity==""||document.getElementById("myfile").files.length==0){
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