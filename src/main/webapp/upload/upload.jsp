<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'upload.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


<link rel="stylesheet" href="<%=path %>/uploadify/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=path %>/uploadify/upload.js"></script>

<style>
.buttonClass {
	background-color: red;width: 100px;
}
</style>

</head>

<body>
	<input type="file" name="uploadify" id="uploadify" />
	<!-- 当非自动上传，需使用 $('#uploadify').uploadify('upload','*')进行手动上传-->
	<a href="javascript:uploadFile();">Upload Files</a>
</body>

<script type="text/javascript">
var path = "<%=path%>";
var opts = {
		'id':'uploadify',
		'buttonClass':'buttonClass',
		'buttonText':'选择文件',
		'auto':false,
		'multi':true,
		'fileTypeExts' : '*.gif; *.jpg; *.png',
		'swf' : path + '/uploadify/uploadify.swf',
		'uploader' : path + '/FileUploadServlet',
		'fileSizeLimit' : '0',//单位B, KB, MB, or GB，设置为0表示不限制
		'uploadSuccess' : function(file, data, response) {
		
		},
		'uploadError' : function(file, errorCode, errorMsg,errorString) {
		
		},
		'uploadComplete' : function(file) {
		
		},
		'cancel':function(file) {
		
		}
};


var cuploadify;
$(function(){
	cuploadify = new CUploadify(opts);
});

function uploadFile() {
	/* var len = cuploadify.getAllFileLength();
	alert(len);
	alert(cuploadify.getFileLength()); */
	$('#uploadify').uploadify('upload','*');
}

</script>

</html>
