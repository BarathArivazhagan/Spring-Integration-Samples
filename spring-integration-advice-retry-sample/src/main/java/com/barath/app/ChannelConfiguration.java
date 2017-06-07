package com.barath.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ChannelConfiguration {
	
	@Bean
	public MessageChannel requestChannel(){
		return new DirectChannel();
	}
	
	@Bean
	public MessageChannel inputChannel(){
		return new DirectChannel();
	}
	
	
	@Bean
	public MessageChannel outputChannel(){
		return new DirectChannel();
	}
	
	@Bean
	public MessageChannel replyChannel(){
		return new DirectChannel();
	}
	
	@Bean
	public MessageChannel errorChannel(){
		return new DirectChannel();
	}

}
