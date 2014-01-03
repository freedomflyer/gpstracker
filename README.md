Android GPS Tracker
===================

GPS Tracker includes a simple, native Android app which transmits the device's current location to a Node.JS server. The server uses websockets via Socket.IO to push the devices coordinates to all connected clients. The coordinates are then
drawn as a polyline on a Google map to trace the devices location. 

## Usage

1) Deploy server to Heroku.
2) Change the POSTURL variable in MainActivity.java to the deployed Heroku instance URL (http://[ your url here]/coord) and it's ready to roll!


