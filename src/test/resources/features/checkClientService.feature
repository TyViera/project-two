Feature: Test ClientService methods

  Scenario: Get all clients
    Given path '/clients'
    When method get
    Then status 200
    And match response == '#[{"id": "#uuid", "name": "John Doe", "nif": "123456789", "address": "123 Main St"}]'  # Usa datos que esperas en la respuesta

  Scenario: Get client by ID
    Given path '/clients', '#uuid'  # Reemplaza #uuid por un UUID válido
    When method get
    Then status 200
    And match response.id == '#uuid'
    And match response.name == 'John Doe'

  Scenario: Get client by ID - Client not found
    Given path '/clients', 'invalid-uuid'  # Proporción un UUID que no existe
    When method get
    Then status 404

  Scenario: Create a new client
    Given path '/clients'
    And request { "name": "Jane Doe", "nif": "987654321", "address": "456 Main St" }
    When method post
    Then status 201
    And match response.name == 'Jane Doe'
    And match response.nif == '987654321'
    And match response.address == '456 Main St'

  Scenario: Update an existing client
    Given path '/clients', '#uuid'  # Reemplaza #uuid por un UUID válido
    And request { "name": "John Doe Updated", "nif": "987654321", "address": "456 Main St" }
    When method put
    Then status 200
    And match response.name == 'John Doe Updated'
    And match response.nif == '987654321'
    And match response.address == '456 Main St'

  Scenario: Update a non-existing client
    Given path '/clients', 'invalid-uuid'  # Proporción un UUID que no existe
    And request { "name": "Non Existent", "nif": "000000000", "address": "Nowhere" }
    When method put
    Then status 404

  Scenario: Delete an existing client
    Given path '/clients', '#uuid'  # Reemplaza #uuid por un UUID válido
    When method delete
    Then status 200

  Scenario: Delete a non-existing client
    Given path '/clients', 'invalid-uuid'  # Proporción un UUID que no existe
    When method delete
    Then status 404
