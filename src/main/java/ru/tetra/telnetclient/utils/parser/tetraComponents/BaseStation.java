package ru.tetra.telnetclient.utils.parser.tetraComponents;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.tetra.telnetclient.utils.parser.enums.NetworkElementType;
import ru.tetra.telnetclient.utils.parser.tetraComponents.software.Component;

import java.util.ArrayList;

/**
 * Create by graf-anton on 12.03.2021
 */
@ToString
@EqualsAndHashCode
public class BaseStation extends NetworkElement  implements TetraComponent{

    //Программные компоненты
    private ArrayList<Component> components;

    public BaseStation(String name, String type) {
        super(name, type);
        this.components = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Component> getAllComponents() {
        return components;
    }

    public void setComponents(ArrayList<Component> components) {
        this.components = components;
    }


    /**
     * Возращает программный компонент по его типу
     * @param ncType    - тип компонента
     * @return          - программный компонент
     */
    public ArrayList<Component> getComponents(String ncType) {

        ArrayList<Component> componentArrayList = new ArrayList<>();

        for(Component component : components) {

            if(component.getNcType().equals(ncType)) {
                componentArrayList.add(component);
            }
        }

        return componentArrayList;
    }

    @Override
    public NetworkElementType getElementType() {
        return NetworkElementType.BS;
    }
}
