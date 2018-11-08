$(function() {
	var bindUrl = '/o2o/local/bindlocalauth';
	//根据传入的usertype判断是用户登录(1)还是店家登录(2)
	var usertype = getQueryString('usertype');
	$('#submit').click(function() {
		var username = $('#username').val();
		var password = $('#psw').val();
		var verifyCodeActual = $('#j_captcha').val();
		var needVerify = false;
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		
		$.ajax({
			type: "POST",
			url: bindUrl,
			async: false,
			cache: false,
			data: {
				username : username,
				password : password,
				verifyCodeActual : verifyCodeActual
			},
			dataType: 'json',
			success: function(data){
				if (data.success) {
					$.toast('登录成功！');
					if(usertype == 1){
						window.location.href = '/o2o/frontend/index.jsp';
					} else {
						window.location.href = '/o2o/shop/shoplist.jsp';
					}
				} else {
					$.toast('提交失败' + data.errMsg);
				}
				$('#captcha_img').click();
			}
		});
	});
});