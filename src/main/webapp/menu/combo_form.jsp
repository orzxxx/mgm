<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">


</script>

<style type="text/css">
#combo_baseform table tr td:first-child{
	width:120px;
}
div.tagsinput{
	border: 1px solid;
    float: left;
}
</style>

<div style='overflow:auto;'>
	<div id="combo_baseInfo" style="float: left;width:400px;">
		<br/>
		<form method="post" id="combo_baseform" class="easyui-form ct-menu-from" data-options="novalidate:true" enctype="multipart/form-data">
			<input id="combo_mchntCd" type="hidden" name="mchntCd">
			<input id="combo_productId" type="hidden" name="productId">
			<input id="combo_pictureLink" type="hidden" name="pictureLink">
			<input id="combo_inventoryId" type="hidden" name="inventory.productId">
			<table class="table_info" border="0">
			<tr>
					<td colspan="3">
						<a id="combo_back" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">返回</a>
						<a id="combo_add" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn">保存</a>
					</td>
					<td class="hintspace">
					</td>				
				</tr>
			<tr>
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
						<tt optional="true">*</tt> 
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
						<input id="combo_inventory" type="text" maxlength="3" class="easyui-numberbox" data-options="required:true,min:0,max:999,precision:0" disabled="true" style="width:180px;"/>
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
						<input type="text" name="packingBoxNum" maxlength="2" value='0' class="easyui-numberbox" data-options="required:true,min:0,max:99,precision:0" style="width:180px;"/>
					</td>
					<td class="hintspace">
						<tt>*</tt> 
					</td>			
				</tr>
				<tr>
					<td>
						<label>
							价格
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<input id="combo_price" type="text" class="easyui-numberbox" maxlength='7' data-options="min:0,max:99999.99,precision:2" name="price" style="width:180px;"/>
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
		</form>
	</div>
	<div id="combo_packages" style="float: left;">
		<form method="post" id="comboPkg_form" class="easyui-form  ct-menu-from" data-options="novalidate:true" >
		<table class="table_info" border="0">
			<tr>
				<td valign="top" >
					<label style='width: 60px;'>
						组合配置
					</label>
				</td>
				<td valign="top" class="tdspace">
				</td>
				<td>
					<input type="text" name="packingBoxNum" maxlength="2" value='0' class="easyui-numberbox" data-options="required:true,min:0,max:99,precision:0" style="width:180px;"/>
				</td>
			</tr>
		</table>
		<br/>
		<hr>
		</form>
	</div>
</div>
