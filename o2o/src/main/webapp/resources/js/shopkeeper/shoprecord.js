$(function() {
	var loading = false;
	var maxItems = 999;
	var pageSize = 10;
	var shopId = getQueryString("shopId");
	var url = '/o2o/cart/getShopRecord';
	var pageNum = 1;
	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		loading = true; 
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				var html = '';
				data.list.map(function(item, index) {
					html +=	'<div class="card" order-id="'
							+ item.userId + '">' + '<div class="card-header">购买用户:'
							+ item.userId +"&nbsp&nbsp	下单时间:" 
							+ formatDate(new Date(item.buyTime).getTime(),"YY-MM-DD hh:mm")
							+'</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' 
							+ '<ul>'
							
							+ '<li class="item-content">'
							+ '<div class="item-media">' 
							+ '<span><img src="' + item.goods.imgAddr + '" width="44"></span>' 
							+ '<span style="width:130px">&nbsp&nbsp' + item.goods.goodsName +  '</span>'
							+ '</div>' 
							+ '</li>' 
							
							+ '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<span>总数：' + item.count +'</span>'
							+ '<span>总收入：' + item.amount +  '</span>' + '</div>'
							+ '</div>';
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					$('.infinite-scroll-preloader').hide();
				} else {
					$('.infinite-scroll-preloader').show();
				}
				pageNum += 1;
				loading = false;
				$.refreshScroller();
			}
		});
	}
	// 预先加载20条
	addItems(pageSize, pageNum);

	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});

	 $('#me').click(function () {
	        $.openPanel('#panel-left-demo');
	    });

});
function back(){
	window.location.href = "../"
};
