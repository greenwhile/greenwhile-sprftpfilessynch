package ua.uhmc.sprftpfilessynch.handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ua.uhmc.sprftpfilessynch.grib2.Grib2;
import ua.uhmc.sprftpfilessynch.grib2.Grib2FileName;
import ua.uhmc.sprftpfilessynch.grib2.body.Body;
import ua.uhmc.sprftpfilessynch.grib2.header.Header;
import ua.uhmc.sprftpfilessynch.provider.ApplicationContextProvider;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class BinariesHandler {

    @Value("${local.directory.binary.name}")
    private String binaryFilesPath;
    private ApplicationContext context = ApplicationContextProvider.getApplicationContext();
    private LocalResourcesPathManager localResourcesPathManager = (LocalResourcesPathManager) context.getBean("localResourcesPathManagerScope");
    public List<File> getBinaryDataFilesList() throws Exception {
        LocalResource localBinaryResource = localResourcesPathManager.create(
                new LocalResource(
                        binaryFilesPath,
                        new File(System.getProperty("user.home"))));
        System.out.println("binaries: " + localBinaryResource.getPath());
        File[] folderEntries = localBinaryResource.getFile().listFiles();
        List<File> files = new ArrayList<File>();
        if(folderEntries.length > 0){
            for (File entry : folderEntries) {
                if (entry.isDirectory()) {
                    List<File> subFolderEntries = getBinaryDataFilesList();
                    for (File subEntry : subFolderEntries) {
                        if(subEntry.isFile())
                            files.add(subEntry);
                    }
                }
                if(entry.isFile()){
                    files.add(entry);
                }
            }
        }
        return files;
    }

    public byte [] cutIntoGrib2Files(byte [] bytes, Path pathToGrib2, Path pathToBinaryFile) throws IOException {

        Integer total_len = bytes.length;
        Integer grb2_len = -1;

        Grib2 grib2 = new Grib2(bytes, pathToBinaryFile);

        Header header = grib2.getHeader();
        Body body = grib2.getBody();

        Integer header_len = header.getLength();
        System.out.println("\nlen: " + header_len);

        Grib2FileName filename = grib2.initGrib2FileName();
        System.out.println(" filename " +
                filename.getSuperWmoHeader() + " " +
                filename.getT1T2A1() + " " +
                filename.getA2() + " " +
                filename.getDatasource() + " " +
                filename.getObservation() + " " +
                filename.getLevel());

        String grib2FileName = grib2.getFileName().get();
        System.out.println(grib2FileName);

        Integer[] headerIndexes = header.getHeaderIndexes();

        if(headerIndexes[0] == null && headerIndexes[1] == null){
            return bytes;
        } else {
            System.out.println("*******************************************");
            System.out.println("H ind: " + headerIndexes[0] + "  " + headerIndexes[1]);
            for(int i = headerIndexes[0]; i <= headerIndexes[1]; i++){
                System.out.print((char) bytes[i]);
            }

            Integer bodyStartIndex = body.getStartIndex();
            Integer bodyEndIndex = body.getEndIndex();

            byte[] gribFileBytes = grib2.getGrib2Grid(bytes, bodyStartIndex, bodyEndIndex);
            grb2_len = gribFileBytes.length;

            File f = new File(pathToGrib2.toString(), grib2.getFileName().get());
            System.out.println("grb2 filename:  " + f.getPath() + " " + pathToGrib2.toString());
            grib2.writeIntoBinaryFile(gribFileBytes, f.getPath());
            bytes = grib2.getGrib2BulletinContent(bytes, bodyEndIndex);
        }
        System.out.println("total_len: " + total_len + " " + " grb2_len: " + grb2_len);

        return total_len >= grb2_len ? cutIntoGrib2Files(bytes, pathToGrib2, pathToBinaryFile) : bytes;
    }

}
