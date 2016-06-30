//@ sourceUrl=menu.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var menuTypes = {};
	var originalPicture = "images/default_menu.png";
	function init(){
		initButton();
		getAllMenuType(currentMchntCd);
		setMenuType("menu_serach_menutpId");
		initDatagrid();
		query();
	}
	function initButton(){
		$("#menu_add").click(add);
		$("#menu_del").click(del);
		$("#menu_edit").click(edit);
		$("#menu_query").click(query);
		$("#menu_clear").click(clearForm);
		$("#menu_import").click(menuImport);
	}
	/**
	 * 库存输入框切换
	 */
	function changeInventoryInput(status){
		if (status == 1) {
			$("#menu_inventory").numberbox("enable"); 
		}else if(status == 0){
			$("#menu_inventory").numberbox("disable"); 
		}
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
	function initMenuTree(data){
		$('#menu_tree').tree({
			checkbox:true,
			cascadeCheck:false,
			onBeforeCheck:function(node, checked){
				if($("#menu_tree").tree('isLeaf',node.target)){
					var pNode = $("#menu_tree").tree('getParent',node.target);
					$("#menu_tree").tree('check',pNode.target);
				}else{
					var cNodes = $("#menu_tree").tree('getChildren',node.target);
					if (!checked) {
						for ( var i in cNodes) {
							$("#menu_tree").tree('uncheck',cNodes[i].target);
						}
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
			}
		}
		return "";
	}
	function upload(){
		if ($("#menu_picture").val() == "") {
			$("#menu_img").attr('src', originalPicture);
    		$("#menu_pictureLink").val("");
			return;
		}
		if($("#menu_picture").validatebox('isValid') && $("#menu_picture").val() != ""){
			$('#menu_form').ajaxSubmit( {
    			url : "menu/menu/upload",
    			dataType : "json",
                success : function(result) {
					if (result.code == 0) {
						//图片回显
						$.messager.alert("提示", "图片上传成功");
	            		$("#menu_img").attr('src', result.data.pictureAddr).show();
	            		$("#menu_pictureLink").val(result.data.pictureLink);
					} else {
						$("#menu_picture").val("");
						$.messager.alert("提示", result.message);
					}
    			}
            })
		}
	}
	function initDatagrid(){
		$('#menu_pageList').datagrid({
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
				    {field:'price',title:'单价(元)',width:70,sortable:true,align:'center',formatter:function(value, rec){
					    return value.toFixed(2);
						}},
					{field:'packingBoxNum',title:'打包盒份数',width:70,sortable:true,align:'center'},
					{field:'inventory',title:'库存',width:70,sortable:true,align:'center',formatter:function(value, rec){
					    if (value.inventory == -1) {
							return "无限制";
						}if (value.inventory == 0) {
							return "<span style=\"color:red;\">售罄</span>";
						}
						return value.inventory;
						}},
					/*{field:'specifications',title:'规格',width:100,sortable:true,align:'center',formatter:function(value, rec){
						if (value == "") {
							return "<span style=\"color:red;\">不可选</span>";
						}
						return value.replace(/\|/g, ",");
						}},
					{field:'taste',title:'口味',width:100,sortable:true,align:'center',formatter:function(value, rec){
						if (value == "") {
							return "<span style=\"color:red;\">不可选</span>";
						}
						return value.replace(/\|/g, ",");
					}},*/
					{field:'productAttrTypes',title:'属性',width:100,sortable:true,align:'center',formatter:function(value, rec){
							if (value == null || value == "") {
								return "<span style=\"color:red;\">未配置</span>";
							}
							var types = "";
							for ( var i in value) {
								types += value[i].attrTypeName+",";
							}
							return types.substring(0, types.length-1);
					}},
				    {field:'productDetail',title:'商品详情',width:200,sortable:true,align:'center',
						formatter : function(value, rec) {
						var info = value.length > 13 ? value.substring(0,13)+"..." : value;
						return "<span title=\""+value+".\" " +
								"class=\"easyui-tooltip\">"+info+"</span>";
					}}
				]],
				toolbar:"#menu_toolbar",
				onDblClickRow:function(index, row){
					edit();
				}
			});
			//重写翻页事件
			var pageOpts = $('#menu_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
				
		}

		function check(){
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#menu_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				param.mchntCd = currentMchntCd;
				
				$('#menu_searchForm').ajaxSubmit( {
					data : param,
					url : contextPath+"/menu/menu/list",
					dataType : "json",
		            success : function(result) {
		            	if (result.code == 0) {
		            		var data = result.data;
		            		$('#menu_pageList').datagrid('loadData',data);
		            		if (data.rows == null || data.rows.length == 0) {
		            			$("#menu_total").text('0');
		            			$("#menu_searchResult").addClass("ct-total0");
							}else{
								$("#menu_searchResult").removeClass("ct-total0");
								$("#menu_total").text(data.total);
							}
						} else {
							$.messager.alert("提示", result.message);
						}
					}
		        })
			}
	}
		
	function clearForm(){
		$("#menu_searchForm").form('clear');
		//$('#menu_pageList').datagrid('loadData',{ total: 0, rows: [] });
		$("#menu_serach_menutpId").val("");
	}

	function add(){
		var dlg = $('<div/>').dialog({    
		    title: '添加单品',    
		    width: 420,    
		    height: 520,    
		    closable: false,    
		    cache: false,    
		    href: 'menu/menu_form.jsp',    
		    modal: true,
		    buttons : [ {
				text : '添加',
				handler :function(){
		    	if($('#menu_form').form("validate")){
		    		requirejs(['menu-attr'],function  (attr) {
		    			var param = {};
						param.productAttrTypeJson = attr.getAttrArr();
						if (!param.productAttrTypeJson) {
							return;
						}
						$('#menu_form').ajaxSubmit( {
			    			url : contextPath+"/menu/menu/add",
			    			data: param,
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
					});
		    		
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
				initForm();
				tagsInit();
				originalPicture = "images/default_menu.png";
				
				requirejs(['menu-attr'],function  (attr) {
					attr.init();
				});
			}
		});  
	}

	function edit(){
		var row = $('#menu_pageList').datagrid("getSelected");
		if(row){
			var dlg = $('<div/>').dialog({    
			    title: '编辑菜品',    
			    width: 420,    
			    height: 520,    
			    closable: false,    
			    cache: false,    
			    href: 'menu/menu_form.jsp',    
			    modal: true,
			    buttons : [ {
					text : '保存',
					handler : function(){
			    	//$('#menu_form').form('enableValidation');
			    	//$('#menu_picture').validatebox('disableValidation');
						requirejs(['menu-attr'],function  (attr) {
			    			var param = {};
							param.productAttrTypeJson = attr.getAttrArr();
							if (!param.productAttrTypeJson) {
								return;
							}
							if($('#menu_form').form("validate")){
					    		$('#menu_form').ajaxSubmit( {
					    			url : contextPath+"/menu/menu/update",
					    			data: param,
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
						});
			    	
			    }
				},{
					text : '关闭',
					handler : function() {
						dlg.dialog('close');
		        		dlg.remove();
					}
				}],
				onLoad : function(){
					$('#menu_picture').validatebox({    
					    required: false,    
					    validType: ''   
					});
					initForm();
					//数据填充
					$("#menu_form").form('load', row);
					tagsInit();
					if (row.inventory.inventory == "0") {
						$("#menu_soldout").attr('checked',true);
					}else if(row.inventory.inventory == "-1"){
						$("#menu_unlimited").attr('checked',true);
					}else{
						$("#menu_custom").attr('checked',true);
						$("#menu_inventory").numberbox("enable"); 
						$("#menu_inventory").numberbox('setValue', row.inventory.inventory);
					}
					$("#menu_img").attr('src', row.pictureLink).show();
					originalPicture = $("#menu_img").attr('src');
					$('#menu_pictureLink').val("");
					//$('#menu_picture').validatebox('disableValidation');
					$("#menu_productId, #menu_inventoryId").val(row.productId);
					$("#menu_form tt[optional='true']").remove();
					
					requirejs(['menu-attr'],function  (attr) {
						attr.init();
						attr.loadAttr(row.productAttrTypes);
					});
				}
			}); 
		}else{
			$.messager.alert('提示', '请选择要修改的记录！', 'info');
		}
	}

	function del(){
		var row = $('#menu_pageList').datagrid("getSelected");
		if(row){
			$.messager.confirm('提示', '确定删除该记录?', function(r){
				if (r){
					var param = {};
					
					param.productId = row.productId;
					$.post("menu/menu/del",param,function(result){
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

	function reload(){
		query();
	}
	
	function menuImport(){
		var dlg = $('<div/>').dialog({    
		    title: '导入单品',    
		    width: 500,    
		    height: 500,    
		    closable: false,    
		    cache: false,    
		    href: 'menu/menu_import.jsp',    
		    modal: true,
		    buttons : [ {
				text : '导入',
				handler :function(){
		    		var selectedNodes = $("#menu_tree").tree('getChecked');
		    		if(selectedNodes == null || selectedNodes.length == 0){
		    			$.messager.alert("提示", "至少选中1条数据");
		    			return;
		    		}
		    		//拼接参数
		    		var param = [];
		    		for ( var i in selectedNodes) {
		    			var node = selectedNodes[i];
		    			//只处理分类节点,已包含子节点
		    			if (!$("#menu_tree").tree('isLeaf',node.target)) {
		    				node.children = $("#menu_tree").tree("getChildren", node.target);
							var type = node.attributes;
							var menus = [];
							for ( var i in node.children) {
								if (node.children[i].checked) {
									var menu = node.children[i].attributes;
									menu.mchntCd = currentMchntCd;
									menus.push(menu);
								}
							}
							type.menus = menus;
							type.mchntCd = currentMchntCd;
							param.push(type);
						}
					}
		    		$.ajax({
		    			 type:"post",
		    			 url:"menu/type/addTree",
		    			 dataType:"json",
		    			 contentType:"application/json",
		    			 data:JSON.stringify(param),
		    			 success:function(result){
								if (result.code == 0) {
									$.messager.alert("提示", "导入成功!");
									//重新刷新
									getAllMenuType(currentMchntCd);
									setMenuType("menu_serach_menutpId");
									
									reload();
									dlg.dialog('close');
						    		dlg.remove();
								}else{
									$.messager.alert("提示", result.message);
								}
							}
		    			 });
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
	
	function initForm(){
		//表单赋值
		$("#menu_mchntCd").val(userInfo.mchntCd);
		setMenuType("menu_menutpId");
		//按钮事件
		
		$("#menu_picture").change(upload);
		$("#menu_unlimited, #menu_soldout").change({status:0},function(event){
			changeInventoryInput(event.data.status);
		});
		$("#menu_custom").change({status:1},function(event){
			changeInventoryInput(event.data.status);
		});
		$("#menu_inventory").numberbox({onChange:function(newValue, oldValue){
			$("#menu_custom").val(newValue);
		}});
	}

	function tagsInit(){
		//标签功能
		var t = $('#menu_taste').tagsInput({
			'height':'65px',
			'width':'170px',
			'defaultText':'',
			'interactive':false,
			'delimiter': ['|'],
			'onBeforeAddTag':function(value){
				if (value.replace(/[^\x00-\xff]/g,"aa").length > 12) {
					$.messager.alert("提示", "单个口味最多支持12个字符!");
					return false;
				}
				//个数校验
				var tags = $("#menu_form [name='taste']").val().split("|");
				if (tags.length >= 5) {
					$.messager.alert("提示", "口味最多添加5个!");
					return false;
				}
				return true;
			}
		});
		var s = $('#menu_specifications').tagsInput({
			'height':'65px',
			'width':'170px',
			'defaultText':'',
			'interactive':false,
			'delimiter': ['|'],
			'onBeforeAddTag':function(value){
				if (value.replace(/[^\x00-\xff]/g,"aa").length > 8) {
					$.messager.alert("提示", "单个规格最多支持8个字符!");
					return false;
				}
				//个数校验
				var tags = $("#menu_form [name='specifications']").val().split("|");
				if (tags.length >= 5) {
					$.messager.alert("提示", "规格最多添加5个!");
					return false;
				}
				return true;
			}
		});
		$("#menu_addSpec").click(function(){
			addSpec(s);
		});
		$("#menu_addTaste").click(function(){
			addTaste(t);
		});
	}
	function addSpec(s){
		var spec = $("#menu_forSpec").val();
		if (spec == "") {
			$.messager.alert("提示", "请输入规格");
			return;
		}
		if ($(s).tagExist(spec)) {
			$.messager.alert("提示", "重复的规格:"+spec);
			return;
		}
		$(s).addTag(spec);
		$("#menu_forSpec").val("");
	}
	function addTaste(t){
		var taste = $("#menu_forTaste").val();
		if (taste == "") {
			$.messager.alert("提示", "请输入口味");
			return;
		}
		if ($(t).tagExist(taste)) {
			$.messager.alert("提示", "重复的口味:"+taste);
			return;
		}
		$(t).addTag(taste);
		$("#menu_forTaste").val("");
	}
    return {
        init : init
    }
    
});

	