<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	requirejs(['terminal'],function  (terminal) {
			terminal.init();
		});
</script>
<div class="easyui-layout" fit="true">
	   	<div region="center" border="false"> 
				<table id="terminal_pageList"></table>
		</div>
		<div id="terminal_toolbar">        
		
		</div>
</div>
