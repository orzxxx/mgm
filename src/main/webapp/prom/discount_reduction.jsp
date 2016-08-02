<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">
requirejs(['discount-reduction'],function  (reduction) {
	reduction.init();
});

</script>
<div>
	<form method="post" id="discountReduction_form" class="easyui-form" data-options="novalidate:true" >
		<table class="table_info" border="0">
		<tr>
			<td width="100px">
				<label>
					满额:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="total" maxlength="9"  class="easyui-numberbox" data-options="required:true,min:0.01,max:999999.99,precision:2" style="width:180px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					减免:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="benefit" maxlength="9"  class="easyui-numberbox" data-options="required:true,min:0,max:999999.99,precision:2" style="width:180px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		</table>
	</form>
		
</div>
