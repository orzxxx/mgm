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
		<div style="float:left;margin-left: 50px;width:400px;height:300px;">
			<div id="img_tips" style="border: 1px solid;width: 400px;height: 300px;line-height:300px;text-align:center;">
				请先上传本地图片
			</div>
		  <img id="img_picture" style="max-width: 400px;width:400px;height:300px;display: none;" src="images/default_menu.png">
		 
		</div>
		<div style="float:left;margin-left: 20px;" class='preview'>
		</div>
		<div style="clear:both;margin-left: 50px;padding-top:40px;width:180px;height:30px;" id="img_buttons">
			 <input id="img_select" type="file" name="picture" style="width:180px;"/>
		 	 <%--<a id="img_upload" href="javascript:void(0)" class="easyui-linkbutton"  plain="true"">确定</a>
		--%></div>
		<div>
		</div>
	</form>
</div>
