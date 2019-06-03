## Twitter Producer

The Twitter Producer gets data from Twitter based on some keywords and put them in a Kafka topic

* Twitter Java Client: https://github.com/twitter/hbc

* Twitter API Credentials: https://developer.twitter.com/

## ElasticSearch Consumer

The ElasticSearch Consumer gets data from twitter topic and inserts it into ElasticSearch

* ElasticSearch Java Client: https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.4/java-rest-high.html

* ElasticSearch setup:
  - https://www.elastic.co/guide/en/elasticsearch/reference/current/setup.html
  - OR https://bonsai.io/

## Kafka Streams Filter Tweets

A simple illustration of the use of Kafka Streams on the example of filtering tweets by number of followers
