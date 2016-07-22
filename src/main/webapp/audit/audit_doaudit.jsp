<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">

</script>
<div>
	<div id="doaudit_tab" class="easyui-tabs" >   
	    <div title="基础信息" style="padding:20px;">   
	    	<form id="doaudit_form" method="post" id="auditInfo" class="easyui-form info_from" data-options="novalidate:true" >
				<table class="table_info" border="0" style="width:500px;">
				<tr>
					<td width="300px;">
						<label>
							手机号:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<span style="width:320px;"></span>
						<input type="text" name="userId"  style="width:320px;" readonly unselectable="on"/>
					</td>
					<td class="hintspace">
					</td>
				</tr>
				
				<tr>
					<td>
						<label>
							店铺名称:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<input type="text" name="mchntName" style="width:320px;" readonly unselectable="on"/>
					</td>
					<td class="hintspace">
					</td>
				</tr>
				<tr>
					<td>
						<label>
							姓名:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<input type="text" name="userName" style="width:320px;" readonly unselectable="on"/>
					</td>
					<td class="hintspace">
					</td>
				</tr>
				<tr>
					<td>
						<label>
							身份证:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<input type="text" name="idCard" style="width:320px;" readonly unselectable="on"/>
					</td>
					<td class="hintspace">
					</td>
				</tr>
				<tr>
					<td>
						<label>
							邮箱:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<input type="text" name="email" style="width:320px;" readonly unselectable="on"/>
					</td>
					<td class="hintspace">
					</td>
				</tr>
				<tr>
					<td valign='top'>
						<label>
							商店地址:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<textarea rows='4'  name="mchntAddr" style="width:320px;" readonly unselectable="on"></textarea>
					</td>
					<td class="hintspace">
					</td>
				</tr>
				<tr>
					<td>
						<label id="doaudit_license_label">
							证件号:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<input type="text" name="license" style="width:320px;" readonly unselectable="on"/>
					</td>
					<td class="hintspace">
					</td>
				</tr>
				<tr>
					<td>
						<label>
							收款卡号:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<input type="text" name="inAcct" style="width:320px;" readonly unselectable="on"/>
					</td>
					<td class="hintspace">
					</td>
				</tr>
				<tr>
					<td>
						<label>
							支付宝账号:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<input type="text" name="alipayNo" style="width:320px;" readonly unselectable="on"/>
					</td>
					<td class="hintspace">
					</td>
				</tr>
				<tr>
					<td>
						<label>
							微信账号:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<input type="text" name="wechatNo" style="width:320px;" readonly unselectable="on"/>
					</td>
					<td class="hintspace">
					</td>
				</tr>
				</table>
				
			</form>
	    </div>   
	    <%--<div title="身份证" data-options="" style="overflow:auto;padding:20px;">   
	    	<table>
	        <tr>
				<td>
					<img id="doaudit_idCardFrontImg" class="iccardimg" >
				</td>
				<td>
					<img  id="doaudit_idCardBackImg"  class="iccardimg" >
				</td>
			</tr>
			<tr>
				<td>
					<label>
						身份证正面
					</label>
					<tt>双击查看原图</tt>
				</td>
				<td>
					<label>
						身份证背面
					</label>
					<tt>双击查看原图</tt>
				</td>
			</tr>
			</table>
	    </div>   
	    <div title="营业执照" data-options="" style="overflow:auto;padding:20px;">   
	        <table>
	        <tr>
				<td>
					<img id="doaudit_licenseFrontImg" class="iccardimg" >
				</td>
				<td>
					<img  id="doaudit_storePhotoImg"  class="iccardimg" >
				</td>
			</tr>
			<tr>
				<td>
					<label>
						营业执照
					</label>
					<tt>双击查看原图</tt>
				</td>
				<td>
					<label>
						店铺照片
					</label>
					<tt>双击查看原图</tt>
				</td>
			</tr>
			</table>    
	    </div>   
	    --%><div title="进行审核" data-options="" style="overflow:auto;padding:20px;">   
	       <form method="post"  id="doaudit_auditForm" class="easyui-form" data-options="novalidate:true" >
				<input id="doaudit_mchntCd" type="hidden" name="mchntCd">
				<table class="table_info" border="0">
				<tr>
					<td width="300px">
						<label>
							审核结果:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<input type="radio" checked name="auditStatus" value="1"/>通过
						<input type="radio" name="auditStatus" value="-1"/>不通过
					</td>
					<td class="hintspace">
					</td>
				</tr>
				<tr>
					<td width="100px" valign="top">
						<label>
							审核信息:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<textarea rows="5" name="auditInf" class="easyui-validatebox" maxlength="90" data-options="required:true" style="width:320px;"></textarea>
					</td>
					<td class="hintspace" >
						<tt>*</tt> 
					</td>
				</tr>
				<tr>
			<td>
				
			</td>
			<td class="tdspace">
			</td>
			<td>
				<a id="doaudit_doaudit" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn" style="width:100px;">审核</a>
			</td>
			<td class="hintspace">
			</td>
		</tr>
				</table>
			</form>  
	    </div>   
	</div>  
</div>
