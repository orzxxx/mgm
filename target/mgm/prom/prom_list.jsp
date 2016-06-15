<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['prom'],function  (prom) {
			prom.init();
		});
</script>
<div class="easyui-layout" fit="true">
	   	<div region="center" border="false"> 
				<table id="prom_pageList"></table>
		</div>
		<div id="prom_toolbar">        
			<a id="prom_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"">编辑</a>
			<a id="prom_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"">删除</a>
			<a id="prom_add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"">添加</a>
		</div>
</div>
