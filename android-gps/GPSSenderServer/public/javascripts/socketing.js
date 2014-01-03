var socket = io.connect('http://damp-inlet-1961.herokuapp.com/');
socket.on('coords', function (data) {
    console.log(data);
    addLatLng(data.latitude, data.longitude);
});

// This example creates an interactive map which constructs a
// polyline based on user clicks. Note that the polyline only appears
// once its path property contains two LatLng coordinates.

var poly;
var map;
var trailLength = 30;
var totDist = 0.0;

// Initiliazes map with centering, zoom, and polygon drawing color/size
function initialize() {
  var mapOptions = {
    zoom: 16,
    // Center map on Springville, Utah
    center: new google.maps.LatLng(40.16, -111.584)
  };

  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

  var polyOptions = {
    strokeColor: '#000000',
    strokeOpacity: 1.0,
    strokeWeight: 3
  };
  poly = new google.maps.Polyline(polyOptions);
  poly.setMap(map);
}


var currLatLng = {};
// Adds a lat/lng pair to the map.
function addLatLng(lat, lng) {
  var newLatLng = new google.maps.LatLng(lat, lng);
  if(currLatLng.lat)
  	totDist += getDistanceFromLatLonInKm(currLatLng.lat, currLatLng.lng, newLatLng.lat(), newLatLng.lng());
  currLatLng.lat = newLatLng.lat();
  currLatLng.lng = newLatLng.lng();

  var path = poly.getPath();

  // Because path is an MVCArray, we can simply append a new coordinate
  // and it will automatically appear.
  path.push(newLatLng);

  // Add a new marker at the new plotted point on the polyline.
  console.log(totDist * 1000);

  trimPath(path);

}

// Avoids long, windy maps by limiting path length to the 10 most recent points
function trimPath(path){
  if(path.length > trailLength){
  	path.removeAt(0);
  }
}

google.maps.event.addDomListener(window, 'load', initialize);



// Haversine Formula for distance calculations
// http://stackoverflow.com/questions/27928/how-do-i-calculate-distance-between-two-latitude-longitude-points
function getDistanceFromLatLonInKm(lat1,lon1,lat2,lon2) {
  var R = 6371; // Radius of the earth in km
  var dLat = deg2rad(lat2-lat1);  // deg2rad below
  var dLon = deg2rad(lon2-lon1); 
  var a = 
    Math.sin(dLat/2) * Math.sin(dLat/2) +
    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
    Math.sin(dLon/2) * Math.sin(dLon/2)
    ; 
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
  var d = R * c; // Distance in km
  return d;
}
function deg2rad(deg) {
  return deg * (Math.PI/180)
}