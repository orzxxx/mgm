<%@ page language="java"  pageEncoding="UTF-8"%>
<script language="javascript">

</script>
<div>
	<div id="assocAudit_tab" class="easyui-tabs" >   
	    <div title="基础信息" style="padding:20px;">   
	    	<form id="assocAudit_form" method="post" id="auditInfo" class="easyui-form info_from" data-options="novalidate:true" >
				<table class="table_info" border="0" style="width:500px;">
				<tr>
					<td width="120px;">
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
					<td>
						<label>
							商店地址:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<input type="text" name="mchntAddr" style="width:320px;" readonly unselectable="on"/>
					</td>
					<td class="hintspace">
					</td>
				</tr>
				</table>
				
			</form>
	    </div>   
	    <div title="进行审核" data-options="" style="overflow:auto;padding:20px;">   
	       <form method="post"  id="assocAudit_auditForm" class="easyui-form" data-options="novalidate:true" >
				<input id="assocAudit_uuid" type="hidden" name="uuid">
				<input id="assocAudit_frchseCd" type="hidden" name="frchseCd">
				<input id="assocAudit_mchntCd" type="hidden" name="mchntCd">
				<table class="table_info" border="0">
				<tr>
					<td width="100px">
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
					<td width="100px">
						<label>
							审核信息:
						</label>
					</td>
					<td class="tdspace">
					</td>
					<td>
						<textarea rows="5" name="auditInf" class="easyui-validatebox" maxlength="90"  style="width:320px;"></textarea>
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
			<td>
				<a id="assocAudit_doaudit" href="javascript:void(0);" class="easyui-linkbutton ct-qry-btn" style="width:100px;">审核</a>
			</td>
			<td class="hintspace">
			</td>
		</tr>
				</table>
			</form>  
	    </div>   
	</div>  
</div>
