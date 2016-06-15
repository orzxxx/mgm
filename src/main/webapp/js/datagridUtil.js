/**
 * 
 * 使用方法：
 * var options = {
 * 	datagridId:'', //必须
 * 	url:'' //必须
 *  //其他详细看代码及easyui关于datagrid相关api   http://www.jeasyui.com/documentation/datagrid.php
 * };
 * var obj = new Datagrid(options); //即可初始化表格
 * 
 * 使用obj.可调用方法
 */
function Datagrid(options){
	var _self = this;
	var dataGridSelIndex = -1;
	var datagridObj=null;
	
	var defaultOnClickFn=null;
	var datagridId = options["datagridId"];
	
	/**
	 * datagrid默认选项设置
	 */
	var default_opts = {
			datagridId:"",  // datagrid table id 
		    queryParams:{},   //search params name for post, must to be {} 
		    columns:[],   //datagrid columns ,must to be [] 
		    idField:'',
		    url:'',
		    
		    //datagrid default set 
		    fitColumns:true, 
		    nowrap:true, 
		    rownumbers:true,  
		    pagination:true, 
		    pageNumber:1,
			pageSize:10,
			pageList:[10,20,30], 
		    width:"auto", 
		    height:"auto", 
		    singleSelect:false,  
		    remortSort:false,
		    fit:true, 
		    border:true,
		    striped:true
	};
	
	/**
	 * 获取默认选项
	 * @returns
	 */
	this.getDefault_opts = function(){
		return default_opts;
	};
	/**
	 * 设置表格选项
	 * @param options
	 */
	this.setDefault_opts = function(options) {
		default_opts = $.extend(default_opts,options);
	};
	/**
	 * 根据选项名称获取对应值
	 * @param keyName
	 * @returns
	 */
	this.getOptionValueByKeyName = function(keyName){
		return default_opts[keyName];
	};
	/**
	 * 设置选项值
	 */
	this.setOptionValue = function(keyName,keyValue) {
		default_opts[keyName] = keyValue;
	};
	
	this.setDefaultOnClickFn = function(_defaultOnClickFn) {
		defaultOnClickFn = _defaultOnClickFn;
	};
	
	this.getDefaultOnClickFn = function() {
		return defaultOnClickFn;
	};
	
	//setter
	this.setDatagridObj = function(_datagridObj) {
		datagridObj = _datagridObj;
	};
	//getter
	this.getDatagridObj = function() {
		return datagridObj;
	};
	//setter
	this.setDataGridSelIndex = function(_dataGridSelIndex) {
		dataGridSelIndex = _dataGridSelIndex;
	};
	//getter
	this.getDataGridSelIndex = function() {
		return dataGridSelIndex;
	};
	
//	console.info(options);
//	console.info(this.getDefault_opts());
//	default_opts = $.extend(default_opts,options);
	this.setDefault_opts(options);
	
	if(this.getOptionValueByKeyName("datagridId")==null||this.getOptionValueByKeyName("datagridId")=="") {
		ct.messagerAlert("错误", "datagridId参数未设置", "error", null);
		return;
	}
	
	if(this.getOptionValueByKeyName("singleSelect")==true) {
		//如果是单选，在第一列增加单选框
		var frozenColumens = new Array();
		frozenColumens[0]=new Array();
		frozenColumens[0][0]={
			    field: 'oid',
			    title: '选择',
			    width: 50,
			    formatter: function(value, rowData, rowIndex){
			        return '<input type="radio" name="listSelectRadio'+datagridId+ '" id="listSelectRadio' + datagridId + rowIndex + '"    value="' + rowIndex + '" />';
			    }
		};
		if(this.getOptionValueByKeyName("frozenColumns")!=null) {
			for(var i=0;i<this.getOptionValueByKeyName("frozenColumns").length;i++) {
				var temp = this.getOptionValueByKeyName("frozenColumns")[i];
				for(var j=0;j<temp.length;j++) {
					if(i==0){
						frozenColumens[i][j+1]=temp[j];
					} else {
						frozenColumens[i][j]=temp[j];
					}
				}
			}
		}
//		this._default_opts.frozenColumns = frozenColumens;
		this.setOptionValue("frozenColumns", frozenColumens);
		
		//如果是单选，需要对点击事件进行处理
		this.setDefaultOnClickFn(this.getOptionValueByKeyName("onClickRow"));
		var _onClickRow = function(rowIndex, rowData){
			if(_self.getDataGridSelIndex()==rowIndex){
				var el = $(this).parents('.datagrid-view').first();
				$(".datagrid-body-inner input[name='listSelectRadio"+datagridId+"']")[rowIndex].checked = false;
				//奇偶行之间背景颜色不同
				if(rowIndex%2==0) {
					el.find("tr[datagrid-row-index='"+rowIndex+"']").attr("class","datagrid-row");
				} else {
					el.find("tr[datagrid-row-index='"+rowIndex+"']").attr("class","datagrid-row datagrid-row-alt");
				}
		 		_self.setDataGridSelIndex(-1);
			}else{
				$(".datagrid-body-inner input[name='listSelectRadio"+datagridId+"']")[rowIndex].checked = true;
				_self.setDataGridSelIndex(rowIndex);
			}
			if(typeof(_self.getDefaultOnClickFn())=="function") {
//				_self._defaultOnClickFn(rowIndex,rowData);
				_self.getDefaultOnClickFn().apply(this,[rowIndex,rowData]);
			}
		};
		this.setOptionValue("onClickRow", _onClickRow);
	}
	this.init(options,_self);
};


Datagrid.prototype.initDatagrid=function(){
    return $('#'+this.getOptionValueByKeyName("datagridId")).datagrid(this.getDefault_opts()); 
};
/**
 * 初始化分页控件
 * @param datagrid
 * @param _self
 */
Datagrid.prototype.initPagination=function(datagrid,_self){
	var pager=$(datagrid).datagrid('getPager');
    pager.pagination({ 
        onSelectPage:function(pageNumber,pageSize){ 
        	$(datagrid).datagrid({pageNumber:pageNumber,pageSize:pageSize});
        	_self.setDataGridSelIndex(-1);
        }
    }); 
};

/**
 * 初始化表格
 * @param opts
 * @param _self
 * @returns
 */
Datagrid.prototype.init=function(opts,_self){
    var datagrid = this.initDatagrid();
    //如果存在分页控件的话，则需对分页控件进行处理
    if(this.getOptionValueByKeyName("pagination")) {
    	this.initPagination(datagrid,_self);
    }
    this.setDatagridObj(datagrid);
    return datagrid;
};
/**
 * 根据keyName获取选中行对应的keyValue
 * @param keyName
 * @returns 
 */
Datagrid.prototype.getValueByKeyName=function(keyName) {
	var rows = $('#'+this.getOptionValueByKeyName("datagridId")).datagrid('getChecked');
	var check = [];
	for(var i=0;i<rows.length;i++) {
		check.push(rows[i][keyName]);
	}
	if(check.length==0) {
		return null;
	}
	return check.toString();
};
/**
 * 获取所有选中的行
 * @returns  返回选择的行
 */
Datagrid.prototype.getChecked = function() {
	var rows = $('#'+this.getOptionValueByKeyName("datagridId")).datagrid('getChecked');
	return rows;
};
/**
 * Return the first selected row record or null.
 * @returns
 */
Datagrid.prototype.getSelected = function() {
	var row = $('#'+this.getOptionValueByKeyName("datagridId")).datagrid('getSelected');
	return row;
};
/**
 * Load and show the first page rows. If the 'param' is specified, it will replace with the queryParams property. Usually do a query by passing some parameters, this method can be called to load new data from server.
 * @param param
 */
Datagrid.prototype.load = function(param) {
	$('#'+this.getOptionValueByKeyName("datagridId")).datagrid('load',param);
	this.setDataGridSelIndex(-1);
};

/**
 * Reload the rows. Same as the 'load' method but stay on current page.
 */
Datagrid.prototype.reload=function(param) {
	$('#'+this.getOptionValueByKeyName("datagridId")).datagrid('reload',param);
	this.setDataGridSelIndex(-1);
};




