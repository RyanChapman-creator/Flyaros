package me.ryanchapman.flyaros;

public class BotModel {

    private String name;

    public BotModel(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
