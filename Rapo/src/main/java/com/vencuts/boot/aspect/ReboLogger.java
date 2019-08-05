package com.vencuts.boot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ReboLogger {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());


	@Before("allMethod()")
	public void beforeAllMethod(JoinPoint joinPoint) {
		logger.info("Before Method call Name ::"+ joinPoint.getSignature().getName());
	}
	
	@AfterReturning("allMethod()")
	public void AfterAllMethod(JoinPoint joinPoint) {
		logger.info("Successfull completion on Method call Name:: "+joinPoint.getSignature().getName());
	}
	
	@AfterThrowing("allMethod()")
	public void AfterExcecptionThrow(JoinPoint joinPoint) {
		logger.info("Exception on Method call Name:: "+joinPoint.getSignature().getName());
	}
	@Pointcut("within(com.vencuts.boot.*.*)")
	public void allMethod() {}
}
