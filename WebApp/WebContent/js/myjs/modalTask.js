//всплаывющее окно
var myTableArray = [];
$(document).ready(function () {
    $('#myModal').bind('show', function () {

    });
    
	$('#change_user').click(function() {
	    $('#select_user').toggle('slow');
	});

	if(taskIdFromMap!=0){
		openModalTask(taskIdFromMap);
	}
    
    $('.btn-modal').click(function() {
    	var taskId = $(this).val();
    	openModalTask(taskId);
    });
});

function openModalTask(taskId){

	var comments = Array();
	var client = new HttpClient();
	
	
	client.get('/WebApp/tasks/task?id='+taskId, function(response) {
			var parser      = new DOMParser ();
	    	var responseDoc = parser.parseFromString (response, "text/html");
    		var table_com = responseDoc.getElementById("com_table");
    		var contacts = responseDoc.getElementById("contacts");
    		
    		
    		//получаем контакты в запросе
    		$(contacts).each(function(){
    			var contactsArray = [];        			
    			var oneContact = $(this).find('.one_contact');
    			if(oneContact.length>0){
    				oneContact.each(function(){
    					var name = $(this).find('#contact_name').text();
    					var post = $(this).find('#contact_post').text();
    					var apartments = $(this).find('#contact_apartments').text();
						var phone = $(this).find('#contact_phones').text().trim().split(",");
						var email = $(this).find('#contact_emails').text().trim().split(",");
						var newPhone = phone.filter(function(v){return v!==''});
						var newEmail = email.filter(function(v){return v!==''});
        					

    					contactsArray.push({
    						name:name,
    						post:post,
    						apartments:apartments,
    						phones:[newPhone],
    						emails:[newEmail]
    					});
    				});
    			}

    			//отображаем контакты
    			for(var i = 0; i < contactsArray.length; i++){
    				var oneContact = $('<div class="one_contact" />');
    				$('.contacts_modal').append(oneContact);
    				oneContact.append('<p><b>Имя: </b>'+contactsArray[i].name+'</p>');
    				oneContact.append('<p><b>Должность: </b>'+contactsArray[i].post+'</p>');
    				oneContact.append('<p><b>Квартира: </b>'+contactsArray[i].apartments+'</p>');
    				var phonesVis = $('<p class="phones" />')
    				oneContact.append(phonesVis);
    				phonesVis.append('<b>Телефоны: </b>');
    				for(var j = 0; j < contactsArray[i].phones.length; j++){
    					phonesVis.append('<span>'+contactsArray[i].phones[j]+'</span>');
    				}
    				var emailVis = $('<p class="emails" />')
    				oneContact.append(emailVis);
    				emailVis.append('<b>Почта: </b>');
    				for(var j = 0; j < contactsArray[i].emails.length; j++){
    					emailVis.append('<a href="mailto:'+contactsArray[i].emails[j]+'">'+ contactsArray[i].emails[j] +'</a>');
    				}
    			}
    		});
    		
    		
    		//получаем комментарии из запроса
    		$(table_com).each(function() { 
    		    var arrayOfThisRow = [];
    		    var tableData = $(this).find('td');
    		    if (tableData.length > 0) {
    		        tableData.each(function() { 
    		        	arrayOfThisRow.push($(this).text()); 
    		        	});
    		   for(var i = 0; i < arrayOfThisRow.length; i=i+4){
    			   myTableArray.push({
    				   commentId : arrayOfThisRow[i],
    				   commentBody : arrayOfThisRow[i+1],
    				   commentUser : arrayOfThisRow[i+2],
    				   commentDate : arrayOfThisRow[i+3]
    			   });
    		   }
				}
    		});
			//addComentToTask 
    		for(var i = 0; i < myTableArray.length; i++){
    			var row = $("<tr />");
    			$("#com_table").append(row); 
    			row.append($("<td>" + myTableArray[i].commentBody + "</td>"));
    			
    			for(var usrIndex = 0; usrIndex < users.length; usrIndex++){
    				if(users[usrIndex].userId==myTableArray[i].commentUser){
    					row.append($("<td>" + users[usrIndex].userLogin + "</td>"));
    				}
    			}
    			
    			row.append($("<td>" + myTableArray[i].commentDate + "</td>"));
    		}
	});
	
    $('#myModal').modal('show');
    for(var i = 0; i<tasks.length; i++){
    	if(tasks[i].taskId==taskId){
    		$('#task_id').text(tasks[i].taskId);
    		$('#created').text(tasks[i].created);
    		$('#importance').text(tasks[i].importance);
    		$('#task_body').text(tasks[i].body);
    		$('#status').text(tasks[i].status);
    		$('#type').text(tasks[i].type);
    		$('#done_time').text(tasks[i].doneTime);
    		$('#address').text(tasks[i].address);
    		$('#org_name').text(tasks[i].orgName);
    		$('#user').text(tasks[i].user);
    		//надо присвоить ссылке редактировать заявку ссылку на нужную страницу + id заявки, которую редактируем
    		//для этого, нужно получить id заявки - есть
    		//получить доступ к редактиремой ссылке либо по id либо по классу, либо создать новую ссылку в скрипте
    		var one_task = document.getElementById('edit_task');
    		var delete_task = document.getElementById('delete_task');
    		var make_done_task = document.getElementById('make_done_task');
    		
    		//var edit_url = document.createElement('a');
    		one_task.setAttribute('href', "/WebApp/tasks/editTask?id="+tasks[i].taskId);
    		//one_task.textContent = 'Редактировать заявку';
    		
    		delete_task.setAttribute('href', "/WebApp/tasks/deleteTask?id="+tasks[i].taskId);
    		
    		make_done_task.setAttribute('href', "/WebApp/tasks/changeTaskStatus?taskId="+tasks[i].taskId+"&status=выполнено");
    		//delete_task.textContent = 'Удалить заявку';
    		//one_task.appendChild(edit_url);
    		
    		
    	}
    }
}


var comments = Array();
var HttpClient = function() {
    this.get = function(aUrl, aCallback) {
        var anHttpRequest = new XMLHttpRequest();
        anHttpRequest.onreadystatechange = function() { 
            if (anHttpRequest.readyState == 4 && anHttpRequest.status == 200)
                aCallback(anHttpRequest.responseText);
        }

        anHttpRequest.open( "GET", aUrl, true );            
        anHttpRequest.send( null );
    }
}

$('#myModal').on('hidden.bs.modal', function () {
    myTableArray = [];
    $("#com_table tr td").each(function(){
    	$(this).remove();
    	$('.one_contact').remove();
    });
})