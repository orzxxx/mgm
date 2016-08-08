<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['admin-order'],function  (order) {
			order.init();
		});
</script>
<div class="easyui-layout" fit="true">
		<div region="north"  border="false"  style="height: 100px;overflow: hidden;" >  
	         <div style="margin-top: 5px;margin-left: 5px;">
		         	<form id="order_searchForm" name="searchForm" method="post">
						<table class="tablearea" >
							<tr>
								<td width="100px">
									<label for="orderNo">
										订单号:
									</label>
								</td>
								<td width="210px">
									<input type="text" name="orderNo" id="orderNo" maxlength="20" style="width:200px;">
								</td>
								
								<td style="padding-left: 20px;">
								</td>
								<td width="100px">
									<label for="pseq">
										商户号:
									</label>
								</td>
								<td>
									<input type="text" name="mchntCd" id="mchntCd" maxlength="11" style="width:200px;">
								</td>
								<td style="padding-left: 20px;">
								</td>
								<td width="100px">
									<label for="operId"><%--
										 商户号:
									--%></label>
								</td>
								<td width="290px">
									<%--<input type="text" name="mchntCd" id="mchntCd" maxlength="11" style="width:200px;">
								--%></td>
								<td class="hintspace"></td>
							</tr>
							<tr>
								<td>
									<label for="transdate">
										交易日期:
									</label>
								</td>
								<td>
									<input id="order_minTransdate" name="minTransdate" type="text" class="Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '%y-%M-%d' })" style="width:90px;">
									至
									<input id="order_maxTransdate" name="maxTransdate" type="text" class="Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '%y-%M-%d',minDate:'#F{$dp.$D(\'order_minTransdate\')}' })" style="width:90px;">
								</td>
								<td style="padding-left: 20px;">
								</td>
								<td>
									<label for="trnsflag">
										交易类型:
									</label>
								</td>
								<td>
									<select id="order_trnsflag" name="trnsflag" style="width:205px;">
										<option value="">请选择</option>
										<option value="-1">交易失败</option>
										<option value="0">交易初始化</option>
										<option value="1">交易成功</option>
										<option value="2">交易撤销</option>
										<option value="3">退单成功</option>
									</select>
								</td>
								
								<td style="padding-left: 20px;">
								</td>
								<td>
								</td>
								<td>
									<a id="order_query" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">查询</a>
									<a id="order_clear" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">重置</a>
								</td>
								<td class="hintspace"></td>
							</tr>
							<tr>
								<td>
								</td>
								<td>
										
								</td>
								<td style="padding-left: 20px;">
								</td>
								<td>
								</td>
								<td>
								</td>
								
								
								<td style="padding-left: 20px;">
								</td>
								<td>
								</td>
								<td>
								</td>
								<td class="hintspace"></td>
							</tr>
						</table>
					<br/>
						<span id="order_searchResult" class='ct-total'>查询记录数:<span id="order_total">0</span>条</span>
				</form>
	         </div>
		</div>
	   	<div region="center" border="false"> 
				<table id="order_pageList"></table>
		</div>
		<div id="order_toolbar">        
			<a id="order_export" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"">导出</a>
		</div>
</div>
