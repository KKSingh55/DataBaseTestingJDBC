package com.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumJDBC {



    static Connection connection = null;
    static Statement statement = null ;
    static WebDriver drivers = null;

    public static void main(String[] args) throws InterruptedException {

	System.setProperty("webdriver.chrome.driver",
		System.getProperty("user.dir")+"//Drivers/chromedriver.exe");

	drivers = new ChromeDriver();
	drivers.get("https://demo.guru99.com/V4/");
	

	String url = "jdbc:mysql://Localhost:3306/";
	String dbName = "credential";
	String dburl = url+dbName;//jdbc:mysql://localhost:3306/students
	String username = "root";
	String password ="root";
	String Query = "select * from Credentials2";

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

		System.out.println("Successfully connected to credentials database");




		statement = connection.createStatement();

		//		    String sql = "CREATE TABLE Credentials2 " +
		//		                   "(username VARCHAR(40) , " +
		//		                   " Password VARCHAR(40)) "; 
		//		               
		//		    
		//		    statement.executeUpdate(sql);
		//		    

		System.out.println("Inserting records into the table...");          
		//		         String sql1 = "INSERT INTO Credentials2 VALUES ('mngr393906','UvydEqY')";
		//		         statement.executeUpdate(sql1);
		//		         sql1 = "INSERT INTO Credentials2 VALUES ('mngr393916','UvydEqY')";
		//		         statement.executeUpdate(sql1);
		//		         sql1 = "INSERT INTO Credentials2 VALUES ('mngr393926','UvydEqY')";
		//		         statement.executeUpdate(sql1);
		//		         sql1 = "INSERT INTO Credentials2 VALUES('mngr393936','UvydEqY')";
		//		         statement.executeUpdate(sql1);
		
		String sql1 = "INSERT INTO Credentials2 VALUES ('mngr393906','kkk')";
		statement.executeUpdate(sql1);    

		sql1 = "INSERT INTO Credentials2 VALUES ('mngr393906','zxcbxzbvcz')";
		statement.executeUpdate(sql1);


		
		
		
		ResultSet resultset = statement.executeQuery(Query);


		while(resultset.next()) {


		    String user = resultset.getString("username");
		    String pass = resultset.getString("Password");

		    drivers.findElement(By.name("uid")).sendKeys(user);
		    drivers.findElement(By.name("password")).sendKeys(pass);
		    drivers.findElement(By.name("btnLogin")).click();
		   

		    if(drivers.getTitle().equals("Guru99 Bank Manager HomePage")) {

			System.out.println("testcase got passed");
		    }
		    else 
		    {
			System.out.println("testcase got failed");
		    }

		    drivers.findElement(By.linkText("Log out")).click();
		   
		    drivers.switchTo().alert().accept();

 

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
        drivers.close();
    }

     


}