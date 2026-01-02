package utils.allureReporting;

import utils.actions.FilesManager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.dataManager.PropertyReader.getProperty;

public class AllureConstants {
    public static final Path USER_DIR= Paths.get(getProperty("user.dir"), File.separator);
    public static final Path USER_HOME= Paths.get(getProperty("user.home"), File.separator);

    public static final Path RESULTS_FOLDER=Paths.get(String.valueOf(USER_DIR),"test-results","allure-results",File.separator);
    public static final Path REPORT_PATH=Paths.get(String.valueOf(USER_DIR),"test-results","reports",File.separator); // single report path .html
    public static final Path FULL_REPORTS_PATH=Paths.get(String.valueOf(USER_DIR),"test-results","reports","full-report",File.separator);

    public static final Path HISTIRY_FOLDER=Paths.get((FULL_REPORTS_PATH.toString()),"history",File.separator); // after generating report
    public static final Path RESULTS_HISTORY_FOLDER=Paths.get((RESULTS_FOLDER.toString()),"history",File.separator); // before generating report

    public static final String INDEX_HTML="index.html";
    public static final String REPORT_PREFIX="AllureReport_";
    public static final String REPORT_EXTENSION=".html";

    public static final String ALLURE_ZIP_BASE_URL="https://repo1.maven.org/maven2/io/qameta/allure/allure-commandline/";
    public static final Path EXTRACTION_DIR=Paths.get(String.valueOf(USER_HOME),".m2/repository/allure",File.separator);

}
