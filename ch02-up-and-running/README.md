Chapter 2. Up and Running
---

## How to run
``` bash
$ sbt up/run
```

## How to test

using cURL
``` bash
$ # Create an event named RHCP
$ curl -X POST localhost:5000/events/RHCP \
    -H "Content-Type: application/json" \
    -d '{ "tickets": 250 }'
$ # { "name": "RHCP", "tickets": 250 }

$ # Book 4 tickets
$ curl -X POST localhost:5000/events/RHCP/tickets \
    -H "Content-Type: application/json" \
    -d '{ "tickets": 4 }'
$ # { "entries": [{ "id": 1 }, { "id": 2 }, { "id": 3 }, { "id": 4 }], "event" :"RHCP" }

$ # Check remained events
$ curl localhost:5000/events
$ # { "events": [{ "name": "RHCP", "tickets": 246 }] }
```

or you can test via [httpie](https://github.com/httpie/httpie) for readability

``` bash
$ http post localhost:5000/events/RHCP tickets:=250         # Create an event named RHCP
$ http post localhost:5000/events/RHCP/tickets tickets:=4   # Book 4 tickets
$ http get localhost:5000/events                            # Check remained events
```


## TODO

akka-http has own test kit like akka-actor-testkit for akka-actor. we can try [Route TestKit](https://doc.akka.io/docs/akka-http/current/routing-dsl/testkit.html)
for reliability and automated test.
