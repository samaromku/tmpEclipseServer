	eventSource = new EventSource(path);

	var notification;
	eventSource.onmessage = function(e) {
		  console.log("Пришло сообщение: " + e.data);
		  /*bootstrap_alert.warning('Уведомление: '+e.data, 'success', 4000);*/
		  checkNotifyRequest(e.data);
	}

	      function checkNotifyRequest(message){
	    	  $( document ).ready(function() {
	    		  if(Notification.permission === 'default'){
	    			  if(!window.Notification){
		    		    	alert("Нужно включить уведомления в браузере");
		    		    }else {
		    		    	Notification.requestPermission(function(p){
		    		    		if(p==='denied'){
		    		    			alert('Вы отказались от уведомлений');
		    		    		}else if(p==='granted'){
		    		    			alert('Вы будете получать новые уведомления');
		    		    		}
		    		    	});
		    		    }
	    		  }else {
	    			  notify(message);
	    		  }   		  
	    		});
	      }
	      
	      function notify(message){
	    	  var jsonObject = JSON.parse(message);
	    	  var task = jsonObject.task;
		      notification = new Notification(jsonObject.response, {
		        body:'Адрес: ' + task.address +
		        	'\nСтатус: ' + task.status + 
		        	'\nВажность: ' + task.importance + 
		        	'\nВыполнить до: ' + task.doneTime +
		        	'\nЧто сделать: ' + task.body,
		        requireInteraction: true,
		        icon: '../img/message.png'
		      });
		      
		      var audioElement = document.createElement('audio');
	          audioElement.setAttribute('src', '../mp3/v.mp3');
	          audioElement.setAttribute('autoplay:false', 'autoplay');
	          audioElement.play();
		      
		      $(notification).click(function() {
		    	  var win
		    	  if(task.status=='выполнено'){
		    		  win = window.open('/WebApp/tasks/done_tasks?id=' + task.id, '_blank');
		    	  }else{
		    		  win = window.open('/WebApp/tasks/home?id=' + task.id, '_blank');
		    	  }
		    	  win.focus();  
		      });
	      }
	     
		  
		/*  $(function() {
			  if (window.Notification) {
			 
			      return Notification.requestPermission();
			    
			    
			      if (Notification.permission) {
			        return $("#result").text(Notification.permission);
			      } else {
			        return $("#result").text((new Notification("check")).permission);
			      }
			    
			    
			      
			    
			  }
			});		
		  
		};*/
		
		
		
		
		/*
bootstrap_alert = function () {}
bootstrap_alert.warning = function (message, alert, timeout) {
    $('<div id="floating_alert" class="alert alert-' + alert + ' fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>' + message + '&nbsp;&nbsp;</div>').appendTo('body');
    
    setInterval(function() { window.focus() }, timeout);
    
	var audioElement = document.createElement('audio');
          audioElement.setAttribute('src', '../mp3/v.mp3');
          audioElement.setAttribute('autoplay:false', 'autoplay');
          audioElement.play();
	
    setTimeout(function () {
        $(".alert").alert('close');
    }, timeout);
}*/