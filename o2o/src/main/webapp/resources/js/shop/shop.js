/**
 * 
 */
//异步获得区域列表信息
$(function(){
	$.get(
		"/o2o/superadmin/listarea.action",
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
			function(data){
				for(var i = 0; i < data.total; i++){
					var category = data.rows[i];
					$("#shop-category").append("<option value='"+category.shopCategoryId+"' >"+category.shopCategoryName+"</option>");
				};
			},
			"json"
		);
})
//登录提交表单
function submit(){
	var shop = {};
	shop.shopName = $('#shop-name').val();
	shop.shopAddr = $('#shop-addr').val();
	shop.phone = $('#phone').val();
	shop.shopDesc = $('#shop-desc').val();
	/*var shop_area =  $("#shop-area").find("option").not(function(){
		return!this.selected;
	}).val();*/
	shop.area = {
		areaId : $("#shop-area").find("option").not(function(){
			return!this.selected;
		}).val()
	};
	shop.shopCategory = {
			shopCategoryId : $("#shop-category").find("option").not(function(){
				return!this.selected;
			}).val()
	};
	var shopImg = $('#shop-img')[0].files[0];
	var formData  = new FormData();
	formData.append('shopImg',shopImg);
	formData.append('shopStr',JSON.stringify(shop));

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
				}else{
					$.toast('提交失败！' + data.errMsg);
				}
			}
	});
	$.toast("s");
}