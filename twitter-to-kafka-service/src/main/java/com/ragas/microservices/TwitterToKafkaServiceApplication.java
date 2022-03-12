package com.ragas.microservices;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.ragas.microservices.configuration.TwitterToKafkaServiceConfiguration;
import com.ragas.microservices.runner.StreamRunner;


@SpringBootApplication
@ComponentScan(basePackages = "com.ragas.microservices")
public class TwitterToKafkaServiceApplication implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);

	private final TwitterToKafkaServiceConfiguration twitterToKafkaServiceConfiguration;

	private final StreamRunner twitterKafkaStreamRunner;

	public TwitterToKafkaServiceApplication(TwitterToKafkaServiceConfiguration twitterToKafkaServiceConfiguration,
			StreamRunner twitterKafkaStreamRunner) {
		this.twitterToKafkaServiceConfiguration = twitterToKafkaServiceConfiguration;
		this.twitterKafkaStreamRunner = twitterKafkaStreamRunner;
	}

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("App starts...");
		LOG.info(Arrays.toString(twitterToKafkaServiceConfiguration.getTwitterKeywords().toArray(new String[] {})));
		twitterKafkaStreamRunner.start();
	}
}
