<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>HelloWorld page</title>
		
		<script type="text/javascript">
		
			/*AJAX call Example*/
			xmlHttp = new XMLHttpRequest();
			
			function getScores() {
				xmlHttp.onreadystatechange = function() {
					/*
					* 0: Hasn't Started
					* 1: Connected to the Server
					* 2: Server has received our request
					* 3: Server Processing
					* 4: Rquest is finished and data is ready
					*/
					if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
						documetn.getElementById('scores').innertHTML = xmlHttp.responseText;
					}
					else {
						document.getElementById('scores').innerHTML = "<strong>Waiting for Server Response...</strong>"
					}
				}
				xmlHttp.open("GET", "http://www.learntoprogram.tv/baseball.php", true);
				xmlHttp.send();
			}
		</script>
	</head>
	
	<body>
		Greeting : ${greeting}
		<br>
		ServerStatus : ${isAlive}
		<br>
		
		<div id="scores">
		</div>
		<input type="button" value="Get Scores!" onclick="getScores()"/>
	</body>
</html>