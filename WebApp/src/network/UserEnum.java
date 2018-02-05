package network;

public class UserEnum {
	public static final String MANAGER_ROLE = "managerRole";
    public static final String USER_ROLE = "userRole";
    public static final String ADMIN_ROLE = "adminRole";
    public static final String GUEST_ROLE = "guestRole";
    private static String[] roles = new String[]{USER_ROLE, MANAGER_ROLE, ADMIN_ROLE};
    
    public static String[] getRoles() {
		return roles;
	}
}
