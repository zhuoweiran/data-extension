package cn.csg.msg.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * <p>类{@code MsgProducerApplication} spring boot 启动入口
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 */
@SpringBootApplication
@EnableSwagger2
@EnableAsync //开启多线程
public class MsgProducerApplication {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.csg.msg.producer"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("仿厂站装置产生报文")
                .version("v1.0")
                .description("模仿厂站装置产生报文")
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(MsgProducerApplication.class,args);
    }
}
