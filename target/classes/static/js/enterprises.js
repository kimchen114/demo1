var flag = false;
ctx += "/customer";
Boxjs = {
    template: null,
    loadedCache: null,
    listUrl: ctx + '/getEnterprises', // 搜索路由
    searchVo: 'searchVo',// 搜索条件的父元素 下面所有param='vo.xxx' 都会自动放入搜索条件，后端vo对象接收即可
    pageSize: 20,
    noPageShow: false
// 没有分页的时候不显示
}
$(function () {
    Boxjs.template = $('#template').remove().val();// 渲染模板内容，删除后放到CDT.template
    renderType = {
        0: '试用版',
        1: '标准版',
        2: '尊享版',
        3: '白金版'
    }
    renderTgfrom = {
        '-1': '默认(-1)',
        '1': '企业号推荐(1)',
        '100': '百度(100)',
        '101': '百度移动端(101)',
        '300': '360(300)',
        '400': '搜狗(400)',
        '600': '神马搜索(600)',
        '700': '有道易投(700)',
        '800': '新浪粉丝通(800)',
        '1000': '头条(1000)',
        '1001': '腾讯(1001)',
        '1005': '一点资讯(1005)',
        '1010': '搜狐汇算(1010)',
        '1015': '新浪扶翼(1015)',
        '1500': '运营商弹窗(1500)',
        '1501': '百川标签移动(1501)',
        '1600': '百川域名锁定PC(1600)',
        '6666': '微加微信公众号(6666)'
    }
});

$(function () {
	$.ajax({
        url:"/user/getUserByType",
        data:{type:1},
        dataType:'json',
        cache:false,
        type:'get',
        success:function(result){
            if (result !=null) {
            	var option='';
                for (var int = 0; int < result.length; int++) {
                	option=option+"<option value="+result[int].id+">"+result[int].name+"</option>";
				}
                $('#contactPerson').append(option);
            } 
        },
        error:function(){
        }
    });
});



formatTem = {
    renderType: function () {
        if (this.type) {
            return renderType[this.type];
        }
        return '';
    },
    comType: function () {
    	var typeView=['其他','免费用户','体验用户','收费用户','vip用户'];
//    	var t=typeView[0];
    	return typeView[this.type];
    },
    renderTgfrom: function () {
        if (this.tgfrom) {
            return renderTgfrom[this.tgfrom];
        }
        return '';
    },
    createTimeClass: function () {
        if (this.createTime) {
            var now = new Date(this.createTime);
            var yy = now.getFullYear();      //年
            var MM = now.getMonth() + 1;     //月
            var dd = now.getDate();          //日
            var hh = now.getHours();
            var mm = now.getMinutes();
            var ss = now.getSeconds();
            var clock = yy + "-";
            if (MM < 10) clock += "0";
            clock += MM + "-";
            if (dd < 10) clock += "0";
            clock += dd + " ";
            if (hh < 10) clock += "0";
            clock += hh + ":";
            if (mm < 10) clock += "0";
            clock += mm + ":";
            if (ss < 10) clock += "0";
            clock += ss;
            return clock;
        }
        return '';
    },
    tryExpiringTimeClass: function () {
        if (this.tryExpiringTime) {
            var now = new Date(this.tryExpiringTime);
            var yy = now.getFullYear();      //年
            var mm = now.getMonth() + 1;     //月
            var dd = now.getDate();          //日
            var clock = yy + "-";
            if (mm < 10) clock += "0";
            clock += mm + "-";
            if (dd < 10) clock += "0";
            clock += dd + " ";
            return clock;
        }
        return '';
    },

}
//搜索前验证
var validBeforeLoadPage = function () {
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    if (startTime != '' && endTime == '') {
        return true;
    }
    if (startTime > endTime) {
        layer.msg("开始时间不能大于结束时间！");
        return false;
    } else {
        // record();
        return true;
    }
}

/* 详情 */
var detail = function (id) {
    location.href=ctx+"/eneterpriseDetail?id="+id;
}

/* 修改 */
var update = function (id) {
    location.href = ctx + '/toUpdateEnterprise?id=' + id;
}

/* 修改体验期 */
var editPeriod = function (id) {
    location.href = ctx + '/toUpdateExperiencePeriod?id=' + id;
}

// function record() {
//     var endTime = $('#endTime').val();
//     var startTime = $('#startTime').val();
//     var title = $('#title').val();
//     if (endTime != null || startTime != null || title != null) {
//         flag = true;
//     } else {
//         return;
//     }
//     $('#conditions').val(startTime + "," + endTime + "," + title);
// }

function sure(id){
    var tryTime=$("#tryTimeDays").val();
    if(null==tryTime || typeof(tryTime)=='undefined' || tryTime.trim()==""){
        layer.msg("体验期延长时间不能为空！");
        return;
    }
    if(window.confirm('确定修改体验期？')){
        renew(id, tryTime);
    }
}

function renew(id, tryTime){
    var url = ctx + "/updateExperiencePeriod";
    var parameters = {};
    parameters['id'] = id;
    parameters['tryTimeDays'] = tryTime;
    $.ajax({
        url:url,
        data:parameters,
        dataType:'json',
        cache:false,
        type:'post',
        success:function(data){
            if (data.success) {
                layer.msg(data.msg)
                var t = setTimeout(function(){location.href=ctx+"/eneterpriseDetail?id="+id},1000)
            } else {
                layer.msg(data.msg)
            }
        },
        error:function(){
        }
    });
}

function upgrade(id){
    $.ajax({
        url:ctx + "/upgrade?id=" + id,
        dataType:'json',
        cache:false,
        type:'post',
        success:function(data){
            if (data.success != null && data.success){
                layer.msg(data.msg)
                var t = setTimeout(function(){location.reload()},1000)
            } else {
                layer.msg(data.msg)
            }
        },
        error:function(){
        }
    });
}

function resetExtra(id){
    $.ajax({
        url:ctx + "/resetExtra?id=" + id,
        dataType:'json',
        cache:false,
        type:'post',
        success:function(data){
           layer.msg(data.msg)
        },
        error:function(){
        }
    });
}

function exported(){
	var phone =$('#phone').val()
	var accountName =$('#accountName').val()
	var companyName =$('#companyName').val()
	var contactPerson =$('#contactPerson').val()
	var startTime =$('#startTime').val()
	var endTime =$('#endTime').val()
	var type =$('#contact').val()
	$.ajax({
        url:"/export/customer",
        data:{
        	"phone": phone,
        	"accountName": accountName,
        	"companyName": companyName,
        	"contactPerson": contactPerson,
        	"startTime": startTime,
        	"endTime": endTime,
        	"type": type
        },
        dataType:'json',
        cache:false,
        type:'get',
        success:function(result){
            if (result !=null && result.success) {
            	layer.msg('导出成功，请到下载中心下载');
            }else{
            	layer.msg('导出失败');
            }
        }
    });
}

