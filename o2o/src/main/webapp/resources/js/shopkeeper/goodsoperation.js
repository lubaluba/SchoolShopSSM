$(function() {
	var goodsId = getQueryString('goodsId');
	var shopId = 1;
	var infoUrl = '/o2o/goods/getgoodsbyid?goodsId=' + goodsId;
	var categoryUrl = '/o2o/goodscategory/getgoodscategory?shopId='
			+ shopId;
	var goodsPostUrl = '/o2o/goodsadmin/modifygoods';
	var isEdit = false;
	if (goodsId) {
		getInfo(goodsId);
		isEdit = true;
	} else {
		getCategory(shopId);
		goodsPostUrl = '/o2o/goodsadmin/addgoods';
	}

	//获取需要编辑的商品的商品信息,并赋值给表单
	function getInfo(id) {
		$.getJSON(
				infoUrl,
				function(data) {
					if (data.success) {
						var goods = data.goods;
						$('#goods-name').val(goods.goodsName);
						$('#goods-desc').val(goods.goodsDesc);
						$('#priority').val(goods.priority);
						$('#point').val(goods.point);
						$('#normal-price').val(goods.normalPrice);
						$('#promotion-price').val(goods.promotionPrice);
						var optionHtml = '';
						var optionArr = data.goodsCategoryList;
						var optionSelected = goods.goodsCategory.goodsCategoryId;
						optionArr.map(function(item, index) {
						var isSelect = optionSelected === item.goodsCategoryId ? 'selected': '';optionHtml += '<option data-value="'
													+ item.goodsCategoryId
													+ '"'
													+ isSelect
													+ '>'
													+ item.goodsCategoryName
													+ '</option>';
						});
						$('#category').html(optionHtml);
					}
				});
	}

	//获取商品类别信息
	function getCategory() {
		$.getJSON(categoryUrl, function(data) {
			if (data.success) {
				var goodsCategoryList = data.goodscategorylist;
				var optionHtml = '<option>---请选择---</option>';
				goodsCategoryList.map(function(item, index) {
					optionHtml += '<option data-value="'
							+ item.goodsCategoryId + '">'
							+ item.goodsCategoryName + '</option>';
				});
				$('#category').html(optionHtml);
			}
		});
	}

	$('.detail-img-div').on('change', '.detail-img:last-child', function() {
		if ($('.detail-img').length < 6) {
			$('#detail-img').append('<input type="file" class="detail-img">');
		}
	});

	$('#submit').click(
			function() {
				var goods = {};
				goods.goodsName = $('#goods-name').val();
				goods.goodsDesc = $('#goods-desc').val();
				goods.priority = $('#priority').val();
				goods.point = $('#point').val();
				goods.normalPrice = $('#normal-price').val();
				goods.promotionPrice = $('#promotion-price').val();
				goods.goodsCategory = {
					goodsCategoryId : $('#category').find('option').not(
							function() {
								return !this.selected;
							}).data('value')
				};
				goods.goodsId = goodsId;

				var thumbnail = $('#small-img')[0].files[0];
				console.log(thumbnail);
				var formData = new FormData();
				formData.append('thumbnail', thumbnail);
				$('.detail-img').map(
						function(index, item) {
							if ($('.detail-img')[index].files.length > 0) {
								formData.append('goodsImg' + index,
										$('.detail-img')[index].files[0]);
							}
						});
				formData.append('goodsStr', JSON.stringify(goods));
				var verifyCodeActual = $('#j_captcha').val();
				if (!verifyCodeActual) {
					$.toast('请输入验证码！');
					return;
				}
				formData.append("verifyCodeActual", verifyCodeActual);
				$.ajax({
					url : goodsPostUrl,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						if (data.success) {
							$.toast('提交成功！');
							$('#captcha_img').click();
						} else {
							$.toast(data.errMsg);
							$('#captcha_img').click();
						}
					}
				});
			});

});