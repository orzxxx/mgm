//@ sourceUrl=audit_submit.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var auditInfo;
	
	function init(){
		initForm();
		initButton();
		
	}
	function initForm(){
		$("#auditSubmit_mchntCd").val(currentMchntCd);
				
		//查询审核状态和图片
		$.post("mchnt/audit/get",{mchntCd: currentMchntCd},function(result){
			if (result.code == 0) {
				//图片回显
				var data = result.data;
				if (data.idCardFront != "") {
					$("#auditSubmit_idCardFrontImg").attr("src", data.idCardFront).css('visibility','visible');
				}
				if (data.idCardBack != "") {
					$("#auditSubmit_idCardBackImg").attr("src", data.idCardBack).css('visibility','visible');
				}
				if (data.licenseFront != "") {
					$("#auditSubmit_licenseFrontImg").attr("src", data.licenseFront).css('visibility','visible');
				}
				if (data.licenseBack != "") {
					$("#auditSubmit_licenseBackImg").attr("src", data.licenseBack).css('visibility','visible');
				}
				
				if (parseInt(result.data.auditStatus) == 2 ) {
					$('#auditSubmit_submit').linkbutton('disable');
					$("#auditSubmit_submit").unbind("click");
					$('#auditSubmit_licenseFront').attr("disabled", "disabled");
					$('#auditSubmit_licenseBack').attr("disabled", "disabled");
					$('#auditSubmit_idCardFront').attr("disabled", "disabled");
					$('#auditSubmit_idCardBack').attr("disabled", "disabled");
				}
			} else {
				$.messager.alert("提示", result.message);
			}
		}, "json");
	}
	function initButton(){
		
		$("#auditSubmit_submit").click(submit);
		
		$("#auditSubmit_idCardFrontImg, #auditSubmit_idCardBackImg," +
				" #auditSubmit_licenseFrontImg, #auditSubmit_licenseBackImg").dblclick(function(){
			var src = $(this).attr("src");
			if (src == "" || src == null) {
				$.messager.alert("提示", "图片还未上传, 请上传图片");
				return;
			}
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
		
		$("#auditSubmit_idCardFront, #auditSubmit_idCardBack, #auditSubmit_licenseFront, #auditSubmit_licenseBack").change(function(){
			var propertyName = $(this).attr("name");
			upload(propertyName);
		});
	}

	function upload(propertyName){
		if($("#auditSubmit input[name='"+propertyName+"']").validatebox('isValid') && $("#auditSubmit input[name='"+propertyName+"']").val() != ""){
			var param = {};
			param.propertyName = propertyName;
			$('#auditSubmit').ajaxSubmit( {
    			url : "mchnt/audit/upload",
    			data: param,
    			dataType : "json",
                success : function(result) {
					if (result.code == 0) {
						//图片回显
						$.messager.alert("提示", "图片上传成功");
	            		$("#auditSubmit_"+propertyName+"Img").attr('src', result.data.pictureAddr).css('visibility','visible');
					} else {
						$("#auditSubmit input[name='"+propertyName+"']").val("");
						$.messager.alert("提示", result.message);
					}
    			}
            })
		}
	}

	function submit(){
		$.post("mchnt/audit/submit",{mchntCd: currentMchntCd},function(result){
			if (result.code == 0) {
				$.messager.alert("提示", "提交申请成功, 请耐心等待审核结果");
				$('#auditSubmit_submit').linkbutton('disable');
				$("#auditSubmit_submit").unbind("click");
				$('#auditSubmit_licenseFront').attr("disabled", "disabled");
				$('#auditSubmit_licenseBack').attr("disabled", "disabled");
				$('#auditSubmit_idCardFront').attr("disabled", "disabled");
				$('#auditSubmit_idCardBack').attr("disabled", "disabled");
			} else {
				$.messager.alert("提示", result.message);
			}
		}, "json");
	}
	
    return {
        init : init
    }
    
});

	