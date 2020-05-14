# Biomedical Images MetaDatabase
## About
A tool to build an image database with searchable metadata for biomedical applications. 

<img src="https://github.com/ShreyaKapoor18/ProgrammingProject05/blob/master/pipline.png" width="70%">

This project was implemented for the course Programming Lab II, in the winter semester of 2019-2020 as a 
part of Msc Life Science Informatics Curriculum. 

## Host Institution 
Bonn Aachen International Center for Information Technology <br> 

## Running the Application
Run the following directories in a Sequential order: <br>
1. **CommandLineInterface** <br>
      - The application should take several command line parameters. Example: --import takes an image file name. --print is an option that prints all meta information on the screen without doing anything more. <br>
2. **SQLite** <br>
      - This application helps the user to create database objects and interact with them. The user can provide a directory on which a database object can be based. This directory should contain image files (.jpg, .jpeg or .png) and corresponding metadata files (.txt) carrying the same file name. The images are stored in the database along with the corresponding metadata. <br>
3. **RestfulAPI** <br>
     - This application provides a RESTful web service using Spring. <br>
## Note
Each directory folder contains a readme that describes what that part of the application does in more detail.


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

 **Shreya Kapoor** <br>
**Sophia Krix** <br>
**Gemma van der Voort**<br>

## Acknowledgments

**Dr. Jens Dorpinghaus** <br>
**Dr. Sebastian Schaaf**<br>
