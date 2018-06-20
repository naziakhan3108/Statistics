A restful API application for statistics of transactions.Calculate realtime statistics from the last 60 secs. 
There are two APIs, one of them is called every time a transaction is made. It is also the sole input of this rest API.
The other one returns the statistic based of the transactions of the last 60 seconds.
Request
http
POST /transactions 
Body
{
	"amount": 12.3,
	"timestamp": 1478192204000
}


Response
201 - in case of success
204 - if transaction is older than 60 seconds

Get/statistics
Returns:
{
  "sum":1000.0,
  "avg":100.0,
  "max":200.0,
  "min":50.0,
  "count":10,
 }
