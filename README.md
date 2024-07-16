# portfolio-project-Simple-Banking-System-


This project is a simple banking system built with Spring Boot. It allows users to manage clients, accounts, payments, and purchases, and to generate financial reports.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher
- Oracle Database

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/YevheniiSatov/portfolio-project-Simple-Banking-System.git
   cd portfolio-project-Simple-Banking-System

2. Set up your Oracle database on AWS RDS:
  Go to the AWS Management Console.
  Navigate to RDS and create a new Oracle database instance.
  Make note of the endpoint, username, and password for your database instance.
3. Configure the database connection in the application.properties file with your own AWS RDS instance details:
  spring.datasource.url=jdbc:oracle:thin:@<your-aws-rds-endpoint>:1521:ORCL
  spring.datasource.username=<your-db-username>
  spring.datasource.password=<your-db-password>
  spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
  spring.jpa.show-sql=true
  spring.application.name=bank-application
  server.port=8080
  spring.mvc.view.prefix=/templates/
  spring.mvc.view.suffix=.html
4.  Build the project:
   ./gradlew build

3 Run the application:

   ./gradlew bootRun

4 Open in the browser:

   http://localhost:8080

Usage

    Create client accounts.
    Manage client accounts and transactions.
    Generate and view reports.

