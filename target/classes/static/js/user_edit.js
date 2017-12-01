$(function(){
	var userId=GetQueryParam("userId");
	if(!isBlank(userId)){
		vm.getInfo(userId);
	}
	
	
})

var vm = new Vue({
	el:'#rrapp',
	data:{
		title: null,
		user: {},//查询实体
	},
	methods: {

		getInfo: function(id){
			vm.user={};
			$.get("/user/getById/"+id, function(r){
                vm.user = r.user;
            });
		},
		
		updateOrAdd: function(){
			var url="/user/update";
			if(isBlank(vm.user.id)){
				url="/user/add";
			}
			$.ajax({
				type: "POST",
			    url:  url,
	            contentType: "application/json",
			    data: JSON.stringify(vm.user),
			    success: function(r){
					if(r.isSuccess){
						alert('操作成功');
						return;
					}
					alert(r.msg);
				}
				
			})
			
		}
	}
});





