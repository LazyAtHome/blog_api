package com.landaojia.blog.post.web;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.rjeschke.txtmark.Processor;
import com.landaojia.blog.common.result.JsonResult;

@RestController
@RequestMapping("/md")
public class MarkDownController {

    @ResponseBody
    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public JsonResult preview(@RequestBody Map<String, String> map) {
        return JsonResult.success(Processor.process(map.get("txt")));
    }
}
