[![Build Status](https://travis-ci.org/sahbi-ktifa/dailyheroes.svg?branch=develop)](https://travis-ci.org/sahbi-ktifa/dailyheroes)

# Daily Heroes: Quests for the house !

## How to run it ?

### Prerequisites
* JDK 8
* MongoDB 3.x

Use `java -jar build/libs/dailyheroes-0.0.1-SNAPSHOT.jar` to start or `gradlew bootRun`

## Deploy to Pivotal CloudFoundry

cf login
cf push dailyheroes(-dev) -p build/libs/***.jar