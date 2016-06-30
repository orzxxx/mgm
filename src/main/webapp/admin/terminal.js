//@ sourceUrl=terminal.js
define(function () {
	//var currentMchntCd = userInfo.mchntCd;
	function init(){
		initButton();
		initDatagrid();
		query();
	}
	function initButton(){
		$("#terminal_query").click(query);
		$("#terminal_clear").click(clearForm);
		$("#terminal_del").click(del);
	}
	function initDatagrid(){
		$('#terminal_pageList').datagrid({
				fit:true,
				pagination:true,
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				pageList : [ 30 ],
				frozenColumns:[[
	                {field:'terminalCd',title:'终端编码',width:70,sortable:true}
				]],
				columns:[[
				   {field:'terminalSn',title:'SN号',width:150,sortable:true,align:'center'},
				   {field:'mchntCd',title:'商户号',width:150,sortable:true,align:'center'},
					{field:'date',title:'绑定日期',width:150,sortable:true,align:'center',formatter:function(value){
						return value.substring(0,4)+"/"+value.substring(4,6)+"/"+value.substring(6,8);
					}},

				]],
				toolbar:"#terminal_toolbar"
			});
			//重写翻页事件
			var pageOpts = $('#terminal_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
				
		}

		function check(){
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#terminal_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				//param.mchntCd = currentMchntCd;
				
				$('#terminal_searchForm').ajaxSubmit( {
					data : param,
					url : contextPath+"/mchnt/terminal/list",
					dataType : "json",
		            success : function(result) {
		            	if (result.code == 0) {
		            		var data = result.data;
		            		$('#terminal_pageList').datagrid('loadData',data); 
		            		if (data.rows == null || data.rows.length == 0) {
		            			$("#terminal_total").text('0');
		            			$("#terminal_searchResult").addClass("ct-total0");
							}else{
								$("#terminal_searchResult").removeClass("ct-total0");
								$("#terminal_total").text(data.total);
							}
						} else {
							$.messager.alert("提示", result.message);
						}
					}
		        })
			}
	}
		
	function clearForm(){
		$("#terminal_searchForm").form('clear');
		//$('#terminal_pageList').datagrid('loadData',{ total: 0, rows: [] });
	}


	function del(){
		var row = $('#terminal_pageList').datagrid("getSelected");
		if(row){
			$.messager.confirm('提示', '确定解除绑定?', function(r){
				if (r){
					var param = {};
					param.terminalCd = row.terminalCd;
					param.mchntCd = row.mchntCd;
					$.post(contextPath+"/mchnt/terminal/del",param,function(result){
						if (result.code == 0) {
							$.messager.alert("提示", "解绑成功!");
							//重新刷新
							reload();
						}else{
							$.messager.alert("提示", result.message);
						}
					},"json");
				}
			});
			
		}else{
			$.messager.alert('提示', '请选择要解绑的终端!', 'info');
		}
	}

	function reload(){
		query();
	}
	
	function initForm(){
		//表单赋值
		//$("#terminal_mchntCd").val(userInfo.mchntCd);
	}

    return {
        init : init
    }
    
});

	