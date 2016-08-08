//@ sourceUrl=menuTmpl.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var menuTypes = {};
	var originalPicture = "images/default_menu.png";
	function init(){
		initButton();
		getAllMenuType(currentMchntCd);
		setMenuType("menuTmpl_search_menutpId");
		initDatagrid();
		query();
	}
	function initButton(){
		$("#menuTmpl_add").click(add);
		$("#menuTmpl_del").click(del);
		$("#menuTmpl_edit").click(edit);
		$("#menuTmpl_query").click(query);
		$("#menuTmpl_clear").click(clearForm);
	}
	/**
	 * 库存输入框切换
	 */
	function changeInventoryInput(status){
		if (status == 1) {
			$("#menuTmpl_inventory").numberbox("enable"); 
		}else if(status == 0){
			$("#menuTmpl_inventory").numberbox("disable"); 
		}
	}
	/**
	 * 获取所有单品分类
	 */
	function getAllMenuType(currentMchntCd){
		$.ajax({
			url : "template/type/all",
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
	function initDatagrid(){
		$('#menuTmpl_pageList').datagrid({
				fitColumns:false,
				fit:true,
				pagination:true,
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				pageList : [ 30 ],
				frozenColumns:[[
	                {field:'productId',title:'商品号',width:70,sortable:true}
				]],
				columns:[[
				    {field:'productName',title:'商品名',width:150,sortable:true,align:'center'},
				    {field:'menutpId',title:'单品分类',width:70,sortable:true,align:'center', formatter:function(value, rec){
						return convertMenutpId(value);
					    }},
					    {field:'price',title:'价格(元)',width:70,sortable:true,align:'center',formatter:function(value, rec){
						    return value.toFixed(2);
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
				toolbar:"#menuTmpl_toolbar",
				onDblClickRow:function(index, row){
					edit();
				}
			});
			//重写翻页事件
			var pageOpts = $('#menuTmpl_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
				
		}
	function upload(){
		if ($("#menuTmpl_picture").val() == "") {
			$("#menuTmpl_img").attr('src', originalPicture);
    		$("#menuTmpl_pictureLink").val("");
			return;
		}
		if($("#menuTmpl_picture").validatebox('isValid') && $("#menuTmpl_picture").val() != ""){
			$('#menuTmpl_form').ajaxSubmit( {
    			url : "template/menu/upload",
    			dataType : "json",
                success : function(result) {
					if (result.code == 0) {
						//图片回显
						$.messager.alert("提示", "图片上传成功");
	            		$("#menuTmpl_img").attr('src', result.data.pictureAddr).show();
	            		$("#menuTmpl_pictureLink").val(result.data.pictureLink);
					} else {
						$("#menuTmpl_picture").val("");
						$.messager.alert("提示", result.message);
					}
    			}
            })
		}
	}
		function check(){
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#menuTmpl_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				param.mchntCd = currentMchntCd;
				
				$('#menuTmpl_searchForm').ajaxSubmit( {
					data : param,
					url : contextPath+"/template/menu/list",
					dataType : "json",
		            success : function(result) {
		            	if (result.code == 0) {
		            		var data = result.data;
		            		$('#menuTmpl_pageList').datagrid('loadData',data);  
		            		if (data.rows == null || data.rows.length == 0) {
		            			$("#menuTmpl_total").text('0');
		            			$("#menuTmpl_searchResult").addClass("ct-total0");
							}else{
								$("#menuTmpl_searchResult").removeClass("ct-total0");
								$("#menuTmpl_total").text(data.total);
							}
						} else {
							$.messager.alert("提示", result.message);
						}
					}
		        })
			}
	}
		
	function clearForm(){
		$("#menuTmpl_searchForm").form('clear');
		//$('#menuTmpl_pageList').datagrid('loadData',{ total: 0, rows: [] });
		$("#menuTmpl_menutpId").val("");
	}

	function add(){
		var dlg = $('<div/>').dialog({    
		    title: '添加单品',    
		    width: 420,    
		    height: 530,    
		    closable: false,    
		    cache: false,    
		    href: 'template/menu_form.jsp',    
		    modal: true,
		    buttons : [ {
				text : '添加',
				handler :function(){
					requirejs(['menu-attr'],function  (attr) {
		    			var param = {};
						param.productAttrTypeJson = attr.getAttrArr();
						if (!param.productAttrTypeJson) {
							return;
						}
						if($('#menuTmpl_form').form("validate")){
				    		$('#menuTmpl_form').ajaxSubmit( {
				    			url : contextPath+"/template/menu/add",
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
				    	}else{
				    		$('#menuTmpl_tab').tabs('select', '基础信息');
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
			    originalPicture = "images/default_menu.png";
				initForm();
				tagsInit();
				
				requirejs(['menu-attr'],function  (attr) {
					attr.init();
				});
			}
		});  
	}

	function edit(){
		var row = $('#menuTmpl_pageList').datagrid("getSelected");
		if(row){
			var dlg = $('<div/>').dialog({    
			    title: '编辑单品',    
			    width: 420,    
			    height: 530,    
			    closable: false,    
			    cache: false,    
			    href: 'template/menu_form.jsp',    
			    modal: true,
			    buttons : [ {
					text : '保存',
					handler : function(){
			    	//$('#menuTmpl_form').form('enableValidation');
			    	//$('#menuTmpl_picture').validatebox('disableValidation');
						requirejs(['menu-attr'],function  (attr) {
			    			var param = {};
							param.productAttrTypeJson = attr.getAttrArr();
							if (!param.productAttrTypeJson) {
								return;
							}
							if($('#menuTmpl_form').form("validate")){
					    		$('#menuTmpl_form').ajaxSubmit( {
					    			url : contextPath+"/template/menu/update",
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
					    	}else{
					    		$('#menuTmpl_tab').tabs('select', '基础信息');
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
					$('#menuTmpl_picture').validatebox({    
					    required: false,    
					    validType: ''   
					});

					initForm();
					//数据填充
					$("#menuTmpl_form").form('load', row);
					tagsInit();
					if (row.inventory.inventory == "0") {
						$("#menuTmpl_soldout").attr('checked',true);
					}else if(row.inventory.inventory == "-1"){
						$("#menuTmpl_unlimited").attr('checked',true);
					}else{
						$("#menuTmpl_custom").attr('checked',true);
						$("#menuTmpl_inventory").numberbox("enable"); 
						$("#menuTmpl_inventory").numberbox('setValue', row.inventory.inventory);
					}
					$("#menuTmpl_img").attr('src', row.pictureLink).show();
					originalPicture = $("#menuTmpl_img").attr('src');
					$('#menuTmpl_pictureLink').val("");
					//$('#menuTmpl_picture').validatebox('disableValidation');
					$("#menuTmpl_productId, #menuTmpl_inventoryId").val(row.productId);
					$("#menuTmpl_form tt[optional='true']").remove();
					
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
		var row = $('#menuTmpl_pageList').datagrid("getSelected");
		if(row){
			$.messager.confirm('提示', '确定删除该记录?', function(r){
				if (r){
					var param = {};
					param.productId = row.productId;
					$.post("template/menu/del",param,function(result){
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
	
	function initForm(){
		//表单赋值
		$("#menuTmpl_mchntCd").val(userInfo.mchntCd);
		setMenuType("menuTmpl_menutpId");
		
		//$("#menuTmpl_picture").change(upload);
		$("#menuTmpl_picture").click(toUpload);
		//按钮事件
		/*$("#menuTmpl_unlimited, #menuTmpl_soldout").change({status:0},function(event){
			changeInventoryInput(event.data.status);
		});
		$("#menuTmpl_custom").change({status:1},function(event){
			changeInventoryInput(event.data.status);
		});
		$("#menuTmpl_inventory").numberbox({onChange:function(newValue, oldValue){
			$("#menuTmpl_custom").val(newValue);
		}});*/
	}

	function toUpload(){
		var dlg = $('<div/>').dialog({    
		    title: '上传图片',    
		    width: 700,    
		    height: 500,    
		    closable: false,    
		    cache: false,    
		    href: 'sys/img_upload.jsp',    
		    modal: true,
		    buttons : [ {
				text : '确定',
				handler :function(){
					var param = {};
					requirejs(['img-upload'],function(img) {
						param = img.getData();
						param.mchntCd = currentMchntCd;
						
						$('#img_form').ajaxSubmit( {
							url : "menu/menu/upload",
							dataType : "json",
							data: param,
				            success : function(result) {
								if (result.code == 0) {
									$("#menuTmpl_img").attr('src', result.data.pictureAddr).show();
				            		$("#menuTmpl_pictureLink").val(result.data.pictureLink);
				            		//关闭对话框
			                		dlg.dialog('close');
			    	        		dlg.remove();
								} else {
									//待处理$("#menu_picture").val("");
									$.messager.alert("提示", result.message);
								}
							}
				        });
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
				requirejs(['img-upload'],function(img) {
					img.init();
				});
			}
		});  
	}
	
	function tagsInit(){
		//标签功能
		var t = $('#menuTmpl_taste').tagsInput({
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
				var tags = $("#menuTmpl_form [name='taste']").val().split("|");
				if (tags.length >= 5) {
					$.messager.alert("提示", "口味最多添加5个!");
					return false;
				}
				return true;
			}
		});
		var s = $('#menuTmpl_specifications').tagsInput({
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
				var tags = $("#menuTmpl_form [name='specifications']").val().split("|");
				if (tags.length >= 5) {
					$.messager.alert("提示", "规格最多添加5个!");
					return false;
				}
				return true;
			}
		});
		$("#menuTmpl_addSpec").click(function(){
			addSpec(s);
		});
		$("#menuTmpl_addTaste").click(function(){
			addTaste(t);
		});
	}
	function addSpec(s){
		var spec = $("#menuTmpl_forSpec").val();
		if (spec == "") {
			$.messager.alert("提示", "请输入规格");
			return;
		}
		if ($(s).tagExist(spec)) {
			$.messager.alert("提示", "重复的规格:"+spec);
			return;
		}
		$(s).addTag(spec);
		$("#menuTmpl_forSpec").val("");
	}
	function addTaste(t){
		var taste = $("#menuTmpl_forTaste").val();
		if (taste == "") {
			$.messager.alert("提示", "请输入口味");
			return;
		}
		if ($(t).tagExist(taste)) {
			$.messager.alert("提示", "重复的口味:"+taste);
			return;
		}
		$(t).addTag(taste);
		$("#menuTmpl_forTaste").val("");
	}
    return {
        init : init
    }
    
});

	