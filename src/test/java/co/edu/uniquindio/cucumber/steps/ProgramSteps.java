package co.edu.uniquindio.cucumber.steps;

import co.edu.uniquindio.cucumber.steps.UserSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;


public class ProgramSteps {

    private Response response;
    private RequestSpecification request;
    private static final String BASE_URL = "http://localhost:8100";

    private JSONObject buildRequestBody(String title, String description, String content, int creatorId) {
        JSONObject body = new JSONObject();
        body.put("title", title);
        body.put("description", description);
        body.put("content", content);
        body.put("creatorId", creatorId);
        return body;
    }

    private void verifyProgramDetails() {
        response.then()
                .body("id", notNullValue())
                .body("title", notNullValue())
                .body("description", notNullValue())
                .body("content", notNullValue())
                .body("creatorId", notNullValue());
    }

    @When("I create a program with title {string}, description {string} content {string} and creatorId {int}")
    public void createProgram(String title, String description, String content, int creatorId) {
        JSONObject requestBody = buildRequestBody(title, description, content, creatorId);

        request = given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());

        response = request.when().post(BASE_URL + "/program");
    }

    @Then("the program should be created successfully")
    public void programCreatedSuccessfully() {
        response.then().statusCode(201);
    }

    @And("the response should include the program details")
    public void responseIncludesProgramDetails() {
        verifyProgramDetails();
    }

    @Given("a program exists with ID {int}")
    public void programExists(int id) {
        response = given().when().get(BASE_URL + "/program/" + id);
    }

    @When("I request the program with ID {int}")
    public void requestProgramById(int id) {
        response = given().when().get(BASE_URL + "/program/" + id);
    }

    @Then("the program details should be returned")
    public void programDetailsReturned() {
        response.then().statusCode(200);
        verifyProgramDetails();
    }

    @When("I update the program with ID {int} with name {string} description {string} and content {string}")
    public void updateProgram(int id, String name, String description, String content) {
        // Get the existing creatorId
        int creatorId = given().when().get(BASE_URL + "/program/" + id).jsonPath().getInt("creatorId");

        JSONObject requestBody = buildRequestBody(name, description, content, creatorId);
        requestBody.put("id", id); // Add id for update

        request = given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());

        response = request.when().put(BASE_URL + "/program");
    }

    @Then("the program should be updated successfully")
    public void programUpdatedSuccessfully() {
        response.then().statusCode(200);
    }

    @And("the program details should be updated")
    public void programDetailsUpdated() {
        response.then()
                .body("id", notNullValue())
                .body("title", notNullValue())
                .body("description", notNullValue())
                .body("content", notNullValue());
    }

    /*
    @When("I delete the program with ID {int}")
    public void deleteProgram(int id) {
        response = given().when().delete(BASE_URL + "/program/" + id);
    }

    @Then("the program should be deleted successfully")
    public void programDeletedSuccessfully() {
        response.then().statusCode(200);
    }

    @And("the program should no longer exist in the system")
    public void programNoLongerExists() {
        Response checkResponse = given().when().get(BASE_URL + "/program/" + id);
        checkResponse.then().statusCode(404);
    }
    */
}