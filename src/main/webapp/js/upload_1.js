var msg = "您可以上传png, jpg, 或者gif格式的图片";
var filter = {
    "jpeg": "/9j/4",
    "gif": "R0lGOD",
    "png": "iVBORw"
};
function f() {
    $.ajax({
        url: "/UploadImageServlet",
        type: 'POST',
        cache: false,
        data: new FormData($("#uploadForm")[0]),
        processData: false,
        contentType: false,
        success: function (result) {
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
    	//如果上传图片图片超过6张，直接清理
    	if(file.files.length>=7){
    		var obj=document.getElementById('myfile');
    		obj.outerHTML=obj.outerHTML;
    		alert("对不起，最多上传六张图片!");
    		return;
    	}
    	//file为input对象
        for (var index=0, f; f = file.files[index]; index++) 
        {
            var filereader = new FileReader();
            filereader.onloadstart = function (e){
            	var num=e.target.files.length ;
            	alert(num);
            	if(num=6){
            		alert();
            	}
            }
            //文件读取完成时执行
            filereader.onload = function (event) {
            	//把源对象的
                var srcpath = event.target.result;//.result把图片编码
                if (!validateImg(srcpath)) {
                	//控制台打印信息
                    console.log("H5"+msg);
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
    console.log("我的"+data);
    var pos = data.indexOf(",") + 1;
    for (var e in filter) {
        if (data.indexOf(filter[e]) === pos) {
        	//返回图片编码后类型
            return e;
        }
    }
    return null;
}

function showPreviewImage(src) {
    console.log(src);

    /*var div = document.createElement('div');
    div.style = "width:400px;height:auto;border:1px solid #FAC;";
    container.appendChild(div);*/
    var img = document.createElement('img');
    img.src = src;
    img.style = "width:100px;height:100px;padding:10px;"
    container.appendChild(img);
}
