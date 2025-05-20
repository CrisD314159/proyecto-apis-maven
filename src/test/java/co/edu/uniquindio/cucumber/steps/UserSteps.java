package co.edu.uniquindio.cucumber.steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.vertx.core.json.JsonObject;


import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
public class UserSteps {

    private Response response;
    private RequestSpecification request;
    private static final String BASE_URL = "http://localhost:8080";
    private JsonObject defaultUser = new JsonObject()
            .put("fullName", "Default User")
            .put("email", "default" + UUID.randomUUID() + "@example.com")
            .put("password", "Default123");

    @When("I create a user with name {string} email {string} and password {string}")
    public void createUser(String name, String email, String password) {
        JsonObject requestBody = new JsonObject()
                .put("fullName", name)
                .put("email", email)
                .put("password", password);

        request = given()
                .header("Content-Type", "application/json")
                .body(requestBody.encode());

        response = request.when().post(BASE_URL + "/users");
    }

    @Then("the user should be created successfully")
    public void userCreatedSuccessfully() {
        response.then().statusCode(200);
    }

    @Given("a user exists with ID {int}")
    public void userExists(int id) {
        response = given().when().get(BASE_URL + "/users/" + id);

        if (response.statusCode() == 404) {
            JsonObject requestBody = new JsonObject()
                    .put("id", id)
                    .put("fullName", "User " + id)
                    .put("email", "user" + id + "@example.com")
                    .put("password", "User12345");

            response = given()
                    .header("Content-Type", "application/json")
                    .body(requestBody.encode())
                    .when()
                    .post(BASE_URL + "/users");
        }
    }

    @When("I request the user with ID {int}")
    public void requestUserById(int id) {
        response = given().when().get(BASE_URL + "/users/" + id);

        if (response.statusCode() == 404) {
            JsonObject requestBody = new JsonObject()
                    .put("id", id)
                    .put("fullName", "Recovered User " + id)
                    .put("email", "recovered" + id + "@example.com")
                    .put("password", "Recovered123");

            given()
                    .header("Content-Type", "application/json")
                    .body(requestBody.encode())
                    .when()
                    .post(BASE_URL + "/users");

            response = given().when().get(BASE_URL + "/users/" + id);
        }
    }

    @Then("the user details should be returned")
    public void userDetailsReturned() {
        response.then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("fullName", notNullValue())
                .body("email", notNullValue())
                .body("creationDate", notNullValue())
                .body("state", notNullValue())
                .body("role", notNullValue());
    }

    @When("I update the user with ID {int} with name {string}")
    public void updateUser(int id, String name) {
        // Primero verificamos si el usuario existe
        Response check = given().when().get(BASE_URL + "/users/" + id);

        if (check.statusCode() == 404) {
            JsonObject requestBody = new JsonObject()
                    .put("id", id)
                    .put("fullName", name)
                    .put("email", "newuser" + id + "@example.com")
                    .put("password", "Newuser123");

            given()
                    .header("Content-Type", "application/json")
                    .body(requestBody.encode())
                    .when()
                    .post(BASE_URL + "/users");
        }

        JsonObject updateBody = new JsonObject()
                .put("id", id)
                .put("fullName", name);

        request = given()
                .header("Content-Type", "application/json")
                .body(updateBody.encode());

        response = request.when().put(BASE_URL + "/users");
    }

    @Then("the user should be updated successfully")
    public void userUpdatedSuccessfully() {
        response.then().statusCode(200);
    }
}