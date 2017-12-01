/*
 * jQuery File Upload Plugin Angular JS Example
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2013, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/* jshint nomen:false */
/* global window, angular */
/**
 * jquery 传递对象处理 
 */
;(function($) {
  // copy from jquery.js
  var r20 = /%20/g,
  rbracket = /\[\]$/;
  $.extend({
    customParam: function( a ) {
      var s = [],
        add = function( key, value ) {
          // If value is a function, invoke it and return its value
          value = jQuery.isFunction( value ) ? value() : value;
          s[ s.length ] = encodeURIComponent( key ) + "=" + encodeURIComponent( value );
        };

      // If an array was passed in, assume that it is an array of form elements.
      if ( jQuery.isArray( a ) || ( a.jquery && !jQuery.isPlainObject( a ) ) ) {
        // Serialize the form elements
        jQuery.each( a, function() {
          add( this.name, this.value );
        });

      } else {
        for ( var prefix in a ) {
          buildParams( prefix, a[ prefix ], add );
        }
      }

      // Return the resulting serialization
      return s.join( "&" ).replace( r20, "+" );
    }
  });

/* private method*/
function buildParams( prefix, obj, add ) {
  if ( jQuery.isArray( obj ) ) {
    // Serialize array item.
    jQuery.each( obj, function( i, v ) {
      if (rbracket.test( prefix ) ) {
        // Treat each array item as a scalar.
        add( prefix, v );

      } else {
        buildParams( prefix + "[" + ( typeof v === "object" || jQuery.isArray(v) ? i : "" ) + "]", v, add );
      }
    });

  } else if (obj != null && typeof obj === "object" ) {
    // Serialize object item.
    for ( var name in obj ) {
      buildParams( prefix + "." + name, obj[ name ], add );
    }

  } else {
    // Serialize scalar item.
    add( prefix, obj );
  }
};
})(jQuery);

function textCount(obj, objnum) {

	$(obj).next().find(".textcount-num").text($(obj).val().length);
}

function Map() {
	this.elements = new Array();
	//向MAP中增加元素（key, value)
	this.put = function(_key, _value) {
			if(!this.get(_key))
			this.elements.push({
				key: _key,
				value: _value
			});
	}
	//获取指定KEY的元素值VALUE，失败返回NULL
	this.get = function(_key) {
			try {
				for (i = 0; i < this.elements.length; i++) {
					if (this.elements[i].key == _key) {
						return this.elements[i].value;
					}
				}
			} catch (e) {
				return null;
			}
	}
	//判断MAP中是否含有指定VALUE的元素
	this.containsValue = function(_value) {
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].value == _value) {
					bln = true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	}
	this.keys = function() {
		var arr = new Array();
		for (i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].key);
		}
		return arr;
	}
}
/**
 * 支持注释模板代码的获取，注释可以保留原生HTML，便于阅读和修改
 * @param func
 * @returns
 */
function findTemplate(func) {
    return  func.toString().split(/\n/).slice(2, -2).join('\n');
}

/**
 * 初始化
 */
$(function() {
	initCoreBaseInfo();
	if($.isFunction(window.beforeLoadPage)){
		beforeLoadPage();
	}
	if ("undefined" != typeof Boxjs && "undefined" != typeof Boxjs.searchVo) {
		Boxjs.noResult=$('#noResult').remove().val();
		loadData(1);
	}
});
/**
 * 公用的渲染模板
 */
var baseTemplate = {
 	trTemplate : '<tr data-row-id="{{id}}" index="{{index}}" ' // tr属性定义，data-row-id结合index可以为表单自动填充，index可以快速查找该行的对象
}
/**
 * 加载搜索条件
 */
function searchParam(pageNo) {
	var params = {
		'search.pageSize' : Boxjs.pageSize,
		'search.pageNo' : pageNo
	};
	$('.' + Boxjs.searchVo + ' .form-control').each(function(i, n) {
		if ($(n).attr('param') != undefined) {
			params[$(n).attr('param')] = $(n).val() || $(n).attr('value');
		}
	});
	return params;
}
/**
 * 加载查询信息
 */
function loadData(pageNo) {
	var params = searchParam(pageNo);
	data(params, pageNo);
}
/**
 * 加载数据 注意后端代码一定要用renderPageJson的方式
 */
function data(condition, pageNo) {
	$.ajax({
		url : Boxjs.listUrl,
		data : condition,
		type : 'get',
		cache:false,
		success : function(data) {
			if($.isFunction(window.firstPageDeal)){
				window.firstPageDeal(data);
			}
			Boxjs.loadedCache = data.obj;
			Boxjs.total = 0;
			if(!data.obj){
				noResult();
				return ;
			}
			Boxjs.currPageNo = pageNo;
			Boxjs.currentPageCount = data.obj.length;// 当前页面的数据条数

			for (var i = 0; i < data.obj.length; i++) {
				data.obj[i].index = i;
			}
			renderPagination(data);
			$('#totalNum').html(Boxjs.total);
			if($.isFunction(window.afterPagination)){
				afterPagination(data);
			}
		},
		error : function() {
			$('#container').html('');
			$('#page').hide();
			if($.isFunction(window.errorLoadDataCallBack)){
				errorLoadDataCallBack();
			}
			layer.msg('查询失败，请稍后重试');
		}
	})
}
function noResult(){
	var length = $('#container').prev().find('th').length;
	output = Mustache.render(Boxjs.noResult, {
		len : length
	});
	$('#container').html(output);
	$('#page').hide();
	if($.isFunction(window.afterNoResult)){
		afterNoResult();
	}
}
/**
 * 分页内容
 * 
 * @param data:ajax返回数据
 */
function renderPagination(data) {
	if (data.obj.length == 0) {
		if(('undefined' == typeof Boxjs.keepOrginHtml) || Boxjs.keepOrginHtml==false){
			noResult();
			return ;
		}
	}
	if(!(undefined==data.totalPage)){
		Boxjs.totalPage = data.totalPage;
	}
	if(!(undefined==data.total)){
		Boxjs.total = data.total;
	}
	if (data.obj.length>0 && ('undefined' != typeof data.obj[0].id)) {
		if (Boxjs.template) {
			Boxjs.template = Boxjs.template.replace(/<tr/g,
					baseTemplate.trTemplate);
		}
	}
	if (data.totalPage > 1 && Boxjs.noPageShow != true) {
		$('#page').show();
		showpageFun2($("#page"), data.curPage, data.totalPage, "loadData");
	} else {// 搜索的时候进入
		$('#page').hide();
	}
	if ("undefined" == typeof formatTem) {
		formatTem = {};
	}
	var output = Mustache.render(Boxjs.template, $.extend(data, formatTem));
	$('#container').html(output);
}
/**
 * 取消事件绑定 一般处理子集事件 比如div绑定了事件 div下的button等不要时间
 * 调用方法$(document).on('click', '.gameDiv .delbtn', function(e) {e.stopPropagation();})
 */
function stopBubble(e) {
	if (e && e.stopPropagation) {
		e.stopPropagation(); //非IE浏览器
		//e.preventDefault();
	} else {
		window.event.cancelBubble = true; //IE浏览器
	}
	return false;
}
/**
 * 初始化加载
 */
function initCoreBaseInfo() {
	if ("undefined" != typeof Boxjs && "undefined" != typeof Boxjs.searchVo) {
		$(document).on('click', '.' + Boxjs.searchVo + ' .btn-primary',function() {
			if($.isFunction(window.validBeforeLoadPage)){
				var boo = validBeforeLoadPage();
				if(!boo){
					return;
				}
			}
			loadData(1); // 搜索查询默认查询第一页
		});
	}
	$(document).on('click', '.deleteOne', function() {
		var dbId = $(this).parents('tr').attr('data-row-id');
		var msg = $(this).attr('msg');
		if(!msg) msg = "确定删除该条记录？";
		layer.confirm(msg, function(index) {
			deleteOne(dbId,index);
		});// 处理取消时间
	});
	/* tab切换,注意不能对mastache模板内容remove 因为CDT.temp只有一个*/
	$(document).on('click', '.tabPannel', function() {
		//layer.closeAll(); // 防止tab页切换了，错误信息还没有消失
		beforeTabRenderHtml($(this));
		$(this).addClass('cur').siblings().removeClass('cur');
		var moduleto = $(this).attr('moduleto');
		Boxjs.template = $('#' + moduleto).val();
		loadData(1);
	});
	if($('.upload_image').length>0){
		initImageFile();
	}
	if($('.file_upload').length>0){
		loadMultipart();
	}
	
	$(document).on('click', '.upload_image', function(e) {
		stopBubble(e);
		if($().length==0){
			initImageFile();
		}
		var configs = $(this).attr('data-config');
		var defaultConfig = {before_func: 'beforeImageUpload', success_func: 'imageUploadSuccess'};
		var mconfig = $.extend(defaultConfig,JSON.parse(configs))
		var $file = $('#file_upload_image');
		$file.attr('data-config',JSON.stringify(mconfig));
		var beforeFunc =  window[mconfig.before_func];
		if (!beforeFunc($(this))) {
			return;
		}else{
			$file.click();
		}
	});
	$(document).on('click', '#file_upload', function() {
		var myconfig = JSON.parse($('#file_upload').attr('data-config'));
		var beforeFunc = window[myconfig.before_func];
		if (!beforeFunc()) {
				//$parent.append($this.clone());
				//$this.remove();
			return;
		}else{
			var $file = $('#multipart_file_upload');
			$file.click();
		}
	});
	$(document).on('change','#multipart_file_upload',function(){
		var myconfig = JSON.parse($('#file_upload').attr('data-config'));
		var $this = $('#file_upload');
		var $parent = $this.parent();
		var mfile = $('#multipart_file_upload').attr('data-config',myconfig);
		$form = $('#multipart-ajax-upload-file');
		file_upload($form,myconfig);
	});
	$(document).on('change', '#file_upload_image', function() {
		var uploadForm =  '\
			<form id="ajax-upload-file" style="display: none;" method="post" enctype="multipart/form-data">\
			 	<input name="type" value="201"/>\
			</form>';
		$parent = $(this).parent();
		var $form = $(uploadForm);
		var $this = $(this);
		var config = $this.data("config");
		$this.prop('name', 'file');
		$form.append($this);
		shadow($parent);
		$form.ajaxSubmit({
				url : ctx+'/web/fileUpload/uploadImage',
				dataType : 'json',
				data:config,
				success : function(data) {
					if (data.success) {
						if (config.success_func
								&& typeof window[config.success_func] == 'function')
							window[config.success_func](data);
					} else {
						layer.msg(data.msg || '文件上传失败');
					}
				},
				error : errorHandle,
				complete : function() {
					$parent.append($this.clone());
					$this.remove();
					closeShadow($parent);
				}
		});
	});
}
function deleteOne(dbId,index){
	$.ajax({
		url : Boxjs.deleteUrl,
		type : 'post',
		data:{
			id:dbId
		},
		success : function(data) {
			if (!data.success) {
				layer.msg(data.msg);
			} else {
				if (Boxjs.currentPageCount==1 && Boxjs.currPageNo>1) { // 当前页面只有一条数据的时候
					Boxjs.currPageNo = Boxjs.currPageNo - 1;
				}
				layer.close(index);
				loadData(Boxjs.currPageNo);// 删除之后直接去渲染列表内容
			}
		}
	});
}
function dealMerge(data,name){
	if(data.obj && data.obj.length>0){
		for(var i=0;i<data.obj.length;i++){
			var dataObj = data.obj[i];
			if(dataObj[name] && dataObj[name].length>0){
				data.obj[i].first=dataObj[name][0];
				var newObject = $.map(dataObj[name],function (n) { return n; });// 克隆一个数组 
				newObject.splice(0,1);//并删掉第一条数据
				data.obj[i].others = newObject;
			}
		}
	}
}
function formatDate(time){
	return new Date(time).Format("yyyy-MM-dd hh:mm:ss")
}
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
;/*(function () {
    'use strict';

    var isOnGitHub = window.location.hostname === 'blueimp.github.io',
        url = isOnGitHub ? '//jquery-file-upload.appspot.com/' : 'server/php/';

    angular.module('demo', [
        'blueimp.fileupload'
    ])
        .config([
            '$httpProvider', 'fileUploadProvider',
            function ($httpProvider, fileUploadProvider) {
                delete $httpProvider.defaults.headers.common['X-Requested-With'];
                fileUploadProvider.defaults.redirect = window.location.href.replace(
                    /\/[^\/]*$/,
                    '/cors/result.html?%s'
                );
                if (isOnGitHub) {
                    // Demo settings:
                    angular.extend(fileUploadProvider.defaults, {
                        // Enable image resizing, except for Android and Opera,
                        // which actually support image resizing, but fail to
                        // send Blob objects via XHR requests:
                        disableImageResize: /Android(?!.*Chrome)|Opera/
                            .test(window.navigator.userAgent),
                        maxFileSize: 999000,
                        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
                    });
                }
            }
        ])

        .controller('DemoFileUploadController', [
            '$scope', '$http', '$filter', '$window',
            function ($scope, $http) {
                $scope.options = {
                    url: url
                };
                if (!isOnGitHub) {
                    $scope.loadingFiles = true;
                    $http.get(url)
                        .then(
                            function (response) {
                                $scope.loadingFiles = false;
                                $scope.queue = response.data.files || [];
                            },
                            function () {
                                $scope.loadingFiles = false;
                            }
                        );
                }
            }
        ])

        .controller('FileDestroyController', [
            '$scope', '$http',
            function ($scope, $http) {
                var file = $scope.file,
                    state;
                if (file.url) {
                    file.$state = function () {
                        return state;
                    };
                    file.$destroy = function () {
                        state = 'pending';
                        return $http({
                            url: file.deleteUrl,
                            method: file.deleteType
                        }).then(
                            function () {
                                state = 'resolved';
                                $scope.clear(file);
                            },
                            function () {
                                state = 'rejected';
                            }
                        );
                    };
                } else if (!file.$cancel && !file._index) {
                    file.$cancel = function () {
                        $scope.clear(file);
                    };
                }
            }
        ]);

}());*/

/**
 * 文件上传
 */
function file_upload(form,myconfig) {
	// 如果定义了就用定义的属性，如果未定义就用默认属性和默认属性值
	// loading
	shadow($('#file_upload').parent());
	var mfile = $('#multipart_file_upload');
	form.ajaxSubmit({
		url : myconfig.url,
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				if (myconfig.success_func
						&& typeof window[myconfig.success_func] == 'function')
					window[myconfig.success_func](data);
				mfile.attr('first',true);
				$('#multipart-ajax-upload-file').remove();
				loadMultipart();
			} else {
				layer.msg(data.msg || '文件上传失败');
			}
		},
		error : errorHandle,
		complete : function() {
			//$parent.append($this.clone());
			//$this.remove();
			// unloading
			closeShadow($('#file_upload').parent());
		}
	});
}  

function loadMultipart(){
	var pageConfig = $('#file_upload').data('config');
	var defaultConfig = {module:pageConfig.module,url: ctx+'/fileUpload/uploadFile', file_type: 'file', before_func: 'beforeUpload', success_func: 'uploadSuccess'};
    var myconfig = $.extend(defaultConfig,pageConfig);
    $('#file_upload').attr('data-config',JSON.stringify(myconfig));
	var $form = $('<form id="multipart-ajax-upload-file" style="display: none;" method="post" enctype="multipart/form-data"></form>');
	$('body').append($form);
	$form.html('').append('<input id="multipart_file_upload" first=true type="file"  name="file" style="display:none;"/>');
	$form.append('<input name="module" value="'+myconfig.module+'">');
}

function initImageFile(){
	if($('#file_upload_image').length>0){
		$('#file_upload_image').remove();
	}
	var fileHtml = '<input id="file_upload_image" type="file"  name="file" style="display:none;"/>';
	if($('#file_upload_image').length==0){
		$('body').append(fileHtml);
	}
}
