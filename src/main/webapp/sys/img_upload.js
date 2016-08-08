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
	var pictureAddr = "";
	var g_ratio = 4/3;
	
	function init(ratio){
		if (ratio != null && ratio != "") {
			g_ratio = ratio;
		}
		//initForm();
		initButton();
	}
	//清空数据
	function dataClear(){
		pictureLink = "";
		pictureAddr = "";
	}
	
	function getData(){
		if ($("#img_select").val() == "") {
			$.messager.alert("提示", "请先上传图片!");
			return;
		}
		if ($("#img_select").val() != "" && !/^.+\.(gif|jpg|jpeg|png)$/.test($("#img_select").val())) {
			$.messager.alert("提示", "非法的图片格式");
			return;
		}
		var cropperData = $('#img_picture').cropper('getData');
		var param = cropperData;
		param.mchntCd = userInfo.mchntCd;
		param.path = picturePath;
		
		return param;
	}
	
	function setData(data){
		//imgShow();
		//$("#img_tips").hide();
	}
	
	function initForm(){
		/*$('#img_picture').cropper({
			  aspectRatio: g_ratio,
			  viewMode: 1,
			  checkCrossOrigin: false,
			  crop: function(e) {
				  
			  }
			});*/
		
		var $previews = $('.preview');

	      $('#img_picture').cropper({
	    	  aspectRatio: g_ratio,
			  viewMode: 1,
			  checkCrossOrigin: false,
	          build: function (e) {
	            var $clone = $(this).clone();

	            $clone.css({
	              display: 'block',
	              width: '200px',
	              minWidth: 0,
	              minHeight: 0,
	              maxWidth: 'none',
	              maxHeight: 'none'
	            });

	            $clone.attr("id", "img_preview");
	            
	            $previews.css({
	              width: '200px',
	              overflow: 'hidden'
	            }).html($clone);
	          },

	          crop: function (e) {
	            var imageData = $(this).cropper('getImageData');
	            var previewAspectRatio = e.width / e.height;

	            $previews.each(function () {
	              var $preview = $(this);
	              var previewWidth = $preview.width();
	              var previewHeight = previewWidth / previewAspectRatio;
	              var imageScaledRatio = e.width / previewWidth;

	              $preview.height(previewHeight).find('img').css({
	                width: imageData.naturalWidth / imageScaledRatio,
	                height: imageData.naturalHeight / imageScaledRatio,
	                marginLeft: -e.x / imageScaledRatio,
	                marginTop: -e.y / imageScaledRatio
	              });
	            });
	            
	            $previews.width(200).hide();
			     $('.cropper-container').hide();
	          }
	        });
	      
	}
	
	function initButton(){
		$("#img_select").change(select);
		//$("#img_upload").click(upload);
	}
	
	/*function upload(){
		var cropperData = $('#img_picture').cropper('getData');
		var param = cropperData;
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
            		$('#img_picture').cropper('reset').cropper('replace', result.data.pictureAddr);
					$('#img_picture').cropper('reset').cropper('replace', result.data.pictureAddr);
					//pictureLink = result.data.pictureAddr;
					//pictureAddr = result.data.pictureAddr;
				} else {
					//待处理$("#menu_picture").val("");
					$.messager.alert("提示", result.message);
				}
			}
        });
	}*/
	
	function imgHide(){
		$('#img_preview').hide().removeClass("cropper-hidden");
		$('#img_picture').cropper('destroy').hide();
		$("#img_tips").show();
	}
	
	function imgShow(){
		var $previews = $('.preview');

	      $('#img_picture').cropper({
	    	  aspectRatio: g_ratio,
			  viewMode: 1,
			  checkCrossOrigin: false,
	          build: function (e) {
	            var $clone = $(this).clone();

	            $clone.css({
	              display: 'block',
	              width: '200px',
	              minWidth: 0,
	              minHeight: 0,
	              maxWidth: 'none',
	              maxHeight: 'none'
	            });

	            $clone.attr("id", "img_preview");
	            
	            $previews.css({
	              width: '200px',
	              overflow: 'hidden'
	            }).html($clone);
	          },

	          crop: function (e) {
	            var imageData = $(this).cropper('getImageData');
	            var previewAspectRatio = e.width / e.height;

	            $previews.each(function () {
	              var $preview = $(this);
	              var previewWidth = $preview.width();
	              var previewHeight = previewWidth / previewAspectRatio;
	              var imageScaledRatio = e.width / previewWidth;

	              $preview.height(previewHeight).find('img').css({
	                width: imageData.naturalWidth / imageScaledRatio,
	                height: imageData.naturalHeight / imageScaledRatio,
	                marginLeft: -e.x / imageScaledRatio,
	                marginTop: -e.y / imageScaledRatio
	              });
	            });
	            
	            $previews.width(200).removeClass('cropper-hidden');
	          }
	        });
	}
	
	function select(){
		if ($("#img_select").val() == "") {
			imgHide();
			return;
		}
		if (!/^.+\.(gif|jpg|jpeg|png)$/.test($("#img_select").val())) {
			$.messager.alert("提示", "非法的图片格式");
			imgHide();
			return;
		}
			$('#img_form').ajaxSubmit( {
    			url : "menu/menu/uploadTemp",
    			dataType : "json",
                success : function(result) {
					if (result.code == 0) {
						dataClear();
	            		//
						picturePath = result.data.picturePath;
						$("#img_tips").hide();
						imgShow();
						$('#img_picture').show().cropper('reset').cropper('replace', result.data.pictureAddr);
						$('#img_preview').width(200).removeClass('cropper-hidden');
					} else {
						$.messager.alert("提示", result.message);
					}
    			}
            });
	}
	
    return {
        init : init,
        getData: getData,
        setData: setData
    };
    
});

	