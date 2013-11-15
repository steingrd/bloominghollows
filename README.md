
Home Brew Fermentation Temperature Tracker
==========================================

REST API
--------

List brews
~~~~~~~~~~

    GET /brews
    
    [ { "id": 1, "name": "My Brew" }, { "id": 2, "name": "Another Brew" } ]
    
    
Create a new brew
~~~~~~~~~~~~~~~~~

	POST /brews
	{ "name": "My New Brew" }
	
	201 Created
	Location: /brews/3


Upload a new temperature reading
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	POST /temperatures
	{ "timestamp": "2013-10-01T12:00:00", "temperature": 20 }
	
	202 Accepted


List temperature readings for a given day
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	GET /temperatures?day=2013-10-01
	
	200 OK
	[
		{"timestamp": "...", "temperature": 20},
		{"timestamp": "...", "temperature": 20},
		// ...
	]