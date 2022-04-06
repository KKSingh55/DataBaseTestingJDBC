package com.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DateBase {
	
	static Connection connection = null;
	static Statement statement = null ;

	public static void main(String[] args){	
	
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "students";
		String dburl = url+dbName;//jdbc:mysql://localhost:3306/students
		String username = "root";
		String password ="root";
		String Query = "select * from department1";
		
		//create an object for mysql JDBC driver class
		 String driver= "com.mysql.cj.jdbc.Driver";
		 try {
			Class.forName(driver).newInstance();
		} catch (InstantiationException e2) {
			
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			
			e2.printStackTrace();
		} catch (ClassNotFoundException e2) {
			
			e2.printStackTrace();
		}
		 
		 //establishing connection to mySql database System
		 
		 try {
			connection = DriverManager.getConnection(dburl, username, password);
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		 
		 //verify the connection established or not
		 
		 try {
			if(!connection.isClosed()) {
				 
				 System.out.println("Successfully connected to students database");
				 
			 	
				 
				 
				 statement = connection.createStatement();
				 
				    String sql  = "DELETE FROM department1 " +
				            "WHERE id = 1";
				    
				    statement.executeUpdate(sql);
				    ResultSet resultset = statement.executeQuery(Query);
				    
				    while(resultset.next()) {
				    	
				    	System.out.print("DepartmentName: " + resultset.getString("DepName"));
			            System.out.print(", ManagerNo: " + resultset.getString("Mgrno"));
			            System.out.print(", Aminsdept: " + resultset.getString("Admrdept"));
			            System.out.println(", Locat: " + resultset.getString("Location"));
				    			          		 
			}	 // close connection
					try { 
				 connection.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				try {
				 if(connection.isClosed()) {
					 
					 System.out.println("Successfully closed the connection to students database");
				 } }
				 catch(Exception e) {
						e.printStackTrace();
					}
				 
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
			}
			 
			 
		 
		 
	}
