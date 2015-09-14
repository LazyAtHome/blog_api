package com.landaojia.mvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;
/**
 * used for converting Date Object to Json String 
 * @author Jason
 *
 * 2015年9月14日
 */
public class DateConverter implements WebBindingInitializer {  
  
    public void initBinder(WebDataBinder binder, WebRequest request) {  
  
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df,  
                        false));  
  
    }  
}  