package com.landaojia.blog.common.util;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by JUN on 15/9/15.
 */
public class BaseController {
    SerializerFeature[] feature =
            { SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse,
                    SerializerFeature.WriteMapNullValue };
}
