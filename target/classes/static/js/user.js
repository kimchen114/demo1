$(function () {
    $("#jqGrid").jqGrid({
        url: '/user/list',
        datatype: "json",
        postData:{},
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '姓名', name: 'username', index: 'input_user', width: 80 }, 			
			{ label: '电话', name: 'phone', index: 'loan_amount', width: 80 }, 
			{ label: '年龄', name: 'age', index: 'one_mortgage_money', width: 80 }, 
			{ label: '创建时间', name: 'gmtCreate', index: 'two_mortgage_money', width: 80 }, 
			{ label: '修改时间', name: 'gmtModified', index: 'loan_day', width: 80 },		
			{ label: '操作', name: 'id', width: 120, formatter: function(value, options, row){
				var button = '';
				button += '<button class="btn btn-primary"  onclick="vm.look(\''+value+'\')" type="button" data-toggle="modal" data-target=".bs-example-modal-lg">查看</button>&nbsp;';
				button += '<button class="btn btn-primary"  onclick="vm.toedit(\''+value+'\')" type="button" data-toggle="modal" data-target=".bs-example-modal-lg">修改</button>&nbsp;';
				button +='<button class="btn btn-primary" onclick="vm.todelete(\''+value+'\')" type="button" data-toggle="modal" data-target="#delete">删除</button>&nbsp;'
				button +='<button class="btn btn-primary" onclick="vm.toUploadUI(\''+value+'\')" type="button" data-toggle="modal" data-target="#myUpload">上传文件</button>&nbsp;'
				return button;
			}}
        ],
		viewrecords: true,
        height: 650,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader : {
            root: "list",
            page: "currPage",
            total: "totalPage",
            records: "totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"size", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});



var vm = new Vue({
	el:'#rrapp',
	data:{
		title: null,
		queryDto: {},//查询实体
		loanUploadDto:{},
	},
	methods: {
		query: function () {//点击查询时触发函数
			vm.reload();
		},
		resetquery: function () {//点击重置时触发函数
			vm.queryDto = {}; 
			vm.reload();
		},

		getInfo: function(id){
			vm.tLoanOrderInfo={};
			$.get(baseURL + "loanorderinfople/info/"+id, function(r){
                vm.tLoanOrderInfo = r.tLoanOrderInfo;
            });
		},
		
		toedit: function(id){
			window.location.href="/user/edit?userId="+id;
		},
		
		add: function(){
			window.location.href="/user/edit";
		},
		look: function(id){
			window.location.href="/user/userInfo?userId="+id;
		},

		toUploadUI:function(orderId){//点击上传资料时调用
			$("#file").val("");
			$("#preview").empty();
			vm.loanUploadDto.orderId=orderId;
		},
		todelete: function(id){
			confirm("你确定要删除吗？",function(){
				$.ajax({
					type: "POST",
				    url:  "/user/delete",
		            contentType: "application/json",
				    data: JSON.stringify(id),
				    success: function(r){
						if(r.isSuccess){
							alert('操作成功');
							vm.reload();
							return;
						}
						alert(r.msg);
					}
				})
			});
		},
		reload: function (event) {
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'startTime': isBlank(vm.queryDto.startTime)?null:vm.queryDto.startTime,'endTime': isBlank(vm.queryDto.endTime)?null:vm.queryDto.endTime,'username': isBlank(vm.queryDto.username)?null:vm.queryDto.username},
                page:page
            }).trigger("reloadGrid");
		}
	}
});


//图片预览
$(function() {
    $('[type=file]').change(function(e) {
        var file = e.target.files[0]
        preview(file)
    })
})

function preview(file) {
    var img = new Image(), url = img.src = URL.createObjectURL(file)
    var $img = $(img)
    img.onload = function() {
        URL.revokeObjectURL(url)
        $img.css("width","400PX")
        $('#preview').empty().append($img)
    }
}


