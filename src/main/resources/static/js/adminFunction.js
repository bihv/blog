$(document).ready(function() {
	$.getJSON(baseURL + "/category/amount", function(result) {
		$("#totalCategory").text(result);
	});
	
	$.getJSON(baseURL + "/post/amount", function(result) {
		$("#totalPost").text(result);
	});
});