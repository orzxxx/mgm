//@ sourceUrl=combo_packages.js
define(function () {
	var attrArr = {};
	var curAttr = "";
	var curAttrVal = "";
	var curPruchaseVal = "";
	var i = 0;
	
	function init(){
		attrArr = {};
		$("#comboPkg_addAttr").click(addNewAttr);
		//test
		/*$("#comboPkg_addAttr").parent().append("<input id='test' type='button'/>");
		$("#test").click(function(){
			alert(getAttrArr());
		});
		var data = [{
			attrTypeName: "a1",
			productAttrs: [
			        {attrName:'v1', attrPrice: '20'},
			        {attrName:'v2', attrPrice: '-20'},
			        {attrName:'v3', attrPrice: '120'}
			]},
			{
				attrTypeName: "a2",
				productAttrs: [
				        {attrName:'v4', attrPrice: '220'},
				        {attrName:'v5', attrPrice: '-240'},
				        {attrName:'v6', attrPrice: '20'}
				]},
		];
		loadAttr(data);*/
	}
	//添加组合配置
	function addNewAttr(){
		var newAttr = $("#comboPkg_newAttr").val();
		if (newAttr == null || $.trim(newAttr) == "") {
			$.messager.alert("提示", "请输入新组合配置");
			return;
		}
		if (newAttr.replace(/[^\x00-\xff]/g,"aa").length > 16) {
			$.messager.alert("提示", "组合配置长度不得大于16个字符");
			return;
		}
		var count = 0;
		for ( var i in attrArr) {
			if (++count >= 10) {
				$.messager.alert("提示", "最多添加10个组合配置");
				return;
			}
		}
		if (attrArr[newAttr] != null) {
			$.messager.alert("提示", "组合配置不能重复");
			return;
		}
		//添加
		insertNewAttr(newAttr);
		$("#comboPkg_newAttr").val("");
		//界面渲染
		$.parser.parse('#comboPkg_form');
	}
	//添加新组合类型
	function addNewAttrValue(div, attr, val, price, productAttrTypes){
		var length = $(div).find("table:eq(0) tr").length-1;
		if (length >= 10) {
			$.messager.alert("提示", "最多添加10个组合类型");
			return;
		}
		if (attr.replace(/[^\x00-\xff]/g,"aa").length > 16) {
			$.messager.alert("提示", "组合类型长度不得大于16个字符");
			return;
		}
		//插入
		var $ele = createNewAttrValue(attr, val, price);
		$(div).find("table:eq(0)").append($ele);
		$.parser.parse('#comboPkg_form');
		$(div).find("table:eq(0) tr:last td:eq(0) input").focus();
		//tag初始化
		$tagInput = $(div).find("table:eq(0) tr:last td:eq(2) input:eq(2)");
		$tagInput.tagsInput({
			'height':'35px',
			'width':'100px',
			'defaultText':'',
			'interactive':false,
			'removable': false
		});
		addTag($tagInput, productAttrTypes);
		//添加数据
		var attrVal = {};
		attrVal.singleName = val;
		attrVal.singlePrice = price;
		attrVal.exchangeProductFlag = 0;
		attrVal.productAttrTypes = productAttrTypes;
		attrArr[attr][i++] = attrVal;
	}
	//添加新换购单品
	function addNewPurchase(div, attr, val, price, productAttrTypes){
		var length = $(div).find("table:eq(0) tr").length-1;
		if (length >= 10) {
			$.messager.alert("提示", "最多添加10个换购单品");
			return;
		}
		//插入
		var $ele = createNewPurchase(attr, val, price);
		$(div).find("table:eq(0)").append($ele);
		$.parser.parse('#comboPkg_form');
		$(div).find("table:eq(0) tr:last td:eq(1) input").focus();
		//tag初始化
		$tagInput = $(div).find("table:eq(0) tr:last td:eq(2) input:eq(2)");
		$tagInput.tagsInput({
			'height':'35px',
			'width':'100px',
			'defaultText':'',
			'interactive':false,
			'removable': false
		});
		addTag($tagInput, productAttrTypes);
		//添加数据
		var purchase = {};
		purchase.singleName = val;
		purchase.singlePrice = price;
		purchase.exchangeProductFlag = 1;
		purchase.productAttrTypes = productAttrTypes;
		attrArr[attr][i++] = purchase;
	}
	//
	function createNewAttr(attr){
		var $ele = $("<div style='float:left;width:400px;clear:both;'>"+
			"<table>"+
				"<tr>"+
					"<td style=\"width:400px;\">"+
						"<label>组合配置:</label> "+
					"</td>"+
					"<td class=\"tdspace\">"+
					"</td>"+
					"<td style=\"width:400px;\">"+
						
					"</td>"+
					"<td class=\"hintspace\" style='width:70px;'></td>"+		
				"</tr>"+
			"</table>"+
		"</div>");
		
		var $addBtn = $("<a href=\"javascript:void(0)\" style=\"width:100px;\" type='addAttr' class=\"easyui-linkbutton ct-rst-btn\" iconCls=\"icon-add\" plain=\"true\">添加组合类型</a>");
		var $delBtn = $("<a href=\"javascript:void(0)\" class=\"easyui-linkbutton ct-rst-btn\" iconCls=\"icon-remove\" plain=\"true\">删除</a>");
		
		var $attr = $("<input value='"+attr+"' type=\"text\" maxlength=\"16\" class=\"easyui-validatebox\" style=\"width:120px;\" data-options=\"required:true,validType:'realLength[16]'\"/>");
		
		$attr.focus(function(){
			curAttr = $attr.val();
		});
		
		$attr.blur(function(){
			//如果为空字符串则还原当前值
			if ($.trim($(this).val()) == "") {
				$(this).val(curAttr).validatebox('validate').focus();
				return;
			};
			//值不变
			if (curAttr == $(this).val()) {
				return;
			}
			$(this).validatebox('validate');
			//校验
			if (attrArr[$(this).val()] != null) {
				$.messager.alert("提示", "组合配置不能重复");
				//值还原
				$(this).val(curAttr);
				return;
			}
			changeAttr($(this).val());
		});
		
		$addBtn.click(function(){
			addNewAttrValue($ele, $attr.val(), '', 0, []);
		});
		
		$ele.find("td:eq(0)").append($attr);
		$ele.find("td:eq(2)").append($addBtn).append(" ").append($delBtn);
		
		//换购窗口
		var $purchase = $("<div style='float:left;width:400px'>"+
				"<table>"+
					"<tr>"+
						"<td style=\"width:400px;\">"+
							"<label>换购:</label> "+
						"</td>"+
						"<td class=\"tdspace\">"+
						"</td>"+
						"<td style=\"width:400px;\">"+
							
						"</td>"+
						"<td class=\"hintspace\" style='width:70px;'></td>"+		
					"</tr>"+
				"</table>"+
			"</div>");
		
		var $pBtn = $("<a href=\"javascript:void(0)\" style=\"width:100px;\" class=\"easyui-linkbutton ct-rst-btn\" iconCls=\"icon-add\" plain=\"true\">添加换购单品</a>");
		
		$pBtn.click(function(){
			addNewPurchase($purchase, $attr.val(), '', 0, []);
		});
		
		$purchase.find("td:eq(0)").append($pBtn);
		
		var $win = $("<div><div></div></div>");
		$win.find("div:eq(0)").append($ele).append($purchase);
		$win.append("<hr style='clear:both;'/>");
		
		
		$delBtn.click(function(){
			removeAttr($attr.val());
			$win.remove();
		});
		
		return $win;
	}
	
	function createNewPurchase(attr, val, price){
		var $ele = $("<tr>"+
			"<td style=\"width:400px;text-align: right;\">"+
			"</td>"+
			"<td class=\"tdspace\">"+
			"</td>"+
			"<td style=\"width:400px;\">"+
				"<label>价格:</label> "+
			"</td>"+
			"<td class=\"hintspace\" style='width:70px;'></td>"+				
			"</tr>"
		);
		
		var $attrVal = $("<input count='"+i+"' type=\"text\" value='"+val+"' maxlength=\"16\" class=\"easyui-validatebox\" data-options=\"required:true,validType:'realLength[16]'\" style=\"width:100px;\"/>");
		var $priceVal = $("<input count='"+i+"' type=\"text\" value='"+price+"' maxlength=\"8\" class=\"easyui-numberbox\" min='-9999.99' max='9999.99' precision='2' style=\"width:60px;\"/> ");
		var $delBtn = $("<a href=\"javascript:void(0)\" class=\"easyui-linkbutton ct-rst-btn\" iconCls=\"icon-remove\" plain=\"true\">删除</a>");
		var n = i;
		
		$attrVal.focus(function(){
			curPruchaseVal = $attrVal.val();
		});
		
		$attrVal.blur(function(){
			//如果为空字符串则还原当前值
			if ($.trim($(this).val()) == "") {
				$(this).val(curPruchaseVal).validatebox('validate');//.focus()
				return;
			};
			//值不变
			if (curPruchaseVal == $(this).val()) {
				return;
			}
			if ($(this).val().replace(/[^\x00-\xff]/g,"aa").length > 16) {
				$.messager.alert("提示", "换购单品长度不得大于16个字符");
				return;
			}
			$(this).validatebox('validate');
			var n = $(this).attr('count');
			var pAttr = getAttrByPurchase($(this));
			for ( var m in attrArr[pAttr]) {
				if (attrArr[pAttr][m].singleName == $(this).val()){
					$.messager.alert("提示", "换购单品不能重复");
					//值还原
					$(this).val(curPruchaseVal);
					return;
				}
			}
			attrArr[pAttr][n].singleName = $(this).val();
		});
		
		$priceVal.blur(function(){
			if ($.trim($(this).val()) == "") {
				//$(this).numberbox('validate').focus();
				return;
			};
			var n = $(this).attr('count');
			var pAttr = getAttrByPurchase($(this));
			attrArr[pAttr][n].singlePrice = $(this).val();
		});
		
		$delBtn.click(function(){
			var pAttr = getAttrByPurchase($(this));
			removeAttrValue(pAttr, n);
			$ele.remove();
		});
		
		//属性配置
		var $attrBtn = $("<a href=\"javascript:void(0)\" style='width:80px;' class=\"easyui-linkbutton ct-rst-btn\" iconCls=\"icon-edit\" plain=\"true\">属性配置</a>");
		var $attrCfg = $("<input count='"+i+"' type=\"text\" style=\"width:100px;\"/>");
		
		$attrBtn.click(function(){
			attrEditForPurchase($attrCfg);
		});
		
		$ele.find("td:eq(0)").append($attrVal);
		$ele.find("td:eq(2)").append($priceVal).append(" ").append($delBtn)
			.append($attrCfg).append($attrBtn);
		
		return $ele;
	}
	
	function createNewAttrValue(attr, val, price){
		var $ele = $("<tr>"+
			"<td style=\"width:400px;text-align: right;\">"+
			"</td>"+
			"<td class=\"tdspace\">"+
			"</td>"+
			"<td style=\"width:400px;\">"+
				"<label style='display:none;'>价格:</label> "+
			"</td>"+
			"<td class=\"hintspace\" style='width:70px;'></td>"+				
			"</tr>"
		);
		
		var $attrVal = $("<input count='"+i+"' type=\"text\" value='"+val+"' maxlength=\"16\" class=\"easyui-validatebox\" data-options=\"required:true,validType:'realLength[16]'\" style=\"width:100px;\"/>");
		var $priceVal = $("<input count='"+i+"' style='display:none;' type=\"text\" value='"+price+"' maxlength=\"7\" class=\"easyui-numberbox\" min='0' max='9999.99' precision='2' style=\"width:60px;\"/> ");
		var $delBtn = $("<a href=\"javascript:void(0)\" class=\"easyui-linkbutton ct-rst-btn\" iconCls=\"icon-remove\" plain=\"true\">删除</a>");
		var n = i;
		
		$attrVal.focus(function(){
			curAttrVal = $attrVal.val();
		});
		
		$attrVal.blur(function(){
			//如果为空字符串则还原当前值
			if ($.trim($(this).val()) == "") {
				$(this).val(curAttrVal).validatebox('validate');//.focus()
				return;
			};
			//值不变
			if (curAttrVal == $(this).val()) {
				return;
			}
			$(this).validatebox('validate');
			var n = $(this).attr('count');
			var pAttr = getAttr($(this));
			for ( var m in attrArr[pAttr]) {
				if (attrArr[pAttr][m].singleName == $(this).val()){
					$.messager.alert("提示", "属性值不能重复");
					//值还原
					$(this).val(curAttrVal);
					return;
				}
			}
			attrArr[pAttr][n].singleName = $(this).val();
		});
		
		$priceVal.blur(function(){
			if ($.trim($(this).val()) == "") {
				//$(this).numberbox('validate').focus();
				return;
			};
			var n = $(this).attr('count');
			var pAttr = getAttr($(this));
			attrArr[pAttr][n].singlePrice = $(this).val();
		});
		
		$delBtn.click(function(){
			var pAttr = getAttr($(this));
			removeAttrValue(pAttr, n);
			$ele.remove();
		});
		
		//属性配置
		var $attrBtn = $("<a href=\"javascript:void(0)\" style='width:80px;' class=\"easyui-linkbutton ct-rst-btn\" iconCls=\"icon-edit\" plain=\"true\">属性配置</a>");
		var $attrCfg = $("<input count='"+i+"' type=\"text\" style=\"width:100px;\"/>");
		
		$attrBtn.click(function(){
			attrEdit($attrCfg);
		});
		
		$ele.find("td:eq(0)").append($attrVal);
		$ele.find("td:eq(2)").append($priceVal).append(" ").append($delBtn)
			.append($attrCfg).append($attrBtn);
		
		return $ele;
	}
	
	function attrEditForPurchase($attrCfg){
		var attr2 = getAttr2($attrCfg);
		if ($.trim(attr2) == "") {
			$.messager.alert("提示", "请先配置类型值");
			return false;
		}
		
		var dlg = $('<div/>').dialog({    
		    title: '属性配置',    
		    width: 420,    
		    height: 520,    
		    closable: false,    
		    cache: false,    
		    href: 'menu/menu_attr.jsp',    
		    modal: true,
		    buttons : [ {
				text : '保存',
				handler :function(){
					requirejs(['menu-attr'],function  (attr) {
						var productAttrTypes = attr.getAttrArr();
						if (!productAttrTypes) {
							return;
						}
						var n = $attrCfg.attr('count');
						var pAttr = getAttrByPurchase($attrCfg);
						productAttrTypes = JSON.parse(productAttrTypes);
						attrArr[pAttr][n].productAttrTypes = productAttrTypes;
						addTag($attrCfg, productAttrTypes);
						//关闭
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
				requirejs(['menu-attr'],function  (attr) {
					attr.init();
					//初始值
					var n = $attrCfg.attr('count');
					var pAttr = getAttrByPurchase($attrCfg);
					attr.loadAttr(attrArr[pAttr][n].productAttrTypes);
				});
			}
		});  
	}
	//添加标签
	function addTag($input, productAttrTypes){
		$input.importTags('');
		for ( var i in productAttrTypes) {
			$input.addTag(productAttrTypes[i].attrTypeName);
		}
	}
	
	function attrEdit($attrCfg){
		var attr2 = getAttr2($attrCfg);
		if ($.trim(attr2) == "") {
			$.messager.alert("提示", "请先配置类型值");
			return false;
		}
		
		var dlg = $('<div/>').dialog({    
		    title: '属性配置',    
		    width: 420,    
		    height: 520,    
		    closable: false,    
		    cache: false,    
		    href: 'menu/menu_attr.jsp',    
		    modal: true,
		    buttons : [ {
				text : '保存',
				handler :function(){
					requirejs(['menu-attr'],function  (attr) {
						var productAttrTypes = attr.getAttrArr();
						if (!productAttrTypes) {
							return;
						}
						var n = $attrCfg.attr('count');
						var pAttr = getAttr($attrCfg);
						productAttrTypes = JSON.parse(productAttrTypes);
						attrArr[pAttr][n].productAttrTypes = productAttrTypes;
						addTag($attrCfg, productAttrTypes);
						//关闭
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
				requirejs(['menu-attr'],function  (attr) {
					attr.init();
					//初始值
					var n = $attrCfg.attr('count');
					var pAttr = getAttr($attrCfg);
					attr.loadAttr(attrArr[pAttr][n].productAttrTypes);
				});
			}
		});  
	}
	
	function changeAttr(attr){
		attrArr[attr] = attrArr[curAttr];
		removeAttr(curAttr);
	}
	
	function removeAttr(attr){
		delete attrArr[attr];
	}
	
	function removeAttrValue(attr, n){
		delete attrArr[attr][n];
	}
	/**
	 * 获取一级组合配置
	 */
	function getAttr(node){
		return node.parents('table').find("tr:eq(0) input:eq(0)").val();
	}	
	/**
	 * 获取一级组合配置ByPurchase
	 */
	function getAttrByPurchase(node){
		return node.parents('table').parent('div').prev('div').find("tr:eq(0) input:eq(0)").val();
	}	
	/**
	 * 获取二级组合配置
	 */
	function getAttr2(node){
		return node.parents('table').find("tr:eq(1) input:eq(0)").val();
	}
	function insertNewAttr(attr){
		attrArr[attr] = {};
		var $ele = createNewAttr(attr);
		$("#comboPkg_form").append($ele);
		return $ele;
	}
	//返回属性配置
	function getAttrArr(){
		if (!validateAttr()) {
			return false;
		}
		var result = [];
		for ( var i in attrArr) {
			var attr = {};
			attr.comboTypeName = i;
			attr.childCombos = [];
			//attr.productAttrTypes = [];
			for ( var j in attrArr[i]) {
				attr.childCombos = attr.childCombos.concat(attrArr[i][j]);
			}
			result.push(attr);
			
		}
		//$.messager.alert("提示", JSON.stringify(result));
		return JSON.stringify(result);
	}
	//校验属性配置
	function validateAttr(){
		for ( var i in attrArr) {
			if (i.replace(/[^\x00-\xff]/g,"aa").length > 16) {
				$.messager.alert("提示", "【"+i+"】名称长度不得大于16个字符");
				return;
			}
			var count = 0;
			for ( var j in attrArr[i]) {
				if (j != "purchaseCfgVal") {
					++count;
					//存在未配置属性值
					if ($.trim(attrArr[i][j].singleName) == "") {
						$.messager.alert("提示", "【"+i+"】中存在空属性值");
						return false;
					};
					//长度校验
					if (attrArr[i][j].singleName.replace(/[^\x00-\xff]/g,"aa").length > 16) {
						$.messager.alert("提示", "【"+attrArr[i][j].singleName+"】名称长度不得大于16个字符");
						return false;
					};
					//价格合法校验
					if (!/^\d+(\.\d+$)?/.test(attrArr[i][j].singlePrice)) {
						$.messager.alert("提示", "【"+i+"】中存在非法价格值");
						return false;
					};
				}
			}
			if (count == 0) {
				$.messager.alert("提示", "【"+i+"】必须至少配置1个属性值");
				return false;
			}
		}
		return true;
	}
	//数据回显
	function loadAttr(data){
		for ( var i in data) {
			var $div = insertNewAttr(data[i].comboTypeName);
			var purchases = [];
			//排序,保证先处理组合分类
			data[i].childCombos.sort(function(a, b){
				return b.exchangeProductFlag - a.exchangeProductFlag;
			});
			for ( var j in data[i].childCombos) {
				if ((data[i].childCombos)[j].exchangeProductFlag == 0) {
					var attrVal = (data[i].childCombos)[j];
					addNewAttrValue($div, data[i].comboTypeName, attrVal.singleName, attrVal.singlePrice, attrVal.productAttrTypes);
				} else {
					var attrVal = (data[i].childCombos)[j];
					addNewPurchase($div.find('div:eq(2)'), data[i].comboTypeName, attrVal.singleName, attrVal.singlePrice, attrVal.productAttrTypes);
				}
			}
			
		}
		//界面渲染
		$.parser.parse('#comboPkg_form');
	}
	
	
    return {
        init : init,
        getAttrArr: getAttrArr,
        loadAttr: loadAttr
    };
    
});

	