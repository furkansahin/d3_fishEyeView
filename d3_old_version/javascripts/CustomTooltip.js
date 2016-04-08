function CustomTooltip(tooltipId, width){
	var tooltipId = tooltipId;
	$("body").append("<div class='tooltip'  id='"+tooltipId+"'><div  id='"+tooltipId+"_content'></div></div>");
	
	if(width){
		$("#"+tooltipId).css("width", width);
	}
	
	hideTooltip();
	
	$("#"+tooltipId).append("<div id='closebtn' class='closebtn'><img src=\"images/close-button3.png\" width=\"19\" height=\"19\"></div>");
	$("#closebtn").click(function(){hideTooltip();});
	
	
	
	function showTooltip(content, event){
		
			
		$("#"+tooltipId + "_content").html(content);	
		$("#"+tooltipId).show();
		updatePosition(event);
	}
	
	function hideTooltip(){
		$("#"+tooltipId).hide();
	}
	
	function updatePosition(event){
		var ttid = "#"+tooltipId;
		var xOffset = 20;
		var yOffset = 10;
		
		 var ttw = $(ttid).width();
	
		 var tth = $(ttid).height();
		 var wscrY = $(window).scrollTop();
		 var wscrX = $(window).scrollLeft();
		 var curX = (document.all) ? event.clientX + wscrX : event.pageX;
		 var curY = (document.all) ? event.clientY + wscrY : event.pageY;
		 
	
		 
		 
		 
		 var ttleft = ((curX - wscrX + xOffset*2 + ttw) > $(window).width()) ? curX - ttw - xOffset*2 : curX + xOffset;
		 if (ttleft < wscrX + xOffset){
		 	ttleft = wscrX + xOffset;
		 } 
		 var tttop = ((curY - wscrY + yOffset*2 + tth) > $(window).height()) ? curY - tth/2 - yOffset*2 : curY + yOffset;
		 		 
		 if (tttop < wscrY + yOffset){
		 	tttop = curY + yOffset;
		 }

		 $(ttid).css('top', tttop + 'px').css('left', ttleft + 'px');
		 
		 $("#closebtn").css('top', '0px').css('left', (ttw-9) + 'px');
	}
	
	return {
		showTooltip: showTooltip,
		hideTooltip: hideTooltip,
		updatePosition: updatePosition
	}
}
