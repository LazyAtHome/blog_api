package com.landaojia.blog.tag.aspect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.tag.entity.Tag;

@Aspect
@Component
public class TagsAspect {

    @Pointcut(value = "execution(* com.landaojia.blog.post.service.PostService.create(..))")
    private void createPointcut() {
    }

    @Pointcut(value = "execution(* com.landaojia.blog.post.service.PostService.update(..))")
    private void updatePointcut() {
    }

    @Before("createPointcut() || updatePointcut()")
    public void before(JoinPoint point) {
        Post post = null;
        for (Object obj : point.getArgs()) {
            if (obj.getClass() == Post.class) {
                post = (Post) obj;
                break;
            }
        }
        String tagsStr = post.getTags();
        if (null == tagsStr)
            return;
        Set<Tag> tags = new HashSet<>();
        for (String tag : tagsStr.split(",|，|\\s+")) {
            if (tag.length() > 10)
                throw new CommonException(CommonExceptionCode.TAG_TOO_LONG);
            if (!Strings.isNullOrEmpty(tag))
                tags.add(new Tag(tag));
        }
        post.setTagList(new ArrayList<>(tags));
    }

}
