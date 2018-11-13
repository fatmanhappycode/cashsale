
            var n=0;
            window.onscroll = function()
            {
                oDiv=document.getElementById("self_left")
                var H =document.getElementById("self_left").offsetTop+n+42;
                var s = document.body.scrollTop || document.documentElement.scrollTop;
                console.log(H+"  "+s);
                if(s>H) {
                    oDiv.style = "position:fixed;top:0px;left:62px;"
                    n=304;
                } else if(s<=362) {
                    oDiv.style = "";
                    n=0;
                }
            }


    $("#test").onclick = function(){
        document.body.scrollTop = document.documentElement.scrollTop = 0;
    }
    
    
    
