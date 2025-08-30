package edu.eci.arsw.blueprints;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "edu.eci.arsw.blueprints.services",
        "edu.eci.arsw.blueprints.persistence.impl"
})

public class Config {
    
}
