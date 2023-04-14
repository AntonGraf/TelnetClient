package ru.tetra.telnetclient.utils.parser.performance;

import java.time.LocalDateTime;

/**
 * Create by graf-anton on 24.12.2020
 *
 * Параметр производительности компонентов системы Янтарь-T IP
 */
public class PerformanceEvent {

    //Имя параметра
    private String name;
    //Идентификатор
    private String id;

    private LocalDateTime time;

    private String value;

    private String componentName;

    private String networkElementName;

    public PerformanceEvent(String name, LocalDateTime time, String value, String componentName, String networkElementName) {
        this.name = name;
        this.time = time;
        this.value = value;
        this.componentName = componentName;
        this.networkElementName = networkElementName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getNetworkElementName() {
        return networkElementName;
    }

    public void setNetworkElementName(String networkElementName) {
        this.networkElementName = networkElementName;
    }
}
