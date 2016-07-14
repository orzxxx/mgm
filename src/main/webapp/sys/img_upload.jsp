<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">
$(function(){
	$('#image').cropper({
		  aspectRatio: 4 / 3,
		  viewMode: 1,
		  crop: function(e) {
			  
		  }
		});
});
function getData(){
	console.log($('#image').cropper('getData'));
}
</script>

<div>
	<form method="post" id="img_form" class="easyui-form  ct-menu-from" data-options="novalidate:true" enctype="multipart/form-data">
		<div>
		  <img id="img_picture" name="picture" style="max-width: 100%;width:400px;height:300px;" src="images/a_code.png">
		  <input id="img_upload" type="file" name="picture" style="width:180px;"/>
		  <button onclick='getData()'>getData</button>
		</div>
	</form>
</div>
