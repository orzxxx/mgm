//@ sourceUrl=audit.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var auditInfo;
	
	function init(){
		initList();
		initButton();
		
		$('#audit_addr').citypicker();
	}
	function initButton(){
		//$("#audit_edit").click(edit);
		
	}

	function edit(){
			var dlg = $('<div/>').dialog({    
			    title: '修改个人信息',    
			    width: 500,    
			    height: 460,    
			    closable: false,    
			    cache: false,    
			    href: 'audit/audit_form.jsp',    
			    modal: true,
				buttons : [ {
					text : '保存',
					handler :function(){
					if($('#audit_form').form("validate")){
			    		$('#audit_form').ajaxSubmit( {
			    			url : contextPath+"/mchnt/mchnt/update",
			    			dataType : "json",
			                success : function(result) {
				    			if (result.code == 0) {
			                		$.messager.alert("提示", "修改成功!");
			                		//刷新数据
			                		initList();
			                		$("#main_userName").text($("#audit_userName").val());
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
					$("#audit_form").form('load', auditInfo);
					$("#audit_userId").numberbox('disable');
					$("#audit_district").citypicker();
				}
			}); 
		
	}
	
	function initList(){
		//载入资料t
		$.post("mchnt/mchnt/get",{mchntCd: currentMchntCd},function(result){
			if (result.code == 0) {
				auditInfo = result.data;
        		$("#auditInfo").form('load', auditInfo);
        		$("#auditInfo_mchntCd").val(currentMchntCd);
        		//$('#audit_userId').numberbox('disable');
			} else {
				$.messager.alert("提示", result.message);
			}
		}, "json");
		//查询审核状态
		$.post("mchnt/audit/get",{mchntCd: currentMchntCd},function(result){
			if (result.code == 0) {
				if (parseInt(result.data.auditStatus) == -1 ) {
					//$('#audit_edit').linkbutton('enable');
					$('#audit_edit').show();
					$("#audit_edit").click(edit);
				}
			} else {
				$.messager.alert("提示", result.message);
			}
		}, "json");
	}

    return {
        init : init
    }
    
});

	