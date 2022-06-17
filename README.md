# PetStore API automation
## Overview
This is a rest assured API automation for PetStore and exploratory testing charter for MoneyFy

## Prerequisites 
* Make sure Java is installed and right environment variables are set in you system. If not please follow [Java Installation](https://www.oracle.com/java/technologies/downloads/)
* Check if Maven is installed in the system. If not plese follow [Maven Installation](https://maven.apache.org/install.html)

### IDE and Java version
* Visual Studio Code 1.63.2
* jdk version "1.8.0_312"

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

## Screenshots
#### Regression 
![App Screenshot](/Images/R1.png)
![App Screenshot](/Images/R2.png)
![App Screenshot](/Images/R3.png)
#### Smoke
![App Screenshot](/Images/S1.png)
![App Screenshot](/Images/S2.png)
![App Screenshot](/Images/S3.png)