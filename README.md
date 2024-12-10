# Explanation of Exercise two

## Activities

Using SpringBoot as base for the project do:

- Create a CRUD for clients
- Create a CRUD for products
- Sell a product
- Purchase a product (renew stock)
- See past sales
- See income report - 5 most sold products
- Add tests for your code, coverage of lines must be at least 50%
- Document the REST operations using Swagger/OpenAPI, the docs endpoint must be available in `/swagger-ui.html`
- Add basic security to the sales, purchases and reports
- (Optional) - See stock
- (Optional) - Add a cache for the products
- (Optional) - Include Karate/Cucumber test cases

## Operations

### CRUD for Clients

Same as exercise one we have to do a CRUD for clients, but this time the structure must be:

Input:

```json
{
  "name": "(mandatory) alphanumeric string, min length: 2, max length: 150",
  "nif": "(mandatory) alphanumeric string, min length: 9, max length: 10",
  "address": "(optional) string, min length: 5, max length: 150"
}
```

Output:a

```json
{
  "id": "uuid - you can use Java generator: UUID.randomUUID().toString()",
  "name": "input string",
  "nif": "input string",
  "address": "(optional) input string, only return it if value is not null"
}
```

Summary:

- `GET /clients` - get all clients in a list
- `GET /clients/{id}` - get one client by id, 404 if not found
- `POST /clients` - create a new client, returns 201 with the new client
- `PUT /clients/{id}` - update a client by id, 404 if not found
- `DELETE /clients/{id}` - delete a client by id, 404 if not found, and 422 if client has orders (sales in the system)

### CRUD for Products

Same as exercise one we have to do a CRUD for products, but this time the structure must be:

Input:

```json
{
  "name": "(mandatory) alphanumeric string, min length: 2, max length: 100",
  "code": "(mandatory) alphanumeric string, min length: 5, max length: 10. Unique value, in case of repetition, return 422 with a error message saying 'There is already a product with the provided code'"
}
```

Output:

```json
{
  "id": "uuid - you can use Java generator: UUID.randomUUID().toString()",
  "name": "input string",
  "code": "input string"
}
```

Summary:

- `GET /products` - get all products in a list
- `GET /products/{id}` - get one product by id, 404 if not found
- `POST /products` - create a new product, returns 201 with the new product - here created stock must be 0
- `PUT /products/{id}` - update a product by id, 404 if not found
- `DELETE /products/{id}` - delete a product by id, 404 if not found, and 422 if product has sold previously (exists a
  sales in the system for this product)

### Sell a product

**Note:** This operation must be protected with basic auth

Defined by `POST /sales` the input must be:

```json
{
  "product": {
    "id": "<product-id>"
  },
  "client": {
    "id": "<client-id>"
  },
  "quantity": "positive integer number"
}
```

And the output must be a `201` without response body

### Purchase a product (renew stock)

**Note:** This operation must be protected with basic auth

Defined by `POST /purchases` the input must be:

```json
{
  "product": {
    "id": "<product-id>"
  },
  "supplier": "<supplier-name>",
  "quantity": "positive integer number"
}
```

And the output must be a `201` without response body

### See past sales

**Note:** This operation must be protected with basic auth

Defined by `GET /clients/{id}/sales` being `{id}` the client id, and the output must be a `200` with the next structure:

```json
[
  {
    "id": "<sale-id>",
    "products": [
      {
        "product": {
          "id": "<product-id>",
          "name": "<product-name>"
        },
        "quantity": "<sale-quantity>"
      }
    ]
  }
]
```

### See income report - 5 most sold products

**Note 1:** This operation must be protected with basic auth
**Note 2:** It must include the 5 most sold products, ordered desc by quantity.

Defined by `GET /sales/most-sold-products` and the output must be a `200` with the next structure:

```json
[
  {
    "product": {
      "id": "<product-id>",
      "name": "<product-name>"
    },
    "quantity": "<sale-quantity>"
  }
]
```

### (Optional) - See stock

Defined by `GET /products/{id}/stock` being `{id}` the product id, and the output must be a `200` with the next
structure:

```json
{
  "stock": "product-stock"
}
```

## Dates

The last day to create the pull request is **Monday, January 13, 2025**
The feedback date is **Monday, January 20, 2025**

## Method of evaluation

- The project must be developed individually.
- Create a fork of this repository and once your development is finished, create a pull request with your name as title.
- You can share information with your teammates.
- Only backend is required, if frontend is also included, add instructions to run it.
- Database provider is on your side, just put your choice in the Pull Request description.
- If you decide to include the stock operation, put it in the Pull Request description.
- If you decide to include cache, put it in the Pull Request description.
- If you decide to use Cucumber, put it in the Pull Request description.
- If you decide to use Karate, put it in the Pull Request description. 
