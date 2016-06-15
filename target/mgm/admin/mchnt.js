//@ sourceUrl=mchnt.js
define(function () {
	//var currentMchntCd = userInfo.mchntCd;
	function init(){
		initButton();
		initDatagrid();
		query();
	}
	function initButton(){
		$("#mchnt_add").click(add);
		$("#mchnt_del").click(del);
		$("#mchnt_edit").click(edit);
		$("#mchnt_query").click(query);
		$("#mchnt_clear").click(clearForm);
	}
	function initDatagrid(){
		$('#mchnt_pageList').datagrid({
				fit:true,
				pagination:true,
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				pageList : [ 10, 20, 30 ],
				frozenColumns:[[
	                {field:'mchntCd',title:'商户号',width:100,sortable:true}
				]],
				columns:[[
				    {field:'userId',title:'手机号',width:150,sortable:true,align:'center'},
					{field:'mchntName',title:'商户名',width:120,sortable:true,align:'center'},
					{field:'userName',title:'姓名',width:120,sortable:true,align:'center'},
					{field:'idCard',title:'身份证号',width:150,sortable:true,align:'center'},
					{field:'email',title:'邮箱',width:150,sortable:true,align:'center'},
					{field:'mchntAddr',title:'商户地址',width:200,sortable:true,align:'center'}
				]],
				toolbar:"#mchnt_toolbar"/*,
				onDblClickRow:function(index, row){
					edit();
				}*/
			});
			//重写翻页事件
			var pageOpts = $('#mchnt_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
				
		}

		function check(){
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#mchnt_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				//param.frchseCd = currentMchntCd;
				param.mchntName = $("#mchntSearch_name").val();
				param.mchntCd = $("#mchntSearch_cd").val();
				
				$.post("mchnt/mchnt/all", param, function(result){
					if (result.code == 0) {
	            		var data = result.data;
	            		$('#mchnt_pageList').datagrid('loadData',data);  
	            		if (data.rows == null || data.rows.length == 0) {
	            			$("#mchnt_total").text('0');
	            			$("#mchnt_searchResult").addClass("ct-total0");
						}else{
							$("#mchnt_searchResult").removeClass("ct-total0");
							$("#mchnt_total").text(data.total);
						}
					} else {
						$.messager.alert("提示", result.message);
					}
				}, "json");
				
				/*$('#mchnt_searchForm').ajaxSubmit( {
					data : param,
					url : contextPath+"/mchnt/mchnt/list",
					dataType : "json",
		            success : function(result) {
		            	if (result.code == 0) {
		            		var data = result.data;
		            		$('#mchnt_pageList').datagrid('loadData',data);  
						} else {
							$.messager.alert("提示", result.message);
						}
					}
		        })*/
			}
	}
		
	function clearForm(){
		$("#mchnt_searchForm").form('clear');
		//$('#mchnt_pageList').datagrid('loadData',{ total: 0, rows: [] });
	}

	function add(){
		var dlg = $('<div/>').dialog({    
		    title: '添加商户',    
		    width: 500,    
		    height: 400,    
		    closable: false,    
		    cache: false,    
		    href: 'mchnt/mchnt_form.jsp',    
		    modal: true,
		    buttons : [ {
				text : '添加',
				handler :function(){
		    	if($('#mchnt_form').form("validate")){
		    		$('#mchnt_form').ajaxSubmit( {
		    			url : contextPath+"/mchnt/mchnt/add",
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
				$("#mchnt_district").citypicker();
			}
		});  
	}

	function edit(){
		var row = $('#mchnt_pageList').datagrid("getSelected");
		if(row){
			var dlg = $('<div/>').dialog({    
			    title: '编辑商户',    
			    width: 500,    
			    height: 350,    
			    closable: false,    
			    cache: false,    
			    href: 'mchnt/mchnt_form.jsp',    
			    modal: true,
			    buttons : [ {
					text : '保存',
					handler : function(){
			    	if($('#mchnt_form').form("validate")){
			    		$('#mchnt_form').ajaxSubmit( {
			    			url : contextPath+"/mchnt/mchnt/update",
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
					$("#mchnt_form").form('load', row);
					$("#mchnt_district").citypicker();
					$("#mchnt_repasswd").parents("tr").remove();
					$("#mchnt_passwd").parents("tr").remove();
					$("#mchnt_userId").attr("disabled", true);
					//$("#acct_userId").numberbox('disable');
				}
			}); 
		}else{
			$.messager.alert('提示', '请选择要修改的记录！', 'info');
		}
	}

	function del(){
		var row = $('#mchnt_pageList').datagrid("getSelected");
		if(row){
			$.messager.confirm('提示', '确定删除该记录?', function(r){
				if (r){
					var param = {};
					param.productId = row.productId;
					$.post(contextPath+"/mchnt/mchnt/del",param,function(result){
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
		$("#mchnt_frchseCd").val(userInfo.mchntCd);
		$("#mchnt_mchntCd").val(userInfo.mchntCd);
	}

    return {
        init : init
    }
    
});

	