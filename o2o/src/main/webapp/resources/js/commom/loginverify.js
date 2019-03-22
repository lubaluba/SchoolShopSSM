/**
*
*/

window.onload=function (){
	var url = '/o2o/local/auth';
	$.getJSON(url, function(data) {
		var tempHtml = "";
		if(data.success){
			var name = data.user.name;
			if (data.user.userType == 1){
				tempHtml += "<p><a href='#' class='close-panel'>" + name + ",您好</a></p>"
				+ "<p><a href='/o2o/customer/shoppingcart.html' class='close-panel'>购物车</a></p>"
				+ "<p><a href='/o2o/customer/record.html' class='close-panel'>消费记录</a></p>"
				+ "<p><a href='#' class='close-panel'>我的积分</a></p>"
				+ "<p><a href='#' class='close-panel'>积分兑换记录</a></p>"
				+ "<p><a href='/o2o/user/changepsw.html' class='close-panel'>修改密码</a></p>"
				+ "<p><a href='/o2o/local/logout' id='log-out' class='close-panel'>退出登录</a></p>";
			} else{
				tempHtml += "<p><a href='#' class='close-panel'>" + name + "店主,您好</a></p>"
				+ "<p><a href='/o2o/shopkeeper/shoplist.html' class='close-panel'>店铺管理</a></p>"
			}
		} else {
			tempHtml += "<p><a href='/o2o/user/login.html?usertype=1' class='close-panel'>账户登录</a></p>";
		}
		$("#sidebar").append(tempHtml);
	});
}