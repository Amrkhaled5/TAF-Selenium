package utils.allureReporting;

import io.qameta.allure.Allure;
import utils.logs.LogsManager;
import org.apache.logging.log4j.LogManager;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.core.LoggerContext;
import utils.media.ScreenRecord;

import static utils.dataManager.PropertyReader.getProperty;

public class AllureAttachmentManager {

    public static void attachScreenshot(String name, String path){
        try{
            Path screenshotPath=Path.of(path);
            if(Files.exists(screenshotPath)){
                Allure.addAttachment(name, Files.newInputStream(screenshotPath));
            }
            else{
                LogsManager.error("Screenshot file does not exist at path: ",path);
            }
        }
        catch (Exception e){
            LogsManager.error("Failed to attach screenshot: ",e.getMessage());
        }
    }

    public static void attachLogs(){
        try{
            LogManager.shutdown();
            File logFile=new File(LogsManager.LOGS_PATH+"logs.log");
            ((LoggerContext) LogManager.getContext(false)).reconfigure();
            if(logFile.exists()) {
                Allure.addAttachment("logs.logs", Files.newInputStream(logFile.toPath()));
            }
        }
        catch (Exception e){
            LogsManager.error("Failed to attach logs: ",e.getMessage());
        }
    }

    public static void attaachRecords(String testName){
        if(getProperty("recordTests").equalsIgnoreCase("true")){
            try{
                File record = new File(ScreenRecord.SCREENRECORDPATH + testName);
                if(record != null && record.getName().endsWith(".mp4")){
                    Allure.addAttachment("Test Eexecution Video", Files.newInputStream(record.toPath()).toString(),".mp4");
                }
            }
            catch (Exception e){
                LogsManager.error("Failed to attach video record: ",e.getMessage());
            }
        }
    }


}
