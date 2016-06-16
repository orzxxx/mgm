<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['turnover-month'],function  (turnover) {
			turnover.init();
		});
</script>
<div class="easyui-layout" fit="true">
		<div region="north"  border="false"  style="height: 100px;overflow: hidden;" >  
	         <div style="margin-top: 5px;margin-left: 5px;">
		         	<form id="turnoverMonth_searchForm" name="searchForm" method="post">
						<table class="tablearea" >
							<tr>
								<td>
									<label for="transdate">
										交易日期:
									</label>
								</td>
								<td>
									<select id="turnoverMonth_startYear" name="menutpId" style="width:65px;">
										<option value="">2016</option>
									</select>年
									<select id="turnoverMonth_startMonth" name="menutpId" style="width:65px;">
										<option value="">01</option>
									</select>月
									<span>至</span>
									<select id="turnoverMonth_endYear" name="menutpId" style="width:65px;">
										<option value="">2016</option>
									</select>年
									<select id="turnoverMonth_endMonth" name="menutpId" style="width:65px;">
										<option value="">01</option>
									</select>月
								</td>
								<td style="padding-left: 20px;">
								</td>
								<td>
								</td>
								<td>
									<a id="turnoverMonth_query" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">查询</a>
									<%--<a id="turnoverMonth_clear" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">重置</a>
								--%></td>
								
								
								<td style="padding-left: 20px;">
								</td>
								<td>
								</td>
								<td>
								</td>
								<td class="hintspace"></td>
							</tr>
						</table>
				</form>
	         </div>
		</div>
	   	<div region="center" border="false"> 
				<table id="turnoverMonth_pageList">
				</table>
		</div>
		<div id="turnoverMonth_toolbar">        
		</div>
</div>
