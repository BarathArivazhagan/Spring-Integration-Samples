package com.barath.app;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {
	
	
	public Message<?> handleOutputMessage(Message<?> message){
		System.out.println("OUTPUT MESSGAE ========> "+message.toString());
		
		return message;
	}

}
