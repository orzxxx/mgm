//@ sourceUrl=mchnt_logo.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	function init(){
		$("#mchntLogo_mchntCd").val(currentMchntCd);
		initPicture();
		initButton();
	}
	function initPicture(){
		$.post("mchnt/mchnt/get",{mchntCd: currentMchntCd},function(result){
			if (result.code == 0) {
				if(result.data.mchntLogo != "" && result.data.mchntLogo != null){
					$("#mchntLogo_hint").hide();
					$("#mchntLogo_img").attr('src', result.data.mchntLogo).show();
				}
			} else {
				$.messager.alert("提示", result.message);
			}
		}, "json");
	}
	function initButton(){
		$("#mchntLogo_picture").change(upload);
	}

	function upload(){
		if($("#mchntLogo_picture").validatebox('isValid') && $("#mchntLogo_picture").val() != ""){
			$('#mchntLogo_form').ajaxSubmit( {
    			url : "mchnt/mchnt/logo",
    			dataType : "json",
                success : function(result) {
					if (result.code == 0) {
						//图片回显
						$.messager.alert("提示", "图片上传成功");
	            		$("#mchntLogo_img").attr('src', result.data.pictureAddr).show();
	            		$("#mchntLogo_hint").hide();
					} else {
						$("#mchntLogo_picture").val("");
						$.messager.alert("提示", result.message);
					}
    			}
            })
		}
	}
	
    return {
        init : init
    }
    
});

	