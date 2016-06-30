<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">


</script>
<div>
	<form method="post" id="menuAttr_form" class="easyui-form  ct-menu-from" data-options="novalidate:true" >
		<input id="menu_mchntCd" type="hidden" name="mchntCd">
		<input id="menu_productId" type="hidden" name="productId">
		<input id="menu_pictureLink" type="hidden" name="pictureLink">
		<input id="menu_inventoryId" type="hidden" name="inventory.productId">
		<table class="table_info" border="0" style="width:370px;">
			<tr>
				<td valign="top" >
					<label>
						属性名
					</label>
				</td>
				<td valign="top" class="tdspace">
				</td>
				<td style="width:420px;text-align: left;">
					<input id="menuAttr_newAttr" type="text" maxlength="8" class="easyui-validatebox" style="width:120px;"/>
					<a id="menuAttr_addAttr" href="javascript:void(0)" style="width:100px;" class="easyui-linkbutton ct-rst-btn" iconCls="icon-add" plain="true">添加新属性</a>
				</td>
			</tr>
		</table>
		<br/>
		<hr>
			<%--<div>
				<table>
					<tr>
						<td style="width:400px;">
							<label>属性名:</label>
							<input type="text" maxlength="8" class="easyui-validatebox" style="width:120px;"/>
						</td>
						<td class="tdspace">
						</td>
						<td style="width:400px;">
							<a href="javascript:void(0)" style="width:100px;" class="easyui-linkbutton ct-rst-btn" iconCls="icon-add" plain="true"">添加属性值</a>
							<a href="javascript:void(0)" class="easyui-linkbutton ct-rst-btn" iconCls="icon-remove" plain="true"">删除</a>
						</td>
						<td class="hintspace"></td>				
					</tr>
					<tr>
						<td style="width:400px;text-align: right;">
							<input type="text" maxlength="8" class="easyui-validatebox" style="width:120px;"/>
						</td>
						<td class="tdspace">
						</td>
						<td style="width:400px;">
							<label>价格:</label>
							<input type="text" maxlength="8" class="easyui-validatebox" style="width:60px;"/>
							<a href="javascript:void(0)" class="easyui-linkbutton ct-rst-btn" iconCls="icon-remove" plain="true"">删除</a>
						</td>
						<td class="hintspace"></td>				
					</tr>
				</table>
			</div>
	--%></form>
</div>
