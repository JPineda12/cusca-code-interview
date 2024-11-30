# cusca-code-interview

Spring boot code test 

![image](https://github.com/user-attachments/assets/ff598a4e-eb07-4a22-9bd2-d3ce84b35a89)

The solution is based on a microservice architecture. 
This means there's 4 microservices which are: 

## cusca-auth
This microservice is just for login and generating a valid jwt token. 

## cusca-products
This microservices obtains the catalog from FakeApiStore 

## cusca-order
This microservice allows the user from the Jwt generated in the cusca-auth service to add products from the cusca-products microservice in a order. 

## cusca-payments 
This microservice simulates the payment of the cart generated in the cusca-order microservice. 

## Testing the app
Run all the projects 
To test all the endpoints you can follow this steps:
1. Request the login in {{api-auth-url}}/auth/login
2. Check all the products with {{api-products-url}}/api/products
3. Add a desired product with {{api-order-url}}/api/orders/add/{id}
4. Check your cart with {{api-order-url}}/api/orders
5. Pay the cart of the user with {{api-payments-url}}/api/payment/process

The user that logged in in the login endpoint, is the one in the jwt token, so everything will work with just the jwt. 


If you use the provided postman collection and set the environment variables, they will be: 

- {{api-auth-url}} can be http://localhost:8050
- {api-products-url}} can be http://localhost:8080
- {{api-order-url}} can be http://localhost:8085
- {{api-payments-url}} can be http://localhost:8090

![image](https://github.com/user-attachments/assets/f0224da7-044d-4f91-ae83-e4fedc42f031)
 
