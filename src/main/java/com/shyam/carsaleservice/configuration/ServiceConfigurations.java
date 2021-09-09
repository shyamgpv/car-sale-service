package com.shyam.carsaleservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingBuilderPlugin;
import springfox.documentation.spi.service.contexts.ApiListingContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class ServiceConfigurations {

    @Bean
    public Docket carListingApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    @Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
    public ApiListingBuilderPlugin getApiPathEnrichPlugin() {
        return new ApiListingBuilderPlugin() {
            @Override
            public void apply(ApiListingContext apiListingContext) {
                List<ApiDescription> apis = apiListingContext.apiListingBuilder().build().getApis();
                List builder = new ArrayList();
                if (apis != null) {
                    apis.forEach(api -> {
                        builder.add(new ApiDescription(api.getGroupName().get(),
                                api.getPath() + "?apiDescription=" + api.getDescription(),
                                "",
                                api.getDescription(),
                                api.getOperations(),
                                api.isHidden()));
                    });
                    apis = builder;
                    apiListingContext.apiListingBuilder().apis(apis);
                }
            }

            @Override
            public boolean supports(DocumentationType documentationType) {
                return true;
            }
        };
    }

}
