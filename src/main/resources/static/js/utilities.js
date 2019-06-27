// test localhost
var baseURL = "http://localhost:8080";

// test localhost other device
//var baseURL = "http://192.168.1.20:8080";

function serializeForm(form){
	var formdata = $(form).serializeArray();
	var data = {};
	$(formdata).each(function(index, obj) {
		data[obj.name] = obj.value;
	});

	requestJSON = JSON.stringify(data);
	return requestJSON;
}

function clearData(form){
	$(form).get(0).reset();
	$("textarea").html("");
}

function initTinymce(){
	tinymce.init({
		  selector: 'textarea',
		  height: 500,
		  theme: 'modern',
		  plugins: 'print preview fullpage searchreplace autolink directionality visualblocks visualchars fullscreen image link media template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists textcolor wordcount imagetools contextmenu colorpicker textpattern help',
		  toolbar1: 'formatselect | bold italic strikethrough forecolor backcolor | link | alignleft aligncenter alignright alignjustify  | numlist bullist outdent indent  | removeformat',
		  image_advtab: true,
		  templates: [
		    { title: 'Test template 1', content: 'Test 1' },
		    { title: 'Test template 2', content: 'Test 2' }
		  ],
		  content_css: [
		    '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
		    '//www.tinymce.com/css/codepen.min.css'
		  ],
		  /* without images_upload_url set, Upload tab won't show up*/
		  images_upload_url: baseURL + '/upload',
		  images_upload_handler: function (blobInfo, success, failure) {
			  console.log("asdasds");
			  //debugger;
				var xhr, formData;
				xhr = new XMLHttpRequest();
				xhr.withCredentials = false;
				xhr.open('POST', 'http://localhost:8080/upload');
				xhr.onload = function() {
					  var json;

					  if (xhr.status != 200) {
						failure('HTTP Error: ' + xhr.status);
						return;
					  }
					  json = JSON.parse(xhr.responseText);

					  if (!json || typeof json.location != 'string') {
						failure('Invalid JSON: ' + xhr.responseText);
						return;
					  }
					  success(json.location);
					};
				formData = new FormData();
				formData.append('files', blobInfo.blob());
				xhr.send(formData);
			  }

		 });
}