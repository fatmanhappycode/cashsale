
    window.onload=
        function(){
            var oDiv = document.getElementById("self_left"),
                H = 0,
                Y = oDiv  
                i=0;
            while (Y) {
                H += Y.offsetTop; 
                if(i==0){
                	H=H+200;
                }
                Y = Y.offsetParent;
                i++;
            }
            window.onscroll = function()
            {
                var s = document.body.scrollTop || document.documentElement.scrollTop
                if(s>H) {
                    oDiv.style = "position:fixed;top:0px;left:70px;"
                } else {
                    oDiv.style = ""
                }
            }
        }

    test.onclick = function(){
        document.body.scrollTop = document.documentElement.scrollTop = 0;
    }
    
    
    
