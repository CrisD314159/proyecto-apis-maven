Feature: Comment management

  Scenario: Create a new comment
    Given a user exists with ID 1
    Given a program exists with ID 1
    When I create a comment with content "This is a test comment" by user with ID 1 and program ID 1
    Then the comment should be created successfully
    And the response should include the comment details

  Scenario: Get a comment by ID
    Given a comment exists with ID 1
    When I request the comment with ID 1
    Then the comment details should be returned

  Scenario: Update a comment
    Given a comment exists with ID 1
    When I update the comment with ID 1 with content "Updated comment text"
    Then the comment should be updated successfully

