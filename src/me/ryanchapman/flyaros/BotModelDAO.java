package me.ryanchapman.flyaros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BotModelDAO {
    
    /**
     * Load Flyaros data file v1.0.0
     * @param file a file object containing the data to be loaded
     * @return the loaded bot model
     */
    public final BotModel load(File file) {
        if (!file.getName().endsWith(".dat") && !file.getName().endsWith(".fly"))
            throw new RuntimeException("Flyaros data files should end in either .dat or .fly");
        try (FileInputStream fis = new FileInputStream(file)) {
            final byte[] bytes = fis.readNBytes(1024);
            String data = new String(bytes)
                .replace("\r", "");
            if (!data.startsWith("flyaros.datafile v1.0.0\n")) {
                final String errorMsg = String.format("The provided file '%s' cannot be loaded due to an invalid header.", file.getPath());
                throw new RuntimeException(errorMsg);
            }
            data = data.substring(data.indexOf('\n')+1);
            if (!data.startsWith("bot.name=")) {
                final String errorMsg = String.format("The provided file '%s' cannot be loaded due to the missing field 'bot.name'.", file.getPath());
                throw new RuntimeException(errorMsg);
            }
            String botName = Main.normalize(data.substring("bot.name=".length()));
            return new BotModel(botName);
        } catch (FileNotFoundException e) {
            final String errorMsg = String.format("The provided file '%s' could not be opened.", file.getPath());
            throw new RuntimeException(errorMsg);
        } catch (IOException e) {
            final String errorMsg = String.format("An error occurred while trying to read '%s'.", file.getPath());
            throw new RuntimeException(errorMsg);
        }
    }
}
