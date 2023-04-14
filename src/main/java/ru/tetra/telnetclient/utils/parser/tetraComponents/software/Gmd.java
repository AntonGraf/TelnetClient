package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

/**
 * Create by graf-anton on 29.12.2020
 *
 * Компонент компонент мониторинга GMD
 */
public class Gmd extends Component{

    //SNMP-порт по умолчанию
    private static final int DEFAULT_SNMP_PORT = 185;
    //SNMP-community по умолчанию
    private static final String DEFAULT_READ_SNMP_COMMUNITY = "public";

    public Gmd(String name) {
        super(name, "GMD");

        this.readSnmpCommunity = DEFAULT_READ_SNMP_COMMUNITY;
        this.snmpPort = DEFAULT_SNMP_PORT;
    }

}
