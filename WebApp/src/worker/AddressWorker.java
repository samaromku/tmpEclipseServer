package worker;

import java.util.ArrayList;
import java.util.List;

import database.DBWorker;
import entities.Address;
import entities.ContactOnAddress;
import managers.AddressManager;
import managers.ContactsManager;

public class AddressWorker {
	AddressManager addressManager = AddressManager.INSTANCE;
	ContactsManager contactsManager = ContactsManager.Instance;
	DBWorker dbWorker;
	private List<Address>addresses;

	public List<Address> getAddresses() {
		return addresses;
	}

	public AddressWorker(){
		addresses = new ArrayList<>();
		dbWorker = new DBWorker();
	}
	
	public boolean getAllAddresses(){
		if(dbWorker.getAllAddresses()){
			addresses.clear();
			addresses.addAll(addressManager.getAddresses());
			return true;
	        }else {
	        	return false;
	        }
	}
	
	public boolean addContact(ContactOnAddress contact){
		if(dbWorker.addContactInDB(contact)){
			contactsManager.addContact(contact);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean UpdateContact(ContactOnAddress contact){
		if(dbWorker.updateContact(contact)){
			contactsManager.updateContact(contact);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean UpdateAddress(Address address){
		if(dbWorker.updateAddress(address)){
			addressManager.updateAddress(address);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean addAddress(Address address){
		if(dbWorker.addAddressInDB(address)){
			addressManager.addAddress(address);
			return true;
		}else {
			return false;
		}
	}
	
	
	public boolean removeAddress(Address address){
		if(dbWorker.removeAddress(address)){
			addressManager.removeAddress(address);
			contactsManager.removeContactsByAddressId(address);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean removeContact(ContactOnAddress contact){
		if(dbWorker.removeContact(contact)){
			contactsManager.removeContact(contact);
			return true;
		}else {
			return false;
		}
	}
	
}
