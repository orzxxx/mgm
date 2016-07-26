<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="zh-CN">
	<head>
		<base href="<%=basePath%>">
		<!-- jquery库 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/default.css" type="text/css"></link>
<%--<link id="easyuiTheme" rel="stylesheet" href="${pageContext.request.contextPath}/themes/default/centermui.css" type="text/css"></link>
--%><script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js" charset="utf-8"></script>
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
		<link rel="stylesheet" type="text/css" href="css/css.css"/>
		<LINK REL="SHORTCUT ICON" HREF="<%=request.getContextPath()%>/images/favicon.ico">
		<title>Q哥点餐管理系统</title>
	</head>
	<script language="javascript">
	
	var ajaxBack = $.ajax;
	$.ajax = function(setting) {
		//showLoading();
		//var s = setting.success;
		setting.success = function(){
			try {
				hideLoading();
				//$.messager.progress('close');
				if($.isFunction(s)){s.apply(setting.context,arguments);}
			}catch(e){
				console.log(e);
			}
		};
		return ajaxBack(setting);
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
	<script type="text/javascript">
	
	
	$(function(){
		//initLeftMenu();
		//tabClose();
		//tabCloseEven();
		
		$("#nav").load("/mgm/menu/combo_form.jsp", function(){
			return false;
		});
	});

	window.onload = cssInit;

	//初始化左侧
	function initLeftMenu() {
		$("#nav").accordion({animate:false});
		//按seq从小到大排序
		sysMenus.sort(function(a,b){
	        return a.menuSeq - b.menuSeq;
	        });

	    $.each(sysMenus, function(i, n) {
	    	//按seq从大到小排序
	    	n.subMenus.sort(function(a,b){
	            return a.menuSeq - b.menuSeq;
	            });
			var menulist ='';
			menulist +='<ul>';
	        $.each(n.subMenus, function(j, o) {
				menulist += '<li><div><a ref="'+o.menuId+'" rel="' + o.menuUrl + '" ><span>&nbsp;</span><span class="nav">' + o.menuNm + '</span></a></div></li> ';
	        })
			menulist += '</ul>';

			$('#nav').accordion('add', {
	            title: n.menuNm,
	            content: menulist
	        });

	    });
		$('#nav ul li a').click(function(){
			var tabTitle = $(this).children('.nav').text();

			var url = $(this).attr("rel");
			var menuid = $(this).attr("ref");
			//var icon = getIcon(menuid,icon);

			addTab(tabTitle,url);
			$('.easyui-accordion li div').removeClass("selected");
			$(this).parent().addClass("selected");
		}).hover(function(){
			$(this).parent().addClass("hover");
		},function(){
			$(this).parent().removeClass("hover");
		});

		//选中第一个
		var panels = $('#nav').accordion('panels');
		var t = panels[0].panel('options').title;
	    $('#nav').accordion('select', t);
	}
	//获取左侧导航的图标
	function getIcon(menuid){
		var icon = 'icon ';
		$.each(_menus.menus, function(i, n) {
			 $.each(n.menus, function(j, o) {
			 	if(o.menuid==menuid){
					icon += o.icon;
				}
			 })
		})

		return icon;
	}

	function addTab(subtitle,url){
		
		//如果当前窗口已经打开，要重新刷新一下
		$('#tabs').tabs('close',subtitle);	
		
		if(!$('#tabs').tabs('exists',subtitle)){
			$('#tabs').tabs('add',{
				title:subtitle,
				//context:createFrame(url),
				href:url,
				closable:true
			});
		}else{
			$('#tabs').tabs('select',subtitle);
			$('#mm-tabupdate').click();
		}
		tabClose();
	}

	function createFrame(url)
	{
		var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		return s;
	}

	function tabClose()
	{
		/*双击关闭TAB选项卡*/
		$(".tabs-inner").dblclick(function(){
			var subtitle = $(this).children(".tabs-closable").text();
			$('#tabs').tabs('close',subtitle);
		})
		/*为选项卡绑定右键*/
		$(".tabs-inner").bind('contextmenu',function(e){
			$('#mm').menu('show', {
				left: e.pageX,
				top: e.pageY
			});

			var subtitle =$(this).children(".tabs-closable").text();

			$('#mm').data("currtab",subtitle);
			$('#tabs').tabs('select',subtitle);
			return false;
		});
	}
	//绑定右键菜单事件
	function tabCloseEven()
	{
		//刷新
		$('#mm-tabupdate').click(function(){
			var currTab = $('#tabs').tabs('getSelected');
			var url = $(currTab.panel('options').content).attr('src');
			$('#tabs').tabs('update',{
				tab:currTab,
				options:{
					content:createFrame(url)
				}
			})
		})
		//关闭当前
		$('#mm-tabclose').click(function(){
			var currtab_title = $('#mm').data("currtab");
			$('#tabs').tabs('close',currtab_title);
		})
		//全部关闭
		$('#mm-tabcloseall').click(function(){
			$('.tabs-inner span').each(function(i,n){
				var t = $(n).text();
				$('#tabs').tabs('close',t);
			});
		});
		//关闭除当前之外的TAB
		$('#mm-tabcloseother').click(function(){
			$('#mm-tabcloseright').click();
			$('#mm-tabcloseleft').click();
		});
		//关闭当前右侧的TAB
		$('#mm-tabcloseright').click(function(){
			var nextall = $('.tabs-selected').nextAll();
			if(nextall.length==0){
				//msgShow('系统提示','后边没有啦~~','error');
				
				return false;
			}
			nextall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				$('#tabs').tabs('close',t);
			});
			return false;
		});
		//关闭当前左侧的TAB
		$('#mm-tabcloseleft').click(function(){
			var prevall = $('.tabs-selected').prevAll();
			if(prevall.length==0){
				
				return false;
			}
			prevall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				$('#tabs').tabs('close',t);
			});
			return false;
		});

		//退出
		$("#mm-exit").click(function(){
			$('#mm').menu('hide');
		})
	}

	//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
	function msgShow(title, msgString, msgType) {
		$.messager.alert(title, msgString, msgType);
	}

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


	function logout(){
		$.post("logout", function(result){
			if (result.code == 0) {
				window.location.href = "login.jsp";
			}else{
				$.messager.alert("提示", result.message);
			}
		},"json");
	}

	function doPwdReset(){
		var dlg = $('<div/>').dialog({    
		    title: '管理员密码修改',    
		    width: 500,    
		    height: 200,    
		    closable: false,    
		    cache: false,    
		    href: 'sys/pwd_reset.jsp',    
		    modal: true,
		    buttons : [ {
				text : '保存',
				handler : function(){
				
		    	if($('#pwd_form').form("validate")){
		    		var param = $("#pwd_form").serializeObject();
		    		$.post("mchnt/mchnt/resetPwd",param,function(result){
		    			if (result.code == 0) {
		    				$.messager.alert("提示", "修改成功!");
	                		//关闭对话框
	                		dlg.dialog('close');
	    	        		dlg.remove();
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
				$("#pwd_userId").val(userInfo.userId);
			}
		}); 
	}


	function cssInit(){
		//图片居中
		var pWidth = $("#mainPanle").width();
		var pHeight = $("#mainPanle").height();
		var imgWidth = $("#welcomeLogo").width();
		var imgHeight = $("#welcomeLogo").height();
		var pTop = $("#mainPanle").offset().top;
		var pLeft = $("#mainPanle").offset().left;
		//$("welcomeLogo").offset({top:(pWidth-imgWidth)/2,left:(pHeight-imgHeight)/2});
		//$("#welcomeLogo").css("margin-left", (pWidth*1-imgWidth*1)/2);
		//$("#welcomeLogo").css("margin-top", (pHeight*1-imgHeight*1)/2);
		$("#welcomeLogo").offset({left:(pWidth*1-imgWidth*1)/2+pLeft, top:(pHeight*1-imgHeight*1)/2+pTop});
	}
	$(function(){
		$("#nav").load("/mgm/menu/combo_form.jsp");
	})
	</script>
	<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
		<noscript>
			<div style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
				<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
			</div>
		</noscript>
	
		
		<div region="north"  class="top">
			<div class="logo"><img src="images/logo_pt.png" alt="Q哥点餐后台管理系统" /></div>
		    <div class="top_button">
		    	<div class="modify_button"><a href="javascript:void(0)" onclick="doPwdReset()">修改密码</a></div><div class="exit_button" ><a href="javascript:void(0)" onclick="logout()" id="loginOut">安全退出</a></div>
		    </div>
		    <div class="welcome"><span style="color:#ffffff;">尊敬的 <span id="main_userName">${sessionScope.loginUser.userInfo.userName}</span>，欢迎来到Q哥点餐管理系统！</span></div>
		</div>
		
		
		
		<div region="west" hide="true" border="13px" title="导航菜单" style="width: 180px;color: #fff;" id="west">
			<div id="nav" class="easyui-accordion" fit="true" border="false">
				<!--  导航内容 -->

			</div>

		</div>
		
		
		<div id="mainPanle" region="center" style="overflow-y: hidden;border-top: 0px">
			<div id="tabs" class="easyui-tabs" fit="true" border="false">
				<div title="欢迎使用" style="overflow: hidden;text-align:center;">
					<img src="images/welcome_bg.jpg" height="100%" width="100%" border="1">
					<img id="welcomeLogo" src="images/welcome_logo.png" border="1" style="position:absolute;z-index: 999;vertical-align: middle;width:50%;">
				</div>
			</div>
		</div>
		<div id="passwordDialog" title="修改密码" style="display: none;width: 350px;" >
			<form method="post" class="form" >
				<table cellpadding="3" >
					<tr>
						<td>
							原密码：
						</td>
						<td>
							<input id="oldPass" name="oldPass" class="easyui-validatebox" type="Password"  data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>
							新密码：
						</td>
						<td>
							<input id="newpass" name="newpass" class="easyui-validatebox"  maxlength="16" validType="custom_reg['^(?=.*[0-9].*)(?=.*[A-Za-z].*).{6,16}$','用户密码长度为6-16个字符，且必须包含数字和字母']"  data-options="required:true" type="Password"  />
						</td>
					</tr>
					<tr>
						<td>
							确认密码：
						</td>
						<td>
							<input id="repnewpass" name="repnewpass" class="easyui-validatebox" maxlength="16" validType="custom_reg['^(?=.*[0-9].*)(?=.*[A-Za-z].*).{6,16}$','用户密码长度为6-16个字符，且必须包含数字和字母']"  data-options="required:true" type="Password"  />
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		
		<div id="mm" class="easyui-menu" style="width:150px;display: none;">
			<div id="mm-tabclose">关闭</div>
			<div id="mm-tabcloseall">全部关闭</div>
			<div id="mm-tabcloseother">除此之外全部关闭</div>
			<div class="menu-sep"></div>
			<div id="mm-tabcloseright">当前页右侧全部关闭</div>
			<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		</div>
		<div id='loading' style="position:relative;"></div>
	</body>
</html>
