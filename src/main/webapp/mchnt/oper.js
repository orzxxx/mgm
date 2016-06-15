//@ sourceUrl=oper.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	function init(){
		initDatagrid();
		query();
		initButton();
	}
	function initButton(){
		$("#oper_add").click(add);
		$("#oper_del").click(del);
		$("#oper_edit").click(edit);
		$("#oper_query").click(query);
		$("#oper_clear").click(clearForm);
	}
	function doPwdReset(operId){
		var dlg = $('<div/>').dialog({    
		    title: '操作员密码重置',    
		    width: 500,    
		    height: 200,    
		    closable: false,    
		    cache: false,    
		    href: 'mchnt/oper_pwd_reset.jsp',    
		    modal: true,
		    buttons : [ {
				text : '保存',
				handler : function(){
					
		    	if($('#pwd_form').form("validate")){
		    		$('#pwd_form').ajaxSubmit( {
		    			url : contextPath+"/mchnt/oper/resetPwd",
		    			dataType : "json",
		    			//data: param,
		                success : function(result) {
		                	if (result.code == 0) {
		                		$.messager.alert("提示", "修改成功!");
		                		//关闭对话框
		                		dlg.dialog('close');
		    	        		dlg.remove();
		                		
		    				} else {
		    					$.messager.alert("提示", result.message);
		    				}
		    			}
		            })
		    	}
		    }
			},{
				text : '关闭',
				handler : function() {
					dlg.dialog('close');
	        		dlg.remove();
				}
			}],
			onLoad : function(){
				$("#pwd_mchntCd").val(currentMchntCd);
				$("#pwd_operId").val(operId);
			}
		}); 
	}
	function initDatagrid(){
		$('#oper_pageList').datagrid({
				fit:true,
				pagination:true,
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				pageList : [ 10],
				columns:[[
				   {field:'operId',title:'操作员号',width:150,sortable:true,align:'center'},
				   {field:'operation',title:'操作',width:150,sortable:true,align:'center',formatter:function(value, rec){
						return "<a class=\"easyui-linkbutton\" operId='"+rec.operId+"'>密码重置</a>";
					}}
				]],
				toolbar:"#oper_toolbar",
				onDblClickRow:function(index, row){
					var operId = row.operId;
        			//var index = $("#oper_pageList").datagrid("getRowIndex", row);
        			doPwdReset(operId);
				}
			});
			//重写翻页事件
			var pageOpts = $('#oper_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
		}

		function check(){
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#oper_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				param.mchntCd = currentMchntCd;
				
				$.post("mchnt/oper/list",param,function(result){
					if (result.code == 0) {
	            		var data = result.data;
	            		$('#oper_pageList').datagrid('loadData',data);  
	            		
	            		$("td[field='operation'] a").click(function(){
	            			var operId = $(this).attr("operId");
	            			//var index = $("#oper_pageList").datagrid("getRowIndex", row);
	            			doPwdReset(operId);
	            		});
					} else {
						$.messager.alert("提示", result.message);
					}
				}, "json")
			}
	}
		
	function clearForm(){
		$("#oper_searchForm").form('clear');
		//$('#oper_pageList').datagrid('loadData',{ total: 0, rows: [] });
	}

	function add(){
		var dlg = $('<div/>').dialog({    
		    title: '添加操作员',    
		    width: 500,    
		    height: 170,    
		    closable: false,    
		    cache: false,    
		    href: 'mchnt/oper_form.jsp',    
		    modal: true,
		    buttons : [ {
				text : '添加',
				handler :function(){
		    	if($('#oper_form').form("validate")){
		    		$('#oper_form').ajaxSubmit( {
		    			url : contextPath+"/mchnt/oper/add",
		    			dataType : "json",
		                success : function(result) {
		                	if (result.code == 0) {
		                		$.messager.alert("提示", "添加成功!");
		                		//刷新
		                		reload();
		                		//关闭对话框
		                		dlg.dialog('close');
		    	        		dlg.remove();
		    				} else {
		    					$.messager.alert("提示", result.message);
		    				}
		    			}
		            })
		    	}
		    }
			},{
				text : '关闭',
				handler : function() {
					dlg.dialog('close');
		    		dlg.remove();
				}
			}],
			onLoad : function(){
				initForm();
			}
		});  
	}

	function edit(){
		var row = $('#oper_pageList').datagrid("getSelected");
		if(row){
			doPwdReset(row.operId);
		}else{
			$.messager.alert('提示', '请选择要修改的记录！', 'info');
		}
	}

	function del(){
		var row = $('#oper_pageList').datagrid("getSelected");
		if(row){
			$.messager.confirm('提示', '确定删除该记录?', function(r){
				if (r){
					var param = {};
					param.operId = row.operId;
					param.mchntCd = row.mchntCd;
					$.post(contextPath+"/mchnt/oper/del",param,function(result){
						if (result.code == 0) {
							$.messager.alert("提示", "删除成功!");
							//重新刷新
							reload();
						}else{
							$.messager.alert("提示", result.message);
						}
					},"json");
				}
			});
			
		}else{
			$.messager.alert('提示', '请选择要删除的记录!', 'info');
		}
	}

	function reload(){
		query();
	}
	
	function initForm(){
		//表单赋值
		$("#oper_mchntCd").val(userInfo.mchntCd);
	}

    return {
        init : init
    }
    
});

	