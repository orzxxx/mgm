//@ sourceUrl=audit.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var auditInfo;
	
	function init(){
		initList();
		initButton();
		
		$('#audit_addr').citypicker();
		
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
	function initButton(){
		//$("#audit_edit").click(edit);
		
	}

	function edit(){
			var dlg = $('<div/>').dialog({    
			    title: '修改个人信息',    
			    width: 500,    
			    height: 500,    
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
					//
					$("#audit_license").change(function(){
						setValidType('license');
					});
					$("#audit_taxCard").change(function(){
						setValidType('taxCard');
					});
					$("#audit_orgCode").change(function(){
						setValidType('orgCode');
					});
					//设置当前校验方式
					var type = "";
					if (auditInfo.licenseType == "1") {
						type = "license";
					}else if(auditInfo.licenseType == "2"){
						type = "taxCard";
					}else if(auditInfo.licenseType == "3"){
						type = "orgCode";
					}
					setValidType(type);
				}
			}); 
		
	}
	
	function setValidType(type){
		//更改校验方式
		$('#audit_licenseNumber').validatebox({    
		    validType: type   
		});  
		//校验当前数据
		$('#audit_licenseNumber').validatebox('validate'); 
	}
	
	function initList(){
		//载入资料
		$.post("mchnt/mchnt/get",{mchntCd: currentMchntCd},function(result){
			if (result.code == 0) {
				auditInfo = result.data;
        		$("#auditInfo").form('load', auditInfo);
        		$("#auditInfo_mchntCd").val(currentMchntCd);
        		//$('#audit_userId').numberbox('disable');
        		//显示证件号
        		changeLicenseView(auditInfo.licenseType);
			} else {
				$.messager.alert("提示", result.message);
			}
		}, "json");
	}
	
	//更改显示的证件号名称
	function changeLicenseView(licenseType){
		if (licenseType == "1") {
			$("#audit_license_label").text("营业执照:");
		} else if(licenseType == "2"){
			$("#audit_license_label").text("税务登记证:");
		} else if(licenseType == "3"){
			$("#audit_license_label").text("组织机构代码证:");
		} 
	}
	
    return {
        init : init
    }
    
});

	