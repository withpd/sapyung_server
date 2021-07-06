package kr.co.sapyoung.utils;

import java.sql.SQLException;

import java.sql.*;

public class Main {

	public static String dbType = "oracle";
	
	public static void insert() {
		String name = "";
		String password = "";
		String memo = "";

		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getMySQLConnection(dbType);

			String sql = "INSERT INTO memo(NAME, PASSWORD, memo) VALUES (?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			pstmt.setString(3, memo);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public static void select() throws SQLException {
		Connection conn = null;
//		PreparedStatement pstmt = null;

		conn = DBUtil.getMySQLConnection(dbType);

		String query = "SELECT * FROM simba_maje";

		// create the java statement
		Statement st = conn.createStatement();

		// execute the query, and get a java resultset
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			int id = rs.getInt("id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			Date dateCreated = rs.getDate("date_created");
			boolean isAdmin = rs.getBoolean("is_admin");
			int numPoints = rs.getInt("num_points");

			// print the results
			System.out.format("%s, %s, %s, %s, %s, %s\n", id, firstName, lastName, dateCreated, isAdmin, numPoints);
		}
		st.close();
	}
	public static void main(String[] args) {
		
		try {
			select();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
