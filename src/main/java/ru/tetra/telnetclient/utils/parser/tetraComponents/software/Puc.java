package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

import ru.tetra.telnetclient.utils.parser.enums.TetraOid;
import ru.tetra.telnetclient.utils.parser.performance.PerformanceEvent;

import java.time.LocalDateTime;

/**
 * Create by graf-anton on 24.12.2020
 *
 * Компонент базовой станции PUC
 */
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

    @Override
    public void updatePerformances(String networkElementName) {

        updateSinglePerformances(networkElementName);
        updateRarelyPerformances(networkElementName);
        updateRarelyPerformances(networkElementName);


    }

    @Override
    public void updateSinglePerformances(String networkElementName) {
        super.updateSinglePerformances(networkElementName);

        for(int i = 1; i <= 2; i++) {

            singlePerformanceEvents.add(new PerformanceEvent("CARRIER " + i + " TX FREQUENCY", LocalDateTime.now(),
                    String.valueOf(getCarTxFrq(i)), name, networkElementName));
            singlePerformanceEvents.add(new PerformanceEvent("CARRIER " + i + " RX FREQUENCY", LocalDateTime.now(),
                    String.valueOf(getCarRxFrq(i)), name, networkElementName));

        }

    }

    @Override
    public void updateRarelyPerformances(String networkElementName) {
        super.updateRarelyPerformances(networkElementName);
    }

    @Override
    public void updateOftenPerformances(String networkElementName) {
        super.updateOftenPerformances(networkElementName);

        oftenPerformanceEvents.add(new PerformanceEvent("GNSS SATELITE NUMBERS", LocalDateTime.now(),
                String.valueOf(getSateliteNumbers()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("BRAC VSWR", LocalDateTime.now(),
                String.valueOf(getBracVswr()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("BRAC TEMPERATURE", LocalDateTime.now(),
                String.valueOf(getBracTemperature()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("BRAC FORWARD POWER", LocalDateTime.now(),
                String.valueOf(getBracForwardPower()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("BRAC REFLECT POWER", LocalDateTime.now(),
                String.valueOf(getBracReflectPower()), name, networkElementName));

        for(int i = 1; i <= 2; i++) {

            oftenPerformanceEvents.add(new PerformanceEvent("CARRIER " + i + " VSWR", LocalDateTime.now(),
                    String.valueOf(getCarVswr(i)), name, networkElementName));
            oftenPerformanceEvents.add(new PerformanceEvent("CARRIER " + i + " TEMPERATURE", LocalDateTime.now(),
                    String.valueOf(getCarTemperature(i)), name, networkElementName));
            oftenPerformanceEvents.add(new PerformanceEvent("CARRIER " + i + " FORWARD POWER", LocalDateTime.now(),
                    String.valueOf(getCarForwardPower(i)), name, networkElementName));
            oftenPerformanceEvents.add(new PerformanceEvent("CARRIER " + i + " JAMMING", LocalDateTime.now(),
                    String.valueOf(getCarRxFrq(i)), name, networkElementName));
        }
    }

    /**
     * Получает количество видимых спутников GPS
     * @return  - количество спутников
     */
    public int getSateliteNumbers() {
        return getSnmpRequestInteger(TetraOid.PUC_GPS_NUMBER_OID.getOid());
    }

    /**
     * Получает значение КСВ на антенном разъеме
     * @return - значение КСВ
     */
    public double getBracVswr() {
        return getSnmpRequestDouble(TetraOid.PUC_BRAC_VSWR_OID.getOid());
    }

    /**
     * Получает значение отраженной мощности на антенном разъеме
     * @return - отраженная мощность в мВт
     */
    public int getBracReflectPower() {
        return getSnmpRequestInteger(TetraOid.PUC_BRAC_REFLECT_POWER_OID.getOid());
    }

    /**
     * Получает значение передаваемой мощности на антенный разъем
     * @return - передаваемая мощность в мВт
     */
    public int getBracForwardPower() {
        return getSnmpRequestInteger(TetraOid.PUC_BRAC_FORWARD_POWER_OID.getOid());
    }

    /**
     * Получает температура сумматора
     * @return - температура в градусах цельсия
     */
    public int getBracTemperature() {
        return getSnmpRequestInteger(TetraOid.PUC_BRAC_TEMPERATURE_OID.getOid());
    }

    /**
     * Получает значение передаваемой мощности несущей
     * @param carNumber - номер несущей
     * @return          - мощность в мВт
     */
    public int getCarForwardPower(int carNumber) {
        return getSnmpRequestInteger(TetraOid.PUC_CAR_FORWARD_POWER_OID.getOid() + carNumber);
    }

    /**
     * Получает температуру передатчика несущей
     * @param carNumber - номер несущей
     * @return          - температур в градусах цельсия
     */
    public int getCarTemperature(int carNumber) {
        return getSnmpRequestInteger(TetraOid.PUC_CAR_TEMPERATURE_OID.getOid() + carNumber);
    }

    /**
     * Получает значение уровня помех на приемнике несущей
     * @param carNumber - номер несущей
     * @return          - уровень помех
     */
    public int getCarJamming(int carNumber) {
        return getSnmpRequestInteger(TetraOid.PUC_CAR_JAMMING_OID.getOid() + (carNumber == 2 ? 6 : carNumber));
    }

    /**
     * Получает значение премной частоты несущей
     * @param carNumber - номер несущей
     * @return          - частота в Гц
     */
    public int getCarRxFrq(int carNumber) {
        return getSnmpRequestInteger(TetraOid.PUC_CAR_RX_FRQ_OID.getOid() + carNumber);
    }

    /**
     * Получает значение передающей частоты несущей
     * @param carNumber - номер несущей
     * @return          - частота в Гц
     */
    public int getCarTxFrq(int carNumber) {
        return getSnmpRequestInteger(TetraOid.PUC_CAR_TX_FRQ_OID.getOid() + carNumber);
    }

    /**
     * Получает значение КСВ на антенном разъеме
     * @param carNumber    - номер несущей
     * @return             - значение КСВ
     */
    public double getCarVswr(int carNumber) {
        return getSnmpRequestDouble(TetraOid.PUC_CAR_VSWR_OID.getOid() + carNumber);
    }

    @Override
    public void printAllInformation() {
        super.printAllInformation();

        if(isOnline()) {
            System.out.println("Кол-во спутников " + getSateliteNumbers());
            System.out.println("КСВ на антенне " + getBracVswr());
            System.out.println("Температура сумматора " + getBracTemperature());
            System.out.println("Передаваемая мощность на антенну " + getBracForwardPower());
            System.out.println("Отраженная мощность от антенны " + getBracReflectPower());

            for(int i = 1; i <= 2; i++) {

                System.out.println("Частота RX несущей " + i + " " + getCarRxFrq(i));
                System.out.println("Частота TX несущей " + i + " " + getCarTxFrq(i));
                System.out.println("КСВ несущей " + i + " " + getCarVswr(i));
                System.out.println("Передаваемая мощность несущей " + i + " " + getCarForwardPower(i));
                System.out.println("Температура несущей " + i + " " + getCarTemperature(i));
                System.out.println("Помехи несущей " + i + " " + getCarJamming(i));
            }
        }
    }
}
