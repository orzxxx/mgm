<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.centerm.base.Constant"%>
<%@ page language="java" import="com.centerm.model.sys.LoginUser"%>
<%@ page language="java" import="com.centerm.model.sys.UserInfo"%>
<%@ page language="java" import="com.centerm.model.sys.SysMenuInf"%>
<%@ page language="java" import="com.centerm.model.sys.RoleInf"%>
<%@ page language="java" import="com.centerm.model.sys.FunctionInf"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.google.gson.*"%>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	Gson gson = new Gson();
	LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
	if(loginUser == null){
		out.print("<script type='text/javascript'>window.location.href='"+path+"/error/nologin.jsp'</script>");
		return;
	}
	UserInfo userInfo = loginUser.getUserInfo();
	List<SysMenuInf> sysMenus = loginUser.getMenus();
	List<FunctionInf> functions = loginUser.getFunctions();
	List<RoleInf> roles = loginUser.getRoles();
%>
<!-- jquery库 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/default.css" type="text/css"></link>
<%--<link id="easyuiTheme" rel="stylesheet" href="${pageContext.request.contextPath}/themes/default/centermui.css" type="text/css"></link>
--%><script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui.validate-extends.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-lang-zh_CN.js" charset="utf-8"></script>

<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/ctUtil.js" charset="utf-8"></script>
--%><%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/datagridUtil.js" charset="utf-8"></script>
--%><link rel="stylesheet" href="${pageContext.request.contextPath}/css/basic.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script language="JavaScript" src="${pageContext.request.contextPath}/plugins/datepicker/WdatePicker.js" defer="defer"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/default/easyui.css">  
<script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tagsinput.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/city-picker.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/city-picker.data.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/city-picker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ct.css" type="text/css"></link>
<style>

</style>
<script language="javascript">
	var contextPath = "<%=path%>";
	var userInfo = <%=gson.toJson(userInfo)%>;
	var sysMenus = <%=gson.toJson(sysMenus)%>;
	var functions = <%=gson.toJson(functions)%>;
	var roles = <%=gson.toJson(roles)%>;
	
	var ajaxBack = $.ajax;
	$.ajax = function(setting) {
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
	
	$(function(){
		//回退按钮
		$(document).keydown(function(e) {  
			
        switch(e.which) {  
            case 8:   {
            	var target = e.target ;
                var tag = e.target.tagName.toUpperCase();
                if((tag == 'INPUT' && !$(target).attr("readonly"))||(tag == 'TEXTAREA' && !$(target).attr("readonly"))){
                     if((target.type.toUpperCase() == "RADIO") || (target.type.toUpperCase() == "CHECKBOX")){
                      return false ;
                     }else{
                      return true ; 
                     }
                    }else{
                    	$.messager.confirm('提示', '是否回到首页?', function(r){
            				if (r){
            					window.location.href="login.jsp";
            				}
            			});
                    	return false;
                 }
            	
           }  
                        break;  
            case 13:   {
            	return false;
                 }
            	
                        break;  
        }  
    });

		
		//按钮权限控制
		$.each($("button"), function(){
			var fid = $(this).attr("fid");	
			if (!isHasFunction(fid, functions)) {
				$(this).hide();
			}
		});
	});
	//是否拥有功能权限
	function isHasFunction(fid, functions){
		for ( var i in functions) {
			if (functions[i].funcId == fid) {
				return true;
			}
		}
		return false;
	}
	//是否是超级管理员
	function isSuperAdmin(){
		for ( var i in role) {
			if (role[i].roleId == "0") {
				return true;
			}
		}
		return false;
	}
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
			hideLoading();
			//$.messager.progress('close');
			$.messager.alert("错误", "连接超时,请重新登录");
		}catch(e){}
	}
});
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
