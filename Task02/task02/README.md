<H1> Task 02 </H1> 


## SQLite Database

This application helps the user to create database objects and interact with them. The user can provide a directory on which a database object can be based. This directory should contain image files (.jpg, .jpeg or .png) and corresponding metadata files (.txt) carrying the same file name. The images are stored in the database along with the corresponding metadata.

**Columns**:   


* AUHTOR		(the author name)   
* TITLE		(the title of the image)   
* LINK	(the URL related to the image)   
* PICTURE  	(the image stored as a byte array)   

It is possible to print the values of the created table. If the user only wants the metadata of a specific sample, then the metadata can be retrieved by specifying either the author or the title and the metadata will be saved as a .txt file.

## Getting Started

### Description
Main Class: **CommandLineInterface.Java** <br> 
Subsidiary Classes: **Image.java**, **Database.Java**, **MetaDataAPI.java**  <br> 

### Installing


How to set up an working environment for this application:
###  Installing SQLite

Before trying to install, please check whether the installation has already been made.

```
$ sqlite3
```

If SQLite is already installed, you should get the following message:

```
SQLite version 3.29.0 2019-07-10 17:32:03
Enter ".help" for usage hints.
Connected to a transient in-memory database.
Use ".open FILENAME" to reopen on a persistent database.
sqlite>
```

If not, please follow these instructions to install SQLite.

Go to the SQLite webpage (https://www.sqlite.org/download.html) and download the most recent version of **SQLite-autoconf-*.tar.gz**.
Type the following commands into the command line to unzip and install the package.

```
$ tar xvfz SQLite-autoconf-*.tar.gz

$ cd SQLite-autoconf-*

$ ./configure --prefix = /usr/local

$ make

$ make install
```

Confirm successful installation by typing again

```
$ sqlite3
```
### Running the Application
In order to get the application running you will need to download the repository from gitlab from the following link.

```
https://gitlab-sysprog.informatik.uni-bonn.de/ProgrammingLab2/winterterm-2019-20/group-03-descartes/tree/master/ProgrammingProject05
```

1. Git clone the repository
2. Open Eclipse
    *  Direct the workspace to /group-03-descartes/ProgrammingProject05
    *  Import existing maven project
    *  Select the de.bit.pl02.task02 pom xml for importing the existing maven project.
    *  Do a maven install
3. After the application is installed you will see a task02-0.0.1-SNAPSHOT.jar in the /target folder
4. To execute the jar use the following command (example in Commands file)
    *  java -cp <Path to Programming Project>/ProgrammingProject05/Task02/task02/target/task02-0.0.1-SNAPSHOT.jar  de.bit.pl02.pp5.task02.CommandLineInterface <options> <arguments>
    *  or use the Commands file to run from the base folder from your computer 
         * bash /group-03-descartes/ProgrammingProject05/Task02/task02 Commands.txt
### Accessing a database

For the program to be running you need to specify the name of the database you want to make or the database you want to see that you have already created. Therefore use the option


**-n** or **--name** ***(Mandatory Option)*** : Enter the path of the database and the name of the database you want to make/see. Example(trialyy.db) database from PP5 repository in the previous task has been made already. 

```
-n your_path,your_database_name 
```

Provide a directory that contains image files (.png, .jpeg, .jpg) and corresponding metadata files (.txt). In our case it is the PP5 directory created in task01.The metadata files have to have the following information:
```
AUTHOR: xxx
TITLE: xxx
https://www.example.com (only for files that contain a link)
```
Therefore use the option


**-d** or **--directory**: Enter the file directory from which you want to store the images
```
-d your_directory_path
```

### Querying a database

Now you can choose how you want to query the database. You can either retrieve metadata information or the image as a .jpg file.


#### **Retrieval of metadata information**

You can give information about the author or the title to retrieve additional metadata. 
To query by the author use the option


**-gma** or **--getMetabyAuthor**: Enter the name of the author of which you want to retrieve the metadata and the outputpath where to save it at
```
-gma author_name,output_path
```
To query by the title use the option


**-gmt** or **--getMetabyTitle**: Enter the name of the title of which you want to retrieve the metadata and the outputpath where to save it at
```
-gmt title_name,output_path
```


#### **Retrieval of images**
You can give information about the author or title to retrieve the images.
To query by the author use the option


**-gia** or **--getImagebyAuthor**: Enter the name of the author from which you want the image and the outputpath where to save it at
```
-gia author_name,output_path
```
To query by the title use the option


**-git** or **--getImagebyTitle**: Enter the name of the title from which you want the image and the outputpath where to save it at
```
-git title_name,output_path
```

### Prerequisites

The Java Version: 1.8.0_231 is used for this application. Apache Maven Version 3.6.3  was installed from https://maven.apache.org/download.cgi. Therefore the binaries apache-maven-3.6.3-bin.zip were downloded.

The following dependencies were added to Maven:

**SQLite-JDBC** 	Version 3.18.0   
**commons-cli**	Version 1.4   
**commons-io**	Version 2.6   
**commons-lang3** Version 3.4

by adding the according dependencies to the pom.xml file:

```
<dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.18.0</version>
</dependency>

<dependency>
	<groupId>commons-cli</groupId>
	<artifactId>commons-cli</artifactId>
	<version>1.4</version>
</dependency>

<dependency>
    	<groupId>commons-io</groupId>
    	<artifactId>commons-io</artifactId>
    	<version>2.6</version>
 </dependency>

<dependency>
    	<groupId>org.apache.commons</groupId>
    	<artifactId>commons-lang3</artifactId>
  	<version>3.4</version>
</dependency>
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

**Shreya Kapoor**   
**Sophia Krix**   
**Gemma van der Voort**   
