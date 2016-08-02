<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['combo'],function  (combo) {
			combo.init();
		});
</script>
	<div id="combo_list" class="easyui-layout" fit="true">
			<div region="north"  border="false"  style="height: 100px;overflow: hidden;" >  
		         <div style="margin-top: 5px;margin-left: 5px;">
			         	<form id="combo_searchForm" name="searchForm" method="post">
							<table class="tablearea" >
								<tr>
									<td width="60px">
										<label>
											商品名:
										</label>
									</td>
									<td>
										<input type="text" name="productName" id="productName" maxlength="20" style="width:200px;">
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
										<a id="combo_query" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">查询</a>
										<a id="combo_clear" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">重置</a>
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
							<span id="combo_searchResult" class='ct-total'>查询记录数:<span id="combo_total">0</span>条</span>
					</form>
		         </div>
			</div>
		   	<div region="center" border="false"> 
					<table id="combo_pageList"></table>
			</div>
			<div id="combo_toolbar">        
				<a id="combo_toEdit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"">修改</a>
				<a id="combo_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"">删除</a>
				<a id="combo_toAdd" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"">添加</a>
			</div>
	</div>
	<div id="combo_form" style="display: none;">
	</div>
