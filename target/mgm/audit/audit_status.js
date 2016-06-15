//@ sourceUrl=audit_status.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	
	function init(){
		//查询审核状态
		$.post("mchnt/audit/get",{mchntCd: currentMchntCd},function(result){
			if (result.code == 0) {
				if (parseInt(result.data.auditStatus) == 0 ) {
					$("#auditStatus_img").attr("src", "images/audit_status2.png");
				}else if (parseInt(result.data.auditStatus) == -1 ) {
					$("#auditStatus_img").attr("src", "images/audit_status4.png");
					var time = result.data.auditTime;
					time = time.substring(0,4)+"年"+time.substring(4,6)+"月"+time.substring(6,8)+"日"+
						time.substring(8,10)+"时"+time.substring(10,12)+"分"+time.substring(12,14)+"秒";
					result.data.auditTime = time;
					$("#auditStatus_auditResult").form('load', result.data);
					$("#auditStatus_auditResult").show();
				}else if (parseInt(result.data.auditStatus) == 2 || parseInt(result.data.auditStatus) == 1) {
					$("#auditStatus_img").attr("src", "images/audit_status3.png");
				}
			} else {
				$.messager.alert("提示", result.message);
			}
		}, "json");
	}
	
    return {
        init : init
    }
    
});

	