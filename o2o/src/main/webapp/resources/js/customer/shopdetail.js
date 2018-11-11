$(function() {
	var loading = false;
	var maxItems = 20;
	var pageSize = 10;
	var listUrl = '/o2o/frontend/listgoodsbyshop';
	var pageNum = 1;
	var shopId = getQueryString('shopId');
	var goodsCategoryId = '';
	var goodsName = '';
	var searchDivUrl = '/o2o/frontend/listshopdetail?shopId=' + shopId;
	function getSearchDivData() {
		var url = searchDivUrl;
		$.getJSON(
				url,
				function(data) {
					if (data.success) {
						var shop = data.shop;
						$('#shop-cover-pic').attr('src', shop.shopImg);
						$('#shop-update-time').html(formatDate(new Date(shop.lastEditTime).getTime(),"YY-MM-DD"));
						$('#shop-name').html(shop.shopName);
						$('#shop-desc').html(shop.shopDesc);
						$('#shop-addr').html(shop.shopAddr);
						$('#shop-phone').html(shop.phone);
						var goodsCategoryList = data.goodsCategoryList;
						var html = '';
						goodsCategoryList.map(function(item, index) {
							html+= '<a href="#" class="button" data-goods-search-id='
								+ item.goodsCategoryId
								+ '>'
								+ item.goodsCategoryName
								+ '</a>';
						});
						$('#shopdetail-button-div').html(html);
					}
			});
	}
	getSearchDivData();

	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&goodsCategoryId=' + goodsCategoryId
				+ '&goodsName=' + goodsName + '&shopId=' + shopId;
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				var html = '';
				data.goodsList.map(function(item, index) {
					html += '' + '<div class="card" data-goods-id='
							+ item.goodsId + '>'
							+ '<div class="card-header">' + item.goodsName
							+ '</div>' + '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.imgAddr + '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.goodsDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ formatDate(new Date(item.lastEditTime).getTime(),"YY-MM-DD hh:mm")
							+ '更新</p>' + '<span>点击查看</span>' + '</div>'
							+ '</div>';
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					// 删除加载提示符
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
	addItems(pageSize, pageNum);
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});
	$('#shopdetail-button-div').on(
			'click',
			'.button',
			function(e) {
				goodsCategoryId = e.target.dataset.goodsSearchId;
				if (goodsCategoryId) {
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						goodsCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
				}
			});

	$('.list-div')
			.on(
					'click',
					'.card',
					function(e) {
						var goodsId = e.currentTarget.dataset.goodsId;
						window.location.href = 'goodsdetail.html?goodsId='
								+ goodsId;
					});

	$('#search').on('change', function(e) {
		goodsName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});

