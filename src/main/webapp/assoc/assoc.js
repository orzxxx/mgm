//@ sourceUrl=assoc.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	function init(){
		initButton();
		initDatagrid();
		query();
	}
	function initButton(){
		$("#assoc_audit").click(audit);
	}
	function initDatagrid(){
		$('#assoc_pageList').datagrid({
				fit:true,
				pagination:true,
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				pageList : [ 30 ],
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
				toolbar:"#assoc_toolbar",
				onDblClickRow:function(index, row){
					audit();
				}
			});
			//重写翻页事件
			var pageOpts = $('#assoc_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
				
		}

		function check(){
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#assoc_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				param.frchseCd = currentMchntCd;
				
				$('#assoc_searchForm').ajaxSubmit( {
					data : param,
					url : contextPath+"/mchnt/assoc/list",
					dataType : "json",
		            success : function(result) {
		            	if (result.code == 0) {
		            		var data = result.data;
		            		for ( var i = 0; i < data.rows.length; i++) {
		            			$.extend(data.rows[i], result.data.rows[i].mchnt);
							}
		            		$('#assoc_pageList').datagrid('loadData',data);
							if (data.rows == null || data.rows.length == 0) {
		            			$("#assoc_total").text('0');
		            			$("#assoc_searchResult").addClass("ct-total0");
							}else{
								$("#assoc_searchResult").removeClass("ct-total0");
								$("#assoc_total").text(data.total);
							}  
						} else {
							$.messager.alert("提示", result.message);
						}
					}
		        })
			}
	}
		
	function clearForm(){
		$("#assoc_searchForm").form('clear');
		//$('#assoc_pageList').datagrid('loadData',{ total: 0, rows: [] });
	}

	function audit(){
		var row = $('#assoc_pageList').datagrid("getSelected");
		if(row){
			var dlg = $('<div/>').dialog({    
			    title: '审核界面',    
			    width: 600,    
			    height: 400,    
			    closable: false,    
			    cache: false,    
			    href: 'assoc/assoc_doaudit.jsp',    
			    modal: true,
			    buttons : [{
					text : '关闭',
					handler : function() {
						dlg.dialog('close');
		        		dlg.remove();
					}
				}],
				onLoad : function(){
					$('#assocAudit_tab').tabs({    
					    border:false, 
					   fit:true
					}); 
					$("#assocAudit_form").form('load', row);
					$("#assocAudit_frchseCd").val(currentMchntCd);
					$("#assocAudit_mchntCd").val(row.mchntCd);
					$("#assocAudit_uuid").val(row.uuid);
					
					$("#assocAudit_doaudit").click(function(){
						doAudit(dlg);
					});
				}
			}); 
		}else{
			$.messager.alert('提示', '请选择要审核的记录！', 'info');
		}
	}

	function doAudit(dlg){
		if($('#assocAudit_auditForm').form("validate")){
    		$('#assocAudit_auditForm').ajaxSubmit( {
    			url : contextPath+"/mchnt/assoc/update",
    			dataType : "json",
                success : function(result) {
                	if (result.code == 0) {
                		$.messager.alert("提示", "审核成功!");
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
	
	function reload(){
		query();
	}
	
	function initForm(){
		//表单赋值
		$("#assoc_mchntCd").val(userInfo.mchntCd);
	}

    return {
        init : init
    }
    
});

	