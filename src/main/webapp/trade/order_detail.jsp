<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
</script>
<div>
	<form method="post" id="order_form" class="easyui-form info_from" data-options="novalidate:true" >
		<table class="table_info" border="0">
		<tr>
			<td width="100px;">
				<label>
					订单号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<span style="width:320px;"></span>
				<input type="text" name="orderNo"  style="width:320px;" readonly unselectable="on"/>
			</td>
		</tr>
		
		<tr>
			<td>
				<label>
					平台流水:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="pseq" style="width:320px;" readonly unselectable="on"/>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					服务员号:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="operId" style="width:320px;" readonly unselectable="on"/>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					交易日期:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="transdate" style="width:320px;" readonly unselectable="on"/>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					交易时间:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="transtime" style="width:320px;" readonly unselectable="on"/>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					订单金额(元):
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="stdtrnsamt" style="width:320px;" readonly unselectable="on"/>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					付款类型:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="payTp" style="width:320px;" readonly unselectable="on"/>
			</td>
		</tr>
		<tr>
			<td>
				<label>
					交易类型:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="trnsflag" style="width:320px;" readonly unselectable="on"/>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: top;">
				<label>
					订单详情:
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<textarea rows="10" type="text" name="orderDetail" style="width:320px;" readonly unselectable="on"></textarea>
			</td>
		</tr>
		</table>
		
	</form>
</div>
