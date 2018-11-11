$(function() {
	var goodsId = getQueryString('goodsId');
	var goodsUrl = '/o2o/goods/getgoodsbyid?goodsId='
			+ goodsId;

	$
			.getJSON(
					goodsUrl,
					function(data) {
						if (data.success) {
							var goods = data.goods;
							$('#goods-img').attr('src', goods.imgAddr);
							$('#goods-time').text(formatDate(new Date(goods.lastEditTime).getTime(),"YY-MM-DD"));
							$('#goods-name').text(goods.goodsName);
							$('#goods-desc').text(goods.goodsDesc);
							var imgListHtml = '';
							goods.goodsImgList.map(function(item, index) {
								imgListHtml += '<div> <img src="'
										+ item.imgAddr + '"/></div>';
							});
							// 生成购买商品的二维码供商家扫描
							imgListHtml += '<div> <img src="/myo2o/frontend/generateqrcode4goods?goodsId='
									+ goods.goodsId + '"/></div>';
							$('#imgList').html(imgListHtml);
						}
					});
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
