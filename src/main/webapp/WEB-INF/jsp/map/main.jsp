<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<!-- bootstrap CDN link -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

  
  <!-- AJAX를 사용하기 위해 slim 아닌 풀버전의 jquery로 교체 -->
  <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="/static/css/layout_style.css" style="text/css">
  
  <!-- kakao map -->
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=48a2dc2c13e5484fc30051ee7881315f&libraries=services,clusterer"></script>
</head>
<body>
	<header class="mt-3">
		<div class="d-flex justify-content-center">
			<input type="text" class="form-control col-5" style="margin-left: 9%" placeholder="주소를 입력하세요" id="address">
			<button type="button" class="btn btn-success" id="searchBtn">검색</button>
			<button type="button" class="btn btn-danger" id="saveBtn">저장</button>
			<select class="form-control col-1" id="storeSelect">
				<option selected value="0">Choose...</option>
				<c:forEach items="${storeList}" var="store">
				<option value="${store.id}">${store.storeName}</option>
				</c:forEach>
			</select>
			<button class="btn btn-outline-secondary" type="button" data-toggle="modal" data-target="#typeControlModal" id="menuTypeUpdateBtn">수정</button>
			<a href="/excel/download" class="btn btn-primary ml-4">엑셀 다운로드</a>
			<!-- 메뉴 수정 Modal -->
			<div class="modal fade" id="typeControlModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="exampleModalLabel">메뉴 수정</h5>
			      </div>
			      <div class="modal-body">
			      	<div class="d-flex justify-content-end">
			      		<button type="button" class="btn btn-primary" id="modalAddBtn">추가</button>
			      	</div>
			      	<div id="menuTypeUpdateBox">
				      	<c:forEach items="${storeList}" var="store">
				      	<div class="input-group">
					        <input type="text" class="form-control" value="${store.storeName}">
					        <button type="button" class="btn btn-danger modal-del-btn">삭제</button>
				       	</div>
				        </c:forEach>
			        </div>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="exitModal">닫기</button>
			        <button type="button" class="btn btn-primary" id="modalTypeSaveBtn">저장</button>
			      </div>
			    </div>
			  </div>
			</div>
		</div>
		
	</header>
	<section class="d-flex justify-content-center my-3">
		<div id="map" style="width:60%;height:700px;"></div>
	</section>
	<footer class="d-flex justify-content-center">
		<div class="d-flex justify-content-center col-7">
			<input type="text" class="form-control" id="searchedAddress">
			<input type="text" class="form-control" placeholder="위도" id="latitude">
			<input type="text" class="form-control" placeholder="경도" id="longtitude">
		</div>
	</footer>
	
	<!-- 모달 영역 -->
<div id="modalBox" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel">모달 타이틀</h4>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
			</div>
			<div class="modal-body">
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="saveModalBtn">수정</button>
				<button type="button" class="btn btn-default" id="closeModalBtn">취소</button>
			</div>
		</div>
	</div>
</div>


	<script>
		$(document).ready(function() {
			
			var container = document.getElementById('map');
			var options = {
				center: new kakao.maps.LatLng(37.5283530798249, 126.712996959483),
				level: 5
			};
	
			var map = new kakao.maps.Map(container, options);
			
			var oldAddress = '';
			
			var markers = [];
			
			function setMarkers(map) {
				for (let i = 0; i < markers.length; i++) {
					markers[i].setMap(map);
				}
				markers =[];
			}
			
			// 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
			function makeOverListener(map, marker, infowindow) {
			    return function() {
			        infowindow.open(map, marker);
			    };
			}

			// 인포윈도우를 닫는 클로저를 만드는 함수입니다 
			function makeOutListener(infowindow) {
			    return function() {
			        infowindow.close();
			    };
			}
			
			function makeClickListener(map, marker, infowindow, tempAddress, tempCount, tempId) {
				return function() {
					$('#modalBox .modal-body *').remove();
					if(tempAddress.indexOf('|') > -1 || tempCount.toString().indexOf('|') > -1) {
						let address = tempAddress.split('|');
						let count = tempCount.split('|');
						let id = tempId.split('|');
						for (let i = 0; i < address.length; i++) {
							$('#modalBox .modal-body').append('<div class="d-flex justify-content-end align-items-center my-1"><div class="px-3">' + address[i] + '</div>' + '<input type="text" class="form-control col-4 mx-3 count_input_box" value="' + count[i] + '" data-id="' + id[i] + '"><button type="button" class="btn btn-danger address_del_btn" data-id="' + id[i] + '">삭제</button></div>');
						}
					} else {
						$('#modalBox .modal-body').append('<div class="d-flex justify-content-end my-1"><div class="px-3">' + tempAddress + '</div>' + '<input type="text" class="form-control col-4 mx-3 count_input_box" value="' + tempCount + '" data-id="' + tempId + '"><button type="button" class="btn btn-danger address_del_btn" data-id="' + tempId + '">삭제</button></div>');
					}
					$('#modalBox').modal('show');
				};
			}
			
			var clusterer = new kakao.maps.MarkerClusterer({
		        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
		        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
		        minLevel: 7 // 클러스터 할 최소 지도 레벨 
		    });
		    
		    // 선택한 주문 목록 삭제
		    $(document).on('click', '.address_del_btn', function() {
		    	if(confirm('삭제하시겠습니까?')) {
					let id = $(this).data('id');
					$.ajax({
						type:'post'
						,url : '/order/delete'
						,data : {'id' : id}
						, success : function(data) {
			    			if (data.result == 'success') {
			    				alert('삭제 완료');
			    				location.reload(true);
			    			} else {
			    				alert('삭제 실패');
			    			}
		    			}
			    		, error : function(e) {
			    			alert("삭제 실패 관리자에게 문의해주세요");
			    		}
					});
		    	}
			});
		    
		    $('#closeModalBtn').on('click', function() {
		    	$('#modalBox').modal('hide');
		    });
		    
		    $('#saveModalBtn').on('click', function() {
		    	let idList = new Array();
		    	let valueList = new Array();
		    	$('.count_input_box').each(function(index, item) {
		    		let id = $(item).data('id');
		    		let value = $(item).val();
		    		idList.push(id);
		    		valueList.push(value);
		    	});
		    	
		    	$.ajax({
		    		type: 'post'
		    		, url: '/order/update'
		    		, data: {
		    			'idList' : idList
		    			, 'valueList' : valueList
		    		}
		    		, success : function(data) {
		    			if (data.result == 'success') {
		    				alert('업데이트 완료');
		    				location.reload(true);
		    			} else {
		    				alert('업데이트 실패');
		    			}
		    		}
		    		, error : function(e) {
		    			alert("업데이트 실패 관리자에게 문의해주세요");
		    		}
		    	});
		    });
		    
		    var mappingData = {};
		 
			$('#storeSelect').on('change', function() {
				let storeId = $('#storeSelect option:selected').val();
				setMarkers(null);
				$.ajax({
					type:'post'
					, url: '/order/select'
					, data: {'storeId': storeId}
					, success : function(data) {
						if(data.result=="fail") {
							alert("불러오기 실패");
							return;
						} else {
							var positions = data.result;
							var tempContent = '';
							// 데이터에서 좌표 값을 가지고 마커를 표시합니다
					        // 마커 클러스터러로 관리할 마커 객체는 생성할 때 지도 객체를 설정하지 않습니다
					       /*  var marker = positions.map(function(i, position) {
					            return new kakao.maps.Marker({
					                position : new kakao.maps.LatLng(i.latitude, i.longtitude)
					            });
					        }); */

					        // 클러스터러에 마커들을 추가합니다
					       	/* clusterer.addMarkers(marker); */
					        let tempAddress = '';
							let tempCount = '';
							let id = '';
							for (let i = 0; i < positions.length; i ++) {
								if(i != 0 && positions[i].latitude == positions[i-1].latitude && positions[i].longtitude == positions[i-1].longtitude) {
									tempContent = tempContent + '<div style="width:500px;text-align:center;padding:6px 0;">' + positions[i].address + " 주문횟수 : " + positions[i].orderCount + '</div>';
									tempAddress = tempAddress + '|' + positions[i].address;
									tempCount = tempCount + '|' + positions[i].orderCount;
									id = id + "|" + positions[i].id;
								} else {
									tempContent = '<div style="width:500px;text-align:center;padding:6px 0;">' + positions[i].address + " 주문횟수 : " + positions[i].orderCount + '</div>';
									tempAddress = positions[i].address;
									tempCount = positions[i].orderCount;
									id = positions[i].id;
								}
							    // 마커를 생성합니다
							    var marker = new kakao.maps.Marker({
							        map: map, // 마커를 표시할 지도
							        position: new kakao.maps.LatLng(positions[i].latitude, positions[i].longtitude) // 마커의 위치
							    });

							 	

							    // 마커에 표시할 인포윈도우를 생성합니다 
							    var infowindow = new kakao.maps.InfoWindow({
							        content: tempContent // 인포윈도우에 표시할 내용
							    });
							    
							    markers.push(marker);

							    /* let tempKey = positions[i].latitude + ' ' + positions[i].longtitude;
							    let address = positions[i].address;
							    let orderCount = positions[i].orderCount;
							    mappingData[tempKey] = {marker, infowindow, address, orderCount};
							    console.log(mappingData); */
							    
							   /*  console.log(tempAddress);
							    console.log(tempCount); */
							    
							    
							    // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
							    // 이벤트 리스너로는 클로저를 만들어 등록합니다 
							    // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
							    kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
							    kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
							 	// 마커에 클릭이벤트를 등록합니다
								kakao.maps.event.addListener(marker, 'click', makeClickListener(map, marker, infowindow, tempAddress, tempCount, id));
							}
							clusterer.addMarkers(markers);
						}
					}
					, error : function(e) {
						alert("불러오기 실패 관리자에게 문의해주세요");
					}
				});
			});
			
			// 모달을 종료했을때 페이지 새로고침
			$('#exitModal').on('click', function() {
				location.reload(true);
			});
			
			// 모달에 추가 버튼 눌렀을 시 input 추가
			$('#modalAddBtn').on('click', function() {
				$('#menuTypeUpdateBox').append('<div class="input-group"><input type="text" class="form-control"><button type="button" class="btn btn-danger modal-del-btn">삭제</button></div>');
			});
			
			// 모달에서 삭제버튼 클릭시 해당 열 삭제
			$(document).on('click', '.modal-del-btn', function() {
				$(this).parent('div').remove();
			});
			
			// 모달에서 저장버튼 클릭시 db저장
			$('#modalTypeSaveBtn').on('click', function() {
				let storeList = new Array();
				// 리스트에 값 추가
				$('#menuTypeUpdateBox input').each(function(index, item) {
					storeList.push($(item).val());
				});
				console.log(storeList);
				// 유효성 검사
				if (storeList.length <= 0) {
					alert('적어도 하나 이상은 추가해야합니다.');
					return;
				}
				for (var value of storeList) {
					if (value == '') {
						alert('내용을 입력하세요');
						return;
					}
				}
				
				let sendData = {'storeList' : storeList};
				console.log(sendData);
				
				$.ajax({
					type : 'post'
					, url : '/store/change'
					, data : sendData
					, success : function(data) {
						if (data.result == 'success') {
							location.reload(true);
						} else {
							alert('메뉴가 수정되지 않았습니다. 다시 시도해주세요');
						}
					}
					, error : function(e) {
						alert('메뉴가 수정되지 않습니다. 관리자에게 문의해주세요' + e);
					}
				});
			});
			
			function orderCreate(storeId, oldAddress, latitude, longtitude) {
				$.ajax({
					type: 'post'
					, url: '/order/create'
					, data: {
						'storeId' : storeId
						,'address' : oldAddress
						, 'latitude' : latitude
						, 'longtitude' : longtitude
					}
					, success : function(data) {
						if (data.result == 'success') {
							alert("포인트가 추가되었습니다.");
							location.reload(true);
						} else {
							alert("포인트가 추가되지 않았습니다.");
						}
					}
					, error : function(e) {
						alert("포인트가 추가되지 않았습니다. 관리자에게 문의해주세요");
						console.log(e);
						return;
					}
				});
			}
			
			var tempMarker = [];
			
			function setTempMarkers(map) {
				for (let i = 0; i < tempMarker.length; i++) {
					tempMarker[i].setMap(map);
				}
				tempMarker =[];
			}
			
			$('#searchedAddress').on('change', function() {
				
			});
			
			$('#saveBtn').on('click', function() {
				let address = $('#searchedAddress').val().trim();
				let latitude = $('#latitude').val();
				let longtitude = $('#longtitude').val();
				if (address == '') {
					alert("주소를 검색해주세요");
					return;
				}
				if (confirm("현재 위치를 기록하시겠습니까?")) {
					let storeId = $('#storeSelect option:selected').val();
					if (storeId == '0') {
						alert("지점을 선택해주세요");
						return;
					}
					orderCreate(storeId, address, latitude, longtitude);
				} else {
					setTempMarkers(null);
					oldAddress = '';
					$('#searchedAddress').val('');
					$('#longtitude').val('');
					$('#latitude').val('');
				}
			});
			
			$('#searchBtn').on('click', function() {
				let address = $('#address').val().trim();
				let latitude = $('#latitude').val();
				let longtitude = $('#longtitude').val();
				if (address == '') {
					alert("주소를 입력해주세요");
					return;
				}
				/* if(oldAddress != '' && oldAddress != address) {
					if (confirm("현재 위치를 기록하시겠습니까?")) {
						let storeId = $('#storeSelect option:selected').val();
						if (storeId == '0') {
							alert("지점을 선택해주세요");
							return;
						}
						orderCreate(storeId, oldAddress, latitude, longtitude);
					} else {
						setTempMarkers(null);
						oldAddress = '';
					}
				}
				
				if(oldAddress == address) {
					if (confirm("현재 위치를 기록하시겠습니까?")) {
						let storeId = $('#storeSelect option:selected').val();
						if (storeId == '0') {
							alert("지점을 선택해주세요");
							return;
						}
						orderCreate(storeId, oldAddress, latitude, longtitude);
					} else {
						setTempMarkers(null);
						oldAddress = '';
						return;
					}
				} */
				
				// 주소-좌표 변환 객체를 생성합니다
				var geocoder = new kakao.maps.services.Geocoder();
		
				/* // 과거 주소 저장
				oldAddress = $('#address').val(); */
				
				// 주소로 좌표를 검색합니다
				geocoder.addressSearch(address, function(result, status) {
		
				    // 정상적으로 검색이 완료됐으면 
				    if (status === kakao.maps.services.Status.OK) {
		
				        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
		
				        $('#searchedAddress').val(address);
				        $('#latitude').val(result[0].y);
				        $('#longtitude').val(result[0].x);
				        
				        // 마커를 생성합니다
				        var marker = new kakao.maps.Marker({
				            position: coords
				        });

				        // 마커가 지도 위에 표시되도록 설정합니다
				        marker.setMap(map);

				        // 마커가 드래그 가능하도록 설정합니다 
				        marker.setDraggable(true); 
				        
				        tempMarker.push(marker);
				        
				        // 인포윈도우로 장소에 대한 설명을 표시합니다
				        var infowindow = new kakao.maps.InfoWindow({
				            content: '<div style="width:150px;text-align:center;padding:6px 0;">검색한 곳</div>'
				            , position: marker.getPosition()
				        });
				        
				        kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
				        kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
				        
				        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
				        map.setCenter(coords);
				        
						kakao.maps.event.addListener(marker, 'dragend', function() {
							infowindow.setPosition(marker.getPosition());
							$('#latitude').val(marker.getPosition().Ma);
					        $('#longtitude').val(marker.getPosition().La);
					        
					        var coord = new kakao.maps.LatLng(marker.getPosition().Ma, marker.getPosition().La);
					        var callback = function(result, status) {
					            if (status === kakao.maps.services.Status.OK) {
					                $('#searchedAddress').val(result[0].road_address.address_name);
					            }
					        };

					        geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
						});
				    }
				});
			});
			
			

		});
		
	</script>
</body>
</html>