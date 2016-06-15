<%@ page language="java"  pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <base href="<%=basePath%>">
     <jsp:include flush="true" page="/include/inc.jsp"></jsp:include>
     <script language="javascript">
     	var pageList;
     	$(function(){
     		var options = {
     			datagridId:"pageList",
				url:'ins/list.do',
				frozenColumns:[[
	                {field:'insCd',title:'运营商编号',width:150,sortable:true}
				]],
				columns:[[
					{field:'insNm',title:'运营商名称',width:150,sortable:true}
				]],
				singleSelect:true
			};
     		pageList = new Datagrid(options);
     	});
     	function find(){
			pageList.load(ct.serializeObject($('#form1').form()));
		}
		
		function clearForm(){
			$("#form1").form('clear');
			pageList.load({});
		}
		
     </script>
     
  </head>
  <body class="easyui-layout" fit="true">
   		<div region="north"  border="false"  style="height: 90px;overflow: hidden;" >  
	         <div style="margin-top: 5px;margin-left: 5px;">
		         	<form id="form1" name="form1" method="post">
						<table class="tablearea" >
							<tr>
								<td>
									<label for="insCd">
										运营商编号:
									</label>
								</td>
								<td class="tdspace">
			
								</td>
								<td>
									<input type="text" name="insCd" id="insCd" maxlength="20" style="width:165px;">
								</td>
								<td class="hintspace"></td>
							</tr>
							<tr>
								<td>
									<label for="insNm">
										运营商名称:
									</label>
								</td>
								<td class="tdspace">
			
								</td>
								<td>
									<input type="text" name="insNm" id="insNm" maxlength="20" style="width:165px;">
								</td>
								<td class="hintspace"></td>
							</tr>
						</table>
						<div style="margin-top: 5px;">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="find();">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="clearForm();">重置</a>
						</div>
				</form>
	         </div>
		</div>
   		<div region="center" border="false"> 
			<table id="pageList"></table>
		</div>
		<div id="toolbar">        
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit();">详情</a>      -->  
		</div>
  </body>
</html>
