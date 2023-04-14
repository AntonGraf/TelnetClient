package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Create by graf-anton on 29.12.2020
 *
 * Компонент шлюза внешний приложений TAP
 */
@ToString
@EqualsAndHashCode
public class Tap extends Component{

    //SNMP-порт по умолчанию
    private static final int DEFAULT_SNMP_PORT = 171;
    //SNMP-community по умолчанию
    private static final String DEFAULT_READ_SNMP_COMMUNITY = "public";

    public Tap(String name) {
        super(name, "TAP");

        this.readSnmpCommunity = DEFAULT_READ_SNMP_COMMUNITY;
        this.snmpPort = DEFAULT_SNMP_PORT;
    }
}
