//выборка по статусу
	$("#status_id").on("change",
        function(){
            var a = $(this).find("option:selected").html();
            $(".task_table tr").each(
            		function(){
            			if($(this).data('value')==a){
            				$(this).show();
            			}else{
            				$(this).hide();
            			}
            		});
            $(".task_table thead tr").show();
            if(a=='Все статусы'){
            	$(".task_table tr").each(
            			function(){
            				$(this).show();
            			});
            }
        });
	
	
	//addresses
	
$("#address_id").on("change",
        function(){
            var a = $(this).find("option:selected").html();
            $(".task_table tr").each(
            		function(){
            			console.log($(this).data('type'));
            			if($(this).data('type')==a){
            				$(this).show();
            			}else{
            				$(this).hide();
            			}
            		});
            $(".task_table thead tr").show();
            if(a=='Все адреса'){
            	$(".task_table tr").each(
            			function(){
            				$(this).show();
            			});
            }
        });