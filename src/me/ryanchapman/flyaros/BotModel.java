package me.ryanchapman.flyaros;

import java.util.Objects;

final class BotModel {

    private String name;
    private final String userName = "user";

    BotModel(final String name) {
        setName(name);
    }

    final void setName(final String name) {
        this.name = Objects.requireNonNull(name);
    }

    final String getName() {
        return name;
    }

    final String getUserName() {
        return userName;
    }
}
