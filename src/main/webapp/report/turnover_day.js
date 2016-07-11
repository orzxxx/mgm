//@ sourceUrl=turnover_day.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	function init(){
		initButton();
		initDatagrid();
	}
	
	function initButton(){
		$("#turnoverDay_query").click(query);
		$("#turnoverDay_clear").click(clearForm);
		
		$("#turnoverDay_in30").click(function(){
			setDate(30);
		});
		$("#turnoverDay_in14").click(function(){
			setDate(14);
		});
		$("#turnoverDay_in7").click(function(){
			setDate(7);
		});
		$("#turnoverDay_in60").click(function(){
			setDate(60);
		});
	}
	
	function setDate(n){
		var ytd = new Date(Date.parse(new Date().toString()) - 1000*3600*24).format("yyyy-MM-dd");
		var startDate = new Date(Date.parse(new Date().toString()) - 1000*3600*24*n).format("yyyy-MM-dd");
		$("#turnoverDay_startDate").val(startDate);
		$("#turnoverDay_endDate").val(ytd);
		query();
	}
	
	function initDatagrid(){
		$('#turnoverDay_pageList').datagrid({
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
						return value.substring(0,4)+"-"+value.substring(4,6)+"-"+value.substring(6,8);
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
				toolbar:"#turnoverDay_toolbar",
				rowStyler : function(index, row) {
					if (row.payTp == -9) {
						return 'color:red;background-color:white;';
					}
					return 'background-color:white;';
				}
			});
			//重写翻页事件
			var pageOpts = $('#turnoverDay_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
		}

		function check(){
			if ($("#turnoverDay_startDate").val() != "" && $("#turnoverDay_endDate").val() != "") {
				if($("#turnoverDay_startDate").val() > $("#turnoverDay_endDate").val()){
					$.messager.alert("提示", "起始时间不得大于结束时间！");
					return false;
				}
			}else{
				$.messager.alert("提示", "请输入时间范围");
				return false;
			}
			return true;
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#turnoverDay_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				param.mchntCd = currentMchntCd;
				param.startDate = $("#turnoverDay_startDate").val().replace(/-/g, "");
				param.endDate = $("#turnoverDay_endDate").val().replace(/-/g, "");
				
				$.post("report/turnover/listday", param, function(result){
					if (result.code == 0) {
	            		var data = result.data.rows;
	            		/*var total = 0;
	            		for ( var i in data) {
							total += data[i].turnover;
						}
	            		data.push({payTp:-9, turnover: total});*/
	            		$('#turnoverDay_pageList').datagrid('loadData', result.data);  
	            		if (data == null || data.length == 0) {
	            			$.messager.alert("提示", "没有符合条件的记录!");
	            			return;
						}
	            		//按照日期合并行
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
									$('#turnoverDay_pageList').datagrid('mergeCells',{
				    					index: start,
				    					field: 'date',
				    					rowspan: rowspan
				    				});
								}
							}else{
								$('#turnoverDay_pageList').datagrid('mergeCells',{
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
		$("#turnoverDay_searchForm").form('clear');
		$('#turnoverDay_pageList').datagrid('loadData',{ total: 0, rows: [] });
	}

	function reload(){
		query();
	}
	
    return {
        init : init
    }
    
});

	