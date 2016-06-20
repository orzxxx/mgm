<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">
requirejs(['audit-submit'],function  (audit) {
	audit.init();
});

</script>
<div>
	<form method="post" id="auditSubmit" class="easyui-form" data-options="novalidate:true" enctype="multipart/form-data">
		<input id="auditSubmit_mchntCd" type="hidden" name="mchntCd">
		<table class="table_info" border="0" style="width:1000px;">
		<tr>
				<td width="100">
				</td>
				<td class="tdspace">
				</td>
				<td>
					<img id="auditSubmit_idCardFrontImg" class="iccardimg" style="visibility:hidden;">
				</td>
				<td class="hintspace">
				</td>
				<td width="100">
				</td>
				<td class="tdspace">
				</td>
				<td>
					<img  id="auditSubmit_idCardBackImg"  class="iccardimg" style="visibility:hidden;">
				</td>
				<td class="hintspace">
				</td>							
			</tr>
			<tr>
				<td>
					<label>
						上传身份证正面
					</label>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<input id="auditSubmit_idCardFront" type="file" name="idCardFront" maxlength="30" class="easyui-validatebox"  validType="image" style="width:180px;"/>
					<tt>双击查看原图</tt>
				</td>
				<td class="hintspace">
				</td>
				<td>
					<label>
						上传身份证背面
					</label>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<input id="auditSubmit_idCardBack" type="file" name="idCardBack" maxlength="30" class="easyui-validatebox"  validType="image" style="width:180px;"/>
					
					<tt>双击查看原图</tt>
				</td>
				<td class="hintspace">
					
				</td>								
			</tr>
			<tr>
				<td width="100">
				</td>
				<td class="tdspace">
				</td>
				<td>
					<img id="auditSubmit_licenseFrontImg" class="licenseimg" style="visibility:hidden;">
				</td>
				<td class="hintspace">
				</td>
				<td width="100">
				</td>
				<td class="tdspace">
				</td>
				<td>
					<img id="auditSubmit_storePhotoImg" class="licenseimg" style="visibility:hidden;">
				</td>
				<td class="hintspace">
				</td>							
			</tr>
			<tr>
				<td>
					<label>
						上传营业执照
					</label>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<input id="auditSubmit_licenseFront" type="file" name="licenseFront" maxlength="30" class="easyui-validatebox"  validType="image" style="width:180px;"/>
					<tt>双击查看原图</tt>
				</td>
				<td class="hintspace">
				</td>
				<td>
					<label>
						上传店铺照片
					</label>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<input id="auditSubmit_storePhoto" type="file" name="storePhoto" maxlength="30" class="easyui-validatebox"  validType="image" style="width:180px;"/>
					<tt>双击查看原图</tt>
				</td>
				<td class="hintspace">
				</td>								
			</tr>
			<tr>
				<td>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<a id="auditSubmit_submit" style="width:100px;" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">提交申请</a>
				</td>
				<td class="hintspace">
				</td>
				<td>
				</td>
				<td class="tdspace">
				</td>
				<td>
				</td>
				<td class="hintspace">
				</td>								
			</tr>
		</table>
		
	</form>
</div>
