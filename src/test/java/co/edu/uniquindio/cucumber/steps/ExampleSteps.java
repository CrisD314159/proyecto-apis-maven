package co.edu.uniquindio.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ExampleSteps {

    private Response response;
    private RequestSpecification request;
    private static final String BASE_URL = "http://localhost:8080";

    @When("I create an example with title {string} description {string} content {string} creator id {int} and difficulty {string}")
    public void createExample(String title, String description, String content, int creatorId, String difficulty) {
        // First make sure the creator user exists
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("description", description);
        requestBody.put("content", content);
        requestBody.put("creatorId", creatorId);
        requestBody.put("difficulty", difficulty);

        request = given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());

        response = request.when().post(BASE_URL + "/examples");
    }

    @Then("the example should be created successfully")
    public void exampleCreatedSuccessfully() {
        response.then().statusCode(201);
    }

    @And("the response should include the example details")
    public void responseIncludesExampleDetails() {
        response.then()
                .body("id", notNullValue())
                .body("title", notNullValue())
                .body("description", notNullValue())
                .body("content", notNullValue())
                .body("creatorId", notNullValue())
                .body("difficulty", notNullValue());
    }

    @Given("an example exists with ID {int}")
    public void exampleExists(int id) {
        // Check if the example exists
        response = given().when().get(BASE_URL + "/examples/" + id);

    }

    @When("I request the example with ID {int}")
    public void requestExampleById(int id) {
        response = given().when().get(BASE_URL + "/examples/" + id);
    }

    @Then("the example details should be returned")
    public void exampleDetailsReturned() {
        response.then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("title", notNullValue())
                .body("description", notNullValue())
                .body("content", notNullValue())
                .body("creatorId", notNullValue())
                .body("difficulty", notNullValue());
    }

    @When("I update the example with ID {int} with title {string} description {string} content {string} creator id {int} and difficulty {string}")
    public void updateExample(int id, String title, String description, String content, int creatorId, String difficulty) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("description", description);
        requestBody.put("content", content);
        requestBody.put("creatorId", creatorId);
        requestBody.put("difficulty", difficulty);

        request = given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());

        response = request.when().put(BASE_URL + "/examples/" + id);
    }

    @Then("the example should be updated successfully")
    public void exampleUpdatedSuccessfully() {
        response.then().statusCode(200);
    }

    @And("the example details should be updated")
    public void exampleDetailsUpdated() {
        response.then()
                .body("id", notNullValue())
                .body("title", notNullValue())
                .body("description", notNullValue())
                .body("content", notNullValue())
                .body("creatorId", notNullValue())
                .body("difficulty", notNullValue());
    }

    @When("I delete the example with ID {int}")
    public void deleteExample(int id) {
        response = given().when().delete(BASE_URL + "/examples/" + id);
    }

    @Then("the example should be deleted successfully")
    public void exampleDeletedSuccessfully() {
        response.then().statusCode(204);
    }

    @And("the example should no longer exist in the system")
    public void exampleNoLongerExists() {
        // Since the delete operation doesn't return the ID in the response,
        // we need to parse it from the request URL
        String path = response.getHeaders().getValue("Path");
        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));

        // Verify the example no longer exists
        Response checkResponse = given().when().get(BASE_URL + "/examples/" + id);
        checkResponse.then().statusCode(404);
    }
}