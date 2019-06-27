var isUpdate = false;

$(document).ready(function() {
	updateTable();
});

function deleteCategory(id) {
	var confirmDelete = confirm("Bạn có muốn xóa thể loại có id " + id + "?");
	if(confirmDelete){
		$.ajax({
			type : "DELETE",
			url : baseURL + "/category/" + id,
			headers : {
				"Content-Type" : "application/json"
			},
			success : function() {
				$.notify("Xóa thành công", "success");
				$("#" + id).fadeOut("slow", "swing", $("#" + id).remove());
			},
			error : function(data) {
				$.notify("Xóa thất bại", "error");
			}
		});
	} 
}

function addCategory() {
	requestJSON = serializeForm("#addCategoryForm");

	$.ajax({
		type : "POST",
		url : baseURL + "/category",
		headers : {
			"Content-Type" : "application/json"
		},
		data : requestJSON,
		success : function(data) {
			$('#modalCategory').modal('hide');
			$.notify("Thêm thành công", "success");
			updateTable();
		},
		error : function(data) {
			$.notify("Thêm thất bại", "error");
		}
	});
}

function updateCategory(id) {
	requestJSON = serializeForm("#addCategoryForm");

	$.ajax({
		type : "PUT",
		url : baseURL + "/category/" + id,
		headers : {
			"Content-Type" : "application/json"
		},
		data : requestJSON,
		success : function() {
			$('#modalCategory').modal('hide');
			$.notify("Cập nhật thành công", "success");
			updateTable();
		},
		error : function(data) {
			alert("fail");
		}
	});

}

function updateTable(){
	$('#records_table').empty();
	clearData("#addCategoryForm");
	$.getJSON(baseURL + "/category/all", function(result) {
		for(var category of result) {
			var btnDelete = '<button style = "margin-right:20px" onclick="deleteCategory(' + category.id + ');" class="btn btn-danger" >Xóa</button>';
			var btnEdit = '<button onclick="showCategory(' + category.id + ');" class="btn btn-success" >Chỉnh sửa</button>';
			$('<tr id="' + category.id + '">').append(
		            $('<td>').text(category.id),
		            $('<td>').text(category.name),
		            $('<td class="text-right">').append(
		            		btnDelete + btnEdit
		            )
		        ).appendTo('#records_table');
		}
	});
}

function showCategory(id) {
	isUpdate = true;
	$.ajax({
		type : "GET",
		url : baseURL + "/category/" + id,
		headers : {
			"Content-Type" : "application/json"
		},
		success : function(data) {
			$('#categoryID').val(data.id);
			$('#categoryName').val(data.name);
			$('#modalCategory').modal('show');
		},
		error : function(data) {
			alert("fail");
		}
	});
}

function triggerAdd(){
	isUpdate = false;
}

function saveOrUpdateCategory(){
	if(isUpdate === true){
		var id = $('#categoryID').val();
		updateCategory(id);
	}else{
		addCategory();
	}
}
