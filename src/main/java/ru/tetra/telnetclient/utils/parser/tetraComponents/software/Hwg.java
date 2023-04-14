package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Create by graf-anton on 29.12.2020
 *
 * Компонент мониторинга аппаратной части базовой станции HWG
 */
@ToString
@EqualsAndHashCode
public class Hwg extends Component {

    //SNMP-порт по умолчанию
    private static final int DEFAULT_SNMP_PORT = 169;
    //SNMP-community по умолчанию
    private static final String DEFAULT_READ_SNMP_COMMUNITY = "public";

    public Hwg(String name) {
        super(name, "HWG");

        this.readSnmpCommunity = DEFAULT_READ_SNMP_COMMUNITY;
        this.snmpPort = DEFAULT_SNMP_PORT;
    }
}
