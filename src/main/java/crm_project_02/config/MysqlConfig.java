package crm_project_02.config;

import java.sql.Connection;
import java.sql.DriverManager;

//Cấu hình JDBC kết nối tới server MySQL
public class MysqlConfig {
	
	public static Connection getConnect() {
		
		try {
			//Khai báo driver sử dụng cho JDBc tương ứng với CSDL cần kết nối
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Khai báo driver sẽ mở kết nối tới CSDL nào.
			return DriverManager.getConnection("jdbc:mysql://localhost:3307/layoutcrm","root","admin123");
		} catch (Exception e) {
			//Lỗi xảy ra chạy vào đây
			System.out.println("Lỗi kết nối database "+ e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}
	}
}
