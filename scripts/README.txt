Welcome to JMSChirp, your friendly JMS browser!

JMSChirp is a web based JMS browser. 
Currently, JMSChirp caters to the following JMS vendors:
 - Apache ActiveMQ (5.8.0)
 - Tibco EMS (7.0)  

JMSChirp is open source. The source code can be found here: https://github.com/BinitaBharati/jmschirp

For bug reports and enhancement requests, please raise issues at the above Github repository. 


Usage:
======

This bundle contains a jar file that can be run from the command line. Running it will start a web server at the specified port or at port 3000 if unspecified.

Requirement: Java 6 or higher should be present.


To start JMSChirp, execute the below command (square brackets imply optional argument):


On Unix like systems:
---------------------
java -classpath .:<JMS vendor jar files>:[<JMS msg class jar files>:]jmschirp-0.1.2-standalone.jar jmschirp.main [<port>]

Note: Do not include tilde(~) character in the classpath, use absolute paths instead.

Example:
$ cd jmschirp-0.1.0
$ java -Xms2g -Xmx2g -classpath .:/activemq-lib/activemq-client-5.8.0.jar:/activemq-lib/activemq-all-5.8.0.jar:/activemq-lib/activemq-broker-5.8.0.jar:/activemq-lib/activemq-amqp-5.8.0.jar:/activemq-lib/activemq-openwire-legacy-5.8.0.jar:jmschirp-0.1.2-standalone.jar jmschirp.main 

On Windows:
-----------
java -Xms2g -Xmx2g -classpath .;<JMS vendor jar files>;[<JMS msg class jar files>;]jmschirp-0.1.0-standalone.jar jmschirp.main [<port>]


License:
========

Copyright © 2013, Binita Bharati

JMSChirp is distributed under the MIT license. You can obtain a copy of this license at http://opensource.org/licenses/MIT
