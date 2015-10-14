package com.landaojia.blog.common.redis;

public interface Function<E, T> {

    public T callback(E e);

}
