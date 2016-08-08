//@ sourceUrl=order.js
define(function () {
	//var currentMchntCd = userInfo.mchntCd;
	function init(){
		initButton();
		initDatagrid();
		query();
	}
	function initButton(){
		$("#order_query").click(query);
		$("#order_clear").click(clearForm);
		$("#order_export").click(exportExcel);
	}
	function initDatagrid(){
		$('#order_pageList').datagrid({
				fit:true,
				pagination:true,
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				pageList : [ 30 ],
				frozenColumns:[[
				                {field:'orderNo',title:'订单号',width:170,sortable:true}
							]],
							columns:[[
									    //{field:'pseq',title:'平台流水',width:80,sortable:true,align:'center'},
									    // {field:'mchntNo',title:'商户号',width:90,sortable:true,align:'center'},
									    // {field:'terminalNo',title:'终端号',width:70,sortable:true,align:'center'},
										{field:'mchntCd',title:'商户号',width:60,sortable:true,align:'center'},
										{field:'mchntName',title:'店铺名称',width:80,sortable:true,align:'center'},
										// {field:'termDatetime',title:'终端交易时间',width:80,sortable:true,align:'center'},
										{field:'transdate',title:'交易日期',width:80,sortable:true,align:'center',formatter:function(value){
											return value.substring(0,4)+"/"+value.substring(4,6)+"/"+value.substring(6,8);
										}},
										{field:'transtime',title:'交易时间',width:90,sortable:true,align:'center',formatter:function(value){
											return value.substring(0,2)+":"+value.substring(2,4)+":"+value.substring(4,6);
										}},
										// {field:'datetime',title:'完整时间',width:110,sortable:true,align:'center'},
										/*
										 * {field:'stdtrnsamt',title:'订单金额',width:70,sortable:true,align:'right',halign:'center',dataType:'float',formatter:function(data){
										 * return value; }},
										 */
										{field:'stdtrnsamt',title:'订单金额(元)',width:70,sortable:true,align:'right',halign:'center',dataType:'float',formatter:function(value){
											return value.toFixed(2);
										}},
										// {field:'totalnum',title:'总计数量',width:70,sortable:true,align:'center'},

										// {field:'payNo',title:'付款账号',width:80,sortable:true,align:'center'},
										{field:'payTp',title:'付款类型',width:100,sortable:true,align:'center',formatter:function(value,rec){
							     	  		if ('0'==value){return '现金';}
							     	  		else if ('1'==value){return '银行卡';}
							    	  		else if ('2' == value){return '拉卡拉扫码';}
							    	  		else if ('3' == value){return '微信扫码';}
							    	  		else if ('4' == value){return '支付宝扫码';}
							    	  		else if ('5' == value){return '微信被扫码';}
							    	  		else if ('6' == value){return '支付宝被扫码';}
							    	  		else {return '';};
											}},
										// {field:'chnlSeq',title:'渠道流水号',width:150,sortable:true,align:'center'},
										{field:'trnsflag',title:'交易类型',width:80,sortable:true,align:'center',formatter:function(value,rec){
							     	  		if ('-1'==value){return '交易失败';}
							    	  		else if ('0' == value){return '交易初始化';}
							    	  		else if ('1' == value){return '交易成功';}
							    	  		else if ('2' == value){return '交易撤销';}
							    	  		else if ('3' == value){return '退单成功';}
							    	  		else {return '';};
											}}
										// {field:'setdate',title:'清算日期',width:80,sortable:true,align:'center'},
										// {field:'trnsdatetime',title:'交易传输时间',width:80,sortable:true,align:'center'},
										// {field:'stdrspcod',title:'应答码',width:60,sortable:true,align:'center'}
										// {field:'printInfo',title:'打印信息',width:150,sortable:true,align:'center'},
									]],
									// singleSelect:true
				toolbar:"#order_toolbar",
				onDblClickRow: function(){
					view();
				}
			});
			//重写翻页事件
			var pageOpts = $('#order_pageList').datagrid('getPager').data("pagination").options;
			pageOpts.onSelectPage = query;
				
		}

		function exportExcel(){
			var form = $("<form/>");//定义一个form表单
			form.attr("style", "display:none");
			form.attr("target", "");
			form.attr("method", "post");
			form.attr("action", contextPath+"/trade/order/export");
			
			$("#order_searchForm input, #order_searchForm select").each(function(){
				var propertyNode =$("<input/>");
				propertyNode.attr("type", "hidden");
				propertyNode.attr("name", $(this).attr('name'));
				propertyNode.attr("value", $(this).val());
				
				form.append(propertyNode);
			});
			$("body").append(form);//将表单放置在web中
			form.submit();//表单提交
		}
	
		function check(){
			if ($("#order_minTransdate").val() != "" && $("#order_maxTransdate").val() != "") {
				if($("#order_minTransdate").val() > $("#order_maxTransdate").val()){
					$.messager.alert("提示", "起始时间不得大于结束时间！");
					return false;
				}
			}
			return true;
		}
		
		function view(){
			var row = $('#order_pageList').datagrid("getSelected");
			if(row){
				var dlg = $('<div/>').dialog({    
				    title: '订单详情',    
				    width: 500,    
				    height: 510,    
				    closable: false,    
				    cache: false,    
				    href: 'trade/order_detail.jsp',    
				    modal: true,
				    buttons : [ {
						text : '关闭',
						handler : function() {
							dlg.dialog('close');
				    		dlg.remove();
						}
					}],
					onLoad : function(){
						$("#order_form").form('load', format(row));
						/*$.post("trade/order/details",{orderNo: row.orderNo},function(result){
							if (result.code == 0) {
								initView();
								var details = result.data;
								$('#order_details').datagrid('loadData',details);  
							} else {
								$.messager.alert("提示", result.message);
							}
						}, "json");*/
					}
				});  
			}
		}
		
		function format(oldobj){
			var obj = $.extend(true, {}, oldobj);
			obj.transdate = obj.transdate.substring(0,4)+"/"+obj.transdate.substring(4,6)+"/"+obj.transdate.substring(6,8);
			obj.transtime = obj.transtime.substring(0,2)+":"+obj.transtime.substring(2,4)+":"+obj.transtime.substring(4,6);
			obj.stdtrnsamt = obj.stdtrnsamt.toFixed(2);
			if ('0'==obj.payTp){obj.payTp =  '现金';}
 	  		else if ('1'==obj.payTp){obj.payTp = '银行卡';}
	  		else if ('2' == obj.payTp){obj.payTp = '拉卡拉扫码';}
	  		else if ('3' == obj.payTp){obj.payTp = '微信扫码';}
	  		else if ('4' == obj.payTp){obj.payTp = '支付宝扫码';}
	  		else if ('5' == obj.payTp){obj.payTp = '微信被扫码';}
	  		else if ('6' == obj.payTp){obj.payTp = '支付宝被扫码';}
	  		else {obj.payTp =  '';};
			
	  		if ('-1'==obj.trnsflag){obj.trnsflag =  '交易失败';}
	  		else if ('0' == obj.trnsflag){obj.trnsflag =  '交易初始化';}
	  		else if ('1' == obj.trnsflag){obj.trnsflag =  '交易成功';}
	  		else if ('2' == obj.trnsflag){obj.trnsflag =  '交易撤销';}
	  		else if ('3' == obj.trnsflag){obj.trnsflag =  '退单成功';}
	  		else {obj.trnsflag =  '';};
	  		
	  		return obj;
	  		
		}
		
		function initView(){
			$('#order_details').datagrid({
				fitColumns:true,
				nowrap: true,
				striped: true,
				singleSelect:true,
				remoteSort : false,
				idField:"productId",
				columns:[[
					{field:'productName',title:'菜名',width:100,sortable:true,align:'center'},
					{field:'taste',title:'口味',width:100,sortable:true,align:'center',formatter:function(value, rec){
						if (value == "" || value == null) {
							return "<span style=\"color:red;\">无</span>";
						}
					    return value;
						}},
					{field:'specifications',title:'规格',width:100,sortable:true,align:'center',formatter:function(value, rec){
						if (value == "" || value == null) {
							return "<span style=\"color:red;\">无</span>";
						}
					    return value;
						}},
				    {field:'price',title:'价格(元)',width:70,sortable:true,align:'center',formatter:function(value, rec){
					    return value.toFixed(2);
						}},
					{field:'num',title:'数量',width:60,sortable:true,align:'center'},
					{field:'total',title:'小计(元)',width:70,sortable:true,align:'center',formatter:function(value, rec){
						value = rec.price * rec.num;
					    return value.toFixed(2);
					}}
				]]
			});
		}
		
		function query(){
			if(check()){
				var pageOpts = $('#order_pageList').datagrid('getPager').data("pagination").options;
				var param = {};
				param.pageSize = pageOpts.pageSize;
				param.currentPage = pageOpts.pageNumber;	
				//param.mchntCd = currentMchntCd;
				
				$('#order_searchForm').ajaxSubmit( {
					data : param,
					url : contextPath+"/trade/order/list",
					dataType : "json",
		            success : function(result) {
		            	if (result.code == 0) {
		            		var data = result.data;
		            		$('#order_pageList').datagrid('loadData',data);  
		            		if (data.rows == null || data.rows.length == 0) {
		            			$("#order_total").text('0');
		            			$("#order_searchResult").addClass("ct-total0");
							}else{
								$("#order_searchResult").removeClass("ct-total0");
								$("#order_total").text(data.total);
							}
						} else {
							$.messager.alert("提示", result.message);
						}
					}
		        })
			}
	}
		
	function clearForm(){
		$("#order_searchForm").form('clear');
		//$('#order_pageList').datagrid('loadData',{ total: 0, rows: [] });
		$("#order_trnsflag").val("");
	}

	function reload(){
		query();
	}

    return {
        init : init
    }
    
});

	