package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

import ru.tetra.telnetclient.utils.parser.enums.TetraOid;
import ru.tetra.telnetclient.utils.parser.performance.PerformanceEvent;

import java.time.LocalDateTime;

/**
 * Create by graf-anton on 29.12.2020
 *
 * Компонент шлюза внешний приложений TAP
 */
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

    @Override
    public void updatePerformances(String networkElementName) {
        super.updatePerformances(networkElementName);

        singlePerformanceEvents.add(new PerformanceEvent("RTP INTERFACE", LocalDateTime.now(),
                getRtpInterface(), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("BUSY CHANNEL G711", LocalDateTime.now(),
                String.valueOf(getBusyChannelG711()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("BUSY CHANNEL ACELP", LocalDateTime.now(),
                String.valueOf(getBusyChannelAcelp()), name, networkElementName));
        rarelyPerformanceEvents.add(new PerformanceEvent("TOTAL CHANNEL G711", LocalDateTime.now(),
                String.valueOf(getTotalChannelG711()), name, networkElementName));
        rarelyPerformanceEvents.add(new PerformanceEvent("TOTAL CHANNEL ACELP", LocalDateTime.now(),
                String.valueOf(getTotalChannelAcelp()), name, networkElementName));
        rarelyPerformanceEvents.add(new PerformanceEvent("TOTAL NUMBERS AVAILAB", LocalDateTime.now(),
                String.valueOf(getTotalNumbersAvailab()), name, networkElementName));
        rarelyPerformanceEvents.add(new PerformanceEvent("TOTAL NUMBERS MONITORING", LocalDateTime.now(),
                String.valueOf(getTotalNumbersMon()), name, networkElementName));
        rarelyPerformanceEvents.add(new PerformanceEvent("TOTAL NUMBERS VOICE RECORDING", LocalDateTime.now(),
                String.valueOf(getTotalNumbersVoiceRecord()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("BUSY CHANNEL MONITORING", LocalDateTime.now(),
                String.valueOf(getBusyChannelMon()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("BUSY CHANNEL CALL CONTROL", LocalDateTime.now(),
                String.valueOf(getBusyChannelCc()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("BUSY CHANNEL VOICE RECORDING", LocalDateTime.now(),
                String.valueOf(getBusyChannelVrc()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("FREE CHANNEL MONITORING", LocalDateTime.now(),
                String.valueOf(getFreeChannelMon()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("FREE CHANNEL CALL CONTROL", LocalDateTime.now(),
                String.valueOf(getFreeChannelCc()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("FREE CHANNEL VOICE RECORDING", LocalDateTime.now(),
                String.valueOf(getFreeChannelVrc()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("BUSY CHANNEL MONITORING", LocalDateTime.now(),
                String.valueOf(getBusyChannelMon()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("BUSY CHANNEL CALL CONTROL", LocalDateTime.now(),
                String.valueOf(getBusyChannelCc()), name, networkElementName));
        singlePerformanceEvents.add(new PerformanceEvent("DGNA ENABLE", LocalDateTime.now(),
                String.valueOf(isDgnaEnable()), name, networkElementName));
        singlePerformanceEvents.add(new PerformanceEvent("ENABLE/DISABLE ENABLE", LocalDateTime.now(),
                String.valueOf(isEnableDisableEnable()), name, networkElementName));
        singlePerformanceEvents.add(new PerformanceEvent("OOCA ENABLE", LocalDateTime.now(),
                String.valueOf(isOocaEnable()), name, networkElementName));
        singlePerformanceEvents.add(new PerformanceEvent("GROUP REPORT ENABLE", LocalDateTime.now(),
                String.valueOf(isGroupReportEnable()), name, networkElementName));
        singlePerformanceEvents.add(new PerformanceEvent("VOICE RECORDING ENABLE", LocalDateTime.now(),
                String.valueOf(isVoiceRecordingEnable()), name, networkElementName));
        singlePerformanceEvents.add(new PerformanceEvent("MONITORING SDS ENABLE", LocalDateTime.now(),
                String.valueOf(isMonSdsEnable()), name, networkElementName));
        singlePerformanceEvents.add(new PerformanceEvent("MONITORING CALL CONTROL ENABLE", LocalDateTime.now(),
                String.valueOf(isMonCcEnable()), name, networkElementName));
        singlePerformanceEvents.add(new PerformanceEvent("MONITORING MOBILITY MANAGEMENT ENABLE", LocalDateTime.now(),
                String.valueOf(isMonMmEnable()), name, networkElementName));
        singlePerformanceEvents.add(new PerformanceEvent("CALL FORWARDING ENABLE", LocalDateTime.now(),
                String.valueOf(isCallForwardingEnable()), name, networkElementName));
        singlePerformanceEvents.add(new PerformanceEvent("CHECK AVAILABILITY ENABLE", LocalDateTime.now(),
                String.valueOf(isCheckAvailEnable()), name, networkElementName));
}

    /**
     * Получает имя интерфейся для RTP-потоков
     * @return  - имя RTP-интерфейса
     */
    public String getRtpInterface() {
        return getSnmpRequest(TetraOid.TAP_RTP_INTERFACE_OID.getOid());
    }

    /**
     * Получает количество занятых каналов g711
     * @return - количество каналов
     */
    public int getBusyChannelG711() {
        return getSnmpRequestInteger(TetraOid.TAP_BUSY_CHANNEL_G711_OID.getOid());
    }

    /**
     * Получает количество занятых каналов ACELP
     * @return - количество каналов
     */
    public int getBusyChannelAcelp() {
        return getSnmpRequestInteger(TetraOid.TAP_BUSY_CHANNEL_ACELP_OID.getOid());
    }

    /**
     * Получает общее количество каналов g711
     * @return - количество каналов
     */
    public int getTotalChannelG711() {
        return getSnmpRequestInteger(TetraOid.TAP_TOTAL_CHANNEL_G711_OID.getOid());
    }

    /**
     * Получает общее количество каналов ACELP
     * @return - количество каналов
     */
    public int getTotalChannelAcelp() {
        return getSnmpRequestInteger(TetraOid.TAP_TOTAL_CHANNEL_ACELP_OID.getOid());
    }

    /**
     * Получает общее количество запросов Check-Avail
     * @return - количество запросов
     */
    public int getTotalNumbersAvailab() {
        return getSnmpRequestInteger(TetraOid.TAP_TOTAL_NUMBERS_AVAILAB_OID.getOid());
    }

    /**
     * Получает общее количество каналов записи разговоров
     * @return - количество каналов
     */
    public int getTotalNumbersVoiceRecord() {
        return getSnmpRequestInteger(TetraOid.TAP_TOTAL_NUMBERS_VOICE_RECORD_OID.getOid());
    }

    /**
     * Получает общее количество каналов мониторинга
     * @return - количество каналов
     */
    public int getTotalNumbersMon() {
        return getSnmpRequestInteger(TetraOid.TAP_TOTAL_NUMBERS_MONITORING_OID.getOid());
    }

    /**
     * Получает количество занятых каналов мониторинга
     * @return - количество каналов
     */
    public int getBusyChannelMon() {
        return getSnmpRequestInteger(TetraOid.TAP_BUSY_CHANNEL_MON_OID.getOid());

    }

    /**
     * Получает количество занятых каналов вызовов
     * @return - количество каналов
     */
    public int getBusyChannelCc() {
        return getSnmpRequestInteger(TetraOid.TAP_BUSY_CHANNEL_CC_OID.getOid());
    }

    /**
     * Получает количество занятых каналов записи разговоров
     * @return - количество каналов
     */
    public int getBusyChannelVrc() {
        return getSnmpRequestInteger(TetraOid.TAP_BUSY_CHANNEL_VRC_OID.getOid());
    }

    /**
     * Получает количество свободных каналов мониторинга
     * @return - количество каналов
     */
    public int getFreeChannelMon() {
        return getSnmpRequestInteger(TetraOid.TAP_FREE_CHANNEL_MON_OID.getOid());
    }

    /**
     * Получает количество свободных каналов вызовов
     * @return - количество каналов
     */
    public int getFreeChannelCc() {
        return getSnmpRequestInteger(TetraOid.TAP_FREE_CHANNEL_CC_OID.getOid());
    }

    /**
     * Получает количество свободных каналов записи разговоров
     * @return - количество каналов
     */
    public int getFreeChannelVrc() {
        return getSnmpRequestInteger(TetraOid.TAP_FREE_CHANNEL_VRC_OID.getOid());
    }

    /**
     * Получает доступна ли функция DGNA
     * @return - включена/выключена функция
     */
    public boolean isDgnaEnable() {
        return getSnmpRequestInteger(TetraOid.TAP_FUNCTION_DGNA_OID.getOid()) == 1;
    }

    /**
     * Получает доступна ли функция удаленной блокировки радиостанции
     * @return - включена/выключена функция
     */
    public boolean isEnableDisableEnable() {
        return getSnmpRequestInteger(TetraOid.TAP_FUNCTION_ENABLE_OID.getOid()) == 1;
    }

    /**
     * Получает доступна ли функция OOCA
     * @return - включена/выключена функция
     */
    public boolean isOocaEnable() {
        return getSnmpRequestInteger(TetraOid.TAP_FUNCTION_OOCA_OID.getOid()) == 1;
    }

    /**
     * Получает доступна ли функция групповых отчетов
     * @return - включена/выключена функция
     */
    public boolean isGroupReportEnable() {

        return getSnmpRequestInteger(TetraOid.TAP_FUNCTION_GROUP_REPORT_OID.getOid()) == 1;
    }

    /**
     * Получает доступна ли функция записи разговоров
     * @return - включена/выключена функция
     */
    public boolean isVoiceRecordingEnable() {
        return getSnmpRequestInteger(TetraOid.TAP_FUNCTION_VOICE_RECORDING_OID.getOid()) == 1;
    }

    /**
     * Получает доступна ли функция мониторинга SDS
     * @return - включена/выключена функция
     */
    public boolean isMonSdsEnable() {
        return getSnmpRequestInteger(TetraOid.TAP_FUNCTION_MON_SDS_OID.getOid()) == 1;
    }

    /**
     * Получает доступна ли функция мониторинга вызовов
     * @return - включена/выключена функция
     */
    public boolean isMonCcEnable() {
        return getSnmpRequestInteger(TetraOid.TAP_FUNCTION_MON_CC_OID.getOid()) == 1;
    }

    /**
     * Получает доступна ли функция мониторинга регистраций абонентов
     * @return - включена/выключена функция
     */
    public boolean isMonMmEnable() {
        return getSnmpRequestInteger(TetraOid.TAP_FUNCTION_MON_MM_OID.getOid()) == 1;
    }

    /**
     * Получает доступна ли функция перевода вызова
     * @return - включена/выключена функция
     */
    public boolean isCallForwardingEnable() {
        return getSnmpRequestInteger(TetraOid.TAP_FUNCTION_CALL_FORWARDING_OID.getOid()) == 1;
    }

    /**
     * Получает доступна ли функция проверка доступности абонента
     * @return - включена/выключена функция
     */
    public boolean isCheckAvailEnable() {
        return getSnmpRequestInteger(TetraOid.TAP_FUNCTION_CHECK_AVAIL_OID.getOid()) == 1;

    }

    @Override
    public void printAllInformation() {
        super.printAllInformation();

        if(isOnline()) {

            System.out.println("Сетевой интерфейс RTP: \t" + getRtpInterface());
            System.out.println("Общее количество TETRA-каналов: \t" + getTotalChannelAcelp());
            System.out.println("Общее количество g711-каналов: \t" + getTotalChannelG711());
            System.out.println("Количество занятых TETRA-каналов: \t" + getBusyChannelAcelp());
            System.out.println("Количество занятых g711-каналов: \t" + getBusyChannelG711());
            System.out.println("Количество занятых каналов для вызовов: \t" + getBusyChannelCc());
            System.out.println("Количество занятых каналов для мониторинга: \t" + getBusyChannelMon());
            System.out.println("Количество занятых каналов для записи разговоров: \t" + getBusyChannelVrc());
            System.out.println("Количество свободных каналов для вызовов: \t" + getFreeChannelCc());
            System.out.println("Количество свободных каналов для мониторинга: \t" + getFreeChannelMon());
            System.out.println("Количество свободных каналов для записи разговоров: \t" + getFreeChannelVrc());
            System.out.println("Количество запросов CheckAvail: \t" + getTotalNumbersAvailab());
            System.out.println("Количество запросов мониторинга: \t" + getTotalNumbersMon());
            System.out.println("Количество запросов записи разговоров: \t" + getTotalNumbersVoiceRecord());
            System.out.println("Функция перевода вызова: \t" + (isCallForwardingEnable() ? "включена" : "отключена"));
            System.out.println("Функция CheckAvail: \t" + (isCheckAvailEnable() ? "включена" : "отключена"));
            System.out.println("Функция DGNA: \t" + (isDgnaEnable() ? "включена" : "отключена"));
            System.out.println("Функция удаленной блокировки: \t" + (isEnableDisableEnable() ? "включена" : "отключена"));
            System.out.println("Функция групповых отчетов: \t" + (isGroupReportEnable() ? "включена" : "отключена"));
            System.out.println("Функция мониторнга вызовов: \t" + (isMonCcEnable() ? "включена" : "отключена"));
            System.out.println("Функция мониторнга SDS: \t" + (isMonSdsEnable() ? "включена" : "отключена"));
            System.out.println("Функция мониторнга регистраций: \t" + (isMonMmEnable() ? "включена" : "отключена"));
            System.out.println("Функция OOCA: \t" + (isOocaEnable() ? "включена" : "отключена"));
            System.out.println("Функция записи разговоров: \t" + (isVoiceRecordingEnable() ? "включена" : "отключена"));

        }
    }
}
