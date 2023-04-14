package ru.tetra.telnetclient.utils.parser;

import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.tetra.telnetclient.utils.parser.tetraComponents.*;
import ru.tetra.telnetclient.utils.parser.tetraComponents.software.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by graf-anton on 12.03.2021
 *
 * Парсит конфигурационный файл системы Янтарь-T IP
 */
public class NetworkConfigParser {

    //Конфигурационный файл nmc511
    private File configurationFile;
    //Конфигурация сети
    private Configuration configNetwork;
    //Список программных компонентов, подлежащих парсингу
    private ArrayList<String> parsedSoftwareComponent;

    public NetworkConfigParser(File configurationFile) {
        this.configurationFile = configurationFile;
    }

    public Configuration getConfigNetwork() {
        return configNetwork;
    }

    /**
     * Получает конфигурацию сети
     * @param parsedSoftwareComponent - программные компоненты, подлежащие парсингу
     */
    public void parseConfiguration(ArrayList<String> parsedSoftwareComponent) {

        if(!parsedSoftwareComponent.isEmpty()) {
            this.parsedSoftwareComponent = parsedSoftwareComponent;
        } else {
            this.parsedSoftwareComponent = initialDefaultListParsedComponent();
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        org.jdom2.Document jdomDoc;

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(configurationFile);
            DOMBuilder domBuilder = new DOMBuilder();
            jdomDoc = domBuilder.build(document);

            Element configuration = jdomDoc.getRootElement();

            configNetwork = new Configuration(configuration.getAttributeValue("version"),
                    configuration.getAttributeValue("packageVersions"),
                    configuration.getAttributeValue("interfaceVersion"));

            configNetwork.setDatabases(parseDatabases(configuration.getChild("Databases").getChildren("Database")));
            configNetwork.setNetwork(parseNetwork(configuration.getChild("Network")));

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
     public void parseConfiguration() {
        parseConfiguration(new ArrayList<>());
     }


    private ArrayList<String> initialDefaultListParsedComponent(){

        ArrayList<String> parsedComponent = new ArrayList<>();
        parsedComponent.add("TOS");
        parsedComponent.add("TAP");
        parsedComponent.add("COS");
        parsedComponent.add("PUC");
        parsedComponent.add("HWG");
        parsedComponent.add("IPS");
        parsedComponent.add("SMG");
        parsedComponent.add("GMD");
        parsedComponent.add("Nem523DownloadManagement");

        return parsedComponent;
    }
    /**
     * Получает список БД из файла конфигурации
     * @param elementList   - список элементов xml
     * @return              - список БД
     */
    private ArrayList<Database> parseDatabases(List<Element> elementList) {

        ArrayList<Database> databases = new ArrayList<>();

        for(Element element : elementList) {
            databases.add(new Database(
                    element.getAttributeValue("displayName"),
                    Integer.parseInt(element.getAttributeValue("portNumber")),
                    element.getAttributeValue("serverName"),
                    element.getAttributeValue("databaseName")
            ));
        }
        return databases;

    }

    /**
     * Получает сеть из файла конфигурации
     * @param element   - элемент xml
     * @return          - сеть
     */
    private Network parseNetwork(Element element) {

        Network network = new Network(element.getAttributeValue("displayName"));
        network.setTetraComponents(parseClusters(element.getChildren()));

        return network;
    }

    /**
     * Получает кластер из файла конфигурации
     * @param elementList   - список элементов xml
     * @return              - кластер
     */
    private ArrayList<TetraComponent> parseClusters(List<Element> elementList) {

        ArrayList<TetraComponent> tetraComponents = new ArrayList<>();
        for(Element element : elementList) {

            if(checkAttribute(element, "isCluster", "true")
                    && hasChildren(element.getChild("Composite"))) {

                tetraComponents.addAll(parseNetworkElements(element.getChildren()));
            }
        }

        return tetraComponents;
    }

    /**
     * Получает список сетевых компонентов
     * @param elementList   - список элементов xml
     * @return              - список сетевых компонентов
     */
    private ArrayList<TetraComponent> parseNetworkElements(List<Element> elementList) {

        ArrayList<TetraComponent> tetraComponents = new ArrayList<>();

        for(Element element : elementList){

            if(checkAttribute(element, "displayName", "NetworkElements")) {

                for(Element childElement : element.getChildren()) {

                    if(checkAttribute(childElement, "isBaseStation","true")) {
                        tetraComponents.add(parseBaseStation(childElement));
                    } else if(checkAttribute(childElement, "isSwitch", "true")) {
                        tetraComponents.addAll(parseIpn(childElement));
                    }

                }

            }

        }
        return tetraComponents;
    }

    /**
     * Получает базовую станцию
     * @param element   - элемент xml
     * @return          - базовая станция
     */
    private BaseStation parseBaseStation(Element element) {

        BaseStation baseStation = new BaseStation(
                element.getAttributeValue("displayName"),
                element.getAttributeValue("type")
        );

        baseStation.setComponents(parseBaseStationRack(element.getChildren()));

        return baseStation;
    }

    /**
     * Получает список шкафов базовой станции
     * @param elementList   - список элементов xml
     * @return              - список шкафов
     */
    private ArrayList<Component> parseBaseStationRack(List<Element> elementList) {

        ArrayList<Component> components = new ArrayList<>();

        for(Element element : elementList) {

            if(checkAttribute(element,"type","Rack")) {

                for(Element childElement : element.getChildren()) {

                    if(checkAttribute(childElement,"type","DIB-500-Shelf")) {
                        components.addAll(parseTib(childElement.getChildren()));
                    }
                }

            }

        }
        return components;
    }

    /**
     * Получает список TIB'ов
     * @param elementList   - список элементов xml
     * @return              - список TIB'ов
     */
    private ArrayList<Component> parseTib(List<Element> elementList) {

        ArrayList<Component> components = new ArrayList<>();

        for(Element element : elementList) {

            if(checkAttribute(element,"type","TIB-500")) {

                for(Element childElement: element.getChildren()) {

                    if(hasAttribute(childElement, "type")) {

                        switch (childElement.getAttributeValue("type")) {

                            case "PowerPC" : {
                                components.addAll(parsePowerPc(childElement));
                                break;
                            }

                            case "IntelPC" : {
                                components.addAll(parseIntelPc(childElement));
                                break;
                            }

                        }
                    }
                }

            }

        }

        return components;
    }

    /**
     * Получает PowerPC
     * @param element   - элемент xml
     * @return          - PowerPC
     */
    private ArrayList<Component> parsePowerPc(Element element) {

        ArrayList<Component> components = new ArrayList<>();

        for(Element childElement : element.getChildren()) {

            if(parsedSoftwareComponent.contains(childElement.getAttributeValue("ncType"))
                    && checkAttribute(childElement,"ncType","PUC")) {

                Puc puc = new Puc(childElement.getAttributeValue("displayName"));
                puc.setIpAddress(childElement.getAttributeValue("compIpAddress"));
                puc.setSnmpPort(Integer.parseInt(childElement.getAttributeValue("compSnmpPort")));

                components.add(puc);
            }
        }
        return components;
    }

    /**
     * Получает IntelPC
     * @param element   - элемент xml
     * @return          - IntelPC
     */
    private ArrayList<Component> parseIntelPc(Element element) {

        ArrayList<Component> components = new ArrayList<>();

        for(Element childElement : element.getChildren()) {

            if(checkAttribute(childElement, "type", "SOFTWARE")) {

                if(parsedSoftwareComponent.contains(childElement.getAttributeValue("ncType"))) {
                    components.add(parseSoftwareComponent(childElement));
                } else if(hasChildren(childElement)
                        && parsedSoftwareComponent.contains(
                                childElement.getChildren().get(0).getAttributeValue("ncType")
                )) {
                    components.add(parseSoftwareComponent(childElement));
                }
            }
        }
        return components;
    }

    /**
     * Получает программный компонент
     * @param element   - элемент xml
     * @return          - программный компонент
     */
    private Component parseSoftwareComponent(Element element) {

        if(hasAttribute(element, "ncType")) {

            switch (element.getAttributeValue("ncType")) {

                case "COS" : {

                    Cos cos = new Cos(element.getAttributeValue("displayName"));
                    cos.setIpAddress(element.getAttributeValue("compIpAddress"));
                    cos.setSnmpPort(Integer.parseInt(element.getAttributeValue("compSnmpPort")));
                    return cos;

                }

                case "GMD" : {

                    Gmd gmd = new Gmd(element.getAttributeValue("displayName"));
                    gmd.setIpAddress(element.getAttributeValue("compIpAddress"));
                    gmd.setSnmpPort(Integer.parseInt(element.getAttributeValue("compSnmpPort")));
                    return gmd;
                }

                case "HWG" : {

                    Hwg hwg = new Hwg(element.getAttributeValue("displayName"));
                    hwg.setIpAddress(element.getAttributeValue("compIpAddress"));
                    hwg.setSnmpPort(Integer.parseInt(element.getAttributeValue("compSnmpPort")));
                    return hwg;
                }

                case "IPS" : {

                    Ips ips = new Ips(element.getAttributeValue("displayName"));
                    ips.setIpAddress(element.getAttributeValue("compIpAddress"));
                    ips.setSnmpPort(Integer.parseInt(element.getAttributeValue("compSnmpPort")));
                    return ips;
                }

                case "PDS" : {

                    Pds pds = new Pds(element.getAttributeValue("displayName"));
                    pds.setIpAddress(element.getAttributeValue("compIpAddress"));
                    pds.setSnmpPort(Integer.parseInt(element.getAttributeValue("compSnmpPort")));
                    return pds;
                }

                case "SMG" : {

                    Smg smg = new Smg(element.getAttributeValue("displayName"));
                    smg.setIpAddress(element.getAttributeValue("compIpAddress"));
                    smg.setSnmpPort(Integer.parseInt(element.getAttributeValue("compSnmpPort")));
                    return smg;
                }

                case "TOS" : {

                    Tos tos = new Tos(element.getAttributeValue("displayName"));
                    tos.setIpAddress(element.getAttributeValue("compIpAddress"));
                    tos.setSnmpPort(Integer.parseInt(element.getAttributeValue("compSnmpPort")));
                    return tos;
                }

                case "TAP" : {

                    Tap tap = new Tap(element.getAttributeValue("displayName"));
                    tap.setIpAddress(element.getAttributeValue("compIpAddress"));
                    tap.setSnmpPort(Integer.parseInt(element.getAttributeValue("compSnmpPort")));
                    return tap;
                }

                case "Nem523DownloadManagement" : {

                    Nem523 nem523 = new Nem523(element.getParentElement().getAttributeValue("displayName"));
                    nem523.setIpAddress(element.getAttributeValue("compIpAddress"));
                    nem523.setSnmpPort(Integer.parseInt(element.getAttributeValue("compSnmpPort")));
                    return nem523;
                }

                default: {

                    Component component = new Component(element.getAttributeValue("displayName"),
                            "other");
                    component.setIpAddress(element.getAttributeValue("compIpAddress"));
                    component.setSnmpPort(Integer.parseInt(element.getAttributeValue("compSnmpPort")));
                    return component;

                }
            }

        } else {
            return parseSoftwareComponent(element.getChildren().get(0));
        }
    }

    /**
     * Получает IPN
     * @param element   - элемент xml
     * @return          - IPN
     */
    private ArrayList<Mpu> parseIpn(Element element) {
        return parseIpnRack(element.getChildren());
    }

    /**
     * Получает список шкафов IPN
     * @param elementList   - список элементов xml
     * @return              - список шкафов IPN
     */
    private ArrayList<Mpu> parseIpnRack(List<Element> elementList) {

        ArrayList<Mpu> mpus = new ArrayList<>();

        for(Element element : elementList) {

            if(checkAttribute(element, "type", "Rack")) {

                for(Element childElement : element.getChildren()) {

                    if(checkAttribute(childElement, "type", "MPU-580-R2")) {
                        mpus.add(parseMpu(childElement));
                    }
                }

            }
        }

        return mpus;
    }

    /**
     * Получает сервер MPU
     * @param element   - элемент xml
     * @return          - сервер MPU
     */
    private Mpu parseMpu(Element element) {

        Mpu mpu = new Mpu(element.getAttributeValue("displayName"),
                element.getAttributeValue("type"));

        for(Element childElement : element.getChildren()) {

            if(parsedSoftwareComponent.contains(childElement.getAttributeValue("ncType"))) {
                mpu.getAllComponents().add(parseSoftwareComponent(childElement));
            } else if(hasChildren(childElement)
                    && parsedSoftwareComponent.contains(
                    childElement.getChildren().get(0).getAttributeValue("ncType")
            )) {
                mpu.getAllComponents().add(parseSoftwareComponent(childElement));
            }

        }

        return mpu;

    }

    /**
     * Проверяет наличие у элемента xml атрибута
     * @param element   - элемент xml
     * @param attribute - проверяемый атрибут
     * @return          - true, если атрибут есть
     */
    private boolean hasAttribute(Element element, String attribute) {

        try {
            return !element.getAttributeValue(attribute).isEmpty();
        } catch (NullPointerException e) {
            return false;
        }

    }

    /**
     * Проверяет наличие дочерних элементов у xml-элемента
     * @param element   - xml-элемент
     * @return          - true, если есть дочерние элементы
     */
    private boolean hasChildren(Element element) {

        try {
            return !element.getChildren().isEmpty();
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Проверяет значение атрибута элемента xml
     * @param element   - элемент xml
     * @param attribute - проверяемый атрибут
     * @param value     - проверяемое значение
     * @return          - true, если атрибут равен проверяемому значению
     */
    private boolean checkAttribute(Element element, String attribute, String value) {

        if(hasAttribute(element, attribute)) {
            return element.getAttributeValue(attribute).equals(value);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Parser{" +
                "configurationFile=" + configurationFile +
                ", configNetwork=" + configNetwork +
                '}';
    }
}
