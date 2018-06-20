A restful API application for statistics of transactions.
Calculate realtime statistics from the last 60 secs.
Request
http
POST /transactions HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
	"amount": 12.3,
	"timestamp": 1478192204000
}


##### Response
201 - in case of success
204 - if transaction is older than 60 seconds
