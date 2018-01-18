$.ajaxSetup({async : true, global: false, cache: false});
var Container = function(isPagination, targetDivId, tableId, pageSize) {
	var object = new Object;
	object.dataCache;
	object.headerHtml;
	object.isPagination = isPagination;
	object.targetDivId = targetDivId;
	object.pagination;
	object.tableId = tableId;
	object.headArray; //是head数组
	object.AjaxUrl;
	object.params = {};
	object.callbackFun;
	object.sortable = false;
	object.sorter = null;
	object.data;
	object.begin;
	object.end;
	object.valign = 'middle';
	object.tableClassName = 'container';
	object.setTableHeadInfo = null;
	object.headFormatter = null;
	if(pageSize) {
		object.pageSize = pageSize;
	}
	//组合列表头
	object.init = function(headers) {
		object.headArray = headers;
		if (headers == null || headers == undefined || headers.length == 0) {
			return;
		}
		object.headerHtml = '<table id="' + object.tableId + '" class="' + object.tableClassName + '" cellspacing="0" cellpadding="0">';
		object.headerHtml += '<tr id="' + object.tableId + '_head" class="container_head">';
		for (var i = 0; i < object.headArray.length; i++) {
			var sortable = object.headArray[i].sortable;
			//添加排序器
			var sorterImgHtml = "";
			var istarget = object.headArray[i].istarget;
			if (sortable !== null && sortable !== undefined && sortable && object.sorter === null) {
				object.sortable = true;
				if (istarget === undefined || istarget === null) {
					istarget = false;
				}
				object.sorter = new sorter(object);
			}
			object.headFormatter = object.headArray[i].headFormatter;
			if (object.headArray[i].sortable === true) {
				if (istarget) {
					sorterImgHtml = object.sorter.getImgHtml(object.headArray[i]);
					object.sorter.params = {
						'sortName': object.headArray[i].code,
						'direction': object.sorter.defaultDirection
					};
					object.headerHtml += '<td width="'+object.headArray[i].width+'" sortable="' + object.headArray[i].sortable + '" index="' + object.headArray[i].index + '" direction="' + object.sorter.defaultDirection + '" id="' + object.headArray[i].code + '" desc="' + this.headArray[i].headName + '" istarget=' + object.headArray[i].istarget + '><a href="javascript:void(0)">' + this.headArray[i].headName + sorterImgHtml + '</a></td>';
				}
				if (!istarget) {
					object.headerHtml += '<td width="'+object.headArray[i].width+'" sortable="' + object.headArray[i].sortable + '" index="' + object.headArray[i].index + '" direction="' + object.sorter.defaultDirection + '" id="' + object.headArray[i].code + '" desc="' + this.headArray[i].headName + '" istarget=' + object.headArray[i].istarget + '><a href="javascript:void(0)">' + this.headArray[i].headName + '</a></td>';
				}
			} else if ((typeof object.headFormatter) == 'function') {
				var formatter = object.headFormatter;
				object.headerHtml += '<td width="'+object.headArray[i].width+'" sortable="' + object.headArray[i].sortable + '" index="' + object.headArray[i].index + '"id="' + object.headArray[i].code + '" desc="' + this.headArray[i].headName + '">' + formatter() + '</td>';
			} else {
				object.headerHtml += '<td width="'+object.headArray[i].width+'" sortable="' + object.headArray[i].sortable + '" index="' + object.headArray[i].index + '"id="' + object.headArray[i].code + '" desc="' + this.headArray[i].headName + '">' + this.headArray[i].headName + '</td>';
			}
		}
		object.headerHtml += '</tr></table>';
		$("#" + object.targetDivId).html(object.headerHtml);

		//如果有排序的列 在这列上加上事件触发 
		if (object.sortable == true) {
			object.sorter.addEvent();
		}
	};
	object.destroy = function() {
		$("#" + object.targetDivId).text("");
	};

	//事先组合列表头
	object.init = function(headers, url, params) {
		object.AjaxUrl = url;
		object.params = params;
		object.headArray = headers;
		if (headers == null || headers == undefined || headers.length == 0) {
			return;
		}
		object.headerHtml = '<table id="' + object.tableId + '" class="' + object.tableClassName + '" cellspacing="0" cellpadding="0">';
		object.headerHtml += '<tr id="' + object.tableId + '_head" class="container_head">';
		for (var i = 0; i < object.headArray.length; i++) {			
			var sortable = object.headArray[i].sortable;
			//添加排序器
			var sorterImgHtml = "";
			var istarget = object.headArray[i].istarget;
			if (sortable !== null && sortable !== undefined && sortable && object.sorter === null) {
				object.sortable = true;
				if (istarget === undefined || istarget === null) {
					istarget = false;
				}
				object.sorter = new sorter(object);
			}
			object.headFormatter = object.headArray[i].headFormatter;
			if (object.headArray[i].sortable === true) {
				if (istarget) {
					sorterImgHtml = object.sorter.getImgHtml(object.headArray[i]);
					object.sorter.params = {
						'sortName': object.headArray[i].code,
						'direction': object.sorter.defaultDirection
					};
					object.headerHtml += '<th width="'+object.headArray[i].width+'" sortable="' + object.headArray[i].sortable + '" index="' + object.headArray[i].index + '" direction="' + object.sorter.defaultDirection + '" id="' + object.headArray[i].code + '" desc="' + this.headArray[i].headName + '" istarget=' + object.headArray[i].istarget + '><a href="javascript:void(0)">' + this.headArray[i].headName + sorterImgHtml + '</a></th>';
				}
				if (!istarget) {
					object.headerHtml += '<th width="'+object.headArray[i].width+'" sortable="' + object.headArray[i].sortable + '" index="' + object.headArray[i].index + '" direction="' + object.sorter.defaultDirection + '" id="' + object.headArray[i].code + '" desc="' + this.headArray[i].headName + '" istarget=' + object.headArray[i].istarget + '><a href="javascript:void(0)">' + this.headArray[i].headName + '</a></th>';
				}
			} else if ((typeof object.headFormatter) == 'function') {				
				var formatter = object.headFormatter;
				object.headerHtml += '<th width="'+object.headArray[i].width+'" sortable="' + object.headArray[i].sortable + '" index="' + object.headArray[i].index + '"id="' + object.headArray[i].code + '" desc="' + this.headArray[i].headName + '">' + formatter() + '</th>';
			} else {
				object.headerHtml += '<th width="'+object.headArray[i].width+'" sortable="' + object.headArray[i].sortable + '" index="' + object.headArray[i].index + '"id="' + object.headArray[i].code + '" desc="' + this.headArray[i].headName + '">' + this.headArray[i].headName + '</th>';
			}
		}
		object.headerHtml += '</tr></table>';
		$("#" + object.targetDivId).html(object.headerHtml);
		//如果有排序的列 在这列上加上事件触发 
		if (object.sortable == true) {
			object.sorter.addEvent();
		}
	};
	
	//start loading image
	object.startloadingImg = function() {
			$("#zeroRecordDiv_" + object.targetDivId).remove();
			$("#" + object.targetDivId + " tr").remove(".data");
			$("#" + object.tableId).after("<div id='loadingDiv_" + object.targetDivId + "' style='display:none,align:center' width='100%' height='30'><img height='30' src='../images/loading.gif'></div>");
			$("#loadingDiv_" + object.targetDivId).attr('display', '');
	};
	//stop loading image
	object.stoploadingImg = function() {
			$("#loadingDiv_" + object.targetDivId).detach();
	};

	//ajax获取数据
	object.getDataByAjax = function(url, params, callbackFun) {
		object.startloadingImg();
		object.AjaxUrl = url;
		object.params = object.mergeJson(object.params, params);
		object.callbackFun = callbackFun;
		var p = object.params;
		if (object.sortable == true) {
				p = object.mergeJson(object.params, object.sorter.params);
		}
		var ajaxSuccessCallback = function(data){
			object.stoploadingImg();
						if (data.invalidSession == true) {
							window.location.reload();
						} else {
							if(!data.pageSize) {
								data.pageSize = object.pageSize;
								data.pageCount = parseInt((data.total - 1)/object.pageSize) + 1;
								data.pageNo = parseInt(data.start/object.pageSize) + 1;
								data.cacheSize = 1;
								data.beginPage = data.pageNo;
							}
							object.dataCache = data.result;
							object.data = data;
							//创建分页组件
							var pagination = new newPagination(object);
							//需要分页就初始化分页组件
							if (Boolean(object.isPagination)) {
								object.pagination = pagination;
								pagination.pageBean.pageNo = data.pageNo;
								pagination.pageBean.pageCount = data.pageCount;
								pagination.pageBean.pageSize = data.pageSize;
								pagination.pageBean.cacheSize = data.cacheSize;
								pagination.init();
							}
							if (Boolean(object.isPagination)) {
								if (pagination.pageNo <= pagination.cacheSize) {
									object.render(object.dataCache, ((pagination.pageNo - 1) * pagination.pageSize + 1), ((pagination.pageNo - 1) * pagination.pageSize + pagination.pageSize));
								} else {
									var beginPageNo = pagination.getBeginPage(pagination.pageNo);
									var localPageNo = (pagination.pageNo - beginPageNo) + 1;
									object.render(object.dataCache, ((localPageNo - 1) * pagination.pageSize + 1), ((localPageNo - 1) * pagination.pageSize + pagination.pageSize));
								}
							} else {
								object.render(object.dataCache, 1, object.dataCache.length);
							}
						}
			};
			var ajaxParam = {
					type : "post",
					url : url,
					data : p,
					dataType : "json",
					success : ajaxSuccessCallback,
					error : function(content) {
						object.stoploadingImg();
						if (content.status == 500) {
							alert("系统出错！");
						}
					}
				};
			$.ajax(ajaxParam);
	};

	//组合数据
	object.render = function(result, begin, end) {
		if (end === undefined) {
			end = 0;
		}
		object.begin = begin;
		object.end = end;
		$("#zeroRecordDiv_" + object.targetDivId).remove();
		var listHtml = "";
		$("#" + object.targetDivId + " tr").remove(".data");
		if (end > result.length) {
			end = result.length;
		}
		if (result.length === 0) {
			$("#" + object.tableId).after("<div id='zeroRecordDiv_" + object.targetDivId + "' style='text-align:center;'>对不起! 共找到0条记录 </div>");
		}
		var x = 1;
		if(object.data.beginPage >= 2) x += (object.data.beginPage - 1)*object.data.pageSize;
		for (var i = begin; i <= end; i++) {
			if (Math.round(i % 2) == 0) {
				listHtml += '<tr class="tr_even data" id="data'+ i +'" onmouseover="displayImg('+i+');" onmouseout="hideImg('+i+');">';
				for (var a = 0; a < object.headArray.length; a++) {
					var code = object.headArray[a].code;
					var formatter = object.headArray[a].formatter;
					var noWrap = object.headArray[a].nowrap;
					var valign = object.headArray[a].valign;
					var halign =  object.headArray[a].halign;
					var className = object.headArray[a].className;
					var align = object.headArray[a].align;
					var isEmptyClassName = false;
					var isEmptyHalign = ($.trim(halign).length==0);
					if ((typeof formatter) == 'function') {
						listHtml += '<td align="' + $.trim(align) + '" valign="' + $.trim(valign) + '" class="' + (isEmptyClassName ? "": className) + (Boolean(noWrap) == true ? " nowrap ": " ") +(isEmptyHalign?"":halign)+ '">' + formatter(result[(i - 1)], i) + '</td>';
						if(formatter(result[(i - 1)], i) == "未发送" || formatter(result[(i - 1)], i) == "未保存"){
							var replaceNum = listHtml.lastIndexOf('type="checkbox"');
							listHtml = listHtml.substring(0,replaceNum)+'type="checkbox" disabled=true'+listHtml.substring(replaceNum+15,listHtml.length);
						}
					} else {
						if (code === "defaultRank") {
							listHtml += '<td align="' + $.trim(align) + '" valign="' + $.trim(valign) + '"  class="' + (isEmptyClassName ? "": className) + (Boolean(noWrap) == true ? " nowrap ": " ") +(isEmptyHalign?"":halign)+ '">' + x + '</td>';
						} else {
							var contentStr = checkNull(eval('result[(i-1)].' + object.headArray[a].code));
							/*if(!contentStr || contentStr == "undefined") contentStr = "";*/
							listHtml += '<td align="' + $.trim(align) + '" valign="' + $.trim(valign) + '" class="' + (isEmptyClassName ? "": className) + (Boolean(noWrap) == true ? " nowrap ": " ") +(isEmptyHalign?"":halign)+ '">' + contentStr + '</td>';
						}
					}
				}
				listHtml += '</tr>';
			} else {
				listHtml += '<tr class="tr_odd data" id="data'+ i +'" onmouseover="displayImg('+i+');" onmouseout="hideImg('+i+');">';
				for (var a = 0; a < object.headArray.length; a++) {
					var code = object.headArray[a].code;
					var formatter = object.headArray[a].formatter;
					var className = object.headArray[a].className;
					var noWrap = object.headArray[a].nowrap;
					var halign =  object.headArray[a].halign;
					var valign = object.headArray[a].valign;
					var align = object.headArray[a].align;
					var isEmptyClassName = false;
					var isEmptyHalign = ($.trim(halign).length==0);
					if ((typeof formatter) == 'function') {
						listHtml += '<td align="' + $.trim(align) + '" valign="' + $.trim(valign) + '"  class="' + (isEmptyClassName ? "": className) + (Boolean(noWrap) == true ? " nowrap ": " ")+(isEmptyHalign?"":halign) + '">' + formatter(result[(i - 1)], i) + '</td>';
						if(formatter(result[(i - 1)], i) == "未发送" || formatter(result[(i - 1)], i) == "未保存"){
							var replaceNum = listHtml.lastIndexOf('type="checkbox"');
							listHtml = listHtml.substring(0,replaceNum)+'type="checkbox" disabled=true'+listHtml.substring(replaceNum+15,listHtml.length);
						}
					} else {
						if (code === "defaultRank") {
							listHtml += '<td align="' + $.trim(align) + '" valign="' + $.trim(valign) + '"  class="' + (isEmptyClassName ? "": className) + (Boolean(noWrap) == true ? " nowrap ": " ")+(isEmptyHalign?"":halign) + '">' + x + '</td>';
						} else {
							var contentStr = eval('result[(i-1)].' + object.headArray[a].code);
							if(!contentStr || contentStr == "undefined") contentStr = "";
							listHtml += '<td align="' + $.trim(align) + '" valign="' + $.trim(valign) + '"  class="' + (isEmptyClassName ? "": className) + (Boolean(noWrap) == true ? " nowrap ": " ") +(isEmptyHalign?"":halign)+ '">' + contentStr + '</td>';
						}
					}
				}
				listHtml += '</tr>';
			}
			listHtml += '<tr><td class="detail" colspan="'+ object.headArray.length +'" id="detail'+ i +'" style="display: none;border-bottom: 1px #c4d9ee solid;" align="center"></td></tr>';
			x++;
		}
		var table = $("#" + object.tableId);
		if (table != null) {
			$("#" + object.tableId + " tr").remove(".data");;
		}

		$("#" + object.tableId + "_head").after(listHtml);
		if ($.isFunction(object.setTableHeadInfo)) {
				object.setTableHeadInfo(object.data);
		}
		if ($.isFunction(object.callbackFun)) {
				object.callbackFun(object.data);
		}

	};

	//重新渲染列表组件
	object.renderByPage = function(pageNo, cacheSize) {
		if (pageNo <= cacheSize) {
			object.render(object.dataCache, ((pageNo - 1) * object.pagination.pageSize + 1), ((pageNo - 1) * object.pagination.pageSize + (object.pagination.pageSize)));
		} else {
			var beginPage = object.pagination.getBeginPage(pageNo);
			var endPage = object.pagination.getEndPage(pageNo);
			var pageSize = object.pagination.pageSize;
			var begin = (pageNo - beginPage) * pageSize + 1;
			var end = (pageNo - beginPage) * pageSize + pageSize;
			object.render(object.dataCache, begin, end);
		}
	};
	//合并json数据
	object.mergeJson = function mergeJson(jsonbject1, jsonbject2) {
		var resultJsonObject = {};
		for (var attr in jsonbject1) {
			resultJsonObject[attr] = jsonbject1[attr];
		}
		for (var attr in jsonbject2) {
			resultJsonObject[attr] = jsonbject2[attr];
		}
		return resultJsonObject;
	};

	object.setTableClass = function(className) {
		object.tableClassName = className;
	};

	object.setCallbackFun = function(fun) {
		object.callbackFun = fun;
	};

	return object;
};
function displayImg(i){
	$("#data"+i).css("background-color","rgb(201, 234, 249)");
}
function hideImg(i){
	if(Math.round(i % 2) == 0) {
		$("#data"+i).css("background-color","#ffffff");
	}else{
		$("#data"+i).css("background-color","rgb(245, 243, 244)");
	}
}
//分页组件
var Pagination = function(tableContainer) {
	var object = new Object;
	object.tableContainer = tableContainer;
	object.pageBean = {};
	object.pageNo; //页数			
	object.pageCount; //页面总数
	object.pageSize; //页面上展示条数
	object.cacheSize; //缓存页数
	object.paginationHtml; //分页HTML
	//初始化
	object.init = function() {
		object.pageNo = object.pageBean.pageNo;
		object.pageCount = object.pageBean.pageCount;
		object.pageSize = object.pageBean.pageSize;
		object.cacheSize = object.pageBean.cacheSize;
		object.composite();
		object.bindEvent(); //添加事件
	};
	
	//组装页面元素
	object.composite = function() {
		var ifBindEvent = false;
		if ($('#' + object.tableContainer.tableId + '_pagination') != null || $('#' + object.tableContainer.tableId + '_pagination') != undefined) {
			$('#' + object.tableContainer.tableId + '_pagination').remove();
			ifBindEvent = true;
		}
		var page = '<table id="' + object.tableContainer.tableId + '_pagination"  border="0" cellpadding="0" cellspacing="0" width="100%">';
		page += '<tr align="right">';
		//page += '<td height="25"> </td><td class="nextpage" height="25" width="400" ></td>';	       
		if (object.pageNo < object.pageCount && object.pageNo > 1) {
			page += '<td  class="nextpage" bgcolor="#FFFFFF" height="0" valign="top" align="right">' + '第' + object.pageNo + '页/共' + object.pageCount + '页';
			page += '转到<input name="pageid" size="2" id="' + object.tableContainer.tableId + '_pageid" type="text" />页' + '</td><td width="26"><a href="javascript:void(0)"><img  id="' + object.tableContainer.tableId + '_togo" src="/img/bt_go.gif" alt="" class="container_togo" /></a>';
			page += '</td><td width="80"><a title="上页" href="javascript:void(0)" id="' + object.tableContainer.tableId + '_before" pageNo="' + (object.pageNo - 1) + '"> 上一页</a>';
			page += '<a title="下页" href="javascript:void(0)"  id="' + object.tableContainer.tableId + '_next" pageNo="' + (parseInt(object.pageNo) + 1) + '">下一页 </a> ';
			page += '</td>';
		}
		page += '</tr>';
		if (object.pageNo < object.pageCount && object.pageNo == 1) {
			page += '<td width="100%" class="nextpage" bgcolor="#FFFFFF" height="0" valign="top">第' + object.pageNo + '页/共' + object.pageCount + '页';
			page += '转到<input name="pageid" size="2" id="' + object.tableContainer.tableId + '_pageid" width="3" type="text">页' + '</td><td width="26"><a href="javascript:void(0)"><img  id="' + object.tableContainer.tableId + '_togo" src="/img/bt_go.gif" alt="" class="container_togo"></a>';
			page += '</td><td width="80"><a title="下页" href="javascript:void(0)"  id="' + object.tableContainer.tableId + '_next" pageNo="' + (parseInt(object.pageNo) + 1) + '">下一页 </a>';
			page += '</td>';
		}
		if (object.pageNo >= object.pageCount && object.pageNo > 1) {
			page += '<td width="100%" class="nextpage" bgcolor="#FFFFFF" height="0" valign="top">第' + object.pageNo + '页/共' + object.pageCount + '页';
			page += '转到<input name="pageid" size="2" id="' + object.tableContainer.tableId + '_pageid" width="3" type="text">页' + '</td><td width="26"><a href="javascript:void(0)">' + '<img class="right" id="' + object.tableContainer.tableId + '_togo" src="/img/bt_go.gif" alt="" class="container_togo"></a>';

			page += '</td><td width="80"><a title="上页" href="javascript:void(0)"  id="' + object.tableContainer.tableId + '_before" pageNo="' + (object.pageNo - 1) + '"> 上一页</a>';
			page += '</td>';
		}
		if (object.pageCount == 1 && object.pageNo == 1 || object.pageCount == 0) {
			page += '';
		}
		page += '</tr>';
		page += '</table>';
		$("#" + object.tableContainer.targetDivId).append(page);

		if (ifBindEvent) {
			object.bindEvent(); //添加事件
		}

	};

	object.bindEvent = function() {
		//隐藏展开内容
		for(var i=1;i<=20;i++){
			if(!($("#detail"+i).is(":hidden"))){
				$("#detail"+i).hide();
			}
		}
		
		//当页面在
		if (object.pageNo < object.pageCount && object.pageNo > 1) {
			$('#' + object.tableContainer.tableId + '_before').unbind('click');
			$('#' + object.tableContainer.tableId + '_next').unbind('click');
			$('#' + object.tableContainer.tableId + '_togo').unbind('click');
			$('#' + object.tableContainer.tableId + '_pageid').unbind('keydown');

			// 上一页
			$('#' + object.tableContainer.tableId + '_before').bind('click', object.beforePage);
			//下一页
			$('#' + object.tableContainer.tableId + '_next').bind('click', object.nextPage);
			//跳转
			$('#' + object.tableContainer.tableId + '_togo').bind('click', object.forwordPage);

			$('#' + object.tableContainer.tableId + '_pageid').bind('keydown', object.forwordPageKeydown);

		}
		if (object.pageNo < object.pageCount && object.pageNo == 1) {

			$('#' + object.tableContainer.tableId + '_next').unbind('click');
			$('#' + object.tableContainer.tableId + '_togo').unbind('click');
			$('#' + object.tableContainer.tableId + '_pageid').unbind('keydown');

			//下一页
			$('#' + object.tableContainer.tableId + '_next').bind('click', object.nextPage);
			//跳转
			$('#' + object.tableContainer.tableId + '_togo').bind('click', object.forwordPage);
			$('#' + object.tableContainer.tableId + '_pageid').bind('keydown', object.forwordPageKeydown);
		}
		if (object.pageNo >= object.pageCount && object.pageNo > 1) {
			$('#' + object.tableContainer.tableId + '_before').unbind('click');
			$('#' + object.tableContainer.tableId + '_togo').unbind('click');
			$('#' + object.tableContainer.tableId + '_pageid').unbind('keydown');
			// 上一页
			$('#' + object.tableContainer.tableId + '_before').bind('click', object.beforePage);
			//跳转
			$('#' + object.tableContainer.tableId + '_togo').bind('click', object.forwordPage);
			$('#' + object.tableContainer.tableId + '_pageid').bind('keydown', object.forwordPageKeydown);
		}
		$('.container_paginationNumLink').unbind('click');
		$('.container_paginationNumLink').bind('click', object.pageNumEvent);
	};
	object.beforePage = function(event) {
		$('#' + object.tableContainer.tableId + '_head input[type="checkbox"]').attr("checked",false);
		object.tableContainer.stoploadingImg();
		object.tableContainer.startloadingImg();
		//隐藏展开内容
		for(var i=1;i<=20;i++){
			if(!($("#detail"+i).is(":hidden"))){
				$("#detail"+i).hide();
			}
		}
		
		var changedPageNo = $('#' + object.tableContainer.tableId + '_before').attr('pageNo');
		var pageSize = object.pageSize;
		var cacheSize = object.cacheSize;
		//如果没有超过cache的数量就走第一个
		if (object.isOutOfCache(changedPageNo)) {
			object.pageNo = changedPageNo;
			object.tableContainer.renderByPage(changedPageNo, cacheSize);
			object.composite();
		} else {
			$("#"+object.tableContainer.tableId+"_pagination").empty();
			$("#"+object.tableContainer.tableId+"_dataList").empty();
			//alert('');
			//如果超过就通过json来获取数据
			var params = {
				'pageBean.pageNo': changedPageNo
			};
			params = new mergeJson(object.tableContainer.params, params);
			params = new mergeJson(params, object.getPageRange(changedPageNo));

			if (object.tableContainer.sortable == true) {
				params = new mergeJson(params, object.tableContainer.sorter.params);
			}

			var ajaxSuccessCallback = function(data){
				object.tableContainer.stoploadingImg();
							if (data.invalidSession == true) {
								window.location.reload();
							} else {
								object.tableContainer.dataCache = data.result;
								object.tableContainer.data = data;
								object.pageNo = changedPageNo;
								object.tableContainer.renderByPage(changedPageNo, cacheSize);
								object.composite();
							}
				
				
							
			};

			var ajaxParam = {
					type : "post",
					url : object.tableContainer.AjaxUrl,
					data : params,
					dataType : "json",
					async : true,
					cache: false,
					success : ajaxSuccessCallback,
					error:function(){
						object.tableContainer.stoploadingImg();
					}
			};
			$.ajax(ajaxParam);
		}
	};
	object.nextPage = function(event) {
		$('#' + object.tableContainer.tableId + '_head input[type="checkbox"]').attr("checked",false);
		object.tableContainer.stoploadingImg();
		object.tableContainer.startloadingImg();
		//隐藏展开内容
		for(var i=1;i<=20;i++){
			if(!($("#detail"+i).is(":hidden"))){
				$("#detail"+i).hide();
			}
		}
		
		var changedPageNo = $('#' + object.tableContainer.tableId + '_next').attr('pageNo');
		var pageSize = object.pageSize;
		var cacheSize = object.cacheSize;
		if (object.isOutOfCache(changedPageNo)) {
			object.pageNo = changedPageNo;
			object.tableContainer.renderByPage(changedPageNo, cacheSize);

			object.composite();
		} else {
			$("#"+object.tableContainer.tableId+"_pagination").empty();
			$("#"+object.tableContainer.tableId+"_dataList").empty();
			//如果超过就通过json来获取数据
			var params = {
				'pageBean.pageNo': changedPageNo
			};
			
			params = new mergeJson(object.tableContainer.params, params);
			params = new mergeJson(params, object.getPageRange(changedPageNo));

			if (object.tableContainer.sortable == true) {
				params = new mergeJson(params, object.tableContainer.sorter.params);
			}
			
			
			var ajaxSuccessCallback = function(data){
				object.tableContainer.stoploadingImg();
							if (data.invalidSession == true) {
								window.location.reload();
							} else {
								object.tableContainer.dataCache = data.result;
								object.tableContainer.data = data;
								object.pageNo = changedPageNo;
								object.tableContainer.renderByPage(changedPageNo, cacheSize);
								object.composite();
							}
							
			};
			
			var ajaxParam = {
					type : "post",
					url : object.tableContainer.AjaxUrl,
					data : params,
					dataType : "json",
					async : true,
					cache: false,
					success : ajaxSuccessCallback,
					error:function(){
						object.tableContainer.stoploadingImg();
					}
			};
			$.ajax(ajaxParam);
		}
	};
	object.forwordPage = function(event) {
		$('#' + object.tableContainer.tableId + '_head input[type="checkbox"]').attr("checked",false);
		object.tableContainer.stoploadingImg();
		object.tableContainer.startloadingImg();
		
		//隐藏展开内容
		for(var i=1;i<=20;i++){
			if(!($("#detail"+i).is(":hidden"))){
				$("#detail"+i).hide();
			}
		}
		
		var changedPageNo = $('#' + object.tableContainer.tableId + '_pageid').val();
		if (changedPageNo == undefined || changedPageNo == null || changedPageNo.length === 0) {
			alert("请输入页数.");
			return;
		}
		if (isNaN(changedPageNo)) {
			alert("请输入正确的页数.");
			return;
		}
		if(Number(changedPageNo) < 1){
				changedPageNo = 1;
		}
		changedPageNo = Number(changedPageNo);
		var pageNoDefine = Math.ceil(Number(changedPageNo));
		if (changedPageNo < pageNoDefine) {
			alert("请输入正确的页数.");
			return;
		}
		if (changedPageNo > object.pageCount) {
			changedPageNo = object.pageCount;
		}
		var pageSize = object.pageSize;
		var cacheSize = object.cacheSize;
		if (object.isOutOfCache(changedPageNo)) {
			object.pageNo = changedPageNo;
			object.tableContainer.renderByPage(changedPageNo, cacheSize);
			object.composite();
		} else {
			$("#"+object.tableContainer.tableId+"_pagination").empty();
			$("#"+object.tableContainer.tableId+"_dataList").empty();
			//如果超过就通过json来获取数据
			var params = {
				'pageBean.pageNo': changedPageNo
			};
			params = new mergeJson(object.tableContainer.params, params);
			params = new mergeJson(params, object.getPageRange(changedPageNo));

			if (object.tableContainer.sortable == true) {
				params = new mergeJson(params, object.tableContainer.sorter.params);
			}

			var ajaxSuccessCallback = function(data){
				object.tableContainer.stoploadingImg();
				if (data.invalidSession == true) {
					window.location.reload();
				} else {
					object.tableContainer.dataCache = data.result;
					object.tableContainer.data = data;
					object.pageNo = changedPageNo;
					object.tableContainer.renderByPage(changedPageNo, cacheSize);
					object.composite();
				}
			};

			var ajaxParam = {
					type : "post",
					url : object.tableContainer.AjaxUrl,
					data : params,
					dataType : "json",
					async : true,
					cache: false,
					success : ajaxSuccessCallback,
					error:function(){
						object.tableContainer.stoploadingImg();
					}
			};
			$.ajax(ajaxParam);
		}
	};

	object.forwordPageKeydown = function(event) {
		$('#' + object.tableContainer.tableId + '_head input[type="checkbox"]').attr("checked",false);
		if (event.keyCode == 13) {
			//隐藏展开内容
			for(var i=1;i<=20;i++){
				if(!($("#detail"+i).is(":hidden"))){
					$("#detail"+i).hide();
				}
			}
			object.forwordPage(event);
		} else {
			return;
		}
	};

	object.pageNumEvent = function(event) {
		$('#' + object.tableContainer.tableId + '_head input[type="checkbox"]').attr("checked",false);
		object.tableContainer.stoploadingImg();
		object.tableContainer.startloadingImg();
		//隐藏展开内容
		for(var i=1;i<=20;i++){
			if(!($("#detail"+i).is(":hidden"))){
				$("#detail"+i).hide();
			}
		}
		
		var pageNo = $(this).attr('pageNo');
		var changedPageNo = parseInt(pageNo);
		var pageSize = object.pageSize;
		var cacheSize = object.cacheSize;
		if (object.isOutOfCache(changedPageNo)) {
			object.pageNo = changedPageNo;
			object.tableContainer.renderByPage(changedPageNo, cacheSize);
			object.composite();
		} else {
			$("#"+object.tableContainer.tableId+"_pagination").empty();
			$("#"+object.tableContainer.tableId+"_dataList").empty();
			//如果超过就通过json来获取数据
			var params = {
				'pageBean.pageNo': changedPageNo
			};
			params = new mergeJson(object.tableContainer.params, params);
			params = new mergeJson(params, object.getPageRange(changedPageNo));
			if (object.tableContainer.sortable == true) {
				params = new mergeJson(params, object.tableContainer.sorter.params);
			}
			var ajaxSuccessCallback = function(data){
				object.tableContainer.stoploadingImg();
						if (data.invalidSession == true) {
							window.location.reload();
						} else {
							object.tableContainer.dataCache = data.result;
							object.tableContainer.data = data;
							object.pageNo = changedPageNo;
							object.tableContainer.renderByPage(changedPageNo, cacheSize);
							object.composite();
						}
							
			};
			var ajaxParam = {
					type : "post",
					url : object.tableContainer.AjaxUrl,
					data : params,
					dataType : "json",
					async : true,
					cache: false,
					success : ajaxSuccessCallback,
					error:function(){
						object.tableContainer.stoploadingImg();
					}
			};
			$.ajax(ajaxParam);
		}
	};

	object.getPageRange = function(changedPageNo) {
		var closeValue = Math.ceil(changedPageNo / object.cacheSize);
		var beginPage = (closeValue * object.cacheSize + 1 - object.cacheSize);
		var endPage = (closeValue * object.cacheSize);
		return {
			'pageBean.beginPage': beginPage,
			'pageBean.endPage': endPage
		};
	};

	object.getBeginPage = function(changedPageNo) {
		var closeValue = Math.ceil(changedPageNo / object.cacheSize);
		var beginPage = (closeValue * object.cacheSize + 1 - object.cacheSize);
		return beginPage;
	};

	object.getEndPage = function(changedPageNo) {
		var closeValue = Math.ceil(changedPageNo / object.cacheSize);
		var endPage = (closeValue * object.cacheSize);
		return endPage;
	};

	object.isOutOfCache = function(changedPageNo) {
		var num1 = Math.ceil(changedPageNo / object.cacheSize);
		var num2 = Math.ceil(object.pageNo / object.cacheSize);
		return num1 == num2;
	};
	return object;
};
function mergeJson(jsonbject1, jsonbject2) {
	var resultJsonObject = {};
	for (var attr in jsonbject1) {
		resultJsonObject[attr] = jsonbject1[attr];
	}
	for (var attr in jsonbject2) {
		resultJsonObject[attr] = jsonbject2[attr];
	}
	return resultJsonObject;
};

var newPagination = function(tableContainer) {
	var object = new Pagination(tableContainer);
	object.paginationNum = 6;
	object.composite = function() {
		var ifBindEvent = false;
		if ($('#' + object.tableContainer.tableId + '_pagination') != null || $('#' + object.tableContainer.tableId + '_pagination') != undefined) {
			$('#' + object.tableContainer.tableId + '_pagination').remove();
			ifBindEvent = true;
		}
		var currentPage = object.pageNo; //当前页数
		var totalPage = object.pageCount;
		var page = "";
		if (object.pageNo < object.pageCount && object.pageNo > 1) {
			page = '<div id="' + object.tableContainer.tableId + '_pagination" class="paging">';
			page += '<div class="paging-bottom">';
			page += '<span class="paging_input"> 共' + totalPage + '页 转到第<input id="' + object.tableContainer.tableId + '_pageid" type="text" title="指定页码" name="jumpto" size="3" value=""/>页<img src="../images/button_go.gif"  id="' + object.tableContainer.tableId + '_togo" title="指定页码" class="container_togo"></a></span>';
			page += '<span class="paging_text">';
			page += '<a class="paging_pre" title="上页" href="javascript:void(0)" id="' + object.tableContainer.tableId + '_before" pageNo="' + (currentPage - 1) + '"> <span>上一页</span> </a>';
			page += object.getButtonsPagination(currentPage);
			page += '<a class="paging_next" title="下页" href="javascript:void(0)"  id="' + object.tableContainer.tableId + '_next" pageNo="' + (parseInt(currentPage) + 1) + '"> <span>下一页</span> </a>';
			page += '</span>';
			page += '</div>';
			page += '</div>';
		}

		if (object.pageNo < object.pageCount && object.pageNo == 1) {
			page = '<div id="' + object.tableContainer.tableId + '_pagination" class="paging">';
			page += '<div class="paging-bottom">';
			page += '<span class="paging_input"> 共' + totalPage + '页 转到第<input id="' + object.tableContainer.tableId + '_pageid" type="text" title="指定页码" name="jumpto" size="3" value=""/>页<img src="../images/button_go.gif" id="' + object.tableContainer.tableId + '_togo" title="指定页码" class="container_togo"></a></span>';
			page += '<span class="paging_text">';
			page += object.getButtonsPagination(currentPage);
			page += '<a class="paging_next" title="下页" href="javascript:void(0)"  id="' + object.tableContainer.tableId + '_next" pageNo="' + (parseInt(currentPage) + 1) + '"> <span>下一页</span> </a>';
			page += '</span>';
			page += '</div>';
			page += '</div>';
		}

		if (object.pageNo >= object.pageCount && object.pageNo > 1) {
			page = '<div id="' + object.tableContainer.tableId + '_pagination" class="paging">';
			page += '<div class="paging-bottom">';
			page += '<span class="paging_input"> 共' + totalPage +'页 转到第<input id="' + object.tableContainer.tableId + '_pageid" type="text" title="指定页码" name="jumpto" size="3" value=""/>页<img src="../images/button_go.gif"  id="' + object.tableContainer.tableId + '_togo" title="指定页码" class="container_togo"></span>';
			page += '<span class="paging_text">';
			page += '<a class="paging_pre" title="上页" href="javascript:void(0)" id="' + object.tableContainer.tableId + '_before" pageNo="' + (currentPage - 1) + '"> <span>上一页</span> </a>';
			page += object.getButtonsPagination(currentPage);
			page += '</span>';
			page += '</div>';
			page += '</div>';
		}

		if (object.pageCount == 1 && object.pageNo == 1 || object.pageCount == 0) {
			page += '';
		}
		$("#" + object.tableContainer.targetDivId).append(page);

		if (ifBindEvent) {
			object.bindEvent(); //添加事件
		}
	};

	//获取分页buttons
	object.getButtonsPagination = function(pageNo) {
		var closeValue = Math.ceil(pageNo / object.paginationNum);
		var closeV = Math.ceil(object.paginationNum / 2);
		var beginPage = (closeValue * object.paginationNum + 1 - object.paginationNum);
		var endPage = (closeValue * object.paginationNum);

		if (pageNo > closeV) {
			var beginPage = (pageNo - closeV);
			var endPage = (parseInt(pageNo) + parseInt(object.paginationNum - closeV) - 1);
		}
		var html = '';
		if (endPage >= parseInt(object.pageCount)) {
			endPage = object.pageCount;
		}
		for (var i = beginPage; i <= endPage; i++) {
			if (parseInt(pageNo) === i) {
				html += '<span class="paging_num_on">' + i + '</span>';
			} else {
				html += '<a class="container_paginationNumLink" href="javascript:void(0)" pageNo=' + i + '>' + i + '</a>';
			}
		}
		if (endPage < object.pageCount) {
			html += '<span class="page-break">...</span>';
		}
		return html;
	};
	return object;
};

//排序器
var sorter = function(container) {
	var object = new Object;
	object.defaultDirection = "asc"; //默认是降序  asc是升序
	object.targetContainer = container;
	object.params = {};

	object.getImgHtml = function(headInfo) {
		var direction = headInfo.direction;
		if (direction == null || direction == undefined) {
			direction = object.defaultDirection;
		}
		var imgHtml = "";
		if (direction == 'desc') {
			imgHtml = '<img id="' + object.targetContainer.tableId + '_arrow" src="/r/cms/www/red/img/images/xia.gif">';
		} else if (direction == 'asc') {
			imgHtml = '<img id="' + object.targetContainer.tableId + '_arrow"  src="/r/cms/www/red/img/images/shang.gif">';
		}
		return imgHtml;
	};
	object.addEvent = function() {
		$('#' + object.targetContainer.tableId + ' th').each(function(i) {
			var sortable = $(this).attr('sortable');
			if (sortable == "true") {
				$(this).bind('click',
				function() {
					var description = $(this).attr('desc');
					var direction = $(this).attr('direction');
					var index = $(this).attr('index');
					var code = $(this).attr('id');
					var istarget = $(this).attr('istarget');
					if (!Boolean(istarget) || istarget === 'false') {
						direction = 'asc';
					} else {
						if (direction === 'asc') {
							direction = 'desc';
						} else if (direction === 'desc') {
							direction = 'asc';
						} else {
							direction = 'asc';
						}
					}
					$(this).attr('istarget', true);
					$(this).empty();
					if (direction === 'desc') {
						$(this).attr('direction', 'desc');
						$(this).append('<a href="javascript:void(0)">' + description + '<img id="' + object.targetContainer.tableId + '_arrow" src="/r/cms/www/red/img/images/xia.gif"></a>');
					} else if (direction === 'asc') {
						$(this).attr('direction', 'asc');
						$(this).append('<a href="javascript:void(0)">' + description + '<img id="' + object.targetContainer.tableId + '_arrow" src="/r/cms/www/red/img/images/shang.gif"></a>');
					} else {
						$(this).attr('direction', 'asc');
						$(this).append('<a href="javascript:void(0)">' + description + '<img id="' + object.targetContainer.tableId + '_arrow" src="/r/cms/www/red/img/images/shang.gif"></a>');
					}

					$('#' + object.targetContainer.tableId + ' th').each(function(p) {
						var index2 = $(this).attr('index');
						var sortable2 = $(this).attr('sortable');
						var description2 = $(this).attr('desc');
						if (sortable2 === 'undefined' || sortable2 === null) {
							sortable2 = false;
						}
						if (index2 != index && sortable2) {
							$(this).empty();
							$(this).append('<a href="javascript:void(0)">' + description2 + '</a>');
							$(this).attr('istarget', false);
						}
					});
					direction = $(this).attr('direction');
					object.params = {
						'sortName': code,
						'direction': direction
					};
					object.targetContainer.getDataByAjax(object.targetContainer.AjaxUrl, object.params, null);
				});
			}
		});
	};
	return object;
};

var staticPagination = function(p) {
	var object = new Object();
	object.pageNo = p.pageNo;
	object.pageCount = p.pageCount;
	object.globalName = p.btnName;
	object.targetContainer = p.target;
	object.paginationNum = 6;
	object.total = p.total;
	object.cacheSize = 5;

	object.composite = function() {
		var ifBindEvent = false;
		if ($('#' + object.globalName + '_pagination') != null || $('#' + object.globalName + '_pagination') != undefined) {
			$('#' + object.globalName + '_pagination').remove();
			ifBindEvent = true;
		}
		var currentPage = object.pageNo; //当前页数
		var totalPage = object.pageCount;
		var page = "";
		if (object.pageNo < object.pageCount && object.pageNo > 1) {
			page = '<div id="' + object.globalName + '_pagination" class="paging">';
			page += '<div class="paging-bottom">';
			page += '<span class="paging_input"> 共' + totalPage +'页 转到第<input id="' + object.globalName + '_pageid" type="text" title="指定页码" name="jumpto" size="3" value=""/>页<img src="../images/button_go.gif" id="' + object.globalName + '_togo" title="指定页码" type="submit" class="container_togo"></span>';
			page += '<span class="paging_text">';
			page += '<a class="paging_pre" title="上页" href="javascript:void(0)" id="' + object.globalName + '_before" pageNo="' + (currentPage - 1) + '"> <span>上一页</span> </a>';
			page += object.getButtonsPagination(currentPage);
			page += '<a class="paging_next" title="下页" href="javascript:void(0)"  id="' + object.globalName + '_next" pageNo="' + (parseInt(currentPage) + 1) + '">下一页</a>';
			page += '</span>';
			page += '</div>';
			page += '</div>';
		}
		if (object.pageNo < object.pageCount && object.pageNo == 1) {
			page = '<div id="' + object.globalName + '_pagination" class="paging">';
			page += '<div class="paging-bottom">';
			page += '<span class="paging_input"> 共' + totalPage +'页 转到第<input id="' + object.globalName + '_pageid" type="text" title="指定页码" name="jumpto" size="3" value=""/>页<img src="../images/button_go.gif" id="' + object.globalName + '_togo" title="指定页码" type="submit" class="container_togo"></span>';
			page += '<span class="paging_text">';
			page += object.getButtonsPagination(currentPage);
			page += '<a class="paging_next" title="下页" href="javascript:void(0)"  id="' + object.globalName + '_next" pageNo="' + (parseInt(currentPage) + 1) + '">下一页</a>';
			page += '</span>';
			page += '</div>';
			page += '</div>';
		}
		if (object.pageNo >= object.pageCount && object.pageNo > 1) {
			page = '<div id="' + object.globalName + '_pagination" class="paging">';
			page += '<div class="paging-bottom">';
			page += '<span class="paging_input"> 共' + totalPage + '页 转到第<input id="' + object.globalName + '_pageid" type="text" title="指定页码" name="jumpto" size="3" value=""/>页<img src="../images/button_go.gif" id="' + object.globalName + '_togo" title="指定页码" type="submit" class="container_togo"></span>';
			page += '<span class="paging_text">';
			page += '<a class="paging_pre" title="上页" href="javascript:void(0)" id="' + object.globalName + '_before" pageNo="' + (currentPage - 1) + '">上一页</a>';
			page += object.getButtonsPagination(currentPage);
			page += '</span>';
			page += '</div>';
			page += '</div>';
		}
		if (object.pageCount == 1 && object.pageNo == 1 || object.pageCount == 0) {
			page += '';
		}
		$("#" + object.targetContainer).append(page);

		if (ifBindEvent) {
			object.bindEvent(); //添加事件
		}
	};

	//获取分页buttons
	object.getButtonsPagination = function(pageNo) {
		var closeValue = Math.ceil(pageNo / object.paginationNum);
		var closeV = Math.ceil(object.paginationNum / 2);
		var beginPage = (closeValue * object.paginationNum + 1 - object.paginationNum);
		var endPage = (closeValue * object.paginationNum);

		if (pageNo > closeV) {
			var beginPage = (pageNo - closeV);
			var endPage = (parseInt(pageNo) + parseInt(object.paginationNum - closeV) - 1);
		}
		var html = '';
		if (endPage >= parseInt(object.pageCount)) {
			endPage = object.pageCount;
		}
		for (var i = beginPage; i <= endPage; i++) {
			if (parseInt(pageNo) === i) {
				html += '<span class="paging_num_on">' + i + '</span>';
			} else {
				html += '<a class="container_paginationNumLink" href="javascript:void(0)" pageNo=' + i + '>' + i + '</a>';
			}
		}
		if (endPage < object.pageCount) {
			html += '<span class="page-break">...</span>';
		}
		return html;
	};

	object.bindEvent = function() {
		//隐藏展开内容
		for(var i=1;i<=20;i++){
			if(!($("#detail"+i).is(":hidden"))){
				$("#detail"+i).hide();
			}
		}
		
		//当页面在
		if (object.pageNo < object.pageCount && object.pageNo > 1) {
			$('#' + object.globalName + '_before').unbind('click');
			$('#' + object.globalName + '_next').unbind('click');
			$('#' + object.globalName + '_togo').unbind('click');
			$('#' + object.globalName + '_pageid').unbind('keydown');
			// 上一页
			$('#' + object.globalName + '_before').bind('click', object.dynamicRequest);
			//下一页
			$('#' + object.globalName + '_next').bind('click', object.dynamicRequest);
			//跳转
			$('#' + object.globalName + '_togo').bind('click', object.dynamicRequest);
			$('#' + object.globalName + '_pageid').bind('keydown', object.forwordPageKeydown);
		}
		if (object.pageNo < object.pageCount && object.pageNo == 1) {
			$('#' + object.globalName + '_next').unbind('click');
			$('#' + object.globalName + '_togo').unbind('click');
			$('#' + object.globalName + '_pageid').unbind('keydown');
			//下一页
			$('#' + object.globalName + '_next').bind('click', object.dynamicRequest);
			//跳转
			$('#' + object.globalName + '_togo').bind('click', object.dynamicRequest);
			$('#' + object.globalName + '_pageid').bind('keydown', object.forwordPageKeydown);
		}
		if (object.pageNo >= object.pageCount && object.pageNo > 1) {
			$('#' + object.globalName + '_before').unbind('click');
			$('#' + object.globalName + '_togo').unbind('click');
			$('#' + object.globalName + '_pageid').unbind('keydown');
			//上一页
			$('#' + object.globalName + '_before').bind('click', object.dynamicRequest);
			//跳转
			$('#' + object.globalName + '_togo').bind('click', object.dynamicRequest);
			$('#' + object.globalName + '_pageid').bind('keydown', object.forwordPageKeydown);
		}
		$('.container_paginationNumLink').unbind('click');
		$('.container_paginationNumLink').bind('click', object.dynamicRequest);
	};

	object.dynamicRequest = function(event) {
		var pageNo = $(this).attr("pageno");
		if (pageNo == undefined || pageNo == null) {
			pageNo = $("#" + object.globalName + "_pageid").val();
		}
		if (pageNo == undefined || pageNo == null || pageNo.length === 0) {
			alert("请输入正确的页数");
			return;
		}
		if (isNaN(pageNo)) {
			alert("请输入正确的页数");
			return;
		}
		if(Number(pageNo) < 1){
			pageNo = 1;
		}
		pageNo = Number(pageNo);
		var pageNoDefine = Math.ceil(Number(pageNo));
		if (pageNo < pageNoDefine) {
			alert("请输入正确的页数");
			return;
		}
		if (pageNo > object.pageCount) {
			pageNo = object.pageCount;
		}
		var ps = {
			'pageBean.pageNo': pageNo,
			'pageBean.beginPage': object.getBeginPage(pageNo),
			'pageBean.endPage': object.getEndPage(pageNo)
		};
		var method = p.method;
		method(ps);
	};

	object.forwordPageKeydown = function(event) {
		if (event.keyCode == 13) {
			//隐藏展开内容
			for(var i=1;i<=20;i++){
				if(!($("#detail"+i).is(":hidden"))){
					$("#detail"+i).hide();
				}
			}
			object.dynamicRequest(event);
		} else {
			return;
		}
	};

	object.getBeginPage = function(changedPageNo) {
		var closeValue = Math.ceil(changedPageNo / object.cacheSize);
		var beginPage = (closeValue * object.cacheSize + 1 - object.cacheSize);
		return beginPage;
	};
	object.getEndPage = function(changedPageNo) {
		var closeValue = Math.ceil(changedPageNo / object.cacheSize);
		var endPage = (closeValue * object.cacheSize);
		return endPage;
	};
	return object;
};

function selAllCheckBox(input, divId) {
	if ($(input).prop("checked")) {
		$("#"+divId+" :checkbox:not(:disabled)").prop("checked", true);
	} else {
		$("#"+divId+" :checkbox:not(:disabled)").prop("checked", false);
	}
};

Date.prototype.format = function(format) {
    var o = {
        "M+" : this.getMonth() + 1,
        "d+" : this.getDate(),
        "h+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        "S" : this.getMilliseconds()
    };
    if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4- RegExp.$1.length));
        }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)){
            format = format.replace(RegExp.$1, RegExp.$1.length == 1? o[k]:("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

/*
 * format="yyyy-MM-dd hh:mm:ss";
 */
var format = 'yyyy-MM-dd hh:mm:ss';

function toDate_bak(objDate) {
    var date = new Date();
    date.setTime(objDate.time);
    date.setHours(objDate.hours);
    date.setMinutes(objDate.minutes);
    date.setSeconds(objDate.seconds);
    return date.format(format);
}

/**
 * 变量不存在或为undefined时，返回空字符串
 */
function checkNull(str){
	if(!str || str == "undefined") return "";
	else return str;
}

/**
 * 将checkbox选中值拼接成["1","2","3","4"]这种样式
 * @param tableId
 * @returns
 */
	function multi(tableId){
	 var arrayStr = "[";
	 $("#"+tableId+" input[type='checkbox']:checked").each(function(){
		 if(!$(this).attr("onclick")){
			 if(arrayStr != "[") arrayStr += ",";
			 arrayStr += '"'+$(this).val()+'"';
		 }
	 });
	 arrayStr += "]";
	 return arrayStr;
	}

/**
 * 针对一个容器的checkbox多选
 */
function multiChoice(tableId){
	 var idArray = new Array();
	 $("#"+tableId+" input[type='checkbox']:checked").each(function(){
		 if(!$(this).attr("onclick")) idArray.push($(this).val());		
	 });
	 var IdStr = idArray.join(',');
	 return IdStr;
}

/**
 * 针对一个容器的checkbox多选(每个字符串带有单引号)
 */
function multiChoices(tableId){
	 var idArray = new Array();
	 $("#"+tableId+" input[type='checkbox']:checked").each(function(){
		 if(!$(this).attr("onclick")) idArray.push($(this).val());
	 });
	 var IdStr = idArray.join("','");
	 return IdStr;
}


/**
 * 针对一个容器的select框多选,返回的是["1","2","3"]这种格式
 */
function multiSelect(objSelectId){
	 var arrayStr = "[";
	 $("#"+objSelectId+" option:selected").each(function(){
		 if(arrayStr != "[") arrayStr += ",";
		 arrayStr += '"'+$(this).val()+'"';
	 });
	 arrayStr += "]";
	 return arrayStr;
}

/**
 * 针对一个容器的select框多选,返回是option Val
 */
function multiSelectVal(objSelectId){
	 var idArray = new Array();
	 $("#"+objSelectId+" option:selected").each(function(){
		 idArray.push($(this).val());
	 });
	 var IdStr = idArray.join(',');
	 return IdStr;
}

/**
 * 针对一个容器的select框多选,返回是option text
 */
function multiSelectText(objSelectId){
	 var textArray = new Array();
	 $("#"+objSelectId+" option:selected").each(function(){
		 textArray.push($(this).text());
	 });
	 var textStr = textArray.join(',');
	 return textStr;
}

/**
 * 针对一个容器的select框多选,返回是option Val
 */
function multiSelectValQ(objSelectId){
	 var idArray = new Array();
	 $("#"+objSelectId+" option:selected").each(function(){                 
		 idArray.push($(this).val());
	 });
	 return idArray;
}

/**
 * 针对一个容器的select框多选,返回是option text,自带''
 */
function multiSelectTextQ(objSelectId){
	 var textArray = new Array();
	 $("#"+objSelectId+" option:selected").each(function(){
		 textArray.push($(this).text());
	 });
	 var IdStr = textArray.join(',');
	 return IdStr;
}

/**
 * 针对一个容器的select框,返回所有选项的数组
 */
function multiSelectOptionsVal(objSelectId){
	 var idArray = new Array();
	 $("#"+objSelectId+" option").each(function(){                 
		 idArray.push($(this).val());
	 });
	 return idArray;
}

/**
 * 针对一个容器的select框,返回所有选项的字符串
 */
function multiSelectOptionsValToString(objSelectId){
	 var idArray = new Array();
	 $("#"+objSelectId+" option").each(function(){                 
		 idArray.push($(this).val());
	 });	 
	 return idArray.join(",");
}

/**
 * 获取其他页面传过来的参数，对于多参的情况，只需要多次调用即可
 * @param name
 * @returns
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) 
    	return unescape(r[2]); 
    return null;
}

Date.prototype.format = function (format) {  
    var o = {  
        "M+": this.getMonth() + 1,  
        "d+": this.getDate(),  
        "h+": this.getHours(),  
        "m+": this.getMinutes(),  
        "s+": this.getSeconds(),  
        "q+": Math.floor((this.getMonth() + 3) / 3),  
        "S": this.getMilliseconds()  
    }; 
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    }  
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
};

function toDate(date,type) {
	if(checkNull(date) == ""  || date == "Invalid Date") return "";
	if(jQuery.type(date) == "string"){
		date = date.replace(/-/g,"/");	
	}
    return getSmpFormatDate(new Date(date), type);
}

function toDateYMD(dateStr) {  
    return getSmpFormatDate(new Date(dateStr));  
}

function getSmpFormatDate(date, type) {
   var pattern = "";   
   if (type == "YMD"){    
	   // 2014-11-07
        pattern = "yyyy-MM-dd";    
    }  else if (type == "YMDHM"){
    	// 2014-11-07 10:08
    	pattern=  "yyyy-MM-dd hh:mm";
    }  else if (type == "HMS"){
    	// 10:08:30
    	pattern=  "hh:mm:ss";
    }  else if (type == "HM"){
    	// 10:08
    	pattern=  "hh:mm";
    } else {    
    	// 2014-11-07 10:08:30
        pattern = "yyyy-MM-dd hh:mm:ss";    
    } 
    return getFormatDate(date, pattern);    
}

function getFormatDate(date, pattern) {  
    if (date == undefined) {  
        date = new Date();  
    }  
    if (pattern == undefined) {  
        pattern = "yyyy-MM-dd hh:mm:ss";  
    }  
    return date.format(pattern);  
}


function UploadFileManger(url,params) {
	if(!params) params="";
	this.url=url;	
	this.async = true;
	this.setAsync = function(async){
		this.async = async;
	};
    this.post = function(fileId) {
    	var uplist = $("input[name="+fileId+"]");
    	var arrId = [];  
        for (var i=0; i< uplist.length; i++){
            if(uplist[i].value){  
            	if($("#"+uplist[i].id).val()!=""){
            		arrId[i] = uplist[i].id;
            	} 
            }
        }        
		var obj = this;		
		$.ajaxFileUpload({
	        url: this.url, 
	        type: 'post',
	        secureuri: false,
	        fileElementId: arrId,
	        dataType: 'json',
	        data:{
	        	'salaryType' : params
	        },
	        success : function(msg) {
				if(msg){
					obj.complete(msg);
				} else if (msg.invalidSession == true) {
					window.location.reload();
				} else {
					alert("文件上传失败！");
				}
			},
			error : function(msg) {
				if (msg.status == 500) {
					alert('系统报错！');
				}
			}
	    });
	};
	this.complete = function(data) {};
}

/**
 * 异步加载页面
 */
function ajaxReturnHtml(url){
	this.url=url;
	this.async = true;
	this.type = "GET";
	this.setAsync = function(async){
		this.async = async;
	};
	this.setType = function(type){
		this.type = type;
	};
	this.post = function(div, postDate) {
		var obj = this;
		$.ajax({
			url : obj.url,
			type : obj.type,
			async: obj.async,
			dataType : "html",
			url : this.url,
			data : postDate,
			success : function(content) {
				if(content){
					if (content.indexOf('"invalidSession":true') > 0) {
						location = location;
					} else if (div != null && div != undefined){
						$(div).html(content);
					}
				}
			},
			error : function(content) {
				if (content.status == 500) {
					alert('系统报错！');
				}
			}
		});
	};
	this.complete = function() {};
}

/**
 * 权限控制页面视图显示
 */
function ajaxReturnView(url){
	this.url=url;
	this.post = function(postDate) {
		var obj = this;
		$.ajax({
			url : obj.url,
			type : "GET",
			async: true,
			url : this.url,
			data : postDate,
			success : function(content) {
				eval(content);
			},
			error : function(content) {
				if (content.status == 500) {
					alert("系统出错！");
				}
			}
		});
	};
}

/**
 * form表单提交
 */
function ajaxFormManager(url) {
	this.validForm = function(){};
	this.post = function(form) {
		var obj = this;
		var options = {
				url : url,
				type : 'post',
				dataType : 'json',
				beforeSubmit : function(){
					obj.validForm($(form));
				},
				success : function(msg) {
					if(msg){
						if (msg.invalidSession == true) {
							location = location;
						} else {
							obj.complete(msg);
						}
					} else {
						alert("系统出错！");
					}
				},
				error : function(msg) {
					if (msg.status == 500) {
						alert('系统报错！');
					}
				}
		};
		$(form).ajaxSubmit(options);
		return obj;
	};
	this.complete = function() {};
}

/**
 * 异步通信数据交互
 */
function PostManager(url) {
	this.url=url;
	this.async = true;
	this.setAsync = function(async){
		this.async = async;
		return this;
	};
	this.postData = {};
	this.post = function(parameter) {
		this.postData = parameter;
		var obj = this;
		$.ajax({
			type : "POST",
			dataType : "json",
			url : this.url,
			data : this.postData,
			success : function(msg) {
				if (msg.invalidSession == true) {
					location = location;
				} else {
					obj.complete(msg);
				}
			},
			error : function(msg) {
				if (msg.status == 500) {
					alert('系统报错！');
				}
			}
		});
		return obj;
	};
	this.complete = function(data) {};
};

/**
 * @param div 指定区域内多元素赋值
 * @param data 推送数据
 */
var setDivOption = function(div, data) {
	
	//label
	$(div).find("label").each(function(){
		var tid = $(this).attr("id");
		if(tid !== undefined) {
			var array = tid.split("_");
			var str = 'data';
			for (var i in array) {
				str += '["'+ array[i] +'"]';
			}
			var ele = eval(str);
			if (ele !== undefined) {
				$("#"+tid).html(ele);
			}
		}
	});
	//input
	$(div).find("input").each(function(){
		var tid = $(this).attr("id");
		if(tid !== undefined) {
			var array = tid.split("_"); 
			var str = 'data';
			for ( var i in array) {
				str += '["'+ array[i] +'"]';  
			}
			var ele = eval(str);
			if (ele !== undefined) {
				if($(this).attr("type") == 'hidden' || $(this).attr("type") == 'text') {
					$("#"+tid).html(eval(str)); //文本类型的输入框
				} else {
					//后续优化多种类型
				}
			}
		}
	});
	
};

/**
 * 封装请求数据
 * @param params
 */
var packParams = function(params) {
	var object_temp = {};
	object_temp.params = JSON.stringify(params);
	return object_temp;
};

/**
 * 局部显示页面
 * @param div
 * @param url
 */
function displaypage(div, url, id) {
	var arh = new ajaxReturnHtml(url);
	var postData = {'id' : id};
	arh.post($(div), postData);
	$("#mainPage").scrollTop(0);
}

function moveLeftOrRight(fromObj,toObj){ 
    var fromObjOptions=fromObj.options; 
    for(var i=0;i<fromObjOptions.length;i++){ 
        if(fromObjOptions[i].selected){ 
            toObj.appendChild(fromObjOptions[i]); 
            i--; 
        } 
    } 
}
function moveLeftOrRightAll(fromObj,toObj){
    var fromObjOptions=fromObj.options; 
    for(var i=0;i<fromObjOptions.length;i++){ 
        fromObjOptions[0].selected=true; 
        toObj.appendChild(fromObjOptions[i]); 
        i--; 
    } 
}
function selectAllOption(selectObj){
    var theObjOptions=selectObj.options; 
    for(var i=0;i<theObjOptions.length;i++){ 
        theObjOptions[0].selected=true; 
    }
}

function checkSelectedOption(){ 
	var ObjSelect = document.frm.ObjSelect; 
	var itemField=""; 
	var itemName=""; 
	if(ObjSelect&&ObjSelect.options&&ObjSelect.options.length>0){ 
	var len = ObjSelect.options.length; 
	for(var j=0; j<len; j++){ 
		itemField += ObjSelect.options[j].value + "|"; 
		itemName += ObjSelect.options[j].text + "|"; 
	} 
	}
	return; 
}

/**
 * 判断函数是否存在，防止报错奔溃
 * @params funName : 函数名
 */
function checkFun(funName){
	try{
		if($.isFunction(eval(funName))){
			return true;
		}
	}catch(e){
		 return false;
	}
}