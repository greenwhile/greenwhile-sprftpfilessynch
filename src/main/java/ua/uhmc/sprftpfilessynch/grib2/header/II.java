package ua.uhmc.sprftpfilessynch.grib2.header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class II {
    List<Integer> exceptions = new ArrayList<>(Arrays.asList(82, 77, 72, 67, 62));
    private Integer pointer;

    public II(Integer pointer) {
        this.pointer = pointer;
    }
    public String getBouderlineLayer(Integer pointer){
        if(exceptions.contains(pointer)){
            return pointer + "5";
        } else {
            return pointer + "0";
        }
    }
}
