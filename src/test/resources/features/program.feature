Feature: Program management

  Scenario: Create a new program
    When I create a program with title "Test Program", description "This is a test program" content "Example" and creatorId 1
    Then the program should be created successfully
    And the response should include the program details

  Scenario: Get a program by ID
    Given a program exists with ID 1
    When I request the program with ID 1
    Then the program details should be returned

  Scenario: Update a program
    Given a program exists with ID 1
    When I update the program with ID 1 with name "Updated Program" description "Updated description" and content "Updated example"
    Then the program should be updated successfully
    And the program details should be updated

  Scenario: Delete a program
    Given a program exists with ID 1
    When I delete the program with ID 1
    Then the program should be deleted successfully
    And the program should no longer exist in the system