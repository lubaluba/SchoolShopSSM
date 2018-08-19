/**
 * 
 */
//异步获得区域列表信息
$(function(){
	$.get(
		"/o2o/superadmin/listarea.action",
		false,
		function(data){
			for(var i = 0; i < data.total; i++){
				var area = data.rows[i];
				$("#shop-area").append("<option value='"+area.areaId+"' >"+area.areaName+"</option>");
			};
		},
		"json"
	);
	$.get(
			"/o2o/shopAdmin/getshopCategorylist.action",
			false,
			function(data){
				for(var i = 0; i < data.total; i++){
					var category = data.rows[i];
					$("#shop-category").append("<option value='"+category.shopCategoryId+"' >"+category.shopCategoryName+"</option>");
				};
			},
			"json"
		);
})

/**
 * 获得子类
 */
function getChildren(data){
	var index = data.selectedIndex;
	var val = data.options[index].value;
	$("#shop-category-sub").empty();
	$.get(
			"/o2o/shopAdmin/getshopCategorylist.action?parentId="+val,
			false,
			function(data){
				for(var i = 0; i < data.total; i++){
					var category = data.rows[i];
					$("#shop-category-sub").append("<option value='"+category.shopCategoryId+"' >"+category.shopCategoryName+"</option>");
				};
			},
			"json"
		);
}
//登录提交表单
function submit(){
	var shop = {};
	shop.shopName = $('#shop-name').val();
	shop.shopAddr = $('#shop-addr').val();
	shop.phone = $('#phone').val();
	shop.shopDesc = $('#shop-desc').val();
	shop.area = {
		areaId : $("#shop-area").find("option").not(function(){
			return!this.selected;
		}).val()
	};
	shop.shopCategory = {
			shopCategoryId : $("#shop-category-sub").find("option").not(function(){
				return!this.selected;
			}).val()
	};
	var shopImg = $('#shop-img')[0].files[0];
	//shop.shopImg = shopImg;
	var formData  = new FormData();
	formData.append('shopImg', shopImg);
	formData.append('shopstr', JSON.stringify(shop));
	var verifyCodeActual = $("#j_captcha").val();
	if(!verifyCodeActual){
		$.toast("请输入验证码");
		return;
	}
	
	formData.append('verifyCodeActual', verifyCodeActual);
	$.ajax({
			type: "POST",
			url: "/o2o/shopAdmin/registershop.action",
			data:formData,
			dataType: "json",
			processData: false,
			contentType: false,
			cache: false,
			success: function(data){
				if(data.success){
					$.toast('提交成功!');
					window.location.href = "/o2o/shopAdmin/toShopList"
				}else{
					$.toast('提交失败！' + data.errMsg);
				}
				$('#captcha_img').click();
			}
	});
}