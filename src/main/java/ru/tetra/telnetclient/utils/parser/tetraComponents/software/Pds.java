package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

/**
 * Create by graf-anton on 29.12.2020
 *
 * Компонент сервиса пакетной передачи данных PDS
 */
public class Pds extends Component{

    //SNMP-порт по умолчанию
    private static final int DEFAULT_SNMP_PORT = 166;
    //SNMP-community по умолчанию
    private static final String DEFAULT_READ_SNMP_COMMUNITY = "public";

    public Pds(String name) {
        super(name, "PDS");

        this.readSnmpCommunity = DEFAULT_READ_SNMP_COMMUNITY;
        this.snmpPort = DEFAULT_SNMP_PORT;
    }

}
