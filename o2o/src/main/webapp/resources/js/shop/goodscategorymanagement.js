/**
 * 
 */
$(function(){
	getList();
	$("#new").click(
			function(){
				var tempHtml = "<div class='row row-goods-category temp'>"
					+ "<div class='col-33'><input class='category-input category' type='text' placeholder='类别'></div>"
					+ "<div class='col-33'><input class='category-input priority' type='number' placeholder='0'></div>"
					+ "<div class='col-33'><a href='#' class='button delete'>删除</a></div>"
					+ "</div>";
				$(".category-warp").append(tempHtml);
			});
	
	$("#submit").click(function(){
		var tempArr = $(".temp");
		var goodsCategoryList = [];
		for(var i = 0; i<tempArr.length; i++){
			var tempObj = {};
			tempObj.goodsCategoryName = $(tempArr[i]).find(".category").val();
			tempObj.priority = $(tempArr[i]).find(".priority").val();
			if(tempObj.goodsCategoryName && tempObj.priority){
				goodsCategoryList.push(tempObj);
			}
		}
		$.ajax({
			type: "POST",
			url: "/o2o/shopAdmin/addgoodscategorys.action",
			data:JSON.stringify(goodsCategoryList),
			contentType: "application/json",
			success: function(data){
				if(data.success){
					$.toast('提交成功!');
					getList();
				}else{
					$.toast('提交失败！' + data.errMsg);
				}
			}
		});
	});
	
})

function getList(){
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
						+ "<a href = '#' class='button delete' >删除</a></div></div>"
					}
					$("#category-row").html(html);
				}
			},
			"json"
		);
}
