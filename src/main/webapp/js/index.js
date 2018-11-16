function goodsclick(str) {
    var goods=str.childNodes;
    var productId=goods[3].value;
<<<<<<< HEAD
=======
    setCookie("productId",productId);
    var saveData={"productId":productId};
>>>>>>> 758d44a44ff6b7f4730c993b256496d710ee246e
    // alert(productId);
    var string="goods.html?productId="+productId
    window.open(string);
}

