package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Create by graf-anton on 23.12.2020
 *
 * Абстрактный компонент системы связи Янтарь-T IP (ACCESSNET-T IP)
 */
@ToString
@EqualsAndHashCode
public class Component {

    //IP-адрес компонента
    protected String ipAddress;
    //Порт SNMP
    protected int snmpPort;
    //Community для чтения данных по SNMP
    protected String readSnmpCommunity;
    //Список аварий
    //Имя компонента
    protected String name;
    //Тип компонента
    protected String ncType;

    public Component(String name, String ncType) {
        this.name = name;
        this.ncType = ncType;
    }

    public String getName() {
        return name;
    }

    public String getNcType() {
        return ncType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getSnmpPort() {
        return snmpPort;
    }

    public void setSnmpPort(int snmpPort) {
        this.snmpPort = snmpPort;
    }

    public String getReadSnmpCommunity() {
        return readSnmpCommunity;
    }

    public void setReadSnmpCommunity(String readSnmpCommunity) {
        this.readSnmpCommunity = readSnmpCommunity;
    }
}
