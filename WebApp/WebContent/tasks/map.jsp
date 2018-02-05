<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../tmp/mapHeader.jsp"></jsp:include>

	<h1>Карта заявок</h1>

<div id="map"></div>
<script type="text/javascript">
//tasks

var tasksNotDone = Array();
<c:forEach items="${tasksNotDone}" var = "task">
	tasksNotDone.push({
		id:'${task.getId()}',
		status:'${task.getStatus()}',
		doneTime:'${task.getDoneTime()}',
		body:'${task.getBody()}',
		importance:'${task.getImportance()}',
		user:'${task.getUserId()}',
	});
</c:forEach>

var userNamesForTask = Array();
<c:forEach items="${userNamesForTask}" var = "userForTask">
	userNamesForTask.push('${userForTask}');
</c:forEach>
/*arrayAddresses*/

var addresses = Array();
<c:forEach items="${addresses}" var="address">
	addresses.push({
		address:'${address.getAddress()}',
		name:'${address.getName()}',
		log:'${address.getCoordsLon()}',
		lat:'${address.getCoordsLat()}'
	});
</c:forEach>
	
/*arrayUsers*/
var userCoordes = Array();
<c:forEach items="${userCoords}" var="userCoordFromServer">
	userCoordes.push({
		id:'${userCoordFromServer.getId()}',
		lat:'${userCoordFromServer.getLat()}',
		log:'${userCoordFromServer.getLog()}',
		userId:'${userCoordFromServer.getUserId()}',
		ts:'${userCoordFromServer.getTs()}'
	});
</c:forEach>

var users = Array();
<c:forEach items="${users}" var="user">
	users.push({
		id:'${user.getId()}',
		login:'${user.getLogin()}'
	});
</c:forEach>
</script>


<script type="text/javascript" src="../js/myjs/confirm.js"></script>
<script type="text/javascript" src="../js/myjs/mapClaster.js"></script>
<jsp:include page="../tmp/footer.jsp"></jsp:include>