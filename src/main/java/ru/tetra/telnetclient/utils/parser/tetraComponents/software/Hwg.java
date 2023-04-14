package ru.tetra.telnetclient.utils.parser.tetraComponents.software;

import ru.tetra.telnetclient.utils.parser.enums.TetraOid;
import ru.tetra.telnetclient.utils.parser.performance.PerformanceEvent;

import java.time.LocalDateTime;

/**
 * Create by graf-anton on 29.12.2020
 *
 * Компонент мониторинга аппаратной части базовой станции HWG
 */
public class Hwg extends Component{

    //SNMP-порт по умолчанию
    private static final int DEFAULT_SNMP_PORT = 169;
    //SNMP-community по умолчанию
    private static final String DEFAULT_READ_SNMP_COMMUNITY = "public";

    public Hwg(String name) {
        super(name, "HWG");

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

        singlePerformanceEvents.add(new PerformanceEvent("CPU MODEL", LocalDateTime.now(),
                getCpuModel(), name, networkElementName));
        singlePerformanceEvents.add(new PerformanceEvent("RAM TOTAL", LocalDateTime.now(),
                String.valueOf(getRamTotal()), name, networkElementName));

        int hddCount = getHddCount();
        singlePerformanceEvents.add(new PerformanceEvent("HDD COUNT", LocalDateTime.now(),
                String.valueOf(hddCount), name, networkElementName));

        for(int i = 1; i <= hddCount; i++) {

            singlePerformanceEvents.add(new PerformanceEvent("HDD " + i + " NAME", LocalDateTime.now(),
                    getHddName(i), name, networkElementName));
            singlePerformanceEvents.add(new PerformanceEvent("HDD " + i + " MOUNT", LocalDateTime.now(),
                    getHddMount(i), name, networkElementName));
            singlePerformanceEvents.add(new PerformanceEvent("HDD " + i + " TOTAL SIZE", LocalDateTime.now(),
                    String.valueOf(getHddTotalSize(i)), name, networkElementName));
        }
    }

    @Override
    public void updateRarelyPerformances(String networkElementName) {
        super.updateRarelyPerformances(networkElementName);

        rarelyPerformanceEvents.add(new PerformanceEvent("CPU BUSY", LocalDateTime.now(),
                String.valueOf(getCpuBusy()), name, networkElementName));
        rarelyPerformanceEvents.add(new PerformanceEvent("RAM FREE", LocalDateTime.now(),
                String.valueOf(getRamFree()), name, networkElementName));
        rarelyPerformanceEvents.add(new PerformanceEvent("RAM BUSY", LocalDateTime.now(),
                String.valueOf(getRamBusy()), name, networkElementName));

        int hddCount = getHddCount();

        for(int i = 1; i <= hddCount; i++) {
            rarelyPerformanceEvents.add(new PerformanceEvent("HDD " + i + " FREE SIZE", LocalDateTime.now(),
                    String.valueOf(getHddFreeSize(i)), name, networkElementName));
            rarelyPerformanceEvents.add(new PerformanceEvent("HDD " + i + " BUSY SIZE", LocalDateTime.now(),
                    String.valueOf(getHddBusySize(i)), name, networkElementName));
        }
    }

    @Override
    public void updateOftenPerformances(String networkElementName) {
        super.updateOftenPerformances(networkElementName);

        oftenPerformanceEvents.add(new PerformanceEvent("MAIN BOARD TEMPERATURE", LocalDateTime.now(),
                String.valueOf(getMbTemperature()), name, networkElementName));
        oftenPerformanceEvents.add(new PerformanceEvent("CPU TEMPERATURE", LocalDateTime.now(),
                String.valueOf(getCpuTemperature()), name, networkElementName));
    }

    /**
     * Получает температуру материнской планы IntelPC
     * @return  - температура с градусах цельсия
     */
    public int getMbTemperature() {
        return getSnmpRequestInteger(TetraOid.HWG_MB_TEMPERATURE_OID.getOid());
    }

    /**
     * Получает модель процессора IntelPC
     * @return - модель процессора
     */
    public String getCpuModel() {
        return getSnmpRequest(TetraOid.HWG_CPU_MODEL_OID.getOid());
    }

    /**
     * Получает температуру процессора IntelPC
     * @return - температура процессора в градусах цельсия
     */
    public int getCpuTemperature() {
        return getSnmpRequestInteger(TetraOid.HWG_CPU_TEMPERATURE_OID.getOid());
    }

    /**
     * Получает загрузку процессора
     * @return - загрузка процессора в %
     */
    public int getCpuBusy() {
        return getSnmpRequestInteger(TetraOid.HWG_CPU_BUSY_OID.getOid());
    }

    /**
     * Получает общий объем ОЗУ IntelPC
     * @return - объем ОЗУ в байтах
     */
    public int getRamTotal() {
        return getSnmpRequestInteger(TetraOid.HWG_RAM_TOTAL_OID.getOid());
    }

    /**
     * Получает свободный объем ОЗУ IntelPC
     * @return  - объем ОЗУ в байтах
     */
    public int getRamFree() {
        return getSnmpRequestInteger(TetraOid.HWG_RAM_FREE_OID.getOid());
    }

    /**
     * Получает занятый объем ОЗУ IntelPC
     * @return  - объем ОЗУ в байтах
     */
    public int getRamBusy() {
        return getSnmpRequestInteger(TetraOid.HWG_RAM_BUSY_OID.getOid());
    }

    /**
     * Получает общий объем раздела диска IntelPC
     * @param hddNumber    - номер раздела диска
     * @return      - объем ОЗУ в байтах
     */
    public int getHddTotalSize(int hddNumber) {
        return getSnmpRequestInteger(TetraOid.HWG_HDD_TOTAL_OID.getOid() + hddNumber);
    }

    /**
     * Получает занятый объем раздела диска IntelPC
     * @param hddNumber    - номер раздела диска
     * @return      - объем ОЗУ в байтах
     */
    public int getHddBusySize(int hddNumber) {
        return getSnmpRequestInteger(TetraOid.HWG_HDD_BUSY_OID.getOid() + hddNumber);
    }

    /**
     * Получает свободный объем раздела диска IntelPC
     * @param hddNumber    - номер раздела диска
     * @return      - объем ОЗУ в байтах
     */
    public int getHddFreeSize(int hddNumber) {
        return getSnmpRequestInteger(TetraOid.HWG_HDD_FREE_OID.getOid() + hddNumber);
    }

    /**
     * Получает имя раздела диска IntelPC
     * @param hddNumber    - номер раздела диска
     * @return      - имя раздела
     */
    public String getHddName(int hddNumber) {
        return getSnmpRequest(TetraOid.HWG_HDD_NAME_OID.getOid() + hddNumber);
    }

    /**
     * Получает точку монтирования раздела диска IntelPC
     * @param hddNumber    - номер раздела диска
     * @return      - точку монтирования раздела диска
     */
    public String getHddMount(int hddNumber) {
        return getSnmpRequest(TetraOid.HWG_HDD_MOUNT_OID.getOid() + hddNumber);
    }


    /**
     * Получает количество разделов дисков IntelPC
     * @return  - количество разделов
     */
    public int getHddCount() {

        int count = 1;

        while (!getSnmpRequest(TetraOid.HWG_HDD_NAME_OID.getOid() + count).isEmpty()){
            count++;
        }

        return count -1;

    }

    @Override
    public void printAllInformation() {
        super.printAllInformation();

        if(isOnline()) {

            System.out.println("Модель процессора: \t" + getCpuModel());
            System.out.println("Загрузка процессора: \t" + getCpuBusy());
            System.out.println("Температура процессора: \t" + getCpuTemperature());
            System.out.println("Температура материнской платы: \t" + getMbTemperature());
            System.out.println("Всего ОЗУ: \t" + getRamTotal());
            System.out.println("Занято ОЗУ: \t" + getRamBusy());
            System.out.println("Свободно ОЗУ: \t" + getRamFree());

            int hddCount = getHddCount();

            for(int i = 1; i <= hddCount; i++) {
                System.out.println("Имя раздела: \t" + getHddName(i));
                System.out.println("Точка монтирования раздела: \t" + getHddMount(i));
                System.out.println("Размер раздела: \t" + getHddTotalSize(i));
                System.out.println("Занято раздела: \t" + getHddBusySize(i));
                System.out.println("Свободно раздела: \t" + getHddFreeSize(i));

            }

        }
    }

}
