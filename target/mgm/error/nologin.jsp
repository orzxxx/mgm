<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" type="text/css" media="screen" />
<title>登陆超时</title>

</head>
<body id="content">
     <div class="space"></div>
    <div class="task" id="rightsider">
      <fieldset>
        <legend>登陆超时</legend>
        <br/>
        <p><img src="${pageContext.request.contextPath}/images/err.gif" alt="错误图标" /><span class="fontred">您还没有登录，或登录超时，请重新登录。</span></p>
        <%--
        <p>ԭ原因码:<span>401</span></p>
        --%>
        <%--<div align="center"><a href="../login.do" style="color:red">��¼</a></div>
         <div class="space"></div> 
       --%>
       <p><br/>
	       <span class="btn">
	       	<span class="btn_left"></span>
	       	<span class="task">
	       		<a class="fontred" href="${pageContext.request.contextPath}/login.jsp" target="_top">登录</a>
	       	</span>
	       <span class="btn_right"></span>
	       </span>
       </p> 
      </fieldset>
      
    <div class="clearall"></div>
     </div>
</body>

</html>




