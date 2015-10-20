
$(document).ready(function() {
	
	$("#procBtn").click(function(){
		var dir = $("#dir").val();
		
		if(dir == null || dir == ""){
			$("#dir").focus();
			return;
		}
		
		var param = new Object();
		param.dir = dir;
		
		$.ajax({
			type : "POST",
			url : "../api/speech/process",
			data : JSON.stringify(param),
			contentType : "application/json",
			success : function(status) {
				
			},
			error : function(e) {
				console.debug(e.responseText);
			}
		});
	});
	
});