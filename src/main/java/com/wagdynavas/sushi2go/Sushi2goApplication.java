package com.wagdynavas.sushi2go;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableEncryptableProperties
public class Sushi2goApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sushi2goApplication.class, args);
    }

}
