package ua.uhmc.sprftpfilessynch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ua.uhmc.sprftpfilessynch.handler.BinariesHandler;
import ua.uhmc.sprftpfilessynch.handler.LocalResourcesPathManager;

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    public BinariesHandler binariesHandlerPrototypeScope() {
        return new BinariesHandler();
    }

    @Bean
    @Scope("prototype")
    public LocalResourcesPathManager localResourcesPathManagerScope() {
        return new LocalResourcesPathManager();
    }

//    @Bean
//    @Scope("prototype")
//    public JsonParser jsonParserPrototypeScope() {
//        return new JsonParser();
//    }

}
