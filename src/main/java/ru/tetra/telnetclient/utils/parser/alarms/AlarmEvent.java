package ru.tetra.telnetclient.utils.parser.alarms;

import ru.tetra.telnetclient.utils.parser.enums.AlarmStatus;
import ru.tetra.telnetclient.utils.parser.enums.TetraOid;
import ru.tetra.telnetclient.utils.parser.utils.SnmpHelper;

import java.time.LocalDateTime;

/**
 * Create by graf-anton on 24.12.2020
 *
 * Авария компонентов системы Янтарь-T IP
 */
public class AlarmEvent {

    //Индекс аварии
    private int index;
    //Идентификатор аварии
    private int id;
    //Имя аварии
    private String name;
    //Статус аварии
    private AlarmStatus alarmStatus;
    //Время аварии
    private LocalDateTime statusTime;
    //IP-адрес компонента
    private String ipAddress;
    //SNMP-порт компонента
    private int snmpPort;
    //Community компонента
    private String readSnmpCommunity;


    public AlarmEvent(int index, String ipAddress, int snmpPort, String readSnmpCommunity) {

        this.index = index;
        this.ipAddress = ipAddress;
        this.snmpPort = snmpPort;
        this.readSnmpCommunity = readSnmpCommunity;

        this.name = SnmpHelper.snmpGet(ipAddress, snmpPort, readSnmpCommunity,
                TetraOid.ALARM_NAME_OID.getOid() + "." + index);

        this.id = cutId(SnmpHelper.snmpGet(ipAddress, snmpPort, readSnmpCommunity,
                TetraOid.ALARM_ID_OID.getOid() + "." + index));
    }

    public int getIndex() {
        return index;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getSnmpPort() {
        return snmpPort;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlarmStatus(AlarmStatus alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public void setStatusTime(LocalDateTime statusTime) {
        this.statusTime = statusTime;
    }

    public String getReadSnmpCommunity() {
        return readSnmpCommunity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AlarmStatus getAlarmStatus() {
        return alarmStatus;
    }

    public LocalDateTime getStatusTime() {
        return statusTime;
    }

    /**
     * Обновляет статус и время аварии
     */
    public void update() {

        String snmpResult = SnmpHelper.snmpGet(ipAddress, snmpPort, readSnmpCommunity, TetraOid.ALARM_STATUS_OID.getOid() +
                "." + index).trim();

        if(snmpResult != null && !snmpResult.isEmpty()) {
            alarmStatus = AlarmStatus.getAlarmStatusByCode(Integer.parseInt(snmpResult));
        }


        snmpResult = SnmpHelper.snmpGet(ipAddress, snmpPort, readSnmpCommunity,TetraOid.ALARM_STATUS_TIME_OID.getOid() +
                "." + index).trim();

        if(snmpResult != null && !snmpResult.isEmpty()) {
            statusTime = SnmpHelper.convertSnmpDateTime(snmpResult.replace(":", ""));
        }
    }

    @Override
    public String toString() {
        return "AlarmEvent{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", alarmStatus=" + alarmStatus +
                ", statusTime=" + statusTime +
                '}';
    }

    private int cutId(String idString) {

        String cutIdString;

        try {

            cutIdString = idString.split("::", 3)[1];
            return Integer.parseInt(cutIdString);

        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return 0;
        }



    }
}
