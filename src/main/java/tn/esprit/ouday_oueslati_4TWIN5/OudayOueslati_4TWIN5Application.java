package tn.esprit.ouday_oueslati_4TWIN5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication
public class OudayOueslati_4TWIN5Application {

    public static void main(String[] args) {
        SpringApplication.run(OudayOueslati_4TWIN5Application.class, args);
    }

}
