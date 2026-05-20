package me.ryanchapman.flyaros;

public class Serializer {
    
    final String serialize(final BotModel botModel) {
        final StringBuilder sb = new StringBuilder("flyaros.datafile v1.1.0\n");
        sb.append("bot.name=").append(botModel.getName()).append('\n');
        sb.append("user.name=").append(botModel.getUserName());
        return sb.toString();
    }
}
