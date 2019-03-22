$(function() {
	var loginUrl = '/o2o/local/logincheck';
	//登录失败次数超过三次要提交验证码
	var loginCount = 0;

	$('#submit').click(function() {
		var username = $('#username').val();
		var password = $('#psw').val();
		var verifyCodeActual = $('#j_captcha').val();
		var needVerify = false;
		if (loginCount >= 3) {
			if (!verifyCodeActual) {
				$.toast('请输入验证码！');
				return;
			} else {
				needVerify = true;
			}
		}
		$.ajax({
			url : loginUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				username : username,
				password : password,
				verifyCodeActual : verifyCodeActual,
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					$.toast('登录成功！');
					if (data.userType == 2){
						window.location.href = '/o2o/shopkeeper/shoplist.html';
					} else {
						back();
					}
				} else {
					$.toast('登录失败！');
					loginCount++;
					if (loginCount >= 3) {
						$('#verifyPart').show();
					}
				}
			}
		});
	});

	$('#register').click(function() {
		window.location.href = '/o2o/local/register';
	});
});

function back(){

	var prevLink = document.referrer;
	if($.trim(prevLink)==''){
		location.href = '/o2o/index.html';
	}else{
		if(prevLink.indexOf('/o2o/index.html?')==-1){	//来自其它站点
			location.href = '/o2o/index.html?';
		}
		location.href = prevLink;
	}
}