var isUpdate = false;

$(document).ready(function() {
	updateTable();
	initTinymce();
});

function deleteItem(id) {
	var confirmDelete = confirm("Bạn có muốn xóa bài viết có id " + id + "?");
	if(confirmDelete){
		$.ajax({
			type : "DELETE",
			url : baseURL + "/post/" + id,
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

function addItem() {
	requestJSON = serializeForm("#addForm");
	
	// @Todo: serialize specific field as Object in Java
	var obj = JSON.parse(requestJSON)
	var category = {"id" : obj.category};
	var author = {"id" : obj.author};
	obj.category = category;
	obj.author = author;
	requestJSON = JSON.stringify(obj);
	
	$.ajax({
		type : "POST",
		url : baseURL + "/post",
		headers : {
			"Content-Type" : "application/json"
		},
		data : requestJSON,
		success : function(data) {
			$('#modal').modal('hide');
			$.notify("Thêm thành công", "success");
			updateTable();
		},
		error : function(data) {
			$.notify("Thêm thất bại", "error");
		}
	});
}

function updateItem(id) {
	//tinyMCE.triggerSave();
	requestJSON = serializeForm("#addForm");

	// @Todo: serialize specific field as Object in Java
	var obj = JSON.parse(requestJSON)
	var category = {"id" : obj.category};
	var author = {"id" : obj.author};
	var content = tinyMCE.get('content').getContent()
	obj.category = category;
	obj.author = author;
	obj.content = content;
	requestJSON = JSON.stringify(obj);
	console.log("requestJSON", requestJSON);
	$.ajax({
		type : "PUT",
		url : baseURL + "/post/" + id,
		headers : {
			"Content-Type" : "application/json"
		},
		crossDomain: true,
		data : requestJSON,
		success : function() {
			$('#modal').modal('hide');
			$.notify("Cập nhật thành công", "success");
			updateTable();
		},
		error : function(data) {
			$.notify("Cập nhật thất bại", "error");
		}
	});

}

function updateTable(){
	$('#records_table').empty();
	clearData("#addForm");
	
	$.getJSON(baseURL + "/post/all", function(result) {
		for(var item of result) {
			
			var generateButton = '<div class="dropdown">' +
			  '<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
			    'Hành động' +
			  '</button>' +
			  '<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">' +
			    '<a class="dropdown-item" href="#" onclick="deleteItem(' + item.id + ');">Xóa</a>' +
			    '<a class="dropdown-item" href="#" onclick="show(' + item.id + ');">Chỉnh sửa</a>' +
			  '</div>' +
			'</div>';
			
			$('<tr id="' + item.id + '">').append(
		            $('<td>').text(item.id),
		            $('<td>').text(item.title),
		            $('<td>').text(item.content.substring(0, 100) + '...'),
		            $('<td class="text-right">').append(
		            		generateButton
		            )
		        ).appendTo('#records_table');
		}
	});
}

function show(id) {
	isUpdate = true;
	$.ajax({
		type : "GET",
		url : baseURL + "/post/" + id,
		headers : {
			"Content-Type" : "application/json"
		},
		success : function(data) {
			$.getJSON(baseURL + "/category/all", function(result) {
				for(category of result){
					if(data.category.id === category.id){
						$('<option value="'+ category.id +'" selected>').text(category.name).appendTo('#category');
					}else{
						$('<option value="'+ category.id +'">').text(category.name).appendTo('#category');
					}
				}
			});
			$('#id').val(data.id);
			$('#title').val(data.title);
			//$('textarea#content').val(data.content);
			tinyMCE.activeEditor.setContent(data.content);
			$('#modal').modal('show');
		},
		error : function(data) {
			$.notify("Tải thông tin thất bại", "error");
		}
	});
}

function triggerAdd(){
	isUpdate = false;
	clearData("#addForm");
	$.getJSON(baseURL + "/category/all", function(result) {
		for(category of result){
			$('<option value="'+ category.id +'">').text(category.name).appendTo('#category');
		}
	});
}

function saveOrUpdate(){
	if(isUpdate === true){
		var id = $('#id').val();
		console.log("id", id);
		updateItem(id);
	}else{
		addItem();
	}
}
