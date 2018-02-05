 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../tmp/mapHeader.jsp"></jsp:include>
	
	<h1>Карта объектов</h1>

<div id="map"></div>
	
<script type="text/javascript">
var addresses = [];
<c:forEach items="${addresses}" var="address">
	addresses.push({
		id:'${address.getId()}',
		pos:'${address.getAddress()}',
		name:'${address.getName()}',
		lat:'${address.getCoordsLat()}',
		log:'${address.getCoordsLon()}'
	});	
</c:forEach>

/*map*/
   ymaps.ready(init);
   var myMap;

  	var myLat = '60.0190183';
  	var myLog = '30.329576';
   
   function init(){     
       myMap = new ymaps.Map("map", {
           center: [myLat, myLog],
           zoom: 10
       });
       myGeoObjects = [];
       for(var i = 0; i < addresses.length; i++){
    	   addCluster(i, 'islands#darkGreenDotIcon');
   	   }       
       
       
       function addCluster(i, presetColor){
			myGeoObjects[i] = new ymaps.GeoObject({
	    	    geometry: { type: "Point", coordinates: [addresses[i].log, addresses[i].lat] },
	    	    properties: {
	    	        clusterCaption: addresses[i].name,
	    	        balloonContentHeader: addresses[i].name,
	    	        balloonContentBody: addresses[i].pos,
	    	        balloonContentFooter:'<a href="/WebApp/tasks/createTask?address='+addresses[i].id
	    	        		+'">Создать заявку</a>',
	    	        locationUrl: '/WebApp/tasks/createTask?address='+addresses[i].id
	    	    }
	    	}, {
	    		preset: presetColor
	    	    }
	    	);
		}
       
	   
	   var clusterer = new ymaps.Clusterer({ 
	    	clusterDisableClickZoom: true,
	    	clusterIconLayout: 'default#pieChart',
	        // Радиус диаграммы в пикселях.
	        clusterIconPieChartRadius: 25,
	        // Радиус центральной части макета.
	        clusterIconPieChartCoreRadius: 10,
	        // Ширина линий-разделителей секторов и внешней обводки диаграммы.
	        clusterIconPieChartStrokeWidth: 3
	    });
	    clusterer.add(myGeoObjects);
	    myMap.geoObjects.add(clusterer);

   }
	</script>
	
<jsp:include page="../tmp/footer.jsp"></jsp:include>