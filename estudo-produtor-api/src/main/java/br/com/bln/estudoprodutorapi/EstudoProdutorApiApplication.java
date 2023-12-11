package br.com.bln.estudoprodutorapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EstudoProdutorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstudoProdutorApiApplication.class, args);
    }
}
