<H1> Command Line Interface</H1> 

## Problem Statement
Check on this for a folder containing example images(PP5). These images are taken
from several open and free studies, articles or other sources. Some of them have explicit or implicit meta data annotations.<br> 
1. Check the meta data (EXIF, filename, ...) and write a concept which metadata needs to be passed as additional information. <br> 
2. Your application should take several command line parameters. --import should take a image file name. --print is an option that prints all meta information on the screen without doing anything more. Define – and doc- ument! – the parameter --meta which takes additional meta information. How should user pass these information? You can also split this up in several parameters. <br> 

## Description

Main Class: **CommandLineInterface.java** <br>
Subsidiary Class: **Metadata.java** <br>

## Installing

In order to get the application running you will need to: 
1. Git clone the following repository 
```
https://github.com/ShreyaKapoor18/biomed_images_metadb
```
2.  Open Eclipse 
*  Direct the workspace to biomed_images_db
*  Import existing maven project
*  Select the CLI (de.bit.pl02.task01) pom xml for importing the existing maven project. 
*  Do a maven install 
3. After the application is installed you will see a task01-0.0.1-SNAPSHOT.jar in the /target folder
  
### Getting Started
  1. To execute created the jar use the following command 
 ```
 java -cp ~/biomed_images_metadb/CommandLineInterface/CLI/target/task01-0.0.1-SNAPSHOT.jar  de.bit.pl02.pp5.task01.CommandLineInterface __\<options>__ __\<arguments>__ 
 ```
  Running the application with our pipeline
  1. Get the path of the folder that you want to be the directory, in our case <B> PP5 </B>
  2. Get the location of the jar file from your local machine it should be something like 
  ```
 ~/biomed_images_db/CommandLineInterface/CLI/target/task01-0.0.1-SNAPSHOT.jar
 ```

  3. Use it to insert the fileds in the Commands_sample.txt file provided in this repository 
  4. Go to terminal, the path provided works best if you make sure you are in the base directory of your computer. 
  5. Use the command:
  ```
  bash ~/biomed_images_metadb/CommandLineInterface/CLI/Commands_sample.txt 
  ```

 * This shall execute all the commands in the .txt file on the command line
    
    
## Application Output 

The results from the Commands in the Commands.txt file can be seen in the folder PP5 included in the repository. 
1. You will be able to see .meta files for all the image files in the directory you supplied (PP5 in our case). 
2. All the meta files will contain fields
    * Author 
    * Title 
    * Database 
    * Infographic 
3. For the files which already had a link in the metadata, these fields have just ben appended to them


## Command Line Interface

This application helps the user to create database objects and interact with them. The user can provide a directory on which a database object can be based. This directory should contain image files (.jpg, .jpeg or .png) and corresponding metadata files (.txt) carrying the same file name. The images are stored in the database along with the corresponding metadata.

**Options**:  
*   <B>    -d or --directory</B>   : for specifying the path to the directory containing the images <br>
```
Example: 
-d  ~/PP5
```
*   <B>    -ip or --inputfile</B>  : the name of the input file you want to deal with <br>  
```
Example: 
-ip BError1.PNG
```
*   <B>    -p or --print</B>       : if you want to print the meta information or not <br>  
```
    -p option if you want to print otherwise omit
```
*   <B>    -m or --meta</B>        : if you want to add the meta information <br> 
```
    -m option if you want to add meta information, omit otherwise
```
*   <B>    -im or --inputmeta</B>  : the values of metainformation you want to enter <br>
``` 
    -im Author,Title,Database_name,Infographic_number 
```
    * Author - String 
    * Title - String 
    * Database_name - String
    * Infographic_number - Integers in range 1 to 4 i.e. (1,2,3,4)
        1. Implies image of a cell/tissue
        2. Implies image of a biological cartoon
        3. Implies that the image is a graph
        4. Implies the type of the image doesn't fit into the above classification 

 Mandatory commands needed for the application to run <br>     
 *   <B>    -d or --directory</B> 
 *   <B>    -ip or --inputfile</B> 




## Dependency Management

### Prerequisites

The Java Version 1.8.0_231 is used for this application. Apache Maven Version 3.6.3  was installed from https://maven.apache.org/download.cgi. Therefore the binaries apache-maven-3.6.3-bin.zip were downloded.

The following dependencies were added to Maven:

**JUnit** 		Version 3.8.1   <br>
**SQLite-JDBC** 	Version 3.18.0   <br> 
**commons-cli**	Version 1.4   <br>
**commons-io**	Version 2.6   <br>
**commons-lang3** Version 3.4 <br>

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



