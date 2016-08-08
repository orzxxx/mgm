<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['on-off-shelves'],function  (shelves) {
			shelves.init();
		});
</script>
<div class="easyui-layout" fit="true">
		<div region="north"  border="false"  style="height: 100px;overflow: hidden;" >  
	         <div style="margin-top: 5px;margin-left: 5px;">
		         	<form id="shelves_searchForm" name="searchForm" method="post">
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
									<select id="shelves_serach_menutpId" name="menutpId" style="width:205px;">
										<option value="">请选择</option>
									</select>
								</td>
								<td style="padding-left: 20px;">
								</td>
								<td width="60px">
									<label for="menutpId">
										状态:
									</label>
								</td>
								<td>
									<select id="shelves_serach_status" name="status" style="width:205px;">
										<option value="">请选择</option>
										<option value="0">正常</option>
										<option value="1">下架</option>
									</select>
								</td>
								<td class="hintspace"></td>
							</tr>
							<tr>
								<td width="60px">
								</td>
								<td>
									<a id="shelves_query" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">查询</a>
									<a id="shelves_clear" href="javascript:void(0);" class="easyui-linkbutton ct-rst-btn">重置</a>
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
						<span id="shelves_searchResult" class='ct-total'>查询记录数:<span id="shelves_total">0</span>条</span>
				</form>
	         </div>
		</div>
	   	<div region="center" border="false"> 
				<table id="shelves_pageList"></table>
		</div>
		<div id="shelves_toolbar">        
		</div>
</div>
