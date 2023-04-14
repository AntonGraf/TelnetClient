package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

import ru.tetra.telnetclient.utils.parser.alarms.AlarmEvent;
import ru.tetra.telnetclient.utils.parser.enums.AlarmStatus;
import ru.tetra.telnetclient.utils.parser.enums.TetraOid;
import ru.tetra.telnetclient.utils.parser.performance.PerformanceEvent;
import ru.tetra.telnetclient.utils.parser.utils.SnmpHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by graf-anton on 23.12.2020
 *
 * Абстрактный компонент системы связи Янтарь-T IP (ACCESSNET-T IP)
 */
public class Component {

    //IP-адрес компонента
    protected String ipAddress;
    //Порт SNMP
    protected int snmpPort;
    //Community для чтения данных по SNMP
    protected String readSnmpCommunity;
    //Список аварий
    protected List<AlarmEvent> alarmEvents;
    //Список очень редко опрашиваемых параметров производительности
    protected List<PerformanceEvent> singlePerformanceEvents;
    //Список редко опрашиваемых параметров производительности
    protected List<PerformanceEvent> rarelyPerformanceEvents;
    //Список часто опрашиваемых параметров производительности
    protected List<PerformanceEvent> oftenPerformanceEvents;
    //Имя компонента
    protected String name;
    //Тип компонента
    protected String ncType;
    //Статус компонента (в сети/не в сети)
    protected boolean status;

    public Component(String name, String ncType) {
        this.name = name;
        this.ncType = ncType;
        this.alarmEvents = new ArrayList<>();
        this.singlePerformanceEvents = new ArrayList<>();
        this.rarelyPerformanceEvents = new ArrayList<>();
        this.oftenPerformanceEvents = new ArrayList<>();
    }

    public boolean isOnline() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    /**
     * Возвращает время работы компонента
     * @return - время работы компонента
     */
    public String getUptime(){
        return SnmpHelper.snmpGet(ipAddress, snmpPort, readSnmpCommunity, TetraOid.COMPONENT_UPTIME_OID.getOid());
    }

    /**
     * Чтение списка аварий из компонента
     */
    public void readAlarmEvents() {

        alarmEvents.clear();
        String snmpResult = "";
        checkAvail();
        if(isOnline()) {
            snmpResult = SnmpHelper.snmpGet(ipAddress, snmpPort, readSnmpCommunity,
                    TetraOid.ALARMS_NUMBER_OID.getOid()).trim();
        }

        int countAlarms = 0;

        if(snmpResult != null && !snmpResult.isEmpty()) {

            countAlarms = Integer.parseInt(snmpResult);
            status = true;

        } else {

            status = false;
        }

        for(int i = 1; i <= countAlarms; i++) {
            alarmEvents.add(new AlarmEvent(i,ipAddress, snmpPort, readSnmpCommunity));
        }

    }

    /**
     * Обновляет аварии компонентов
     */
    public void updateAlarms() {

        if(alarmEvents.isEmpty()) {
            readAlarmEvents();
        }

        for(AlarmEvent event : alarmEvents) {
            event.update();
        }

        for(int i = 0; i < alarmEvents.size(); i++) {

            if(alarmEvents.get(i).getAlarmStatus() == null) {
                alarmEvents.remove(i);
                i--;
            }

        }
    }

    public void updatePerformances(String networkElementName) {

        updateSinglePerformances(networkElementName);
        updateRarelyPerformances(networkElementName);
        updateOftenPerformances(networkElementName);

    }

    public void updateSinglePerformances(String networkElementName) {
        singlePerformanceEvents.clear();
    }

    public void updateRarelyPerformances(String networkElementName) {

        rarelyPerformanceEvents.clear();
        rarelyPerformanceEvents.add(new PerformanceEvent("uptime", LocalDateTime.now(), getUptime(), name,
                networkElementName));

    }

    public void updateOftenPerformances(String networkElementName) {
        oftenPerformanceEvents.clear();
    }

    /**
     * Возвращает самый критичный статус из списка аварий
     * @return - статус аварии
     */
    public AlarmStatus getComponentsAlarmStatus() {

        int alarmStatusCode = AlarmStatus.OK.getStatusCode();

        if(alarmEvents.isEmpty()) {
            readAlarmEvents();
        }
        for(AlarmEvent event : alarmEvents) {

            int eventCode = event.getAlarmStatus().getStatusCode();
            if (eventCode < alarmStatusCode)
                alarmStatusCode = eventCode;

        }

        return AlarmStatus.getAlarmStatusByCode(alarmStatusCode);
    }

    public List<PerformanceEvent> getPerformanceEvents() {

        List<PerformanceEvent> performanceEvents = new ArrayList<>();
        performanceEvents.addAll(singlePerformanceEvents);
        performanceEvents.addAll(rarelyPerformanceEvents);
        performanceEvents.addAll(oftenPerformanceEvents);

        return performanceEvents;
    }

    public List<PerformanceEvent> getSinglePerformanceEvents() {
        return singlePerformanceEvents;
    }

    public List<PerformanceEvent> getRarelyPerformanceEvents() {
        return rarelyPerformanceEvents;
    }

    public List<PerformanceEvent> getOftenPerformanceEvents() {
        return oftenPerformanceEvents;
    }

    /**
     * Возвращает список аварий
     * @return - список аварий
     */
    public List<AlarmEvent> getAlarmEvents() {
        return alarmEvents;
    }

    /**
     * Вывод в консоль списка аварий
     */
    public void printAlarmEvents() {

        for(AlarmEvent event : alarmEvents) {
            if(event.getAlarmStatus().getStatusCode() > 5) {
                System.out.println(event.getName() + "\t" + event.getAlarmStatus() + "\t" + event.getStatusTime());
            } else {
                System.err.println(event.getName() + "\t" + event.getAlarmStatus() + "\t" + event.getStatusTime());
            }
        }
    }

    /**
     * Вывод в консоль аварий, отличных от ОК
     */
    public void printErrorAlarmEvents() {

        for(AlarmEvent event : alarmEvents) {
            if(event.getAlarmStatus() != AlarmStatus.OK){
                System.err.println(event.getName() + "\t" + event.getAlarmStatus() + "\t" + event.getStatusTime());
            }
        }
    }

    /**
     * Проверка доступности компонента
     */
    public void checkAvail() {
        status = !SnmpHelper.snmpGet(ipAddress, snmpPort, readSnmpCommunity,
                TetraOid.ALARMS_NUMBER_OID.getOid()).trim().isEmpty();
    }

    /**
     * Возвращает результет snmp-get запроса
     * @param oid   - snmp-oid запроса
     * @return      - результат в текстовом виде
     */
    protected String getSnmpRequest(String oid) {

        String snmpResult = SnmpHelper.snmpGet(
                ipAddress, snmpPort, readSnmpCommunity, oid
        ).trim();

        if(snmpResult != null && !snmpResult.equals("4294967295")) {

            status = true;
            return snmpResult;

        } else {
            status = false;
            return "";
        }

    }

    /**
     * Возвращает результет snmp-get запроса
     * @param oid   - snmp-oid запроса
     * @return      - результат в целочисленном виде
     */
    protected int getSnmpRequestInteger(String oid) {
        String snmpResult = getSnmpRequest(oid);

        if(!snmpResult.isEmpty()) {

            try {
                return Integer.parseInt(snmpResult);
            } catch (NumberFormatException e) {
                return 0;
            }

        } else {
            return 0;
        }

    }

    /**
     * Возвращает результет snmp-get запроса
     * @param oid   - snmp-oid запроса
     * @return      - результат в вещественном виде
     */
    protected double getSnmpRequestDouble(String oid) {
        String snmpResult = getSnmpRequest(oid);

        if(!snmpResult.isEmpty()) {

            try {
                return Double.parseDouble(snmpResult);
            } catch (NumberFormatException e) {
                return 0;
            }

        } else {
            return 0;
        }

    }

    /**
     * Выводит информацию о компоненте
     */
    public void printAllInformation() {

        System.out.println("Имя компонента: \t" + name);
        System.out.println("Тип компонента: \t" + ncType);
        System.out.println("IP-адрес: \t" + ipAddress);
        System.out.println("SNMP-порт: \t" + snmpPort);
        System.out.println("SNMP-community: \t" + readSnmpCommunity);
        System.out.println("Статус компонента: \t" + (status ? "online" : "offline"));

        if(status)
            System.out.println("Время в сети: \t" + getUptime());

    }
}
