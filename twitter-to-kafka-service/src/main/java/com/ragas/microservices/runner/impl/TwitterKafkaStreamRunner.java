package com.ragas.microservices.runner.impl;

import java.util.Arrays;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.ragas.microservices.configuration.TwitterToKafkaServiceConfiguration;
import com.ragas.microservices.listener.TwitterKafkaStatusListener;
import com.ragas.microservices.runner.StreamRunner;

import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@Component
@ConditionalOnProperty(prefix = "twitter-to-kafka-service", name = "enable-mock-tweets", havingValue = "false", matchIfMissing = true)
public class TwitterKafkaStreamRunner implements StreamRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStreamRunner.class);

	private final TwitterToKafkaServiceConfiguration twitterToKafkaServiceConfiguration;

	private final TwitterKafkaStatusListener twitterKafkaStatusListener;

	private TwitterStream twitterStream;

	public TwitterKafkaStreamRunner(TwitterToKafkaServiceConfiguration twitterToKafkaServiceConfiguration,
			TwitterKafkaStatusListener twitterKafkaStatusListener) {
		this.twitterToKafkaServiceConfiguration = twitterToKafkaServiceConfiguration;
		this.twitterKafkaStatusListener = twitterKafkaStatusListener;

	}

	@Override
	public void start() throws TwitterException {
		LOG.info("Starting Twitter Stream runner");
		twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(twitterKafkaStatusListener);
		filterTwitterStream();
	}

	@PreDestroy
	public void shutdown() {
		if (twitterStream != null) {
			LOG.info("Shutting down Twitter Stream!!!");
			twitterStream.shutdown();
		}
	}

	private void filterTwitterStream() {
		String[] keywords = twitterToKafkaServiceConfiguration.getTwitterKeywords().toArray(new String[] {});
		FilterQuery filterQuery = new FilterQuery(keywords);
		twitterStream.filter(filterQuery);
		LOG.info("Started filtering twitter stream for keywords:{}", Arrays.toString(keywords));
	}

}
