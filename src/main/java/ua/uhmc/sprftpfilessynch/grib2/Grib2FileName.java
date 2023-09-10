package ua.uhmc.sprftpfilessynch.grib2;

import ua.uhmc.sprftpfilessynch.grib2.header.A2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Grib2FileName {

    byte [] t1t2a1a2 = new byte[4];
    byte [] t1t2a1 = new byte[3];
    byte [] a2 = new byte[1];
    byte [] level = new byte[2];
    byte [] datasource = new byte[4];
    byte [] observation = new byte[6];

    Path pathToBinaryFile;

    public Grib2FileName(Path pathToBinaryFile) {
        this.pathToBinaryFile = pathToBinaryFile;
    }

    public String getSuperWmoHeader() {
        return new String(t1t2a1a2, StandardCharsets.ISO_8859_1);
    }
    public void setSuperWmoHeader(byte[] t1t2a1a2) {
        this.t1t2a1a2 = t1t2a1a2;
    }
    public String getT1T2A1() {
        return new String(t1t2a1, StandardCharsets.ISO_8859_1);
    }
    public void setT1T2A1(byte[] t1t2a1) {
        this.t1t2a1 = t1t2a1;
    }
    public String getA2() {
        String _a2 = new String(a2, StandardCharsets.ISO_8859_1);
        for (A2 a2 : A2.values()) {
            if (a2.name().equalsIgnoreCase(_a2))
                return a2.getForecast();
        }
        return null;
    }
    public void setA2(byte[] a2) {
        this.a2 = a2;
    }
    public String getLevel() {
        return new String(level, StandardCharsets.ISO_8859_1);
    }
    public void setLevel(byte[] level) {
        this.level = level;
    }
    public String getDatasource() {
        return new String(datasource, StandardCharsets.ISO_8859_1);
    }
    public void setDatasource(byte[] datasource) {
        this.datasource = datasource;
    }
    public String getObservation() {
        return new String(observation, StandardCharsets.ISO_8859_1);
    }
    public void setObservation(byte[] observation) {
        this.observation = observation;
    }
    public String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSSSSSSSSSSSS");
        return sdf.format(new Timestamp(new Date().getTime()));
    }

    public String getCreationTime() throws IOException {
        BasicFileAttributes attr = Files.readAttributes(this.pathToBinaryFile, BasicFileAttributes.class);
        String yyyyMMdd = attr.creationTime().toString().replaceAll("-", "").substring(0, 8);
        String hhmmss = attr.creationTime().toString().replaceAll(":", "").substring(11, 17);
        System.out.println("creation:  " + attr.creationTime() + "  " + yyyyMMdd + "  " + hhmmss);
        return yyyyMMdd + hhmmss;
    }

    // Z_GRIB2+T06,,120000_C_EGRR_20191112035531_YHXC10_20235731_155714_1693486634714.grib2
    public String get() throws IOException {
        return "Z_GRIB2+T" + getA2() + ",," + getObservation() + "_C_" + getDatasource() + "_" + getCreationTime() + "_" +
                getSuperWmoHeader() + getLevel() + "_" + getCurrentTime() + ".grib2";
    }
}
