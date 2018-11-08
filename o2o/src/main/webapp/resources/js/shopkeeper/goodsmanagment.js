$(function() {
	var shopId = 1;
	var listUrl = '/o2o/goods/getgoodslist?pageIndex=1&pageSize=10&shopId='
			+ shopId;
	var deleteUrl = '/o2o/goodsadmin/removegoods';
	function getList() {
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var goodsList = data.goodsList;
				var tempHtml = '';
				goodsList.map(function(item, index) {
					var textOp = "下架";
					var contraryStatus = 0;
					if (item.enableStatus == 0) {
						textOp = "上架";
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					tempHtml += '' + '<div class="row row-goods">'
							+ '<div class="col-40">'
							+ item.goodsName
							+ '</div>'
							+ '<div class="col-20">'
							+ item.priority
							+ '</div>'
							+ '<div class="col-40">'
							+ '<a href="#" class="edit" data-id="'
							+ item.goodsId
							+ '" data-status="'
							+ item.enableStatus
							+ '">编辑</a>'
							+ '<a href="#" class="delete" data-id="'
							+ item.goodsId
							+ '" data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.goodsId
							+ '" data-status="'
							+ item.enableStatus
							+ '">预览</a>'
							+ '</div>'
							+ '</div>';
				});
				$('.goods-wrap').html(tempHtml);
			}
		});
	}

	getList();

	function deleteItem(id, enableStatus) {
		$.confirm('确定么?', function() {
			$.ajax({
				url : deleteUrl,
				type : 'POST',
				data : {
					goodsId : id,
					enableStatus : enableStatus
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('操作成功！');
						getList();
					} else {
						$.toast('操作失败！');
					}
				}
			});
		});
	}

	$('.goods-wrap')
			.on(
					'click',
					'a',
					function(e) {
						var target = $(e.currentTarget);
						if (target.hasClass('edit')) {
							window.location.href = 'goodsoperation.html?goodsId='
									+ e.currentTarget.dataset.id;
						} else if (target.hasClass('delete')) {
							deleteItem(e.currentTarget.dataset.id,
									e.currentTarget.dataset.status);
						} else if (target.hasClass('preview')) {
							window.location.href = '/myo2o/frontend/goodsdetail?goodsId='
									+ e.currentTarget.dataset.id;
						}
					});

	$('#new').click(function() {
		window.location.href = 'goodsoperation.html';
	});
});