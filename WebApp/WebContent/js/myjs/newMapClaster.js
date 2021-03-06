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
    //добавляем юзеров на карту
    for(var i = 0; i < userCoordes.length; i++){
    	myMap.geoObjects.add(new ymaps.Placemark([userCoordes[i].lat, userCoordes[i].log], {
    		iconContent: getLoginById(userCoordes[i].userId),
    		hintContent: userCoordes[i].ts
        }, {
        	 preset: 'islands#blackStretchyIcon'
        }
        ));
    }
	
    function getLoginById(id){
    	for(var i = 0; i < users.length; i++){
    		if(users[i].id==id){
    			return users[i].login;
    		}
    	}
    }
    
    myGeoObjects = [];
    
    for(var i = 0; i < addresses.length; i++){
    	if(tasksNotDone[i].importance=='Стандартная'){
    		addCluster(i, 'islands#darkGreenDotIcon');
    	} else if(tasksNotDone[i].importance=='Аварийная'){
    		addCluster(i, 'islands#redDotIcon');
    	}else if(tasksNotDone[i].importance=='Информационная'){
    		addCluster(i, 'islands#blueDotIcon');
    	}
    }
    
    var customBalloonContentLayout = ymaps.templateLayoutFactory.createClass([
        '<ul class=list>',
        // Выводим в цикле список всех геообъектов.
        '{% for geoObject in properties.geoObjects %}',
            '<li><a href=# data-placemarkid="{{ geoObject.properties.placemarkId }}" class="list_item">{{ geoObject.properties.balloonContentHeader|raw }}</a></li>',
        '{% endfor %}',
        '</ul>'
    ].join(''));


	jQuery(document).on( "click", "a.list_item", function() {
	    // Определяем по какой метке произошло событие.
	    var selectedPlacemark = myGeoObjects[jQuery(this).data().placemarkid];
	    alert( selectedPlacemark.properties.get('balloonContentFooter') );
	});
	
	
	
	
    
	function addCluster(i, presetColor){
		myGeoObjects[i] = new ymaps.GeoObject({
    	    geometry: { type: "Point", coordinates: [addresses[i].log, addresses[i].lat] },
    	    properties: {
    	        clusterCaption: tasksNotDone[i].status,
    	        balloonContentHeader: tasksNotDone[i].status,
    	        balloonContentBody:
    	        	'<a id="test" href="/WebApp/tasks/editTask?id='+tasksNotDone[i].id
    	        	+'" title="Редактировать задание" class="edit_btn" id="edit_task">'+
    	        	'<span class="glyphicon glyphicon-edit map_btn edit_map_btn"></span></a>'+
    	        	 /*'<a href="/WebApp/tasks/deleteTask?id='+tasksNotDone[i].id
    	        	 +'" title="Удалить задание" id="delete_task">'+
    	            '<span class="glyphicon glyphicon-remove map_btn"></span></a>'+*/
    	            '<a href="/WebApp/tasks/done_tasks?id='+tasksNotDone[i].id
    	            +'" title="В выполненные" id="make_done_task">'+
    	            '<span class="glyphicon glyphicon-ok map_btn done_map_btn"></span></a>'+
	        	'<ins>Адрес:</ins> ' +addresses[i].name+
    			'<br/><ins>Заявка:</ins> '+ tasksNotDone[i].body+
    			'<br/><ins>Статус:</ins> '+tasksNotDone[i].status+
    			'<br/><ins>Исполнитель:</ins> ' + userNamesForTask[i]+
    			'<br/><ins>Важность:</ins> '+ tasksNotDone[i].importance+
	    		'<br/><ins>Сделать до:</ins> '+tasksNotDone[i].doneTime,
	    		balloonContentFooter: tasksNotDone[i].id,
	    		placemarkId: i
    	    }
    	}, {
    		preset: presetColor
    	    }
    	);
	}
  
	$('#test').click(function() {
		  alert( "Handler for .click() called." );
	});
	
    var clusterer = new ymaps.Clusterer({ 
    	clusterDisableClickZoom: true,
    	clusterIconLayout: 'default#pieChart',
    	clusterBalloonPanelMaxMapArea: 1,
        // Радиус диаграммы в пикселях.
        clusterIconPieChartRadius: 25,
        // Радиус центральной части макета.
        clusterIconPieChartCoreRadius: 10,
        // Ширина линий-разделителей секторов и внешней обводки диаграммы.
        clusterIconPieChartStrokeWidth: 3,
        clusterBalloonContentLayout: customBalloonContentLayout
    });

    clusterer.add(myGeoObjects);
    myMap.geoObjects.add(clusterer);

    var presets = Array();
    for(var i = 0; i < myGeoObjects.length; i++){
    	presets.push(myGeoObjects[i].options.get('preset'));
    }

    var isPick=false;
    setInterval(function(){
    	//одно из двух условий для возможности мигать
    				  
    					  //перебираем список меток
    					  for(var i = 0; i < myGeoObjects.length; i++){
    						  //если статус = новое задание
    						  if(isPick){
    						  if((tasksNotDone[i].status=='новое задание')||(tasksNotDone[i].status=='отказ')||
    							(tasksNotDone[i].status=='контроль')||(tasksNotDone[i].status=='нужна помощь')){
    							  //делаем точке иконку одного цвета
    							  myGeoObjects[i].options.set("preset", "islands#yellowDotIcon");
    						  	}
    						  }else {
    							  //иначе ставим иконке его изначальный цвет(не работает)
    							  myGeoObjects[i].options.set('preset', presets[i]);
    						  }
    						  
    					  }
    					  clusterer.options.set('visible',false);
				            clusterer.options.set('visible',true);
    				//меняем условие на противоположное
    				  isPick=!isPick; 		  
  	  },2000);
}