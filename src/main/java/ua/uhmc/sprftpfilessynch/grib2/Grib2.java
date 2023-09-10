package ua.uhmc.sprftpfilessynch.grib2;

import ua.uhmc.sprftpfilessynch.grib2.body.Body;
import ua.uhmc.sprftpfilessynch.grib2.header.Header;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Grib2 {
    private byte[] bytes;
    private Header header;
    private Body body;
    private Grib2FileName fileName;

    private Integer GRIB2_BULLETIN_SIZE = 10;

    public Grib2(byte[] bytes, Path pathToBinaryFile) {
        this.bytes = bytes;
        this.header = new Header(bytes);
        this.body = new Body(bytes);
        this.fileName = new Grib2FileName(pathToBinaryFile);
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header grib2Header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Grib2FileName getFileName() {
        return fileName;
    }

    public void setFileName(Grib2FileName fileName) {
        this.fileName = fileName;
    }

    public Grib2FileName initGrib2FileName(){
        Integer [] indexes = header.getHeaderIndexes();
        byte [] t1t2a1a2 = new byte[4];
        byte [] t1t2a1 = new byte[3];
        byte [] a2 = new byte[1];
        byte [] level = new byte[2];
        byte [] datasource = new byte[4];
        byte [] observation = new byte[6];
        int count = 0;
        if(indexes[0]!=null || indexes[0] != null){
            for(int i = indexes[0]; i <= indexes[1]; i++){
                if(count < 3){
                    t1t2a1[count] = bytes[i];
                    t1t2a1a2[count] = bytes[i];
                    fileName.setT1T2A1(t1t2a1);
                }
                if(count == 3){
                    a2[0] = bytes[i];
                    t1t2a1a2[count] = bytes[i];
                    fileName.setA2(a2);
                    fileName.setSuperWmoHeader(t1t2a1a2);
                }
                if(count >= 4 && count <= 5){
                    Integer indx = 4;
                    level[count-indx] = bytes[i];
                    fileName.setLevel(level);
                }
                if(count >= 7 && count <= 10){
                    Integer indx = 7;
                    datasource[count-indx] = bytes[i];
                    fileName.setDatasource(datasource);
                }
                if(count >= 12 && count <= 17){
                    Integer indx = 12;
                    observation[count-indx] = bytes[i];
                    fileName.setObservation(observation);
                }
                count++;
            }
        }
        return fileName;
    }
    public byte[] getGrib2Grid(byte[] bytes, Integer bodyStartIndex, Integer bodyEndIndex){
        byte [] arr = new byte[bodyEndIndex-bodyStartIndex+1];
        int index = 0;
        for(int i = bodyStartIndex; i <= bodyEndIndex; i++){
            arr[index] = bytes[i];
            index++;
        }
        return arr;
    }
    public char[] getT1T2A1(byte[] bytes, Integer [] indexes){
        char []  t1t2a1 = new char[3];
        int counter = 0;
        t1t2a1[0] = (char)bytes[indexes[0]];
        t1t2a1[1] = (char)bytes[indexes[0]+1];
        t1t2a1[2] = (char)bytes[indexes[0]+2];
        System.out.println((char)bytes[indexes[0]] + " " + (char)bytes[indexes[0]+1] + " " + (char)bytes[indexes[0]+2]);
        return t1t2a1;
    }
    public byte[] getGrib2BulletinContent(byte[] bytes, Integer bodyEndIndex){
        byte [] arr = new byte[bytes.length - bodyEndIndex];
        int index = 0;
        for(int i = bodyEndIndex+1; i < bytes.length; i++){
            arr[index] = bytes[i];
            index++;
        }
        return arr;
    }
    public void writeIntoBinaryFile(byte[] bytes, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Files.write(path, bytes);
    }
}
