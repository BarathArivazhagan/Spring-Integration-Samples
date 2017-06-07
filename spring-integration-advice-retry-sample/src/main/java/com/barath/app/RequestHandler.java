package com.barath.app;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class RequestHandler {
	
	public Message<?> handleInputMessage(Message<?> message){
		System.out.println("INPUT MESSGAE ========> "+message.toString());
		
		return message;
	}

}
