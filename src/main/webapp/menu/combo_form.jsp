<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">


</script>
<div>
	<form method="post" id="combo_form" class="easyui-form ct-menu-from" data-options="novalidate:true" enctype="multipart/form-data">
		<input id="combo_mchntCd" type="hidden" name="mchntCd">
		<input id="combo_productId" type="hidden" name="productId">
		<input id="combo_pictureLink" type="hidden" name="pictureLink">
		<input id="combo_inventoryId" type="hidden" name="inventory.productId">
		<table class="table_info" border="0">
		<tr>
				<td>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<img alt="套餐图" id="combo_img" class="menuimg" src="images/default_menu.png">
				</td>
				<td class="hintspace">
				</td>				
			</tr>
		<tr>
				<td width="100px;">
					<label>
						上传图片
					</label>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<input id="combo_picture" type="file" name="picture" maxlength="30" class="easyui-validatebox"  optional='true' data-options="required:true" validType="image" style="width:180px;"/>
				</td>
				<td class="hintspace">
					<tt optional="true">*上传长宽比4:3的图片获取最佳显示效果</tt> 
				</td>				
			</tr>
		<tr>
			<td>
				<label>
					商品名
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<input type="text" name="productName" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'realLength[20]'" style="width:180px;"/>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
			<tr >
				<td>
					<label>
						库存
					</label>
				</td>
				<td class="tdspace">
				</td>
				<td style="text-align: center;">
					<input id="combo_unlimited" type="radio" checked name="inventory.inventory" value="-1"/>无限制
					<input id="combo_soldout" type="radio" name="inventory.inventory" value="0"/>售罄
					<input id="combo_custom" type="radio" name="inventory.inventory" value=""/>自定义</br>
				</td>
				<td class="hintspace">
					<tt>*</tt> 
				</td>				
			</tr>
			<tr>
				<td valign="top">
				</td>
				<td valign="top" class="tdspace">
				</td>
				<td>
					<input id="combo_inventory" type="text" maxlength="30" class="easyui-numberbox" data-options="required:true,min:0,max:999,precision:0" disabled="true" style="width:180px;"/>
				</td>
				<td class="hintspace"></td>				
			</tr>
			<tr>
				<td>
					<label>
						打包盒份数
					</label>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<input type="text" name="packingBoxNum" maxlength="30" value='0' class="easyui-numberbox" data-options="required:true,min:0,max:99,precision:0" style="width:180px;"/>
				</td>
				<td class="hintspace">
					<tt>*</tt> 
				</td>			
			</tr>
		<!-- <tr>
			<td>
				<label>
					商品详情
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<textarea rows="5" name="productDetail" class="easyui-validatebox" data-options="required:true" style="width:180px;"></textarea>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr> -->
		</table>
		<hr/>
		<div region="center" border="false"> 
				<h1 align="center">套餐组合</h1>
				<table id="combo_package"></table>
				<table>
					<tr>
						<td style="text-align: left;">原价:<span id="combo_oriPrice">0.00</span>元</td>
					</tr>
					<tr>
						<td style="text-align: left;">定价:<input id="combo_price" type="text" class="easyui-numberbox" data-options="min:0,max:99999,precision:2" name="price"/>元</td>
					</tr>
				</table>
		</div>
		<div id="comboPackage_toolbar">        
			<a id="comboPackage_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"">删除</a>
			<a id="combo_select" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"">选择</a>
		</div>
	</form>
</div>
