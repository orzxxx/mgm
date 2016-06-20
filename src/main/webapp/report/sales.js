//@ sourceUrl=sales.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var menuTypes = {};
	function init(){
		initButton();
		getAllMenuType(currentMchntCd);
		setMenuType("sales_menutpId");
		initDatagrid();
	}
	/**
	 * 获取所有单品分类
	 */
	function getAllMenuType(currentMchntCd){
		$.ajax({
			url : "menu/type/tree",
			data : {mchntCd:currentMchntCd, needCombo: true},
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
	 * 填充单品分类选项卡
	 */
	function setMenuType(id){
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
	function initButton(){
		$("#sales_query").click(query);
		$("#sales_clear").click(clearForm);
		
		$("#sales_ytd").click(function(){
			setDate(1);
		});
		$("#sales_in3").click(function(){
			setDate(3);
		});
		$("#sales_in5").click(function(){
			setDate(5);
		});
		$("#sales_in7").click(function(){
			setDate(7);
		});
		$("#sales_in30").click(function(){
			setDate(30);
		});
	}
	function setDate(n){
		var ytd = new Date(Date.parse(new Date().toString()) - 1000*3600*24).format("yyyy-MM-dd");
		var startDate = new Date(Date.parse(new Date().toString()) - 1000*3600*24*n).format("yyyy-MM-dd");
		$("#sales_startDate").val(startDate);
		$("#sales_endDate").val(ytd);
		query();
	}
	function initDatagrid(){
		$('#sales_pageList').datagrid({
				fit:true,
				pagination:true,
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				pageList : [ 10, 20, 30 ],
				columns:[[
				   {field:'productName',title:'商品名',width:150,sortable:true,align:'center',formatter:function(value, rec){
						if (rec.status == "-1") {
							return "<span style=\"color:red;\">"+value+"(已删除)</span>";
						}
						return value;
					}},
					{field:'menutpName',title:'分类',width:150,sortable:true,align:'center'},
					{field:'sales',title:'销量',width:150,sortable:true,align:'center'}
				]],
				toolbar:"#sales_toolbar"
			});
			//重写翻页事件
			var pageOpts = $('#sales_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
				
		}

		function check(){
			if ($("#sales_startDate").val() != "" && $("#sales_endDate").val() != "") {
				if($("#sales_startDate").val() > $("#sales_endDate").val()){
					$.messager.alert("提示", "起始时间不得大于结束时间！");
					return false;
				}
			}
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#sales_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				param.mchntCd = currentMchntCd;
				//param.menutpId = $("#sales_menutpId").val();
				//param.startDate = $("#sales_startDate").val();
				//param.endDate = $("#sales_endDate").val();
				
				$('#sales_searchForm').ajaxSubmit( {
					data : param,
					url : contextPath+"/report/sales/list",
					dataType : "json",
		            success : function(result) {
		            	if (result.code == 0) {
		            		var data = result.data;
		            		$('#sales_pageList').datagrid('loadData',data);  
		            		if (data.rows == null || data.rows.length == 0) {
		            			$.messager.alert("提示", "没有符合条件的记录!");
							}
						} else {
							$.messager.alert("提示", result.message);
						}
					}
		        });
				/*$.post("report/sales/list", param, function(result){
					if (result.code == 0) {
	            		var data = result.data;
	            		$('#sales_pageList').datagrid('loadData',data);  
					} else {
						$.messager.alert("提示", result.message);
					}
				}, "json");*/
			}
	}
		
	function clearForm(){
		$("#sales_searchForm").form('clear');
		//$('#sales_pageList').datagrid('loadData',{ total: 0, rows: [] });
		$("#sales_menutpId").val("");
	}

	function reload(){
		query();
	}

    return {
        init : init
    }
    
});

	