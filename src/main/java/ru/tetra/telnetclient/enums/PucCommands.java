package ru.tetra.telnetclient.enums;

public enum PucCommands{
    GPSD("msuc gpsd");

    private String commands;

    PucCommands(String commands) {
        this.commands = commands;
    }

    public String getCommands() {
        return commands;
    }
}
