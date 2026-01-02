package utils.allureReporting;

import org.jsoup.Jsoup;
import utils.actions.OSManager;
import utils.actions.TerminalManager;
import utils.logs.LogsManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static utils.actions.OSManager.OS.WINDOWS;

public class AllureBinary {


    private static class LazyHolder {
        private static final String VERSION=latestVersion();

        private static String latestVersion(){
            try {
                String url = Jsoup.connect("https://github.com/allure-framework/allure2/releases/latest")
                        .followRedirects(true).execute().url().toString();

                return url.split("/tag/")[1];
            }
            catch (Exception e){
                throw new RuntimeException("Failed to fetch latest Allure version: " + e.getMessage());
            }
        }
    }

    private static Path downloadZip(String version) {
        try {
            String downloadUrl = AllureConstants.ALLURE_ZIP_BASE_URL + version + "/allure-commandline-" + version + ".zip";
            Path zipPath =Paths.get(AllureConstants.EXTRACTION_DIR.toString(), "allure-" + version + ".zip");

            if(!Files.exists(zipPath)){
                Files.createDirectories(AllureConstants.EXTRACTION_DIR);
                try(BufferedInputStream in = new BufferedInputStream(new URI(downloadUrl).toURL().openStream())) {
                    OutputStream out = Files.newOutputStream(zipPath);
                    in.transferTo(out);
                }
                catch (Exception e) {
                    LogsManager.error("Failed to download Allure binary: ", e.getMessage());
                }
            }
            return zipPath;
        }
        catch (Exception e) {
            LogsManager.error("Failed to download Allure binary: ", e.getMessage());
            return Paths.get("");
        }
    }

    private static void exreactZip(Path zipPath) {
        try (ZipInputStream zis =new ZipInputStream((Files.newInputStream(zipPath)))){
            ZipEntry entry;
            while((entry=zis.getNextEntry())!=null){
                Path newPath=Paths.get(AllureConstants.EXTRACTION_DIR.toString(), File.separator,entry.getName());
                if(entry.isDirectory()){
                    Files.createDirectories(newPath);
                } else {
                    Files.createDirectories(newPath.getParent());
                    Files.copy(zis,newPath);
                }
            }
        }
        catch (Exception e) {
            LogsManager.error("Failed to extract Allure binary: ", e.getMessage());
        }
    }

    public static void downloadAndExtract() {
        try{
            String version = LazyHolder.VERSION;
            Path extractionDir=Paths.get(AllureConstants.EXTRACTION_DIR.toString(), "allure-" + version);
            if(!Files.exists(extractionDir)){
                LogsManager.info("Downloading Allure version: " + version);
                return;
            }
            if(!OSManager.getOperatingSystem().equals(OSManager.OS.WINDOWS)){
                TerminalManager.executeTerminalCommand("chmod" +"u+x"+AllureConstants.USER_DIR.toString());
            }
            Path zipPath = downloadZip(version);
            exreactZip(zipPath);
            LogsManager.info("Allure binary downloaded and extracted successfully.");

            if(!OSManager.getOperatingSystem().equals(WINDOWS)){
                TerminalManager.executeTerminalCommand("chmod" +"u+x"+getExecutable().toString());
            }
            Files.deleteIfExists(Files.list(AllureConstants.EXTRACTION_DIR)
                    .filter(p -> p.toString().endsWith(".zip"))
                    .findFirst()
                    .orElseThrow());
        }
        catch (Exception e){
            LogsManager.error("Failed to download and extract Allure binary: ", e.getMessage());
        }
    }

    public static Path getExecutable(){
        String version = LazyHolder.VERSION;
        Path binaryPath=Paths.get(AllureConstants.EXTRACTION_DIR.toString(), "allure-" + version, "bin", "allure");
        return OSManager.getOperatingSystem()==OSManager.OS.WINDOWS? binaryPath.resolveSibling(binaryPath.getFileName()+".bat") : binaryPath;
    }
}
