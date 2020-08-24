package TestCases;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;

public class Typicode {
    
	 
	static ExtentReports report;

   @Test
    public void getTest1()
    {
	   ExtentTest test=  report.startTest("Get_Test1");
    	 RequestSpecification httpRequest = RestAssured.given();
         Response response = httpRequest.get("https://jsonplaceholder.typicode.com/posts");
         System.out.println(response.statusCode());
         Assert.assertEquals(response.statusCode(), 200);
         test.log(LogStatus.PASS, "Status Code Verified "+response.statusCode(),"Status code");
         //System.out.println(response.asString());
         
         ValidatableResponse response1 =given().when().get("https://jsonplaceholder.typicode.com/posts")
        		 .then().statusCode(200);
         System.out.println(response1.extract().jsonPath().getList("$").size());
         test.log(LogStatus.PASS, "API returns at least 100 records "+response1.extract().jsonPath().getList("$").size()," records validate");
         
      
         
         RestAssured.given()
			.when()
			.get("https://jsonplaceholder.typicode.com/posts")
			.then()
			.assertThat()
			.body(matchesJsonSchemaInClasspath("Sample.json"));
         test.log(LogStatus.PASS, "Schema validated sucessfully");
         
         report.endTest(test);
    }
    
    
  @Test
    public void getTest2()
    {
    	 ExtentTest test=  report.startTest("Get_Test2");
    	 RequestSpecification httpRequest = RestAssured.given();
         Response response = httpRequest.get("https://jsonplaceholder.typicode.com/posts/1");
         System.out.println(response.statusCode());
         Assert.assertEquals(response.statusCode(), 200);
         test.log(LogStatus.PASS, "Status Code Verified "+response.statusCode(),"Status code");
         //System.out.println(response.asString());
         
         
         System.out.println(response.getBody().jsonPath().getJsonObject("id"));
         
			
	  ValidatableResponse response1
	  =given().when().get("https://jsonplaceholder.typicode.com/posts/1")
	  .then().statusCode(200);
	  test.log(LogStatus.PASS, "Validate extracted id is 1 "+response.getBody().jsonPath().getJsonObject("id"),"id validation");
	  
	 
	 int noOfrecords=response1.extract().jsonPath().getMap("$").size()/4;
	 Assert.assertEquals(noOfrecords, 1);
	  System.out.println( noOfrecords);
	  test.log(LogStatus.PASS, "API returns only 1 records "+noOfrecords," records validate");
      
         
         RestAssured.given()
			.when()
			.get("https://jsonplaceholder.typicode.com/posts/1")
			.then()
			.assertThat()
			.body(matchesJsonSchemaInClasspath("Sample2.json"));
         
         test.log(LogStatus.PASS, "Schema validated sucessfully"); 
         report.endTest(test);
    }
    
    
    @Test
    public void getTest3()
    {
    	ExtentTest test=  report.startTest("Get_Test3");
    	 RequestSpecification httpRequest = RestAssured.given();
         Response response = httpRequest.get("https://jsonplaceholder.typicode.com/posts/invalidposts");
         System.out.println(response.statusCode());
         Assert.assertEquals(response.statusCode(), 404);
         test.log(LogStatus.PASS, "Status Code Verified "+response.statusCode(),"Status code");
         System.out.println(response.asString());
         
         report.endTest(test);
    }
    
  
    
   @Test
    public void postTest1()
    {
    	ExtentTest test=  report.startTest("Get_Test3");
    	HashMap<String, String> map=new HashMap<String, String>();
    	map.put("title", "foo");
		map.put("body", "bar");
		map.put("userId", "1");
			
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		RestAssured.basePath = "/posts/";
    	
    	given()
		.contentType("application/json")
		.body(map)
		.when()
		.post()
		.then()
		.statusCode(201)
		.and()
		
		;
    	
         
    	 report.endTest(test);
    }
    
    
    @Test
    public void putTest1() throws Exception
    {
    	Thread.sleep(5000);
    	HashMap<String, String> map=new HashMap<String, String>();
    	map.put("title", "abc");
		map.put("body", "xyz");
		map.put("userId", "1");
		map.put("id", "1");
			
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com/posts/1";
		//RestAssured.basePath = "";
    	
    	given()
		.contentType("application/json")
		.body(map)
		.when()
		.put()
		.then()
		.statusCode(200)
		.and()
		
		;
         
        
    }
    
    
    @Test
    public void deleteTest1()
    {
    	HashMap<String, String> map=new HashMap<String, String>();
    	map.put("title", "abc");
		map.put("body", "xyz");
		map.put("userId", "1");
		map.put("id", "1");
			
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com/posts/1";
		//RestAssured.basePath = "";
    	
    	given()
		.contentType("application/json")
		.body(map)
		.when()
		.delete()
		.then()
		.statusCode(200)
		.and()
		
		;
         
        
    }
    
    
    @BeforeClass
    public static void startTest()
    {
    report = new ExtentReports(System.getProperty("user.dir")+"\\ExtentReportResults.html");
    
    }
    
    @AfterClass
    public static void endTest()
    {
   
    report.flush();
    }
    
    
    
   
}
