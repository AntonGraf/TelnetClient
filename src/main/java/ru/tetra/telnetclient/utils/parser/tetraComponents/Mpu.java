package ru.tetra.telnetclient.utils.parser.tetraComponents;

import ru.tetra.telnetclient.utils.parser.enums.NetworkElementType;
import ru.tetra.telnetclient.utils.parser.tetraComponents.software.Component;

import java.util.ArrayList;

/**
 * Create by graf-anton on 12.03.2021
 */
public class Mpu extends NetworkElement implements TetraComponent {

    //Список программных компонентов
    private ArrayList<Component> softwareComponents;

    public Mpu(String name, String type) {
        super(name, type);
        this.softwareComponents = new ArrayList<>();
    }

    @Override
    public ArrayList<Component> getAllComponents() {
        return softwareComponents;
    }

    @Override
    public void printAlarms() {

    }

    @Override
    public void updateAlarm() {

    }

    @Override
    public void readAlarms() {

    }


    public String getName() {
        return name;
    }

    @Override
    public NetworkElementType getElementType() {
        return NetworkElementType.SCN;
    }


    public void setSoftwareComponents(ArrayList<Component> softwareComponents) {
        this.softwareComponents = softwareComponents;
    }

    @Override
    public String toString() {
        return "Mpu{" +
                "name='" + name + '\'' +
                ", softwareComponents=" + softwareComponents +
                '}';
    }
}
