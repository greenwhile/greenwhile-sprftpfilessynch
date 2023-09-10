package ua.uhmc.sprftpfilessynch.handler;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class LocalResourcesPathManager {
    public LocalResource create(LocalResource resource) throws Exception {
        String resourcepath = resource.getPath();
        File resourcefile = resource.getFile();

        int startDirIndex = -1;
        String dir = null;
        int endDirIndex = -1;
        String restpath = null;

        if(!isEmptyPath(resourcepath)) {
            startDirIndex = resourcepath.indexOf("/")+1;
            dir = resourcepath.substring(startDirIndex);
            endDirIndex =  dir.indexOf("/")+1;
            restpath = resourcepath.substring(endDirIndex);
            System.out.println("dir: " + startDirIndex + " " + endDirIndex + " " +  dir.substring(0,dir.indexOf("/")) + "   " + restpath);
            System.out.println("lr -> " + new LocalResource(restpath, new File(resourcefile, dir.substring(0,dir.indexOf("/")))));
            if(restpath.equals("/")) return new LocalResource(restpath, new File(resourcefile, dir.substring(0,dir.indexOf("/"))));
        }

        return !isEmptyPath(resourcepath) ? create (
                new LocalResource(restpath, new File(resourcefile, dir.substring(0,dir.indexOf("/"))))) :null;
    }

    public static boolean isEmptyPath(String path){
        return path == null || path.isEmpty() || path.trim().isEmpty() || path.equals("/") || path.equals("");
    }
}
