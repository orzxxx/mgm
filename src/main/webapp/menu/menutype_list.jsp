<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['menuType'],function  (menuType) {
		menuType.init();
	});
</script>
<div class="easyui-layout" fit="true">
		<div region="north"  border="false"  style="height: 100px;overflow: hidden;" >  
	         <div style="margin-top: 5px;margin-left: 5px;">
		         	<form id="menutype_searchForm" name="searchForm" method="post">
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
								<td class="hintspace"></td>
							</tr>
							<tr>
								<td width="60px"></td>
								<td>
							       <a id="menuType_query" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">查询</a>
								   <a id="menuType_clear" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">重置</a>
								</td>
								<td class="hintspace"></td>
							</tr>
						</table>
						<br/>
						<span id="menutype_searchResult" class='ct-total'>查询记录数:<span id="menutype_total">0</span>条</span>
				</form>
	         </div>
		</div>
	   	<div region="center" border="false"> 
				<table id="menutype_pageList"></table>
		</div>
		<div id="menutype_toolbar">        
			<a id="menuType_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"">修改</a>
			<a id="menuType_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"">删除</a>
			<a id="menuType_add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"">添加</a>
		</div>
</div>
