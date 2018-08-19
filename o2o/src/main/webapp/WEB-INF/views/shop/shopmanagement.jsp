<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>SUI Mobile Demo</title>
    <meta name="description" content="MSUI: Build mobile apps with simple HTML, CSS, and JS components.">
    <meta name="author" content="阿里巴巴国际UED前端">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="../resources/css/shop/shopmanagement.css">
	<link rel="stylesheet" href="../resources/css/shop/shoplist.css">
    <!-- Google Web Fonts -->
	<link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
	<link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
	<link rel="apple-touch-icon-precomposed" href="/assets/img/apple-touch-icon-114x114.png">
    <script>
var _hmt = _hmt || [];
(function() {
    var hm = document.createElement("script");
    hm.src = "//hm.baidu.com/hm.js?ba76f8230db5f616edc89ce066670710";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();
</script>
  <body>
    <header class = "bar bar-nav">
  		<h1 class="title">商店管理</h1>
  	</header>
  <div class="content">
  	<div class="cotent-block">
  		<div class = "row">
  			<div class="col-50 mb">
  				<a id = "shopinfo" href="/o2o/shopAdmin/toUpdatePage"
  					class="button button-big button-fill">商铺信息</a>
  			</div>
  			<div class="col-50 mb">
  				<a  href="/o2o/shop/productmanage?"
  					class="button button-big button-fill">商品管理</a>
  			</div>
  			<div class="col-50 mb">
  				<a href="#" class="button button-big button-fill">类别管理</a>
  			</div>
  			<div class = "col-100 mb">
  				<a href="/o2o/shopAdmin/toShopList" class="button button-big button-fill">返回</a>
  			</div>
		</div>
	</div>
</div>
   	 	<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
    	<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
    	<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
    	<script type="text/javascript" src="../resources/js/shop/shopmanagement.js"></script>
  </body>
</html>

