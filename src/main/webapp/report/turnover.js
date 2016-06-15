//@ sourceUrl=turnover.js
define(function () {
	var currentMchntCd = userInfo.mchntCd;
	function init(){
		initButton();
		initDatagrid();
	}
	
	function initButton(){
		$("#turnover_query").click(query);
		$("#turnover_clear").click(clearForm);
		$("#turnover_ytd").click(function(){
			setDate(1);
		});
		$("#turnover_in3").click(function(){
			setDate(3);
		});
		$("#turnover_in5").click(function(){
			setDate(5);
		});
		$("#turnover_in7").click(function(){
			setDate(7);
		});
		$("#turnover_in30").click(function(){
			setDate(30);
		});
	}
	
	function setDate(n){
		var ytd = new Date(Date.parse(new Date().toString()) - 1000*3600*24).format("yyyy-MM-dd");
		var startDate = new Date(Date.parse(new Date().toString()) - 1000*3600*24*n).format("yyyy-MM-dd");
		$("#turnover_startDate").val(startDate);
		$("#turnover_endDate").val(ytd);
		query();
	}
	
	function initDatagrid(){
		$('#turnover_pageList').datagrid({
				fit:true,
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				pageList : [ 10],
				columns:[[
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
				toolbar:"#turnover_toolbar",
				rowStyler : function(index, row) {
					if (row.payTp == -9) {
						return 'color:red;';
					}
				}
			});
		}

		function check(){
			if ($("#turnover_startDate").val() != "" && $("#turnover_endDate").val() != "") {
				if($("#turnover_startDate").val() > $("#turnover_endDate").val()){
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
				var param = {};
				param.mchntCd = currentMchntCd;
				param.startDate = $("#turnover_startDate").val().replace(/-/g, "");
				param.endDate = $("#turnover_endDate").val().replace(/-/g, "");
				
				$.post("report/turnover/list", param, function(result){
					if (result.code == 0) {
	            		var data = result.data;
	            		var total = 0;
	            		for ( var i in data) {
							total += data[i].turnover;
						}
	            		data.push({payTp:-9, turnover: total});
	            		$('#turnover_pageList').datagrid('loadData',data);  
					} else {
						$.messager.alert("提示", result.message);
					}
				}, "json");
				
			}
	}
		
	function clearForm(){
		$("#turnover_searchForm").form('clear');
		$('#turnover_pageList').datagrid('loadData',{ total: 0, rows: [] });
	}

	function reload(){
		query();
	}
	
    return {
        init : init
    }
    
});

	