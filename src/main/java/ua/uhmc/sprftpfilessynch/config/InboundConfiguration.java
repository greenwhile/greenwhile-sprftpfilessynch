package ua.uhmc.sprftpfilessynch.config;

//import org.apache.commons.net.ftp.FTPClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.dsl.IntegrationFlow;
//import org.springframework.integration.dsl.IntegrationFlows;
//import org.springframework.integration.ftp.dsl.Ftp;
//import org.springframework.integration.ftp.dsl.FtpInboundChannelAdapterSpec;
//import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
//import org.springframework.util.StringUtils;
//
//import java.io.File;
//import java.util.Arrays;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
//@Configuration
//public class InboundConfiguration {
//
//    private final String myPatterns = ".SIG";
//    private Set<String> patterns = StringUtils.commaDelimitedListToSet(this.myPatterns);
//
//    @Value("${ftp.username}") String username;
//    @Value("${ftp.password}") String pw;
//    @Value("${ftp.host}") String host;
//    @Value("${ftp.port}") int port;
//
//    @Bean
//    FtpInboundChannelAdapterSpec ftpInboundChannelAdapterSpec(DefaultFtpSessionFactory ftpSf) {
//        var localDirectory = new File(new File(System.getProperty("user.home"), "Desktop"), "local");
//        return Ftp
//                .inboundAdapter(ftpSf)
//                .remoteDirectory("/GRIB2/COMPRESSED/EGRR/T+06")
//                .autoCreateLocalDirectory(true)
//                .patternFilter("*")
//                .filter(files -> Arrays.stream(files)
//                            .filter(file -> patterns.stream()
//                                    .filter(pattern -> !file.getName().endsWith(pattern))
//                                    .findFirst()
//                                    .isPresent())
//                            .collect(Collectors.toList()))
//                .localDirectory(localDirectory);
//    }
//
//    @Bean
//    IntegrationFlow inbound(FtpInboundChannelAdapterSpec ftpInboundSpec) {
//        return IntegrationFlows
//                .from(ftpInboundSpec, pc -> pc.poller(pm -> pm.fixedRate(1000, TimeUnit.MILLISECONDS)))
//                .handle((file, messageHeaders) -> {
//                    Set<String> keys = messageHeaders.keySet();
//                    Set<Map.Entry<String,Object>>entries = messageHeaders.entrySet();
//                    messageHeaders.entrySet().stream().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
////                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//                        return null;
//                    })
//                    .get();
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
