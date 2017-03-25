<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ad campaign</title>
<style>
	.error{
		color : red;
		font-size : large;
		font-weight : bold;
	}
	
	label{
		font-size : large;
		font-weight : bold;
		color : blue;
	}
	
	table,th, td{
		border : solid black 1px;
		border-collapse: collapse;
		padding: 15px;
		text-align: center;
		font-weight : bold;
		color : blue;
	}
	
	td {
		color : black;
	}
	
	body {
		margin : 250px;
	}
	
</style>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<%-- <script src="${pageContext.request.contextPath}/resources/js/main.js"></script> --%>


<script type="text/javascript">

function adrequest(){
	var formData = {};
	$("#form input").each(function(){
		var input = $(this);
		if (input.attr('type') == "text")
			formData[input.attr('name')] = input.val();
	});
	console.log(formData);
	$.ajax({
		type: 'POST',
		url:"ad",
		contentType: "application/json",
		dataType: 'json',
		data: JSON.stringify(formData),
		success : function () {
			$('#write').html("data saved!");
		}, 
		error : function() {
			var message = "<p class = 'error'>"
				+ "Something went wrong when saving the data."
				+ "</p>"
			$('#showError').html(message);
		}
	});
}

function getdata(){
	var partner ={};
	$("#requestform input").each(function(){
		var input = $(this);
		if (input.attr('type') == "text") {
			partner[input.attr('name')] = input.val();			
		}
	});
	
	console.log(partner);
	$.ajax({
		type: 'GET',
		url:"ad/" + partner.partner_id,
		contentType: "application/json",
		dataType: 'json',
		success:function(data){
		//	console.log(data);
			var adcam = "<p>partner id = " + data.partner_id + "<br>"
			+ "ad content = "
			+data.ad_content + '</p><br>';
			
            $('#write').html(adcam);
		},
		
		error:function(){
			
			var message = "<p class = 'error'>"
				+ "No active ad campaigns exist for the specified partner"
				+ "</p>"
			$('#showError').html(message);
//
		}
	});
}

	// show all the valid campaigh
	function showAll() {
		var pre = "<td>";
		var pos = "</td>";
		$.ajax({
			type : "GET",
			url : "list",
			dataType : "json",
			success : function (list) {
				
				var tablehead = "<tr><th>Partner ID</th><th>Ad Content</th><th>Duration</th></tr>";
				if (list.length == 0){					
					$( document ).ready(function() {
						var message = "<p class = 'error'>"
							+ "There is no data."
							+ "</p>"
						$('#tb').html(message);
					});
					
				} else {
					$('#th').html (tablehead);
					list.forEach(function(item){
						var content ="<tr>" + pre + item.partner_id + pos
						+ pre + item.ad_content + pos
						+ pre + item.duration + pos;
						
						$('#tb').html(content);
					})
				}
			},
			error : function () {
				var message = "<p class = 'error'>"
					+ "ERROR."
					+ "</p>"
				$('#showError').html(message);
			}
			
		});
	}
</script>
</head>
<body>
	<h1>Add Request</h1>
	<div>
		<form action="" id="form">
			<label>partner id:</label><br>
			<input type="text" name="partner_id"><br>
			<label>duration(s):</label><br>
			<input type="text" name="duration"><br>
			<label>ad content:</label><br>
			<input type="text" name="ad_content"><br>
			<input type="button" value = "Submit" onclick="adrequest()">
		</form>		
	</div>
	<hr>
	
	<h1>Show Content</h1>
	<div>
		<form action="" id = "requestform" method="GET">
			<label>Find by partner id:</label>
			<input type="text" name = "partner_id">
			<input type="button" value="GO" onclick="getdata()">
		</form>
	</div>
	
	<div id = "write"></div>
	<hr>
	<h1>Show All Valid Campaigh</h1>
	<button onclick="showAll()">Show</button>
	<table>	
		<thead id = "th"></thead>
		<tbody id = "tb"></tbody>
	</table>
	
	<hr>
	<div id = "showError"></div>
</body>
</html>