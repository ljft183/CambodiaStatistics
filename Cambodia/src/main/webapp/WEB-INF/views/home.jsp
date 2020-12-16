<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-3.3.1.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
<script type="text/javascript"> 
$(function(){ 
	var formObj4 = $("form[name='uploadForm2']");
	// Excel Upload : List Grid
	$("#uploadCambodiaBtn").on("click", function(){	
		formObj4.attr("action", "/getListAddExcelCambodia.do");
		formObj4.submit();
	});
	$("#cambodia").on("click",function(e){
		e.preventDefault();
		window.location.href = '/cambodia.do';
	});
})


</script>
	<title>Home</title>
</head>
<body>
<h1>
	Cambodia  
</h1>

	<div>
		<button id="cambodia">cambodia_excel</button>
	</div>
	<div>
		<form name="uploadForm2" method="POST" enctype="Multipart/form-data">
			<div>
	    		<input type="file" name="fileName2" />	
	    		<button type="submit" id="uploadCambodiaBtn">UploadCam</button>
	        </div>
	    </form>
	</div>
</body>
</html>
