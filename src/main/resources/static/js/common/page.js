var modulePage = modulePage || {};
modulePage.showpageFun = showpageFun;
modulePage.showpageFun2 = showpageFun2;
modulePage.showpageFunc = showpageFun2;

/**
 * 通讯录分页
 * @param obj
 * @param page
 * @param total
 * @param type
 * @param deptId
 * @param funName
 */
function showpageFun(obj, page, total, type, deptId, funName) {
	var html = "<div class=\"pagination fr\">";
	var pre_dis;
	var next_dis;
	var currentPageDiv;
	if (page == 1) {
		pre_dis = "prev-disabled";
	}
	if (page == total) {
		next_dis = "next-disabled";
	}

	if (page != 1) {
		html += "<a  href=\"javascript:void(0)\"  onclick=\"" + funName + "('" + deptId + "','" + type + "','"
			+ (parseInt(page) - 1 ) + "');\" > < 上一页 </a>";
	} else {
		html += "<span class=\"disabled\" >< 上一页 </span>";
	}
	if (page < 4 || total < 6) {
		var xh = 5;
		if (total < xh)
			xh = total;
		for (var i = 1; i <= xh; i++) {
			if (i == page) {
				html += "<span class=\"current ft-arial bold\" title=\"" + i + "\">" + i + "</span>";
			} else {
				html += "<a href=\"javascript:void(0)\" onclick=\"" + funName + "('" + deptId + "','" + type + "','" + i
					+ "')\" class=\"ft-arial\">" + i + "</a>";
			}
		}
		if (total > 5) {
			html += "<a href=\"javascript:void(0)\"  class=\"ft-arial\">...</a>"
		}
	} else {
		html += "<a  href=\"javascript:void(0)\" onclick=\"" + funName + "('" + deptId + "','" + type + "','" + 1 + "')\"  class=\"ft-arial\">" + 1 + "</a>"
		html += "<a href=\"javascript:void(0)\"   class=\"ft-arial\">...</a>"
		var isShow = true;
		for (var i = -1; i < 3; i++) {
			var c = parseInt(page) + parseInt(i);
			if (c <= total) {
				if (c == page) {
					html += "<span class=\"current ft-arial bold\" title=\"" + c + "\">" + c + "</span>";
				} else {
					html += "<a href=\"javascript:void(0)\" onclick=\"" + funName + "('" + deptId + "','" + type + "','" + c
						+ "')\" class=\"ft-arial\"> " + c + " </a>";
				}
			} else {
				isShow = false;
			}
		}
		if (isShow)
			html += "<a href=\"javascript:void(0)\"   class=\"ft-arial\">...</a>"
	}
	// 下一页
	if (page != total) {
		html += "<a  href=\"javascript:void(0)\"  onclick=\"" + funName + "('" + deptId + "','" + type + "','"
			+ (parseInt(page) + 1)
			+ "');\" >下一页></a>";
	} else {
		html += "<span class=\"disabled\" >下一页 ></span>";
	}
	html += "</div>";
	obj.text("");// 清空数据
	obj.append(html);
}

/**
 * 通用分页
 * @param obj
 * @param page
 * @param total
 * @param funcName
 * @param args 传入函数的调用参数（可选参数）
 */
function showpageFun2(obj, page, total, funcName, args) {
	var html = '<div class="pagination fr">';
	var pre_dis;
	var next_dis;
	var currentPageDiv;
	page = parseInt(page);
	if (page == 1) {
		pre_dis = "prev-disabled";
	}
	if (page == total) {
		next_dis = "next-disabled";
	}

	if (page != 1) {
		html += '<a href="javascript:void(0)" class="link-page" data-page="' + (page - 1) + '" > < 上一页 </a>';
	} else {
		html += '<span class="disabled" >< 上一页 </span>';
	}
	if (page < 4 || total < 6) {
		var xh = 5;
		if (total < xh)
			xh = total;
		for (var i = 1; i <= xh; i++) {
			if (i == page) {
				html += '<span class="current ft-arial bold" title="' + i + '">' + i + '</span>';
			} else {
				html += '<a href="javascript:void(0)" class="link-page" data-page="' + i + '" class="ft-arial">' + i + '</a>';
			}
		}
		if (total > 5) {
			html += '<a href="javascript:void(0)" class="ft-arial">...</a>';
		}
	} else {
		html += '<a  href="javascript:void(0)" class="link-page" data-page="1" class="ft-arial">' + 1 + '</a>';
		html += '<a href="javascript:void(0)" class="ft-arial">...</a>';
		var isShow = true;
		for (var i = -1; i < 3; i++) {
			var c = parseInt(page) + parseInt(i);
			if (c <= total) {
				if (c == page) {
					html += '<span class="current ft-arial bold" title="' + c + '">' + c + '</span>';
				} else {
					html += '<a href="javascript:void(0)" class="link-page" data-page="' + c + '" class="ft-arial"> ' + c + ' </a>';
				}
			} else {
				isShow = false;
			}
		}
		if (isShow)
			html += '<a href="javascript:void(0)" class="ft-arial">...</a>'
	}
	// 下一页
	if (page < total) {
		html += '<a  href="javascript:void(0)" class="link-page" data-page="' + (page + 1) + '" >下一页></a>';
	} else {
		html += '<span class="disabled" >下一页 ></span>';
	}
	html += "</div>";
	obj.text(""); // 清空数据
	obj.append(html);

	// 绑定事件
	$('.pagination').off().on('click', '.link-page', function () {
		if (typeof funcName === 'string') {
			funcName = eval(funcName);
		}
		funcName($(this).data('page'), args);
	});
}

//// 格式化时间
//var format = function (time, format) {
//	var t = new Date(time);
//	var tf = function (i) {
//		return (i < 10 ? '0' : '') + i
//	};
//	return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function (a) {
//		switch (a) {
//			case 'yyyy':
//				return tf(t.getFullYear());
//				break;
//			case 'MM':
//				return tf(t.getMonth() + 1);
//				break;
//			case 'mm':
//				return tf(t.getMinutes());
//				break;
//			case 'dd':
//				return tf(t.getDate());
//				break;
//			case 'HH':
//				return tf(t.getHours());
//				break;
//			case 'ss':
//				return tf(t.getSeconds());
//				break;
//		}
//	});
//}
