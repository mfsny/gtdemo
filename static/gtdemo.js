// set map view to UK
//var map = L.map('map').setView([53.3, -2.2],6); //UK
//var map = L.map('map').setView([51.5, 0],10); //London
var map = L.map('map').setView([40, -75],8); //Philadelphia
/*********************************************************************/
/* from Leaflet tutorial quick start guide */
L.tileLayer('http://{s}.tiles.mapbox.com/v3/examples.map-dev-de/{z}/{x}/{y}.png', {
attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors'
 +', <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>'
 +', Imagery © <a href="http://mapbox.com">Mapbox</a>',
maxZoom: 18
}).addTo(map);
var fg = L.featureGroup().addTo(map);

/* From Geotrellis labs1-demo
L.tileLayer('http://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x}.jpg', {
attribution: 'Map data &copy; ESRI',
maxZoom: 18,
format: 'image/jpeg'
}).addTo(map);
*/
/*********************************************************************/
// add overlay
var urlhome = location.pathname.match(/(.*)\/(.*)$/)[1]
document.getElementById("debug").innerHTML = "URL home ='" + urlhome + "'";
//urlhome = 'http://localhost:8777';
//document.getElementById("debug2").innerHTML = "URL home ='" + urlhome + "'";
var d = new L.TileLayer.WMS(urlhome + "/get/map",{
    layers: 'default',
    //style: spread,
    format: 'image/png',
    transparent: true,
    attribution: 'Azavea'}
    );
d.addTo(map);

