$(function(){
	initLeftMenu();
	tabClose();
	tabCloseEven();
});

window.onload = cssInit;

//初始化左侧
function initLeftMenu() {
	$("#nav").accordion({animate:false});
	//按seq从小到大排序
	sysMenus.sort(function(a,b){
        return a.menuSeq - b.menuSeq;
        });

    $.each(sysMenus, function(i, n) {
    	//按seq从大到小排序
    	n.subMenus.sort(function(a,b){
            return a.menuSeq - b.menuSeq;
            });
		var menulist ='';
		menulist +='<ul>';
        $.each(n.subMenus, function(j, o) {
			menulist += '<li><div><a ref="'+o.menuId+'" rel="' + o.menuUrl + '" ><span>&nbsp;</span><span class="nav">' + o.menuNm + '</span></a></div></li> ';
        })
		menulist += '</ul>';

		$('#nav').accordion('add', {
            title: n.menuNm,
            content: menulist
        });

    });
	$('#nav ul li a').click(function(){
		var tabTitle = $(this).children('.nav').text();

		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		//var icon = getIcon(menuid,icon);

		addTab(tabTitle,url);
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});

	//选中第一个
	var panels = $('#nav').accordion('panels');
	var t = panels[0].panel('options').title;
    $('#nav').accordion('select', t);
}
//获取左侧导航的图标
function getIcon(menuid){
	var icon = 'icon ';
	$.each(_menus.menus, function(i, n) {
		 $.each(n.menus, function(j, o) {
		 	if(o.menuid==menuid){
				icon += o.icon;
			}
		 })
	})

	return icon;
}

function addTab(subtitle,url){
	
	//如果当前窗口已经打开，要重新刷新一下
	$('#tabs').tabs('close',subtitle);	
	
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			//context:createFrame(url),
			href:url,
			closable:true
		});
	}else{
		$('#tabs').tabs('select',subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function createFrame(url)
{
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose()
{
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}
//绑定右键菜单事件
function tabCloseEven()
{
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update',{
			tab:currTab,
			options:{
				content:createFrame(url)
			}
		})
	})
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			$('#tabs').tabs('close',t);
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

Date.prototype.format = function(format) {
	if (isNaN(this.getMonth())) {
		return '';
	}
	if (!format) {
		format = "yyyy-MM-dd hh:mm:ss";
	}
	var o = {
		/* month */
		"M+" : this.getMonth() + 1,
		/* day */
		"d+" : this.getDate(),
		/* hour */
		"h+" : this.getHours(),
		/* minute */
		"m+" : this.getMinutes(),
		/* second */
		"s+" : this.getSeconds(),
		/* quarter */
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		/* millisecond */
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};


function logout(){
	$.post("logout", function(result){
		if (result.code == 0) {
			window.location.href = "login.jsp";
		}else{
			$.messager.alert("提示", result.message);
		}
	},"json");
}

function doPwdReset(){
	var dlg = $('<div/>').dialog({    
	    title: '修改密码',    
	    width: 500,    
	    height: 200,    
	    closable: false,    
	    cache: false,    
	    href: 'sys/pwd_reset.jsp',    
	    modal: true,
	    buttons : [ {
			text : '保存',
			handler : function(){
			
	    	if($('#pwd_form').form("validate")){
	    		var param = $("#pwd_form").serializeObject();
	    		$.post("mchnt/mchnt/resetPwd",param,function(result){
	    			if (result.code == 0) {
	    				$.messager.alert("提示", "修改成功!");
                		//关闭对话框
                		dlg.dialog('close');
    	        		dlg.remove();
    				} else {
    					$.messager.alert("提示", result.message);
    				}
				},"json");
	    	}
	    }
		},{
			text : '关闭',
			handler : function() {
				dlg.dialog('close');
        		dlg.remove();
			}
		}],
		onLoad : function(){
			$("#pwd_userId").val(userInfo.userId);
		}
	}); 
}


function cssInit(){
	//图片居中
	var pWidth = $("#mainPanle").width();
	var pHeight = $("#mainPanle").height();
	var imgWidth = $("#welcomeLogo").width();
	var imgHeight = $("#welcomeLogo").height();
	var pTop = $("#mainPanle").offset().top;
	var pLeft = $("#mainPanle").offset().left;
	//$("welcomeLogo").offset({top:(pWidth-imgWidth)/2,left:(pHeight-imgHeight)/2});
	//$("#welcomeLogo").css("margin-left", (pWidth*1-imgWidth*1)/2);
	//$("#welcomeLogo").css("margin-top", (pHeight*1-imgHeight*1)/2);
	$("#welcomeLogo").offset({left:(pWidth*1-imgWidth*1)/2+pLeft, top:(pHeight*1-imgHeight*1)/2+pTop});
}