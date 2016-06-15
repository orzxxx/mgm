<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">
requirejs(['discount'],function  (discount) {
	discount.init();
});

</script>
<div>
	<form method="post" id="discount_form" class="easyui-form" data-options="novalidate:true" enctype="multipart/form-data">
		<input id="discount_mchntCd" type="hidden" name="mchntCd">
		<input id="discount_id" type="hidden" name="uuid">
		<table class="table_info" border="0" style="width:500px;">
		<tr>
			<td>
				<label>
					折扣率:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="discount_rate" type="text" name="userId" class="easyui-numberbox" 
				data-options="required:true,min:0.1,max:10,precision:1" style="width:180px;"/>
				<span>折</span>
			</td>
			<td class="hintspace">
				<tt>*0.1到10折,可精确到1位小数</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					生效日期:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="discount_startDate" type="text" name="startDate" onfocus="WdatePicker({startDate:'%y-%M-%d',alwaysUseStartDate:true,dateFmt: 'yyyy-MM-dd',minDate:'%y-%M-%d'})"  class="easyui-validatebox Wdate" data-options="required:true" style="width:180px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					失效日期:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="discount_endDate" type="text" name="endDate" onfocus="WdatePicker({startDate:'#F{$dp.$D(\'discount_startDate\')||\'%y-%M-%d\'}',alwaysUseStartDate:true,dateFmt: 'yyyy-MM-dd',minDate:'#F{$dp.$D(\'discount_startDate\')||\'%y-%M-%d\'}'})"  class="easyui-validatebox Wdate" data-options="required:true" style="width:180px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		<tr>
			<td>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<a id="discount_edit" href="javascript:void(0);" style="width:100px;" class="easyui-linkbutton ct-qry-btn">保存设置</a>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		</table>
		
	</form>
</div>
