<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">
requirejs(['discount-date'],function  (date) {
	date.init();
});

</script>
<div>
	选择时间模式:
	<input id="discountDate_setTime" type="radio" name="mode" value="1" checked/><span>模式一</span>
	<input id="discountDate_setWeek" type="radio" name="mode" value="2"/><span>模式二</span>
	<%--时间段设置--%>
	<form method="post" id="discountDate_timeQuantum" class="easyui-form" data-options="novalidate:true" >
		<hr size="1" color="#95B8E7"> 
		<table class="table_info" border="0" style="width:500px;">
		<tr>
			<td width="100px" colspan="4">
				<label>
					模式一
				</label>
				<span style="color: #cc0000;font-size: 12px;font-weight: bold;">
					*该起始日期的时间段内折扣生效
				</span>
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
				<input id="discountDate_startDate" type="text" name="endDate"  class="easyui-validatebox Wdate" style="width:180px;"/>
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
				<input id="discountDate_endDate" type="text" name="endDate"  class="easyui-validatebox Wdate"  style="width:180px;"/>
				</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		</table>
	</form>
		
		<%--星期+时间设置--%>
	<form style="" method="post" id="discountDate_weekday" class="easyui-form" data-options="novalidate:true" >
	<hr size="1" color="#95B8E7"> 
		<table class="table_info" border="0" style="width:500px;">
		<tr>
			<td colspan="4">
				<label>
					模式二
				</label>
				<span style="color: #cc0000;font-size: 12px;font-weight: bold;">
					*勾选星期的时间段内折扣生效
				</span>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<input type="checkbox" name="week" value="2">星期一
				<input type="checkbox" name="week" value="3">星期二
				<input type="checkbox" name="week" value="4">星期三
				<input type="checkbox" name="week" value="5">星期四<br/>
				<input type="checkbox" name="week" value="6">星期五
				<input type="checkbox" name="week" value="7">星期六
				<input type="checkbox" name="week" value="1">星期日
			</td>
		</tr>
		</table>
	</form>
	<hr size="1" color="#95B8E7"> 
		<table class="table_info" border="0" style="width:500px;">
		<tr>
			<td width="100px">
				<label>
					有效时间段:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td width="400px">
				<input id="discountDate_startTime" class="easyui-timespinner"  style="width:100px;"  data-options="showSeconds:true" />  
				至
				<input id="discountDate_endTime" class="easyui-timespinner"  style="width:100px;"  data-options="showSeconds:true" />  
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
		</table>
</div>
