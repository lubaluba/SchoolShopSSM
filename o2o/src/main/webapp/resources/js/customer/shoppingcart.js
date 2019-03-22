$(function() {
	var loading = false;
	var maxItems = 999;
	var pageSize = 10;
	var url = '/o2o/cart/get';
	var pageNum = 1;
	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		loading = true; 
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.total;
				var html = '';
				if(maxItems == 0){	
					$("#buy_botton").css("background-color","gray");//改变背景颜色
					$("#buy_botton").removeAttr("onclick")
					var img = "<img alt='空空如也' src='/upload/ss.jpg' width='480px' height='400px'>";
					$("#content").append(img);
				} else{
					
				}
				data.items.map(function(item, index) {
					html +=	'<div class="card" item-id="'
							+ item.itemId + '">' + '<div class="card-header">'
							+ item.shop.shopName + '</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' 
							+ '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' 
							+ '<span><img src="' + item.goods.imgAddr + '" width="44"></span>' 
							+ '<span>' + item.goods.goodsName +  '</span>'
							+ '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">'
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<span>数目：' + item.number +'</span>'
							+ '<span>价格：' + item.price +  '</span>' + '</div>'
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

function buy(){
	if(window.confirm('你确定要购买吗？')){
		$.get(
				"/o2o/cart/buy",
				false,
				function(data){
					if(data.success){
						$.toast("购买成功！");
						location.reload();
					} else{
						$.toast("购买失败！！")
					}
				},
				"json"
			);
     }else{
        return false;
    }
}
