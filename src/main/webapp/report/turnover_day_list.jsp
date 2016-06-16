<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['turnover-day'],function  (turnover) {
			turnover.init();
		});
</script>
<div class="easyui-layout" fit="true">
		<div region="north"  border="false"  style="height: 100px;overflow: hidden;" >  
	         <div style="margin-top: 5px;margin-left: 5px;">
		         	<form id="turnoverDay_searchForm" name="searchForm" method="post">
						<table class="tablearea" >
							<tr>
								<td>
									<label for="transdate">
										交易日期:
									</label>
								</td>
								<td>
									<input id="turnoverDay_startDate" name="startDate" type="text" class="Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '%y-%M-#\{%d-1\}' })" style="width:90px;">
									至
									<input id="turnoverDay_endDate" name="endDate" type="text" class="Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '%y-%M-#\{%d-1\}',minDate:'#F{$dp.$D(\'turnoverDay_startDate\')}'  })" style="width:90px;">
								</td>
								<td style="padding-left: 20px;">
								</td>
								<td>
								</td>
								<td>
									<a id="turnoverDay_query" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">查询</a>
									<a id="turnoverDay_clear" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">重置</a>
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
						<a id="turnoverDay_in7" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">一周内</a>
						<a id="turnoverDay_in14" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">两周内</a>
						<a id="turnoverDay_in30" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">一个月内</a>
						<a id="turnoverDay_in60" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">两个月内</a>
				</form>
	         </div>
		</div>
	   	<div region="center" border="false"> 
				<table id="turnoverDay_pageList">
				</table>
		</div>
		<div id="turnoverDay_toolbar">        
		</div>
</div>
