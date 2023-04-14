package ru.tetra.telnetclient.utils.parser.utils;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * Create by graf-anton on 23.12.2020
 *
 * Осуществляет непосредственное взаимодействие по протоколу SNMP
 */
public class SnmpHelper {

    /**
     * Выполняет запрос snmp-get
     * @param strAddress    - ip-адрес
     * @param port          - snmp-порт
     * @param community     - snmp-community для чтения
     * @param strOID        - snmp OID
     * @return              - результат выполнения запроса
     */
    public static String snmpGet(String strAddress, int port, String community, String strOID) {

        String str="";

        try {

            OctetString community1 = new OctetString(community);
            strAddress= strAddress + "/" + port;
            Address targetAddress = new UdpAddress(strAddress);

            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();

            CommunityTarget comtarget = new CommunityTarget();
            comtarget.setCommunity(community1);
            comtarget.setVersion(SnmpConstants.version1);
            comtarget.setAddress(targetAddress);
            comtarget.setRetries(1);
            comtarget.setTimeout(100);

            PDU pdu = new PDU();
            ResponseEvent response;
            Snmp snmp;
            pdu.add(new VariableBinding(new OID(strOID)));

            pdu.setType(PDU.GET);

            snmp = new Snmp(transport);
            response = snmp.get(pdu,comtarget);

            if(response.getResponse() != null) {

                if(response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")) {

                    PDU pduresponse = response.getResponse();
                    str = pduresponse.getVariableBindings().get(0).getVariable().toString();
                    if(str.contains("=")) {

                        int len = str.indexOf("=");
                        str = str.substring(len+1);

                    }

                }

            }

            snmp.close();

        } catch(Exception e) { e.printStackTrace(); }

//        System.out.println("Response=" + str);
        return str;
    }

    /**
     * Преобразует дату из HEX-String в LocalDateTime
     * @param snmpDate  - дата в формате HEX-String
     * @return          - дата в формате LocalDateTime
     */
    public static LocalDateTime convertSnmpDateTime(String snmpDate) {
        if (snmpDate.length() % 2 != 0) {
            throw new IllegalArgumentException("Не корректная строка байтов, должна быть длина 8");
        }
        if (snmpDate.startsWith("00")
                || snmpDate.charAt(0) > '7') {
            throw new IllegalArgumentException(
                    "Эта простая реализация не может обрабатывать годы ни до 256 года, ни после 32767; " +
                            "необходимо другое преобразование в байты");
        }

        byte[] bytes = new BigInteger(snmpDate, 16).toByteArray();

        int year = (bytes[0] & 0xFF) * 0x100 + (bytes[1] & 0xFF);
        int month = bytes[2] & 0xFF;
        checkRange(month, 1, 12);
        int dayOfMonth = bytes[3] & 0xFF;
        checkRange(dayOfMonth, 1, 31);
        int hour = bytes[4] & 0xFF;
        checkRange(hour, 0, 23);
        int minute = bytes[5] & 0xFF;
        checkRange(minute, 0, 59);
        int second = 0;
        int deciseconds = 0;
        if (bytes.length >= 7) {
            second = bytes[6] & 0xFF;
            checkRange(second, 0, 60); // 60 will cause conversion to fail, though

            if (bytes.length >= 8) {
                deciseconds = bytes[7] & 0xFF;
                checkRange(deciseconds, 0, 9);
            }
        }

        return LocalDateTime.of(year, month, dayOfMonth,
                hour, minute, second, deciseconds * 100_000_000);

    }

    /**
     * Проверяет, входит ли значение в диапазон
     * @param value - проверяемое значение
     * @param min   - минимально допустимое значение
     * @param max   - максимально допустимое значение
     */
    private static void checkRange(int value, int min, int max) {
        if (value < min || value > max) {
            throw new IllegalArgumentException("Значение " + value + " вне диапазона " + min + ".." + max);
        }
    }
}
