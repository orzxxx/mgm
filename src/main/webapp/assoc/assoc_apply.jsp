<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">
requirejs(['assoc-apply'],function  (assoc) {
	assoc.init();
});

</script>
<div>
	<div style="padding-top: 10px;
    	padding-left: 10px;
    	font-size: 15px;">
		状态: <span id="assocApply_state" style="font-size: 15px;"></span>
		<br/>
		<br/>
		<br/>
		<a id="assocApply_add" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn" style="width:100px;">关联申请</a>
	</div>
	<form method="post" id="assocInfo" class="easyui-form info_from" style="display:none;" data-options="novalidate:true" >
		<table class="table_info" border="0" style="width:700px;">
		<tr>
			<td width="100px;">
				<label>
					总部编号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<span style="width:320px;"></span>
				<input type="text" name="frchseCd"  style="width:320px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr>
			<td>
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
				<input type="text" name="frchseName" style="width:320px;" readonly unselectable="on"/>
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
					商店地址:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="mchntAddr" style="width:320px;" readonly unselectable="on"/>
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
			</td>
			<td class="hintspace">
			</td>
		</tr>
		</table>
	</form>
	<form method="post" id="assocResult" class="easyui-form" style="display:none;" data-options="novalidate:true" >
		<table class="table_info" border="0" style="width:700px;">
		<tr>
			<td width="100px;">
				<label>
					审核人:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<span style="width:320px;"></span>
				<input type="text" name="auditRole"  style="width:320px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr>
			<td>
				<label>
					审核时间:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<span style="width:320px;"></span>
				<input type="text" name="auditTime"  style="width:320px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		
		<tr>
			<td valign="top">
				<label>
					审核信息:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<textarea rows="5" name="auditInf" class="easyui-validatebox"   readonly style="width:320px;" readonly unselectable="on"></textarea>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		</table>
		</form>
</div>
