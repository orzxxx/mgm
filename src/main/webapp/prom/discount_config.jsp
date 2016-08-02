<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">
requirejs(['discount'],function  (discount) {
	discount.init();
});

</script>
<style type="text/css">
	.readonly{
		border: 0px;
	}
</style>
<div>
	<%--全品打折--%>
	<form method="post" id="discount_allForm" class="easyui-form" data-options="novalidate:true" >
		<table class="table_info" border="0" style="width:500px;">
		<tr>
			<td width="100px" colspan="4">
				<label>
					<input id="discount_allSwitch" type="checkbox" style="width:16px;height:16px;"/>
					启用整体折扣
				</label>
				<span style="color: #cc0000;font-size: 12px;font-weight: bold;">
					*店铺整体折扣，无法与满减优惠共用
				</span>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<a id="discount_allDate" href="javascript:void(0)" style="width:100px;" class="easyui-linkbutton ct-rst-btn" iconCls="icon-edit">修改有效时间</a>
			</td>
		</tr>
		<tr type="date">
			<td>
				<label>
					生效日期:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="" type="text" name="startDate" value="未设置" class="readonly" style="width:180px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr type="date">
			<td>
				<label>
					失效日期:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="" type="text" name="endDate" value="未设置" class="readonly"  style="width:180px;" readonly unselectable="on"/>
				</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr type="time">
			<td colspan="4">
				<input type="checkbox" name="week" value="1" disabled="disabled">星期一
				<input type="checkbox" name="week" value="2" disabled="disabled">星期二
				<input type="checkbox" name="week" value="3" disabled="disabled">星期三
				<input type="checkbox" name="week" value="4" disabled="disabled">星期四<br/>
				<input type="checkbox" name="week" value="5" disabled="disabled">星期五
				<input type="checkbox" name="week" value="6" disabled="disabled">星期六
				<input type="checkbox" name="week" value="7" disabled="disabled">星期日
			</td>
		</tr>
		<tr type="time">
			<td>
				<label>
					生效时间:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="" type="text" name="startTime" value="未设置" class="readonly" style="width:180px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr type="time">
			<td>
				<label>
					失效时间:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="" type="text" name="endTime" value="未设置" class="readonly" style="width:180px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr>
			<td>
				<label>
					折扣率:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="discount_rate" type="text" name="rate" class="easyui-numberbox" maxlength="3"
				data-options="required:true,precision:1" style="width:180px;"/>
				<span>折</span>
			</td>
			<td class="hintspace">
				<tt>*0.1到10折,可精确到1位小数</tt>
			</td>
		</tr>
		</table>
	</form>
	<%--满减优惠--%>
	<form method="post" id="discount_reductionForm" class="easyui-form" data-options="novalidate:true" >
		<hr size="1" color="#95B8E7"> 
		<div style="float:left;clear:both;">
		<table class="table_info" border="0" style="width:500px;">
		<tr>
			<td width="100px" colspan="4">
				<label>
					<input id="discount_reductionSwitch" type="checkbox" style="width:16px;height:16px;"/>
					启用满减优惠
				</label>
				<span style="color: #cc0000;font-size: 12px;font-weight: bold;">
					*满减优惠，可设置多级，无法与整体折扣共用
				</span>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<a id="discount_reductionDate" href="javascript:void(0)" style="width:100px;" class="easyui-linkbutton ct-rst-btn" iconCls="icon-edit">修改有效时间</a>
			</td>
		</tr>
		<tr type="date">
			<td>
				<label>
					生效日期:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="" type="text" name="startDate" value="未设置" class="readonly" style="width:180px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr type="date">
			<td>
				<label>
					失效日期:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="" type="text" name="endDate" value="未设置" class="readonly"  style="width:180px;" readonly unselectable="on"/>
				</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr type="time">
			<td colspan="4">
				<input type="checkbox" name="week" value="1" disabled="disabled">星期一
				<input type="checkbox" name="week" value="2" disabled="disabled">星期二
				<input type="checkbox" name="week" value="3" disabled="disabled">星期三
				<input type="checkbox" name="week" value="4" disabled="disabled">星期四<br/>
				<input type="checkbox" name="week" value="5" disabled="disabled">星期五
				<input type="checkbox" name="week" value="6" disabled="disabled">星期六
				<input type="checkbox" name="week" value="7" disabled="disabled">星期日
			</td>
		</tr>
		<tr type="time">
			<td>
				<label>
					生效时间:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="" type="text" name="startTime" value="未设置" class="readonly" style="width:180px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr type="time">
			<td>
				<label>
					失效时间:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="" type="text" name="endTime"  value="未设置" class="readonly" style="width:180px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		</table>
		</div>
		<div style="margin-top: 15px;">
			<a id="discount_addReduction" href="javascript:void(0)" style="width:100px;" class="easyui-linkbutton ct-rst-btn" iconCls="icon-edit">添加满减优惠</a>
			<input type="text" id="discount_reductions"/>
		</div>
	</form>
	<%--单品优惠--%>
	<form method="post" id="discount_menuForm" class="easyui-form" data-options="novalidate:true" style="clear:both;">
		<hr size="1" color="#95B8E7"> 
		<div style="float:left;clear:both;">
		<table class="table_info" border="0" style="width:500px;">
		<tr>
			<td width="100px" colspan="4">
				<label>
					<input id="discount_menuSwitch" type="checkbox" style="width:16px;height:16px;"/>
					启用单品优惠
				</label>
				<span style="color: #cc0000;font-size: 12px;font-weight: bold;">
					*单品优惠，可与整体折扣或满减优惠共存
				</span>
			</td>
		</tr>
		<tr>
			<td width="100px" colspan="4">
				<label>
					<input id="discount_mutex" type="checkbox" style="width:16px;height:16px;"/>
					开启优惠互斥
				</label>
				<span style="color: #cc0000;font-size: 12px;font-weight: bold;">
					*优惠互斥，开启后多种优惠不叠加，取最大优惠值
				</span>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<a id="discount_menuDate" href="javascript:void(0)" style="width:100px;" class="easyui-linkbutton ct-rst-btn" iconCls="icon-edit">修改有效时间</a>
			</td>
		</tr>
		<tr type="date">
			<td>
				<label>
					生效日期:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="" type="text" name="startDate" value="未设置" class="readonly" style="width:180px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr type="date">
			<td>
				<label>
					失效日期:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="" type="text" name="endDate"  value="未设置" class="readonly"  style="width:180px;" readonly unselectable="on"/>
				</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr type="time">
			<td colspan="4">
				<input type="checkbox" name="week" value="1" disabled="disabled">星期一
				<input type="checkbox" name="week" value="2" disabled="disabled">星期二
				<input type="checkbox" name="week" value="3" disabled="disabled">星期三
				<input type="checkbox" name="week" value="4" disabled="disabled">星期四<br/>
				<input type="checkbox" name="week" value="5" disabled="disabled">星期五
				<input type="checkbox" name="week" value="6" disabled="disabled">星期六
				<input type="checkbox" name="week" value="7" disabled="disabled">星期日
			</td>
		</tr>
		<tr type="time">
			<td>
				<label>
					生效时间:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="" type="text" name="startTime"  value="未设置" class="readonly" style="width:180px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		<tr type="time">
			<td>
				<label>
					失效时间:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input id="" type="text" name="endTime" value="未设置" class="readonly" style="width:180px;" readonly unselectable="on"/>
			</td>
			<td class="hintspace">
			</td>
		</tr>
		</table>
		</div>
		<div style="margin-top: 15px;">
			<a id="discount_seletcMenus" href="javascript:void(0)" style="width:100px;" class="easyui-linkbutton ct-rst-btn" iconCls="icon-edit">选择优惠单品</a>
			<table id="discount_menus" class="table_info" border="0" style="width:500px;">
				<tr>
					<td align='center'>单品名</td>
					<td align='center'>分类</td>
					<td align='center'>原价(元)</td>
					<td align='center'>优惠价(元)</td>
					<td align='center'></td>
				</tr>
			</table>
		</div>
	</form>
	<hr size="1" color="#95B8E7" style="clear:both;">
	<table class="table_info" border="0" style="width:500px;">
		<tr>
			<td>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<a id="discount_save" href="javascript:void(0);" style="width:100px;" class="easyui-linkbutton ct-qry-btn">保存设置</a>
			</td>
			<td class="hintspace">
			</td>
		</tr>
	</table>
	
</div>
