<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>Q哥点餐后台管理系统</title>
    <%--<link id="easyuiTheme" rel="stylesheet" href="${pageContext.request.contextPath}/themes/default/centermui.css" type="text/css"></link>
	--%><link rel="stylesheet" href="${pageContext.request.contextPath}/themes/icon.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/ctUtil.js" charset="utf-8"></script>--%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/basic.css" type="text/css"></link>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/default/easyui.css">  
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui.validate-extends.js" charset="utf-8"></script>
    <link href="css/login.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/city-picker.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/city-picker.data.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/city-picker.js"></script>
<script type="text/javascript">
	$(function(){
		//var minWidth = window.screen.width;
		var minWidth = 1200;
		$(".footer,.wrapper,.header").css("min-width", minWidth);
		
		$('input:first').focus();//直接定位到当前页面的第一个文本框
	    var $inp = $('input');//所有文本框
	    $(document).bind('keydown', function (e) {
	        var key = e.which;
	        if (key == 13) {
	            doLogin();
	        }
	    });
	});
	
	function doLogin() {
		
		var $userId = $('#userId');
        var $passwd = $('#passwd');
        
        if ($userId.val() == '') {
            $.messager.alert('系统提示', '请输入用户名！', 'warning');
            return false;
        }
        
        if ($passwd.val() == '') {
            $.messager.alert('系统提示', '请输入密码！', 'warning');
            return false;
        }
		
		
		$('#loginForm').ajaxSubmit( {
			dataType : "json",
            success : function(result) {
            	if (result.code == 0) {
            		window.location.href="<%=path%>/main.jsp";
				} else {
					$.messager.progress('close');
					$.messager.show({
						title : '系统提示',
						msg : result.message
					});
				}
			}
        })
	}
//	
function setValidType(type){
	//更改校验方式
	$('#register_xxx').validatebox({    
	    validType: type   
	});  
	//校验当前数据
	$('#register_xxx').validatebox('validate'); 
}

function doRegister() {
		var dlg = $('<div/>').dialog({    
		    title: '注册表单',    
		    width: 500,    
		    height: 600,    
		    closable: false,    
		    cache: false,    
		    href: 'sys/register.jsp',    
		    modal: true,
		    buttons : [ {
				text : '注册',
				handler :function(){
		    	if($('#register_form').form("validate")){
		    		var param = $("#register_form").serializeObject();
		    		$.post("register",param,function(result){
		    			if (result.code == 0) {
		    				$.messager.alert("提示", "注册成功, 即将自动登入系统");
		    				setTimeout(function(){
		    					window.location.href="<%=path%>/main.jsp";
		    				}, 3000);
	    				} else {
	    					$.messager.alert("提示", result.message);
	    				}
					},"json");
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
				$('#register_district').citypicker({
					responsive:true
				});
			}
		});  
	}
$.fn.serializeObject = function()    
{    
   var o = {};    
   var a = this.serializeArray();    
   $.each(a, function() {    
       if (o[this.name]) {    
           if (!o[this.name].push) {    
               o[this.name] = [o[this.name]];    
           }    
           o[this.name].push(this.value || '');    
       } else {    
           o[this.name] = this.value || '';    
       }    
   });    
   return o;    
}; 
$.ajaxSetup({
	type : 'POST',
	complete: function (xhr, status) {
		try {
			var data = $.parseJSON(xhr.responseText);
			if(data.loginStatus == false) {
				top.location.href = data.redirectUrl;
				return;
			}
		} catch(e){}
	},error : function(XMLHttpRequest, textStatus, errorThrown) {
		try{
			//$.messager.progress('close');
			hideLoading();
			$.messager.alert("错误", "连接超时,请等待一段时间后重试");
		}catch(e){}
	}
});
var ajaxBack = $.ajax;
	$.ajax = function(setting) {
		/*$.messager.progress({ 
			title:'请稍后', 
			msg:'正在处理...'
			});*/
			showLoading();
		var s = setting.success;
		setting.success = function(){
			try {
				hideLoading();
				//$.messager.progress('close');
				if($.isFunction(s)){s.apply(setting.context,arguments);}
			}catch(e){
				
			}
		};
		ajaxBack(setting);
	};
function showLoading(){
	$('#loading').dialog({    
		title: '正在处理...',
	    width: 100,    
	    height: 110,    
	    closable: false,    
	    cache: false,    
	    border:false,
	    shadow:false,
	    content: "<img width='64px' height='64px'"+ 
	    	"style='position:absolute;"+
	    	"left:0; right:0; top:0; bottom:0;"+
	    	"margin: auto;' src='images/loading.gif'/>",    
	    modal: true
	});
	$("#loading").prev("div").hide();
	$("#loading").parent("div").css("padding", "0px");
}

function hideLoading(){
	$('#loading').dialog('close');
}
</script>
</head>

<body>
	<div class="header"><div class="logo"></div></div>
	<div class="wrapper">
		
        <div class="login">
        <%--<div style="padding-top: 50px;position:absolute;color: white;font-size: 35px;"></div>--%>
        	<form name="loginForm" id="loginForm" method="post" action="<%=path%>/login">
            <ul style="">
                <li class="title"></li>
                <li><input type="text" name="userId" id="userId" class="textbox-user" maxlength="11"></li>
                <li><input type="password" name="passwd" id="passwd" class="textbox-password"></li>
                <%--<li style="padding-bottom: 1px;"><a href="javascript:void(0);" onclick="doLogin()" class="btn">登录</a></li>
                <br/> 
                --%><li style="padding-top: 10px;">
                <a href="javascript:void(0);" style="margin-bottom: 20px;" onclick="doLogin()" class="btn">登录</a><br/>
                <a href="javascript:void(0);" onclick="doRegister()" class="btn">免费注册</a></li>
                <li class="clearfix">
                </li>
            </ul>
            <div class="code">
				<img src="images/a_code.png"/>
				<br/>
				<span>安卓应用扫码下载</span>
			</div>
            </form>
    	</div>
    </div>
    <div id='loading' style="position:relative;"></div>
     <%--<div class="footer">Copyright © 2002-2015 Centerm Co., Ltd. All Rights Reserved. 闽ICP备12007612号</div>
--%></body>
</html>

