package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

import ru.tetra.telnetclient.utils.parser.enums.TetraOid;
import ru.tetra.telnetclient.utils.parser.performance.PerformanceEvent;

import java.time.LocalDateTime;

/**
 * Create by graf-anton on 29.12.2020
 *
 * Компонент коммутации COS
 */
public class Cos extends Component{

    //SNMP-порт по умолчанию
    private static final int DEFAULT_SNMP_PORT = 167;
    //SNMP-community по умолчанию
    private static final String DEFAULT_READ_SNMP_COMMUNITY = "public";

    public Cos(String name) {
        super(name, "COS");

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

        singlePerformanceEvents.add(new PerformanceEvent("NETWORK NAME", LocalDateTime.now(),
                String.valueOf(getGroupDeliveredSds()), name, networkElementName));

    }

    @Override
    public void updateRarelyPerformances(String networkElementName) {
        super.updateRarelyPerformances(networkElementName);
    }

    @Override
    public void updateOftenPerformances(String networkElementName) {
        super.updateOftenPerformances(networkElementName);

        oftenPerformanceEvents.add(new PerformanceEvent("GROUP QUEEN TIME", LocalDateTime.now(),
                String.valueOf(getGroupQuenTime()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("INDIV QUEEN TIME", LocalDateTime.now(),
                String.valueOf(getIndivQuenTime()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("GROUP DELIVERED SDS", LocalDateTime.now(),
                String.valueOf(getGroupDeliveredSds()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("INDIV DELIVERED SDS", LocalDateTime.now(),
                String.valueOf(getIndivDeliveredSds()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("GROUP UNREACH CALL", LocalDateTime.now(),
                String.valueOf(getGroupUnreachCall()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("INDIV UNREACH CALL", LocalDateTime.now(),
                String.valueOf(getIndivUnreachCall()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("INDIV UNREACH SDS", LocalDateTime.now(),
                String.valueOf(getIndivUnreachSds()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("GROUP QUEEN", LocalDateTime.now(),
                String.valueOf(getGroupQuen()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("INDIV QUEEN", LocalDateTime.now(),
                String.valueOf(getIndivQuen()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("GROUP RESET CALL", LocalDateTime.now(),
                String.valueOf(getGroupResetCall()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("INDIV RESET CALL", LocalDateTime.now(),
                String.valueOf(getIndivResetCall()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("GROUP SUCCESS CALL", LocalDateTime.now(),
                String.valueOf(getGroupSuccessCall()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("INDIV SUCCESS CALL", LocalDateTime.now(),
                String.valueOf(getIndivSuccessCall()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("INDIV SUCCESS HANDOVER", LocalDateTime.now(),
                String.valueOf(getIndivSuccessHandover()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("GROUP INITIAL CALL", LocalDateTime.now(),
                String.valueOf(getGroupInitialCall()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("INDIV INITIAL CALL", LocalDateTime.now(),
                String.valueOf(getIndivInitialCall()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("GROUP INITIAL EMERGENCY CALL", LocalDateTime.now(),
                String.valueOf(getGroupInitialEmergencyCall()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("INDIV INITIAL EMERGENCY CALL", LocalDateTime.now(),
                String.valueOf(getIndivInitialEmergencyCall()), name, networkElementName));
    }

    /**
     * Получает время в очереди групп
     * @return - время в ms
     */
    public int getGroupQuenTime() {
        return getSnmpRequestInteger(TetraOid.COS_GROUP_QUEN_TIME_OID.getOid());
    }

    /**
     * Получает время в очереди индивидуальных вызовов
     * @return - время в ms
     */
    public int getIndivQuenTime() {
        return getSnmpRequestInteger(TetraOid.COS_INDIV_QUEN_TIME_OID.getOid());
    }

    /**
     * Получает количество доставленных групповых SDS
     * @return - количество SDS
     */
    public int getGroupDeliveredSds() {
        return getSnmpRequestInteger(TetraOid.COS_GROUP_DELIVERED_SDS_OID.getOid());
    }

    /**
     * Получает количество доставленных индивидуальных SDS
     * @return - количество SDS
     */
    public int getIndivDeliveredSds() {
        return getSnmpRequestInteger(TetraOid.COS_INDIV_DELIVERED_SDS_OID.getOid());
    }

    /**
     * Получает имя сети
     * @return - имя сети
     */
    public String getNetworkName() {
        return getSnmpRequest(TetraOid.COS_NETWORK_NAME_OID.getOid());
    }

    /**
     * Получает количество неотвеченных групповых вызовов
     * @return - количество вызовов
     */
    public int getGroupUnreachCall() {
        return getSnmpRequestInteger(TetraOid.COS_GROUP_UNREACH_CALL_OID.getOid());
    }

    /**
     * Получает количество неотвеченных индивидуальных вызовов
     * @return - количество вызовов
     */
    public int getIndivUnreachCall() {
        return getSnmpRequestInteger(TetraOid.COS_INDIV_UNREACH_CALL_OID.getOid());
    }

    /**
     * Получает количество недоставленных индивидуальных SDS
     * @return - количество вызовов
     */
    public int getIndivUnreachSds() {
        return getSnmpRequestInteger(TetraOid.COS_INDIV_UNREACH_SDS_OID.getOid());
    }

    /**
     * Получает количество групп в очереди
     * @return - количество групп
     */
    public int getGroupQuen() {
        return getSnmpRequestInteger(TetraOid.COS_GROUP_QUEN_OID.getOid());
    }

    /**
     * Получает количество индивидуальных в очереди
     * @return - количество индивидуальных
     */
    public int getIndivQuen() {
        return getSnmpRequestInteger(TetraOid.COS_INDIV_QUEN_OID.getOid());
    }

    /**
     * Получает количество сброшенных групповых вызовов
     * @return - количество вызовов
     */
    public int getGroupResetCall() {
        return getSnmpRequestInteger(TetraOid.COS_GROUP_RESET_CALL_OID.getOid());
    }

    /**
     * Получает количество сброшенных индивидуальных вызовов
     * @return - количество вызовов
     */
    public int getIndivResetCall() {
        return getSnmpRequestInteger(TetraOid.COS_INDIV_RESET_CALL_OID.getOid());
    }

    /**
     * Получает количество успешных групповых вызовов
     * @return - количество вызовов
     */
    public int getGroupSuccessCall() {
        return getSnmpRequestInteger(TetraOid.COS_GROUP_SUCCESS_CALL_OID.getOid());
    }

    /**
     * Получает количество успешных индивидуальных вызовов
     * @return - количество вызовов
     */
    public int getIndivSuccessCall() {
        return getSnmpRequestInteger(TetraOid.COS_INDIV_SUCCESS_CALL_OID.getOid());
    }

    /**
     * Получает количество успешных индивидуальных handover'ов
     * @return - количество handover'ов
     */
    public int getIndivSuccessHandover() {
        return getSnmpRequestInteger(TetraOid.COS_INDIV_HANDOVER_OID.getOid());
    }

    /**
     * Получает количество инициированных групповых вызовов
     * @return - количество вызовов
     */
    public int getGroupInitialCall() {
        return getSnmpRequestInteger(TetraOid.COS_GROUP_INITIAL_CALL_OID.getOid());
    }

    /**
     * Получает количество инициированных индивидуальных вызовов
     * @return - количество вызовов
     */
    public int getIndivInitialCall() {
        return getSnmpRequestInteger(TetraOid.COS_INDIV_INITIAL_CALL_OID.getOid());
    }

    /**
     * Получает количество инициированных групповых экстренных вызовов
     * @return - количество вызовов
     */
    public int getGroupInitialEmergencyCall() {
        return getSnmpRequestInteger(TetraOid.COS_GROUP_INITIAL_EMERGENCY_CALL_OID.getOid());
    }

    /**
     * Получает количество инициированных индивидуальных экстренных вызовов
     * @return - количество вызовов
     */
    public int getIndivInitialEmergencyCall() {
        return getSnmpRequestInteger(TetraOid.COS_INDIV_INITIAL_EMERGENCY_CALL_OID.getOid());
    }

    @Override
    public void printAllInformation() {
        super.printAllInformation();

        if(isOnline()) {

            System.out.println("Имя сети: \t" + getNetworkName());
            System.out.println("Количество доставленных группых SDS: \t" + getGroupDeliveredSds());
            System.out.println("Инициированно групповых вызовов: \t" + getGroupInitialCall());
            System.out.println("Инициированно экстренных групповых вызовов: \t" + getGroupInitialEmergencyCall());
            System.out.println("Количество групп в очереди: \t" + getGroupQuen());
            System.out.println("Длительнность группых очередей: \t" + getGroupQuenTime());
            System.out.println("Количество сброшенных групповых вызовов: \t" + getGroupResetCall());
            System.out.println("Количество успешных групповых вызовов: \t" + getGroupSuccessCall());
            System.out.println("Количество недоступных групповых вызовов: \t" + getGroupUnreachCall());
            System.out.println("Количество доставленных индивидуальных SDS: \t" + getIndivDeliveredSds());
            System.out.println("Инициировано индивидуальных вызовов: \t" + getIndivInitialCall());
            System.out.println("Инициировано экстренных индивидуальных вызовов: \t" + getIndivInitialEmergencyCall());
            System.out.println("Количество абонентов в очереди: \t" + getIndivQuen());
            System.out.println("Длительность индивидуальных очередей: \t" + getIndivQuenTime());
            System.out.println("Количество сброшенных индивидуальных вызовов: \t" + getIndivResetCall());
            System.out.println("Количество успешных индивидуальных вызовов: \t" + getIndivSuccessCall());
            System.out.println("Количество успешных индивидуальных handover'ов: \t" + getIndivSuccessHandover());
            System.out.println("Количество недоступных индивидуальных вызовов: \t" + getIndivUnreachCall());
            System.out.println("Количество недоступных индивидуальных SDS: \t" + getIndivUnreachSds());

        }
    }
}
