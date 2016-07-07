<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">


</script>
<div>
	<form method="post" id="prom_form" class="easyui-form ct-menu-from" data-options="novalidate:true" enctype="multipart/form-data">
		<input id="prom_mchntCd" type="hidden" name="mchntCd">
		<input id="prom_productId" type="hidden" name="productId">
		<input id="prom_pictureLink" type="hidden" name="pictureLink">
		<table class="table_info" border="0">
			<tr>
				<td width="100">
				</td>
				<td class="tdspace">
				</td>
				<td style="text-align: center;">
					<img alt="促销图" id="prom_img" class="promimg" style="width:135px;height:180px;" src="images/default_menu.png">
				</td>
				<td class="hintspace">
				</td>				
			</tr>
			<tr>
				<td>
					<label>
						上传图片
					</label>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<input id="prom_picture" type="file" name="picture" maxlength="30" class="easyui-validatebox" data-options="required:true" validType="image" style="width:180px;"/>
				</td>
				<td class="hintspace">
					<tt optional="true">*上传长宽比3:4的图片获取最佳显示效果</tt> 
				</td>				
			</tr>
			<tr>
			<td style="vertical-align: top;">
				<label>
					促销信息
				</label>
			</td>
			<td class="tdspace">
			</td>
			<td>
				<textarea rows="5" name="promotionInf" maxlength="120"  class="easyui-validatebox" data-options="required:true" style="width:180px;"></textarea>
			</td>
			<td class="hintspace">
				<tt>*</tt>
			</td>
		</tr>
			<tr>
				<td>
				</td>
				<td class="tdspace">
				</td>
				<td style="text-align: left;">
					<a style="width: 110px;" id="prom_select" href="javascript:void(0)" class="easyui-linkbutton ct-rst-btn" iconCls="icon-add" plain="true"">选择促销菜品</a>
				</td>
				<td class="hintspace">
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
					<input id="prom_productName" type="text" name="productName" readonly="readonly" style="width:180px;border:0px;" unselectable="on"/>
				</td>
				<td class="hintspace">
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
					<input type="text" name="menutpName" readonly="readonly"  style="width:180px;border:0px;" unselectable="on"/>
				</td>
				<td class="hintspace">
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
					<input type="text" name="price" readonly="readonly" style="width:180px;border:0px;" unselectable="on"/>
				</td>
				<td class="hintspace">
				</td>			
			</tr>
			<tr>
				<td>
					<label>
						库存
					</label>
				</td>
				<td class="tdspace">
				</td>
				<td>
					<input type="text" name="inventory" readonly="readonly" style="width:180px;border:0px;" unselectable="on"/>
				</td>
				<td class="hintspace">
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
					<input type="text" name="specifications" value="" readonly="readonly" style="width:180px;"/>
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
				<td>
					<input  type="text" name="taste" readonly="readonly" style="width:180px;"/>
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
					<textarea rows="5" name="productDetail" readonly="readonly" style="width:180px;border:0px;outline: 0px;" unselectable="on"></textarea>
				</td>
				<td class="hintspace">
				</td>			
			</tr>

		</table>
	</form>
</div>
