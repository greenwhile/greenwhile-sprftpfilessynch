package ua.uhmc.sprftpfilessynch.grib2.header;

import java.util.stream.IntStream;

public class Header {
    private Integer WMO_BINARY_DATA_DETERMINATIVE_GRIB2_HEADER_LENGTH_LONDON = 21;
    private byte [] bytes;
    public Header(byte[] bytes) {
        this.bytes = bytes;
    }
    public Integer getLength(){
        return WMO_BINARY_DATA_DETERMINATIVE_GRIB2_HEADER_LENGTH_LONDON;
    }



    public Integer[] getFlagFieldSeparatorIndexes(Integer[] indexes) {
        for(int i = indexes[0]; i < indexes[1]; i++) {
            if ((char) bytes[i] == (char) 10) { // LF - Line Feed
                System.out.println((char) bytes[indexes[0]] + " " + (char) bytes[i]);
                return IntStream.of(indexes[0], i).boxed().toArray(Integer[]::new);
            }
        }
        return null;
    }
    public Integer[] getWmoSuperHeaderIndexes(Integer[] indexes) {
        Integer [] indxs = new Integer[2];
        Integer index = 0;
        System.out.println();
        System.out.print("WMO HEADER " + indexes[0] + " " + indexes[1]);
        System.out.println();
        for(int i = indexes[0]; i < indexes[1]; i++) {
            if ((char) bytes[i] == (char) 10) { // LF - Line Feed #1
                indxs[index++] = i;
            }
        }
        return indxs;
    }
    public Integer[] getWmoSimpleHeaderIndexes(Integer[] indexes) {
        Integer [] indxs = new Integer[2];
        Integer index = 1;
        System.out.println();
        System.out.print("SIMPLE HEADER " + indexes[0] + " " + indexes[1]);
        System.out.println();
        for(int i = indexes[1]; i >= indexes[0]; i--) {
            if ((char) bytes[i] == (char) 10 && index > -1) { // LF - Line Feed #1
                indxs[index--] = i;
            }
        }
        return indxs;
    }
    public Integer[] getHeaderIndexes(){
        boolean flag = false;
        Integer index = 0;
        Integer [] indexes = new Integer [2];
        for(int i = 0; i < bytes.length; i++) {
            if((char)bytes[i] == (char)1) { // SOH - START OF HEADING
                flag = true;
            }
            if(flag){
                char firstGribSymbol = (char)bytes[i];
                char secondGribSymbol = (char)bytes[i+1];
                char thirdGribSymbol = (char)bytes[i+2];
                char fourthGribSymbol = (char)bytes[i+3];

                if(firstGribSymbol == (char)71) { // (char)71 = G
                    if(secondGribSymbol == (char)82) { // (char)82 = R
                        if(thirdGribSymbol == (char)73) { // (char)73 = I
                            if(fourthGribSymbol == (char)66) { // (char)66 = B
                                indexes[1] = i - 3 - 1; //
                                indexes[0] = i - WMO_BINARY_DATA_DETERMINATIVE_GRIB2_HEADER_LENGTH_LONDON;

                                System.out.println( firstGribSymbol + " [0] " + (char)bytes[indexes[0]] + "  [1] " + (char)bytes[indexes[1]]);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return indexes;
    }
    public Integer getStartIndex(){
        boolean flag = false;
        Integer [] indexes = new Integer [2];
        Integer end_index = -1;
        for(int i = 0; i < bytes.length; i++) {
            if((char)bytes[i] == (char)1) { // SOH - START OF HEADING
                flag = true;
            }
            if(flag){
                char firstGribSymbol = (char)bytes[i];
                char secondGribSymbol = (char)bytes[i+1];
                char thirdGribSymbol = (char)bytes[i+2];
                char fourthGribSymbol = (char)bytes[i+3];

                if(firstGribSymbol == (char)71) { // (char)71 = G
                    if(secondGribSymbol == (char)82) { // (char)82 = R
                        if(thirdGribSymbol == (char)73) { // (char)73 = I
                            if(fourthGribSymbol == (char)66) { // (char)66 = B
                                return i - 1;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    public Integer getWmoHeaderLength() {
        boolean flag = false;
        Integer start_ind = -1;
        Integer end_index = -1;
        for(int i = 0; i < bytes.length; i++) {
            if((char)bytes[i] == (char)1) { // SOH - START OF HEADING
                start_ind = i;
                flag = true;
            }
            if(flag){
                char firstGribSymbol = (char)bytes[i];
                char secondGribSymbol = (char)bytes[i+1];
                char thirdGribSymbol = (char)bytes[i+2];
                char fourthGribSymbol = (char)bytes[i+3];

                if(firstGribSymbol == (char)71) { // (char)71 = G
                    if(secondGribSymbol == (char)82) { // (char)82 = R
                        if(thirdGribSymbol == (char)73) { // (char)73 = I
                            if(fourthGribSymbol == (char)66) { // (char)66 = B
                                end_index = i - 1;
                                System.out.println("WMO Header");
                                System.out.println("start: " + start_ind + "  " + "end: " + end_index);
                                for(int j = start_ind; j <= end_index; j++){
                                    System.out.print((char)bytes[j]);
                                }
                                return i - 1;
                            }
                        }
                    }
                }
            }
        }
        return (start_ind !=-1 && end_index !=-1) ? end_index - start_ind : null;
    }
    private Integer getEndOfHeader(Integer startIndex){
        for(int i = startIndex; i >= startIndex - WMO_BINARY_DATA_DETERMINATIVE_GRIB2_HEADER_LENGTH_LONDON; i--){
            if(bytes[i]==10 && Character.isDigit((char)bytes[i-1])){
                return i-1;
            }
        }
        return null;
    }
    public Integer getEndIndex(){
        Integer index = null;
        boolean flag = false;
        for(int i = 0; i < bytes.length - 4; i++) {
            if((char)bytes[i] == (char)1){ // SOH - START OF HEADING
                flag = true;
            }
            if(flag){
                char firstGribSymbol =  (char)bytes[i];
                char secondGribSymbol = (char)bytes[i+1];
                char thirdGribSymbol =  (char)bytes[i+2];
                char fourthGribSymbol = (char)bytes[i+3];

                if(firstGribSymbol == (char)71) { // (char)71 = G
                    if(secondGribSymbol == (char)82) { // (char)82 = R
                        if(thirdGribSymbol == (char)73) { // (char)73 = I
                            if(fourthGribSymbol == (char)66) { // (char)66 = B
                                index = getEndOfHeader(i);
                            }
                        }
                    }
                }
            }
        }
        return index;
    }
}
