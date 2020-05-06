<H1> Restful Web Service </H1> 

## Problem Statement
Your application should provide a RESTful web service using Spring, take a look at https://spring.io/guides/gs/rest-service/
1. Discuss, how Spring and a a REST API works and how these two technologies work together. 
2. Provide at least the two functions described below. Write documentation about what format you expect (JSON, XML, ...) and how this API is
working. 
3./store should take an image and additional meta data
4. /get should export a single image with meta-information or should export a list of images matching the pattern.

## Description

Main Class: **Task3Application.java** 

Subsidiary Classes: 
1. **FileController.java** 
2. **FileController.java** 
3. **StoreImageResponse.java** 
4. **NotFoundExceptionDb.java**



## Database creator

### Installing 

In order to get the application running you will need to: 
1. Git clone the following repository 
```
https://gitlab-sysprog.informatik.uni-bonn.de/ProgrammingLab2/winterterm-2019-20/group-03-descartes/tree/master/ProgrammingProject05/Task02
```
2.  Open Eclipse 
*  Direct the workspace to /group-03-descartes/ProgrammingProject05
*  Import existing maven project
*  Select the de.bit.pl02.task03 pom xml for importing the existing maven project. 
3.  Run Task3Application.java. in your IDE and go to the next section OR:
4.  Do a maven install.
5. After the application is installed you will see a task03-0.0.1-SNAPSHOT.jar in the /target folder
6. To execute the jar use the following command:
    * Move to the task03 directory using cd
    * type: java -jar target/task03-0.0.1-SNAPSHOT.jar

#### Getting Started

1. Add the chosen database path and database name to the config.json file. Database name should be a unique identifier (DatabaseId).
2. Check if the file upload settings for your collaborators suit your needs in application.properties file, to be found in scr/main/resources. Default values are:
* Threshold after which files are written to disk: 2KB
* Max file size: 200 MB
* Max Request Size: 215 MB
3. Terminate the application using crtl+c. Collaborators now cannot access the databases anymore. 


## Database Collaborator

No installation necessary. 

### Getting Started
1. Ensure you have access to the network/server that the application is running on (eg http://localhost:8080/ or the address of your server).
2. copy the url given to you by the database owner into either your terminal or web browser.

** Command line use (linux)**
Use curl commands followed by the url, as explained in the curl documentation: https://curl.haxx.se/docs/. In the examples below, entries in curly brackets are to be filled in by the user. <br>

*   <B>   /store </B>   : for storing the image and additional metadata. parameter file is required, the others are optional.  <br>
```
Example:
curl -F author: {author name} -F title: {title} -F link: {link} -F file:@{filename including extension} http://localhost:8080/{databaseId}/store
```   
*   <B>    /get </B>   : for getting image identifier and additional metadata. search can be done by author and/or title. Case sensitive. <br>
```
Examples: 
curl http://localhost:8080/{databaseId}/get?author={authorname}&title={title}
curl http://localhost:8080/{databaseId}/get?author={authorname}
curl http://localhost:8080/{databaseId}/get?title={title}
```
*   <B>    /get </B>   : for getting image in a .png format. search can be done by id.  <br>
```
Examples: 
http://localhost:8080/{databaseId}/get?id={image id}
```


**Web browser use**
In the examples below, entries in curly brackets are to be filled in by the user. <br>

*   <B>    /get </B>   : for getting image identifier and additional metadata. search can be done by author and/or title. Case sensitive. <br>
```
Examples: 
http://localhost:8080/{databaseId}/get?author={authorname}&title={title}
http://localhost:8080/{databaseId}/get?author={authorname}
http://localhost:8080/{databaseId}/get?title={title}
```
*   <B>    /get </B>   : for getting image in a .png format. search can be done by id.  <br>
```
Examples: 
http://localhost:8080/{databaseId}/get?id={image id}
```


## Dependency Management

### Prerequisites

The Java Version 1.8.0_231 is used for this application. Apache Maven Version 3.6.3  was installed from https://maven.apache.org/download.cgi. Therefore the binaries apache-maven-3.6.3-bin.zip were downloded. Spring Boot version 2.2.2 was installed from https://start.spring.io/.

The following dependencies were added to Maven:

**JUnit** 		Version 3.8.1   <br>
**SQLite-JDBC** 	Version 3.18.0   <br> 
**commons-cli**	Version 1.4   <br>
**commons-io**	Version 2.6   <br>
**commons-lang3** Version 3.4 <br>

The following dependencies were added to Spring: 
**Rest repositories** <br>
**Spring Data JPA** <br>

The following plugins were used: 
*  <B> Maven jar plugin  Version 1.4</B> 
*  <B> Maven shade plugin  Version 3.2.0</B> 






## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

 **Shreya Kapoor** <br>
**Sophia Krix** <br>
**Gemma van der Voort**<br>

## Acknowledgments

**Dr. Jens Dorpinghaus** <br>
**Dr. Sebastian Schaaf**<br>
