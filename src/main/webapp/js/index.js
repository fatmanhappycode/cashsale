function goodsclick(str) {
    var goods=str.childNodes;
    var productId=goods[3].value;
    // alert(productId);
    var string="goods.html?productId="+productId
    window.open(string);
}

