//@ sourceUrl=frchse.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var frchseInfo;
	
	function init(){
		initList();
		initButton();
		
	}
	function initButton(){
		$("#frchse_edit").click(edit);
	}


	function edit(){
			var dlg = $('<div/>').dialog({    
			    title: '修改个人信息',    
			    width: 500,    
			    height: 300,    
			    closable: false,    
			    cache: false,    
			    href: 'mchnt/frchse_form.jsp',    
			    modal: true,
				buttons : [ {
					text : '保存',
					handler :function(){
					if($('#frchse_form').form("validate")){
			    		$('#frchse_form').ajaxSubmit( {
			    			url : contextPath+"/mchnt/frchse/update",
			    			dataType : "json",
			                success : function(result) {
				    			if (result.code == 0) {
			                		$.messager.alert("提示", "修改成功!");
			                		//刷新数据
			                		initList();
			                		$("#main_userName").text($("#frchse_userName").val());
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
					$("#frchse_form").form('load', frchseInfo);
					$("#frchse_userId").numberbox('disable');
				}
			}); 
		
	}
	
	function initList(){
		$.post("mchnt/frchse/get",{frchseCd: currentMchntCd},function(result){
			if (result.code == 0) {
				frchseInfo = result.data;
        		$("#frchseInfo").form('load', frchseInfo);
        		$("#frchseInfo_frchseCd").val(currentMchntCd);
			} else {
				$.messager.alert("提示", result.message);
			}
		}, "json");
	}

    return {
        init : init
    }
    
});

	