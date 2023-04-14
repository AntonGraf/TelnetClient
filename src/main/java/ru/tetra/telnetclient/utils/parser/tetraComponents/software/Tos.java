package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

import ru.tetra.telnetclient.utils.parser.enums.TetraOid;
import ru.tetra.telnetclient.utils.parser.performance.PerformanceEvent;

import java.time.LocalDateTime;

/**
 * Create by graf-anton on 29.12.2020
 *
 * Компонент базовой станции TOS
 */
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

    @Override
    public void updatePerformances(String networkElementName) {

        updateSinglePerformances(networkElementName);
        updateRarelyPerformances(networkElementName);
        updateOftenPerformances(networkElementName);
    }

    @Override
    public void updateSinglePerformances(String networkElementName) {
        super.updateSinglePerformances(networkElementName);

        singlePerformanceEvents.add(new PerformanceEvent("LA", LocalDateTime.now(),
                String.valueOf(getLa()), name, networkElementName));
        singlePerformanceEvents.add(new PerformanceEvent("MCC", LocalDateTime.now(),
                String.valueOf(getMcc()), name, networkElementName));
        singlePerformanceEvents.add(new PerformanceEvent("MNC", LocalDateTime.now(),
                String.valueOf(getMnc()), name, networkElementName));
    }

    @Override
    public void updateRarelyPerformances(String networkElementName) {
        super.updateRarelyPerformances(networkElementName);
    }

    @Override
    public void updateOftenPerformances(String networkElementName) {
        super.updateOftenPerformances(networkElementName);

        oftenPerformanceEvents.add(new PerformanceEvent("LOAD MCCH DL", LocalDateTime.now(),
                String.valueOf(getMcchDl()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("LOAD MCCH UL", LocalDateTime.now(),
                String.valueOf(getMcchUl()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("NDB FILE NUMBER", LocalDateTime.now(),
                String.valueOf(getNdbFileNumber()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("QUEEN COUNT", LocalDateTime.now(),
                String.valueOf(getQuenCount()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("FREE CHANNEL COUNT", LocalDateTime.now(),
                String.valueOf(getFreeChannelCount()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("TRAFFIC CHANNEL COUNT", LocalDateTime.now(),
                String.valueOf(getTrafficChannelCount()), name, networkElementName));
    }

    /**
     * Получает номер Location Area
     * @return - номер LA
     */
    public int getLa() {
        return getSnmpRequestInteger(TetraOid.TOS_LA_OID.getOid());
    }

    /**
     * Получает код страны MCC
     * @return  - код страны MCC
     */
    public int getMcc() {
        return getSnmpRequestInteger(TetraOid.TOS_MCC_OID.getOid());
    }

    /**
     * Получает код страны MТC
     * @return  - код страны MТC
     */
    public int getMnc() {
        return getSnmpRequestInteger(TetraOid.TOS_MNC_OID.getOid());
    }

    /**
     * Получает загруженность MCCH в нисходящем канале
     * @return  - загруженность канала в %
     */
    public int getMcchDl() {
        return getSnmpRequestInteger(TetraOid.TOS_MCCH_DL_OID.getOid());
    }

    /**
     * Получает загруженность MCCH в восходящем канале
     * @return  - загруженность канала в %
     */
    public int getMcchUl() {
        return getSnmpRequestInteger(TetraOid.TOS_MCCH_UL_OID.getOid());
    }

    /**
     * Получает номер файла абонентов NDB
     * @return  - номер файла
     */
    public int getNdbFileNumber() {
        return getSnmpRequestInteger(TetraOid.TOS_NDB_FILE_NUMBER_OID.getOid());
    }

    /**
     * Получает количество очередей
     * @return  - количество очередей
     */
    public int getQuenCount() {
        return getSnmpRequestInteger(TetraOid.TOS_QUEN_COUNT_OID.getOid());
    }

    /**
     * Получает количество свободных каналов
     * @return - количество каналов
     */
    public int getFreeChannelCount() {
        return getSnmpRequestInteger(TetraOid.TOS_FREE_CHANNELS_COUNT_OID.getOid());
    }

    /**
     * Получает количество занятых каналов
     * @return - количество каналов
     */
    public int getTrafficChannelCount() {
        return getSnmpRequestInteger(TetraOid.TOS_TRAFFIC_CHANNELS_COUNT_OID.getOid());
    }

    @Override
    public void printAllInformation() {
        super.printAllInformation();

        if(isOnline()) {

            System.out.println("MCC: \t" + getMcc());
            System.out.println("MNC: \t" + getMnc());
            System.out.println("LA: \t" + getLa());
            System.out.println("Номер файла абонентов: \t" + getNdbFileNumber());
            System.out.println("Загрузка MCCH DL: \t" + getMcchDl());
            System.out.println("Загрузка MCCH UL: \t" + getMcchUl());
            System.out.println("Количество очередей: \t" + getQuenCount());
            System.out.println("Количество занятых трафиковых каналов: \t" + getTrafficChannelCount());
            System.out.println("Количество свободных каналов: \t" + getFreeChannelCount());

        }

    }
}
