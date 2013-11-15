# Fermentation Temperature Tracker #

## REST API ##

### Auth tokens ###

All write operations require an authorization token. The client should present this token through the `X-Auth-Token` header. A valid token is created by inserting a row in the `tokens` table.

### List brews ###

	GET /brews
	
	==>
	
	[ { "id": 1, "name": "My Brew" }, { "id": 2, "name": "Another Brew" } ]
    
### Create a new brew ###

	POST /brews
	X-Auth-Token: some-valid-token
	
	{ "name": "My New Brew" }
	
	==>
	
	201 Created
	Location: /brews/3


### Upload a new temperature reading ###

	POST /brews/{brewId}/temperatures
	X-Auth-Token: some-valid-token
	
	{ "timestamp": "2013-10-01T12:00:00", "temperature": 20 }
	
	==>
	
	202 Accepted

### List temperature readings for a given day ###

	GET /brews/{brewId}/temperatures?day=2013-10-01
	
	==>
	
	200 OK
	[
		{"timestamp": "...", "temperature": 20},
		{"timestamp": "...", "temperature": 20},
		// ...
	]
