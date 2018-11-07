<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>商店详情</title>
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel="shortcut icon" href="/favicon.ico">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
<link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
<link rel="stylesheet" href="../resources/css/frontend/shopdetail.css">
</head>
<body>
	<div class="page-group">
		<div class="page">
			<header class="bar bar-nav">
				<a class="button button-link button-nav pull-left" href="#"
						onClick="javascript :history.back(-1);" data-transition='slide-out'>
					<span class="icon icon-left"></span> 返回
				</a>
				<h1 class="title">店铺详情</h1>
			</header>
			<nav class="bar bar-tab">
				<a class="tab-item" href="/myo2o/frontend/index"> 
					<span class="icon icon-home"></span> 
					<span class="tab-label">首页</span>
				</a>
				<a class="tab-item" href="#" id="me">
					<span class="icon icon-me"></span>
					<span class="tab-label">我</span>
				</a>
			</nav>
			<div class="content infinite-scroll infinite-scroll-bottom" data-distance="100">
				<!-- 这里是页面内容区 -->
				<div class="shop-detail-dev">
					<div class="card">
						<div class="card-header color-white no-border no-padding">
							<img class='card-cover' id="shop-cover-pic" src="" alt="">
						</div>
						<div class="card-content">
							<div class="card-content-inner">
								<p class="color-gray">
									<span id="shop-update-time"></span>
								</p>
								<p >
									<span id="shop-name" style="color:red;"></span>
								</p>
								<p id="shop-desc"></p>
								<span id="shop-addr"></span>
								<span id="shop-phone" style="float:right"></span>
							</div>
						</div>
						<div class="card-footer">
							<!-- <a href="#" class="link">赞</a> -->
						</div>
					</div>
				</div>
				<div class="shopdetail-button-div" id="shopdetail-button-div">
					<!-- <a href="#" class="button">所有货物</a> -->
				</div>
				<div class="detail-search">
					<div class="searchbar">
						<a class="searchbar-cancel">取消</a>
						<div class="search-input">
							<label class="icon icon-search" for="search"></label> 
							<input type="search" id='search' placeholder='输入关键字...' />
						</div>
					</div>
				</div>
				<div class="list-div"></div>
				<div class="infinite-scroll-preloader">
					<div class="preloader"></div>
				</div>
			</div>
		</div>

		<!--侧边栏  -->
		<div class="panel-overlay"></div>
		<div class="panel panel-right panel-reveal" id="panel-left-demo">
			<div class="content-block">
				<p>
					<a href="/myo2o/frontend/myrecord" class="close-panel">消费记录</a>
				</p>
				<p>
					<a href="/myo2o/frontend/mypoint" class="close-panel">我的积分</a>
				</p>
				<p>
					<a href="/myo2o/frontend/pointrecord" class="close-panel">积分兑换记录</a>
				</p>
				<!-- Click on link with "close-panel" class will close panel -->
			</div>
		</div>
	</div>

	<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
	<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
	<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
	<script type='text/javascript' src='../resources/js/commom/commom.js' charset='utf-8'></script>
	<script type='text/javascript' src='../resources/js/frontend/shopdetail.js' charset='utf-8'></script>
</body>
</html>
