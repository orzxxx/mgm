<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">
requirejs(['mchnt-logo'],function  (logo) {
	logo.init();
});

</script>
<div>
	<form method="post" id="mchntLogo_form" class="easyui-form" data-options="novalidate:true" enctype="multipart/form-data">
		<input id="mchntLogo_mchntCd" type="hidden" name="mchntCd">
		<table class="table_info" border="0">
		<tr>
				<td class="tdspace">
				</td>
				<td width="100">
					<span id="mchntLogo_hint">还未上传logo</span>
					<img id="mchntLogo_img" class="logoimg" style="display:none;">
				</td>
				<td class="hintspace" width="100px;">
				</td>				
			</tr>
			<tr>
				<td class="tdspace">
				</td>
				<td>
					<input id="mchntLogo_picture" type="file" name="picture" maxlength="30" class="easyui-validatebox" validType="image" style="width:180px;"/>
					</br></br></br>
					<tt optional="true">*上传长宽比1:1的图片获取最佳显示效果</tt> 
					
				</td>
				<td class="hintspace">
				</td>				
			</tr>
		</table>
	</form>
</div>
