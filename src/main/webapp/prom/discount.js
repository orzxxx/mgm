//@ sourceUrl=discount.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	var menuTypes = {};
	var $tagInput;
	var paramId = "";
	var curMode = "000";//当前模式
	var discountParam = [{
	            	 discountMode: 1,
	            	 param1: "",
	            	 rules: []
	             },{
	            	 discountMode: 2,
	            	 param1: "",
	            	 rules: []
	             },{
	            	 discountMode: 3,
	            	 param1: "",
	            	 rules: []
	             }];
	
	function init(){
		initForm();
		initButton();
		
		getParam();
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
				changeReductionForm(data /10 % 10);
				changeAllForm(data /100 % 10);
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
        		discountParam = result.data;
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
				setDate("discount_menuForm", discountParam[2].param1);
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
		} else if (type == 1){
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
		} else if (type == 1){
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
			$("#discount_seletcMenus").linkbutton('disable');
			$("#discount_seletcMenus").unbind('click');
			$("#discount_mutex").attr('disabled', 'disabled');
			$("#discount_mutex").attr('checked', false);
		} else if (type == 1){
			$("#discount_menuSwitch").attr('checked', 'checked');
			$("#discount_menuDate").linkbutton('enable');
			$("#discount_menuDate").click(function(){
				toEditDate("discount_menuForm");
			});
			$("#discount_seletcMenus").linkbutton('enable');
			$("#discount_seletcMenus").click(toSelectMenus);
			$("#discount_mutex").attr('disabled', false);
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
	
	function initButton(){
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
	
	function toSelectMenus(){
		//个数校验
		var dlg = $('<div/>').dialog({    
		    title: '选择优惠单品(多选)',    
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
		    			$.messager.alert("提示", "请至少选择1个单品");
		    			return;
		    		}
		    		if(length > 20){
		    			$.messager.alert("提示", "一次最多选择20个单品");
		    			return;
		    		}
		    		//拼接参数
		    		discountParam[2].rules = [];
		    		$("#discount_menus tr:gt(0)").remove();
		    		for ( var i in selectedNodes) {
		    			var node = selectedNodes[i];
		    			//只处理子节点
		    			if ($("#combo_tree").tree('isLeaf',node.target)) {
		    				var menu = {};
							menu = node.attributes;
							if (!menu.param1) {
								menu.param1 = 0;
							}
							menu.param2 = menu.productId;
							$menuTr = $("<tr><td align='center'><span>"+menu.productName+"</span></td>"+
									"<td align='center'><span>"+convertMenutpId(menu.menutpId)+"</span></td>"+
				    				"<td align='center'><span>"+menu.price+"</span></td>"+
				    				"<td align='center'><span>" +
				    						"</span></td></tr>");
							$discount = $("<input type='text' name='discount' maxlength='8' class=\"easyui-numberbox\" data-options=\"min:0,max:99999.99,precision:2\" value='"+(menu.discount||0)+"'/>");
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
							$menuTr.find('td:eq(3) span').append($discount);
							$menuTr.append($delBtn);
							$("#discount_menus").append($menuTr);
							discountParam[2].rules.push(menu);
						}
					}
		    		$.parser.parse('#discount_menus');
		    		//赋值
		    		
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
			checkbox:true
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
		var menus = discountParam[2].rules;
		for ( var i in menus) {
			var node = $('#combo_tree').tree('find', menus[i].productId);
			if (node != null) {
				$('#combo_tree').tree('update', {
					target: node.target,
					text: menus[i].productName+"<img src='themes/icons/ok.png' style='width:14px;height:14px;border:0;'/>"
				});
				$('#combo_tree').tree('check', node.target);
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
			}
			if ($("#discount_reductionSwitch").attr('checked')) {
				m2 = "1";
			}
			if ($("#discount_menuSwitch").attr('checked')) {
				if ($("#discount_mutex").attr('checked')) {
					m3 = "2";
				} else {
					m3 = "1";
				}
			}
			var data = m1+m2+m3;
			var param = {};
			param.uuid = paramId;
			param.data = data;
			param.mchntCd = currentMchntCd;
			param.discountTimeJson = JSON.stringify(discountParam);
			$.post("prom/discount/add", param, function(result){
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
			if (discountParam[2].param1 == null || discountParam[2].param1 == "") {
				$.messager.alert("提示", "单品优惠时间未配置!");
				return false;
			}
			if (discountParam[2].rules == null || discountParam[2].rules.length == 0) {
				$.messager.alert("提示", "至少配置1个单品优惠!");
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
		var time = value.substring(8, 10)+":"+value.substring(10, 12)+":"+value.substring(12, 14);
		return date +" "+ time;
	}
	
    return {
        init : init
    };
    
});

	