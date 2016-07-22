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
		  <img id="img_picture" style="max-width: 100%;width:400px;height:300px;" src="images/a_code.png">
		  <input id="img_select" type="file" name="picture" style="width:180px;"/>
		  <a id="img_upload" href="javascript:void(0)" class="easyui-linkbutton"  plain="true"">确定</a>
		</div>
	</form>
</div>
