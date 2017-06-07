package com.barath.app;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.handler.advice.RequestHandlerRetryAdvice;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.client.ResponseErrorHandler;

@Configuration
public class FlowConfiguration {
	
	@Autowired
	private ApplicationContext context;
	private final static SpelExpressionParser PARSER = new SpelExpressionParser();
	
	@Autowired
	private RequestHandlerRetryAdvice advice;
	
	@Bean
	public IntegrationFlow inFlow(){
		
		return IntegrationFlows.from(Http.inboundGateway("/customer/**")
				.requestMapping( r-> {
					r.methods(HttpMethod.GET,HttpMethod.POST,HttpMethod.PUT,HttpMethod.OPTIONS,HttpMethod.DELETE);
				})
				.replyChannel(getChannelBean("replyChannel"))
				.errorChannel(getChannelBean("errorChannel")))
				.channel(getChannelBean("requestChannel"))
				.handle("requestHandler","handleInputMessage")
				.channel(getChannelBean("inputChannel"))				
				.get();
			
				
	}
	
	@Bean
	public IntegrationFlow errorFlow(){
		
		return IntegrationFlows.from(getChannelBean("errorChannel"))			
				.handle("errorHandler","handleError")
				.channel(getChannelBean("replyChannel"))				
				.get();
			
				
	}
	
	@Bean
	public IntegrationFlow outFlow(){
	
		return IntegrationFlows.from(getChannelBean("inputChannel"))
				.handle("customInterceptor","interceptMessage")
				.handle(Http.outboundGateway(PARSER.parseExpression("headers.serviceurl"))
						.expectedResponseType(String.class)								
						.httpMethodExpression(PARSER.parseExpression("headers.httpmethod"))						
						.errorHandler( (ResponseErrorHandler) context.getBean(RestResponseErrorHandler.class))
						,e-> e.advice(advice))
				.channel(getChannelBean("outputChannel"))
				.handle("responseHandler","handleOutputMessage")
				.channel(getChannelBean("replyChannel"))				
				.get();
	}
	
	private MessageChannel getChannelBean(String channelName){
		return (MessageChannel) context.getBean(channelName);
	}

}
