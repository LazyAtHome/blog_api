package com.landaojia.swagger;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by JUN on 15/9/15.
 */

@Configuration
@EnableSwagger
// Loads the spring beans required by the framework
public class SwaggerConfig
{


    private SpringSwaggerConfig springSwaggerConfig;

    /**
     * Required to autowire SpringSwaggerConfig
     */
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig)
    {
        this.springSwaggerConfig = springSwaggerConfig;
    }


    /**
     * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc
     * framework - allowing for multiple swagger groups i.e. same code base
     * multiple swagger resource listings.
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation()
    {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns(".*?");
    }

    private ApiInfo apiInfo()
    {
        ApiInfo apiInfo = new ApiInfo(
                        "懒到家blog系统API接口管理",
                        "各个controller下对应相应业务接口",
                        "My Apps API terms of service",
                        "",
                        null,
                        null);
        return apiInfo;
    }
}