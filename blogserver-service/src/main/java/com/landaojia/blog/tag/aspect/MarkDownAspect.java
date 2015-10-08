package com.landaojia.blog.tag.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.github.rjeschke.txtmark.Processor;
import com.landaojia.blog.post.entity.Post;

@Aspect
@Component
public class MarkDownAspect {

    @Pointcut(value = "execution(* com.landaojia.blog.post.service.PostService.queryById(..))")
    private void pointcut() {
    }

    @AfterReturning(pointcut = "pointcut()", returning = "returnValue")
    public void afterReturning(Object returnValue) {
        Post post = (Post) returnValue;
        post.setContent(Processor.process(post.getContent()));
    }
}
