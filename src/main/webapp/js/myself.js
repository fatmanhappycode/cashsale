function myselfBG(s,s1,s2) {
	var img1 = document.getElementById("myself_back"+s);
	var img2 = document.getElementById("myself_back"+s1);
	var img3 = document.getElementById("myself_back"+s2);
	img1.style.opacity="1";
	img2.style.opacity="0";
	img3.style.opacity="0";
}


function selectCard(s,s1,s2,s3) {
	var card1 = document.getElementById("main"+s);
	var card2 = document.getElementById("main"+s1);
	var card3 = document.getElementById("main"+s2);
	var card4 = document.getElementById("main"+s3);
	card1.style.display="block";
	card2.style.display="none";
	card3.style.display="none";
	card4.style.display="none";
	
	var card1 = document.getElementById("card"+s);
	var card2 = document.getElementById("card"+s1);
	var card3 = document.getElementById("card"+s2);
	var card4 = document.getElementById("card"+s3);
	card1.style="color:#fff;background:rgba(166,128,226,.5);";
	card2.style="color:#000;background:#FFF;";
	card3.style="color:#000;background:#FFF;";
	card4.style="color:#000;background:#FFF;";
}


$(window).scroll(function(){
	var winScrollHeight=$(window).scrollTop();//滚动条滚动距离
	if(winScrollHeight>=100){
		document.getElementById("test").style.display="block";
	}else{
		document.getElementById("test").style.display="none";
	}
});




