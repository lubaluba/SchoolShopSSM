$(function() {
	var url = '/o2o/frontend/listmainpageinfo';
    $.getJSON(url, function (data) {
        if (data.success) {
            var headLineList = data.headLineList;
            var swiperHtml = '';
            headLineList.map(function (item, index) {
                swiperHtml += ''
                            + '<div class="swiper-slide img-wrap">'
                            + 	'<img class="banner-img" src="'+ item.lineImg +'" alt="'+ item.lineName +'">'
                            + '</div>';
            });
            $('.swiper-wrapper').html(swiperHtml);
            $(".swiper-container").swiper({
                autoplay: 3000,
                autoplayDisableOnInteraction: false
            });
            var shopCategoryList = data.shopCategoryList;
            var categoryHtml = '';
            shopCategoryList.map(function (item, index) {
                categoryHtml += ''
                             +  '<div class="col-50 shop-classify" data-category='+ item.shopCategoryId +'>'
                             +      '<div class="word">'
                             +          '<p class="shop-title">'+ item.shopCategoryName +'</p>'
                             +          '<p class="shop-desc">'+ item.shopCategoryDesc +'</p>'
                             +      '</div>'
                             +      '<div class="shop-classify-img-warp">'
                             +          '<img class="shop-img" src="' +item.shopCategoryImg +'">'
                             +      '</div>'
                             +  '</div>';
            });
            $('.row').html(categoryHtml);
        }
    });

    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });

    $('.row').on('click', '.shop-classify', function (e) {
        var shopCategoryId = e.currentTarget.dataset.category;
        var newUrl = 'customer/shoplist.html?parentId=' + shopCategoryId;
        window.location.href = newUrl;
    });
    
	$('#log-out').click(function () {
		$.ajax({
			url : "/o2o/local/logout",
			type : "post",
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					window.location.href = '/o2o/user/login.html';
				}
			},
			error : function(data, error) {
				alert(error);
			}
		});
	});

});

function toList() {
	window.location.href = "customer/shoplist.html"
}