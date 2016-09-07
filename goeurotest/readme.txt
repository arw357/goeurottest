Executable JAR
--------------
This is used to call http://api.goeuro.com/api/v2/position/suggest/en/  with a parameter 
and the result will be parsed into a CSV file with the following structure: 

id, name, type, latitude, longitude 

This is an executable JAR package. To run it, use the following command:

java -jar goeurotest.jar cityName 



