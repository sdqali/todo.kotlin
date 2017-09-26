# Todo-Backend

Example implementation for [Todo-Backend](http://www.todobackend.com/) in Kotlin using SpringBoot.

## Usage
### Building
```
./gradlew clean build
```
### Running
```
java -jar build/libs/todo.kotlin-0.1.0.jar
```

When run anywhere other than localhost, the URL should be made available to the application using the ENV variable ROOT_URL:
```
env ROOT_URL=http://todo.example.com:13445 java -jar build/libs/todo.kotlin-0.1.0.jar
```
This defaults to `http://localhost:8080` if the ENV variable is not specified.