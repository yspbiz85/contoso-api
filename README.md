# Contoso Gaming Platform

This platform  implement's the back-end platform for a Multi-player Gaming software and consist of an API to locate the actors in the game.

All actors are tagged to the Landmarks and the 
purpose of the API is to suggest the various routes
between landmarks, to the actors based on various criteria.

Primarily API should be able to perform below operation 

* Able to generate and save the graph based on the input, provided by client.

* The distance along certain routes via landmarks for the particular saved graph.

* The number of different routes between landmarks for the particular saved graph.

For e.g.

The input to the program should consist of set of data represented by Starting Landmark, Ending 
Landmark and directed distance. A given route should not appear more than once. The starting and 
ending landmark cannot be the same for a given route. For input data you can use a formatted input 
so for e.g. A route from A to B landmarks with a distance of 3Km will be represented as AB3. For 
queries where no path exists between 2 landmarks, output should be. returned as “Path not found".

API should be able to answer following question -
    
    1. The distance between landmarks via the route A-B-C.
    
    2. The distance between landmarks via the route A-E-B-C-D.
    
    3. The distance between landmarks via the route A-E-D .
    
    4. The number of routes starting at A and ending at C with a maximum of 2 stops.

**Sample input to test with**

`AB3, BC9, CD3, DE6, AD4, DA5, CE2, AE4,EB1`

**Expected Output from Sample Input**

    1. 12
    2. 17
    3. Path not found.
    4. 2

## API Reference

#### New User Signup

```http
  POST /api/v1/auth/signup
```

| Parameter | Type     | Description                         |
| :-------- | :------- | :-----------------------------------|
| `email`   | `string` | **Required**. Should be valid email. |
| `username`| `string` | **Required**. Should not be null/empty and minimum length is 3.|
| `password`| `string` | **Required**. Should not be null/empty and must be of minimum 6 characters, consist of At least one upper case,one lower case,one digit and one special character.|

#### User Login

```http
  POST /api/v1/auth/signin
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username`| `string` | **Required**. Should not be null/empty and minimum length is 3.|
| `password`| `string` | **Required**. Should not be null/empty and must be of minimum 6 characters, consist of At least one upper case,one lower case,one digit and one special character.|

**Default username JSON request body**

`{
    "userName":"contoso",
    "password":"contoso123"
}`

#### Create New Graph

```http
  POST /api/v1/graph/save
```

Requires JWT token key obtain using User Login API

| Parameter | Type     | Description                         |
| :-------- | :------- | :-----------------------------------|
| `graphName'| `string` | **Required**. Should not be null or empty |
| `graphData`| `JSONBody of Nodes and Edges` | **Required**. Minimum 2 nodes and and 1 Edge|

#### Get All Graphs By User ID

```http
  GET /api/v1/graph/all
```
Requires JWT token key obtain using User Login API

#### Get Graph By Graph ID

```http
  GET /api/v1/graph/{graphId}
```

Requires JWT token key obtain using User Login API

| Parameter | Type     | Description                         |
| :-------- | :------- | :-----------------------------------|
| `graphId'| `UUID` | **Required**. Should not be null or empty |

#### Update Graph By Graph ID

```http
  PUT /api/v1/graph/{graphId}
```

Requires JWT token key obtain using User Login API

| Parameter | Type     | Description                         |
| :-------- | :------- | :-----------------------------------|
| `graphId'| `UUID` | **Required**. Should not be null or empty |

#### Delete Graph By Graph ID

```http
  DELETE /api/v1/graph/{graphId}
```

Requires JWT token key obtain using User Login API

| Parameter | Type     | Description                         |
| :-------- | :------- | :-----------------------------------|
| `graphId'| `UUID` | **Required**. Should not be null or empty |

#### Delete All Graph for the user

```http
  DELETE /api/v1/graph/all
```
Requires JWT token key obtain using User Login API


#### Get Default Graph Data with answer

```http
  Get /api/v1/gaming/default
```

Requires JWT token key obtain using User Login API

#### Calculate route between landmark for graph or graphs

```http
  Get /api/v1/gaming/calculateData
```

Requires JWT token key obtain using User Login API

Requires JSON body having graphID and Array of routes

## Roadmap

**Deployment Strategy**

Github -> AWS Code Build -> AWS Code Pipeline  -> AWS Code Deploy -> AWS EC2 Ubuntu AMI

1. Devlopers will commit the changes to Github.
2. Github webhook triggers AWS Code Build.
3. Code Build fetches latest changes and builds the JAR
4. AWS Code Pipeline will triggered by Code Build once finished and which
   in turns triggers AWS Code Deploy which deploys the production JAR
   over AWS EC2 Ubuntu AMI and starts the application over 
   port 8000.


**UI Developement**

Currently user will be able to access the application using Swagger UI
But Currently UI application devloped using React is in progress.

**Unit Testing**

Will use Mockito and Junit frameowrk for unit testing of
REST Endpoint

**Cache Implementation**
Redis cache Implementation for performance centric application




  
## Project Architecture

Swagger UI/Postman/React Web App/Mobile App -> Spring Security -> REST -> Spring Data JPA -> AWS RDS PostgreSQL

1. Client call the REST endpoint.
2. Spring security validates the user.
3. Spring framwork handles all the REST endpoint operations.
4. Spring Data JPA saves/retrieves the data to AWS RDS PostgreSQL Instance


  