package crm_project_02.entity;

//Entity: là nơi khai báo ra các class đặt tên và thược tính giống với lại tên bảng trong CSDL.

public class Users {
	private int id;
	private String email;
	private String password;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
