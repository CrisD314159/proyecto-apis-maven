Feature: User management

  Scenario: Create a new user
    When I create a user with name "John Doe" email "john@example.com" and password "John-doe8"
    Then the user should be created successfully

  Scenario: Get a user by ID
    Given a user exists with ID 1
    When I request the user with ID 1
    Then the user details should be returned

  Scenario: Update a user
    Given a user exists with ID 1
    When I update the user with ID 1 with name "Jane Doe"
    Then the user should be updated successfully
