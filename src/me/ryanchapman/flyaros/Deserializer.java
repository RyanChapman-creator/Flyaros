package me.ryanchapman.flyaros;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        } else if (headerMatcher.group("version").equals("v1.1.0")) {
            return v1_1_0(data, line+1);
        } else if (headerMatcher.group("version").equals("v1.2.0")) {
            return v1_2_0(data, line+1);
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

    private final BotModel v1_1_0(final List<String> data, int line) throws ParseException {
        if (!data.get(line).startsWith("bot.name=")) {
            final String errorFmt = "The provided file '%s' cannot be loaded due to the missing field 'bot.name'.";
            throw new ParseException(errorFmt, line);
        }
        final String botName = BotLogic.normalize(data.get(line++).substring("bot.name=".length()));
        if (!data.get(line).startsWith("user.name=")) {
            final String errorFmt = "The provided file '%s' cannot be loaded due to the missing field 'user.name'.";
            throw new ParseException(errorFmt, line);
        }
        final String userName = BotLogic.normalize(data.get(line++).substring("user.name=".length()));
        return new BotModel(botName, userName);
    }

    private final BotModel v1_2_0(final List<String> data, int line) throws ParseException {
        final Map<String, String> attributes = new HashMap<>();
        for (; line < data.size(); ++line) {
            final String[] lineData = data.get(line).split("=", 2);
            if (lineData.length >= 2) {
                attributes.put(lineData[0], lineData[1]);
            }
        }
        if (!attributes.containsKey("bot.name")) {
            final String errorFmt = "The provided file '%s' cannot be loaded due to the missing field 'bot.name'.";
            throw new ParseException(errorFmt, line);
        }
        final String botName = BotLogic.normalize(attributes.get("bot.name"));
        if (!attributes.containsKey("user.name")) {
            final String errorFmt = "The provided file '%s' cannot be loaded due to the missing field 'user.name'.";
            throw new ParseException(errorFmt, line);
        }
        final String userName = BotLogic.normalize(attributes.get("user.name"));
        return new BotModel(botName, userName);
    }
}
