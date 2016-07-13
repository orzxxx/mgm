//@ sourceUrl=discount.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	function init(){
		initButton();
		query();
	}
	function initButton(){
		$("#discount_edit").click(edit);
		$("#discount_startDate").click(function(){
			var startDate = $("#discount_startDate").val();
			var curDate = new Date().format('yyyy-MM-dd');
			if (curDate > startDate) {
				$("#discount_startDate").val(curDate);
				WdatePicker({startDate:curDate, dateFmt: 'yyyy-MM-dd',minDate:'%y-%M-%d'}); 
			} else {
				WdatePicker({startDate:startDate, dateFmt: 'yyyy-MM-dd',minDate:'%y-%M-%d'}); 
			}
		});
		$("#discount_endDate").click(function(){
			var endDate = $("#discount_endDate").val();
			var curDate = new Date().format('yyyy-MM-dd');
			if (curDate > endDate) {
				WdatePicker({startDate:'#F{$dp.$D(\'discount_startDate\')||\'%y-%M-%d\'}',alwaysUseStartDate:true,dateFmt: 'yyyy-MM-dd',minDate:'#F{$dp.$D(\'discount_startDate\')||\'%y-%M-%d\'}'});
			} else {
				WdatePicker({startDate:endDate,dateFmt: 'yyyy-MM-dd',minDate:'#F{$dp.$D(\'discount_startDate\')||\'%y-%M-%d\'}'});
			}
		});
		
	}
	/**
	 * 设置日期小于当前日期时,设置时间为当前日期
	 */
	function setCurrentDate(){
		var startDate = $("#discount_startDate").val();
		var curDate = new Date().format('yyyy-MM-dd');
		if (curDate > startDate) {
			$("#discount_startDate").val(curDate);
		}
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
	
		function check(){
			return true;
		}
		
		function query(){
			if(check()){
				var param = {};
				param.mchntCd = currentMchntCd;
				param.param = "discount_rate";
				
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
		if($('#discount_form').form("validate")){
			if($("#discount_startDate").val() > $("#discount_endDate").val()){
				$.messager.alert("提示", "生效日期不得大于失效日期！");
				return false;
			}
			var rate = $("#discount_rate").val()*1;
			if (rate < 0.1 || rate > 10) {
				$.messager.alert("提示", "输入折扣率有误,无法保存设置！");
				return false;
			}
			var param = {};
			param.data = $("#discount_rate").numberbox('getValue')+"|"+
				$("#discount_startDate").val().replace(/-/g, "")+"|"+$("#discount_endDate").val().replace(/-/g, "");
			param.param = "discount_rate";
	    		$('#discount_form').ajaxSubmit( {
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
	            })
		}
	}
	
	
	function initForm(data){
		$("#discount_mchntCd").val(currentMchntCd);
		if(data != null && data.data != null){
			$("#discount_id").val(data.uuid);
			var cfgs = data.data.split("|");
			//表单赋值
			$("#discount_rate").numberbox('setValue', cfgs[0]*1);
			var startDate = cfgs[1].substring(0,4)+"-"+cfgs[1].substring(4,6)+"-"+cfgs[1].substring(6,8);
			var endDate = cfgs[2].substring(0,4)+"-"+cfgs[2].substring(4,6)+"-"+cfgs[2].substring(6,8);
			$("#discount_startDate").val(startDate);
			$("#discount_endDate").val(endDate);
		}
	}

    return {
        init : init
    }
    
});

	