<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">


</script>
<div id="menuTmpl_tab" class="easyui-tabs" data-options="border:false, fit:true">  
<div title="基础信息">
	<form method="post" id="menuTmpl_form" class="easyui-form ct-menu-from" data-options="novalidate:true" enctype="multipart/form-data">
		<input id="menuTmpl_mchntCd" type="hidden" name="mchntCd">
		<input id="menuTmpl_productId" type="hidden" name="productId">
		<input id="menuTmpl_pictureLink" type="hidden" name="pictureLink">
		<input id="menuTmpl_inventoryId" type="hidden" name="inventory.productId">
		<table class="table_info" border="0" style="width:370px;">
			<tr>
				<td>
				</td>
				<td class="tdspace">
				</td>
				<td>
						<img alt="单品图片" id="menuTmpl_img" class="menuimg" src="images/default_menu.png">
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
					<input id="menuTmpl_picture" type="file" name="picture" maxlength="30" class="easyui-validatebox" data-options="required:true" validType="image" style="width:180px;"/>
				</td>
				<td class="hintspace">
					<tt optional="true">*上传长宽比4:3的图片获取最佳显示效果</tt>  
				</td>				
			</tr>
			<tr>
				<td>
					<label for="product_name">
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
			<tr>
				<td>
					<label for="menutp_id">
						分类
					</label>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<select id="menuTmpl_menutpId" name="menutpId" style="width:180px;" class="easyui-validatebox" data-options="required:true">
						<option value="">请选择</option>
					</select>
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
					<input type="text" name="price" maxlength="30" class="easyui-numberbox" data-options="required:true,min:0,max:99999,precision:2" style="width:180px;"/>
				</td>
				<td class="hintspace">
					<tt>*</tt> 
				</td>			
			</tr>
			<%--<tr>
				<td valign="top">
					<label>
						规格
					</label>
				</td>
				<td valign="top" class="tdspace">
				</td>
				<td>
					<input id="menuTmpl_forSpec" type="text" maxlength="8" class="easyui-validatebox" style="width:120px;"/>
					<a id="menuTmpl_addSpec" href="javascript:void(0)" class="easyui-linkbutton ct-rst-btn" iconCls="icon-add" plain="true"">增加</a>
				</td>
				<td class="hintspace"></td>				
			</tr>
			<tr>
				<td valign="top">
					<label>
					</label>
				</td>
				<td valign="top" class="tdspace">
				</td>
				<td>
					<input id="menuTmpl_specifications" type="text" name="specifications"  maxlength="30" class="easyui-validatebox" style="width:180px;"/>
				</td>
				<td class="hintspace"></td>				
			</tr>
			<tr>
				<td valign="top">
					<label>
						口味
					</label>
				</td>
				<td valign="top" class="tdspace">
				</td>
				<td style="line-height:35px;">
					<input id="menuTmpl_forTaste" type="text" maxlength="12" class="easyui-validatebox" style="width:120px;"/>
					<a id="menuTmpl_addTaste" href="javascript:void(0)" class="easyui-linkbutton ct-rst-btn" iconCls="icon-add" plain="true"">增加</a>
				</td>
				<td class="hintspace">
				</td>			
			</tr>
			<tr>
				<td valign="top">
					<label>
					</label>
				</td>
				<td valign="top" class="tdspace">
				</td>
				<td>
					<input id="menuTmpl_taste" type="text" name="taste" maxlength="30" class="easyui-validatebox" style="width:180px;"/>
				</td>
				<td class="hintspace">
				</td>			
			</tr>
			--%><tr>
				<td style="vertical-align: top;">
					<label for="product_detail">
						商品详情
					</label>
				</td>
				<td class="tdspace" valign="top">
				</td>
				<td>
					<textarea rows="5" name="productDetail" class="easyui-validatebox" data-options="required:true" maxlength="90" style="width:180px;"></textarea>
				</td>
				<td class="hintspace">
					<tt>*</tt> 
				</td>			
			</tr>
			
			
		</table>
	</form>
</div>
	<div title="单品属性">
	<form method="post" id="menuAttr_form" class="easyui-form  ct-menu-from" data-options="novalidate:true" >
		<input id="menu_mchntCd" type="hidden" name="mchntCd">
		<table class="table_info" border="0" style="width:370px;">
			<tr>
				<td valign="top" >
					<label style='width: 60px;'>
						属性名
					</label>
				</td>
				<td valign="top" class="tdspace">
				</td>
				<td style="width:420px;text-align: left;">
					<input id="menuAttr_newAttr" type="text" maxlength="8" class="easyui-validatebox" style="width:120px;"/>
					<a id="menuAttr_addAttr" href="javascript:void(0)" style="width:100px;" class="easyui-linkbutton ct-rst-btn" iconCls="icon-add" plain="true">添加新属性</a>
				</td>
			</tr>
		</table>
		<br/>
		<hr>
	</form>
	</div>
</div>
