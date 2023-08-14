package crm_project_02.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project_02.config.MysqlConfig;
import crm_project_02.entity.Role;

@WebServlet(name = "RoleController", urlPatterns = { "/role-add", "/role" })
public class RoleController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getServletPath().equals("/role-add"))
			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
		else {
			List<Role> list = new ArrayList<Role>();

			// Chuẩn bị câu query
			String query = "SELECT * FROM `Role` r";

			// Mở kết nối
			Connection connection = MysqlConfig.getConnect();

			try {
				// Truyền câu query để lấy dữ liệu
				PreparedStatement statement = connection.prepareStatement(query);

				// Lấy dữ liệu trả về
				ResultSet resultSet = statement.executeQuery();

				// Duyệt qua các kết quả trong biến resultSet và chuyển về List.
				while (resultSet.next()) {
					Role role = new Role();
					role.setId(resultSet.getInt("id"));
					role.setName(resultSet.getString("name"));
					role.setDescription(resultSet.getString("description"));
					list.add(role);
				}

			} catch (SQLException e) {
				System.out.println("Lỗi truy xuất dữ liệu " + e.getLocalizedMessage());
			} finally {
				try {
					// Đóng connection
					connection.close();
				} catch (SQLException e) {
					System.out.println("Lỗi đóng connection " + e.getLocalizedMessage());
				}
			}

			req.setAttribute("listRole", list);
			req.getRequestDispatcher("/role-table.jsp").forward(req, resp);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Lấy dữ liệu người dùng nhập vào
		String roleName = req.getParameter("roleName");
		String description = req.getParameter("description");
		boolean isSuccess = false;

		//Viết câu query
		String query = "INSERT INTO `Role`  (name,description) VALUES (?,?)";

		//Dùng DJBC kết nối tới CSDL và truyền câu query ở bước 2 cho CSDL thực thi
		Connection connection = MysqlConfig.getConnect();

		try {
			// Chuẩn bị câu query cho truyền xuống CSDL thông qua PreparedStatement
			PreparedStatement statement = connection.prepareStatement(query);

			// Gán giá trị cho tham số trong câu query có dấu (?)
			statement.setString(1, roleName);
			statement.setString(2, description);

			// Thực thi câu query
			int result = statement.executeUpdate();

			if (result > 0) {
				isSuccess = true;
				req.setAttribute("isSuccess", isSuccess);
			}
				
		

		} catch (SQLException e) {
			System.out.println("Lỗi kết nối CSDL " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		req.getRequestDispatcher("role-add.jsp").forward(req, resp);
	}
}
