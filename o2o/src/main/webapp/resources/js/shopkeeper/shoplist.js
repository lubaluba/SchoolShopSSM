/**
 * 
 */
//异步获得区域列表信息
$(function(){
	
	$.ajax({
		url : "/o2o/shopadmin/shoplist.action",
		type : "get",
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$('#user-name').text(data.user.name);
				var list = data.shoplist;
				var html = '';
				for(var i = 0; i < list.length; i++){
					html += "<div class='row row-shop'><div class='col-40'>"
					+ list[i].shopName + "</div><div class='col-40'>"
					+ getshopstatus(list[i].enableStatus) + "</div><div class='col-20'>"
					+ "<a href = 'shopmanagement.html?shopId=" + list[i].shopId + " '>进入</a></div></div>"
				}
				$("#shop-row").html(html);
			}
		},
    	error : function(data){   
    		if(data.responseText = 'needlogin'){
    			window.location.href = "/o2o/user/login.html";
    		}
    	}
	});
	
	$('#log-out').click(function () {
		$.ajax({
			url : "/o2o/local/logout",
			type : "post",
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					window.location.href = '/o2o/user/login.html';
				}
			},
			error : function(data, error) {
				alert(error);
			}
		});
	});
})


function getshopstatus(status){
	if(status == 0){
		return "审核中";
	} else if (status == -1){
		return "不可用";
	} else if (status == 1){
		return "审核通过";
	}
}
