Eco Clothes Shop.

This project is a platform for selling eco-friendly clothing and clothing that has been recycled. 
The online store offers a wide range of clothing for men and women. 
It meets all the technical requirements:

- Product search based on various parameters such as price, color, size, etc.
- Quick view of product information and adding it to the cart
- Order processing using customer data
- Convenient payment options through various payment systems
- Order tracking functionality

The project addresses the problem of waste production on our planet.

To start using this application, you need to connect a database (PostgreSQL is set as the default). 
GET requests for obtaining information about products, new arrivals, promotions, and searching for products based on your criteria are available to all users,
including non-registered ones. To add items to the cart, create an order, and make a payment, you will need to authenticate yourself in the system.

## Technology Stack

- Spring Boot
- Maven
- Spring Security
- PostgreSQL

## Additional Features

The project includes logging (slf4j) that records all information about errors in the service layer methods to a log file (logs/logs.log).
Exception handling is done using the @ExceptionHandler annotation (a separate class is created to handle exceptions and pass them to the frontend, making the controller classes more concise).
For payment processing, services like PayPal and Stripe are used, allowing users to pay for their orders online using credit or debit cards.
Password recovery is implemented by generating a token with a limited lifespan (5 minutes) and sending the token route to the user's email.
By following the route, the user can change the current password to a new one. Security is implemented using JWT tokens and OAuth 2.0 (Google).

## Conclusion

Thank you for your attention. I believe this project will be useful for both practical use and learning purposes.
