package me.ryanchapman.flyaros;

import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Deserializer {
    
    final BotModel deserialize(final List<String> data) throws ParseException {
        final int line = 0;
        if (data.isEmpty()) {
            final String errorFmt = "The provided file '%s' cannot be loaded due to a missing header.";
            throw new ParseException(errorFmt, line);
        }
        final Pattern headerPattern = Pattern.compile("^flyaros.datafile (?<version>v(?<major>\\d+)\\.(?<minor>\\d+)\\.(?<patch>\\d+))");
        final Matcher headerMatcher = headerPattern.matcher(data.get(line));
        if (!headerMatcher.matches()) {
            final String errorFmt = "The provided file '%s' cannot be loaded due to an invalid header.";
            throw new ParseException(errorFmt, line);
        } else if (headerMatcher.group("version").equals("v1.0.0")) {
            return v1_0_0(data, line+1);
        } else {
            final String errMsg = String.format("Flyaros datafile version %s is not supported.", headerMatcher.group("version"));
            throw new UnsupportedOperationException(errMsg);
        }
    }

    private final BotModel v1_0_0(final List<String> data, int line) throws ParseException {
        if (!data.get(line).startsWith("bot.name=")) {
            final String errorFmt = "The provided file '%s' cannot be loaded due to the missing field 'bot.name'.";
            throw new ParseException(errorFmt, line);
        }
        final String botName = BotLogic.normalize(data.get(line).substring("bot.name=".length()));
        return new BotModel(botName);
    }
}
