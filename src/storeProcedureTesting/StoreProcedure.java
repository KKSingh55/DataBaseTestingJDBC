package storeProcedureTesting;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StoreProcedure {

    static Connection connection = null;
    static Statement stmt = null;
    static ResultSet rset1=null;
    static ResultSet rset2=null;

    @BeforeClass

    public void setup() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {


	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "classicmodels";
	String dburl = url+dbName;//jdbc:mysql://localhost:3306/students
	String username = "root";
	String password ="root";

	//create an object for mysql JDBC driver class
	
	
	//establishing connection to mySql database System

	connection = DriverManager.getConnection(dburl, username, password);

	//verify the connection established or not


	if(!connection.isClosed()) {

	    System.out.println("Successfully connected to students database");

	}

    }    
    @AfterClass

    public void tearDown() throws SQLException {

	connection.close();
    }



    @Test(enabled=false)
    public void test_storeProcedureExists() throws SQLException {

	stmt = connection.createStatement();

	String sql   = "show procedure status" +
		" where Name ='AllCustomers'";


	ResultSet resultset = stmt.executeQuery(sql);

	resultset.next();

	Assert.assertEquals(resultset.getString("Name"),"AllCustomers");	  	    	
    }

    @Test(priority =1)
    public void test_setAllCustomers() throws SQLException {

	CallableStatement cstmt =   connection.prepareCall("{CALL AllCustomers()}");
	rset1 =cstmt.executeQuery();

	stmt = connection.createStatement();
	rset2 = stmt.executeQuery("select * from customers");

	Assert.assertEquals(compareResultSets(rset1,rset2), true);



    }

    public boolean compareResultSets(ResultSet resultSet1, ResultSet resultSet2) throws SQLException{
	while (resultSet1.next()) {
	    resultSet2.next();

	    int count = resultSet1.getMetaData().getColumnCount();
	    for (int i = 1; i <= count; i++) {
		if (!StringUtils.equals(resultSet1.getString(i),resultSet2.getString(i))) {
		    return false;
		}
	    }
	}
	return true;
    }



}


