//всплаывющее окно
var myTableArray = [];
$(document).ready(function () {
    $('#myModal').bind('show', function () {

    });
    
	$('#change_user').click(function() {
	    $('#select_user').toggle('slow');
	});

    
    $('.btn-modal').click(function() {
    	var contactId = $(this).val();
    
    	$('#myModal').modal('show'); 
    	var contact;
    	
    	for(var i = 0; i < contacts.length; i++){
    		if(contacts[i].id==contactId){
    			contact = contacts[i];
    			$('#contact_id').text(contact.id);
    			$('#address').text(contact.address);
    			$('#org_name').text(contact.org_name);
    			$('#post').text(contact.post);
    			$('#fio').text(contact.fio);
    			$('#phone').text(contact.phone);
    			$('#email').text(contact.email);
    			$('#apartments').text(contact.apartments);
    		}
    	}
    	
    	var edit_contact = document.getElementById('edit_contact');
    	var remove_contact = document.getElementById('delete_contact');
    	var edit_address = document.getElementById('edit_address');
    	var remove_address = document.getElementById('remove_address');
    	
    	edit_contact.setAttribute('href', "/WebApp/tasks/editContact?id="+contact.id);
    	remove_contact.setAttribute('href', "/WebApp/tasks/removeContact?id="+contact.id);
    	edit_address.setAttribute('href', "/WebApp/tasks/editAddress?id="+contact.addressId);
    	remove_address.setAttribute('href', "/WebApp/tasks/removeAddress?id="+contact.addressId);
    });
});

