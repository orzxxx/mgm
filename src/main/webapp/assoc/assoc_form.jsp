<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">


</script>
<div>
	<form method="post" id="assoc_form" class="easyui-form ct-menu-from" data-options="novalidate:true" enctype="multipart/form-data">
		<input id="assoc_mchntCd" type="hidden" name="mchntCd">
		<table class="table_info" border="0">
		<tr>
			<td width="100">
				<label>
					关联账号
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="assoc_frchseCd" type="text" name="frchseCd" maxlength="30" class="easyui-validatebox" data-options="required:true" style="width:260px;" />
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td style="text-align: left;">
				<a style="width: 80px;" id="assoc_query" href="javascript:void(0)" class="easyui-linkbutton ct-rst-btn"  plain="true"">查询</a>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr>
			<td>
				<label>
					手机号
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="assocForm_userId" type="text" name="userId" class="easyui-numberbox" readonly="readonly"
				style="width:260px;"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr>
			<td>
				<label>
					商户名
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="frchseName" readonly="readonly" maxlength="35" class="easyui-validatebox" style="width:260px;"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr>
			<td>
				<label>
					姓名
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="userName" readonly="readonly" maxlength="15" class="easyui-validatebox" style="width:260px;"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr>
			<td>
				<label>
					身份证号
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="idCard" readonly="readonly" maxlength="18" class="easyui-validatebox" style="width:260px;"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr>
			<td>
				<label>
					商店地址
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="mchntAddr" readonly="readonly" maxlength="35" class="easyui-validatebox" style="width:260px;"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>

		</table>
	</form>
</div>
