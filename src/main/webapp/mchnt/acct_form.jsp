<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">

</script>
<div>
	<form method="post" id="acct_form" class="easyui-form" data-options="novalidate:true" >
		<input id="acct_mchntCd" type="hidden" name="mchntCd">
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
				<input id="acct_userId" type="text" name="userId" class="easyui-numberbox" maxlength="11"
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
				<input type="text" name="mchntName" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'realLength[20]'" style="width:220px;"/>
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
				<input id="acct_userName" type="text" name="userName" maxlength="15" class="easyui-validatebox" data-options="required:true" style="width:220px;"/>
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
				<input type="text" name="email" maxlength="60" class="easyui-validatebox"   validType="email" style="width:220px;"/>
			</td>
			<td class="hintspace">
				<tt>可选</tt>
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
					<input id="acct_district" readonly name="district" type="text" style="width:220px;" class="easyui-validatebox" data-options="required:true">
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
		<tr >
				<td>
					<label>
						证件类型
					</label>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<input id="acct_license" type="radio" checked name="licenseType" value="1"/><span>营业执照</span>
					<span style="float:right;"><input id="acct_taxCard" type="radio" name="licenseType" value="2"/><span>税务登记证</span></span>
					<br/>
					<input id="acct_orgCode" type="radio" name="licenseType" value="3"/><span>组织机构代码证</span>
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
				<input id="acct_licenseNumber" type="text" name="license" maxlength="20" class="easyui-validatebox" data-options="required:true" validType="license" style="width:220px;"/>
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
