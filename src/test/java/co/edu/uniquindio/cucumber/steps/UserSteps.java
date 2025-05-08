package co.edu.uniquindio.cucumber.steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.vertx.core.json.JsonObject;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserSteps {

    private Response response;
    private RequestSpecification request;
    private static final String BASE_URL = "http://localhost:8080";

    @When("I create a user with name {string} email {string} and password {string}")
    public void createUser(String name, String email, String password) {
        JsonObject requestBody = new JsonObject();
        requestBody.put("fullName", name);
        requestBody.put("email", email);
        requestBody.put("password", password);

        request = given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());

        response = request.when().post(BASE_URL + "/users");
    }

    @Then("the user should be created successfully")
    public void userCreatedSuccessfully() {
        response.then().statusCode(200);
    }

    @Given("a user exists with ID {int}")
    public void userExists(int id) {
        // Check if the user exists
        response = given().when().get(BASE_URL + "/users/" + id);
    }

    @When("I request the user with ID {int}")
    public void requestUserById(int id) {
        response = given().when().get(BASE_URL + "/users/" + id);
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

        // Create update request with the new name but keeping other fields
        JsonObject requestBody = new JsonObject();
        requestBody.put("id", id);
        requestBody.put("fullName", name);
        // Don't include password in update if it's not being changed

        request = given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());

        response = request.when().put(BASE_URL + "/users");
    }

    @Then("the user should be updated successfully")
    public void userUpdatedSuccessfully() {
        response.then().statusCode(200);
    }

    @When("I delete the user with ID {int}")
    public void deleteUser(int id) {
        response = given().when().delete(BASE_URL + "/users/" + id);
    }

    @Then("the user should be deleted successfully")
    public void userDeletedSuccessfully() {
        response.then().statusCode(204);
    }

    @And("the user should no longer exist in the system")
    public void userNoLongerExists() {
        // Get the ID from the previous request context
        int id = Integer.parseInt(response.getHeader("Resource-ID"));

        // Verify the user no longer exists
        Response checkResponse = given().when().get(BASE_URL + "/users/" + id);
        checkResponse.then().statusCode(404);
    }
}