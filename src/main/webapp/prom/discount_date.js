//@ sourceUrl=discount_date.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	function init(){
		initForm();
		initButton();
		changeMode(1);
		//test();
	}
	
	function test(){
		//test
		$("#discountDate_setTime").parent().append("<input id='test' type='button'/>");
		$("#test").click(function(){
			alert(getValue());
		});
		//var data = "1235|001243|235512";
		var data = "20160808001243|20160818235512";
		setValue(data);
	}
	/*模式一 时间段
	 * 模式二 星期
	 */
	function changeMode(mode){
		if (mode == 1) {
			changeTimeForm(0);
			changeWeekForm(1);
		} else if (mode == 2){
			changeWeekForm(0);
			changeTimeForm(1);
		}
	}
	/**
	 * 0可 1不可编辑
	 */
	function changeTimeForm(type){
		if (type == 1) {
			$("#discountDate_startDate").attr('disabled', 'disabled');
			$("#discountDate_endDate").attr('disabled', 'disabled');
		} else if (type == 0){
			$("#discountDate_startDate").attr('disabled', false);
			$("#discountDate_endDate").attr('disabled', false);
		}
	}
	
	/**
	 * 0可 1不可编辑
	 */
	function changeWeekForm(type){
		if (type == 1) {
			$("#discountDate_weekday input:checkbox[name='week']").attr('disabled', 'disabled');
		} else if (type == 0){
			$("#discountDate_weekday input:checkbox[name='week']").attr('disabled', false);
		}
	}
	
	function initForm(){
		$("#discountDate_startTime").timespinner('setValue', '00:00:00');
		$("#discountDate_endTime").timespinner('setValue', '23:59:59');
		$("#discountDate_startDate").click(function(){
			var startDate = $("#discountDate_startDate").val();
			var curDate = new Date().format('yyyy-MM-dd');
			if (curDate > startDate) {
				$("#discountDate_startDate").val(curDate);
				WdatePicker({startDate:curDate, dateFmt: 'yyyy-MM-dd',minDate:'%y-%M-%d'}); 
			} else {
				WdatePicker({startDate:startDate, dateFmt: 'yyyy-MM-dd',minDate:'%y-%M-%d'}); 
			}
		});
		$("#discountDate_endDate").click(function(){
			var endDate = $("#discountDate_endDate").val();
			var curDate = new Date().format('yyyy-MM-dd');
			if (curDate > endDate) {
				WdatePicker({startDate:'#F{$dp.$D(\'discount_startDate\')||\'%y-%M-%d\'}',alwaysUseStartDate:true,dateFmt: 'yyyy-MM-dd',minDate:'#F{$dp.$D(\'discount_startDate\')||\'%y-%M-%d\'}'});
			} else {
				WdatePicker({startDate:endDate,dateFmt: 'yyyy-MM-dd',minDate:'#F{$dp.$D(\'discount_startDate\')||\'%y-%M-%d\'}'});
			}
		});
	}
	function initButton(){
		$("#discountDate_setTime, #discountDate_setWeek").click(function(){
			changeMode($(this).val());
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


	function getValue(){
		var mode = $("input:radio[name='mode']:checked").val();
		if (mode == 1) {
			if($("#discountDate_startDate").val() == ""){
				$.messager.alert("提示", "生效日期不得为空！");
				return false;
			}
			if($("#discountDate_endDate").val() == ""){
				$.messager.alert("提示", "失效日期不得为空！");
				return false;
			}
			if($("#discountDate_startDate").val() > $("#discountDate_endDate").val()){
				$.messager.alert("提示", "生效日期不得大于失效日期！");
				return false;
			}
			if($("#discountDate_startTime").val() > $("#discountDate_endTime").val()){
				$.messager.alert("提示", "生效时间不得大于失效时间！");
				return false;
			}
			return "1|"+$("#discountDate_startDate").val().replace(/[- :]/g, "")+$("#discountDate_startTime").val().replace(/[- :]/g, "")
			+"|"+$("#discountDate_endDate").val().replace(/[- :]/g, "")+$("#discountDate_endTime").val().replace(/[- :]/g, "");
		} else if (mode == 2){
			var weeks = $("#discountDate_weekday input:checkbox[name='week']:checked");
			if(weeks.length == 0){
				$.messager.alert("提示", "至少勾选一个星期值！");
				return false;
			}
			if($("#discountDate_startTime").val() > $("#discountDate_endTime").val()){
				$.messager.alert("提示", "生效时间不得大于失效时间！");
				return false;
			}
			var value = "";
			weeks.each(function(){
				value += $(this).val();
			});
			return "2|"+value + "|"+ $("#discountDate_startTime").val().replace(/[- :]/g, "")+"|"+$("#discountDate_endTime").val().replace(/[- :]/g, "");
		}
	}
	
	function setValue(val){
		if(val == null || val == ""){
			return;
		}
		var datas = val.split('|');
		if (datas.length == 3) {
			$("#discountDate_setTime").click();
			var startDate = datas[1].substring(0,4)+"-"+datas[1].substring(4,6)+"-"+datas[1].substring(6,8);
			var endDate = datas[2].substring(0,4)+"-"+datas[2].substring(4,6)+"-"+datas[2].substring(6,8);
			$("#discountDate_startDate").val(startDate);
			$("#discountDate_endDate").val(endDate);
			var startTime = datas[1].substring(8,10)+":"+datas[1].substring(10,12)+":"+datas[1].substring(12,14);
			var endTime = datas[2].substring(8,10)+":"+datas[2].substring(10,12)+":"+datas[2].substring(12,14);
			$("#discountDate_startTime").timespinner('setValue', startTime);
			$("#discountDate_endTime").timespinner('setValue', endTime);
		} else if (datas.length == 4){
			$("#discountDate_setWeek").click();
			$("#discountDate_weekday input:checkbox[name='week']").each(function(){
				if (datas[1].indexOf($(this).val()) != -1) {
					$(this).attr('checked', 'checked');
				}
			});
			var startTime = datas[2].substring(0,2)+":"+datas[2].substring(2,4)+":"+datas[2].substring(4,6);
			var endTime = datas[3].substring(0,2)+":"+datas[3].substring(2,4)+":"+datas[3].substring(4,6);
			$("#discountDate_startTime").timespinner('setValue', startTime);
			$("#discountDate_endTime").timespinner('setValue', endTime);
			
		} else {
			$.messager.alert("提示", "非法的时间数据！");
		}
	}
	
    return {
        init : init,
        getValue: getValue,
        setValue: setValue
    };
    
});

	