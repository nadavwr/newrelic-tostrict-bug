- make sure to provide license (e.g. `export NEW_RELIC_LICENSE_KEY=...` prior to sbt being run)
- run using `sbt run`
- once up and running, `curl http://localhost:8000`
- note stdout output, it should report "transaction id" (in reality `getTransaction().hashCode()`) 4 times:
  - before toStrictEntity handles the request
  - after toStrictEntity handles the request
  - before toStrictEntity handles the response
  - after toStrictEntity handles the response
- since akka-http 10.1.5 and above we lose transaction id immediately following `toStrictEntity` directive. Of the 4 checks above, only the first report yields a unique transaction id.
- tested against NewRelic Agent 5.10 and 6.0.0
- tested against Akka HTTP 10.1.4, 10.1.9, 10.1.11
