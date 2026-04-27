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
		if(dbcp==null) dbcp = new DBCP();//ÇÑ¹ø¸¸ 
		String uri = "jdbc:oracle:thin:@192.168.0.234:1521:xe";
		return  DriverManager.getConnection(uri, "kosta", "0707");
		
	} 
}
>>>>>>> refs/remotes/origin/master
