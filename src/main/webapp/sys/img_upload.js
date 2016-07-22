//@ sourceUrl=menu_attr.js
define(function () {
	/**
	 * 初始有图片时，没图片时
	 * 取消时删除temp图片,change时删除temp图片
	 * 初始设置比例
	 * 文件类型校验
	 * 上传文件类型menu combo prom
	 */
	var mchntCd = "";
	var picturePath = "";
	var pictureLink = "";
	
	function init(){
		initForm();
		initButton();
	}
	
	function initForm(){
		$('#img_picture').cropper({
			  aspectRatio: 16 / 9,
			  viewMode: 1,
			  checkCrossOrigin: false,
			  crop: function(e) {
				  
			  }
			});
	}
	
	function initButton(){
		$("#img_select").change(select);
		$("#img_upload").click(upload);
	}
	
	function upload(){
		var cropperData = $('#img_picture').cropper('getData');
		var param = cropperData;
		param.mchntCd = userInfo.mchntCd;
		param.path = picturePath;
		
		$('#img_form').ajaxSubmit( {
			url : "menu/menu/upload",
			dataType : "json",
			data: param,
            success : function(result) {
				if (result.code == 0) {
					//图片回显
            		//$("#img_picture").attr('src', result.data.pictureAddr);
            		//
            		//$('#img_picture').cropper('reset').cropper('replace', result.data.pictureAddr);
					$('#img_picture').cropper('reset').cropper('replace', result.data.pictureAddr);
				} else {
					//待处理$("#menu_picture").val("");
					$.messager.alert("提示", result.message);
				}
			}
        });
	}
	
	function select(){
		if ($("#img_select").val() == "") {
			//待处理
			//$("#img_picture").attr('src', "");
			return;
		}
		//添加图片类型校验
			$('#img_form').ajaxSubmit( {
    			url : "menu/menu/uploadTemp",
    			dataType : "json",
                success : function(result) {
					if (result.code == 0) {
						//图片回显
	            		//$("#img_picture").attr('src', result.data.pictureAddr);
	            		//
	            		//$('#img_picture').cropper('reset').cropper('replace', result.data.pictureAddr);
						picturePath = result.data.picturePath;
						$('#img_picture').cropper('reset').cropper('replace', result.data.pictureAddr);
					} else {
						//待处理$("#menu_picture").val("");
						$.messager.alert("提示", result.message);
					}
    			}
            });
	}
	
    return {
        init : init
    };
    
});

	