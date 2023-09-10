package ua.uhmc.sprftpfilessynch.grib2.body;

public class Body {
    byte [] bytes;
    public Body(byte[] bytes) {
        this.bytes = bytes;
    }
    public Integer getStartIndex(){
        boolean flag = false;
        for(int i = 0; i < bytes.length - 4; i++) {
            if((char)bytes[i] == (char)1){ // SOH - START OF HEADING
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
                                return i;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public Integer getEndIndex(){
        boolean flag = false;
        for(int i = 0; i < bytes.length; i++) {
            if((char)bytes[i] == (char)3) { // i = ETX LAST symbol
                flag = true;
            }
            if(flag) {
                /*
                char firstGribSymbol = (char)bytes[i-3];
                char secondGribSymbol = (char)bytes[i-2];
                char thirdGribSymbol = (char)bytes[i-1];
                char fourthGribSymbol = (char)bytes[i];
                */
                if((char)bytes[i] == (char)55){
                    if((char)bytes[i-1] == (char)55){
                        if((char)bytes[i-2] == (char)55){
                            if((char)bytes[i-3] == (char)55){
                                return i;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
