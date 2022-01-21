<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<!-- bootstrap CDN link -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

  <!-- kakao map -->
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=48a2dc2c13e5484fc30051ee7881315f"></script>
  
  <!-- AJAX를 사용하기 위해 slim 아닌 풀버전의 jquery로 교체 -->
  <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>

  <link rel="stylesheet" href="/static/css/layout_style.css" style="text/css">
</head>
<body>
	<header class="mt-3">
		<div class="d-flex justify-content-center">
			<input type="text" class="form-control col-5" placeholder="주소를 입력하세요">
			<button type="button" class="btn btn-success">검색</button>
			<button type="button" class="btn btn-danger">삭제</button>
		</div>
	</header>
	<section class="d-flex justify-content-center my-3">
		<div id="map" style="width:60%;height:700px;"></div>
	</section>
	<footer class="d-flex justify-content-center">
		<div class="d-flex justify-content-center col-7">
			<input type="text" class="form-control">
			<input type="text" class="form-control" placeholder="위도">
			<input type="text" class="form-control" placeholder="경도">
		</div>
	</footer>
	<script>
		$(document).ready(function() {
			alert();
		})
		var container = document.getElementById('map');
		var options = {
			center: new kakao.maps.LatLng(33.450701, 126.570667),
			level: 3
		};

		var map = new kakao.maps.Map(container, options);
	</script>
</body>
</html>