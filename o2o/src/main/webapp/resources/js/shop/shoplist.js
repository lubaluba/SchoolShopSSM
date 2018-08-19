/**
 * 
 */
//异步获得区域列表信息
$(function(){
	$.get(
		"/o2o/shopAdmin/getshoplist.action",
		function(data){
			if(data.success){
				$('#user-name').text(data.user.name);
				var list = data.shoplist;
				var html = '';
				for(var i = 0; i < list.length; i++){
					html += "<div class='row row-shop'><div class='col-40'>"
					+ list[i].shopName + "</div><div class='col-40'>"
					+ getshopstatus(list[i].enableStatus) + "</div><div class='col-20'>"
					+ "<a href = '/o2o/shopAdmin/toShopManagement?shopId=" + list[i].shopId + " '>进入</a></div></div>"
				}
				$("#shop-row").html(html);
			}
		},
		"json"
	);
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
