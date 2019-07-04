import java.sql.Timestamp;
import java.util.HashMap;

import com.google.gson.JsonObject;
import com.sun.xml.xsom.impl.scd.Iterators.Map;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
public class TestApi {

	public static void main(String[] args) {
		
		//requestGet("/api/v1/employees");
		
		// Specify the base URL to the RESTful web service
		 RestAssured.baseURI = "http://dummy.restapiexample.com/";
		 
		 System.out.println("----------Get----------");
		 System.out.println(requestGet("/api/v1/employees").getBody().asString());
		 
		 System.out.println("---------Post-----------");
		 Response resp=requestPost("api/v1/create");
		 JsonPath jsonPathEvaluator= resp.jsonPath();
		 String emp_id=jsonPathEvaluator.get("id");
		 System.out.println(resp.getBody().asString());
		 System.out.println("emp id=" +emp_id);
		 System.out.println("----------Get with emp id----------");
		 System.out.println(requestGet("/api/v1/employee/"+emp_id).getBody().asString());
		 
		 
		 System.out.println("----------Update details with ----------" +emp_id);
		 System.out.println(requestPut("api/v1/update/"+emp_id).getBody().asString());
		 
		 System.out.println("----------Delete details with ----------" +emp_id);
		 Response respDel=deleteJsonPayload("api/v1/delete/"+emp_id);
		 JsonPath jsonPathEvaluator12= respDel.jsonPath();
		 System.out.println(jsonPathEvaluator12.get("success.text").toString());;
	}
	
	public static Response requestGet(String Uri)
	{
		return getJsonPayload(Uri);
	}
	
	
	public static Response requestPost(String Uri)
	{
		Timestamp ts=new Timestamp(System.currentTimeMillis());  
				
		JsonObject person = new JsonObject();
		person.addProperty("name", "test"+ts.getTime());
		person.addProperty("salary", "123");
		person.addProperty("age", "24");
		
		return postJsonPayload(person.toString(),Uri);
	}

	public static Response requestPut(String Uri)
	{
		Timestamp ts=new Timestamp(System.currentTimeMillis());  
				
		JsonObject person = new JsonObject();
		person.addProperty("name", "test"+ts.getTime());
		person.addProperty("salary", "123");
		person.addProperty("age", "24");
		
		return putJsonPayload(person.toString(),Uri);
	}
	
	  public static Response postJsonPayload(String payload,String uri) {
	        
	        return
	            given()
	            .contentType(ContentType.JSON)
	            .body(payload)
	            .post(uri)
	            .then()
	            .statusCode(200)
	            .extract()
	            .response();
	    }
	  
	  public static Response getJsonPayload(String uri) {
	        
	        return
	            given().when()
	            .get(uri)
	            .then()
	            .assertThat().statusCode(200).extract()
	            .response();
	    }
	
	  public static Response putJsonPayload(String payload,String uri) {
	        
	        return
	            given()
	            .contentType(ContentType.JSON)
	            .body(payload)
	            .put(uri)
	            .then()
	            .statusCode(200)
	            .extract()
	            .response();
	    }
	  
	  public static Response deleteJsonPayload(String uri) {
	        
		  return
		            given().when()
		            .delete(uri)
		            .then()
		            .assertThat().statusCode(200).extract()
		            .response();
	    }
	  
}
