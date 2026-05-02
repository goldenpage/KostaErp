package com.kostaErp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCP {

	private static DBCP dbcp;

	private DBCP() throws ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("driver loading");
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (dbcp == null)
			dbcp = new DBCP();

		// String uri = "jdbc:oracle:thin:@192.168.0.234:1521:xe";
		String uri = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		// String uri = "jdbc:oracle:thin:@localhost:1521:xe";
		// return DriverManager.getConnection(uri, "mywork", "1234");
		return DriverManager.getConnection(uri, "hr", "hr");

	}
}