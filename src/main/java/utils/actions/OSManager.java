package utils.actions;

import utils.dataManager.PropertyReader;

public class OSManager {
    public enum OS {
        WINDOWS,
        MAC,
        LINUX
    }

    public static OS getOperatingSystem() {
        String osName = PropertyReader.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return OS.WINDOWS;
        } else if (osName.contains("mac")) {
            return OS.MAC;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            return OS.LINUX;
        } else {
            throw new UnsupportedOperationException("Unsupported operating system: " + osName);
        }
    }
}
