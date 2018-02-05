 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../tmp/header.jsp"></jsp:include>

<h1>Уведомления</h1>

<h1 id="big"></h1>

<script src="https://www.gstatic.com/firebasejs/4.1.3/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/4.1.3/firebase-auth.js"></script>
<script src="https://www.gstatic.com/firebasejs/4.1.3/firebase-database.js"></script>
<script src="https://www.gstatic.com/firebasejs/4.1.3/firebase-messaging.js"></script>


<script src="https://www.gstatic.com/firebasejs/4.1.3/firebase.js"></script>
<script>
	var firebase = require("firebase/app");
	require("firebase/auth");
	require("firebase/database");

  // Initialize Firebase
  var config = {
    apiKey: "AIzaSyD8vDFo6AwvU9A2KsCJJ9QE2nmTTQWwATI",
    authDomain: "webtmpclient.firebaseapp.com",
    databaseURL: "https://webtmpclient.firebaseio.com",
    projectId: "webtmpclient",
    storageBucket: "",
    messagingSenderId: "88225485829"
  };
  firebase.initializeApp(config);
 
  if ('serviceWorker' in navigator) {
	  console.log('Service Worker is supported');
	  navigator.serviceWorker.register('/WebApp/sw.js').then(function() {
	  return navigator.serviceWorker.ready;
	}).then(function(serviceWorkerRegistration) {

	   console.log('Service Worker is ready :^)', reg);
	}).catch(function(error) {
	   console.log('Service Worker Error :^(', error);
	});
	}
  
</script>



<jsp:include page="../tmp/footer.jsp"></jsp:include>