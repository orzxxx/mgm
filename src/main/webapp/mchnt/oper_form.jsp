<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">


</script>
<div>
	<form method="post" id="oper_form" class="easyui-form" data-options="novalidate:true" enctype="multipart/form-data">
		<input id="oper_mchntCd" type="hidden" name="mchntCd">
		<table class="table_info" border="0">
		<tr>
			<td>
				<label>
					操作员号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="operId" class="easyui-validatebox" maxlength="2"
				data-options="required:true,validType:'numbertwo'" style="width:180px;"/>
			</td>
			<td class="hintspace">
				<tt>*两位数字(如01)</tt>
			</td>
		</tr>
		<tr>
			<td width="2" class="hintspace">
				<tt>默认密码为111111</tt>
			</td>
			<td>
			</td>
			<td class="hintspace">
			</td>
		</tr>

		</table>
	</form>
</div>
