//@ sourceUrl=turnover_month.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	function init(){
		//
		initSearchForm();
		initButton();
		initDatagrid();
	}
	
	function initSearchForm(){
		$("#turnoverMonth_startYear option, #turnoverMonth_endYear option, " +
		"#turnoverMonth_startMonth option, #turnoverMonth_endMonth option").remove();
		
		for (var i = 1; i <= 12; i++){
			$("#turnoverMonth_startMonth, #turnoverMonth_endMonth").append("<option value='"+i+"'>"+i+"</option");
		}
		//年限制,最大到当前年
		var curYear = new Date().getFullYear();
		for (var i = 2016; i <= curYear; i++){
			$("#turnoverMonth_startYear, #turnoverMonth_endYear").append("<option value='"+i+"'>"+i+"</option");
		}
		//初始月限制,最大到当前月
		var curMonth = new Date().getMonth()+1;
		resetStartMonth(curMonth);
		resetEndMonth(curMonth);
	}
	
	function initButton(){
		$("#turnoverMonth_startYear").change(function(){
			var curYear = new Date().getFullYear();
			if($(this).val() == curYear){
				var curMonth = new Date().getMonth()+1;
				resetStartMonth(curMonth);
			}else{
				resetStartMonth(12);
			}
		});
		$("#turnoverMonth_endYear").change(function(){
			var curYear = new Date().getFullYear();
			if($(this).val() == curYear){
				var curMonth = new Date().getMonth()+1;
				resetEndMonth(curMonth);
			}else{
				resetEndMonth(12);
			}
		});
		$("#turnoverMonth_query").click(query);
		$("#turnoverMonth_clear").click(clearForm);
	}
	//重置月份
	function resetStartMonth(month){
		$("#turnoverMonth_startMonth option").remove();
		for (var i = 1; i <= month; i++){
			$("#turnoverMonth_startMonth").append("<option value='"+i+"'>"+i+"</option");
		}
	}
	function resetEndMonth(month){
		$("#turnoverMonth_endMonth option").remove();
		for (var i = 1; i <= month; i++){
			$("#turnoverMonth_endMonth").append("<option value='"+i+"'>"+i+"</option");
		}
	}
	
	function initDatagrid(){
		$('#turnoverMonth_pageList').datagrid({
				fit:true,
				pagination:true,
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				pageList : [ 20, 30],
				columns:[[
				   {field:'date',title:'日期',width:150,sortable:true,align:'center',formatter:function(value){
						return value.substring(0,4)+"-"+value.substring(4,6);
					}},
				   {field:'payTp',title:'支付方式',width:150,sortable:true,align:'center',formatter:function(value,rec){
		     	  		if ('0'==value){return '现金';}
		     	  		else if ('1'==value){return '银行卡';}
		    	  		else if ('2' == value){return '拉卡拉扫码';}
		    	  		else if ('3' == value){return '微信扫码';}
		    	  		else if ('4' == value){return '支付宝扫码';}
		    	  		else if ('5' == value){return '微信被扫码';}
		    	  		else if ('6' == value){return '支付宝被扫码';}
		    	  		else if ('-9' == value){return '总计';}
		    	  		else {return '';};
					}},
				   {field:'turnover',title:'营业额(元)',width:150,sortable:true,align:'center',formatter:function(value){
						return value.toFixed(2);
					}}

				]],
				toolbar:"#turnoverMonth_toolbar",
				rowStyler : function(index, row) {
					if (row.payTp == -9) {
						return 'color:red;';
					}
				}
			});
			//重写翻页事件
			var pageOpts = $('#turnoverMonth_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
		}

		function check(){
			if ($("#turnoverMonth_endYear").val() < $("#turnoverMonth_startYear").val()) {
				$.messager.alert("提示", "起始时间不得大于结束时间！");
				return false;
			}
			
			if ($("#turnoverMonth_endYear").val() == $("#turnoverMonth_startYear").val()) {
				if ($("#turnoverMonth_endMonth").val() < $("#turnoverMonth_startMonth").val()) {
					$.messager.alert("提示", "起始时间不得大于结束时间！");
					return false;
				}
			}
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#turnoverMonth_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				param.mchntCd = currentMchntCd;
				param.startDate = $("#turnoverMonth_startYear").val() + 
					($("#turnoverMonth_startMonth").val().length == 2 ? $("#turnoverMonth_startMonth").val() : "0"+$("#turnoverMonth_startMonth").val())
					+ "01";
				param.endDate = $("#turnoverMonth_endYear").val() + 
					($("#turnoverMonth_endMonth").val().length == 2 ? $("#turnoverMonth_endMonth").val() : "0"+$("#turnoverMonth_endMonth").val())
					+"31";
				
				$.post("report/turnover/listmonth", param, function(result){
					if (result.code == 0) {
	            		var data = result.data;
	            		/*var total = 0;
	            		for ( var i in data) {
							total += data[i].turnover;
						}
	            		data.push({payTp:-9, turnover: total});*/
	            		$('#turnoverMonth_pageList').datagrid('loadData',data);  
	            		//按照月份合并行
	            		if (data.length == 0) {
							return;
						}
	            		var start = 0;
	            		var rowspan = 1;
	            		var curDate = data[0].date;
	            		for ( var i = 1; i < data.length; i++) {
							if (data[i].date == curDate) {
								rowspan++;
								if(i == data.length-1){
									$('#turnoverMonth_pageList').datagrid('mergeCells',{
				    					index: start,
				    					field: 'date',
				    					rowspan: rowspan
				    				});
								}
							}else{
								$('#turnoverMonth_pageList').datagrid('mergeCells',{
			    					index: start,
			    					field: 'date',
			    					rowspan: rowspan
			    				});
								curDate = data[i].date;
								rowspan = 1;
								start = i;
							}
						}
					} else {
						$.messager.alert("提示", result.message);
					}
				}, "json");
				
			}
	}
		
	function clearForm(){
		$("#turnoverMonth_searchForm").form('clear');
		$('#turnoverMonth_pageList').datagrid('loadData',{ total: 0, rows: [] });
	}

	function reload(){
		query();
	}
	
    return {
        init : init
    }
    
});

	