package com.barath.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.handler.advice.RequestHandlerRetryAdvice;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
public class RetryAdviceConfiguration {
	private final static SpelExpressionParser PARSER = new SpelExpressionParser();
	
	@Autowired
	private RecoveryCallback<Object> callback;
	
	
	@Bean
	public RequestHandlerRetryAdvice retryAdvice(){
		RequestHandlerRetryAdvice retryAdvice=new  RequestHandlerRetryAdvice();
		retryAdvice.setRetryTemplate(retryTemplate());
		//retryAdvice.setRecoveryCallback(callback);		
		return retryAdvice;
	}
	
	@Bean
	public RetryTemplate retryTemplate(){
		RetryTemplate retryTemplate=new RetryTemplate();
		SimpleRetryPolicy retryPolicy=new SimpleRetryPolicy(5);
		retryTemplate.setRetryPolicy(retryPolicy);
		
		return retryTemplate;
	}

}
