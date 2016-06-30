<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">
requirejs(['packingboxfee'],function  (fee) {
	fee.init();
});

</script>
<div>
	<form method="post" id="fee_form" class="easyui-form" data-options="novalidate:true" >
		<input id="fee_mchntCd" type="hidden" name="mchntCd">
		<input id="fee_id" type="hidden" name="uuid">
		<table class="table_info" border="0" style="width:500px;">
		<tr>
			<td>
				<label>
					打包盒费(元):
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="fee_fee" type="text" name="userId" class="easyui-numberbox" 
				data-options="required:true,precision:2" style="width:180px;"/>
				<span></span>
			</td>
			<td class="hintspace">
				<tt>*范围0到99.99, 可精确到2位小数</tt>
			</td>
		</tr>
		<tr>
			<td>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<a id="fee_edit" href="javascript:void(0);" style="width:100px;" class="easyui-linkbutton ct-qry-btn">保存设置</a>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		</table>
		
	</form>
</div>
