//@ sourceUrl=discount_reduction.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	function init(){
		//test();
	}
	
	function getValue(){
		if($('#discountReduction_form').form("validate")){
			var total = Number($("#discountReduction_form input[name='total']").val());
			var benefit = Number($("#discountReduction_form input[name='benefit']").val());
			if (total == 0) {
				$.messager.alert("提示", "满额值不能为0！");
				return false;
			}
			if (benefit > total) {
				$.messager.alert("提示", "减免值不能大于满额值！");
				return false;
			}
			
			return {
				total: total,
				benefit: benefit
			};
		}
	}
	
    return {
        init : init,
        getValue: getValue
    };
    
});

	