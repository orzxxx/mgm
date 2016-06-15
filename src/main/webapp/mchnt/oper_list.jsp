<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['oper'],function  (oper) {
			oper.init();
		});
</script>
<div class="easyui-layout" fit="true">
	   	<div region="center" border="false"> 
				<table id="oper_pageList"></table>
		</div>
		<div id="oper_toolbar">        
			<a id="oper_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"">修改</a>
			<a id="oper_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"">删除</a>
			<a id="oper_add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"">添加</a>
		</div>
</div>
