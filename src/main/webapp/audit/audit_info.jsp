<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">
requirejs(['audit-info'],function  (audit) {
	audit.init();
});

</script>
<div>
	<form method="post" id="auditInfo" class="easyui-form info_from" data-options="novalidate:true" >
		<input id="auditInfo_mchntCd" type="hidden" name="mchntCd">
		<table class="table_info" border="0" style="width:700px;">
		<tr>
			<td width="100px;">
				<label>
					手机号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<span style="width:320px;"></span>
				<input type="text" name="userId"  style="width:320px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
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
				<input type="text" name="mchntName" style="width:320px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
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
				<input type="text" name="userName" style="width:320px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
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
				<input type="text" name="idCard" style="width:320px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
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
				<input type="text" name="email" style="width:320px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr>
			<td valign='top'>
				<label>
					商店地址:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<textarea rows='4' name="mchntAddr" style="width:320px;" readonly unselectable="on"></textarea>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr>
			<td>
				<label id="audit_license_label">
					证件号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="license" style="width:320px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
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
				<input type="text" name="inAcct" style="width:320px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
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
				<input type="text" name="alipayNo" style="width:320px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
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
				<input type="text" name="wechatNo" style="width:320px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr>
			<td>
				
			</td>
			<td class="tdspace">
			</td>
			<td>
				<a id="audit_edit" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn" style="width:100px;display: none;">进行修改</a>
				<a id="audit_submit" style="width:100px;display: none;" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">提交申请</a>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		</table>
		
	</form>
</div>
