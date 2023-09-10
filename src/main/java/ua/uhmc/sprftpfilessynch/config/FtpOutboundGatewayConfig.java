package ua.uhmc.sprftpfilessynch.config;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPFile;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.annotation.MessagingGateway;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.dsl.IntegrationFlow;
//import org.springframework.integration.dsl.IntegrationFlows;
//import org.springframework.integration.dsl.Pollers;
//import org.springframework.integration.file.remote.session.CachingSessionFactory;
//import org.springframework.integration.file.remote.session.SessionFactory;
//import org.springframework.integration.ftp.gateway.FtpOutboundGateway;
//import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
//import org.springframework.util.StringUtils;
//
//import java.io.File;
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Configuration
//public class FtpOutboundGatewayConfig {
//    private final String myPatterns = ".SIG";
//    private Set<String> patterns = StringUtils.commaDelimitedListToSet(this.myPatterns);
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Value("${ftp.username}") String username;
//    @Value("${ftp.password}") String pw;
//    @Value("${ftp.host}") String host;
//    @Value("${ftp.port}") int port;
//
//    @Value("${local.directory.name}") String localDirectory;
//
//    @MessagingGateway(defaultRequestChannel = "fetchRecursive")
//    public interface Gateway {
//        public List<File> fetchFiles(String remoteDir);
//    }
//
//    @Bean
//    IntegrationFlow polled() {
//        return IntegrationFlows.fromSupplier(() -> gateway(),
//                        e -> e.poller(Pollers.fixedDelay(Duration.ofSeconds(5))))
//                .handle(System.out::println)
//                .get();
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "fetchRecursive")
//    public FtpOutboundGateway gateway() {
//        // Create a recursive MGET gateway that gets the remote directory from the payload
//        FtpOutboundGateway gateway = new FtpOutboundGateway(sessionFactory(), "mget", "payload");
//        gateway.setOptions("-R");
//        gateway.setLocalDirectory(new File(localDirectory));
//        gateway.setFilter(files -> Arrays.stream(files)
//                .filter(file -> patterns.stream()
//                        .filter(pattern -> !file.getName().endsWith(pattern))
//                        .findFirst()
//                        .isPresent())
//                .collect(Collectors.toList()));
//        return gateway;
//    }
//
//    @Bean
//    public SessionFactory<FTPFile> sessionFactory() {
//        return new CachingSessionFactory<>(defaultFtpSessionFactory());
//    }
//
//    @Bean
//    DefaultFtpSessionFactory defaultFtpSessionFactory() {
//        DefaultFtpSessionFactory defaultFtpSessionFactory = new DefaultFtpSessionFactory();
//        defaultFtpSessionFactory.setPassword(pw);
//        defaultFtpSessionFactory.setUsername(username);
//        defaultFtpSessionFactory.setHost(host);
//        defaultFtpSessionFactory.setPort(port);
//        defaultFtpSessionFactory.setClientMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);
//        return defaultFtpSessionFactory;
//    }
//
//}
//
