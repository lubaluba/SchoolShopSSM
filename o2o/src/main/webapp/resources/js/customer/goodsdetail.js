$(function() {
	
    // 从地址栏的URL里获取goodsId
    var goodsId = getQueryString('goodsId');
    // 获取商品信息的URL
    var goodsUrl = '/o2o/goods/getgoodsbyid?goodsId=' + goodsId;
    // 访问后台获取该商品的信息并渲染
    $.getJSON(goodsUrl, function(data) {
        if (data.success) {
            // 获取商品信息
            var goods = data.goods;
            // 给商品信息相关HTML控件赋值

            // 商品缩略图
            $('#goods-img').attr('src', goods.imgAddr);
            // 商品更新时间
            $('#goods-time').text(formatDate(new Date(goods.lastEditTime).getTime(),"YY-MM-DD"));
            if (goods.point != undefined) {
            	$('#goods-point').text('购买可得' + goods.point + '积分');
            }
            // 商品名称
            $('#goods-name').text(goods.goodsName);
            // 商品简介
            $('#goods-desc').text(goods.goodsDesc);
            // 商品价格展示逻辑，主要判断原价现价是否为空 ，所有都为空则不显示价格栏目
            if (goods.normalPrice != undefined
                    && goods.promotionPrice != undefined) {
                // 如果现价和原价都不为空则都展示，并且给原价加个删除符号
                $('#price').show();
                $('#normalPrice').html(
                        '<del>' + '￥' + goods.normalPrice + '</del>');
                $('#realPrice').val(goods.promotionPrice);
                $('#goods_id').val(goods.goodsId);
                $('#promotionPrice').text('￥' + goods.promotionPrice);
            } else if (goods.normalPrice != undefined
                    && goods.promotionPrice == undefined) {
                // 如果原价不为空而现价为空则只展示原价
                $('#price').show();
                $('#promotionPrice').text('￥' + goods.normalPrice);
            } else if (goods.normalPrice == undefined
                    && goods.promotionPrice != undefined) {
                // 如果现价不为空而原价为空则只展示现价
                $('#promotionPrice').text('￥' + goods.promotionPrice);
            }
            var imgListHtml = '';
            // 遍历商品详情图列表，并生成批量img标签
            /*goods.goodsImgList.map(function(item, index) {
                imgListHtml += '<div> <img src="' + item.imgAddr
                        + '" width="100%" /></div>';
            });*/
            // if (data.needQRCode) {
            // // 生成购买商品的二维码供商家扫描
            // imgListHtml += '<div> <img
            // src="/o2o/frontend/generateqrcode4goods?goodsId='
            // + goods.goodsId
            // + '" width="100%"/></div>';
            // }
            $('#imgList').html(imgListHtml);
            checkGoods();
        }
    });
    // 点击后打开右侧栏
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
    $.init();
});

function addToCart() {
	var number = $("#number").val();
	if (number == null || number == ""){
		$.toast("请选择购买数目！！");
	} else {
		var price = $("#realPrice").val();
		var goodsId = $("#goods_id").val();
		$.ajax({
			type: "POST",
			url: "/o2o/cart/add?price=" + price +"&number=" + number + "&goodsId=" +goodsId,
			/*data:{
				"price" :price,
				"number":number,
				"goodsId":goodsId
			},
			dataType: "text",*/
			processData: false,
			contentType: false,
			cache: false,
			success: function(data){
				if(data.success){
					$.toast('成功添加至购物车!');
					location.reload();
				}else{
					$.toast('提交失败！' + data.errMsg);
				}
			},
		});
	}
};

function checkGoods(){
	var goodsId = $("#goods_id").val();
	var url = "/o2o/cart/check?goodsId="+goodsId;
	$.getJSON(url, function(data) {
		 if(data.isExist){
			 $("#buy_bottom").css("background-color","gray");//改变背景颜色
			 $("#buy_bottom").text("已购买");
		 }    
	});
}
