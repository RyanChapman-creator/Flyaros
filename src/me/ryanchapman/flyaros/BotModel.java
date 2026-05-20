package me.ryanchapman.flyaros;

import java.util.Objects;

final class BotModel {

    private String name;
    private String userName;

    BotModel(final String name) {
        setName(name);
        this.userName = "user";
    }

    BotModel(final String name, final String userName) {
        setName(name);
        setUserName(userName);
    }

    final void setName(final String name) {
        this.name = Objects.requireNonNull(name);
    }

    final String getName() {
        return name;
    }

    final void setUserName(final String name) {
        this.userName = Objects.requireNonNull(name);
    }

    final String getUserName() {
        return userName;
    }
}
