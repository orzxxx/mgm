<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['menuTmpl'],function  (menu) {
			menu.init();
		});
</script>
<div class="easyui-layout" fit="true">
		<div region="north"  border="false"  style="height: 100px;overflow: hidden;" >  
	         <div style="margin-top: 5px;margin-left: 5px;">
		         	<form id="menuTmpl_searchForm" name="searchForm" method="post">
						<table class="tablearea" >
							<tr>
								<td width="60px">
									<label for="productName">
										商品名:
									</label>
								</td>
								<td>
									<input type="text" name="productName" id="productName" maxlength="20" style="width:200px;">
								</td>
								
								<td style="padding-left: 20px;">
								</td>
								<td>
									<label for="menutpId">
										单品分类:
									</label>
								</td>
								<td>
									<select id="menuTmpl_search_menutpId" name="menutpId" style="width:205px;">
										<option value="">请选择</option>
									</select>
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
									<a id="menuTmpl_query" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">查询</a>
									<a id="menuTmpl_clear" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">重置</a>
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
						<span id="menuTmpl_searchResult" class='ct-total'>查询记录数:<span id="menuTmpl_total">0</span>条</span>
				</form>
	         </div>
		</div>
	   	<div region="center" border="false"> 
				<table id="menuTmpl_pageList"></table>
		</div>
		<div id="menuTmpl_toolbar">        
			<a id="menuTmpl_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"">修改</a>
			<a id="menuTmpl_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"">删除</a>
			<a id="menuTmpl_add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"">添加</a>
		</div>
</div>
