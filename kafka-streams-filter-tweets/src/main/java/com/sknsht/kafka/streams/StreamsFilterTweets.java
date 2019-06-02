package com.sknsht.kafka.streams;

import com.google.gson.JsonParser;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class StreamsFilterTweets {

    private static final Logger log = LoggerFactory.getLogger(StreamsFilterTweets.class.getName());

    public static final String TOPIC_NAME = "twitter_tweets";

    public static void main(String[] args) {
        // Create properties
        Properties properties = new Properties();
        properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams");
        properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());

        // Create a topology
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        // Input topic
        KStream<String, String> inputTopic = streamsBuilder.stream(TOPIC_NAME);
        KStream<String, String> filteredStream = inputTopic.filter(
                // Filter for tweets which have a user of over 10000 followers
                (k, jsonTweet) ->  extractUserFollowersInTweet(jsonTweet) > 10000
        );
        filteredStream.to("important_tweets");

        // Build the topology
        KafkaStreams kafkaStreams = new KafkaStreams( streamsBuilder.build(), properties);

        // Start
        kafkaStreams.start();
    }

    private static int extractUserFollowersInTweet(String tweetJson){
        try {
            return new JsonParser().parse(tweetJson)
                    .getAsJsonObject()
                    .get("user")
                    .getAsJsonObject()
                    .get("followers_count")
                    .getAsInt();
        }
        catch (NullPointerException e){
            log.warn("Skipping bad data: " + tweetJson);
            return 0;
        }
    }
}
