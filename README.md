# Likhitha-NR
## Overview
This is a rest assured API automation for PetStore and exploratory testing charter for MoneyFy

## Getting Started

### Setting up the local API Server
Follow the guidelines provided in [PetStore](https://github.com/swagger-api/swagger-petstore) to setup the local server. Once the setup is completed the server is started http://localhost:8080 which can be used to access the APIs
### Running Tests
Clone the project

```bash
  git clone https://github.com/likhithanr/Likhi

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
Example: test_11_06_2022_18_55_24.html

## Screenshots

![App Screenshot](/Images/Report1.png)
![App Screenshot](/Images/Report2.png)
![App Screenshot](/Images/Report3.png)