$(document).ready(function(){
	
	//default loading
	//$('#patient-info-content').load('/admin/medication-for-patient');
	
	//Handle tab click
	$("#patient-info-nav ul li a").click(function(){
		var section = $(this).attr("href");
		$("#patient-info-content").load("patient-info.html #" + section);
		//alert("OK");
		//Prevent default action
		return false;
	});
});

