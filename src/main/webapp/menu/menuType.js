//@ sourceUrl=menuType.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var menuTypes = {};
	function init(){
		initButton();
		initDatagrid();
		query();
	}
	function initButton(){
		$("#menuType_add").click(add);
		$("#menuType_del").click(del);
		$("#menuType_edit").click(edit);
		$("#menuType_query").click(query);
		$("#menuType_clear").click(clearForm);
	}
	function initDatagrid(){
		$('#menutype_pageList').datagrid({
				fitColumns:false,
				fit:true,
				pagination:true,
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				pageList : [30],
				frozenColumns:[[
	                {field:'menutpId',title:'分类号',width:70,sortable:true}
				]],
				columns:[[
				    {field:'menutpName',title:'分类名',width:150,sortable:true,align:'center'},
				    {field:'menuNum',title:'单品数',width:150,sortable:true,align:'center'},
				]],
				toolbar:"#menutype_toolbar",
				onDblClickRow:function(index, row){
					edit();
				}
			});
			//重写翻页事件
			var pageOpts = $('#menutype_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
				
		}

		function check(){
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#menutype_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				param.mchntCd = currentMchntCd;
				
				$('#menutype_searchForm').ajaxSubmit( {
					data : param,
					url : contextPath+"/menu/type/list",
					dataType : "json",
		            success : function(result) {
		            	if (result.code == 0) {
		            		var data = result.data;
		            		$('#menutype_pageList').datagrid('loadData',data);  
		            		if (data.rows == null || data.rows.length == 0) {
		            			$("#menutype_total").text('0');
		            			$("#menutype_searchResult").addClass("ct-total0");
							}else{
								$("#menutype_searchResult").removeClass("ct-total0");
								$("#menutype_total").text(data.total);
							}
						} else {
							$.messager.alert("提示", result.message);
						}
					}
		        })
			}
	}
		
	function clearForm(){
		$("#menutype_searchForm").form('clear');
		//$('#menutype_pageList').datagrid('loadData',{ total: 0, rows: [] });
	}

	function add(){
		var dlg = $('<div/>').dialog({    
		    title: '添加分类',    
		    width: 350,    
		    height: 150,    
		    closable: false,    
		    cache: false,    
		    href: 'menu/menutype_form.jsp',    
		    modal: true,
		    buttons : [ {
				text : '添加',
				handler :function(){
		    	if($('#menutype_form').form("validate")){
		    		$('#menutype_form').ajaxSubmit( {
		    			url : contextPath+"/menu/type/add",
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
		var row = $('#menutype_pageList').datagrid("getSelected");
		if(row){
			var dlg = $('<div/>').dialog({    
			    title: '编辑分类',    
			    width: 350,    
			    height: 150,    
			    closable: false,    
			    cache: false,    
			    href: 'menu/menutype_form.jsp',    
			    modal: true,
			    buttons : [ {
					text : '保存',
					handler : function(){
			    	if($('#menutype_form').form("validate")){
			    		$('#menutype_form').ajaxSubmit( {
			    			url : contextPath+"/menu/type/update",
			    			dataType : "json",
			                success : function(result) {
			                	if (result.code == 0) {
			                		$.messager.alert("提示", "修改成功!");
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
					//数据填充
					$("#menutype_form").form('load', row);
				}
			}); 
		}else{
			$.messager.alert('提示', '请选择要修改的记录!', 'info');
		}
	}

	function del(){
		var row = $('#menutype_pageList').datagrid("getSelected");
		if(row){
			$.messager.confirm('提示', '确定删除该记录?', function(r){
				if (r){
					if(row.menuNum != "0"){
						$.messager.alert('提示', '该分类下存在菜品,不可删除!');
						return;
					}
					var param = {};
					param.menutpId = row.menutpId;
					param.mchntCd = currentMchntCd;
					$.post("menu/type/del",param,function(result){
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
		$("#menutype_mchntCd").val(userInfo.mchntCd);
	}
	
    return {
        init : init
    }
    
});

	