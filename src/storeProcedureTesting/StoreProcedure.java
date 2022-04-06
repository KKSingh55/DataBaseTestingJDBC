package storeProcedureTesting;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

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
    CallableStatement cstmt;

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

	String sql = "show procedure status" +
		" where Name ='AllCustomers'";


	ResultSet resultset = stmt.executeQuery(sql);

	resultset.next();

	Assert.assertEquals(resultset.getString("Name"),"AllCustomers");	  	    	
    }

    @Test(enabled = false)
    public void test_setAllCustomers() throws SQLException {

	cstmt =   connection.prepareCall("{CALL AllCustomers()}");
	rset1 =cstmt.executeQuery();

	stmt = connection.createStatement();
	rset2 = stmt.executeQuery("select * from customers");

	Assert.assertEquals(compareResultSets(rset1,rset2), true);



    }
    

    @Test(enabled = false)
    public void test_SelectAllCustomerByCity() throws SQLException {
	
	cstmt = connection.prepareCall("{CALL SelectAllCustomersCity(?)}");
	cstmt.setString(1,"Singapore");
	rset1 = cstmt.executeQuery();
	
	
	stmt =connection.createStatement();
	rset2 =stmt.executeQuery("select * from customers where City ='Singapore'");
	
	Assert.assertEquals(compareResultSets(rset1,rset2), true);
	
	
	
    }

    @Test(enabled = false)
    public void test_SelectAllCustomerByCityAndPincode() throws SQLException {
	
	cstmt = connection.prepareCall("{CALL SelectAllCustomersCityAndPin(?,?)}");
	cstmt.setString(1,"Singapore");
	cstmt.setString(2,"079903");
	rset1 = cstmt.executeQuery();
	
	
	stmt =connection.createStatement();
	rset2 =stmt.executeQuery("select * from customers where City ='Singapore' and postalCode = '079903' ");
	
	Assert.assertEquals(compareResultSets(rset1,rset2), true);
	
	
	
    }
    @Test(priority =5)
    
    public void get_order_by_cust() throws SQLException {
	
	cstmt = connection.prepareCall("{CALL get_order_by_cust(?,?,?,?,?)}");
	cstmt.setInt(1,141);
	cstmt.registerOutParameter(2,Types.INTEGER);
	cstmt.registerOutParameter(3,Types.INTEGER);
	cstmt.registerOutParameter(4,Types.INTEGER);
	cstmt.registerOutParameter(5,Types.INTEGER);
        cstmt.executeQuery();
        
        int Shipped = cstmt.getInt(2);
        int Canceled = cstmt.getInt(3);
        int resolved = cstmt.getInt(4);
        int disputed = cstmt.getInt(5);
        
	System.out.println(Shipped + " " + Canceled + " " + resolved + " " + disputed);
	stmt =connection.createStatement();
	ResultSet rset =stmt.executeQuery("select(SELECT COUNT(*) as 'shipped' from orders WHERE customerNumber = 141 AND status ='Shipped'");
	rset.next();
	
	int exp_shipped = rset.getInt("shipped");
	int exp_canceled = rset.getInt("canceled");
	int exp_resolved = rset.getInt("resolved");
	int exp_disputed = rset.getInt("disputed");
	
	
	if(Shipped == exp_shipped && exp_canceled == Canceled && resolved == exp_resolved
		&& disputed==exp_disputed) 
	    
	    Assert.assertTrue(true);
	
	else
	
	    Assert.assertTrue(false);

	
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


