package ru.tetra.telnetclient.utils.parser.tetraComponents;

/**
 * Create by graf-anton on 12.03.2021
 *
 * Класс сетевого элемента системы Янтарь-T IP
 */
public abstract class NetworkElement {
    //Имя сетевого элемента
    protected String name;
    //Тип mpu, BS
    protected String type;

    public NetworkElement(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
