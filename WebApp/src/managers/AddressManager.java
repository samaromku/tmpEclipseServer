package managers;
import entities.Address;
import entities.Task;
import entities.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AddressManager {
    private Address address;
    private List<Address>addresses;
    private List<Address>usersAddresses;
    private TasksManager tasksManager = TasksManager.INSTANCE;

    public static final AddressManager INSTANCE = new AddressManager();
    private AddressManager(){
    	usersAddresses = new CopyOnWriteArrayList<>();
        addresses = new CopyOnWriteArrayList<>();
    }
    
    public List<Address> getUsersAddresses(User user) {
    	usersAddresses.clear();
    	for(Task t:tasksManager.getUserTasks(user)){
    		int addressId = t.getAddressId();
    		usersAddresses.add(getAddressById(addressId));
    	}
		return usersAddresses;
	}
    
    
    
    public void addToUserAddresses(Address address){
    	usersAddresses.add(address);
    }

	public void addAll(List<Address> addressList){
        addresses.addAll(addressList);
    }
    
    public void addAddress(Address address){
    	addresses.add(address);
    }

    public void removeAll(){
        if(addresses.size()>0){
            addresses.clear();
        }
    }
    
    public void removeAddress(Address address){
    	addresses.remove(address);
    }
    
    public void updateAddress(Address address){
    	for(Address a:addresses){
    		if(a.getId()==address.getId()){
    			a.setAddress(address.getAddress());
    			a.setName(address.getName());
    		}
    	}
    }

    public Address getAddressByName(String name){
        for(Address a:addresses){
            if(a.getName().equals(name)){
                return a;
            }
        }
        return null;
    }

    public Address getAddressByAddress(String address){
        for(Address a:addresses){
            if(a.getAddress().equals(address)){
                return a;
            }
        }
        return null;
    }
    
    public Address getAddressById(int id){
        for(Address a:addresses){
            if(a.getId()==id){
                return a;
            }
        }
        return null;
    }

    public Address getAddress() {
        return address;
    }

    public List<Address> getAddresses() {
        return addresses;
    }
}
