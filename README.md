# PetStore API automation
## Overview
This is a rest assured API automation for PetStore and exploratory testing charter for MoneyFy
### Exploratory testing charter for Moneyfy is documented under MoneyfyExploratoryCharter.md
## Prerequisites 
* Make sure Java is installed and right environment variables are set in you system. If not please follow [Java Installation](https://www.oracle.com/java/technologies/downloads/)
* Check if Maven is installed in the system. If not plese follow [Maven Installation](https://maven.apache.org/install.html)

### IDE,OS and Java version
* Visual Studio Code : 1.63.2
* jdk version : "1.8.0_312"
* linux version : Ubuntu 20.04.3 LTS

## Getting Started
### Setting up the local API Server
Follow the guidelines provided in [PetStore](https://github.com/swagger-api/swagger-petstore) to setup the local server. Once the setup is completed the server is started http://localhost:8080 which can be used to access the APIs
### Running Tests
Clone the project

```bash
  git clone https://github.com/likhithanr/Likhitha-NR.git
```

Go to the project directory

```bash
  cd Likhitha-NR
```

Install dependencies

```bash
  mvn clean install
```

Run the test

```bash
  mvn test
```

Once the tests are completed results are stored under /Results/<dd_mm_yyyy> folder
The result HTML file is stored as <test_dd_mm_yy_HH_MM_ss.html> and the log for respective
test is stored as Test-automation.log under /Results.
Example: regression_11_06_2022_18_55_24.html or smoke_11_06_2022_18_55_24.html based on the suite selected

## Levels of Automation
The API automation framwork is divided into two levels.
* Regression: Regression testing is responsible for the overall stability and functionality of the existing features.
   * Pros and cons
     * Regression testing ensures even with integration of new feature it remains intact. It helps in generate results constantly
     * Optimizing the test cases in regression testing sometimes is difficult.Scale of regression testing grows with every feature and become difficult to maintain.  
* Smoke: Smoke testing, also called build verification testing or build acceptance testing, is nonexhaustive software analysis that ascertains that the most crucial functions of a program work 
   * Pros and cons
     * Helps in finding critial bugs or blockers in early stages of testing.
     * It has smaller number of test cases which are not able to find other critcial issues.
### Running regression suite

Update pom.xml file, <suiteXmlFile></suiteXmlFile> to point to the regression suite

```bash
<build>
   <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.0.0-M6</version>
        <configuration>
          <suiteXmlFiles>
            <suiteXmlFile>regression.xml</suiteXmlFile>
          </suiteXmlFiles> 
        </configuration>
      </plugin>
    </plugins>
</build>
```

### Running smoke suite

Update pom.xml file, <suiteXmlFile></suiteXmlFile> to point to the smoke suite

```bash
<build>
   <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.0.0-M6</version>
        <configuration>
          <suiteXmlFiles>
            <suiteXmlFile>smoke.xml</suiteXmlFile>
          </suiteXmlFiles> 
        </configuration>
      </plugin>
    </plugins>
</build>
```
Once the suite is update. Run maven test to generate the results for the respective suite. The report is created under "Results" folder.
```bash
mvn test
```
## Framework Design
The framework is designed to automate the APIs for PetsStore. It is a mavenized java project. The libraries used in the frework are:
* RestAssured : Used to automate API
* TestNG: Used for execution and reporting
* Maven: To miantain the dependencies and build lifecyle
* Log4j: User for logging
* Extent report: Create the HTML result resport
* json-simple: Used to manipulate json objects
* poi- Used to read data from the excel sheet

The framework is designed using various object oriented patterns. The structure is divided into three major blockers:
* java: Holds all the common classes used across the framework
* test: Holds all the automated test cases classes
* resources: Holds all the common resources like .json, .properties etc 
##### Description
* Internally the packages are modularized based on individual features Ex: User, Store, Pet.
* The data required for test automations are maintained in excel sheet and is fetched based on individual test case.
* Singeton design pattern is implemented as part of utility class which contains common functionalities
* Running tests generates new report for each time with name containing the right timestamp.
* Each module request data is maintined as seperate JSON file which increases modularity, readability and is easier to maintain. 

### Proposed scenarios for PetStore automation
* Pet
 * Scenario 1:Adding pet to the store
   * Test case 1: Validate success 200 response code when valid request sent with all the required parameter
   * Test case 2: Validate error response 400/405 as invalid input when request sent without madandatory parameters like – name ,id,photoUrls
   * Test case 3: Send request with invalid http method Ex: instead of POST use PATCH then valdiate error reponse 405 says method not allowed
   * Test case 4: Error response should be received when invalid data types passed to request parameters.

 * Scenario 2: Updating existing pet
   * Test case 1: Update a Pet with name and categories details, success response should be displayed with updated name and categories details
 * Scenario 3 : Finding pet by status, End point : /pet/findByStatus
   * Test case 1: Pass valid status Ex: available,pending,sold in the request. Respose should have only pets with status passed.
   * Test case 2: Pass invalid status other than available,penidng and sold. Error message should be displayed
 * Scenario 4 : Finding pets using tags, End points : pet/findByTags/tags?tag=tag1&tag=tag2
   * Test case 1: Create pet with one tag with valid id and name.Try to find the pet using same tags, response should be displayed with pet which has same tags
   * Test case 2: Send a request with invalid tag, error message should be displayed in response
 * Scenario 5: Finding pet by id
   * Test case 1: Find pet by providing valid pet id,successfully pet details should be retrieved
   * Test case 2: Create a request with invalid pet id, error response as “Pet not found”
 * Scenario 6: Delete existing pet
   * Test case 1: Enter valid pet id and send delete request , pet should be successfully deleted
   * Test case 2: Enter invalid pet id and send delete request. Pet not found should be displayed
 * Scenario 7: Update pet using id
   * Test case 1: Update the existing pet id with new name and status , successfully pets name and status should be updated
   * Test case 2: Provide invalid pet id and add new name and status , error response should be displayed with no name and status updation
 * Scenario 8: Uploading pet image
   * Test case 1: add jpg formated image and try to upload image to existing pet,success response should be displayed
* Store 
 * Scenario 1: Create a pet store
   * Test case 1: Create a order with all the required parameter and send request, store should be created successfully
   * Test case 2: Create a order without madandatory request parameters, error response should be displayed  
 * Scenario 2: Get order details for a pet 
   * Test case 1: Enter valid order id and send GET request , order details for a pet should be displayed
   * Test case 2: Enter invalid order and send request, “Order not found” response should be displayed
 * Scenario 3: Delete order by id
   * Test case 1: Pass valid order id and send request , order should be deleted
   * Test case 2: Try to retrieve deleted order and order not found response should be displayed
   * Test case 3: Pass invalid order id and send request ,error response should be displayed
* User
 * Scenario 1: Create User
   * Test case 1: Create a user with all the request parameter with valid data, succesful response should be displayed
   * Test case 2: Create a list of user , pass two or more user request object in array and send request , success response should be displayed  
 * Scenario 2: User login/logout
   * Test case 1: Send request with valid user name, password parameters , logged in seession response shuld be displayed  
   * Test case 2: Send request with only uer name , error response should be displayed
   * Test case 3: Send request with only password , error response should be displayed
   * Test case 4: Logout, logged in user and validate success response
 * Scenario 3: Update user by name
   * Test case 1: Update user with valid name, Success response should be returned
   * Test case 2: Update user with invalid name, Error response should be returned.
 * Scenario : Delete user using user name
   * Test case 1: Delete existing user using valid user name, success response should be returned
   * Test case 2: Delete user using invalid user name , error response should be returned

### PetStore Swagger API Observations
 * Create and send a request without mandatory fields, receiving success response, expected to recieve error. Ex: Creating a pet without name
 * Create and send a request to delete with invalid id, receiving success response, expected to recieve error.
 * JSON validation is not woking as expected. Ex: Adding duplicate fields and invalid data type etc.

## Screenshots
#### Regression 
![App Screenshot](/Images/R1.png)
![App Screenshot](/Images/R2.png)
![App Screenshot](/Images/R3.png)
#### Smoke
![App Screenshot](/Images/S1.png)
![App Screenshot](/Images/S2.png)
![App Screenshot](/Images/S3.png)