# CS-Select

## Developer setup
- Install tomcat-9 from the official [Tomcat Website](http://tomcat.apache.org/) 
- In your Tomcat installation folder edit `conf/tomcat-users.xml`.
    Add the following at the end of the file 
    ```xml
    <tomcat-users>
        <role rolename="manager-gui"/>
        <role rolename="manager-script"/>
        <user username="admin" password="password" roles="manager-gui,manager-script" />
    </tomcat-users>
    ```
- Start Tomcat with either `bin/startup.sh` or `bin/startup.bat` (in Tomcat folder) depending on your OS.
- Clone the repository to your local machine
- Put the default configuration file into the `conf/Catalina/cs_select directory` inside your tomcat home directory and edit it as you see fit. If the directory doesn't exist, create the folders.
- Deploy the project from the command line by typing `mvn tomcat7:deploy` whilst in the repository folder
- Access the server from a browser under the address [localhost:8080/CS-Select/]() (If you used the default settings in
 Tomcat)
 
 # Known issues
 - Only works with MySql Version 5.7.20
 - pattern loading doesn't load termination value
