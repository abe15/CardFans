# Card Fans Ecommerce Site

## Overview

Card Fans is an ecommerce web application centered on selling playing card decks. Users can add decks to their cart, edit their cart, check out, and check their orders. Users are also able to register, login. and view/edit their profile.

The application covers the entire full-stack with the frontend built on Angular and Bootstrap and the backend built using Spring in Java 8. Communication between the two stacks is achieved using the REST API. Additional tools used are JWT for authentication and authorization and Hibernate for Object Relational Mapping.

## Prerequisites

The following software must be installed before you're able to run the web app:

- [Java](https://www.java.com/en/download/)
- [Apache Maven](https://maven.apache.org/download.cgi)
- PostgreSQL ([Local Install](https://www.postgresql.org/download/) or [Docker Container](https://hub.docker.com/_/postgres))
- [Node.js](https://nodejs.org/en/download/)

### Additional Configurations

#### Preparing Spring

1. Navigate to `.\Card Fans Backend\src\main\resources\application.properties`.
2. Set the values for `spring.datasource.url`, `.username`, and `.password` to the relevant fields associated with your PostgreSQL installation.
3. Make `spring.jpa.hibernate.ddl-auto` equal to `create`. **_Once you run Spring for the first time, change it back to `update`._**
4. _Optional:_ If you already have another program running on port 8080, change `server.port` to something different.

#### Preparing Angular

- Within your terminal of choice, navigate to `.\web-app` and run the command `npm install`.
- If you changed the port the Spring server runs on, open up `.\web-app\src\app\services\rest.datasource.ts`. Find `this.baseUrl` which is being set to a URL and change the `8080` port to the port you defined.

## Continue Reading

For more information concerning the Spring backend API, follow the README [here](https://github.com/221114-Java-Angular-NGC/card-fans-p2/blob/julioBranch/Card%20Fans%20Backend/README.md)

For more information on the Angular frontend, follow the README [here](https://github.com/221114-Java-Angular-NGC/card-fans-p2/blob/julioBranch/web-app/README.md).
