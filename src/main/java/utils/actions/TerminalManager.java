package utils.actions;

import utils.logs.LogsManager;

public class TerminalManager {
    public static void executeTerminalCommand(String... command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LogsManager.error("Terminal command failed with exit code: ", String.valueOf(exitCode));
            }
        }
        catch (Exception e) {
            LogsManager.error("Error executing terminal command: ", e.getMessage());
        }
    }
}
