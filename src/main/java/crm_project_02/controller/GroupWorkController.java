package crm_project_02.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import crm_project_02.config.MysqlConfig;

@WebServlet(name = "GruopWorkController" , urlPatterns = {"/groupwork-add"})
public class GroupWorkController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy thông tin người nhập.
		String name = req.getParameter("name");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		boolean isSuccess = false;
		boolean errorDate = false;
		
		//Viết câu query.
		String query ="INSERT INTO Project (name,startDate,endDate)\r\n"
				+ "VALUES (?,STR_TO_DATE(?,\"%d/%m/%Y\"),STR_TO_DATE(?,\"%d/%m/%Y\"))";
		
		//Kết nối CSDL và truyền câu query
		Connection connection = MysqlConfig.getConnect();
		
		try {
			//Chuẩn bị câu query để truyền vào CSDL.
			PreparedStatement statement = connection.prepareStatement(query);
			
			//Gán gái trị vào câu query.
			statement.setString(1, name);
			statement.setString(2,startDate);
			statement.setString(3,endDate);
			
			//Thực thi câu query.
			int result = statement.executeUpdate();
			
			if (result > 0) {
				isSuccess = true;
				req.setAttribute("isSuccess", isSuccess);
			}
			
		} catch (SQLException e) {
			System.out.println("Lỗi thực thi câu query "+ e.getLocalizedMessage());
			errorDate = true;
			req.setAttribute("errorDate", errorDate);
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi connection close "+ e.getLocalizedMessage());
			}
		}
		
		req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
	}
}
