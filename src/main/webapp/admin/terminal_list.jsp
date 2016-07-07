<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['admin-terminal'],function  (terminal) {
			terminal.init();
		});
</script>
<div class="easyui-layout" fit="true">
		<div region="north"  border="false"  style="height: 100px;overflow: hidden;" >  
	         <div style="margin-top: 5px;margin-left: 5px;">
		         	<form id="terminal_searchForm" name="searchForm" method="post">
						<table class="tablearea" >
							<tr>
								<td width="100px">
									<label>
										终端编码:
									</label>
								</td>
								<td width="210px">
									<input type="text" name="terminalCd" maxlength="20" style="width:200px;">
								</td>
								
								<td style="padding-left: 20px;">
								</td>
								<td width="100px">
									<label>
										SN号:
									</label>
								</td>
								<td>
									<input type="text" name="terminalSn" maxlength="20" style="width:200px;">
								</td>
								<td style="padding-left: 20px;">
								</td>
								<td width="100px">
								</td>
								<td width="290px">
								</td>
								<td class="hintspace"></td>
							</tr>
							<tr>
								<td>
									<label>
										商户号:
									</label>
								</td>
								<td width="290px">
									<input type="text" name="mchntCd" maxlength="20" style="width:200px;">
								</td>
								<td style="padding-left: 20px;">
								</td>
								<td width="60px">
									<label>
										手机号:
									</label>
								</td>
								<td>
									<%--<a id="terminal_query" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">查询</a>
									<a id="terminal_clear" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">重置</a>
								--%>
									<input type="text" name="userId" maxlength="20" style="width:200px;">
								</td>
								<td style="padding-left: 20px;">
								</td>
								<td width="60px">
								</td>
								<td>
									<a id="terminal_query" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">查询</a>
									<a id="terminal_clear" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">重置</a>
								</td>
								<td class="hintspace"></td>
							</tr>
						</table>
						<br/>
						<span id="terminal_searchResult" class='ct-total'>查询记录数:<span id="terminal_total">0</span>条</span>
				</form>
	         </div>
		</div>
	   	<div region="center" border="false"> 
				<table id="terminal_pageList"></table>
		</div>
		<div id="terminal_toolbar">        
			<a id="terminal_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"">解绑</a>
		</div>
</div>
