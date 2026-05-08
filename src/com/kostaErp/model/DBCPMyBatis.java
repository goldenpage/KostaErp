package com.kostaErp.model;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBCPMyBatis {
	private static SqlSessionFactory factory;
	private DBCPMyBatis(){}
	
	public static SqlSessionFactory getSqlSessionFactory(){
		if(factory == null){
			String resource = "config/mybatis-Config.xml";
			InputStream in = null;
			try{
				in = Resources.getResourceAsStream(resource);
			}catch(IOException e){
				e.printStackTrace();
				throw new NullPointerException("환결성정 오류");
			}
			factory = new SqlSessionFactoryBuilder().build(in);
		}
		return factory;
	}

}
