package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Create by graf-anton on 24.12.2020
 *
 * Компонент базовой станции PUC
 */
@ToString
@EqualsAndHashCode
public class Puc extends Component {

    //SNMP-порт по умолчанию
    private static final int DEFAULT_SNMP_PORT = 161;
    //SNMP-community по умолчанию
    private static final String DEFAULT_READ_SNMP_COMMUNITY = "public";

    public Puc(String name) {
        super(name, "PUC");

        this.readSnmpCommunity = DEFAULT_READ_SNMP_COMMUNITY;
        this.snmpPort = DEFAULT_SNMP_PORT;
    }
}
