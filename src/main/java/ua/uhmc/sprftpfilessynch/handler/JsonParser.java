package ua.uhmc.sprftpfilessynch.handler;

//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//@Service
//public class JsonParser {
//
//    @Value("${remote.directories.name.filename}") String remoteDirectoriesFileName;
//
//    @Value("${keyword}") String remoteDirectoriesKeyWord;
//    public List<String> getRemoteDirectoriesList() throws IOException {
//        List<String> remoteDirectories = new ArrayList<>();
//        ClassLoader classLoader = getClass().getClassLoader();
//        File file = new File(classLoader.getResource(remoteDirectoriesFileName).getFile());
//        byte[] jsonData = new byte[0];
//        try {
//            jsonData = Files.readAllBytes(Paths.get(file.getPath()));
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(jsonData);
//            JsonNode directoriesNode = rootNode.path(remoteDirectoriesKeyWord);
//            System.out.println(remoteDirectoriesKeyWord + " " + remoteDirectoriesFileName);
//            Iterator<JsonNode> elements = directoriesNode.elements();
//            while(elements.hasNext()){
//                JsonNode directory = elements.next();
//                remoteDirectories.add(directory.asText());
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return remoteDirectories;
//    }
//
//}

