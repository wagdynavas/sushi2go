package com.wagdynavas.sushi2go;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableEncryptableProperties TODO: remove comment after you fix the encryption issue
public class Sushi2goApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sushi2goApplication.class, args);
    }

}
