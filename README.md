# JMSChirp

*Welcome to JmsChirp. Your friendly JMS browser !*

As of now, JMSChirp caters to the following JMS vendors:  
* Apache ActiveMQ (5.8.0)
* Tibco EMS (4.4.1)


## Installation

Todo

### Proprietary vendor support
Proprietary vendor jars (like Tibco EMS jars) arent available in public. The user requiring proprietary vendor support need to  build the solution jar themselves.Outlined below are the steps to achieve this:
* Clone this repo to the local machine.
* cd to the cloned directory (jmschirp).
* Edit the *packaging* from *war* to *jar* in the pom.xml.   
* Build the jmschirp jar.
```
mvn clean package 
```

* Install the jmschirp jar into local Maven repo.
```
mvn install:install-file -Dfile=<path to jmschirp jar> -DgroupId=<groupId> -DartifactId=<artifactId> -Dversion=<proprietary version> -Dpackaging=jar
mvn install:install-file -Dfile=<path to jmschirp jar> -DgroupId=binitabharati.jmschirp -DartifactId=jmschirp -Dversion=1.0 -Dpackaging=jar
```
* Install the vendor specific jars into local Maven repo. E.g : In the case of Tibco EMS, 2 jars, namely, tibems.jar and tibjmsadmin.jar needs to be uploaded.

```
mvn install:install-file -Dfile=/x/y/tibems.jar -DgroupId=com.tibco -DartifactId=tibems -Dversion=4.4.1 -Dpackaging=jar
mvn install:install-file -Dfile=/x/y/tibjmsadmin.jar -DgroupId=com.tibco -DartifactId=tibjmsadmin -Dversion=4.4.1 -Dpackaging=jar
```
* cd to vendor specific cloned directory. (jmschirp/provider-tibcoems).
* Build vendor solution jar.
```
mvn clean package
```
* The above step will generate the vendor solution jar (jmschirp-provider-tibcoems-1.0.jar).
* This should be added to the classpath of the web container.


## Usage

Todo

## License

Copyright © 2013 Binita Bharati

Distributed under the MIT license. 
