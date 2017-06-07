package com.barath.app;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.http.HttpHeaders;
import org.springframework.integration.support.MutableMessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
public class CustomInterceptor {
	
	@Value("${outbound.service.url}")
	private String serviceUrl;
	
	public Message<?> interceptMessage(Message<?> message) throws MalformedURLException{

		MessageHeaders headers=message.getHeaders();
		String http_requestUrl=(String) headers.get(HttpHeaders.REQUEST_URL);

	
		String requestUrl =serviceUrl+new URL(http_requestUrl).getPath();
		System.out.println("REQUEST URL "+http_requestUrl);
		String httpMethod=(String) headers.get(HttpHeaders.REQUEST_METHOD);

		System.out.println("REQUEST URL "+requestUrl+" METHOD USED "+httpMethod);
		return MutableMessageBuilder.fromMessage(message)
				.setHeader("serviceurl", requestUrl)
				.setHeader("httpmethod", httpMethod)
				.setHeader("Content-Type", headers.get("contentType"))
				.setHeader("id", headers.getId())				
				.copyHeaders(message.getHeaders())
				.build();


}

}
