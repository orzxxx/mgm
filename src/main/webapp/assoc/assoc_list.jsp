<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['assoc-list'],function  (assoc) {
			assoc.init();
		});
</script>
<div class="easyui-layout" fit="true">
		<div region="north"  border="false"  style="height: 100px;overflow: hidden;" >  
	         <div style="margin-top: 5px;margin-left: 5px;">
		         	<form id="assoc_searchForm" name="searchForm" method="post">
						<table class="tablearea" >
							<tr>
								<td width="">
									<label style="font-size: 18px;">
										你的总部编号:
									</label>
								</td>
								<td style="padding-left: 20px;">
								</td>
								<td>
									<span style="font-size: 18px;">${sessionScope.loginUser.userInfo.mchntCd}</span>
								</td>
							</tr>
						</table>
						<br/>
						<br/>
						<br/>
						<span id="assoc_searchResult" class='ct-total'>查询记录数:<span id="assoc_total">0</span>条</span>
				</form>
	         </div>
		</div>
	   	<div region="center" border="false"> 
				<table id="assoc_pageList"></table>
		</div>
		<div id="assoc_toolbar">        
			<a id="assoc_audit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"">审核</a>
		</div>
</div>
