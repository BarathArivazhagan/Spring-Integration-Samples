package com.barath.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestResponseErrorHandler implements ResponseErrorHandler{
	
	@Autowired
	@Qualifier("errorChannel")
	private MessageChannel errorChannel;
	
	
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		
		if(!response.getStatusCode().is2xxSuccessful()){
			return true;
		}
		return false;
	}
	
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		
		System.out.println("HANDLING ERROR "+response.getBody().toString());
		Map<String, Object> headers=new HashMap<String,Object>();
		response.getHeaders().toSingleValueMap().forEach( (key,value) -> {
			headers.put(key, value);
		});;
		//errorChannel.send(new ErrorMessage(new Throwable(response.getBody().toString()), headers));
	}

}
