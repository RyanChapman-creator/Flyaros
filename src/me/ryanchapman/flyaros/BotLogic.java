package me.ryanchapman.flyaros;

import java.util.Objects;

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
            case "what is your name", "who are you", "whats your name" ->
                String.format("My name is %s.", BotLogic.capitalize(botModel.getName(), true));
            case "goodbye", "bye" -> {
                Main.running = false;
                yield "Goodbye, User!";
            }
            default -> {
                if (normalized.startsWith("your name is now ")) {
                    botModel.setName(normalized.substring("your name is now ".length()));
                    yield "I accept this new name.";
                }
                else if (normalized.startsWith("your name is ")) {
                    botModel.setName(normalized.substring("your name is ".length()));
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
        else if (!allWords)
            return Character.toUpperCase(x.charAt(0)) + x.substring(1).toLowerCase();
        else {
            StringBuilder sb = new StringBuilder(x);
            for (int i = 1; i < sb.length(); i++) {
                if (sb.charAt(i-1) == ' ')
                    sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
            }
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        }
    }
}
