function setFocus1()
{
    document.getElementById('price1').focus();
}
function setFocus2()
{
    document.getElementById('price2').focus();
}

if(getCookie("usernme")!=""&&getCookie("username")!=null) {
    $("#username").html(getCookie("username"));
}


//回车事件
function enterSubmit(obj) {
    var button = document.getElementById('mySearch_img');
    //enter按键的keyCode编码为13
    if (obj.keyCode == 13) {
        button.click();
    }
}