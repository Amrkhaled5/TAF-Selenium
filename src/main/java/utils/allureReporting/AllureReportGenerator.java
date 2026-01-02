package utils.allureReporting;

import org.apache.commons.io.FileUtils;
import utils.actions.FilesManager;
import utils.actions.OSManager;
import utils.actions.TerminalManager;
import utils.logs.LogsManager;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FileUtils.*;
import static utils.dataManager.PropertyReader.getProperty;

public class AllureReportGenerator {

    public static void generateAllureReport(boolean isSingleFile) {
        Path outputFolder = isSingleFile ? AllureConstants.REPORT_PATH : AllureConstants.FULL_REPORTS_PATH;
        List<String>command=new ArrayList<>(List.of(
                AllureBinary.getExecutable().toString(),
                "generate",
               AllureConstants.RESULTS_FOLDER.toString(),
                "-o",
                String.valueOf(outputFolder),
                "--clean"
        ));
        if (isSingleFile) {
            command.add("--single-file");
        }
        TerminalManager.executeTerminalCommand(command.toArray(new String[0]));
    }

    public static void openAllureReport(String reportFileName) {
        if (!getProperty("executionType").toLowerCase().contains("local")) {
            return;
        }
        Path reportPath = AllureConstants.REPORT_PATH.resolve(reportFileName);
        switch (OSManager.getOperatingSystem()){
            case WINDOWS -> TerminalManager.executeTerminalCommand("cmd.exe", "/c", "start", "", reportPath.toString());
            case MAC, LINUX -> TerminalManager.executeTerminalCommand("open", reportPath.toString());
            default -> LogsManager.error("Unsupported operating system for opening report: " + OSManager.getOperatingSystem());

        }
    }

    public static void copyHistoryToResultsFolder() {
        try{
            copyDirectory(AllureConstants.HISTIRY_FOLDER.toFile(), AllureConstants.RESULTS_HISTORY_FOLDER.toFile());
        }
        catch (Exception e){
            LogsManager.error("Failed to copy history folder to results folder. Error: ",e.getMessage());
        }
    }

    public static String renameReport(){
        String timestamp= String.valueOf(System.currentTimeMillis());
        String newReportName=AllureConstants.REPORT_PREFIX+timestamp+AllureConstants.REPORT_EXTENSION;
        FilesManager.renameFile(AllureConstants.REPORT_PATH.resolve(AllureConstants.INDEX_HTML).toString(), newReportName);

        return newReportName;
    }
}
