package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Create by graf-anton on 29.12.2020
 *
 * Компонент базовой станции TOS
 */
@ToString
@EqualsAndHashCode
public class Tos extends Component{

    //SNMP-порт по умолчанию
    private static final int DEFAULT_SNMP_PORT = 173;
    //SNMP-community по умолчанию
    private static final String DEFAULT_READ_SNMP_COMMUNITY = "public";

    public Tos(String name) {
        super(name, "TOS");

        this.readSnmpCommunity = DEFAULT_READ_SNMP_COMMUNITY;
        this.snmpPort = DEFAULT_SNMP_PORT;
    }
}
