<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<jsp:include flush="true" page="include/inc.jsp"></jsp:include>
		<link rel="stylesheet" type="text/css" href="css/css.css"/>
		<LINK REL="SHORTCUT ICON" HREF="<%=request.getContextPath()%>/images/favicon.ico">
		<script type="text/javascript" src="${pageContext.request.contextPath}/main.js" charset="utf-8"></script>
		<script data-main="js/config" src="js/require.js"></script>
		<title>Q哥点餐管理系统</title>
	</head>
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
					<img src="images/welcome_bg.jpg" height="100%"  border="1">
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
	</body>
</html>
