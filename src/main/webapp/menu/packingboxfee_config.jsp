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
			<td width="100px">
				<label>
					打包盒费(元):
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="fee_fee" type="text" name="userId" class="easyui-numberbox" maxlength="5"
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
		
		<%--批量打包盒设置--%>
	<form style="margin-top: 50px;" method="post" id="num_form" class="easyui-form" data-options="novalidate:true" >
	<hr size="1" color="#95B8E7"> 
		<table class="table_info" border="0" style="width:500px;">
		<tr>
			<td width="100px" colspan="4">
				<label>
					<input id="num_switch" type="checkbox" style="width:16px;height:16px;"/>
					打包盒数批量设置
				</label>
				<span style="color: #cc0000;font-size: 12px;font-weight: bold;">
					*使用该功能将批量设置单品和套餐的打包盒份数
				</span>
			</td>
		</tr>
		<tr>
			<td width="100px">
				<label>
					打包盒份数:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="num_num" type="text" name="userId" class="easyui-numberbox" maxlength="2" value='0' 
				data-options="required:true" style="width:180px;"/>
				<span></span>
			</td>
			<td class="hintspace">
				<tt>*范围0到99</tt>
			</td>
		</tr>
		<tr>
			<td>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<a id="num_edit" href="javascript:void(0);" style="width:100px;" class="easyui-linkbutton ct-qry-btn">批量设置</a>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		</table>
	</form>
</div>
