package com.barath.app;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.stereotype.Component;


@Component
public class CustomRecoveryCallback implements RecoveryCallback<Object> {
	
	@Override
	public Object recover(RetryContext paramRetryContext) throws Exception {
		System.out.println("RECOVERY CALLBACK "+paramRetryContext.getRetryCount());
		return paramRetryContext.getLastThrowable();
	}


}
