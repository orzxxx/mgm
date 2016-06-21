//@ sourceUrl=acct.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var acctInfo;
	
	function init(){
		initList();
		initButton();
		
		$('#acct_addr').citypicker();
	}
	function initButton(){
		$("#acct_edit").click(edit);
	}

	function edit(){
			var dlg = $('<div/>').dialog({    
			    title: '修改个人信息',    
			    width: 500,    
			    height: 500,    
			    closable: false,    
			    cache: false,    
			    href: 'mchnt/acct_form.jsp',    
			    modal: true,
				buttons : [ {
					text : '保存',
					handler :function(){
					if($('#acct_form').form("validate")){
			    		$('#acct_form').ajaxSubmit( {
			    			url : contextPath+"/mchnt/mchnt/update",
			    			dataType : "json",
			                success : function(result) {
				    			if (result.code == 0) {
			                		$.messager.alert("提示", "修改成功!");
			                		//刷新数据
			                		initList();
			                		$("#main_userName").text($("#acct_userName").val());
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
					$("#acct_form").form('load', acctInfo);
					$("#acct_userId").numberbox('disable');
					$("#acct_district").citypicker();
					//证件号
					$("#acct_license").change(function(){
						setValidType('license');
					});
					$("#acct_taxCard").change(function(){
						setValidType('taxCard');
					});
					$("#acct_orgCode").change(function(){
						setValidType('orgCode');
					});
					//设置当前校验方式
					var type = "";
					if (acctInfo.licenseType == "1") {
						type = "license";
					}else if(acctInfo.licenseType == "2"){
						type = "taxCard";
					}else if(acctInfo.licenseType == "3"){
						type = "orgCode";
					}
					setValidType(type);
				}
			}); 
		
	}
	function setValidType(type){
		//更改校验方式
		$('#acct_licenseNumber').validatebox({    
		    validType: type   
		});  
		//校验当前数据
		$('#acct_licenseNumber').validatebox('validate'); 
	}
	function initList(){
		$.post("mchnt/mchnt/get",{mchntCd: currentMchntCd},function(result){
			if (result.code == 0) {
				acctInfo = result.data;
        		$("#acctInfo").form('load', acctInfo);
        		$("#acctInfo_mchntCd").val(currentMchntCd);
        		//$('#acct_userId').numberbox('disable');
        		//显示证件号
        		changeLicenseView(acctInfo.licenseType);
			} else {
				$.messager.alert("提示", result.message);
			}
		}, "json");
	}
	//更改显示的证件号名称
	function changeLicenseView(licenseType){
		if (licenseType == "1") {
			$("#acct_license_label").text("营业执照:");
		} else if(licenseType == "2"){
			$("#acct_license_label").text("税务登记证:");
		} else if(licenseType == "3"){
			$("#acct_license_label").text("组织机构代码证:");
		} 
	}
	
    return {
        init : init
    }
    
});

	