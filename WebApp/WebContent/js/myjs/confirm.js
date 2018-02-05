//tasks changeUser confirm
	$(function(){
	var $myModal = jQuery('#distrib');
	
	$('.form-control').on('change', function(e) {
		e.preventDefault();
		var taskId = $('#task_id').text();
		var url = $('#users_distr option:selected').val()+'&taskId='+taskId;
		var id = url.replace(/[^0-9]/g, '');
		var user = $('#users_distr option:selected').html();		

		// Objects from alert modal
		var $dsBody = $myModal.find('div.modal-body');
		var $dsTitle = $myModal.find('div.modal-header h3');
		var $btConfirm = jQuery('#bt-modal-confirm1');
		var $btCancel = jQuery('#bt-modal-cancel1');


		
		$dsBody.html('<p>Назначить исполнителем '+ user + '</p>');
		$dsTitle.html('Delete Record');

		$myModal.modal({
			show: true
		});

		$btConfirm.attr('href', url).removeAttr('data-dismiss');
		$btCancel.click(function(){
			$dsTitle.html('Warning');
			$dsBody.html('<p>Notice</p>');
			$btConfirm.attr('href', '#').attr('data-dismiss', 'modal');
		});
		
		})
	});

//tasks delete confirm
$(function(){
	var $myModal = jQuery('#my-modal');

	// Modal to delete record
	var $btDelete = jQuery('#delete_task');
	if ($btDelete.length) {
		$btDelete.click(function(e){
			e.preventDefault();

			var url = jQuery(this).attr('href');
			var id = url.replace(/[^0-9]/g, '');

			// Objects from alert modal
			var $dsBody = $myModal.find('div.modal-body');
			var $dsTitle = $myModal.find('div.modal-header h3');
			var $btConfirm = jQuery('#bt-modal-confirm');
			var $btCancel = jQuery('#bt-modal-cancel');

			$dsBody.html('<p>Вы уверены что хотите удалить заявку: ' + $('#task_body').text() + ' id: '+ $('#task_id').text()+'</p>');
			$dsTitle.html('Delete Record');

			$myModal.modal({
				show: true
			});

			$btConfirm.attr('href', url).removeAttr('data-dismiss');
			$btCancel.click(function(){
				$dsTitle.html('Warning');
				$dsBody.html('<p>Notice</p>');
				$btConfirm.attr('href', '#').attr('data-dismiss', 'modal');
			});
		});
	}
});
//users delete confirm
$(function(){
	var $myModal = jQuery('#my-modal');

	// Modal to delete record
	var $btDelete = jQuery('#delete_user');
	if ($btDelete.length) {
		$btDelete.click(function(e){
			e.preventDefault();

			var url = jQuery(this).attr('href');
			var id = url.replace(/[^0-9]/g, '');

			// Objects from alert modal
			var $dsBody = $myModal.find('div.modal-body');
			var $dsTitle = $myModal.find('div.modal-header h3');
			var $btConfirm = jQuery('#bt-modal-confirm');
			var $btCancel = jQuery('#bt-modal-cancel');

			$dsBody.html('<p>Вы уверены что хотите удалить пользователя: ' + $('#login').text() +'?</p>');
			$dsTitle.html('Delete Record');

			$myModal.modal({
				show: true
			});


			$btConfirm.attr('href', url).removeAttr('data-dismiss');
			$btCancel.click(function(){
				$dsTitle.html('Warning');
				$dsBody.html('<p>Notice</p>');
				$btConfirm.attr('href', '#').attr('data-dismiss', 'modal');
			});
		});
	}

});