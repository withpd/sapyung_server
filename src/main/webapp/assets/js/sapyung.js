function hide_mask() {
	$('#mask, .window').hide();
}

function show_mask() {
    // 화면의 높이와 너비를 구한다.
    var maskHeight = $(document).height();  
    var maskWidth = $(window).width();  
	
    // 마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
    $('#mask').css({'width':maskWidth,'height':maskHeight});  
	
    // 애니메이션 효과
    $('#mask').fadeIn(1000);      
    $('#mask').fadeTo("slow",0.6);
}

function search() {
	var searchSelect = document.getElementById('search_condition');
	var searchCondition = searchSelect.options[searchSelect.selectedIndex].value
	var searchWord = document.getElementById('search_word').value;
	
	if(searchWord == '') {
		alert('검색어를 입력해주세요');
	}
	else {
//		alert(searchCondition + ', ' + searchWord);
		
		var mask_flag = false;
		
		$(document).ready(function(){
			hide_mask();
		})
		.ajaxStart(function(){
			if(mask_flag == false) {
				show_mask();
				mask_flag = true;
			}
		})
		.ajaxStop(function(){
			hide_mask();
		});

		$(document).ready(function(){
		    $.ajax({
		    	crossOrigin : true,
		        type : "GET", // 전송방식을 지정한다 (POST,GET)
		        url : LOCAL_URL + '?type=' + type,
		        dataType : "html",// 호출한 페이지의 형식이다. xml,json,html,text등의 여러 방식을
									// 사용할 수 있다.
		        beforeSend : function(xhr){
		            xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
		        },
		        error : function(){
		            info('잠시 후에 다시 시도해주세요');
		        },
		        success : function(parse_data){
		        	var json = eval('[' + parse_data + ']')[0];
		        	if(type == 'VINTEDCATE') {
		        		vintedJson = json;
		        	} else if(type == 'CAROUSELLCATE') {
		        		carousellJson = json;
		        	} else if(type == 'MERCICATE') {
		        		merciJson = json;
		        	} else if(type == 'MATCHESMENCATE') {
		        		matchesMenJson = json;
		        	} else if(type == 'MATCHESWOMENCATE') {
		        		matchesWomenJson = json;
		        	}
		        	var keyList = Object.keys(json);
		        	for (var i = 0; i<keyList.length; i++) {
		        		var key = keyList[i];
		        		addSubCate(cateUpSelectBox, json[key]);
		        	}
		        	hide_mask();
		        }
		    });
		}); 
		
		
	}
	
}

