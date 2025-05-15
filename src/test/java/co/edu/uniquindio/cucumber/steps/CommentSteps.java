package co.edu.uniquindio.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.vertx.core.json.JsonObject;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CommentSteps{

    private Response response;
    private RequestSpecification request;
    private static final String BASE_URL = "http://localhost:8080"; // Adjust as needed

    private void createProgram() {
        createUser();
        JSONObject body = new JSONObject();
        body.put("title", "Comment program");
        body.put("description", "pogrammm description");
        body.put("content", "public static void");
        body.put("creatorId", 1);
        request = given()
                .header("Content-Type", "application/json")
                .body(body.toString());
        response = request.when().post(BASE_URL + "/program");
    }

    private void createUser() {
        JsonObject requestBody = new JsonObject()
                .put("fullName", "Pedro")
                .put("email", "pedro@gmail.com")
                .put("password", "PasswoR-23423");

        request = given()
                .header("Content-Type", "application/json")
                .body(requestBody.encode());
        response = request.when().post(BASE_URL + "/users");

    }

    @When("I create a comment with content {string} by user with ID {int} and program ID {int}")
    public void createComment(String content, int userId, int programId) {
        createProgram();
        JSONObject requestBody = new JSONObject();
        requestBody.put("content", content);
        requestBody.put("authorId", userId);
        requestBody.put("programId", programId);

        request = given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());

        response = request.when().post(BASE_URL + "/comments");
    }

    @Then("the comment should be created successfully")
    public void commentCreatedSuccessfully() {
        response.then().statusCode(201);
    }

    @And("the response should include the comment details")
    public void responseIncludesCommentDetails() {
        response.then()
                .statusCode(201)
                .body("content", notNullValue())
                .body("authorId", notNullValue())
                .body("programId", notNullValue());
    }

    @Given("a comment exists with ID {int}")
    public void commentExists(int id) {
        // Check if the comment exists
        response = given().when().get(BASE_URL + "/comments/" + id);
        if (response.getStatusCode() != 200) {
            // Si no existe, crear el comentario con datos por defecto
            JSONObject requestBody = new JSONObject();
            requestBody.put("id", id); // si tu API lo permite
            requestBody.put("content", "Default content");
            requestBody.put("authorId", 1);   // Asegúrate que el usuario y programa existen
            requestBody.put("programId", 1);

            given()
                    .header("Content-Type", "application/json")
                    .body(requestBody.toString())
                    .when()
                    .post(BASE_URL + "/comments")
                    .then()
                    .statusCode(201);
        }

    }

    @When("I request the comment with ID {int}")
    public void requestCommentById(int id) {
        response = given().when().get(BASE_URL + "/comments/" + id);

        if (response.getStatusCode() != 200) {
            // Si no existe, crear el comentario con datos por defecto
            JSONObject requestBody = new JSONObject();
            requestBody.put("id", id); // si tu API lo permite
            requestBody.put("content", "Default content");
            requestBody.put("authorId", 1);   // Asegúrate que el usuario y programa existen
            requestBody.put("programId", 1);

            given()
                    .header("Content-Type", "application/json")
                    .body(requestBody.toString())
                    .when()
                    .post(BASE_URL + "/comments")
                    .then()
                    .statusCode(201);

            response = given().when().get(BASE_URL + "/comments/" + id);
        }


    }

    @Then("the comment details should be returned")
    public void commentDetailsReturned() {
        response.then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("content", notNullValue());
    }

    @When("I update the comment with ID {int} with content {string}")
    public void updateComment(int id, String content) {
        // First, get the existing comment to preserve other fields
        Response getResponse = given().when().get(BASE_URL + "/comments/" + id);

        // Create update request with the new content but keeping other fields
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", id);
        requestBody.put("content", content);

        request = given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());

        response = request.when().put(BASE_URL + "/comments");
    }

    @Then("the comment should be updated successfully")
    public void commentUpdatedSuccessfully() {
        response.then().statusCode(200);
    }

//    @When("I delete the comment with ID {int}")
//    public void deleteComment(int id) {
//        response = given().when().delete(BASE_URL + "/comments/" + id);
//    }
//
//    @Then("the comment should be deleted successfully")
//    public void commentDeletedSuccessfully() {
//        response.then().statusCode(204);
//    }
//
//    @And("the comment should no longer exist in the system")
//    public void commentNoLongerExists() {
//        // Parse the ID from the delete request path
//        String path = response.getHeaders().getValue("Path");
//        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
//
//        // Verify the comment no longer exists
//        Response checkResponse = given().when().get(BASE_URL + "/comments/" + id);
//        checkResponse.then().statusCode(404);
//    }
}
