package utils.actions;

import org.apache.commons.io.FileUtils;
import utils.logs.LogsManager;

import java.io.File;

public class FilesManager {
    private static final String USER_DIR=System.getProperty("user.dir")+File.separator;

    private FilesManager() {
        // Private constructor to prevent instantiation
    }

    public static void createDirectory(String path){
        try{
            File file=new File(USER_DIR+path);
            if (!file.exists()) {
                file.mkdirs();
                LogsManager.info("Directory created at: ",file.getAbsolutePath());
            } else {
                LogsManager.info("Directory already exists at: ",file.getAbsolutePath());
            }
        }
        catch (Exception e){
            LogsManager.error("Failed to create directory: ",path," Error: ",e.getMessage());
        }
    }

    public static void renameFile(String oldPath, String newPath){
        try{
            File oldFile=new File(USER_DIR+oldPath);
            File newFile=new File(USER_DIR+newPath);
            if(oldFile.exists()){
                boolean renamed=oldFile.renameTo(newFile);
                if(renamed){
                    LogsManager.info("File renamed from ",oldFile.getAbsolutePath()," to ",newFile.getAbsolutePath());
                } else {
                    LogsManager.error("Failed to rename file from ",oldFile.getAbsolutePath()," to ",newFile.getAbsolutePath());
                }
            } else {
                LogsManager.error("File to rename does not exist: ",oldFile.getAbsolutePath());
            }
        }
        catch (Exception e){
            LogsManager.error("Failed to rename file from ",oldPath," to ",newPath," Error: ",e.getMessage());
        }
    }

    public static void cleanDirectory(File file) {
        try{
            FileUtils.deleteQuietly(file);
        }
        catch (Exception e){
            LogsManager.error("Failed to clean directory: ",file.getAbsolutePath()," Error: ",e.getMessage());
        }

    }

}
