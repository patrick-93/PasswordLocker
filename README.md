# Password Locker
This application allows users to share account information and provides 
basic logging. It includes different roles to provide different access
levels. It has basic logging about when and what was changed inside the
application.

### Disclaimer
This is a simple password manager that I built for a college class
and definitely shouldn't be used for storing real passwords without
at least throwing a reverse proxy in front of it to use HTTPS.


## Technologies used
* Spring Boot
  * Spring Boot MVC
  * Spring Boot Security
  * Spring Data JPA
* Thymeleaf templates   

## Requirements to run/build
* Latest JDK 8+
  (Tested and built on OpenJDK 11)

## How to Run
1. Download the project using *git clone* or as a zip file and unzip it
2. Open a terminal and run *cd ~/passwordlocker*
3. Run *./gradlew bootRun* to run the developer server
4. Run *./gradlew build* to build an executable jar file in the *build/libs* directory

## How it works
1. On first run you can access the web UI by going to ht<span>tp://localhost:8080.
This automatically creates and H2 database at ~/passwordlocker.mv.db
   
2. On first accessing the application you'll be prompted to create the admin account and enter a new password

3. Roles
    1. Admin
       * Full access to do everything
    2. Auditor
        * Access to view saved accounts but not their passwords
        * Access to view messages logged about when accounts and users were created / changed / deleted  
        * No access to add or change users
    3. User
        * Access to save new accounts, view passwords, change accounts
        * Access to view log messages
        * No access to add or change other users
    
4. From here the application is ready to be used