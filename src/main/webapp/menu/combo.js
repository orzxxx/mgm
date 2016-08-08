//@ sourceUrl=combo.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var originalPicture = "images/default_menu.png";
	function init(){
		initButton();
		initDatagrid();
		query();
	}
	function initButton(){
		$("#combo_toAdd").click(toAdd);
		$("#combo_del").click(del);
		$("#combo_toEdit").click(toEdit);
		$("#combo_query").click(query);
		$("#combo_clear").click(clearForm);
	}
	function initDatagrid(){
		$('#combo_pageList').datagrid({
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
				    {field:'price',title:'价格(元)',width:70,sortable:true,align:'center',formatter:function(value, rec){
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
							{field:'status',title:'状态',width:70,sortable:true,align:'center',formatter:function(value, rec){
							    if (value == 0) {
									return "正常";
								}if (value == 1) {
									return "<span style=\"color:red;\">下架</span>";
								}
								return "<span style=\"color:red;\">异常</span>";
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
				    {field:'productDetail',title:'商品详情',width:200,sortable:true,align:'center',
						formatter : function(value, rec) {
						var info = value.length > 30 ? value.substring(0,30)+"..." : value;
						return "<span title=\""+value+".\" " +
								"class=\"easyui-tooltip\">"+info+"</span>";
					}}
				]],
				toolbar:"#combo_toolbar",
				onDblClickRow:function(index, row){
					toEdit();
				}
			});
			//重写翻页事件
			var pageOpts = $('#combo_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
				
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
									$("#combo_img").attr('src', result.data.pictureAddr).show();
				            		$("#combo_pictureLink").val(result.data.pictureLink);
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
	
		function check(){
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#combo_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				param.mchntCd = currentMchntCd;
				
				$('#combo_searchForm').ajaxSubmit( {
					data : param,
					url : contextPath+"/menu/combo/list",
					dataType : "json",
		            success : function(result) {
		            	if (result.code == 0) {
		            		var data = result.data;
		            		$('#combo_pageList').datagrid('loadData',data);  
		            		if (data.rows == null || data.rows.length == 0) {
		            			$("#combo_total").text('0');
		            			$("#combo_searchResult").addClass("ct-total0");
							}else{
								$("#combo_searchResult").removeClass("ct-total0");
								$("#combo_total").text(data.total);
							}
						} else {
							$.messager.alert("提示", result.message);
						}
					}
		        })
			}
	}
		
	function clearForm(){
		$("#combo_searchForm").form('clear');
		//$('#combo_pageList').datagrid('loadData',{ total: 0, rows: [] });
	}

	function toAdd(){
		$("#combo_form").load(contextPath+"/menu/combo_form.jsp", function(){
			requirejs(['combo-packages'],function  (pkg) {
				pkg.init();
				initForm();
				$("#combo_add").click(add);
			});
		}).show();
		$("#combo_list").hide();
	}
	
	function toEdit(){
		var row = $('#combo_pageList').datagrid("getSelected");
		if(row){
			$("#combo_form").load(contextPath+"/menu/combo_form.jsp", function(){
				var param = {};
				param.comboId = row.productId;
				$('#combo_baseform').ajaxSubmit( {
	    			url : contextPath+"/menu/combo/details",
	    			data: param,
	    			dataType : "json",
	                success : function(result) {
	                	if (result.code == 0) {
	                		requirejs(['combo-packages'],function  (pkg) {
	        					pkg.init();
	        					initForm();
	        					$("#combo_add").click(edit);
	        					$("#combo_list").hide();
	        					//数据回显
	        					$('#combo_picture').validatebox({    
	        					    required: false,    
	        					    validType: ''   
	        					});
	        					
	        					requirejs(['combo-packages'],function  (packages) {
	        						packages.loadAttr(result.data);
	        					});
	        					
	        					$("#combo_baseform").form('load', row);
	        					if (row.inventory.inventory == "0") {
	        						$("#combo_soldout").attr('checked',true);
	        					}else if(row.inventory.inventory == "-1"){
	        						$("#combo_unlimited").attr('checked',true);
	        					}else{
	        						$("#combo_custom").attr('checked',true);
	        						$("#combo_inventory").numberbox("enable"); 
	        						$("#combo_inventory").numberbox('setValue', row.inventory.inventory);
	        					}
	        					$("#combo_img").attr('src', row.pictureLink).show();
	        					originalPicture = $("#combo_img").attr('src');
	        					$('#combo_pictureLink').val('');
	        					$("#combo_productId, #combo_inventoryId").val(row.productId);
	        					$("#combo_baseform tt[optional='true']").remove();
	        				});
	    				} else {
	    					$.messager.alert("提示", result.message);
	    				}
	    			}
	            })
			}).show();
			
		}else{
			$.messager.alert('提示', '请选择要修改的记录！', 'info');
		}
		
	}
	
	function add(){
		if($('#combo_baseform').form("validate")){
			requirejs(['combo-packages'],function  (pkg) {
				var param = {};
				param.childComboTypeJson = {};
				param.childComboTypeJson = pkg.getAttrArr();
				if (!param.childComboTypeJson) {
					return;
				}
				if (param.childComboTypeJson == "[]") {
					$.messager.alert("提示", "请配置套餐组合!");
					return;
				}
				$('#combo_baseform').ajaxSubmit( {
	    			url : contextPath+"/menu/combo/add",
	    			data: param,
	    			dataType : "json",
	                success : function(result) {
	                	if (result.code == 0) {
	                		$.messager.alert("提示", "添加成功!");
	                		//刷新
	                		reload();
	                		//关闭对话框
	                		back();
	    				} else {
	    					$.messager.alert("提示", result.message);
	    				}
	    			}
	            })
			});
    	}
	}
	
	function edit(){
		if($('#combo_baseform').form("validate")){
			requirejs(['combo-packages'],function  (pkg) {
				var param = {};
				param.childComboTypeJson = {};
				param.childComboTypeJson = pkg.getAttrArr();
				if (!param.childComboTypeJson) {
					return;
				}
				$('#combo_baseform').ajaxSubmit( {
	    			url : contextPath+"/menu/combo/update",
	    			data: param,
	    			dataType : "json",
	                success : function(result) {
	                	if (result.code == 0) {
	                		$.messager.alert("提示", "修改成功!");
	                		//刷新
	                		reload();
	                		//关闭对话框
	                		back();
	    				} else {
	    					$.messager.alert("提示", result.message);
	    				}
	    			}
	            })
			});
    	}
	}
	
	function back(){
		$("#combo_form *").remove();
		$("#combo_list").show();
		//界面渲染
		$.parser.parse('#combo_pageList');
	}
	
	/*function add(){
		var dlg = $('<div/>').dialog({    
		    title: '添加套餐',    
		    width: 630,    
		    height: 600,    
		    closable: false,    
		    cache: false,    
		    href: 'menu/combo_form.jsp',    
		    modal: true,
		    buttons : [ {
				text : '添加',
				handler :function(){
		    	var param = {};
		    	param.comboInf = {};
		    	
		    	var rows = $('#combo_package').datagrid('getRows');        
		    	if (rows == "" || rows.length == 0) {
		    		$.messager.alert("提示", "请配置套餐组合");
		    		return;
				}
		    	
		    	if($('#combo_form').form("validate")){
			    	for ( var i in rows) {
			    		rows[i].attrCmp = "";
			    		rows[i].selectedAttr = "";
			    		for ( var j in rows[i].productAttrTypes) {
			    			rows[i].attrCmp += $("input[name='"+rows[i].productAttrTypes[j].attrTypeId+"']:checked").val()+"|";
			    			rows[i].selectedAttr += $("input[name='"+rows[i].productAttrTypes[j].attrTypeId+"']:checked").attr("attrName")+",";
						}
			    		if (rows[i].attrCmp != "") {
			    			rows[i].attrCmp = rows[i].attrCmp.substring(0, rows[i].attrCmp.length-1);
			    			rows[i].selectedAttr = rows[i].selectedAttr.substring(0, rows[i].selectedAttr.length-1);
						}
					}
			    	param.comboDetailsJson = JSON.stringify(rows);
			    	param.price = $('#combo_price').numberbox('getValue');
			    	param.oriPrice = $("#combo_oriPrice").text();
			    	if(parseInt(param.price) > parseInt(param.oriPrice)){
			    		$.messager.alert("提示", "套餐定价不能大于原价!");
			    		return;
			    	}
			    	
		    		$('#combo_form').ajaxSubmit( {
		    			url : contextPath+"/menu/combo/add",
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
			}
		});  
	}*/

	/*function edit(){
		var row = $('#combo_pageList').datagrid("getSelected");
		if(row){
			var dlg = $('<div/>').dialog({    
			    title: '编辑套餐',    
			    width: 630,    
			    height: 600,    
			    closable: false,    
			    cache: false,    
			    href: 'menu/combo_form.jsp',    
			    modal: true,
			    buttons : [ {
					text : '保存',
					handler : function(){
						var param = {};
				    	param.comboInf = {};
				    	
				    	var rows = $('#combo_package').datagrid('getRows');        
				    	if (rows == "" || rows.length == 0) {
				    		$.messager.alert("提示", "请配置套餐组合");
				    		return;
						}	
						
					//$('#combo_form').form('enableValidation');
				    //$('#combo_picture').validatebox('disableValidation');
			    	if($('#combo_form').form("validate")){
			    		for ( var i in rows) {
				    		rows[i].attrCmp = "";
				    		rows[i].selectedAttr = "";
				    		for ( var j in rows[i].productAttrTypes) {
				    			rows[i].attrCmp += $("input[name='"+rows[i].productAttrTypes[j].attrTypeId+"']:checked").val()+"|";
				    			rows[i].selectedAttr += $("input[name='"+rows[i].productAttrTypes[j].attrTypeId+"']:checked").attr("attrName")+",";
							}
				    		if (rows[i].attrCmp != "") {
				    			rows[i].attrCmp = rows[i].attrCmp.substring(0, rows[i].attrCmp.length-1);
				    			rows[i].selectedAttr = rows[i].selectedAttr.substring(0, rows[i].selectedAttr.length-1);
							}
						}
				    	param.comboDetailsJson = JSON.stringify(rows);
				    	param.price = $('#combo_price').numberbox('getValue');
				    	param.oriPrice = $("#combo_oriPrice").text();
				    	if(parseInt(param.price) > parseInt(param.oriPrice)){
				    		$.messager.alert("提示", "套餐定价不能大于原价!");
				    		return;
				    	}
			    		
			    		$('#combo_baseform').ajaxSubmit( {
			    			url : contextPath+"/menu/combo/update",
			    			dataType : "json",
			    			data: param,
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
					$.post("menu/combo/details",{comboId: row.productId},function(result){
						if (result.code == 0) {
							$('#combo_picture').validatebox({    
							    required: false,    
							    validType: ''   
							});
							initForm();
							var oriPrice = 0;//重新计算原价,避免单品价格更新导致原价变动
							var list = result.data;
							//属性赋值
							for ( var i in list) {
								list[i].attrPrice1 = 0;
								list[i].attrPrice2 = 0;
								var selectedAttrs = list[i].attrCmp.split("|");
								var attrTypes = list[i].product.productAttrTypes;
								for ( var j in attrTypes) {
									var attrs = attrTypes[j].productAttrs;
									var isSelected = false;
									for ( var k in attrs) {
										if (selectedAttrs.indexOf(attrs[k].attrId+"") != -1) {
											list[i]["attrPrice"+(j*1+1)] = attrs[k].attrPrice;
											isSelected = true;
										}
									}
									if (!isSelected) {
										list[i]["attrPrice"+(j*1+1)] = attrs[0].attrPrice;
									}
								}
								if (list[i].attrCmp != null && list[i].attrCmp != "") {
									var attrArr = list[i].attrCmp.split("|");
									for ( var j in attrArr) {
											var attrTypes = list[i].product.productAttrTypes[j];
											if (attrTypes != null) {
												var attrs = attrTypes.productAttrs;
												var isSelected = false;
												for ( var k in attrs) {
													if (attrs[k].attrId == attrArr[j]) {
														list[i]["attrPrice"+(j*1+1)] = attrs[k].attrPrice;
														isSelected = true;
													}
												}
												if (!isSelected) {
													list[i]["attrPrice"+(j*1+1)] = attrs[0].attrPrice;
												}
											}
									}
								}
								list[i].product.productId = list[i].productId;
								$.extend(true, list[i], list[i].product);
								list[i].attrType1 = list[i].product.productAttrTypes[0];
								list[i].attrType2 = list[i].product.productAttrTypes[1];
								list[i].total = (list[i].product.price + list[i].attrPrice1 + list[i].attrPrice2) * list[i].num;
								oriPrice += list[i].total;
							}
							//深拷贝
							var pList = $.extend(true, {}, list);
							var arr = [];
							
							for ( var i in pList) {
								arr.push(pList[i]);
							}
							$('#combo_package').datagrid('loadData', arr);
							//属性单选回显
							for ( var i in list) {
								if (list[i].product.productAttrTypes[0] != null) {
									$("[name='"+list[i].product.productAttrTypes[0].attrTypeId+"'][value='"+list[i].attrCmp.split("|")[0]+"']").attr("checked","checked");				
								}
								if (list[i].product.productAttrTypes[1] != null) {
									$("[name='"+list[i].product.productAttrTypes[1].attrTypeId+"'][value='"+list[i].attrCmp.split("|")[1]+"']").attr("checked","checked");				
								}
								//list[i].total = list[i].price * list[i].num;
							}
							//数据填充
							$("#combo_baseform").form('load', row);
							$("#combo_oriPrice").text(parseFloat(oriPrice).toFixed(2));
							tagsInit();
							if (row.inventory.inventory == "0") {
								$("#combo_soldout").attr('checked',true);
							}else if(row.inventory.inventory == "-1"){
								$("#combo_unlimited").attr('checked',true);
							}else{
								$("#combo_custom").attr('checked',true);
								$("#combo_inventory").numberbox("enable"); 
								$("#combo_inventory").numberbox('setValue', row.inventory.inventory);
							}
							$("#combo_img").attr('src', row.pictureLink).show();
							originalPicture = $("#combo_img").attr('src');
							$('#combo_pictureLink').val('');
							//$('#combo_picture').validatebox('disableValidation');
							$("#combo_productId, #combo_inventoryId").val(row.productId);
							$("#combo_baseform tt[optional='true']").remove();
						} else {
							$.messager.alert("提示", result.message);
						}
					}, "json");
					
				}
			}); 
		}else{
			$.messager.alert('提示', '请选择要修改的记录！', 'info');
		}
	}*/

	function del(){
		var row = $('#combo_pageList').datagrid("getSelected");
		if(row){
			$.messager.confirm('提示', '确定删除该记录?', function(r){
				if (r){
					var param = {};
					param.productId = row.productId;
					param.mchntCd = currentMchntCd;
					$.post("menu/combo/del",param,function(result){
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
		if ($("#combo_picture").val() == "") {
			$("#combo_img").attr('src', originalPicture);
    		$("#combo_pictureLink").val("");
			return;
		}
		if($("#combo_picture").validatebox('isValid') && $("#combo_picture").val() != ""){
			$('#combo_baseform').ajaxSubmit( {
    			url : "menu/combo/upload",
    			dataType : "json",
                success : function(result) {
					if (result.code == 0) {
						//图片回显
						$.messager.alert("提示", "图片上传成功");
	            		$("#combo_img").attr('src', result.data.pictureAddr).show();
	            		$("#combo_pictureLink").val(result.data.pictureLink);
					} else {
						$("#combo_picture").val("");
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
		//滚动条
		$("#comboForm").height($("#mainPanle").height());
		//图片居中
		var pHeight = $("#mainPanle").height();
		$("#combo_packages").height(pHeight);
		//表单赋值
		originalPicture = "images/default_menu.png";
		$("#combo_mchntCd").val(userInfo.mchntCd);
		$("#combo_select").click(select);
		//$("#combo_picture").change(upload);
		$("#combo_picture").click(toUpload);
		
		
		//按钮事件
		$("#combo_unlimited, #combo_soldout").change({status:0},function(event){
			changeInventoryInput(event.data.status);
		});
		$("#combo_custom").change({status:1},function(event){
			changeInventoryInput(event.data.status);
		});
		$("#combo_inventory").numberbox({onChange:function(newValue, oldValue){
			$("#combo_custom").val(newValue);
		}});
		
		$("#combo_back").click(back);
		//界面渲染
		$.parser.parse('#combo_form');
	}
	/**
	 * 库存输入框切换
	 */
	function changeInventoryInput(status){
		if (status == 1) {
			$("#combo_inventory").numberbox("enable"); 
			$('#combo_inventory').numberbox({    
			    required: true
			});
		}else if(status == 0){
			$("#combo_inventory").numberbox("disable"); 
			$('#combo_inventory').numberbox({    
			    required: false
			});
			$('#combo_inventory').numberbox('validate');
		}
	}
	function setOriPrice(num){
		var price = parseFloat($("#combo_oriPrice").text());
		$("#combo_oriPrice").text((price+num).toFixed(2));
	}
	
	function select(){
		var dlg = $('<div/>').dialog({    
		    title: '选择单品',    
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
		    		if(selectedNodes == null || selectedNodes.length == 0){
		    			$.messager.alert("提示", "至少选中1条数据");
		    			return;
		    		}
		    		//拼接参数
		    		var data = {};
		    		var menus = [];
		    		for ( var i in selectedNodes) {
		    			var node = selectedNodes[i];
		    			//只处理子节点
		    			if ($("#menu_tree").tree('isLeaf',node.target)) {
							var menu = node.attributes;
							menu.num = menu.num || 1;
							menu.attrType1 = menu.productAttrTypes[0];
							menu.attrType2 = menu.productAttrTypes[1];
							//根据属性处理单价，默认选中第一项
							if (menu.attrType1 != null && menu.attrType1.productAttrs != null) {
								menu.attrType1.productAttrs.sort(function(a, b){
									return a.attrPrice - b.attrPrice;
								});
								menu.attrPrice1 = parseFloat(menu.attrType1.productAttrs[0].attrPrice);
							}else{
								menu.attrPrice1 = 0;
							}
							if (menu.attrType2 != null && menu.attrType2.productAttrs != null) {
								menu.attrType2.productAttrs.sort(function(a, b){
									return a.attrPrice - b.attrPrice;
								});
								menu.attrPrice2 = parseFloat(menu.attrType2.productAttrs[0].attrPrice);
							} else {
								menu.attrPrice2 = 0;
							}
							//总价是计算完属性后的价格
							menu.total = (menu.price+menu.attrPrice1+menu.attrPrice2) * menu.num;
							//menu.allSpec = menu.specifications;
							//menu.allTaste = menu.taste;
							menus.push(menu);
						}
					}
		    		data.rows = menus;
		    		//赋值
		    		$('#combo_package').datagrid('loadData',data);
		    		for ( var i in menus) {
		    			if (menus[i].selectedAttr1 != null && menus[i].selectedAttr1 != "") {
		    				$("[name='"+menus[i].productAttrTypes[0].attrTypeId+"'][value='"+menus[i].selectedAttr1+"']").attr("checked","checked");				
						}
		    			if (menus[i].selectedAttr2 != null && menus[i].selectedAttr2 != "") {
		    				$("[name='"+menus[i].productAttrTypes[1].attrTypeId+"'][value='"+menus[i].selectedAttr2+"']").attr("checked","checked");	
		    			}
		    						
		    		}
		    		var total = 0;
		    		for ( var i in menus) {
		    			total += parseFloat(menus[i].total);
					}
		    		$("#combo_oriPrice").text(total.toFixed(2));
		    		$('#combo_price').numberbox('setValue', total.toFixed(2));
		    		
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
			checkbox:true
		}); 
		getAllMenuTypeTree(currentMchntCd);
		//根据套餐组合回显
		var rows = $('#combo_package').datagrid('getRows');        
    	if (!(rows == "" || rows.length == 0)) {
    		for ( var i in rows) {
    			var node = $('#combo_tree').tree('find', rows[i].productId);
    			node.attributes.num = rows[i].num;
    			if (rows[i].attrType1 != null && rows[i].attrType1 !="") {
    				node.attributes.selectedAttr1 = $("[name='"+rows[i].attrType1.attrTypeId+"']:checked").val();
				}
    			if (rows[i].attrType2 != null && rows[i].attrType2 !="") {
    				node.attributes.selectedAttr2 = $("[name='"+rows[i].attrType2.attrTypeId+"']:checked").val();
				}
    			
    			$('#combo_tree').tree('check', node.target);
			}
		}
		
	}
	/**
	 * 获取所有单品分类树
	 */
	function getAllMenuTypeTree(currentMchntCd){
		$.ajax({
			url : "menu/type/tree",
			data : {mchntCd:currentMchntCd, needCombo:false},
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
	
	
    return {
        init : init
    }
    
});