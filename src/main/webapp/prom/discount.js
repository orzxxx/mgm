//@ sourceUrl=discount.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var menuTypes = {};
	var $tagInput;
	var paramId = "";
	var curMode = "000";//当前模式
	var discountParam = [];
	
	function init(){
		initVariables();
		initForm();
		initButton();
		initDatagrid();
		
		getParam();
		query();
	}
	
	function initVariables(){
		 paramId = "";
		 curMode = "000";
		discountParam = [{
		   	 discountMode: 1,
			 param1: "",
			 rules: []
		 },{
			 discountMode: 2,
			 param1: "",
			 rules: []
		 }];
	}
	
	function initDatagrid(){
		$('#discount_pageList').datagrid({
				fitColumns:false,
				fit:true,
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				pageList : [30],
				frozenColumns:[[
	                {field:'param1',title:'商品号',width:70,sortable:true}
				]],
				columns:[[
				    {field:'productName',title:'商品名',width:100,sortable:true,align:'center'},
				    {field:'menutpId',title:'单品分类',width:80,sortable:true,align:'center', formatter:function(value, rec){
						return convertMenutpId(value);
					    }},
				    {field:'price',title:'价格(元)',width:60,sortable:true,align:'center',formatter:function(value, rec){
					    return parseFloat(value).toFixed(2);
					}},
					{field:'dates',title:'有效日期',width:200,sortable:true,align:'center',formatter:function(value, rec){
						var datas = rec.time.split("|");
						if (datas.length == 3) {
							var startDate = datas[1].substring(0,4)+"-"+datas[1].substring(4,6)+"-"+datas[1].substring(6,8);
							var endDate = datas[2].substring(0,4)+"-"+datas[2].substring(4,6)+"-"+datas[2].substring(6,8);
							return startDate + "至" + startDate; 
						} else if (datas.length == 4){
							var week = "";
							var weeks = datas[1].split(/./);
							if (datas[1].indexOf("1") != -1) {
								week += "星期日,";
							}
							if (datas[1].indexOf("2") != -1) {
								week += "星期一,";
							}
							if (datas[1].indexOf("3") != -1) {
								week += "星期二,";
							}
							if (datas[1].indexOf("4") != -1) {
								week += "星期三,";
							}
							if (datas[1].indexOf("5") != -1) {
								week += "星期四,";
							}
							if (datas[1].indexOf("6") != -1) {
								week += "星期五,";
							}
							if (datas[1].indexOf("7") != -1) {
								week += "星期六,";
							}
							return week.substring(0, week.length-1);
						}
						return "";
					}},
					{field:'times',title:'有效时间段',width:150,sortable:true,align:'center',formatter:function(value, rec){
						var datas = rec.time.split("|");
						var startTime = "";
						var endTime = "";
						if (datas.length == 3) {
							startTime = datas[1].substring(8,10)+":"+datas[1].substring(10,12)+":"+datas[1].substring(12,14);
							endTime = datas[2].substring(8,10)+":"+datas[2].substring(10,12)+":"+datas[2].substring(12,14);
						} else if (datas.length == 4){
							startTime = datas[2].substring(0,2)+":"+datas[2].substring(2,4)+":"+datas[2].substring(4,6);
							endTime = datas[3].substring(0,2)+":"+datas[3].substring(2,4)+":"+datas[3].substring(4,6);
						}
						return startTime + "至" + endTime;
					}},
				    {field:'param2',title:'优惠所需份数',width:60,sortable:true,align:'center'},
				    {field:'param3',title:'优惠(元)',width:60,sortable:true,align:'center',formatter:function(value, rec){
					    return parseFloat(value).toFixed(2);
					}},
				    {field:'param4',title:'最大优惠(元)',width:60,sortable:true,align:'center',formatter:function(value, rec){
					    return parseFloat(value).toFixed(2);
					}},
				    {field:'param5',title:'优惠描述',width:100,sortable:true,align:'center',
						formatter : function(value, rec) {
							var info = value.length > 13 ? value.substring(0,13)+"..." : value;
							return "<span title=\""+value+".\" " +
									"class=\"easyui-tooltip\">"+info+"</span>";
						}}
				]],
				toolbar:"#discount_toolbar",
				onDblClickRow:function(index, row){
					edit();
				}
			});
		}
	
	function toMenu(){
		$('#discount_tab').tabs('enableTab', 1);   
		$('#discount_tab').tabs('select', 1);   
	}
	
	function getParam(){
		//查询模式配置
		var param = {};
		param.param = "discount_mode";
		param.mchntCd = currentMchntCd;
		$.post("sys/param/get", param, function(result){
			if (result.code == 0) {
				paramId = result.data.uuid;
				curMode = result.data.data;
				var data = Number(result.data.data);
				changeMenusForm(data % 10);
				changeReductionForm(Math.floor(data /10) % 10);
				changeAllForm(Math.floor(data /100) % 10);
			} else {
				$.messager.alert("提示", result.message);
			}
		}, "json");
		//查询模式配置参数
		$.post("prom/discount/get", {mchntCd: currentMchntCd}, function(result){
			if (result.code == 0) {
        		var data = result.data;
        		if (data == null || data.length == 0) {
					return;
				}
        		for ( var i in result.data) {
					if (result.data[i].discountMode == 1) {
						discountParam[0] = result.data[i];
					} else if (result.data[i].discountMode == 2) {
						discountParam[1] = result.data[i];
					}
				}
        		//discountParam = result.data;
        		//模式一赋值
				setDate("discount_allForm", discountParam[0].param1);
				$("#discount_rate").numberbox('setValue', (discountParam[0].rules[0].param1 || 0));
				//模式二赋值
				setDate("discount_reductionForm", discountParam[1].param1);
				var rules = discountParam[1].rules;
				//排序
				rules.sort(function(a, b){
					return b.param1 - a.param1;
				});
				for ( var i in rules) {
					$tagInput.addTag("满"+rules[i].param1+"减"+rules[i].param2, {
						removable: true,
						callback: function(){
							var param = $.trim($(this).prev('span').text()).match(/^满([\d\.]+)减([\d\.]+)$/)[1];
							for ( var j in rules) {
								if (rules[j].param1 == param) {
									rules.splice(j, 1);
									break;
								}
							}
						}
					});
				}
				//模式三赋值
				/*if (discountParam.length > 2) {
					var discountMenus = [];
					//setDate("discount_menuForm", discountParam[2].param1);
					for ( var i = 2; i < discountParam.length; i++) {
						discountMenus.push($.extend({}, discountParam[i] ,discountParam[i].rules));
					}
					$('#discount_pageList').datagrid('load',data); 
				}*/
				
			} else {
				$.messager.alert("提示", result.message);
			}
		}, "json");
	}
	
	/*模式一 整体折扣
	 * 模式二 满减优惠
	 * 模式三 单品优惠
	 */
	function changeMode(mode){
		if (mode == 1) {
			if ($("#discount_allSwitch").attr('checked')) {
				changeAllForm(1);
				changeReductionForm(0);
			}else{
				changeAllForm(0);
			}
			
		} else if (mode == 2){
			if ($("#discount_reductionSwitch").attr('checked')) {
				changeAllForm(0);
				changeReductionForm(1);
			}else{
				changeReductionForm(0);
			}
			
		} else {
			if ($("#discount_menuSwitch").attr('checked')) {
				changeMenusForm(1);
			}else{
				changeMenusForm(0);
			}
			
		}
	}
	
	/**
	 * 0不可 1可编辑
	 */
	function changeAllForm(type){
		if (type == 0) {
			$("#discount_allSwitch").attr('checked', false);
			$("#discount_allDate").linkbutton('disable');
			$("#discount_allDate").unbind('click');
			$("#discount_rate").attr('disabled', 'disabled');
		} else if (type == 1 || type == 2){
			$("#discount_allSwitch").attr('checked', 'checked');
			$("#discount_allDate").linkbutton('enable');
			$("#discount_allDate").click(function(){
				toEditDate("discount_allForm");
			});
			$("#discount_rate").attr('disabled', false);
		}
	}
	
	/**
	 * 0不可 1可编辑
	 */
	function changeReductionForm(type){
		if (type == 0) {
			$("#discount_reductionSwitch").attr('checked', false);
			$("#discount_reductionDate").linkbutton('disable');
			$("#discount_reductionDate").unbind('click');
			$("#discount_addReduction").linkbutton('disable');
			$("#discount_addReduction").unbind('click');
			//标签不可移除
			var tagId = "#"+$tagInput.attr('id')+"_tagsinput";
			$(tagId).find('a').hide();
		} else if (type == 1 || type == 2){
			$("#discount_reductionSwitch").attr('checked', 'checked');
			$("#discount_reductionDate").linkbutton('enable');
			$("#discount_reductionDate").click(function(){
				toEditDate("discount_reductionForm");
			});
			$("#discount_addReduction").linkbutton('enable');
			$("#discount_addReduction").click(toAddReduction);
			//标签可移除
			var tagId = "#"+$tagInput.attr('id')+"_tagsinput";
			$(tagId).find('a').show();
		}
	}
	
	/**
	 * 0不可 1可编辑 2可且共存
	 */
	function changeMenusForm(type){
		if (type == 0) {
			$("#discount_menuSwitch").attr('checked', false);
			$("#discount_menuDate").linkbutton('disable');
			$("#discount_menuDate").unbind('click');
			$("#discount_toMenu").linkbutton('disable');
			$("#discount_toMenu").unbind('click');
			$("#discount_mutex").attr('disabled', 'disabled');
			$("#discount_mutex").attr('checked', false);
			$('#discount_tab').tabs('disableTab', 1);
		} else if (type == 1){
			$("#discount_menuSwitch").attr('checked', 'checked');
			$("#discount_menuDate").linkbutton('enable');
			$("#discount_menuDate").click(function(){
				toEditDate("discount_menuForm");
			});
			$("#discount_toMenu").linkbutton('enable');
			$("#discount_toMenu").click(toMenu);
			$("#discount_mutex").attr('disabled', false);
			$('#discount_tab').tabs('enableTab', 1);            
		} else if (type == 2){
			changeMenusForm(1);
			$("#discount_mutex").attr('checked', 'checked');
		}
	}
	
	function initForm(){
		$("tr[type='time']").hide();
		
		getAllMenuType(currentMchntCd);
		
		$tagInput = $("#discount_reductions").tagsInput({
			'height':'100px',
			'width':'500px',
			'defaultText':'',
			'interactive':false,
			'removable': false
		});
		
		//默认全部不可选
		changeAllForm(0);
		changeReductionForm(0);
		changeMenusForm(0);
	}
	
	function initMenuForm(){
		$("#discountMenu_select").click(toSelectMenus);
		//$("#discountMenu_date").load(contextPath+"/prom/discount_date.jsp");
		
	}
	
	function initButton(){
		$("#discount_add").click(add);
		$("#discount_del").click(del);
		$("#discount_edit").click(edit);
		$("#discount_toMenu").click(toMenu);
		/*$("#discount_allDate").click(function(){
			toEditDate("discount_allForm");
		});
		$("#discount_reductionDate").click(function(){
			toEditDate("discount_reductionForm");
		});
		$("#discount_menuDate").click(function(){
			toEditDate("discount_menuForm");
		});
		$("#discount_addReduction").click(toAddReduction);
		$("#discount_seletcMenus").click(toSelectMenus);*/
		
		$("#discount_save").click(save);
		
		$("#discount_allSwitch").click(function(){
			changeMode(1);
		});
		$("#discount_reductionSwitch").click(function(){
			changeMode(2);
		});
		$("#discount_menuSwitch").click(function(){
			changeMode(3);
		});
		
	}
	
	function edit(){
		var row = $('#discount_pageList').datagrid("getSelected");
		if(row){
			var dlg = $('<div/>').dialog({    
			    title: '编辑优惠单品',    
			    width: 520,    
			    height: 500,    
			    closable: false,    
			    cache: false,    
			    href: 'prom/discount_menu.jsp',    
			    modal: true,
			    buttons : [ {
					text : '保存',
					handler : function(){
						if($('#discountMenu_form').form("validate")){
				    		requirejs(['discount-date'],function  (date) {
								if (!date.getValue()) {
									$('#discountMenu_tab').tabs('select', '有效时间');
									return;
								}
								var discount = $("#discountMenu_form input[name='param3']").val();
								var num = $("#discountMenu_form input[name='param2']").val();
								var price = $("#discountMenu_form input[name='price']").val();
								var maxDiscount = $("#discountMenu_form input[name='param4']").val();
								if (discount > num*price) {
									$.messager.alert("提示", "优惠不得大于【"+num*price+"】!(价格X份数)");
									return;
								}
								if (discount > maxDiscount) {
									$.messager.alert("提示", "最大优惠不得小于优惠【"+discount+"】");
									return;
								}
								
								var param = {};
								param.id = row.id;
					    		param.param1 = date.getValue();
					    		param.param2 = $("#discountMenu_productId").val();
					    		param.discountMode = 3;
					    		param.mchntCd = currentMchntCd;
					    		var rule = {};
					    		rule.id = row.r_id;
					    		rule.mchntCd = currentMchntCd;
					    		rule.discountMode = 3;
					    		rule.param1 = $("#discountMenu_productId").val();
					    		rule.param2 = $("#discountMenu_form input[name='param2']").val();
					    		rule.param3 = $("#discountMenu_form input[name='param3']").val();
					    		rule.param4 = $("#discountMenu_form input[name='param4']").val();
					    		rule.param5 = $("#discountMenu_form textarea[name='param5']").val();
					    		param.discountRuleJson = JSON.stringify([].concat(rule));
					    		$.post("prom/discount/update", param, function(result){
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
								}, "json");
							});
				    	} else {
				    		$('#discountMenu_tab').tabs('select', '基础信息');
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
					requirejs(['discount-date'],function  (date) {
						initMenuForm();
						date.setValue(row.time);
						//数据填充
						var data = $.extend(true, {}, row);
						data.menutpName = convertMenutpId(row.menutpId);
						data.price = Number(data.price).toFixed(2);
						$("#discountMenu_form").form('load', data);
						$("#discountMenu_select").hide();
					});
				}
			}); 
		}else{
			$.messager.alert('提示', '请选择要修改的记录！', 'info');
		}
	}
	
	function del(){
		var row = $('#discount_pageList').datagrid("getSelected");
		if(row){
			$.messager.confirm('提示', '确定删除该记录?', function(r){
				if (r){
					var param = {};
					param.param2 = row.param1;
					param.mchntCd = currentMchntCd;
					$.post("prom/discount/del",param,function(result){
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
	
	function query(){
			var param = {};
			param.mchntCd = currentMchntCd;
			
			$.post("prom/discount/list",param,function(result){
				if (result.code == 0) {
					var data = result.data;
					for ( var i in data) {
						var tempId = data[i].id;
						data[i].time = data[i].param1;
						data[i] = $.extend({}, data[i], data[i].rules[0], data[i].rules[0].product);
						data[i].r_id = data[i].id;
						data[i].id = tempId;
						data[i].productId = data[i].param1;
					}
					$("#discount_total").text(data.length);
            		$('#discount_pageList').datagrid('loadData',data);  
				} else {
					$.messager.alert("提示", result.message);
				}
			}, "json");
}
	
	function toAddReduction(){
		//最大个数校验
		var rules = discountParam[1].rules;
		if (rules.length >= 10) {
			$.messager.alert("提示", "最多配置10个满减优惠");
			return;
		}
		var dlg = $('<div/>').dialog({    
		    title: '添加满减优惠',    
		    width: 520,    
		    height: 220,    
		    closable: false,    
		    cache: false,    
		    href: 'prom/discount_reduction.jsp',    
		    modal: true,
		    buttons : [ {
				text : '保存',
				handler :function(){
					requirejs(['discount-reduction'],function  (reduction) {
						if (!reduction.getValue()) {
							return;
						}
						var data = reduction.getValue();
						var rules = discountParam[1].rules;
						//重复校验
						var flag = false;
						for ( var i in rules) {
							if (rules[i].param1 == data.total) {
								var msg = "已存在优惠【满"+rules[i].param1+"减"+rules[i].param2+"】,是否覆盖?";
								if (confirm(msg)) {
									rules[i].param2 = data.benefit;
									flag = true;
									break;
								} else {
									return;
								}
							}
						}
						if (!flag) {
							var param = {};
							param.discountMode = 2;
							param.param1 = data.total;
							param.param2 = data.benefit;
							rules.push(param);
						}
						//排序
						rules.sort(function(a, b){
							return b.param1 - a.param1;
						});
						$tagInput.importTags('');
						for ( var i in rules) {
							$tagInput.addTag("满"+rules[i].param1+"减"+rules[i].param2, {
								removable: true,
								callback: function(){
									var param = $.trim($(this).prev('span').text()).match(/^满(\d+)减(\d+)$/)[1];
									for ( var j in rules) {
										if (rules[j].param1 == param) {
											rules.splice(j, 1);
											break;
										}
									}
								}
							});
						}
						
						dlg.dialog('close');
			    		dlg.remove();
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
				
			}
		}); 
	}
	
	function add(){
		var rows = $('#discount_pageList').datagrid('getRows');
		if (rows >= 20) {
			$.messager.alert("提示", "最多只能添加20个优惠单品");
			return;
		}
		var dlg = $('<div/>').dialog({    
		    title: '添加优惠单品',    
		    width: 520,    
		    height: 500,    
		    closable: false,    
		    cache: false,    
		    href: 'prom/discount_menu.jsp',    
		    modal: true,
		    buttons : [ {
				text : '添加',
				handler :function(){
		    	if($('#discountMenu_form').form("validate")){
		    		if($("#discountMenu_productName").val() == ""){
		    			$.messager.alert("提示", "请选择优惠单品");
		    			return;
		    		}
		    		requirejs(['discount-date'],function  (date) {
						if (!date.getValue()) {
							$('#discountMenu_tab').tabs('select', '有效时间');
							return;
						}
						var discount = $("#discountMenu_form input[name='param3']").val();
						var num = $("#discountMenu_form input[name='param2']").val();
						var price = $("#discountMenu_form input[name='price']").val();
						var maxDiscount = $("#discountMenu_form input[name='param4']").val();
						if (discount > num*price) {
							$.messager.alert("提示", "优惠不得大于【"+num*price+"】!(价格X份数)");
							return;
						}
						if (discount > maxDiscount) {
							$.messager.alert("提示", "最大优惠不得小于优惠【"+discount+"】");
							return;
						}
						
						var param = {};
			    		param.param1 = date.getValue();
			    		param.param2 = $("#discountMenu_productId").val();
			    		param.discountMode = 3;
			    		param.mchntCd = currentMchntCd;
			    		var rule = {};
			    		rule.mchntCd = currentMchntCd;
			    		rule.discountMode = 3;
			    		rule.param1 = $("#discountMenu_productId").val();
			    		rule.param2 = $("#discountMenu_form input[name='param2']").val();
			    		rule.param3 = $("#discountMenu_form input[name='param3']").val();
			    		rule.param4 = $("#discountMenu_form input[name='param4']").val();
			    		rule.param5 = $("#discountMenu_form textarea[name='param5']").val();
			    		param.discountRuleJson = JSON.stringify([].concat(rule));
			    		$.post("prom/discount/add", param, function(result){
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
						}, "json");
					});
		    	} else {
		    		$('#discountMenu_tab').tabs('select', '基础信息');
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
				initMenuForm();
			}
		});  
	}
	
	function toSelectMenus(){
		//个数校验
		var dlg = $('<div/>').dialog({    
		    title: '选择优惠单品(单选)',    
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
		    		//discountParam[2].rules = [];
		    		//$("#discount_menus tr:gt(0)").remove();
		    		for ( var i in selectedNodes) {
		    			var node = selectedNodes[i];
		    			//只处理子节点
		    			if ($("#combo_tree").tree('isLeaf',node.target)) {
		    				
							menu = node.attributes;
							var pNode = $("#combo_tree").tree('getParent',node.target);
							menu.menutpName = pNode.attributes.menutpName;
							/*if (!menu.param1) {
								menu.param1 = 0;
							}
							menu.param2 = menu.productId;
							$menuTr = $("<tr><td align='center'><span>"+menu.productName+"</span></td>"+
									"<td align='center'><span>"+convertMenutpId(menu.menutpId)+"</span></td>"+
				    				"<td align='center'><span>"+menu.price+"</span></td>"+
				    				"<td align='center'><span></span></td>"+
				    				"<td align='center'><span></span></td></tr>");
							$discount = $("<input style='width:80px;' type='text' name='discount' maxlength='8' class=\"easyui-numberbox\" " +
									"data-options=\"min:0,max:99999.99,precision:2\" value='"+(menu.discount||0)+"'/>");
							$num = $("<input style='width:60px;' type='text' name='num' maxlength='2' class=\"easyui-numberbox\" " +
									"data-options=\"min:0,max:99,precision:0\" value='"+(menu.num||0)+"'/>");
							$delBtn = $("<td align='center'><img src='themes/icons/edit_remove.png'/></td>");
							
							$delBtn.click((function(productId, $menuTr){
								return function(){
									$.messager.confirm('提示', '确定移除该优惠单品?', function(r){
			            				if (r){
			            					var rules = discountParam[2].rules;
			        						for ( var i in rules) {
			        							if (rules[i].productId == productId) {
			        								rules.splice(i, 1);
			        								$menuTr.remove();
			        							}
			        						}
			            				}
			            			});
								};
							})(menu.productId, $menuTr));
							$menuTr.find('td:eq(3) span').append($num);
							$menuTr.find('td:eq(4) span').append($discount);
							$menuTr.append($delBtn);
							$("#discount_menus").append($menuTr);
							discountParam[2].rules.push(menu);*/
						}
					}
		    		//$.parser.parse('#discount_menus');
		    		//赋值
		    		$("#discountMenu_form").form('load', menu);
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
					if (result.data == null || result.data.length == 0) {
						$.messager.alert("提示", "没有单品数据, 请先添加！");
						return;
					}
					setMenuTree(result.data);
				} else {
					$.messager.alert("提示", result.message);
				}
			}});
	}
	
	function setMenuTree(data){
		var treeData = getTreeDate(data);
		$("#combo_tree").tree('loadData', treeData);
		//已添加回显
		var menus = $('#discount_pageList').datagrid('getRows');
		for ( var i in menus) {
			var node = $('#combo_tree').tree('find', menus[i].param1);
			if (node != null) {
				$('#combo_tree').tree('update', {
					target: node.target,
					text: menus[i].productName+"<img src='themes/icons/ok.png' style='width:14px;height:14px;border:0;'/>"
				});
				//$('#combo_tree').tree('check', node.target);
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
	
	function toEditDate(id){
		var dlg = $('<div/>').dialog({    
		    title: '修改时间',    
		    width: 520,    
		    height: 420,    
		    closable: false,    
		    cache: false,    
		    href: 'prom/discount_date.jsp',    
		    modal: true,
		    buttons : [ {
				text : '保存',
				handler :function(){
					requirejs(['discount-date'],function  (date) {
						//param1 = date.getValue();
						if (!date.getValue()) {
							return;
						}
						if (id == "discount_allForm") {
							discountParam[0].param1 = date.getValue();
						} else if (id == "discount_reductionForm"){
							discountParam[1].param1 = date.getValue();
						} else {
							discountParam[2].param1 = date.getValue();
						}
						setDate(id, date.getValue());
						dlg.dialog('close');
			    		dlg.remove();
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
				requirejs(['discount-date'],function  (date) {
					if (id == "discount_allForm") {
						date.setValue(discountParam[0].param1);
					} else if (id == "discount_reductionForm"){
						date.setValue(discountParam[1].param1);
					} else {
						date.setValue(discountParam[2].param1);
					}
				});
			}
		}); 
	}
	
	function setDate(id, value){
		if (value == null || value == "") {
			return false;
		}
		var values = value.split("|");
		if (values[0] == "1") {
			$("#"+id+" tr[type='date']").show();
			$("#"+id+" input[name='startDate']").val(convertDateTime(values[1]));
			$("#"+id+" input[name='endDate']").val(convertDateTime(values[2]));
			$("#"+id+" input[name='startTime']").val(convertTime(values[1].substring(8, 14)));
			$("#"+id+" input[name='endTime']").val(convertTime(values[2].substring(8, 14)));
			$("#"+id+" tr[type='date']").show();
			$("#"+id+" tr[type='time']").hide();
		} else {
			$("#"+id+" input[name='startTime']").val(convertTime(values[2]));
			$("#"+id+" input[name='endTime']").val(convertTime(values[3]));
			$("#"+id+" input:checkbox[name='week']").each(function(){
				if (values[1].indexOf($(this).val()) != -1) {
					$(this).attr('checked', 'checked');
				}else{
					$(this).attr('checked', false);
				}
			});
			$("#"+id+" tr[type='date']").hide();
			$("#"+id+" tr[type='time']").show();
		}
	}
	
	
	function save(){
		if (check()) {
			var m1 = "0";
			var m2 = "0";
			var m3 = "0";
			if ($("#discount_allSwitch").attr('checked')) {
				m1 = "1";
				m2 = "0";
			}
			if ($("#discount_reductionSwitch").attr('checked')) {
				m2 = "1";
				m1 = "0";
			}
			if ($("#discount_menuSwitch").attr('checked')) {
				if ($("#discount_mutex").attr('checked')) {
					m3 = "1";
				} else {
					m3 = "2";
				}
			}
			var data = m1+m2+m3;
			var param = {};
			param.uuid = paramId;
			param.data = data;
			param.mchntCd = currentMchntCd;
			var timeForSave = [];//排除折扣3
			if (discountParam[0] != null) {
				timeForSave[0] = discountParam[0];
			}
			if (discountParam[1] != null) {
				timeForSave[1] = discountParam[1];
			}
			param.discountTimeJson = JSON.stringify(timeForSave);
			$.post("prom/discount/save", param, function(result){
				if (result.code == 0) {
					$.messager.alert("提示", "保存成功!");
				} else {
					$.messager.alert("提示", result.message);
				}
			}, "json");
		}
	}

	function check(){
		if ($("#discount_allSwitch").attr('checked')) {
			if (discountParam[0].param1 == null || discountParam[0].param1 == "") {
				$.messager.alert("提示", "整体折扣时间未配置!");
				return false;
			}
			var rate = $("#discount_rate").val();
			if ($.trim(rate) == "") {
				$.messager.alert("提示", "折扣率不得为空!");
				return false;
			}
			if (rate < 0.1) {
				$.messager.alert("提示", "折扣率不得小于0.1!");
				return false;
			} else if (rate > 10) {
				$.messager.alert("提示", "折扣率不得大于10!");
				return false;
			}
		}else if ($("#discount_reductionSwitch").attr('checked')) {
			if (discountParam[1].param1 == null || discountParam[1].param1 == "") {
				$.messager.alert("提示", "满减优惠时间未配置!");
				return false;
			}
			if (discountParam[1].rules == null || discountParam[1].rules.length == 0) {
				$.messager.alert("提示", "至少配置1个满减优惠!");
				return false;
			}
		}
		if ($("#discount_menuSwitch").attr('checked')) {
			/*if (discountParam[2].param1 == null || discountParam[2].param1 == "") {
				$.messager.alert("提示", "单品优惠时间未配置!");
				return false;
			}*/
			var rows = $('#discount_pageList').datagrid('getRows');
			if (rows == null || rows.length == 0) {
				$.messager.alert("提示", "至少配置1个优惠单品!");
				return false;
			}
		}
		discountParam[0].rules = [].concat({param1: $("#discount_rate").val(), discountMode: 1});
		return true;
	}
	
	function convertTime(value){
		return value.substring(0, 2)+":"+value.substring(2, 4)+":"+value.substring(4, 6);
	}
	
	function convertDateTime(value){
		var date = value.substring(0, 4)+"-"+value.substring(4, 6)+"-"+value.substring(6, 8);
		//var time = value.substring(8, 10)+":"+value.substring(10, 12)+":"+value.substring(12, 14);
		return date;
	}
	
    return {
        init : init
    };
    
});

	