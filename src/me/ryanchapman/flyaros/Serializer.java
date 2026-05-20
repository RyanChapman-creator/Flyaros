package me.ryanchapman.flyaros;

public class Serializer {
    
    final String serialize(final BotModel botModel) {
        final StringBuilder sb = new StringBuilder("flyaros.datafile v1.0.0\n");
        sb.append("bot.name=").append(botModel.getName());
        return sb.toString();
    }
}
