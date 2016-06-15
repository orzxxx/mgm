<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">
requirejs(['audit-status'],function  (audit) {
	audit.init();
});

</script>
<div>
	<img id="auditStatus_img"/>
	<form method="post" id="auditStatus_auditResult" class="easyui-form"   style="display:none;">
		<table>
			<tr>
				<td valign="top">
					<label>
						审核人:
					</label>
				</td>
				<td valign="top" class="tdspace">
				</td>
				<td>
					<input type="text" name="auditRole" class="easyui-validatebox"  readonly style="width:220px;" disabled="disabled"/>
				</td>
				<td class="hintspace">
				</td>			
			</tr>
			<tr>
				<td valign="top">
					<label>
						审核时间:
					</label>
				</td>
				<td valign="top" class="tdspace">
				</td>
				<td>
					<input type="text" name="auditTime"  class="easyui-validatebox"  readonly style="width:220px;" disabled="disabled"/>
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
				<td valign="top" class="tdspace">
				</td>
				<td>
					<textarea rows="5" name="auditInf" class="easyui-validatebox"   readonly style="width:220px;" disabled="disabled"></textarea>
				</td>
				<td class="hintspace">
				</td>			
			</tr>
		</table>
	</form>
</div>
