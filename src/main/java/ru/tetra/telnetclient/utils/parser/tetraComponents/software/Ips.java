package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

/**
 * Create by graf-anton on 29.12.2020
 *
 * Компонент коммутации IPS
 */
public class Ips extends Component{

    //SNMP-порт по умолчанию
    private static final int DEFAULT_SNMP_PORT = 165;
    //SNMP-community по умолчанию
    private static final String DEFAULT_READ_SNMP_COMMUNITY = "public";

    public Ips(String name) {
        super(name, "IPS");

        this.readSnmpCommunity = DEFAULT_READ_SNMP_COMMUNITY;
        this.snmpPort = DEFAULT_SNMP_PORT;
    }

}
