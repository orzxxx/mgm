<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['admin-mchnt'],function  (mchnt) {
			mchnt.init();
		});
</script>
<div class="easyui-layout" fit="true">
		<div region="north"  border="false"  style="height: 100px;overflow: hidden;" >  
	         <div style="margin-top: 5px;margin-left: 5px;">
		         	<form id="mchnt_searchForm" name="searchForm" method="post">
						<table class="tablearea" >
							<tr>
								<td width="60px">
									<label>
										商户名:
									</label>
								</td>
								<td>
									<input id="mchntSearch_name" type="text" name="mchntName" maxlength="20" style="width:200px;">
								</td>
								
								<td style="padding-left: 20px;">
								</td>
								<td>
									<label>
										商户号:
									</label>
								</td>
								<td>
									<input id="mchntSearch_cd" type="text" name="mchntCd"  maxlength="20" style="width:200px;">
								</td>
								<td style="padding-left: 20px;">
								</td>
								<td width="60px">
								</td>
								<td>
								</td>
								<td class="hintspace"></td>
							</tr>
							<tr>
								<td width="60px">
									
								</td>
								<td>
									<a id="mchnt_query" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">查询</a>
									<a id="mchnt_clear" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">重置</a>
								</td>
								
								<td style="padding-left: 20px;">
								</td>
								<td>
								</td>
								<td>
								</td>
								<td style="padding-left: 20px;">
								</td>
								<td width="60px">
								</td>
								<td>
								</td>
								<td class="hintspace"></td>
							</tr>
						</table>
						<br/>
						<span id="mchnt_searchResult" class='ct-total'>查询记录数:<span id="mchnt_total">0</span>条</span>
				</form>
	         </div>
		</div>
	   	<div region="center" border="false"> 
				<table id="mchnt_pageList"></table>
		</div>
		<div id="mchnt_toolbar">        
		</div>
</div>
