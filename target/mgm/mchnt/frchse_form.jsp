<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">


</script>
<div>
	<form method="post" id="frchse_form" class="easyui-form" data-options="novalidate:true" enctype="multipart/form-data">
		<input id="frchse_frchseCd" type="hidden" name="frchseCd">
		<table class="table_info" border="0">
		<tr>
			<td>
				<label>
					手机号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="frchse_userId" type="text" name="userId" class="easyui-numberbox"
				style="width:180px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					商户名:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="frchseName" maxlength="35" class="easyui-validatebox" data-options="required:true" style="width:180px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					姓名:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="frchse_userName" type="text" name="userName" maxlength="15" class="easyui-validatebox" data-options="required:true" style="width:180px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					身份证号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="idCard" maxlength="18" class="easyui-validatebox" data-options="required:true" validType="idCard" style="width:180px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					商店地址:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="mchntAddr" maxlength="35" class="easyui-validatebox" data-options="required:true" style="width:180px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		</table>
	</form>
</div>
