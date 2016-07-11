/**
 * easyui表单验证扩展验证规则
 */
$.extend($.fn.validatebox.defaults.rules, {
    equals: {
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: 'Field do not match.'
    },
    //整数
    integer : {
		validator : function(value, param) {
			var str = /^[0-9]*$/;
			return str.test(value);  
		},
		message : '请输入整数'
	},
    //验证手机号
    mobile: {
        validator: function (value, param) {
            return /^1[3|4|5|7|8]\d{9}$/.test(value);
        },
        message: '手机号码不正确'
    },
    //图片文件
    image: {
        validator: function (value, param) {
    		value = value.toLowerCase();
            return /^.+\.(gif|jpg|jpeg|png)$/.test(value);
        },
        message: '非法的图片格式'
    },
    //密码
    pwd: {
        validator: function (value, param) {
            //return /^\w{6,20}$/.test(value);
        	return /^.{6,20}$/.test(value);
        },
        message: '密码格式不正确'
    },
    pwdEquals: {
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: '密码输入前后不一致'
    },
    idCard: {
        validator: function(value,param){
    	 	return /(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(value);
        },
        message: '身份证格式不正确'
    },
    email: {
            validator: function (value, param) {
                return /^[a-zA-Z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)+$/.test(value);
            },
            message: '邮箱格式不正确'
    },
  //n位数字
    numbertwo: {
        validator: function (value, param) {
            return /^\d{2}$/.test(value);
        },
        message: '请输入2位数字'
    },
    	realLength: {
        	validator: function (value, param) {
    		var length = param[0];
        	return value.replace(/[^\x00-\xff]/g,"aa").length <= length;
        },
        message: '长度不得超过{0}个字符'
    },
	taxCard: {
    	validator: function (value, param) {
    		var length = value.replace(/[^\x00-\xff]/g,"aa").length;
    		return (length == 15 || length == 18 || length == 20);
    	},
    	message: '税务登记证格式不正确'
	},
	license: {
    	validator: function (value, param) {
    		return value.replace(/[^\x00-\xff]/g,"aa").length == 15;
	    },
    	message: '营业执照格式不正确'
	},
	orgCode: {
    	validator: function (value, param) {
    		return value.replace(/[^\x00-\xff]/g,"aa").length == 10;
    	},
    	message: '组织机构代码证格式不正确'
	}
});