# CS-Select
[![Build Status](https://travis-ci.com/cs-select-team/CS-Select.svg?branch=master)](https://travis-ci.com/cs-select-team/CS-Select)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/95d15dc25a58451792797e54c2d60765)](https://www.codacy.com/app/cs-select-team/CS-Select?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cs-select-team/CS-Select&amp;utm_campaign=Badge_Grade)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
## Installation
### Manual
1. Install Tomcat-9 from the official [Tomcat Website](http://tomcat.apache.org/)
1. Install MySQL-Server version 5.7 from the official [MySQL Website](https://dev.mysql.com/downloads/mysql/5.7.html#downloads)
1. (Optional, but recommended) Install MySQL-Workbench from the official [MySQL Website](https://dev.mysql.com/downloads/workbench/)
1. Install [Maven](https://maven.apache.org/) for your system
1. Edit values in example-conf.properties and move the new file to `$CATALINA_HOME/conf/Catalina/cs_select/config.properties`
   (`$CATALINA_HOME` is the Tomcat home directory)
1. (Optional) If you don't want to use the default logging settings, refer to the [Logger configuration section](#logger-configuration)
1. The MySQL-user has to be allowed to create new databases. If you don't want to use a default MySQL-user, the user has to be
   created (e.g. with MySQL-Workbench) and the config edited accordingly
1. Execute `mvn package`
1. Copy `target/CS-Select.war` to `$CATALINA_HOME/webapps/`
1. Start Tomcat by executing `$CATALINA_HOME/bin/startup.sh` on Linux or `$CATALINA_HOME/bin/startup.bat` on Windows
1. Start the ML-Server on the hostname specified in the config
1. Go to [http://localhost:8080/CS-Select/](http://localhost:8080/CS-Select/) to use CS:Select
1. To register a new organiser they need to know the masterpassword which is set in the config.properties

### Installation under Debian 
1. Clone this git repository `git clone https://github.com/bendixsonnenberg/CS-Select.git` (for the following steps we will assume that the repository is in your home directory)
1. Install Maven `sudo apt install maven` (this is only needed during deployment and can be uninstalled after the setup is complete)
1. Go into the CS-Select repository `cd CS-Select`
1. Package the project `mvn package`
1. Set up MySQL
    1. Install MySQL version 5.7 `sudo apt update && sudo apt upgrade && sudo apt install mysql-server-5.7`
    1. Start the MySQL setup and use the default values `sudo mysql_secure_installation`
    1. Add a new user for CS:Select
        1. Log in to MySQL by using `sudo mysql -u root -p`. You will be prompted for the root password you set during the setup
        1. Create a new MySQL user `CREATE USER 'CSSELECT'@'localhost' IDENTIFIED BY '<custom password>';`
        1. Grant privileges to create new databases and use existing ones to the CSSELECT user `GRANT ALL PRIVILEGES ON *.* TO 'CSSELECT'@'localhost' WITH GRANT OPTION;`
        1. Exit MySQL `exit;`
    1. Make sure that MySQL is started on system boot `sudo systemctl enable mysql.service`
1. Set up Tomcat
    1. Go to your home directory (or wherever you want to install Tomcat) `cd ~`
    1. Download tomcat version 9 `wget https://www-eu.apache.org/dist/tomcat/tomcat-9/v9.0.16/bin/apache-tomcat-9.0.16.tar.gz`
    1. Unpack the file `tar -xzf apache-tomcat-9.0.16.tar.gz`
    1. Go to Tomcat dir `cd apache-tomcat-9.0.16`
    1. Copy the CS-Select project war file to Tomcat webapps `cp ~/CS-Select/target/CS-Select.war webapps/`
    1. Copy the example-config file `mkdir -p conf/Catalina/cs_select/ && cp ~/CS-Select/example-config.properties conf/Catalina/cs_select/config.properties`
    1. Open the config file `vim conf/Catalina/cs_select/config.properties`
        1. Edit the `database.username`, `database.password` to fit the created user from the MySQL setup
        1. Edit `csselecturl` (only change the localhost path unless you know what you are doing)
        1. Change `organiserpassword` to a password that your organisers will use to authenticate themselves when creating a new account
        1. Set `homedirectory` to a path where you have edit rights and where you do not care about the contents of that path (best way is to create a new directory)
1. Set up the ML-Server
    1. Follow the setup instructions in the ML-Server repository and host it on localhost on port `8000`
1. Start the Tomcat server by running `~/apache-tomcat-9.0.16/bin/startup.sh` 
### Docker
1. Refer to [https://github.com/bendixsonnenberg/CS-Select-Docker](https://github.com/bendixsonnenberg/CS-Select-Docker)

## Developer setup
If you want to expand CS:Select with your own additions, use the following setup explanation (or just use the docker-repository).
- Follow steps 1-7 from the manual setup
- In your Tomcat installation folder, edit `conf/tomcat-users.xml`.
    Add the following before the `</tomcat-users>` line at the end of the file
    ``` xml
        <role rolename="manager-gui"/>
        <role rolename="manager-script"/>
        <user username="admin" password="password" roles="manager-gui,manager-script" />
    ```
- Start Tomcat with either `bin/startup.sh` or `bin/startup.bat` (in Tomcat folder) depending on your OS
- Start the ML-Server on the hostname and port specified in the config
- Deploy the project from the command line by typing `mvn tomcat7:redeploy` whilst in the repository folder
- Access the server from a browser under the address [http://localhost:8080/CS-Select/](http://localhost:8080/CS-Select/) (or the specified url in the config)
 
 ## How to develop
 CS:Select is realised as a simple maven project, so it can be used in any supported IDE. However, we recommend
 developing in IntelliJ Idea, as we integrated the JetBrains language plugin which allows for easy language injection
 via Annotations to allow for syntax highlighting and auto-completion of different languages inside your java code
 (e.g. SQL).
 
 ## JUnit
 The test cases in the `mysql` and `mlserver` package only work with a correctly set up MySQL- or ML-Server respectively.
 All other test cases work without external resources
 
 ## Configuration Values
 - organiserpassword: The password an organiser has to supply to be able to register a new account
 - mlserverurl: The URL the ML-Server is available under
 - homedirectory: The directory path in which CS:Select will save downloaded featuresets from the ML-Server
 - csselecturl: The URL CS:Select will be accessible under (used e.g. in invitation mails)
 - timezone: The timezone your server uses
 - database.hostname: The hostname of your database server
 - database.port: The port of your database server
 - database.username: The username of your database user
 - database.password: The password of your database user
 - email.hostname: The hostname of the email server CS:Select uses for sending invitation mails
 - email.port: The port of the email server
 - email.address: The email address to use for sending invitations
 - email.password: The password to the email account
 
 ## Logger Configuration
 The Logger used by CS:Select is the [tinylog-framework](https://tinylog.org/). Tinylog can be configured in a multitude
 of ways through a properties file. If you don't want to use our default way of logging (to console and log.txt in the
 CS:Select home directory), you can create a `logger.properties` file in the `conf/Catalina/cs_select` directory and fill
 it with values as described in detail in the [tinylog documentation](https://tinylog.org/configuration#settings).
 
 ## Localisation
 The local Strings are stored in [src/main/resources/locale/Locale.properties](src/main/resources/locale/Locale.properties) and the 
 respective language files.
 
 ### Add new languages 
 1. Add new Locale file to the resource path mentioned above. Name it accordingly (`Locale_xx.properties`). If the ML-Server does not supply the feature description in this language (same country code), the english version will be used
 2. Add new option to [setting.js](src/main/webapp/src/js/settings.js) (data.languageoptions array has to be extended)
 3. (Optional) Add new constant to `com/csselect/utils/Languages.java` instead of directly using the language code to allow for easy refactoring of language codes in the java code
 
 - For non-ascii characters you will have to use the [native2ascii](https://docs.oracle.com/javase/7/docs/technotes/tools/windows/native2ascii.html) tool by Oracle, or an alternative that converts UTF-8 to ISO-8859-1 standard.
 It converts characters like `ü` to the `\uxxxx` notation.
 
 ## Database
 If you wish to use a different database implementation instead of our MySQL one, you will need to follow these steps:
 1. Implement all interfaces in the `com.csselect.database` package according to their documentation for your database of choice
 1. Edit `com.csselect.inject.CSSelectModule` to use your implementation of the database package or create your own extension of the
    `com.csselect.inject.Module` abstract class and use it in the `com.csselect.inject.Injector` class instead of our default implementation
 
 
 # Known issues
 
 ## Newer MySQL versions
 This application is developed and tested on MySQL version 5.7, using newer version might result in unknown bugs.
 
 ## Potential out-of-memory-error
 When creating multiple games in a row an out-of-memory-error might occur and the dataset will not be written to
 the database correctly when using Tomcat. This happens if the application is redeployed multiple times, as Tomcat has a problem
 with memory leaking when often redeploying. Restarting Tomcat fixes this. Though this should not happen in productive use, 
 as you won't be redeploying your application as often as when developing. You might as well try using CS:Select with a
 different web-container application because as far as it is known to us, our application doesn't use any Tomcat specific code.
