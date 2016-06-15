/**
 * 包含easyui的扩展和常用的方法
 * 
 */

var ct = $.extend({}, ct);/* 定义全局对象，类似于命名空间或包的作用 */

/**
 * 使panel和datagrid在加载时提示
 * 
 * @author 
 * 
 * @requires jQuery,EasyUI
 * 
 */
$.fn.panel.defaults.loadingMessage = '加载中....';
$.fn.datagrid.defaults.loadMsg = '加载中....';

/**
 * @author 
 * 
 * @requires jQuery,EasyUI
 * 
 * 避免验证tip屏幕跑偏
 */
var removeEasyuiTipFunction = function() {
	window.setTimeout(function() {
		$('div.validatebox-tip').remove();
	}, 0);
};
$.fn.panel.defaults.onClose = removeEasyuiTipFunction;
$.fn.window.defaults.onClose = removeEasyuiTipFunction;
$.fn.dialog.defaults.onClose = removeEasyuiTipFunction;

/**
 * @author 
 * 
 * @requires jQuery,EasyUI
 * 
 * 通用错误提示
 * 
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 */
var easyuiErrorFunction = function(XMLHttpRequest) {
	if (parent.$ && parent.$.messager) {
		parent.$.messager.progress('close');
		parent.$.messager.alert('错误', XMLHttpRequest.responseText);
	} else {
		$.messager.progress('close');
		$.messager.alert('错误', XMLHttpRequest.responseText);
	}
};
$.fn.form.defaults.onLoadError = easyuiErrorFunction;
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.error = easyuiErrorFunction;

/**
 * @author 
 * 
 * @requires jQuery,EasyUI
 * 
 * panel关闭时回收内存
 */
$.fn.panel.defaults.onBeforeDestroy = function() {
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			for ( var i = 0; i < frame.length; i++) {
				frame[i].contentWindow.document.write('');
				frame[i].contentWindow.close();
			}
			frame.remove();
			if ($.browser.msie) {
				CollectGarbage();
			}
		}
	} catch (e) {
	}
};






/**
 * @author 
 * 
 * @requires jQuery,EasyUI
 * 
 * @param options
 */
ct.dialog = function(options) {
	var opts = $.extend({
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);
	return $('<div/>').dialog(opts);
};

/**
 * @author 
 * 
 * @requires jQuery,EasyUI
 * 
 * @param title
 *            标题
 * 
 * @param msg
 *            提示信息
 * 
 * @param fun
 *            回调方法
 */
ct.confirm = function(title, msg, fn) {
	return $.messager.confirm(title, msg, fn);
};

/**
 * @author 
 * 
 * @requires jQuery,EasyUI
 */
ct.messagerShow = function(options) {
	return $.messager.show(options);
};

/**
 * @author 
 * 
 * @requires jQuery,EasyUI
 */
ct.messagerAlert = function(title, msg, icon, fn) {
	return $.messager.alert(title, msg, icon, fn);
};

/**
 * @author 
 * 
 * @requires jQuery,EasyUI,jQuery cookie plugin
 * 
 * 更换EasyUI主题的方法
 * 
 * @param themeName
 *            主题名称
 */
ct.changeTheme = function(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for ( var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			$(ifr).contents().find('#easyuiTheme').attr('href', href);
		}
	}

	$.cookie('easyuiThemeName', themeName, {
		expires : 7
	});
};

/**
 * @author 
 * 
 * 获得项目根路径
 * 
 * 使用方法：ct.bp();
 * 
 * @returns 项目根路径
 */
ct.bp = function() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
};

/**
 * @author 
 * 
 * 使用方法:ct.pn();
 * 
 * @returns 项目名称(/sshe)
 */
ct.pn = function() {
	return window.document.location.pathname.substring(0, window.document.location.pathname.indexOf('\/', 1));
};

/**
 * @author 
 * 
 * 增加formatString功能
 * 
 * 使用方法：ct.fs('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * 
 * @returns 格式化后的字符串
 */
ct.fs = function(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * @author 
 * 
 * 增加命名空间功能
 * 
 * 使用方法：ct.ns('jQuery.bbb.ccc','jQuery.eee.fff');
 */
ct.ns = function() {
	var o = {}, d;
	for ( var i = 0; i < arguments.length; i++) {
		d = arguments[i].split(".");
		o = window[d[0]] = window[d[0]] || {};
		for ( var k = 0; k < d.slice(1).length; k++) {
			o = o[d[k + 1]] = o[d[k + 1]] || {};
		}
	}
	return o;
};

/**
 * @author 郭华(夏悸)
 * 
 * 生成UUID
 * 
 * @returns UUID字符串
 */
ct.random4 = function() {
	return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
};
ct.UUID = function() {
	return (ct.random4() + ct.random4() + "-" + ct.random4() + "-" + ct.random4() + "-" + ct.random4() + "-" + ct.random4() + ct.random4() + ct.random4());
};

/**
 * @author 
 * 
 * 获得URL参数
 * 
 * @returns 对应名称的值
 */
ct.getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};

/**
 * @author 
 * 
 * 接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
 * 
 * @returns list
 */
ct.getList = function(value) {
	if (value != undefined && value != '') {
		var values = [];
		var t = value.split(',');
		for ( var i = 0; i < t.length; i++) {
			if(t[i]!=''){
				values.push(''+ $.trim(t[i]));/* 避免他将ID当成数字 */
			}
		}
		return values;
	} else {
		return [];
	}
};

/**
 * @author 
 * 
 * @requires jQuery
 * 
 * 判断浏览器是否是IE并且版本小于8
 * 
 * @returns true/false
 */
ct.isLessThanIe8 = function() {
	return ($.browser.msie && $.browser.version < 8);
};

/**
 * @author 
 * 
 * @requires jQuery
 * 
 * 将form表单元素的值序列化成对象
 * 
 * @returns object
 */
ct.serializeObject = function(form) {
	
	var o = {};
	$.each(form.serializeArray(), function(index) {
		
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

/**
 * 
 * 将JSON对象转换成字符串
 * 
 * @param o
 * @returns string
 */
ct.jsonToString = function(o) {
	var r = [];
	if (typeof o == "string")
		return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
	if (typeof o == "object") {
		if (!o.sort) {
			for ( var i in o)
				r.push(i + ":" + obj2str(o[i]));
			if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
				r.push("toString:" + o.toString.toString());
			}
			r = "{" + r.join() + "}";
		} else {
			for ( var i = 0; i < o.length; i++)
				r.push(obj2str(o[i]));
			r = "[" + r.join() + "]";
		}
		return r;
	}
	return o.toString();
};

/**
 * @author 郭华(夏悸)
 * 
 * 格式化日期时间
 * 
 * @param format
 * @returns
 */
Date.prototype.format = function(format) {
	if (isNaN(this.getMonth())) {
		return '';
	}
	if (!format) {
		format = "yyyy-MM-dd hh:mm:ss";
	}
	var o = {
		/* month */
		"M+" : this.getMonth() + 1,
		/* day */
		"d+" : this.getDate(),
		/* hour */
		"h+" : this.getHours(),
		/* minute */
		"m+" : this.getMinutes(),
		/* second */
		"s+" : this.getSeconds(),
		/* quarter */
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		/* millisecond */
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

/**
 * @author 
 * 
 * @requires jQuery
 * 
 * 改变jQuery的AJAX默认属性和方法
 */
$.ajaxSetup({
	type : 'POST',
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		try{
			if (parent.$ && parent.$.messager) {
				parent.$.messager.progress('close');
				parent.$.messager.alert('错误', XMLHttpRequest.responseText);
			} else {
				$.messager.progress('close');
				$.messager.alert('错误', XMLHttpRequest.responseText);
			}
		}catch(e){}
	},
	complete: function (xhr, status) {
		try {
			var data = $.parseJSON(xhr.responseText);
			if(data.loginStatus == false) {
				top.location.href = data.redirectUrl;
			}
		} catch(e){}
	}
});

var ajaxBack = $.ajax;
$.ajax = function(setting) {
	var s = setting.success;
	setting.success = function(){
		try {
			if($.isFunction(s)){s.apply(setting.context,arguments);}
		}catch(e){
			
		}
	};
	ajaxBack(setting);
};

/**
 * @author 
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展validatebox，添加验证两次密码功能
 */
$.extend($.fn.validatebox.defaults.rules, {
	eqPassword : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});

$.extend($.fn.validatebox.defaults.rules, {
	selectValueRequired : {
		validator : function(value, param) {
			return $(param[0]).find("option:contains('"+value+"')").val() != '';  
		},
		message : '该输入项为必输项'
	}
});



//数字和字母 
$.extend($.fn.validatebox.defaults.rules, {
	isNumAndZimu : {
		validator : function(value, param) {
			var str = /^[a-zA-Z0-9]*$/;
			return str.test(value);  
		},
		message : '请输入数字和字母'
	}
});

//数字 
$.extend($.fn.validatebox.defaults.rules, {
	isNum : {
		validator : function(value, param) {
			var str = /^[0-9]*$/;
			return str.test(value);  
		},
		message : '请输入数字'
	}
});

//数字和字母和中文
$.extend($.fn.validatebox.defaults.rules, {
	isNumAndZimuAndChs : {
		validator : function(value, param) {
			var str = /^[A-Za-z0-9_\u4e00-\u9fa5]*$/;
			return str.test(value);  
		},
		message : '请输入合法数据'
	}
});

//数字和字母 
$.extend($.fn.validatebox.defaults.rules, {
	isNumAndAToF : {
		validator : function(value, param) {
			var str = /^[A-F0-9]*$/;
			return str.test(value);  
		},
		message : '请输入数字和字母(A-F)'
	}
});
//IP地址
$.extend($.fn.validatebox.defaults.rules, {
	isIp : {
		validator : function(value, param) {
			var str =/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
			return str.test(value);  
		},
		message : '请输入合法IP地址'
	}
});

//端口号
$.extend($.fn.validatebox.defaults.rules, {
	isPort : {
		validator : function(value, param) {
			 var is_port = false;
			if(0<=value && 65536>value)
			    is_port = true;
			return is_port;  
		},
		message : '请输入合法端口号'
	}
});

window.onload=function(){  
   document.getElementsByTagName("body")[0].onkeydown =function(){  
          
    //获取事件对象  
    var elem = event.relatedTarget || event.srcElement || event.target ||event.currentTarget;   
    
    if(event.keyCode==8){//判断按键为backSpace键  
      
            //获取按键按下时光标做指向的element  
            var elem = event.srcElement || event.currentTarget;   
              
            //判断是否需要阻止按下键盘的事件默认传递  
            var name = elem.nodeName;  
              
            if(name!='INPUT' && name!='TEXTAREA'){  
                return _stopIt(event);  
            }  
            var type_e = elem.type.toUpperCase();  
            if(name=='INPUT' && (type_e!='TEXT' && type_e!='TEXTAREA' && type_e!='PASSWORD' && type_e!='FILE')){  
                    return _stopIt(event);  
            }  
            if(name=='INPUT' && (elem.readOnly==true || elem.disabled ==true)){  
                    return _stopIt(event);  
            }  
        }  
    }  
    
    $("input[name='listSelectRadio']").bind("onclick",function(){
    	alert(11);
    	
    });
}  
function _stopIt(e){  
        if(e.returnValue){  
            e.returnValue = false ;  
        }  
        if(e.preventDefault ){  
            e.preventDefault();  
        }                 
  
        return false;  
} 





