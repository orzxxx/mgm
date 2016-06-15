
/***********************************
 * 文件上传插件封装
 * var opts = {
 * 		id:'',	//input的ID
 * 		auto:'', //是否自动上传，可选true、false
 * 		multi:'', //是否支持一次选择多个文件，可选true,false
 * 		buttonClass : '', //上传按钮的样式
 * 		buttonText : '', //上传按钮上显示的文字,
 * 		buttonImage : null, //上传按钮的图片
 * 		fileTypeDesc : '图片文件', //可上传文件的描述
 * 		fileTypeExts:'', //支持上传的文件扩展名如*.img;*.png;*.gif等
 * 		swf:'', //uploadify.swf文件的路径
 * 		uploader:'', //后台处理地址
 * 		fileSizeLimit:'', //文件大小限制，单位B, KB, MB, or GB，设置为0表示不限制
 * 		uploadSuccess:function(file, data, response) {
 * 			file:上传文件的信息，具体可在chrome下使用console.info(file)查看
 * 			data:后台返回的信息，具体可在chrome下使用console.info(data)查看
 * 			response:
 * 		}, //文件上传成功后执行此方法
 * 		uploadError:function(file, errorCode, errorMsg,errorString) {}, //文件上传失败后执行此方法
 * 		uploadComplete:function(file) {}, //文件上传完成后执行此方法，无论上传成功还是上次失败，都会执行该方法
 * 		cancel:function(file) {} //点击取消按钮后触发该方法
 * }
 * 参考：http://www.uploadify.com/documentation/
 * 使用方法：$(function(){new CUploadify(opts);});
 * 提供两个方法：
 * 1、getAllFileLength() //获取所有上传的文件个数，包括已上传和未上传
 * 2、getFileLength() //获取待上传的文件个数
 * 
 * caijie@centerm.com
 * 2015-10-22 17:45:05
 ***********************************/
function CUploadify(opts){
	
	this.upload_default_settings = {
			'id' :'',
			'auto' : true,//是否自动上传
			'multi' : true, //false则在选择文件的时候一次只能选择一个
			'height' : 30,
			'buttonClass' : '',
			'buttonText' : '选择文件',
			'buttonImage' : null,
			'buttonCursor' : 'hand',
			'swf' : '/uploadify/uploadify.swf',
			'uploader' : '/FileUploadServlet',
			'width' : 120,
			'fileTypeDesc' : 'All Files',
			'fileTypeExts' : '*.*', //可上传文件的扩展名
			'fileSizeLimit' : '0',//单位B, KB, MB, or GB，设置为0表示不限制
			'removeCompleted' : false, //上传完成后是否移除
			'successTimeout' : 9999,
			'removeTimeout':0,
			'onClearQueue' : function(queueItemCount) {
	            alert(queueItemCount + ' file(s) were removed from the queue');
	        }
	};
	
	this.onReady(opts);
	$("#" + opts.id).uploadify(this.upload_default_settings);
}

CUploadify.prototype.setSettingPara = function(a, b) {
	for(var c in this.upload_default_settings) {
		if(c == a) {
			this.upload_default_settings[c] = b;
			break;
		}
	}
};

/**
 * 获取所有上传的文件个数，包括已上传和未上传
 * @returns {Number}
 */
CUploadify.prototype.getAllFileLength = function() {
	var files = $("#" + this.upload_default_settings.id).data('uploadify').queueData.files;
	var length = 0;
	if(undefined != files) {
		for(var f in files) {
			length ++;
		}
	}
	return length;
};

/**
 * 获取待上传的文件个数
 * @returns
 */
CUploadify.prototype.getFileLength = function() {
	var length = $("#" + this.upload_default_settings.id).data('uploadify').queueData.queueLength;
	return length;
};

/**
 * 清楚队列中所有的文件
 */
CUploadify.prototype.cleanQueueData = function() {
	var _id = this.upload_default_settings.id;
	//console.info(_id);
	//console.info($("#" + _id).data('uploadify').queueData);
	//delete $("#" + opts.id).data('uploadify').queueData.files[id];
	
};

CUploadify.prototype.onReady = function(opts) {
	
	var self = this;
	
	if(undefined != opts) {
		for(var key in opts) {
			if(typeof(opts[key]) != 'function') {
				this.setSettingPara(key, opts[key]);
			}
		}
	}
	this.upload_default_settings.onUploadSuccess = function(file, data, response) {
		var id = file.id;
    	data = $.parseJSON(data);
    	if(data.success == false) {
    		var message = " - <font color='red'>"+data.message+"</font>";
    		$("#"+id).children('.data').html(message);
    		$("#"+id).find('.uploadify-progress .uploadify-progress-bar').css('width','0%');
    	}
    	if(typeof(opts['uploadSuccess']) == 'function') {
    		opts['uploadSuccess'](file, data, response);
    	}
	};
	
	this.upload_default_settings.onUploadError = function(file, errorCode, errorMsg,errorString) {
		if(typeof(opts['uploadError']) == 'function') {
    		opts['uploadError'](file, errorCode, errorMsg,errorString);
    	}
	};
	
	this.upload_default_settings.onUploadComplete = function(file) {
		if(typeof(opts['uploadComplete']) == 'function') {
			opts['uploadComplete'](file);
		}
		var _id = self.upload_default_settings.id;
		var id = file.id;
		//上传完成后绑定取消按钮事件
		$("#"+_id+"-queue #" + id + " .cancel a").on("click", function() {
			//从上传文件队列中清除
			delete $("#" + opts.id).data('uploadify').queueData.files[id];
			if(typeof(opts['cancel']) == 'function') {
	    		opts['cancel'](file);
	    	}
		});
	};
	
	this.upload_default_settings.onCancel = function(file) {
		if(typeof(opts['cancel']) == 'function') {
    		opts['cancel'](file);
    	}
	};
};
