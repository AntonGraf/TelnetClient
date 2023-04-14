package ru.tetra.telnetclient.utils.parser.tetraComponents;

import ru.tetra.telnetclient.utils.parser.enums.NetworkElementType;
import ru.tetra.telnetclient.utils.parser.tetraComponents.software.Component;

import java.util.ArrayList;

/**
 * Create by graf-anton on 17.03.2021
 *
 * Интерфейс сетевого компонента TETRA
 */
public interface TetraComponent {

    /**
     * Получает список программных компонентов
     * @return  - список программных компонентов
     */
    ArrayList<Component> getAllComponents();

    /**
     * Возвращает имя компонента
     * @return
     */
    String getName();

    NetworkElementType getElementType();

    String getType();
}
