Feature: Testing the controllers

  #CLIENTCONTROLLER
  Scenario: Get all clients
    Given path '/clients'
    When method GET
    Then status 200
    And match response == []

  Scenario: Create a new client
    Given path '/clients'
    And request { "name": "John Doe", "email": "johndoe@example.com" }
    When method POST
    Then status 201
    And match response.name == "John Doe"
    And match response.email == "johndoe@example.com"

  Scenario: Get client by ID
    Given path '/clients/00000000-0000-0000-0000-000000000001'
    When method GET
    Then status 200
    And match response.id == '00000000-0000-0000-0000-000000000001'

  Scenario: Client not found by ID
    Given path '/clients/00000000-0000-0000-0000-000000000002'
    When method GET
    Then status 404

  Scenario: Update an existing client
    Given path '/clients/00000000-0000-0000-0000-000000000001'
    And request { "name": "Jane Doe", "email": "janedoe@example.com" }
    When method PUT
    Then status 200
    And match response.name == "Jane Doe"
    And match response.email == "janedoe@example.com"

  Scenario: Delete a client
    Given path '/clients/00000000-0000-0000-0000-000000000001'
    When method DELETE
    Then status 200

    #PRODUCTCONTROLLER
  Scenario: Get all products
    Given path '/products'
    When method GET
    Then status 200
    And match response == []

  Scenario: Create a new product
    Given path '/products'
    And request { "name": "Product A", "description": "Description for Product A", "price": 100.00 }
    When method POST
    Then status 201
    And match response.name == "Product A"
    And match response.description == "Description for Product A"
    And match response.price == 100.00

  Scenario: Get product by ID
    Given path '/products/00000000-0000-0000-0000-000000000001'
    When method GET
    Then status 200
    And match response.id == '00000000-0000-0000-0000-000000000001'

  Scenario: Product not found by ID
    Given path '/products/00000000-0000-0000-0000-000000000002'
    When method GET
    Then status 404

  Scenario: Update an existing product
    Given path '/products/00000000-0000-0000-0000-000000000001'
    And request { "name": "Product A Updated", "description": "Updated description", "price": 150.00 }
    When method PUT
    Then status 200
    And match response.name == "Product A Updated"
    And match response.description == "Updated description"
    And match response.price == 150.00

  Scenario: Delete a product
    Given path '/products/00000000-0000-0000-0000-000000000001'
    When method DELETE
    Then status 200

    # SALESCONTROLLER
  Scenario: Create a new sale
    Given path '/sales'
    And request { "clientId": "00000000-0000-0000-0000-000000000001", "productId": "00000000-0000-0000-0000-000000000002", "quantity": 3 }
    When method POST
    Then status 200

  Scenario: Get past sales by client ID
    Given path '/sales/clients/00000000-0000-0000-0000-000000000001/sales'
    When method GET
    Then status 200
    And match response == []

  Scenario: Get most sold products
    Given path '/sales/clients/most-sold-products'
    When method GET
    Then status 200
    And match response != []
    And match response[0].name == 'Product A'  # Reemplaza con el nombre de un producto esperado
    And match response[0].quantitySold > 0

    # PURCHASESCONTROLLER
  Scenario: Create a new purchase
    Given path '/purchases'
    And request { "clientId": "00000000-0000-0000-0000-000000000001", "productId": "00000000-0000-0000-0000-000000000002", "quantity": 3 }
    When method POST
    Then status 201
