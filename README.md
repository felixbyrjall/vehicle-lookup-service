this service does a vehicle lookup through statensvegvesen.no public api<br>
this means that the user can look up any vehicle registered at statensvegvesen<br>

<p>usage</p>
mvn spring-boot:run<br>
open postman or use curl<br>
send a request to http://localhost:8080/vehicle/<br>
followed by any license plate, for example 'a1'<br>
http://localhost:8080/vehicle/a1<br>
this will return 'all' available and relevant info about the vehicle<br>
