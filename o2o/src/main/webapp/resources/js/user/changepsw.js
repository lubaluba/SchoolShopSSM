$(function() {
	var url = '/o2o/local/changepwd';
	$('#submit').click(function() {
		var username = $('#username').val();
		var password = $('#password').val();
		var newPassword = $('#newPassword').val();
		var newPassword2 = $('#newPassword2').val();
		var formData = new FormData();
		formData.append('username', username);
		formData.append('password', password);
		formData.append('newPassword', newPassword);
		formData.append('newPassword2', newPassword2);
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		$.ajax({
			url : url,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					window.location.href = 'login.html';
				} else {
					$.toast('提交失败！' + data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '../shopkeeper/shoplist.html';
	});
});