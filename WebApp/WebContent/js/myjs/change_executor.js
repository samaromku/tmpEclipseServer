var executor = $('#executor').val();

$('#executor').change(function(){
	changeStatus();
});

$('#status').change(function(){
	changeExecutor();
});

function changeStatus(){
	var status = $('#status');
	var executor = $('#executor').val();
	console.log(executor);
	if(executor!='Не назначена'){
		status.val('распределено');
	}else{
		status.val('новое задание');
	}
}

function changeExecutor(){
	var status = $('#status').val();
	var executor = $('#executor');
	if(status=='новое задание'){
		executor.val('Не назначена');
	}
}