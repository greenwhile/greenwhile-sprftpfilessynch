package ua.uhmc.sprftpfilessynch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"ua.uhmc.sprftpfilessynch.config",
        "ua.uhmc.sprftpfilessynch.provider",
        "ua.uhmc.sprftpfilessynch.service",
        "ua.uhmc.sprftpfilessynch.handler",
        "ua.uhmc.sprftpfilessynch.grib2"})
@SpringBootApplication
public class SprftpfilessynchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SprftpfilessynchApplication.class, args);
    }

}
