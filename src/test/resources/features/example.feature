Feature: Example management

  Scenario: Create a new example
    When I create an example with title "Test Example" description "This is a test example" content "example content" creator id 1 and difficulty "hard"
    Then the example should be created successfully
    And the response should include the example details

  Scenario: Get an example by ID
    Given an example exists with ID 1
    When I request the example with ID 1
    Then the example details should be returned

  Scenario: Update an example
    Given an example exists with ID 1
    When I update the example with ID 1 with title "Test Updated" description "This is a test updated" content "example updated" creator id 1 and difficulty "easy"
    Then the example should be updated successfully
    And the example details should be updated

  Scenario: Delete an example
    Given an example exists with ID 1
    When I delete the example with ID 1
    Then the example should be deleted successfully
    And the example should no longer exist in the system