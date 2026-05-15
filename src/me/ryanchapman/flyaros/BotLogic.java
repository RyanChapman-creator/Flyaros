package me.ryanchapman.flyaros;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class BotLogic {
    
    private final BotModel botModel;

    BotLogic(BotModel botModel) {
        this.botModel = Objects.requireNonNull(botModel);
    }

    final String respond(final String prompt) {
        final String normalized = BotLogic.normalize(prompt);
        return switch (normalized) {
            case "hello", "hi", "whats up" -> "Hello, User!";
            case "how are you", "how are you doing" -> "I am doing good.";
            case "what is your name",  "whats your name" ->
                String.format("My name is %s.", BotLogic.capitalize(botModel.getName(), true));
            case "who are you" ->
                String.format("I am %s.", BotLogic.capitalize(botModel.getName(), true));
            case "goodbye", "bye" -> {
                Main.running = false;
                yield "Goodbye, User!";
            }
            default -> {
                final Pattern pattern = Pattern.compile("^your name is (now )?(?<name>[\\w ]+)");
                final Matcher matcher = pattern.matcher(normalized);
                if (matcher.find()) {
                    botModel.setName(matcher.group("name"));
                    yield "I accept this new name.";
                }
                else if (normalized.startsWith("you are now ")) {
                    botModel.setName(normalized.substring("you are now ".length()));
                    yield "I accept this new name.";
                }
                else yield "I don't understand.";
            }
        };
    }

    static final String normalize(final String x) {
        final StringBuilder sb = new StringBuilder();
        boolean word = false;
        for (char c : x.toCharArray()) {
            if (word && Character.isWhitespace(c)) {
                word = false;
                sb.append(' ');
            }
            else if (Character.isLetterOrDigit(c)) {
                word = true;
                sb.append(c);
            }
        }
        if (sb.charAt(sb.length()-1) == ' ') {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString().toLowerCase();
    }

    static final String capitalize(final String x, final boolean allWords) {
        if (x.isEmpty()) return x;

        StringBuilder sb = new StringBuilder(x.length())
            .append(Character.toUpperCase(x.charAt(0)));
        for (int i = 1; i < x.length(); i++) {
            if (allWords && x.charAt(i-1) == ' ')
                sb.append(Character.toUpperCase(x.charAt(i)));
            else
                sb.append(Character.toLowerCase(x.charAt(i)));
        }
        return sb.toString();
    }
}
