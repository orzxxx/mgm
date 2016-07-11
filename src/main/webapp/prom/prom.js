//@ sourceUrl=prom.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var menuTypes = {};
	var originalPicture = "images/default_menu.png";
	function init(){
		getAllMenuType(currentMchntCd);
		initButton();
		initDatagrid();
		query();
	}
	function initButton(){
		$("#prom_add").click(add);
		$("#prom_del").click(del);
		$("#prom_edit").click(edit);
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
	function initDatagrid() {
		$('#prom_pageList').datagrid( {
			fit : true,
			pagination : true,
			fitColumns : true,
			nowrap : true,
			striped : true,
			singleSelect : true,
			remoteSort : false,
			pageList : [20],
			frozenColumns : [ [ {
				field : 'productId',
				title : '商品号',
				width : 70,
				sortable : true
			} ] ],
			columns : [ [ {
				field : 'productName',
				title : '商品名',
				width : 100,
				sortable : true,
				align : 'center'
			}, {
				field : 'menutpId',
				title : '分类',
				width : 70,
				sortable : true,
				align : 'center',
				formatter:function(value, rec){
					if (value.match(/^C\d{8}$/)) {
						return "套餐";
					}
					return convertMenutpId(value);
			    }
			}, {
				field : 'price',
				title : '单价(元)',
				width : 70,
				sortable : true,
				align : 'center',
				formatter : function(value, rec) {
					return value.toFixed(2);
				}
			}, /*{field:'specifications',title:'规格',width:100,sortable:true,align:'center',formatter:function(value, rec){
				if (value == "") {
					//return "<span style=\"color:red;\">不可选</span>";
					return "不可选";
				}
				return value.replace(/\|/g, ",");
				}},
			{field:'taste',title:'口味',width:100,sortable:true,align:'center',formatter:function(value, rec){
				if (value == "") {
					//return "<span style=\"color:red;\">不可选</span>";
					return "不可选";
				}
				return value.replace(/\|/g, ",");
			}},*/{
				field : 'productDetail',
				title : '商品详情',
				width : 200,
				sortable : true,
				align : 'center',
				formatter : function(value, rec) {
					var info = value.length > 13 ? value.substring(0,13)+"..." : value;
					return "<span title=\""+value+".\" " +
							"class=\"easyui-tooltip\">"+info+"</span>";
				}
			},{
				field : 'promotionInf',
				title : '促销信息',
				width : 200,
				sortable : true,
				align : 'center',
				formatter : function(value, rec) {
					var info = value.length > 13 ? value.substring(0,13)+"..." : value;
					return "<span title=\""+value+".\" " +
							"class=\"easyui-tooltip\">"+info+"</span>";
				}
			}, {
				field : 'status',
				title : '状态',
				width : 70,
				sortable : true,
				align : 'center',
				formatter : function(value, rec) {
					if (rec.status == -1) {
						return "菜品已删除";
					}else if(rec.inventory == null || rec.inventory == ""){
						return "已售罄";
					}
					return "正常";
				}
			} ] ],
			toolbar : "#prom_toolbar",
			onDblClickRow:function(index, row){
				edit();
			},
			rowStyler : function(index, row) {
				if (row.status == -1) {
					return 'color:red;';
				}
				if (row.inventory == null || row.inventory == "") {
					return 'color:blue;';
				}
			}

		});
		//重写翻页事件
		var pageOpts = $('#prom_pageList').datagrid('getPager').data(
				"pagination").options;
		pageOpts.onSelectPage = query;

	}

		function check(){
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#prom_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				param.mchntCd = currentMchntCd;
				
				$.post("prom/prom/list",param,function(result){
					if (result.code == 0) {
						var data = result.data;
						for ( var i in data.rows) {
							data.rows[i] = $.extend({}, data.rows[i].product, data.rows[i]);
						}
						data.rows.sort(function(a, b){
							if (a.status == b.status) {
								return a.inventory - b.inventory;
							} else {
								return a.status - b.status;
							}
							
						});
	            		$('#prom_pageList').datagrid('loadData',data);  
					} else {
						$.messager.alert("提示", result.message);
					}
				}, "json");
			}
	}
		
	function clearForm(){
		$("#prom_searchForm").form('clear');
		$('#prom_pageList').datagrid('loadData',{ total: 0, rows: [] });
	}

	function add(){
		var total = $('#prom_pageList').datagrid('getPager').data(
			"pagination").options.total;
		if (total >= 10) {
			$.messager.alert("提示", "最多只能添加10个促销菜品");
			return;
		}
		var dlg = $('<div/>').dialog({    
		    title: '添加促销菜品',    
		    width: 500,    
		    height: 500,    
		    closable: false,    
		    cache: false,    
		    href: 'prom/prom_form.jsp',    
		    modal: true,
		    buttons : [ {
				text : '添加',
				handler :function(){
		    	if($('#prom_form').form("validate")){
		    		if($("#prom_productName").val() == ""){
		    			$.messager.alert("提示", "请选择促销菜品");
		    			return;
		    		}
		    		$('#prom_form').ajaxSubmit( {
		    			url : contextPath+"/prom/prom/add",
		    			dataType : "json",
		                success : function(result) {
		                	if (result.code == 0) {
		                		$.messager.alert("提示", "添加成功!");
		                		//刷新
		                		reload();
		                		//关闭对话框
		                		dlg.dialog('close');
		    	        		dlg.remove();
		    				} else {
		    					$.messager.alert("提示", result.message);
		    				}
		    			}
		            })
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
				originalPicture = "images/default_menu.png";
				initForm();
			}
		});  
	}

	function edit(){
		var row = $('#prom_pageList').datagrid("getSelected");
		if(row){
			var dlg = $('<div/>').dialog({    
			    title: '编辑促销菜品',    
			    width: 500,    
			    height: 500,    
			    closable: false,    
			    cache: false,    
			    href: 'prom/prom_form.jsp',    
			    modal: true,
			    buttons : [ {
					text : '保存',
					handler : function(){
			    	//$('#prom_form').form('enableValidation');
			    	//$('#prom_picture').validatebox('disableValidation');
			    	if($('#prom_form').form("validate")){
			    		$('#prom_form').ajaxSubmit( {
			    			url : contextPath+"/prom/prom/update",
			    			dataType : "json",
			                success : function(result) {
			                	if (result.code == 0) {
			                		$.messager.alert("提示", "修改成功!");
			                		//刷新
			                		reload();
			                		//关闭对话框
			                		dlg.dialog('close');
			    	        		dlg.remove();
			                		
			    				} else {
			    					$.messager.alert("提示", result.message);
			    				}
			    			}
			            })
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
					$('#prom_picture').validatebox({    
					    required: false,    
					    validType: ''   
					});
					initForm();
					//数据填充
					var inventory = row.inventory;
					if (row.inventory == "-1") {
						inventory = "无限制";
					}else if(inventory == "0"){
						inventory = "售罄";
					}
					row.inventory = inventory;
					row.menutpName = convertMenutpId(row.menutpId);
					//row.taste = row.taste.replace(/\|/g, ",");
					//row.specifications = row.specifications.replace(/\|/g, ",");
					$("#prom_form").form('load', row);
					$("#prom_img").attr('src', row.pictureLink).show();
					originalPicture = $("#prom_img").attr('src');
					//$('#prom_picture').validatebox('disableValidation');
					$('#prom_pictureLink').val('');
					$("#prom_productId").val(row.productId);
					$("#prom_form tt[optional='true']").remove();
					$("#prom_select").hide();
				}
			}); 
		}else{
			$.messager.alert('提示', '请选择要修改的记录！', 'info');
		}
	}
	
	function select(){
		var dlg = $('<div/>').dialog({    
		    title: '选择单品(单选)',    
		    width: 500,    
		    height: 500,    
		    closable: false,    
		    cache: false,    
		    href: 'menu/combo_select.jsp',    
		    modal: true,
		    buttons : [ {
				text : '确定',
				handler :function(){
		    		var selectedNodes = $("#combo_tree").tree('getChecked');
		    		var length = 0;
		    		for ( var i in selectedNodes) {
		    			if ($("#combo_tree").tree('isLeaf',selectedNodes[i].target)) {
							length++;
						}
					}
		    		
		    		if(selectedNodes == null || length == 0){
		    			$.messager.alert("提示", "请选择1个单品");
		    			return;
		    		}
		    		if(length > 1){
		    			$.messager.alert("提示", "一次只能选择1个单品");
		    			return;
		    		}
		    		//拼接参数
		    		var menu = {};
		    		for ( var i in selectedNodes) {
		    			var node = selectedNodes[i];
		    			//只处理子节点
		    			if ($("#combo_tree").tree('isLeaf',node.target)) {
							menu = node.attributes;
							var pNode = $("#combo_tree").tree('getParent',node.target);
							menu.menutpName = pNode.attributes.menutpName;
						}
					}
		    		//赋值
		    		//menu.taste = menu.taste.replace(/\|/g, ",");
		    		//menu.specifications = menu.specifications.replace(/\|/g, ",");
		    		var inventory = menu.inventory.inventory;
					if (inventory == "-1") {
						inventory = "无限制";
					}else if(inventory == "0"){
						inventory = "售罄";
					}
					menu.inventory = inventory;
					delete menu.pictureLink;//避免覆盖图片字段
		    		$("#prom_form").form('load', menu);
		    		//关闭
		    		dlg.dialog('close');
		    		dlg.remove();
		    	}
			},{
				text : '关闭',
				handler : function() {
					dlg.dialog('close');
		    		dlg.remove();
				}
			}],
			onLoad : function(){
				initMenuTree();
			}
		});  
	}
	
	function initMenuTree(data){
		$('#combo_tree').tree({
			checkbox:true,
			onBeforeCheck: function(node, checked){
				//只能选择叶节点
				if(!$("#combo_tree").tree('isLeaf',node.target)){
					return false;
				}
				//一次只能选中一个，先把选中的都取消
				if(checked){
					var selectedNodes = $('#combo_tree').tree("getChecked");
					for ( var i in selectedNodes) {
						$('#combo_tree').tree("uncheck", selectedNodes[i].target);
					}
				}
			}
		}); 
		getAllMenuTypeTree(currentMchntCd);
		
	}
	/**
	 * 获取所有单品分类树
	 */
	function getAllMenuTypeTree(currentMchntCd){
		$.ajax({
			url : "menu/type/tree",
			data : {mchntCd:currentMchntCd, needCombo:true},
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
		$("#combo_tree").tree('loadData', treeData);
		//已添加为促销菜品标记
		var rows = $('#prom_pageList').datagrid('getRows');
		for ( var i in rows) {
			var node = $('#combo_tree').tree('find', rows[i].productId);
			if (node != null) {
				$('#combo_tree').tree('update', {
					target: node.target,
					text: rows[i].productName+"<img src='themes/icons/ok.png' style='width:14px;height:14px;border:0;'/>"
				});
			}
		}
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
				cNode.id = menus[i].productId;
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
	
	function del(){
		var row = $('#prom_pageList').datagrid("getSelected");
		if(row){
			$.messager.confirm('提示', '确定删除该记录?', function(r){
				if (r){
					var param = {};
					param.productId = row.productId;
					param.mchntCd = currentMchntCd;
					$.post(contextPath+"/prom/prom/del",param,function(result){
						if (result.code == 0) {
							$.messager.alert("提示", "删除成功!");
							//重新刷新
							reload();
						}else{
							$.messager.alert("提示", result.message);
						}
					},"json");
				}
			});
			
		}else{
			$.messager.alert('提示', '请选择要删除的记录!', 'info');
		}
	}
	function upload(){
		if ($("#prom_picture").val() == "") {
			$("#prom_img").attr('src', originalPicture);
    		$("#prom_pictureLink").val("");
			return;
		}
		if($("#prom_picture").validatebox('isValid') && $("#prom_picture").val() != ""){
			$('#prom_form').ajaxSubmit( {
    			url : "prom/prom/upload",
    			dataType : "json",
                success : function(result) {
					if (result.code == 0) {
						//图片回显
						$.messager.alert("提示", "图片上传成功");
	            		$("#prom_img").attr('src', result.data.pictureAddr).show();
	            		$("#prom_pictureLink").val(result.data.pictureLink);
					} else {
						$("#prom_picture").val("");
						$.messager.alert("提示", result.message);
					}
    			}
            })
		}
	}
	function reload(){
		query();
	}
	
	function initForm(){
		//表单赋值
		$("#prom_mchntCd").val(userInfo.mchntCd);
		$("#prom_select").click(select);
		
		$("#prom_picture").change(upload);
	}

    return {
        init : init
    }
    
});

	