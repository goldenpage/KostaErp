package com.kostaErp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCP {
	private static DBCP dbcp;
	private DBCP() throws ClassNotFoundException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("driver loading");
	}
	//생성하자마자 던져줌 -> factory패턴
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		if(dbcp==null) dbcp=new DBCP(); //한번만 호출
		String uri = "jdbc:oracle:thin:@192.168.0.234:1521:xe";
		return DriverManager.getConnection(uri, "kostaErp", "0707");
	}
}
