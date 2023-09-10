package ua.uhmc.sprftpfilessynch.handler;

import java.io.File;

public class LocalResource {
    String path;
    File file;

    public LocalResource(String path, File file) {
        this.path = path;
        this.file = file;
    }

    public String getPath() throws Exception {
        if(!isEmptyPath(this.path)){
            if(this.path.startsWith("/")){
                if(this.path.endsWith("/")){
                    return this.path;
                }
            }
            if(!this.path.startsWith("/")) {
                this.path = addBegginingSlash(this.path);
                if(this.path.endsWith("/")){
                    return this.path;
                } else {
                    this.path = addEnddiningSlash(this.path);
                    return this.path;
                }
            }
            if(this.path.startsWith("/")){
                if(!this.path.endsWith("/")){
                    this.path = addEnddiningSlash(this.path);
                    return this.path;
                }
            }
            else {
                throw new Exception("FROM LocalResource.class:create ---> Add custom wrong local resource path exception");
            }
        }
        return null;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static boolean isEmptyPath(String path){
        return path == null || path.isEmpty() || path.trim().isEmpty() || path.equals("/");
    }

    private String addBegginingSlash(String path){
        return !isEmptyPath(path) && this.path.startsWith("/") ? path : "/"+path;
    }

    private String addEnddiningSlash(String path){
        return !isEmptyPath(path) && this.path.endsWith("/") ? path : path+"/";
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "LocalResource{" +
                "path='" + this.path + '\'' +
                ", file=" + this.file +
                '}';
    }
}
