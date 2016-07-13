//@ sourceUrl=packingboxfee.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	function init(){
		initButton();
		query();
	}
	function initButton(){
		$("#fee_edit").click(edit);
		$("#num_switch").click(changeNumShow);
		//默认灰显
		$('#num_num').numberbox('disable');
		$('#num_edit').linkbutton('disable');
	}
	
	function changeNumShow(){
		if ($("#num_switch").attr('checked')) {
			$('#num_num').numberbox('enable');
			$('#num_edit').linkbutton('enable');
			$("#num_edit").click(batchPackingBoxNumUpdate);
		} else {
			$('#num_num').numberbox('disable');
			$('#num_edit').linkbutton('disable');
			$("#num_edit").unbind('click');
		}
	}
	
	function batchPackingBoxNumUpdate(){
		var boxNum = $('#num_num').numberbox('getValue');
		var param = {};
		param.mchntCd = currentMchntCd;
		param.packingBoxNum = boxNum;
		$.messager.confirm('提示', '确定批量设置打包盒份数?', function(r){
			if (r){
				$.post("menu/menu/setPackingBoxNum", param, function(result){
					if (result.code == 0) {
					
						$.messager.alert("提示", "批量设置成功!");
					} else {
						$.messager.alert("提示", result.message);
					}
				}, "json");
			}
		});
		
	}
	
		function check(){
			return true;
		}
		
		function query(){
			if(check()){
				var param = {};
				param.mchntCd = currentMchntCd;
				param.param = "packing_box_price";
				
				$.post("sys/param/get", param, function(result){
					if (result.code == 0) {
						var data = result.data;
						initForm(data);
					} else {
						$.messager.alert("提示", result.message);
					}
				}, "json");
			}
	}


	function edit(){
		if($('#fee_form').form("validate")){
			var fee = $("#fee_fee").val()*1;
			if (fee < 0) {
				$.messager.alert("提示", "打包盒费不得小于0！");
				return false;
			} else if(fee > 99.99){
				$.messager.alert("提示", "打包盒费不得大于99.99！");
				return false;
			}
			var param = {};
			param.param = "packing_box_price";
			param.data = fee;
	    		$('#fee_form').ajaxSubmit( {
	    			url : contextPath+"/sys/param/update",
	    			data: param,
	    			dataType : "json",
	                success : function(result) {
	                	if (result.code == 0) {
	                		$.messager.alert("提示", "修改成功!");
	    				} else {
	    					$.messager.alert("提示", result.message);
	    				}
	    			}
	            });
		}
	}
	
	
	function initForm(data){
		$("#fee_mchntCd").val(currentMchntCd);
		if(data != null && data.data != null){
			$("#fee_id").val(data.uuid);
			//表单赋值
			$("#fee_fee").numberbox('setValue', data.data);
		}
	}

    return {
        init : init
    }
    
});

	