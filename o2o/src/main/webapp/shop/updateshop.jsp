<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>SUI Mobile Demo</title>
<meta name="description" content="MSUI: Build mobile apps with simple HTML, CSS, and JS components.">
<meta name="author" content="阿里巴巴国际UED前端">
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel="shortcut icon" href="/favicon.ico">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
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
</head>
<body>
	<div class="page-group">
    <div id="page-label-input" class="page">
  	<header class="bar bar-nav">
    	<a class="button button-link button-nav pull-left back" href="/demos/form">
    		<span class="icon icon-left"></span>返回</a>
   		 	<h1 class="title">店铺修改</h1>
  	</header>
  	<div class="content">
    <div class="list-block">
      <ul>
       <!-- 店铺姓名 -->
        <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">店铺名称</div>
              <div class="item-input">
                <input type="text" placeholder="店名" id="shop-name" >
              </div>
            </div>
          </div>
        </li>
        <!-- 店铺类型 -->
        <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">店铺类型</div>
              <div class="item-input">
                <select id = "shop-category">
                </select>
              </div>
            </div>
          </div>
        </li>
       <!-- 所属区域 -->
       <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">所属区域</div>
              <div class="item-input">
                <select id = "shop-area">
                </select>
              </div>
            </div>
          </div>
        </li>
       <!-- 联系方式 -->
        <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">联系方式</div>
              <div class="item-input">
                <input type="text" placeholder="联系方式" id="phone">
              </div>
            </div>
          </div>
        </li>
        <!-- 店铺图片 -->
        <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">店铺图片</div>
              <div class="item-input">
                <input type="file" id = "shop-img">
              </div>
            </div>
          </div>
        </li>
       <!-- 店铺地址 -->
        <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">详细地址</div>
              <div class="item-input">
                <input type="text"  placeholder="address" id="shop-addr">
              </div>
            </div>
          </div>
        </li>
        <!-- 店铺详情 -->
        <li class="align-top">
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">店铺简介</div>
              <div class="item-input">
                <textarea placeholder="店铺简介" id="shop-desc"></textarea>
              </div>
            </div>
          </div>
        </li>
        <!-- 验证码 -->
        <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">验证码</div>
              <input type="text" id = "j_captcha" placeholder="验证码">
              <div class="item-input">
                <img id = "captcha_img" alt="点击更换" title="点击更换" onclick="changeVerifyCode(this)"  src = "../kaptcha"> 
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
    <div class="content-block">
      <div class="row">
        <div class="col-50"><a href="#" class="button button-big button-fill button-danger">返回</a></div>
        <div class="col-50">
        	<a href="javascript:void(0)" class="button button-big button-fill button-success" id="addShopSubmit" onclick="update()">更新</a>
        </div>
      </div>
    </div>
  </div>
</div>`

  </div>
    <script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
    <script type="text/javascript" src="../resources/js/shop/shopupdate.js"></script>
    <script type="text/javascript" src="../resources/js/commom/commom.js"></script>
  </body>
</html>
