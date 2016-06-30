package up.jservlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet(urlPatterns = { "/Login" }, initParams = { @WebInitParam(name = "driverclassname", value = "org.h2.Driver"),
		@WebInitParam(name = "dburl", value = "jdbc:h2:C:/temp/DB-H2"), @WebInitParam(name = "username", value = "sa"),
		@WebInitParam(name = "password", value = "pwd") })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection dbConnection;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		System.out.println(config.getServletName() + " : Initializing...");

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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String userid = request.getParameter("userid");
			String password = request.getParameter("password");
			boolean login = false;

			if (userid != null && password != null) {
				Statement stmt = dbConnection.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM LOGIN WHERE ID='" + userid + "' AND PWD='" + password + "'");
				if (rs.next())
					login = true;
			}

			if (login) {
				request.setAttribute("userid", userid);

				/*
				 * ServletContext ct = getServletContext(); RequestDispatcher rd
				 * =
				 * ct.getRequestDispatcher("/JavaServlet-5/AdicionaContato.xml")
				 * ;
				 */

				RequestDispatcher rd = request.getRequestDispatcher("AdicionaContato.html");

				rd.forward(request, response);

				return;

			} else {
				RequestDispatcher rd = request.getRequestDispatcher("Login.html");

				rd.forward(request, response);

				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
