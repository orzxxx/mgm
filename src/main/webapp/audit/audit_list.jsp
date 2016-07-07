<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['audit-list'],function  (audit) {
			audit.init();
		});
</script>
<div class="easyui-layout" fit="true">
	   	<div region="center" border="false"> 
				<table id="auditList_pageList"></table>
		</div>
		<div id="auditList_toolbar">        
			<a id="auditList_audit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"">审核</a>
		</div>
</div>
