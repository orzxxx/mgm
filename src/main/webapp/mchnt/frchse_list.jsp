<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['frchse'],function  (frchse) {
			frchse.init();
		});
</script>
<div>
		<form method="post" id="frchseInfo" class="easyui-form info_from">
		<input id="frchseInfo_frchseCd" type="hidden" name="frchseCd">
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
				<input type="text" name="userId" class="easyui-numberbox" readonly unselectable="on"
		 style="width:320px;"/>
			</td>
			<td class="hintspace">
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
				<input type="text" name="frchseName" maxlength="30" class="easyui-validatebox" readonly unselectable="on" style="width:320px;"/>
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
				<input type="text" name="userName" maxlength="30" class="easyui-validatebox" readonly unselectable="on" style="width:320px;"/>
			</td>
			<td class="hintspace">
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
				<input type="text" name="idCard" maxlength="30" class="easyui-validatebox" readonly unselectable="on" style="width:320px;"/>
			</td>
			<td class="hintspace">
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
				<input type="text" name="mchntAddr" maxlength="30" class="easyui-validatebox" readonly unselectable="on" style="width:320px;"/>
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
				<a id="frchse_edit" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn" style="width:100px;">进行修改</a>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		</table>
	</form>
</div>
