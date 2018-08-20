/**
 * 
 */
$(function(){
	$.get(
			"/o2o/shopAdmin/getgoodscategory.action",
			function(data){
				if(data.success){
					var list = data.goodscategorylist;
					var html = '';
					for(var i = 0; i < list.length; i++){
						html += "<div class='row row-goods-category now'><div class='col-33 goods-category-name'>"
						+ list[i].goodsCategoryName + "</div><div class='col-33'>"
						+ list[i].priority + "</div><div class='col-33'>"
						+ "<a href = '#'>删除</a></div></div>"
					}
					$("#category-row").html(html);
				}
			},
			"json"
		);
})