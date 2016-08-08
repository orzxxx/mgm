//@ sourceUrl=on_off_shelves.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var menuTypes = {};
	function init(){
		initButton();
		getAllMenuType(currentMchntCd);
		setMenuType("shelves_serach_menutpId");
		initDatagrid();
		query();
	}
	function initButton(){
		$("#shelves_query").click(query);
		$("#shelves_clear").click(clearForm);
	}
	
	
	/**
	 * 获取所有单品分类
	 */
	function getAllMenuType(currentMchntCd){
		$.ajax({
			url : "menu/type/all",
			data : {mchntCd:currentMchntCd},
			dataType :"json",
			async:false,
			success : function(result){
				if (result.code == 0) {
					menuTypes = result.data;
				} else {
					$.messager.alert("提示", result.message);
				}
			}});
	}
	/**
	 * 获取所有单品分类树
	 */
	function getAllMenuTypeTree(currentMchntCd){
		$.ajax({
			url : "template/type/tree",
			data : {mchntCd:currentMchntCd},
			dataType :"json",
			async:false,
			success : function(result){
				if (result.code == 0) {
					setMenuTree(result.data);
				} else {
					$.messager.alert("提示", result.message);
				}
			}});
	}
	function setMenuTree(data){
		var treeData = getTreeDate(data);
		$("#menu_tree").tree('loadData', treeData);
	}
	/**
	 * 转成树型结构数据
	 */
	function getTreeDate(data){
		var treeData = [];
		for ( var i in data) {
			var pNode = {};
			pNode.text = data[i].menutpName;
			pNode.attributes = data[i];
			//pNode.iconCls = "";
			var childrens = [];
			var menus = data[i].menus;
			for ( var i in menus) {
				var cNode = {};
				cNode.text = menus[i].productName;
				//cNode.iconCls = "";
				cNode.attributes = menus[i];
				childrens.push(cNode);
			}
			pNode.children = childrens;
			treeData.push(pNode);
		}
		return treeData;
	}
	/**
	 * 填充单品分类选项卡
	 */
	function setMenuType(id){
		$("#"+id+" option").remove();
		$("#"+id).append("<option value=\"\">请选择</option>");
		for ( var i in menuTypes) {
			$("#"+id).append(buildMenuTypeOption(menuTypes[i]));
		}
	}
	function buildMenuTypeOption(menuType){
		var $ele = $("<option>");
		$ele.val(menuType.menutpId);
		$ele.text(menuType.menutpName);
		return $ele;
	}
	/**
	 * 根据分类id获取分类名
	 */
	function convertMenutpId(id){
		for ( var i in menuTypes) {
			if (menuTypes[i].menutpId == id) {
				return menuTypes[i].menutpName;
			}else if(id.match(/^C\d{8}$/)){
				return "套餐";
			}
		}
		return "已删除分类";
	}
	
	function initDatagrid(){
		$('#shelves_pageList').datagrid({
				fitColumns:false,
				fit:true,
				pagination:true,
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				pageList : [30],
				frozenColumns:[[
	                {field:'productId',title:'商品号',width:70,sortable:true}
				]],
				columns:[[
				    {field:'productName',title:'商品名',width:100,sortable:true,align:'center'},
				    {field:'menutpId',title:'单品分类',width:80,sortable:true,align:'center', formatter:function(value, rec){
						return convertMenutpId(value);
					    }},
				    {field:'price',title:'价格(元)',width:70,sortable:true,align:'center',formatter:function(value, rec){
					    return value.toFixed(2);
						}},
					{field:'status',title:'状态',width:70,sortable:true,align:'center',formatter:function(value, rec){
					    if (value == 0) {
							return "正常";
						}if (value == 1) {
							return "<span style=\"color:red;\">下架</span>";
						}
						return "<span style=\"color:red;\">异常</span>";
						}},
				    {field:'operation',title:'操作',width:200,sortable:true,align:'center',
						formatter : function(value, rec) {
							var on = rec.status == 0 ? "none" : "display";
							var off = rec.status == 0 ? "display" : "none";
							var buttons = "<button style='display:"+on+";' onclick=\"changeShelvesStatus('"+rec.productId+"', 0, this)\">上架</button>"+
							"<button style='display:"+off+";' onclick=\"changeShelvesStatus('"+rec.productId+"', 1, this)\">下架</button>";
						return buttons;
					}}
				]],
				toolbar:"#shelves_toolbar"
			});
			//重写翻页事件
			var pageOpts = $('#shelves_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
				
		}

		function check(){
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#shelves_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				param.mchntCd = currentMchntCd;
				
				$('#shelves_searchForm').ajaxSubmit( {
					data : param,
					url : contextPath+"/menu/menu/menuAndCombo",
					dataType : "json",
		            success : function(result) {
		            	if (result.code == 0) {
		            		var data = result.data;
		            		$('#shelves_pageList').datagrid('loadData',data);
		            		if (data.rows == null || data.rows.length == 0) {
		            			$("#shelves_total").text('0');
		            			$("#shelves_searchResult").addClass("ct-total0");
							}else{
								$("#shelves_searchResult").removeClass("ct-total0");
								$("#shelves_total").text(data.total);
							}
		            		$.parser.parse('#shelves_pageList');
						} else {
							$.messager.alert("提示", result.message);
						}
					}
		        });
			}
	}
		
	function clearForm(){
		$("#shelves_searchForm").form('clear');
		//$('#shelves_pageList').datagrid('loadData',{ total: 0, rows: [] });
		$("#shelves_serach_menutpId").val("");
		$("#shelves_serach_status").val("");
	}

	function reload(){
		query();
	}
	
    return {
        init : init
    };
    
});

function changeShelvesStatus(productId, status, node){
	$("#shelves_pageList").datagrid("selectRecord", productId);
	var row = $("#shelves_pageList").datagrid("getSelected");
	var index = $("#shelves_pageList").datagrid("getRowIndex", row);
	var param = {};
	param.productId = productId;
	param.status = status;
	param.mchntCd = userInfo.mchntCd;
	$.post("menu/menu/shelve", param, function(result){
		if (result.code == 0) {
			var statusText = status == 0 ? "正常" : "<span style='color:red;'>下架</span>";
			$(node).parents("td").siblings("td[field='status']").find("div").html(statusText);
			$(node).hide().siblings("button").show();
		} else {
			$.messager.alert("提示", result.message);
		}
	}, "json");
}