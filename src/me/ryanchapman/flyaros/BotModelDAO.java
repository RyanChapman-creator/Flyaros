package me.ryanchapman.flyaros;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BotModelDAO {
    
    /**
     * Load Flyaros data file v1.0.0
     * @param file a file object containing the data to be loaded
     * @return the loaded bot model
     */
    public final BotModel load(File file) {
        if (!file.getName().endsWith(".dat") && !file.getName().endsWith(".fly"))
            throw new RuntimeException("Flyaros data files should end in either .dat or .fly");
        try (FileReader fReader = new FileReader(file)) {
            final List<String> data = fReader.readAllLines();
            int line = 0;
            if (!data.get(line).equals("flyaros.datafile v1.0.0")) {
                final String errorMsg = String.format("The provided file '%s' cannot be loaded due to an invalid header.", file.getPath());
                throw new RuntimeException(errorMsg);
            }
            ++line;
            if (!data.get(line).startsWith("bot.name=")) {
                final String errorMsg = String.format("The provided file '%s' cannot be loaded due to the missing field 'bot.name'.", file.getPath());
                throw new RuntimeException(errorMsg);
            }
            String botName = Main.normalize(data.get(line).substring("bot.name=".length()));
            return new BotModel(botName);
        } catch (FileNotFoundException e) {
            final String errorMsg = String.format("The provided file '%s' could not be opened.", file.getPath());
            throw new RuntimeException(errorMsg);
        } catch (IOException e) {
            final String errorMsg = String.format("An error occurred while trying to read '%s'.", file.getPath());
            throw new RuntimeException(errorMsg);
        }
    }

    /**
     * Save to Flyaros data file v1.0.0
     * @param file a file object to where the data to be saved
     * @param botModel the bot model to be saved
     * @return the loaded bot model
     */
    public final void save(File file, BotModel botModel) {
        if (!file.getName().endsWith(".dat") && !file.getName().endsWith(".fly"))
            throw new RuntimeException("Flyaros data files should end in either .dat or .fly");
        try (FileWriter fWriter = new FileWriter(file)) {
            StringBuilder sb = new StringBuilder("flyaros.datafile v1.0.0\n");
            sb.append("bot.name=").append(botModel.getName());
            fWriter.write(sb.toString());
        } catch (IOException e) {
            final String errorMsg = String.format("An error occurred while trying to write to '%s'. The data was not saved.", file.getPath());
            throw new RuntimeException(errorMsg);
        }
    }
}
