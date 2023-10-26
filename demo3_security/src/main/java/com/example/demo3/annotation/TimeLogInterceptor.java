package com.example.demo3.annotation;

import org.aopalliance.intercept.MethodInvocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.aop.IntroductionInterceptor;

public class TimeLogInterceptor implements IntroductionInterceptor/*, BeanFactoryAware */{
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeLogInterceptor.class);
	
//	private final StandardEvaluationContext evaluationCtx = new StandardEvaluationContext(); 
//	private BeanFactory beanFactory;
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String args = "";
		for(Object arg : invocation.getArguments()) {
			if(!args.isEmpty()) args = args.concat(",");
			args = args.concat(null == arg ? "null" : arg.toString());
		}
		LOGGER.info("begin - {} arguments[{}]", invocation.getStaticPart().toString(), args);
		
		long begin = System.currentTimeMillis();
		Object ret = invocation.proceed();
		long end = System.currentTimeMillis();
		
		LOGGER.info("end   - {} runtime(msec)[{}]", invocation.getStaticPart(), (end-begin));
		
		return ret;
	}

	@Override
	public boolean implementsInterface(Class<?> arg0) {
		LOGGER.info("implementsInterface : arg[{}]", arg0);
		return false;
	}

//	@Override
//	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//		LOGGER.info("setBeanFactory : {}", beanFactory);
//		// TODO Auto-generated method stub
//		this.beanFactory = beanFactory;
//		this.evaluationCtx.setBeanResolver(new BeanFactoryResolver(this.beanFactory));
//	}

}
