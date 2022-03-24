package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend"; 
	}
	
	@Test
	public void willReturnTasks() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void willAddTaskWithSuccess() {
		RestAssured.given()
		.body("{\r\n"
				+ "	\"task\": \"A test with API\",\r\n"
				+ "	\"dueDate\": \"2022-04-01\"\r\n"
				+ "}")
		.contentType(ContentType.JSON)
		.when()
		.post("/todo")
		.then()
		.statusCode(201);
	}

	@Test
	public void willFailWhenAddingInvalidTask() {
		RestAssured.given()
		.body("{\r\n"
				+ "	\"task\": \"A test with API\",\r\n"
				+ "	\"dueDate\": \"2010-04-01\"\r\n"
				+ "}")
		.contentType(ContentType.JSON)
		.when()
		.post("/todo")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
	}
}

