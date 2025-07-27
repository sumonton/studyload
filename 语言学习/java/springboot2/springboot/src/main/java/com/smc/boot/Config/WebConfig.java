package com.smc.boot.Config;

import com.smc.boot.converter.SmcMessageConverter;
import com.smc.boot.bean.Pet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2022/5/2
 * @Author smc
 * @Description:
 */
@Configuration(proxyBeanMethods = false)
public class WebConfig /*implements WebMvcConfigurer*/ {
//    @Bean
//    public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
//        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
//        hiddenHttpMethodFilter.setMethodParam("_m");
//        return hiddenHttpMethodFilter;
//    }

//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        UrlPathHelper urlPathHelper = new UrlPathHelper();
//        urlPathHelper.setRemoveSemicolonContent(false);
//        configurer.setUrlPathHelper(urlPathHelper);
//    }
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            /**
             * 自定义内容协商策略
             * @param configurer
             */
//            @Override
//            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//                Map<String, MediaType> mediaTypes = new HashMap<>();
//                mediaTypes.put("json",MediaType.APPLICATION_JSON);
//                mediaTypes.put("xml",MediaType.APPLICATION_XML);
//                mediaTypes.put("x-smc",MediaType.parseMediaType("application/x-smc"));
//
//                //指定支持解析哪些参数对应的哪些媒体类型
//                ParameterContentNegotiationStrategy parameterStrategy = new ParameterContentNegotiationStrategy(mediaTypes);
//                //重新加请求头的
////                HeaderContentNegotiationStrategy headerContentNegotiationStrategy = new HeaderContentNegotiationStrategy();
////                configurer.strategies(Arrays.asList(parameterStrategy,headerContentNegotiationStrategy));
//                configurer.strategies(Arrays.asList(parameterStrategy));
//            }

            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new SmcMessageConverter());
            }

            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                UrlPathHelper urlPathHelper = new UrlPathHelper();
                urlPathHelper.setRemoveSemicolonContent(false);
                configurer.setUrlPathHelper(urlPathHelper);
            }

            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new Converter<String, Pet>() {

                    @Override
                    public Pet convert(String source) {
                        if (!StringUtils.hasLength(source)){
                            Pet pet = new Pet();
                            String[] split = source.split(",");
                            pet.setName(split[0]);
                            pet.setAge(Integer.valueOf(split[1]));
                            return pet;
                        }
                        return null;
                    }
                });
            }
        };
    }
}
