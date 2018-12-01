var msg = "您可以上传png, jpg, 或者gif格式的图片";
var filter = {
    "jpeg": "/9j/4",
    "gif": "R0lGOD",
    "png": "iVBORw"
};
function preview(file) {
    //获取展示图片的容器
    var container = document.getElementById("container");
    //置空
    container.innerHTML = "";
    if (window.FileReader)
    {
        //如果上传图片图片超过1张，直接清理
        if(file.files.length>1){
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
//我的相册
function preview_1(file) {
    //获取展示图片的容器
    var container = document.getElementById("container1");
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
            alert(f.name);
            var name=f.name;
            var filereader = new FileReader();
            //文件读取完成时执行
            filereader.onload = function (event) {
                //把源对象的
                var srcpath = event.target.result;//.result把图片编码
                if (!validateImg(srcpath)) {
                    //控制台打印信息
                    console.log("H5"+msg);
                    showPreviewImage_1(srcpath,name);
                } else {
                    showPreviewImage_1(srcpath,name);
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
            showPreviewImage_1(file.value,f.name);
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
    var container = document.getElementById("container");
    container.appendChild(img);
    container.appendChild(div);
}
//加入图片2
i=0;
function showPreviewImage_1(src,name) {
    console.log(src);
    var img = document.createElement('img');
    var div = document.createElement('div');
    img.setAttribute("class", "myfile_img");
    div.setAttribute("class", "imgDelete1");
    div.setAttribute("onclick", "deleImg(this,'"+name+"')");
    img.src = src;
    var container = document.getElementById("container1");
    div.appendChild(img);
    container.appendChild(div);
}

//删除图片
document.getElementById("container").onclick = function(){
    var obj=document.getElementById('myfile');
    obj.outerHTML=obj.outerHTML;
    container.innerHTML = "";
    //alert(document.getElementById('myfile').files.length);
}
var n=3;
function deleImg(s,name){
    var container = document.getElementById("container1");
    container.removeChild(s);


    var files=document.getElementById("myfile1").files;
    if (files && files.length) {
        var file1=[];
    // 原始FileList对象不可更改，所以将其赋予curFiles提供接下来的修改
        Array.prototype.push.apply(file1, files);
        // 去除该文件
        file1 = file1.filter(function(file) {
            return file.name !== name;
        });
        document.getElementById("myfile1").files=file1;
    }
}