
<!-- ${msg}<script type="text/javascript" charset="utf-8">try{parent.$.messager.progress('close');}catch(e){}</script> -->
<!-- 以上代码用户在没有添加和修改权限时，信息提示弹出框无法弹出，可能是由于界面已经有一个弹出框处于打开状态，无法再打开一个弹出框 -->
<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript">

alert("${msg}");

</script>