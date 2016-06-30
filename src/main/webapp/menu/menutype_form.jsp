<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">

</script>
<div>
	<form method="post" id="menutype_form" class="easyui-form" data-options="novalidate:true" >
		<input id="menutype_mchntCd" type="hidden" name="mchntCd">
		<input id="menutype_menutpId" type="hidden" name="menutpId">
		<table class="table_info" border="0">
			<tr>
				<td width="100px">
					<label>
						分类名:
					</label>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<input type="text" name="menutpName" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'realLength[30]'"style="width:180px;"/>
				</td>
				<td class="hintspace">
					<tt>*</tt> 
				</td>				
			</tr>
			
		</table>
	</form>
</div>
