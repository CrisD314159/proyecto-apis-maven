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
    private static final String BASE_URL = "http://localhost:8010";
    private int exampleId;

    private JSONObject buildExampleJson(String title, String description, String content, int creatorId, String difficulty) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("description", description);
        requestBody.put("content", content);
        requestBody.put("creatorId", creatorId);
        requestBody.put("difficulty", difficulty);
        return requestBody;
    }

    private void sendPostExample(JSONObject requestBody) {
        request = given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());
        response = request.when().post(BASE_URL + "/examples");
        exampleId = response.jsonPath().getInt("id");
    }

    @When("I create an example with title {string} description {string} content {string} creator id {int} and difficulty {string}")
    public void createExample(String title, String description, String content, int creatorId, String difficulty) {
        JSONObject requestBody = buildExampleJson(title, description, content, creatorId, difficulty);
        sendPostExample(requestBody);
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
        // Try to fetch it first
        response = given().when().get(BASE_URL + "/examples/" + id);

        if (response.statusCode() == 404) {
            // Create one if not found
            JSONObject requestBody = buildExampleJson("Sample Title", "Sample Desc", "Sample Content", 1, "MEDIUM");
            sendPostExample(requestBody);
        } else {
            exampleId = id;
        }
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
        JSONObject requestBody = buildExampleJson(title, description, content, creatorId, difficulty);
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
}