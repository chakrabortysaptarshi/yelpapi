Technologies used - 

1) Java 
2) Jersey framework for REST API
3) Jackson library for JSON parsing
4) Maven to buid and deploy the project
5) Tomcat webserver

Steps to Build the project - 

1) Open command prompt and navigate to the home direcotry of the project, and type 
			mvn clean install

2) it should generate the executable in the target direcotry as 'outpatient.war'

Deployment process -

1) Start Tomcat server

2) Go to the webapps folder in the Tomcat server and place the 'outpatient.war' file in that direcotry.


Project API endpoints -

Question 1 - http://localhost:8080/outpatient/physicaltherapy/default?location=<locationname>

Question 2 - http://localhost:8080/outpatient/physicaltherapy/sorted?location=<locationname>

Type any one of these URL in the browser and the result will be displayed in a JSON format.

For both the APIs, if the request is successful, it will return 200 as response code and 400 as the response code for an unsuccessful request.

If the server is hosted in some other machine and port number is different than 8080, then the API or generic API structure is -

  http://<IP Address: Port Number>/outpatient/physicaltherapy/default?location=<locationname>
  
  http://<IP Address: Port Number>/outpatient/physicaltherapy/sorted?location=<locationname>
  
Sample Response - 

Question 1 - 

{"numberofpoints":91.0,"pointswithrating":91.0,"averagerating":4.159340659340659,"totalreviews":366.0}

Question 2 -

[{"name":"Leone's Chiropractic Accident & Injury Center","address":"7003 NW 11th PlGainesville, FL 32606","reviewCount":8,"rating":5},{"name":"West Family Chiropractic","address":"5010 W Newberry RdSte DGainesville, FL 32607","reviewCount":2,"rating":5},{"name":"Laura Mcchesney, DC","address":"2341 NW 41st StSte CNorth Florida Rehab and ChiropracticGainesville, FL 32606","reviewCount":1,"rating":5},{"name":"ACG Therapy Center","address":"4907 NW 43rd StGainesville, FL 32606","reviewCount":2,"rating":5},{"name":"Craig A. Sainz Chiropractic","address":"4631 NW 53rd AveSte 106Gainesville, FL 32653","reviewCount":1,"rating":5},{"name":"Kinetix Physical Therapy","address":"2839 SW 87th DrGainesville, FL 32608","reviewCount":1,"rating":5},{"name":"Shands Rehab Center At Magnolia Parke","address":"4740 NW 39th PlGainesville, FL 32606","reviewCount":2,"rating":4},{"name":"Clear Passage","address":"4421 NW 39th AveSte 2-2Gainesville, FL 32606","reviewCount":7,"rating":3},{"name":"Fit For Life Physical Therapy","address":"3919 W Newberry RdGainesville, FL 32607","reviewCount":2,"rating":3},{"name":"Gainesville Health Care Center","address":"4842 SW Archer RdGainesville, FL 32607","reviewCount":3,"rating":3},{"name":"Balanced Body Pilates","address":"108 NW 76th DrGainesville, FL 32607","reviewCount":3,"rating":3},{"name":"ReQuest Physical Therapy","address":"4820 W Newberry RdGainesville, FL 32607","reviewCount":1,"rating":1}]