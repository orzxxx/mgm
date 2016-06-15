<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css" media="screen" />
<title>操作成功</title>

</head>
<body id="content">
<div id="breadnav">
          <ul>
             <li>系统提示</li>
             <li> &gt; </li>
             <li class="current">系统提示</li>
          </ul>
     </div>
     <div class="space"></div>
     <div class="task">
      <fieldset>
        <legend>系统提示消息</legend>
        <p><img src="<%=request.getContextPath()%>/images/correct.gif" alt="操作成功" /><span class="fontred">操作成功！</span></p>
        
         <div class="space"></div> 
         
       <p><span class="btn"><span class="btn_left"></span><span class="btn_bar"><a href="javascript:window.history.back(-1);" target="_self">返回</a></span><span class="btn_right"></span></span></p> 
      </fieldset>
      
    <div class="clearall"></div>
     </div>
</body>
</html>

