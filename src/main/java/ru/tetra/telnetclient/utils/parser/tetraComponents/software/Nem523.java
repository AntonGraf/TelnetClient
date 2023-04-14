package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Create by graf-anton on 29.12.2020
 *
 * Компонент управления сетевый оборудование Nem523
 */
@ToString
@EqualsAndHashCode
public class Nem523 extends Component{

    //SNMP-порт по умолчанию
    private static final int DEFAULT_SNMP_PORT = 5020;
    //SNMP-community по умолчанию
    private static final String DEFAULT_READ_SNMP_COMMUNITY = "public";

    public Nem523(String name) {
        super(name, "Nem523DownloadManagement");

        this.readSnmpCommunity = DEFAULT_READ_SNMP_COMMUNITY;
        this.snmpPort = DEFAULT_SNMP_PORT;
    }

}
