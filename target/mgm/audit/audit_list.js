//@ sourceUrl=auditList.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	function init(){
		initDatagrid();
		query();
		initButton();
	}
	function initButton(){
		$("#auditList_audit").click(audit);
	}
	
	function initDatagrid(){
		$('#auditList_pageList').datagrid({
				fit:true,
				pagination:true,
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				pageList : [ 10],
				columns:[[
				   {field:'mchntCd',title:'商户号',width:150,sortable:true,align:'center'},
				   {field:'mchntName',title:'店名',width:150,sortable:true,align:'center'},
				   {field:'userName',title:'姓名',width:150,sortable:true,align:'center'},
				   {field:'userId',title:'手机号',width:150,sortable:true,align:'center'},
				   {field:'submitTime',title:'提交时间',width:150,sortable:true,align:'center',formatter:function(value, rec){
						return  value.substring(0,4)+"-"+value.substring(4,6)+"-"+value.substring(6,8);
					}}
				]],
				toolbar:"#auditList_toolbar",
				onDblClickRow:function(index, row){
					audit();
				}
			});
			//重写翻页事件
			var pageOpts = $('#auditList_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
		}

		function check(){
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#auditList_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				param.mchntCd = currentMchntCd;
				
				$.post("mchnt/audit/list",param,function(result){
					if (result.code == 0) {
	            		var data = result.data;
	            		var rows = data.rows;
	            		for ( var i in rows) {
	            			$.extend(rows[i], rows[i].mchnt);
						}
	            		$('#auditList_pageList').datagrid('loadData',data);  
					} else {
						$.messager.alert("提示", result.message);
					}
				}, "json")
			}
	}

	function audit(){
		var row = $('#auditList_pageList').datagrid("getSelected");
		if(row){
			var dlg = $('<div/>').dialog({    
			    title: '审核界面',    
			    width: 700,    
			    height: 500,    
			    closable: false,    
			    cache: false,    
			    href: 'audit/audit_doaudit.jsp',    
			    modal: true,
			    buttons : [ {
					text : '关闭',
					handler : function() {
						dlg.dialog('close');
		        		dlg.remove();
					}
				}],
				onLoad : function(){
					$('#doaudit_tab').tabs({    
					    border:false, 
					   fit:true
					});  
					$("#doaudit_form").form('load', row);	
					$("#doaudit_mchntCd").val(row.mchntCd);
					$("#doaudit_idCardFrontImg").attr("src", row.idCardFront);
					$("#doaudit_idCardBackImg").attr("src", row.idCardBack);
					$("#doaudit_licenseFrontImg").attr("src", row.licenseFront);
					$("#doaudit_licenseBackImg").attr("src", row.licenseBack);
					
					$("#doaudit_doaudit").click(function(){
						doAudit(dlg);
						
					});
					
					$("#doaudit_idCardFrontImg, #doaudit_idCardBackImg," +
					" #doaudit_licenseFrontImg, #doaudit_licenseBackImg").dblclick(function(){
						var src = $(this).attr("src");
						var dlg = $('<div/>').dialog({    
							title: "原图",
							width :600,
							height: 400,
						    closable: false,    
						    cache: false,    
						    content: "<img id='viewimg'/>",
						    modal: true,
						    buttons : [ {
								text : '关闭',
								handler : function() {
									dlg.dialog('close');
					        		dlg.remove();
								}
							}],
							onOpen : function(){
								$("#viewimg").attr("src", src);
							}
						}); 
					});
				}
			}); 
		}else{
			$.messager.alert('提示', '请选择要修改的记录！', 'info');
		}
	}

	function doAudit(dlg){
		if($('#doaudit_auditForm').form("validate")){
    		$('#doaudit_auditForm').ajaxSubmit( {
    			url : contextPath+"/mchnt/audit/audit",
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
	}

    return {
        init : init
    }
    
});

	