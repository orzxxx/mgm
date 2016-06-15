<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['menuTypeTmpl'],function  (menuType) {
		menuType.init();
	});
</script>
<div class="easyui-layout" fit="true">
		<div region="north"  border="false"  style="height: 100px;overflow: hidden;" >  
	         <div style="margin-top: 5px;margin-left: 5px;">
		         	<form id="menuTypeTmpl_searchForm" name="searchForm" method="post">
						<table class="tablearea" >
							<tr>
								<td width="60px">
									<label>
										分类名:
									</label>
								</td>
								<td>
									<input type="text" name="menutpName" id="menutpName" maxlength="20" style="width:200px;">
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
							<tr>
								<td width="60px">
									
								</td>
								<td>
									<a id="menuTypeTmpl_query" href="javascript:void(0);"  class="easyui-linkbutton ct-qry-btn" >查询</a>
									<a id="menuTypeTmpl_clear" href="javascript:void(0);"  class="easyui-linkbutton ct-rst-btn" >重置</a>
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
						<span id="menuTypeTmpl_searchResult" class='ct-total'>查询记录数:<span id="menuTypeTmpl_total">0</span>条</span>
				</form>
	         </div>
		</div>
	   	<div region="center" border="false"> 
				<table id="menuTypeTmpl_pageList"></table>
		</div>
		<div id="menuTypeTmpl_toolbar">        
			<a id="menuTypeTmpl_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"">修改</a>
			<a id="menuTypeTmpl_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"">删除</a>
			<a id="menuTypeTmpl_add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"">添加</a>
		</div>
</div>
