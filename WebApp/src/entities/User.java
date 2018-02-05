package entities;

public class User {
    private int id;
    private String login;
    private String password;
    private String FIO;
    private String role;
    private String telephone;
    private String email;
    private UserRole userRole;
    
	public User() {
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", FIO=" + FIO + ", role=" + role
				+ ", telephone=" + telephone + ", email=" + email + ", userRole=" + userRole + "]";
	}
    
	public static class Builder{
		private int id;
	    private String login;
	    private String password;
	    private String FIO;
	    private String role;
	    private String telephone;
	    private String email;
	    private UserRole userRole;
	    
	    public Builder id(int val){
	    	id = val;
	    	return this;
	    }
	    
	    public Builder login(String val){
	    	login = val;
	    	return this;
	    }
	    
	    public Builder password(String val){
	    	password = val;
	    	return this;
	    }
	    
	    public Builder fio(String val){
	    	FIO = val;
	    	return this;
	    }
	    public Builder role(String val){
	    	role = val;
	    	return this;
	    }
	    public Builder phone(String val){
	    	telephone = val;
	    	return this;
	    }
	    public Builder email(String val){
	    	email = val;
	    	return this;
	    }
	    public Builder userRole(UserRole val){
	    	userRole = val;
	    	return this;
	    }
	    
	    public User build(){
	    	return new User(this);
	    }
	}
	
	private User(Builder builder){
		this.id = builder.id;
        this.login = builder.login;
        this.password = builder.password;
        this.FIO = builder.FIO;
        this.role = builder.role;
        this.telephone = builder.telephone;
        this.email = builder.email;
        this.userRole = builder.userRole;
	}
    
}
