# Car Service Management System

A Java console application that demonstrates a car repair shop workflow: registering clients, cars, and mechanics; creating and managing repair orders; and calculating order costs. The demo also exercises invalid operations and prints the resulting validation messages.

## Requirements

- JDK 21 or later
- Apache Maven 3.6 or later

Check that Java and Maven are available:

```shell
java -version
mvn -version
```

## Run the application

From the project root, compile the project and run its demo:

```shell
mvn compile
java -cp target/classes org.example.CarServiceDemo
```

The demo output includes a successful repair-order scenario followed by several negative scenarios.

Alternatively, open the project in IntelliJ IDEA, import it as a Maven project, and run `org.example.CarServiceDemo`.

## Run tests

Run the test suite with Maven:

```shell
mvn test
```