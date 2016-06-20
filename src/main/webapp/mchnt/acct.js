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
					
					$("#acct_license").change(function(){
						setValidType('license');
					});
					$("#acct_taxCard").change(function(){
						setValidType('taxCard');
					});
					$("#acct_orgCode").change(function(){
						setValidType('orgCode');
					});
				}
			}); 
		
	}
	function setValidType(type){
		//更改校验方式
		$('#register_xxx').validatebox({    
		    validType: type   
		});  
		//校验当前数据
		$('#register_xxx').validatebox('validate'); 
	}
	function initList(){
		$.post("mchnt/mchnt/get",{mchntCd: currentMchntCd},function(result){
			if (result.code == 0) {
				acctInfo = result.data;
        		$("#acctInfo").form('load', acctInfo);
        		$("#acctInfo_mchntCd").val(currentMchntCd);
        		//$('#acct_userId').numberbox('disable');
        		//显示证件号
        		if (acctInfo.a == "1") {
					$("#a_name").text("12");
				} else if(acctInfo.a == "2"){
					$("#a_name").text("12");
				} else if(acctInfo.a == "2"){
					$("#a_name").text("12");
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

	