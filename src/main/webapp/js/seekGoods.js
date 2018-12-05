var msg = "您可以上传png, jpg, 或者gif格式的图片";
var filter = {
    "jpeg": "/9j/4",
    "gif": "R0lGOD",
    "png": "iVBORw"
};
//富文本编辑器
function fU() {
    $.ajax({
        url: "/cashsale/UploadImageServlet",
        type: 'POST',
        cache: false,
        data: new FormData($("#uploadForm")[0]),
        processData: false,
        contentType: false,
        dataType:"json",
        success: function (result) {
            document.getElementById('image').value = result.data;
        },
        error: function (err) {
        }
    });
}
function preview(file) {
    //获取展示图片的容器
    var container = document.getElementById("container");
    //置空
    container.innerHTML = "";
    if (window.FileReader)
    {
        //如果上传图片图片超过1张，直接清理
        if(file.files.length>4){
            var obj=document.getElementById('myfile');
            obj.outerHTML=obj.outerHTML;
            alert("对不起，最多上传一张封面!");
            return;
        }

        //file为input对象
        for (var index=0, f; f = file.files[index]; index++)
        {
            var filereader = new FileReader();
            //文件读取完成时执行
            filereader.onload = function (event) {
                //把源对象的
                var srcpath = event.target.result;//.result把图片编码
                if (!validateImg(srcpath)) {
                    //控制台打印信息
                    console.log("H5"+msg);
                    showPreviewImage(srcpath);
                } else {
                    showPreviewImage(srcpath);
                }
            };
            filereader.readAsDataURL(f);
        }
    }
    //降级处理
    else {
        //匹配jpg/png/gif,如果file.value不是该类型，则打印提示语，否则显示图片
        if (!/\.jpg$|\.png$|\.gif$/i.test(file.value)) {
            console.log("原生"+msg);
        }
        else {
            showPreviewImage(file.value);
        }
    }

}

function validateImg(data) {
    console.log(data);
    var pos = data.indexOf(",") + 1;
    for (var e in filter) {
        if (data.indexOf(filter[e]) === pos) {
            //返回图片编码后类型
            return e;
        }
    }
    return null;
}
//加入图片
function showPreviewImage(src) {
    console.log(src);
    var img = document.createElement('img');
    var div = document.createElement('div');
    img.setAttribute("class", "myfile_img");
    div.setAttribute("id", "imgDelete");
    img.src = src;
    div.innerHTML="点击删除图片";
    container.appendChild(img);
    container.appendChild(div);
}

//删除图片
document.getElementById("container").onclick = function(){
    var obj=document.getElementById('myfile');
    obj.outerHTML=obj.outerHTML;
    container.innerHTML = "";
    //alert(document.getElementById('myfile').files.length);
}
