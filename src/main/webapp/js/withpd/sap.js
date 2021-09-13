function request() {
	var type = "sapyoung";
	var LOCAL_URL = "http://ec2-18-118-166-134.us-east-2.compute.amazonaws.com:8080/sapyoung/sapyoung";
	 $(document).ready(function(){
        })
        .ajaxStart(function(){
        })
        .ajaxStop(function(){
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
                    alert('error');
                },
                success : function(parse_data){
                        /*var json = eval('[' + parse_data + ']')[0];*/
					console.log(parse_data);
						/*alert(parse_data);*/
                }
            });
        });
}


function move() {
	var select_page = document.getElementById("select_page").value;
	location.replace(select_page + ".html")
}

function search() {
	var search_input = document.getElementById("search_input").value;
	if (search_input == "") {
		alert("검색어를 입력해주세요!");
	} else {
		alert("검색어는 " + search_input + " 입니다");
	}
}

function insert() {
	var type = "select";
	var search_input = document.getElementById("search_input").value;
	if (search_input == "") {
		alert("alert");
		return;
	} else {
		alert("search_input : " + search_input);
	}
	
	var LOCAL_URL = "http://localhost:8080/sapyoung/sapyoung";
	 $(document).ready(function(){
        })
        .ajaxStart(function(){
        })
        .ajaxStop(function(){
        });

        $(document).ready(function(){
            $.ajax({
                crossOrigin : true,
                type : "GET", // 전송방식을 지정한다 (POST,GET)
                url : LOCAL_URL + '?type=' + type + '&phone=' + search_input,
                dataType : "html",// 호출한 페이지의 형식이다. xml,json,html,text등의 여러 방식을
                                                                // 사용할 수 있다.
                beforeSend : function(xhr){
                    xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
                },
                error : function(){
                    alert('error');
                },
                success : function(parse_data){
                        /*var json = eval('[' + parse_data + ']')[0];*/
					console.log(parse_data);
						/*alert(parse_data);*/
                }
            });
        });
	

	/*
	var res = document.getElementById("search_input").value;
	document.getElementById("td1").innerHTML = res;
	*/
}

/* Teachable Machine Function Start */

// More API functions here:
// https://github.com/googlecreativelab/teachablemachine-community/tree/master/libraries/image

// the link to your model provided by Teachable Machine export panel
const URL = "./model/withpd/";

let model, webcam, labelContainer, maxPredictions;

// Load the image model and setup the webcam
async function init() {
	const modelURL = URL + "model.json";
	const metadataURL = URL + "metadata.json";

	// load the model and metadata
	// Refer to tmImage.loadFromFiles() in the API to support files from a file picker
	// or files from your local hard drive
	// Note: the pose library adds "tmImage" object to your window (window.tmImage)
	model = await tmImage.load(modelURL, metadataURL);
	maxPredictions = model.getTotalClasses();

	// Convenience function to setup a webcam
	const flip = true; // whether to flip the webcam
	webcam = new tmImage.Webcam(600, 600, flip); // width, height, flip
	await webcam.setup(); // request access to the webcam
	await webcam.play();
	window.requestAnimationFrame(loop);

	// append elements to the DOM
	document.getElementById("webcam-container").appendChild(webcam.canvas);
	labelContainer = document.getElementById("label-container");
	for (let i = 0; i < maxPredictions; i++) { // and class labels
		labelContainer.appendChild(document.createElement("div"));
	}
}

async function loop() {
	webcam.update(); // update the webcam frame
	await predict();
	window.requestAnimationFrame(loop);
}

// run the webcam image through the image model
async function predict() {
	// predict can take in an image, video or canvas html element
	const prediction = await model.predict(webcam.canvas);
	for (let i = 0; i < maxPredictions; i++) {
		const classPrediction =
			prediction[i].className + ": " + prediction[i].probability.toFixed(2);
		labelContainer.childNodes[i].innerHTML = classPrediction;
	}
}
/* Teachable Machine Function End */