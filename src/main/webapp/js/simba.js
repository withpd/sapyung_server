var prev_page;
var pres_page;
var selectItem;

function insertUser(id, accessToken, name, thumbNailImg, isEmailValid, isEmailVerified, mail, loginType) {
	var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "Simba?type=insertUser" + "&id=" + id + "&accessToken=" + accessToken + "&name=" + name + "&thumbNailImg=" + thumbNailImg + "&isEmailValid=" + isEmailValid + "&isEmailVerified=" + isEmailVerified + "&mail=" + mail + "&loginType=" + loginType);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState === 4 && xmlhttp.status == 200) {
//          var jsonData = JSON.parse(xmlhttp.response);
           	alert(name + "님 가입을 환영합니다!");
    	} 
  	}
}

function selectUserByMail(mail) {
	var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "Simba?type=selectUserByMail" + "&mail=" + mail);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState === 4 && xmlhttp.status == 200) {
           	var jsonData = JSON.parse(xmlhttp.response);
           	console.log('selectUserByMail : ' + jsonData);
    	} 
  	}
}

function order() {
	console.log(document.getElementById('orderForm'));
}

function selectUser(id, kakao_access_token, name, thumb_nail_img, isEmailValid, isEmailVerified, mail, type) {
	var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "Simba?type=selectUser" + "&mail=" + mail);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState === 4 && xmlhttp.status == 200) {
           	var jsonData = JSON.parse(xmlhttp.response);
//           	console.log('jsonData : ' + jsonData);
           	if(jsonData == '1') {
           		alert('이미 가입되어 있습니다.');
           		updateAccessToken(kakao_access_token, mail);
           		goIndex(name, thumb_nail_img, mail);
           	} else if(jsonData == '0'){
           		alert('가입되었습니다.');
           		insertUser(id, kakao_access_token, name, thumb_nail_img, isEmailValid, isEmailVerified, mail, type);
           		goIndex(name, thumb_nail_img, mail);
           	} else {
           		alert('로그인에 실패했습니다.');
           		window.location.reload();
           	}
    	} 
  	}
}

function updateAccessToken(accessToken, mail) {
	var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "Simba?type=updateAccessToken" + "&accessToken=" + accessToken + "&mail=" + mail);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState === 4 && xmlhttp.status == 200) {
    		var jsonData = JSON.parse(xmlhttp.response);
//    		alert("UPDATE 성공 : " + jsonData);
    	}
  	}
}

function insertItem(mail, name, color, length, amount, price, imgUrl, saveType, title, cate) {
	title = title.replace(/%/g, 'XX');
	var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "Simba?type=insertItem" + "&mail=" + mail + "&name=" + name + "&color=" + color + "&length=" + length + "&amount=" + amount + "&price=" + price + "&imgUrl=" + imgUrl + "&saveType=" + saveType + "&title=" + title + "&cate=" + cate);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState === 4 && xmlhttp.status == 200) {
    		var json = JSON.parse(xmlhttp.response);
    		if(json == '-1') {
    			alert('[Insert] 다시 시도해주세요');
    		} else {
    			alert(saveType + '에 추가되었습니다');
    		}
    	} 
  	}
}

function checkLogin() {
	Kakao.init('09a0c6eb7b4c38ce0b4e8f9dd25fa33b');
	Kakao.Auth.getStatus(function(statusObj) {
		var json = JSON.parse(JSON.stringify(statusObj));
		if(json.status == 'connected') {
			console.log(json);
//			alert(json.status);
			var nickName = json.user.properties.nickname;
			var thumbnailImage = json.user.properties.thumbnail_image;
			var mail = json.user.kaccount_email;
			goIndex(nickName, thumbnailImage, mail);
		}
	});
}

index = 0;
var rootElement;
function likeInit(itemJson) {
	rootElement = document.getElementById("like_content");
//	rootElement.setAttribute('id', 'item_root_elem');
	if(itemJson == null) {
		rootElement.setAttribute('style', 'color:white;');
		rootElement.innerHTML = "It's Empty";
		var item_menu = document.getElementsByClassName("item_menu");
		for(i=0; i < item_menu.length; i++) {
			item_menu[i].style.visibility = "hidden";;
		}
		return;
	}
//	var html = $("#add_like_template").html();

    var likeTplElement = document.createElement("div");
    likeTplElement.setAttribute('name', 'add_like_template');
    
    var likeInnerElement = document.createElement("div");
//    likeInnerElement.setAttribute('id', 'like_inner_content');
    likeInnerElement.setAttribute('align', 'right');
    likeInnerElement.setAttribute('style', 'width:50%; height:auto; min-width:300px; border: 2px solid #ffffff; border-radius: 5px; margin-bottom: 2em; padding: 1em; display:flex; flex-direction: row;');
    
    var inputElement = document.createElement("input");
    inputElement.setAttribute('type', 'checkbox');
    inputElement.setAttribute('style', 'padding:10px;');
    inputElement.setAttribute('class', 'item_input');
    inputElement.addEventListener("click", function() {
    	buyInit();
    });
    
    var imgElement = document.createElement("img");
    imgElement.src = itemJson.imgUrl;
    imgElement.setAttribute('class', 'item_img');
    imgElement.setAttribute('style', 'width: 4em; height: 4em; margin-left:10px; margin-right:10px; vertical-align: middle; align-items: flex-end;');
    imgElement.addEventListener("click", function() {
    	
    });
    
    var conElement1 = document.createElement("div");
    var conElement2 = document.createElement("div");
    var conElement3 = document.createElement("div");
    
    var titleElement = document.createElement("span");
    titleElement.setAttribute('class', 'item_title');
    titleElement.append(itemJson.title);
    titleElement.setAttribute('style', 'display: inline-block; width: 100%; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; color:white;');
    
    var priceElement = document.createElement("span");
    priceElement.setAttribute('class', 'item_price');
    priceElement.append(itemJson.price);
    priceElement.setAttribute('style', 'margin-top: 5px; color:white;');
    
    var priceMenuElement = document.createElement("span");
//    priceMenuElement.setAttribute('class', 'item_price_menu');
    priceMenuElement.append("가격 : ");
    priceMenuElement.setAttribute('style', 'margin-top: 5px; color:white; font-size:0.7em;');
    
    var colorElement = document.createElement("span");
    colorElement.setAttribute('class', 'item_color');
    colorElement.append(itemJson.color);
    colorElement.setAttribute('style', 'margin-top: 5px; color:white;');
    
    var colorMenuElement = document.createElement("span");
    colorMenuElement.setAttribute('class', 'item_color_menu');
    colorMenuElement.append("색 : ");
    colorMenuElement.setAttribute('style', 'color:white; font-size:0.7em; margin-left: 20px; ');
    
    var sizeElement = document.createElement("span");
    sizeElement.setAttribute('class', 'item_size');
    sizeElement.append(itemJson.length);
    sizeElement.setAttribute('style', 'margin-top: 5px; color:white;');
    
    var sizeMenuElement = document.createElement("span");
    sizeMenuElement.setAttribute('class', 'item_size_menu');
    sizeMenuElement.append("크기 : ");
    sizeMenuElement.setAttribute('style', 'color:white; font-size:0.7em; margin-left: 20px; ');
    
    var amountElement = document.createElement("span");
    amountElement.setAttribute('class', 'item_amount');
    amountElement.append(itemJson.amount);
    amountElement.setAttribute('style', 'margin-top: 5px; color:white;');
    
    var amountMenuElement = document.createElement("span");
    amountMenuElement.setAttribute('class', 'item_amount_menu');
    amountMenuElement.append("수량 : ");
    amountMenuElement.setAttribute('style', 'color:white; font-size:0.7em; margin-left: 20px; ');
    
    var rootConElement = document.createElement("div");
    rootConElement.setAttribute('style', 'float:right; width:75%;');
    rootConElement.setAttribute('align', 'right');
    rootConElement.setAttribute('class', 'item_root');
    conElement1.appendChild(titleElement);
    
    conElement2.appendChild(colorMenuElement);
    conElement2.appendChild(colorElement);
    conElement2.appendChild(sizeMenuElement);
    conElement2.appendChild(sizeElement);
    conElement2.appendChild(amountMenuElement);
    conElement2.appendChild(amountElement);
    conElement3.appendChild(priceMenuElement);
    conElement3.appendChild(priceElement);
    rootConElement.appendChild(conElement1);
    rootConElement.appendChild(conElement2);
    rootConElement.appendChild(conElement3);
    
    likeInnerElement.appendChild(inputElement);
    likeInnerElement.appendChild(imgElement);
    likeInnerElement.appendChild(rootConElement);
    likeTplElement.appendChild(likeInnerElement);
    rootElement.appendChild(likeTplElement);

}

function buyInit() {
	var json = '';
	var item_nodes = rootElement.childNodes;
	var result = 0;
	var delivery_price = 0;
	for(i=0; i < item_nodes.length; i++) {
		var data = {}; 
		var item = item_nodes[i];
		var isChecked = item_nodes[i].getElementsByClassName("item_input")[0].checked;
		
		if(isChecked) {
			var price = item.getElementsByClassName("item_price")[0].innerHTML;
			result += Number(price);	// 합
			
			data.mail = document.getElementById("menu_mail").innerHTML;
			data.imgUrl = item.getElementsByClassName("item_img")[0].src;
			data.color = item.getElementsByClassName("item_color")[0].innerHTML;
			data.saveType = price;
			data.length = item.getElementsByClassName("item_size")[0].innerHTML;
			data.amount = item.getElementsByClassName("item_amount")[0].innerHTML;
			data.price = price;
			data.title = item.getElementsByClassName("item_title")[0].innerHTML;
			json += JSON.stringify(data) + "yyyyyy";
			
		}
	}
	
	json = btoa(json);
	if(json.length == 0 ) {
		document.getElementById("order_json").value = '';
	} else {
		document.getElementById("order_json").value = json;
	}
	
	var euroProductPrice = result * 1321;
	document.getElementById("order_product_price").value = addComma(Number(euroProductPrice));
	if(!euroProductPrice == 0) {
		delivery_price = 20000;
	}
	document.getElementById("order_delivery_price").value = addComma(Number(delivery_price));
	var total_price = document.getElementById("order_total_price").value = addComma(Number(euroProductPrice + delivery_price));
}

function itemMenuClick(type, page) {
	if(type == 'buy' || type == 'del') {
		var json = '';
		var item_nodes = rootElement.childNodes;
		for(i=0; i < item_nodes.length; i++) {
			var data = {}; 
			var item = item_nodes[i];
			var isChecked = item_nodes[i].getElementsByClassName("item_input")[0].checked;
			var name = document.getElementById("menu_name").innerHTML.split(" ")[0];
			if(isChecked) {
				data.mail = document.getElementById("menu_mail").innerHTML;
				data.imgUrl = item.getElementsByClassName("item_img")[0].src;
				data.color = item.getElementsByClassName("item_color")[0].innerHTML;
				data.saveType = page;
				data.length = item.getElementsByClassName("item_size")[0].innerHTML;
				data.amount = item.getElementsByClassName("item_amount")[0].innerHTML;
				data.price = item.getElementsByClassName("item_price")[0].innerHTML;
				data.title = item.getElementsByClassName("item_title")[0].innerHTML;
				json += JSON.stringify(data) + "yyyyyy";
			}
		}
		json = btoa(json);
		if(json.length == 0 ) {
			alert("상품을 선택해 주세요");
			return;
		}
		if(type == 'del') {
			deleteItem(json, page);
		} else if(type == 'buy'){
			// 동일한 조건의 구매라면 해당 상품의 구매 수량을 업데이트 해주는 부분 필요
			insertItem(data.mail, name, data.color, data.length, data.amount, data.price, data.imgUrl, type, data.title, '' );
		}
	} else if(type == 'sel') {
		var inputList = rootElement.getElementsByTagName("input");
		var checked;
		for(i=0; i < inputList.length; i++) {
			if(i == 0) {
				checked = inputList[i].checked
			}
			inputList[i].checked = !checked;
   		}
		if(page == 'buy') {
			buyInit();
		}
	}
}

function deleteItem(json, page) {
	var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "Simba?type=deleteLikeItem&json=" + json);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState === 4 && xmlhttp.status == 200) {
           	var jsonData = JSON.parse(xmlhttp.response);
           	if(jsonData == null || jsonData.length == 0) {
           	} else {
           		if(page == 'like') {
           			binding_page('like.html');
           		} else if(page == 'buy') {
           			binding_page('buy.html');
           		}
           	}
    	} 
  	}	
}

function orderRegister() {
	binding_page('buy.html');
}


function selectOrderByUser(mail) {
	var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "Simba?type=selectOrderByUser" + "&mail=" + mail);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState === 4 && xmlhttp.status == 200) {
           	var jsonData = JSON.parse(xmlhttp.response);
           	console.log(xmlhttp.response);
           	if(jsonData == null || jsonData.length ==0) {
           		likeInit(null);
           	} else {
           		for(i=0; i < jsonData.length; i++) {
           			likeInit(jsonData[i]);
           		}
           	}
    	} 
  	}
}

function selectItemByType(mail, saveType) {
	var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "Simba?type=selectItemByType" + "&mail=" + mail + "&saveType=" + saveType);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState === 4 && xmlhttp.status == 200) {
           	var jsonData = JSON.parse(xmlhttp.response);
//           	console.log('jsonData : ' + jsonData.length);
           	if(jsonData == null || jsonData.length ==0) {
           		likeInit(null);
           	} else {
           		for(i=0; i < jsonData.length; i++) {
           			likeInit(jsonData[i]);
           		}
           	}
           	if(saveType == 'buy') {
           		buyInit();
           	}
    	} 
  	}
}
function selectItem(mail, name, selectColor, title, selectSize, saveType, imgSrc, amount, price) {
	var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "Simba?type=selectItem" + "&mail=" + mail + "&color=" + selectColor  + "&title=" + title + "&saveType=" + saveType + "&name=" + name + "&length=" + selectSize + "&imgSrc=" + imgSrc);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState === 4 && xmlhttp.status == 200) {
           	var jsonData = JSON.parse(xmlhttp.response);
           	if(jsonData == -1 || jsonData == '-1') {
           		insertItem(mail, name, selectColor, selectSize, amount, price, imgSrc, saveType, title);
           	} else {
           		var updateAmountCnt = parseInt(jsonData) + parseInt(amount);
           		updateAmount(mail, name, selectColor, selectSize, saveType, title, updateAmountCnt);
           	}
           	
           	if(saveType == 'like') {
           		$('a#bottom_menu_like').addClass('active').siblings('a').removeClass('active');
           		$("#section_home").load('like.html');
           	} else if(saveType == 'buy') {
           		$('a#bottom_menu_buy').addClass('active').siblings('a').removeClass('active');
           		$("#section_home").load('buy.html');
           	}
           	
    	} 
  	}
}

function updateAmount(mail, name, selectColor, selectSize, saveType, title, updateAmountCnt) {
	var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "Simba?type=updateAmount" + "&mail=" + mail + "&color=" + selectColor  + "&title=" + title + "&saveType=" + saveType + "&name=" + name + "&length=" + selectSize + "&updateAmount=" + updateAmountCnt);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState === 4 && xmlhttp.status == 200) {
           	var jsonData = JSON.parse(xmlhttp.response);
           	if(jsonData == -1 || jsonData == '-1') {
           		alert('[Update] 다시 시도해주세요');
           	} else {
//           		alert(saveType + '에 추가되었습니다');
           	}
    	} 
  	}
}

//function logout(type) {
//	if(type == 'KAKAO') {
//		Kakao.Auth.logout(function() { console.log("logged out."); });
//		$('body').load('login.html');
//	}
//	
//	/*var nickName = document.getElementById("nickname");*/
//}

/*
function loginAnotherId(type) {
	if(type == 'KAKAO') {
		var nickName;
		var thumbnailImage;
		var name;
		var id;
		var thumb_nail_img;
		var isEmailValid;
		var isEmailVerified;
		var mail;
		var new_access_token;
		var old_token = Kakao.Auth.getAccessToken();
		
		Kakao.Auth.loginForm({
			success: function(authObj) {
				var json = JSON.parse(JSON.stringify(authObj));
				new_access_token = authObj.access_token;
				
//				alert(old_token);
//				alert(new_token);
			},
			fail : function(errorObj) {
				alert('another ID Fail...');
				console.log(errorObj);
			}
		});
//	   $('body').load('login.html');
//		window.location.reload();
	} else if(type == 'NAVER') {
		
	}
}
*/

function login(type) {
	if(type == 'KAKAO') {
		var kakao_access_token;
		var id;
		var name;
		var thumb_nail_img;
		var isEmailValid;
		var isEmailVerified;
		var mail;
//		Kakao.init('09a0c6eb7b4c38ce0b4e8f9dd25fa33b');
        Kakao.Auth.login({
        	success: function(authObj) {
        		window.location.reload();
	        	var json = JSON.parse(JSON.stringify(authObj));
	    		kakao_access_token = json.access_token; 
	    		Kakao.API.request({
    	          url: '/v2/user/me',
    	          success: function(res) {
//    	        	  alert(kakao_access_token);
    	        	  var json = JSON.parse(JSON.stringify(res));
    	        	  name = json.properties.nickname;
    	        	  id = json.id;
    	        	  thumb_nail_img = json.properties.thumbnail_image;
    	        	  isEmailValid = json.kakao_account.is_email_valid;
    	        	  isEmailVerified = json.kakao_account.is_email_verified;
    	        	  mail = json.kakao_account.email;
    	        	  selectUser(id, kakao_access_token, name, thumb_nail_img, isEmailValid, isEmailVerified, mail, type);
    	          },
    	          fail: function(error) {
    	        	  alert("로그인에 실패했습니다.");
    	        	  window.location.reload();
    	          }
    			});
        	},
        	fail: function(err) {
        		alert("로그인에 실패했습니다.");
        		window.location.reload();
        	}
        });
	} else if(type == 'NAVER'){
		alert(type);
	}
}
/*
function binding_page(page) {
	pres_page=page;
	if(pres_page != 'index.html') {
		$("#section_home").load(page);
	} else if(page == 'like.html') {
		alert('js"s menu_like');
//		var menu_like = document.getElementById("bottom_menu_like");
//		menu_like.setAttribute('class', 'active');
		
//		$('a#bottom_menu_like').addClass('active').siblings('a').removeClass('active');
	} else if(page == 'login.html') {
		$("#section_home").load(page);
	}
}*/

function backVisible(prevPage, isVisible) {
	if(isVisible == true) {
		prev_page = prevPage;
		document.getElementById("img_back").style.visibility = 'visible';
	} else {
		document.getElementById("img_back").style.visibility = 'hidden';
	}
}

function btnClick(saveType) {
	var colorElement = document.getElementById('custom-color');
	var sizeElement = document.getElementById('custom-size');
	
	var selectColor = colorElement.options[colorElement.selectedIndex].text;
	var selectSize = sizeElement.options[sizeElement.selectedIndex].text;
	var name = document.getElementById("menu_name").innerHTML.split(" ")[0];
	var mail = document.getElementById("menu_mail").innerHTML;
	
	
	if(selectColor.includes("Select") || selectSize.includes("Select")) {
		alert("옵션을 선택해주세요!");
		return;
	}
	var amount = document.getElementById("amount").value;
	var price = document.getElementById("price").innerHTML;
	var title = document.getElementById("title").innerHTML;
	var imgSrc = document.getElementsByClassName("item_img")[0].src;
	selectItem(mail, name, selectColor, title, selectSize, saveType, imgSrc, amount, price);
}

function addElement(item) {

	var imgList = item.imgUrl.split(",");
	var colorList = item.color;
	var sizeList = item.sizes;
	var price = item.price;
	var title = item.title;
	var url = item.url;
	var cate = item.cate;
   	
    var rootDiv = document.getElementById("columns");
/*    if(rootDiv == null) {
    	rootDiv = document.createElement("columns");
    }*/
    // create a new div element
    var figureElement = document.createElement("figure");
    var imgElement = document.createElement("img");
    var figcaptionElement = document.createElement("figcaption");
    var figcaptionText = document.createTextNode(title);
    figcaptionElement.appendChild(figcaptionText);
//    imgElement.src = "img/logo1.png";
    imgElement.src = imgList[0];
    imgElement.addEventListener("click", function() {
    	selectItem = item;
    	// clearElement();
    	goSelectDetailItem(imgList, price, colorList, sizeList, title, cate);
    	backVisible('display.html', false);
    });
    figureElement.appendChild(imgElement);
    figureElement.appendChild(figcaptionElement);
    rootDiv.appendChild(figureElement);
}

function goSelectDetailItem(imgList, price, colorList, sizeList, title, cate ) {
	$("#section_home").load("detail.html", function(data, status, jqXGR) {  // callback function
		var rootSlider = document.getElementById("lightSlider");
		for(i=0; i < imgList.length; i++) {
			if(imgList[i] != "") {
    			var liElement = document.createElement("li");
    			var imgElement = document.createElement("img");
    			imgElement.setAttribute("src", imgList[i]);
    			imgElement.setAttribute("class", "item_img");
    			liElement.setAttribute("data-thumb", imgList[i]);
    			liElement.appendChild(imgElement);
    			rootSlider.appendChild(liElement);
			}
		}
		$("#price").append(price);
//		$("#color").append(colorList);
		custom_select_box("custom-color", colorList.split(","));
		custom_select_box("custom-size", sizeList.split(","));
//		$("#size").append(sizeList);
		$("#title").append(title);
		$("#cate").append(cate);
		wheel_check = true;
	});
}

function custom_select_box(id, list) {
	
	var divElement = document.getElementById(id);
	
	for(i=0; i < list.length; i++) {
		var optElement = document.createElement("option");
		optElement.setAttribute("value", i+1);
		if(list[i].includes('_x')) {
			list[i] = list[i].replace('_x', "(품절)");
			optElement.setAttribute("disabled", "disabled");
		} 
		optElement.innerHTML = list[i];
		divElement.appendChild(optElement);
	}
}

function closeAllSelect(elmnt) {
  var x, y, i, arrNo = [];
  x = document.getElementsByClassName("select-items");
  y = document.getElementsByClassName("select-selected");
  for (i = 0; i < y.length; i++) {
    if (elmnt == y[i]) {
      arrNo.push(i)
    } else {
      y[i].classList.remove("select-arrow-active");
    }
  }
  for (i = 0; i < x.length; i++) {
    if (arrNo.indexOf(i)) {
      x[i].classList.add("select-hide");
    }
  }
}

function goIndex(name, thumb_nail_img, mail) {
	$('body').load('index.html', function(data, status, jqXGR) {
		/*var nickName = document.getElementById("nickname");*/
		var thumbNail = document.getElementById("thumb_nail");
		var menu_name = document.getElementById("menu_name");
		var menu_mail = document.getElementById("menu_mail");
		/*nickName.append(name + ' 님');*/
		thumbNail.src = thumb_nail_img;
		menu_name.innerHTML = name + ' 님';
		menu_mail.innerHTML = mail;
		
	});
}

function addComma(num) {
  var regexp = /\B(?=(\d{3})+(?!\d))/g;
  return num.toString().replace(regexp, ',');
}

document.addEventListener("click", closeAllSelect);
