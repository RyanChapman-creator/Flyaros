package me.ryanchapman.flyaros;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

final class BotModelDAO {
    
    /**
     * Loads a bot model from a Flyaros data file
     * @param file a file object containing the data to be loaded
     * @return the loaded bot model
     */
    final BotModel load(final File file) {
        if (!file.getName().endsWith(".dat") && !file.getName().endsWith(".fly"))
            throw new RuntimeException("Flyaros data files should end in either .dat or .fly");
        try (final FileReader fReader = new FileReader(file)) {
            final List<String> data = fReader.readAllLines();
            try {
                return new Deserializer().deserialize(data);
            } catch (final ParseException e) {
                System.err.println("A parse exception occurred on line " + (e.getErrorOffset()+1) + " (1-indexed)");
                throw new RuntimeException(String.format(e.getMessage().formatted(file.getPath()), e));
            }
        } catch (final FileNotFoundException e) {
            final String errorMsg = String.format("The provided file '%s' could not be opened.", file.getPath());
            throw new RuntimeException(errorMsg);
        } catch (final IOException e) {
            final String errorMsg = String.format("An error occurred while trying to read '%s'.", file.getPath());
            throw new RuntimeException(errorMsg);
        }
    }

    /**
     * Saves as a Flyaros data file
     * @param file a file object to where the data to be saved
     * @param botModel the bot model to be saved
     * @return the loaded bot model
     */
    final void save(final File file, final BotModel botModel) {
        if (!file.getName().endsWith(".dat") && !file.getName().endsWith(".fly"))
            throw new RuntimeException("Flyaros data files should end in either .dat or .fly");
        try (final FileWriter fWriter = new FileWriter(file)) {
            fWriter.write(new Serializer().serialize(botModel));
        } catch (final IOException e) {
            final String errorMsg = String.format("An error occurred while trying to write to '%s'. The data was not saved.", file.getPath());
            throw new RuntimeException(errorMsg);
        }
    }
}
