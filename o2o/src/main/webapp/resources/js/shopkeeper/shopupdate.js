/**
 * 
 */
// 异步获得区域列表信息
$(function() {
	var shopId = getQueryString("shopId");
	$.ajax({
		type : "GET",
		url : "/o2o/superadmin/listarea.action",
		dataType : "json",
		async : false,
		success : function(data) {
			for (var i = 0; i < data.total; i++) {
				var area = data.rows[i];
				$("#shop-area").append(
						"<option value='" + area.areaId + "' >" + area.areaName
								+ "</option>");
			}
			;
		}
	})

	$.get("/o2o/shop/getshopbyid?shopId=" + shopId, function(data) {
		var shop = data.shop;
		$("#shop-name").val(shop.shopName);
		$("#phone").val(shop.phone);
		$("#shop-addr").val(shop.shopAddr);
		$("#shop-desc").val(shop.shopDesc);
		var areaops = document.getElementById('shop-area').options;
		for (var i = 0; i < areaops.length; i++) {
			if (areaops[i].value == shop.area.areaId) {
				areaops[i].selected = 'selected';
			}
		}
		;

		$("#shop-category").append(
				"<option value='" + shop.shopCategory.shopCategoryId + "' >"
						+ shop.shopCategory.shopCategoryName + "</option>");
		$('#shop-category').attr('disabled', 'disabled');
	}, "json");
})
// 登录提交表单
function update() {
	var shopId = getQueryString("shopId");
	var shop = {};
	shop.shopId = shopId;
	shop.shopName = $('#shop-name').val();
	shop.shopAddr = $('#shop-addr').val();
	shop.phone = $('#phone').val();
	shop.shopDesc = $('#shop-desc').val();
	shop.area = {
		areaId : $("#shop-area").find("option").not(function() {
			return !this.selected;
		}).val()
	};
	shop.shopCategory = {
		shopCategoryId : $("#shop-category").find("option").not(function() {
			return !this.selected;
		}).val()
	};
	var shopImg = $('#shop-img')[0].files[0];
	// shop.shopImg = shopImg;
	var formData = new FormData();
	formData.append('shopImg', shopImg);
	formData.append('shopstr', JSON.stringify(shop));
	var verifyCodeActual = $("#j_captcha").val();
	if (!verifyCodeActual) {
		$.toast("请输入验证码");
		return;
	}

	formData.append('verifyCodeActual', verifyCodeActual);
	$.ajax({
		type : "POST",
		url : "/o2o/shopadmin/updateshop.action",
		data : formData,
		dataType : "json",
		processData : false,
		contentType : false,
		cache : false,
		success : function(data) {
			if (data.success) {
				$.toast('提交成功!');
			} else {
				$.toast('提交失败！' + data.errMsg);
			}
			$('#captcha_img').click();
		}
	});
}