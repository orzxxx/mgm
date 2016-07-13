<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">


</script>
<div>
	<form method="post" id="mchnt_form" class="easyui-form" data-options="novalidate:true" >
		<input id="mchnt_mchntCd" type="hidden" name="mchntCd">
		<input id="mchnt_frchseCd" type="hidden" name="frchseCd">
		<table class="table_info" border="0">
		<tr>
			<td width="100px;">
				<label>
					手机号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="mchnt_userId" type="text" name="userId" maxlength="11" class="easyui-validatebox" data-options="required:true,validType:'mobile'" style="width:220px;"/>
			</td>
			<td class="hintspace">
				<tt>*11位数字</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					店铺名称:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="mchntName" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'realLength[20]'" style="width:220px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
				<tr>
			<td>
				<label>
					密码:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="mchnt_passwd"  type="password" name="passwd" maxlength="30" class="easyui-validatebox" data-options="required:true" style="width:220px;" validType="pwd"/>
			</td>
			<td class="hintspace">
				<tt>*6位到20位,允许输入数字和字母</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					重复密码:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="mchnt_repasswd"  type="password" name="repasswd" maxlength="30" class="easyui-validatebox" validType="pwdEquals['#mchnt_passwd']" data-options="required:true" style="width:220px;"/>
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
				<input type="text" name="userName" maxlength="15" class="easyui-validatebox" data-options="required:true" style="width:220px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					身份证:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="idCard" maxlength="18" class="easyui-validatebox" data-options="required:true" validType="idCard" style="width:220px;"/>
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
				<input type="text" name="email" maxlength="60" class="easyui-validatebox"  validType="email" style="width:220px;"/>
			</td>
			<td class="hintspace">
				<tt>可选</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					地区:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td style="position: relative;">
					<input id="mchnt_district" readonly name="district" type="text" style="width:220px;" class="easyui-validatebox" data-options="required:true">
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
				<input type="text" maxlength="60" name="detailedAddress" class="easyui-validatebox" data-options="required:true" style="width:220px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr >
				<td>
					<label>
						证件类型
					</label>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<input id="mchnt_license" type="radio" checked name="licenseType" value="1"/><span>营业执照</span>
					<span style="float:right;"><input id="mchnt_taxCard" type="radio" name="licenseType" value="2"/><span>税务登记证</span></span>
					<br/>
					<input id="mchnt_orgCode" type="radio" name="licenseType" value="3"/><span>组织机构代码证</span>
				</td>
				<td class="hintspace">
					<tt>*</tt> 
				</td>				
			</tr>
		<tr>
			<td>
				<label>
					证件号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="mchnt_licenseNumber" type="text" name="license" maxlength="20" class="easyui-validatebox" data-options="required:true" validType="license" style="width:220px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		</table>
	</form>
</div>
