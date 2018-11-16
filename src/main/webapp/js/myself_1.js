var n=0;
window.onscroll = function()
{
    oDiv=document.getElementById("self_left")
    var H =document.getElementById("self_left").offsetTop;
    var s = document.body.scrollTop || document.documentElement.scrollTop;
    console.log(H+"  "+s);
    if(s>=304) {
        oDiv.style = "position:fixed;top:-100px;left:40px;"
    } else{
        oDiv.style = "";
    }
}
test.onclick = function(){
     document.body.scrollTop = document.documentElement.scrollTop = 0;
}



    
    
    
