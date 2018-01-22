Spring readme


https://spring.io/guides/gs/maven/
https://spring.io/guides/gs/serving-web-content/
https://spring.io/guides/gs/spring-boot/
====================================================


You can find tomcat insside the training software folder
you can download maven from http://maven.apache.org/download.cgi

You must add java to your enviorment variables
test by entering 'javac -help' in cmd line


You must add maven to your enviorment variables
test by entering 'mnv -help' in cmd line
==========================================================


Inside you tomcat bin folder there is "server.xml"
Change the 'connector port' to 9090

Open your browser and go to http://localhost:9090/
if tomcat is running properly you shoudld see the tomcat cat default page


If you dont see the page go to the tomcat bin folder in the cmd prompt and run the following files
shutdown.bat
startup.bat
=================================================================

Inside you project use the cmd 'mvn compile'
to run the program use the cmd 'mvn spring-boot:run -Drun.jvmArguments='-Dserver.port=9090'


Open your browser and go to http://localhost:9090/greeting