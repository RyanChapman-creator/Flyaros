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
            case "hello", "hi", "whats up" ->
                String.format("Hello, %s!", BotLogic.capitalize(botModel.getUserName(), true));
            case "how are you", "how are you doing" -> "I am doing good.";
            case "what is your name",  "whats your name" ->
                String.format("My name is %s.", BotLogic.capitalize(botModel.getName(), true));
            case "who are you" ->
                String.format("I am %s.", BotLogic.capitalize(botModel.getName(), true));
            case "what is my name", "whats my name" ->
                String.format("Your name is %s.", BotLogic.capitalize(botModel.getUserName(), true));
            case "who am i", "do you know who i am" ->
                String.format("You are %s.", BotLogic.capitalize(botModel.getUserName(), true));
            case "goodbye", "bye" -> {
                Main.running = false;
                yield String.format("Goodbye, %s!", BotLogic.capitalize(botModel.getUserName(), true));
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
                else if (normalized.equals("is your name"))
                    yield "Is my name what?";
                else if (normalized.startsWith("is ") && normalized.endsWith(" your name")) {
                    final String suggestedName = normalized.substring("is ".length(), normalized.length()-" your name".length());
                    if (suggestedName.equals(botModel.getName()))
                        yield "Yes, that is my name.";
                    else
                        yield "No, that is not my name.";
                }
                else if (normalized.startsWith("is your name ")) {
                    final String suggestedName = normalized.substring("is your name ".length());
                    if (suggestedName.equals(botModel.getName()))
                        yield "Yes, that is my name.";
                    else
                        yield "No, that is not my name.";
                }
                else if (normalized.startsWith("are you ")) {
                    final String suggestedName = normalized.substring("are you ".length());
                    if (suggestedName.equals(botModel.getName()))
                        yield "Yes, that is my name.";
                    else
                        yield "No, that is not my name.";
                }
                else if (normalized.startsWith("my name is now ")) {
                    botModel.setUserName(normalized.substring("my name is now ".length()));
                    yield "I understand.";
                }
                else if (normalized.startsWith("i am now ")) {
                    botModel.setUserName(normalized.substring("i am now ".length()));
                    yield "I understand.";
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
