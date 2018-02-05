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
    
    //myGeoObjects = [];
    
    
    
    var MyClusterBalloonItemContentLayout = ymaps.templateLayoutFactory.createClass([
    	  '<a href="/WebApp/tasks/editTask?id={{ properties.taskId }}" title="{{ properties.title }}" class="edit_btn">',
    	  '<span class="glyphicon glyphicon-edit map_btn edit_map_btn"></span>',
    	  '</a>'
    	].join(''), {
    	  build: function() {
    	    MyClusterBalloonItemContentLayout.superclass.build.call(this);
    	    jQuery('.edit_btn', this.getParentElement()).on('click', function(e) {
    	      alert('btn clicked');
    	    })
    	  }
    	});
    
    var myClusterer = new ymaps.Clusterer({
    	clusterDisableClickZoom: true,
    	clusterIconLayout: 'default#pieChart',
    	clusterBalloonPanelMaxMapArea: 1,
        // Радиус диаграммы в пикселях.
        clusterIconPieChartRadius: 25,
        // Радиус центральной части макета.
        clusterIconPieChartCoreRadius: 10,
        // Ширина линий-разделителей секторов и внешней обводки диаграммы.
        clusterIconPieChartStrokeWidth: 3,

	    clusterBalloonItemContentLayout: MyClusterBalloonItemContentLayout
    	});
    	    
	myMap.geoObjects.add(myClusterer);
	
	for(var i = 0; i < addresses.length; i++){
    	if(tasksNotDone[i].importance=='Стандартная'){
    		addCluster(i, 'islands#darkGreenDotIcon');
    	} else if(tasksNotDone[i].importance=='Аварийная'){
    		addCluster(i, 'islands#redDotIcon');
    	}else if(tasksNotDone[i].importance=='Информационная'){
    		addCluster(i, 'islands#blueDotIcon');
    	}
    }
    
	function addCluster(i, presetColor){
		myClusterer.add(new ymaps.GeoObject({
  		  geometry: {
  		    type: "Point",
  		    coordinates: [addresses[i].log, addresses[i].lat]
  		  },
  		  properties: {
  		    taskId: tasksNotDone[i].id,
  		    title: "Редактировать задание",
  		    clusterCaption: tasksNotDone[i].status
  		  }
  		}, {
			  preset: presetColor
		  }));
	}

//    var presets = Array();
//    for(var i = 0; i < myGeoObjects.length; i++){
//    	presets.push(myGeoObjects[i].options.get('preset'));
//    }
//
//    var isPick=false;
//    setInterval(function(){
//    	//одно из двух условий для возможности мигать
//    				  
//    					  //перебираем список меток
//    					  for(var i = 0; i < myGeoObjects.length; i++){
//    						  //если статус = новое задание
//    						  if(isPick){
//    						  if((tasksNotDone[i].status=='новое задание')||(tasksNotDone[i].status=='отказ')||
//    							(tasksNotDone[i].status=='контроль')||(tasksNotDone[i].status=='нужна помощь')){
//    							  //делаем точке иконку одного цвета
//    							  myGeoObjects[i].options.set("preset", "islands#yellowDotIcon");
//    						  	}
//    						  }else {
//    							  //иначе ставим иконке его изначальный цвет(не работает)
//    							  myGeoObjects[i].options.set('preset', presets[i]);
//    						  }
//    						  
//    					  }
//    					  clusterer.options.set('visible',false);
//				            clusterer.options.set('visible',true);
//    				//меняем условие на противоположное
//    				  isPick=!isPick; 		  
//  	  },2000);
}