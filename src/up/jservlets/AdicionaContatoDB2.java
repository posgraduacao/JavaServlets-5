package up.jservlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdicionaContatoDB2
 */
@WebServlet(urlPatterns = { "/AdicionaContatoDB2" }, initParams = {
		@WebInitParam(name = "driverclassname", value = "org.h2.Driver"),
		@WebInitParam(name = "dburl", value = "jdbc:h2:C:/temp/DB-H2"), @WebInitParam(name = "username", value = "sa"),
		@WebInitParam(name = "password", value = "pwd") })
public class AdicionaContatoDB2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection dbConnection;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdicionaContatoDB2() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println(getServletName() + " : Initializing...");

		String driverClassName = config.getInitParameter("driverclassname");

		String dbURL = config.getInitParameter("dburl");

		String username = config.getInitParameter("username");

		String password = config.getInitParameter("password");

		// Load the driver class
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// get a database connection
		try {
			dbConnection = DriverManager.getConnection(dbURL, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Initialized " + dbConnection.toString());
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try {
			dbConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
