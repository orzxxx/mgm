<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">

</script>
<div>
	<form method="post" id="audit_form" class="easyui-form" data-options="novalidate:true" enctype="multipart/form-data">
		<input id="audit_mchntCd" type="hidden" name="mchntCd">
		<table class="table_info" border="0">
		<tr>
			<td width="100px">
				<label>
					手机号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="audit_userId" type="text" name="userId" class="easyui-numberbox" maxlength="11"
				 style="width:220px;"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr>
			<td>
				<label>
					店名:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="mchntName" maxlength="35" class="easyui-validatebox" data-options="required:true" style="width:220px;"/>
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
				<input id="audit_userName" type="text" name="userName" maxlength="15" class="easyui-validatebox" data-options="required:true" style="width:220px;"/>
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
				<input type="text" name="idCard" maxlength="18" class="easyui-validatebox" validType="idCard" data-options="required:true" style="width:220px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					邮箱:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="email" maxlength="60" class="easyui-validatebox" data-options="required:true" validType="email" style="width:220px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					区域信息:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td style="position: relative;">
					<input id="audit_district" readonly name="district" type="text" style="width:220px;" class="easyui-validatebox" data-options="required:true">
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					详细地址:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="detailedAddress" class="easyui-validatebox" maxlength="25" data-options="required:true" style="width:220px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					收款卡号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="inAcct" maxlength="30" class="easyui-validatebox" style="width:220px;"/>
			</td>
			<td class="hintspace">
				<tt>可选</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					支付宝账号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="alipayNo" maxlength="30" class="easyui-validatebox" style="width:220px;"/>
			</td>
			<td class="hintspace">
				<tt>可选</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					微信账号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="wechatNo" maxlength="30" class="easyui-validatebox" style="width:220px;"/>
			</td>
			<td class="hintspace">
				<tt>可选</tt>
			</td>
		</tr>
		</table>
	</form>
</div>
