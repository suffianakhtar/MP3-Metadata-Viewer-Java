package com.sheifu_mp3_meta_viewer.Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SongServlet
 */
@WebServlet("/SongServlet")
public class SongServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SongServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		StringBuilder builder = new StringBuilder();
		try (Connection conn = DriverManager.getConnection("jdbc:h2:~/mydatabase", "sa", "")) {

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from SONGS");

			while (rs.next()) {
				builder.append("<tr class=\"table\">").append("<td>").append(rs.getString("year")).append("</td>")
						.append("<td>").append(rs.getString("artist")).append("</td>").append("<td>")
						.append(rs.getString("album")).append("</td>").append("<td>").append(rs.getString("title"))
						.append("</td>").append("</tr>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String string = "<html><h1>Your Songs</h1><table><tr><th>Year</th><th>Artist</th><th>Album</th><th>Title</th></tr>"
				+ builder.toString() + "</table></html>";
		resp.getWriter().write(string);
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
