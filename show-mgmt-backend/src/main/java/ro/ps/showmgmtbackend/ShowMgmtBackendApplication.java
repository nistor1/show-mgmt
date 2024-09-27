package ro.ps.showmgmtbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ShowMgmtBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowMgmtBackendApplication.class, args);
    }

}
