package ua.uhmc.sprftpfilessynch.service;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ua.uhmc.sprftpfilessynch.config.Constants;
import ua.uhmc.sprftpfilessynch.handler.BinariesHandler;
import ua.uhmc.sprftpfilessynch.provider.ApplicationContextProvider;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MeteoDataProcessingService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private ApplicationContext context = ApplicationContextProvider.getApplicationContext();
    private BinariesHandler binariesHandler = (BinariesHandler) context.getBean("binariesHandlerPrototypeScope");

    @RabbitListener(queues = Constants.BINARY_QUEUE)
    public void consumeMessageFromQueue(String message) throws Exception {
        System.out.println("From MeteoDataProcessingService.class receiver: " + message);
        List<File> binaries = binariesHandler.getBinaryDataFilesList();
        System.out.println("binaries sz: " + binaries.size());
        for(File file : binaries) {
            System.out.println("file bin: " + file.getName());
            Path tmp = Files.createTempDirectory("temp");
            System.out.println("tmp dir path: " + tmp); // prints the path of the temporary directory
            byte[] bytes = binariesHandler.cutIntoGrib2Files(Files.readAllBytes(Paths.get(file.getPath())), tmp, Path.of(file.getPath()));
            String messageToSend = tmp.toString().concat(":").concat(file.getName());
            rabbitTemplate.convertAndSend(Constants.METEO_DATA_EXCHANGE,Constants.GRIB2_ROUTING_KEY, messageToSend);
        }
    }
}
