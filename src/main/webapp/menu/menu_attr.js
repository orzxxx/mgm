//@ sourceUrl=menu_attr.js
define(function () {
	var attrArr = {};
	var curAttr = "";
	var curAttrVal = "";
	var i = 0;
	
	function init(){
		attrArr = {};
		$("#menuAttr_addAttr").click(addNewAttr);
		//test
		$("#menuAttr_addAttr").parent().append("<input id='test' type='button'/>");
		$("#test").click(getAttrArr);
		var data = [{
			name: "a1",
			value: [
			        {name:'v1', price: '20'},
			        {name:'v2', price: '-20'},
			        {name:'v3', price: '120'}
			]},
			{
				name: "a2",
				value: [
				        {name:'v4', price: '220'},
				        {name:'v5', price: '-240'},
				        {name:'v6', price: '20'}
				]},
		];
		loadAttr(data);
	}
	//添加新属性
	function addNewAttr(){
		var newAttr = $("#menuAttr_newAttr").val();
		if (newAttr == null || $.trim(newAttr) == "") {
			$.messager.alert("提示", "请输入新属性");
			return;
		}
		var count = 0;
		for ( var i in attrArr) {
			if (++count >= 3) {
				$.messager.alert("提示", "最多添加3个属性");
				return;
			}
		}
		if (attrArr[newAttr] != null) {
			$.messager.alert("提示", "属性名不能重复");
			return;
		}
		//添加
		insertNewAttr(newAttr);
		$("#menuAttr_newAttr").val("");
		//界面渲染
		$.parser.parse('#menuAttr_form');
	}
	//添加新属性值
	function addNewAttrValue(div, attr, val, price){
		var length = $(div).find("table tr").length-1;
		if (length >= 3) {
			$.messager.alert("提示", "最多添加3个属性值");
			return;
		}
		//插入
		var $ele = createNewAttrValue(attr, val, price);
		$(div).find("table").append($ele);
		$.parser.parse('#menuAttr_form');
		$(div).find("table tr:last td:eq(0) input").focus();
		//添加数据
		var attrVal = {};
		attrVal.name = val;
		attrVal.price = price;
		attrArr[attr][i++] = attrVal;
	}
	//
	function createNewAttr(attr){
		var $ele = $("<div >"+
			"<table>"+
				"<tr>"+
					"<td style=\"width:400px;\">"+
						"<label>属性名:</label> "+
					"</td>"+
					"<td class=\"tdspace\">"+
					"</td>"+
					"<td style=\"width:400px;\">"+
						
					"</td>"+
					"<td class=\"hintspace\" style='width:70px;'></td>"+		
				"</tr>"+
			"</table>"+
		"</div><hr/>");
		
		var $addBtn = $("<a href=\"javascript:void(0)\" style=\"width:100px;\" type='addAttr' class=\"easyui-linkbutton ct-rst-btn\" iconCls=\"icon-add\" plain=\"true\">添加属性值</a>");
		var $delBtn = $("<a href=\"javascript:void(0)\" class=\"easyui-linkbutton ct-rst-btn\" iconCls=\"icon-remove\" plain=\"true\">删除</a>");
		
		var $attr = $("<input value='"+attr+"' type=\"text\" maxlength=\"8\" class=\"easyui-validatebox\" style=\"width:120px;\"/>");
		
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
				$.messager.alert("提示", "属性名不能重复");
				//值还原
				$(this).val(curAttr);
				return;
			}
			changeAttr($(this).val());
		});
		
		$addBtn.click(function(){
			addNewAttrValue($ele, $attr.val(), '', 0);
		});
		
		$delBtn.click(function(){
			removeAttr($attr.val());
			$ele.remove();
		});
		
		$ele.find("td:eq(0)").append($attr);
		$ele.find("td:eq(2)").append($addBtn).append(" ").append($delBtn);
		
		return $ele;
	}
	
	function createNewAttrValue(attr, val, price){
		var $ele = $("<tr>"+
		"<td style=\"width:400px;text-align: right;\">"+
		"</td>"+
		"<td class=\"tdspace\">"+
		"</td>"+
		"<td style=\"width:400px;\">"+
			"<label>价格:</label> "+
		"</td>"+
		"<td class=\"hintspace\" style='width:70px;'></td>"+				
		"</tr>");
		
		var $attrVal = $("<input count='"+i+"' type=\"text\" value='"+val+"' maxlength=\"8\" class=\"easyui-validatebox\" data-options=\"required:true\" style=\"width:100px;\"/>");
		var $priceVal = $("<input count='"+i+"' type=\"text\" value='"+price+"' maxlength=\"8\" class=\"easyui-numberbox\" min='0' max='9999.99' precision='2' style=\"width:60px;\"/> ");
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
			var pAttr = getAttr(this);
			for ( var m in attrArr[pAttr]) {
				if (attrArr[pAttr][m].name == $(this).val()){
					$.messager.alert("提示", "属性值不能重复");
					//值还原
					$(this).val(curAttrVal);
					return;
				}
			}
			attrArr[pAttr][n].name = $(this).val();
		});
		
		$priceVal.blur(function(){
			if ($.trim($(this).val()) == "") {
				//$(this).numberbox('validate').focus();
				return;
			};
			var n = $(this).attr('count');
			var pAttr = getAttr(this);
			attrArr[pAttr][n].price = $(this).val();
		});
		
		$delBtn.click(function(){
			var pAttr = getAttr(this);
			removeAttrValue(pAttr, n);
			$ele.remove();
		});
		
		$ele.find("td:eq(0)").append($attrVal);
		$ele.find("td:eq(2)").append($priceVal).append(" ").append($delBtn);
		
		return $ele;
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
	
	function getAttr(node){
		return $(node).parents('table').find("tr:eq(0) input").val();
	}	
	function insertNewAttr(attr){
		attrArr[attr] = {};
		var $ele = createNewAttr(attr);
		$("#menuAttr_form").append($ele);
		return $ele;
	}
	//返回属性配置
	function getAttrArr(){
		if (!validateAttr()) {
			return;
		}
		var result = [];
		for ( var i in attrArr) {
			var attr = {};
			attr.name = i;
			attr.value = [];
			for ( var j in attrArr[i]) {
				var attrVal =  attrArr[i][j];
				attr.value.push(attrVal);
			}
			result.push(attr);
		}
		$.messager.alert("提示", JSON.stringify(result));
		//return JSON.stringify(result);
	}
	//校验属性配置
	function validateAttr(){
		for ( var i in attrArr) {
			var count = 0;
			for ( var j in attrArr[i]) {
				++count;
				//存在未配置属性值
				if ($.trim(attrArr[i][j].name) == "") {
					$.messager.alert("提示", "【"+i+"】中存在空属性值");
					return false;
				};
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
			var $div = insertNewAttr(data[i].name);
			for ( var j in data[i].value) {
				var attrVal = (data[i].value)[j];
				addNewAttrValue($div, data[i].name, attrVal.name, attrVal.price);
			}
		}
		//界面渲染
		$.parser.parse('#menuAttr_form');
	}
	
	
    return {
        init : init,
        getAttrArr: getAttrArr,
        loadAttr: loadAttr
    };
    
});

	