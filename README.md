Android GPS Tracker
===================

GPS Tracker includes a simple, native Android app which transmits the device's current location to a Node.JS server. The server uses websockets via Socket.IO to push the devices coordinates to all connected clients. The coordinates are then
drawn as a polyline on a Google map to trace the devices location. 

USAGE
=====
The server is ready to be deployed to Heroku. Once deployed, change the POSTURL variable to the deployed Heroku instance URL and it's ready to roll!


