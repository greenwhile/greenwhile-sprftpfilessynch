package ua.uhmc.sprftpfilessynch.config;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.ftp.inbound.FtpInboundFileSynchronizer;
import org.springframework.integration.ftp.inbound.FtpInboundFileSynchronizingMessageSource;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class FtpSynchConfiguration {
    private final String myPatterns = ".SIG";
    private Set<String> patterns = StringUtils.commaDelimitedListToSet(this.myPatterns);

    @Value("${ftp.username}")
    private String username;
    @Value("${ftp.password}")
    private String pw;
    @Value("${ftp.host}")
    private String host;
    @Value("${ftp.port}")
    private int port;
// Dont use as ready properties file param
//    @Value("${local.directory.binaries.name}")
//    private String localDirectoryBinaries;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private File localDirectoryBinaries = new File(new File(new File(new File(new File(new File(System.getProperty("user.home"),
            "Pictures"),
            "windroze"),
            "src"),
            "main"),
            "resources"),
            "BINARY");

    @Bean
    DefaultFtpSessionFactory defaultFtpSessionFactory() {
        DefaultFtpSessionFactory defaultFtpSessionFactory = new DefaultFtpSessionFactory();
        defaultFtpSessionFactory.setPassword(pw);
        defaultFtpSessionFactory.setUsername(username);
        defaultFtpSessionFactory.setHost(host);
        defaultFtpSessionFactory.setPort(port);
        defaultFtpSessionFactory.setClientMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);
        return defaultFtpSessionFactory;
    }

    @Bean("synchronizertest")
    public FtpInboundFileSynchronizer synchronizer() {
        FtpInboundFileSynchronizer sync = new FtpInboundFileSynchronizer(defaultFtpSessionFactory());
        sync.setDeleteRemoteFiles(false);
        sync.setRemoteDirectory("/home/sammy/ftp/files");
        sync.setFilter(files -> Arrays.stream(files)
                .filter(file -> patterns.stream()
                        .filter(pattern -> !file.getName().endsWith(pattern))
                        .findFirst()
                        .isPresent())
                .collect(Collectors.toList()));
        return sync;
    }

    @Bean("ftpmessagesourcetest")
    @InboundChannelAdapter(channel = "fileuploaded", poller = @Poller(fixedDelay = "1000"))
    public MessageSource<File> ftpMessageSource() {
//        var localDirectory = new File(new File(System.getProperty("user.home"), "Desktop"), "local");
        FtpInboundFileSynchronizingMessageSource source =
                new FtpInboundFileSynchronizingMessageSource(synchronizer());
        source.setLocalDirectory(localDirectoryBinaries);
        source.setAutoCreateLocalDirectory(true);
        return source;
    }

    @Bean("synchronizer06")
    public FtpInboundFileSynchronizer synchronizer06() {
        FtpInboundFileSynchronizer sync = new FtpInboundFileSynchronizer(defaultFtpSessionFactory());
        sync.setDeleteRemoteFiles(false);
        sync.setRemoteDirectory("/GRIB2/COMPRESSED/EGRR/T+06");
        sync.setFilter(files -> Arrays.stream(files)
                .filter(file -> patterns.stream()
                        .filter(pattern -> !file.getName().endsWith(pattern))
                        .findFirst()
                        .isPresent())
//                        .forEach(handleIncomingFile(file -> file/**/)));
                .collect(Collectors.toList()));
        return sync;
    }

    @Bean("ftpmessagesource06")
    @InboundChannelAdapter(channel = "fileuploaded", poller = @Poller(fixedDelay = "1000"))
    public MessageSource<File> ftpMessageSource06() {
//        var localDirectory = new File(new File(System.getProperty("user.home"), "Desktop"), "local");
        FtpInboundFileSynchronizingMessageSource source =
                new FtpInboundFileSynchronizingMessageSource(synchronizer06());
        source.setLocalDirectory(localDirectoryBinaries);
        source.setAutoCreateLocalDirectory(true);
        return source;
    }

//    @Bean("synchronizer09")
//    public FtpInboundFileSynchronizer synchronizer09() {
//        FtpInboundFileSynchronizer sync = new FtpInboundFileSynchronizer(defaultFtpSessionFactory());
//        sync.setDeleteRemoteFiles(false);
//        sync.setRemoteDirectory("/GRIB2/COMPRESSED/EGRR/T+09");
//        sync.setFilter(files -> Arrays.stream(files)
//                .filter(file -> patterns.stream()
//                        .filter(pattern -> !file.getName().endsWith(pattern))
//                        .findFirst()
//                        .isPresent())
//                .collect(Collectors.toList()));
//        return sync;
//    }
//
//    @Bean("ftpmessagesource09")
//    @InboundChannelAdapter(channel = "fileuploaded", poller = @Poller(fixedDelay = "1000"))
//    public MessageSource<File> ftpMessageSource09() {
////        var localDirectory = new File(new File(System.getProperty("user.home"), "Desktop"), "local");
//        FtpInboundFileSynchronizingMessageSource source =
//                new FtpInboundFileSynchronizingMessageSource(synchronizer09());
//        source.setLocalDirectory(new File(localDirectory));
//        source.setAutoCreateLocalDirectory(true);
//        return source;
//    }
//
//    @Bean("synchronizer12")
//    public FtpInboundFileSynchronizer synchronizer12() {
//        FtpInboundFileSynchronizer sync = new FtpInboundFileSynchronizer(defaultFtpSessionFactory());
//        sync.setDeleteRemoteFiles(false);
//        sync.setRemoteDirectory("/GRIB2/COMPRESSED/EGRR/T+12");
//        sync.setFilter(files -> Arrays.stream(files)
//                .filter(file -> patterns.stream()
//                        .filter(pattern -> !file.getName().endsWith(pattern))
//                        .findFirst()
//                        .isPresent())
//                .collect(Collectors.toList()));
//        return sync;
//    }
//
//    @Bean("ftpmessagesource12")
//    @InboundChannelAdapter(channel = "fileuploaded", poller = @Poller(fixedDelay = "1000"))
//    public MessageSource<File> ftpMessageSource12() {
////        var localDirectory = new File(new File(System.getProperty("user.home"), "Desktop"), "local");
//        FtpInboundFileSynchronizingMessageSource source =
//                new FtpInboundFileSynchronizingMessageSource(synchronizer12());
//        source.setLocalDirectory(new File(localDirectory));
//        source.setAutoCreateLocalDirectory(true);
//        return source;
//    }
//
//    @Bean("synchronizer15")
//    public FtpInboundFileSynchronizer synchronizer15() {
//        FtpInboundFileSynchronizer sync = new FtpInboundFileSynchronizer(defaultFtpSessionFactory());
//        sync.setDeleteRemoteFiles(false);
//        sync.setRemoteDirectory("/GRIB2/COMPRESSED/EGRR/T+15");
//        sync.setFilter(files -> Arrays.stream(files)
//                .filter(file -> patterns.stream()
//                        .filter(pattern -> !file.getName().endsWith(pattern))
//                        .findFirst()
//                        .isPresent())
//                .collect(Collectors.toList()));
//        return sync;
//    }
//
//    @Bean("ftpmessagesource15")
//    @InboundChannelAdapter(channel = "fileuploaded", poller = @Poller(fixedDelay = "1000"))
//    public MessageSource<File> ftpMessageSource15() {
////        var localDirectory = new File(new File(System.getProperty("user.home"), "Desktop"), "local");
//        FtpInboundFileSynchronizingMessageSource source =
//                new FtpInboundFileSynchronizingMessageSource(synchronizer15());
//        source.setLocalDirectory(new File(localDirectory));
//        source.setAutoCreateLocalDirectory(true);
//        return source;
//    }
//
//    @Bean("synchronizer18")
//    public FtpInboundFileSynchronizer synchronizer18() {
//        FtpInboundFileSynchronizer sync = new FtpInboundFileSynchronizer(defaultFtpSessionFactory());
//        sync.setDeleteRemoteFiles(false);
//        sync.setRemoteDirectory("/GRIB2/COMPRESSED/EGRR/T+18");
//        sync.setFilter(files -> Arrays.stream(files)
//                .filter(file -> patterns.stream()
//                        .filter(pattern -> !file.getName().endsWith(pattern))
//                        .findFirst()
//                        .isPresent())
//                .collect(Collectors.toList()));
//        return sync;
//    }
//
//    @Bean("ftpmessagesource18")
//    @InboundChannelAdapter(channel = "fileuploaded", poller = @Poller(fixedDelay = "1000"))
//    public MessageSource<File> ftpMessageSource18() {
////        var localDirectory = new File(new File(System.getProperty("user.home"), "Desktop"), "local");
//        FtpInboundFileSynchronizingMessageSource source =
//                new FtpInboundFileSynchronizingMessageSource(synchronizer18());
//        source.setLocalDirectory(new File(localDirectory));
//        source.setAutoCreateLocalDirectory(true);
//        return source;
//    }
//
//    @Bean("synchronizer21")
//    public FtpInboundFileSynchronizer synchronizer21() {
//        FtpInboundFileSynchronizer sync = new FtpInboundFileSynchronizer(defaultFtpSessionFactory());
//        sync.setDeleteRemoteFiles(false);
//        sync.setRemoteDirectory("/GRIB2/COMPRESSED/EGRR/T+21");
//        sync.setFilter(files -> Arrays.stream(files)
//                .filter(file -> patterns.stream()
//                        .filter(pattern -> !file.getName().endsWith(pattern))
//                        .findFirst()
//                        .isPresent())
//                .collect(Collectors.toList()));
//        return sync;
//    }
//
//    @Bean("ftpmessagesource21")
//    @InboundChannelAdapter(channel = "fileuploaded", poller = @Poller(fixedDelay = "1000"))
//    public MessageSource<File> ftpMessageSource21() {
////        var localDirectory = new File(new File(System.getProperty("user.home"), "Desktop"), "local");
//        FtpInboundFileSynchronizingMessageSource source =
//                new FtpInboundFileSynchronizingMessageSource(synchronizer21());
//        source.setLocalDirectory(new File(localDirectory));
//        source.setAutoCreateLocalDirectory(true);
//        return source;
//    }
//
//    @Bean("synchronizer24")
//    public FtpInboundFileSynchronizer synchronizer24() {
//        FtpInboundFileSynchronizer sync = new FtpInboundFileSynchronizer(defaultFtpSessionFactory());
//        sync.setDeleteRemoteFiles(false);
//        sync.setRemoteDirectory("/GRIB2/COMPRESSED/EGRR/T+24");
//        sync.setFilter(files -> Arrays.stream(files)
//                .filter(file -> patterns.stream()
//                        .filter(pattern -> !file.getName().endsWith(pattern))
//                        .findFirst()
//                        .isPresent())
//                .collect(Collectors.toList()));
//        return sync;
//    }
//
//    @Bean("ftpmessagesource24")
//    @InboundChannelAdapter(channel = "fileuploaded", poller = @Poller(fixedDelay = "1000"))
//    public MessageSource<File> ftpMessageSource24() {
////        var localDirectory = new File(new File(System.getProperty("user.home"), "Desktop"), "local");
//        FtpInboundFileSynchronizingMessageSource source =
//                new FtpInboundFileSynchronizingMessageSource(synchronizer24());
//        source.setLocalDirectory(new File(localDirectory));
//        source.setAutoCreateLocalDirectory(true);
//        return source;
//    }

    @ServiceActivator(inputChannel = "fileuploaded")
    public void handleIncomingFile(File file) throws IOException {
//        System.out.printf("handle BEGIN %s", file.getName());
//        String content = FileUtils.readFileToString(file, "UTF-8");
//        System.out.printf("Content: %s", content);
        rabbitTemplate.convertAndSend(Constants.METEO_DATA_EXCHANGE,Constants.BINARY_ROUTING_KEY, file.getName());
        System.out.printf("handle END %s", file.getName());
    }

}
