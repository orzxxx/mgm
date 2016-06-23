//@ sourceUrl=assocApply.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var auditInfo;
	
	function init(){
		initState();
		initButton();
	}
	function initButton(){
		$("#assocApply_add").click(add);
		
	}
	
	function initState(){
		//查询关联状态
		$.post("mchnt/assoc/get",{mchntCd: currentMchntCd},function(result){
			if (result.code == 0) {
				if (result.data == null) {
					changeState("0");
				} else {
					changeState(result.data.auditStatus, result);
				}
			} else {
				$.messager.alert("提示", result.message);
			}
		}, "json");
	}

	function changeState(state, result){
		$("#assocResult").hide();
		$("#assocInfo").hide();
		if (state == "0") {
			$("#assocApply_state").text("当前未关联总部账号");
		}else if(state == "2"){
			$("#assocApply_add").hide();
			$("#assocApply_state").text("审核中,请耐心等待审核结果");
			$("#assocInfo").form('load', result.data.frchse);
			$("#assocInfo").show();
		}else if(state == "1"){
			$("#assocApply_add").hide();
			$("#assocApply_state").text("已关联总部账号");
			$("#assocInfo").form('load', result.data.frchse);
			$("#assocInfo").show();
		}else if(state == "-1"){
			$("#assocApply_state").text("申请被拒绝");
			var time = result.data.auditTime;
			time = time.substring(0,4)+"年"+time.substring(4,6)+"月"+time.substring(6,8)+"日"+
				time.substring(8,10)+"时"+time.substring(10,12)+"分"+time.substring(12,14)+"秒";
			result.data.auditTime = time;
			$("#assocResult").form('load', result.data);
			$("#assocResult").show();
		}
	}
	
	function add(){
			var dlg = $('<div/>').dialog({    
			    title: '关联申请',    
			    width: 500,    
			    height: 400,    
			    closable: false,    
			    cache: false,    
			    href: 'assoc/assoc_form.jsp',    
			    modal: true,
				buttons : [ {
					text : '确定',
					handler :function(){
					if($('#audit_form').form("validate")){
						if($("#assocForm_userId").val() == ""){
							$.messager.alert("提示", "请先查询总部信息");
							return;
						}
			    		$('#assoc_form').ajaxSubmit( {
			    			url : contextPath+"/mchnt/assoc/add",
			    			dataType : "json",
			                success : function(result) {
				    			if (result.code == 0) {
				    				$.messager.alert("提示", "提交申请成功,请耐心等待审核结果");
			                		initState();
			                		//关闭对话框
			                		dlg.dialog('close');
			    	        		dlg.remove();
			    				} else {
			    					$.messager.alert("提示", result.message);
			    				}
			    			}
			            })
			    	}
			    }},{
					text : '关闭',
					handler : function() {
						dlg.dialog('close');
			    		dlg.remove();
					}
				}],
				onLoad : function(){
					$("#assoc_mchntCd").val(currentMchntCd);
					$("#assoc_query").click(queryFrchse);
				}
			}); 
		
	}
	
	function queryFrchse(){
		var frchseCd = $("#assoc_frchseCd").val();
		if (frchseCd == "") {
			$.messager.alert("提示", "请输入总部编号");
			return;
		}
		$.post("mchnt/frchse/get",{frchseCd: frchseCd},function(result){
			if (result.code == 0) {
				if (result.data == null) {
					$.messager.alert("提示", "未找到总部信息");
					return;
				}
        		$("#assoc_form").form('load', result.data);
			} else {
				$.messager.alert("提示", result.message);
			}
		}, "json");
	}
	
    return {
        init : init
    }
    
});

	