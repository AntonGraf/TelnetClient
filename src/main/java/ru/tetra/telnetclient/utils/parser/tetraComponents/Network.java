package ru.tetra.telnetclient.utils.parser.tetraComponents;

import java.util.ArrayList;

/**
 * Create by graf-anton on 12.03.2021
 */
public class Network {

    //Имя сети
    private String name;
    //Сетевые элементы
    private ArrayList<TetraComponent> tetraComponents;

    public Network(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TetraComponent> getTetraComponents() {
        return tetraComponents;
    }

    public void setTetraComponents(ArrayList<TetraComponent> tetraComponents) {
        this.tetraComponents = tetraComponents;
    }

    public String getName() {
        return name;
    }
}
