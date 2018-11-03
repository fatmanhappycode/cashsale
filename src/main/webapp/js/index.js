
window.onload=
    function(){
        var oDiv = document.getElementById("species"),
            H = 0,
            Y = oDiv
        i=0;
        while (Y) {
            H += Y.offsetTop;
            if(i==0){
                H=H+200;
            }
            Y = Y.offsetParent-200;
            i++;
        }
        window.onscroll = function()
        {
            var s = document.body.scrollTop || document.documentElement.scrollTop
            if(s>H) {
                oDiv.style = "position:fixed;top:0px;"
            } else {
                oDiv.style = ""
            }
        }
    }

test.onclick = function(){
    document.body.scrollTop = document.documentElement.scrollTop = 0;
}



