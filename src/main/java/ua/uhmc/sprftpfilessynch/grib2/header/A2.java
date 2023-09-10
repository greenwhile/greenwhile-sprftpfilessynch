package ua.uhmc.sprftpfilessynch.grib2.header;

import java.io.Serializable;

public enum A2 implements Comparable<A2>, Serializable {

    A ("00"),
    B ("03"),
    C ("06"),
    D ("09"),
    E ("12"),
    F ("15"),
    G ("18"),
    H ("21"),
    I ("24"),
    J ("27"),
    K ("30"),
    L ("33"),
    M ("36"),
    N ("39"),
    O ("42"),
    P ("45"),
    Q ("48");
    private final String forecast;
    A2(String forecast) {
        this.forecast = forecast;
    }

    public String getForecast() {
        return forecast;
    }
}
