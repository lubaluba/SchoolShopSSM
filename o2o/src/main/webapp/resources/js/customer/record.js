$(function() {
	var loading = false;
	var maxItems = 999;
	var pageSize = 10;
	var url = '/o2o/cart/getRecord';
	var pageNum = 1;
	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		loading = true; 
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				var html = '';
				data.list.map(function(item, index) {
					var content = '';
					item.recordList.map(function(record,index){
						content +='<li class="item-content">'
								+ '<div class="item-media">' 
								+ '<span><img src="' + record.goods.imgAddr + '" width="44"></span>' 
								+ '<span style="width:100px">' + record.goods.goodsName +  '</span>'
                                + '<span class="one" style="width:80px">数目:' +record.count + ' </span>'
                                + '<span>总价￥' + record.amount + '</span>'
								+ '</div>' 
								+ '</li>' 
					});
					html +=	'<div class="card" order-id="'
							+ item.orderId + '">' + '<div class="card-header">订单号:'
							+ item.orderId +"&nbsp&nbsp	下单时间:" 
							+ formatDate(new Date(item.orderTime).getTime(),"MM-DD hh:mm")
							+'</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' 
							+ '<ul>'
							+ content
							+ '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<span>总数：' + item.count +'</span>'
							+ '<span>总花费：' + item.amount +  '</span>' + '</div>'
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
