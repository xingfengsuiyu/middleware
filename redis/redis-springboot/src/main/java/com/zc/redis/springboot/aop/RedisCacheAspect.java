package com.zc.redis.springboot.aop;

import com.zc.redis.springboot.annotations.RedisCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.Semaphore;

/**
 * @author zc
 * @create 2021-07-04 15:25
 **/
@Component
@Aspect
public class RedisCacheAspect {
	@Autowired
	private RedisTemplate redisTemplate;

	Semaphore semaphore = new Semaphore(30);


	@Pointcut("@annotation(com.zc.redis.springboot.annotations.RedisCache)")
	public void cachePointcut() {
	}

	@Around("cachePointcut()")
	public Object doCache(ProceedingJoinPoint joinPoint) throws Throwable {
		Object value = null;
		try {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
			RedisCache cacheAnnotation = method.getAnnotation(RedisCache.class);
			String keyEl = cacheAnnotation.key();
			String prefix = cacheAnnotation.value();
			ExpressionParser parser = new SpelExpressionParser();
			Expression expression = parser.parseExpression(keyEl);
			EvaluationContext context = new StandardEvaluationContext();
			// 添加参数
			Object[] args = joinPoint.getArgs();
			DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
			String[] parameterNames = discover.getParameterNames(method);
			for (int i = 0; i < parameterNames.length; i++) {
				context.setVariable(parameterNames[i], args[i].toString());
			}
			// 解析  上面为了获取redis key
			String key = prefix + "::" + expression.getValue(context).toString();

			// 1、 判定缓存中是否存在
			value = redisTemplate.opsForValue().get(key);
			if (value != null) {
				System.out.println("从缓存中读取到值：" + value);
				return value;
			}

			semaphore.acquire();

			// 2、不存在则执行方法
			value = joinPoint.proceed();

			// 3、 同步存储value到缓存。
			redisTemplate.opsForValue().set(key, value);

		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		return value;
	}
}