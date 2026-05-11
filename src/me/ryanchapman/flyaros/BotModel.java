package me.ryanchapman.flyaros;

public class BotModel {

    private String name;

    public BotModel(String name) {
        setName(name);
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }
}
