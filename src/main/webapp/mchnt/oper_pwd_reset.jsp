<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">


</script>
<div>
	<form method="post" id="pwd_form" class="easyui-form" data-options="novalidate:true" >
		<input id="pwd_mchntCd" type="hidden" name="mchntCd">
		<input id="pwd_operId" type="hidden" name="operId">
		<input id="pwd_userId" type="hidden" name="userId">
		<table class="table_info" border="0">
		<tr>
			<td width="100px;">
				<label>
					新密码:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="pwd_newPwd" type="password" name="newPwd" class="easyui-validatebox"
				data-options="required:true" style="width:180px;" validType="pwd"/>
			</td>
			<td class="hintspace">
				<tt>* 长度6到20位<!-- ,允许输入数字和字母 --></tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					重复新密码:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="password" name="reNewPwd" class="easyui-validatebox" validType="pwdEquals['#pwd_newPwd']"
				data-options="required:true" style="width:180px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>

		</table>
	</form>
</div>
