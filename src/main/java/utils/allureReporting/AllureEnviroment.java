package utils.allureReporting;

import com.google.common.collect.ImmutableMap;
import utils.logs.LogsManager;

import java.io.File;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static utils.dataManager.PropertyReader.getProperty;

public class AllureEnviroment {
    public static void setEnviromentVariable(){
        allureEnvironmentWriter(ImmutableMap.<String, String>builder()
            .put("OS", getProperty("os.name"))
            .put("java version", getProperty("java.runtime.version"))
            .put("Browser", getProperty("browserType"))
            .put("Execution Type", getProperty("executionType"))
            .put("URL", getProperty("baseUrl"))
            .build(),String.valueOf(AllureConstants.RESULTS_FOLDER)+ File.separator);

        LogsManager.info("Allure environment variables set successfully.");
        AllureBinary.downloadAndExtract();
    }
}
