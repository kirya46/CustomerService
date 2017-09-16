package com.common;

import com.common.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Kirill Stoianov on 15/09/17.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(
                AppConfig.class
        );
        context.refresh();
    }

}
