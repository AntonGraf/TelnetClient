package ru.tetra.telnetclient.enums;

public enum Commands {
    AUTHORIZATION("u s3"),
    ALARM("o ao"),
    FULL_ALARM("o ao -A"),
    PERFORMANCE("o pc"),
    FULL_PERFORMANCE("o pc -A"),
    SAVE("p save"),
    SET("p s"),
    GET("p l -A");
    private String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
