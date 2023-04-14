package ru.tetra.telnetclient.utils.parser.enums;

/**
 * Create by graf-anton on 24.12.2020
 *
 * Статусы аварий
 */
public enum AlarmStatus {

    CRITICAL(2),
    MAJOR(3),
    MINOR(4),
    INFO(5),
    OK(6);

    private int statusCode;

    AlarmStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Возвращает Статус аварии по коду
     * @param statusCode    - код аварии
     * @return              - статус аварии
     */
    public static AlarmStatus getAlarmStatusByCode(int statusCode){

        for(AlarmStatus status : AlarmStatus.values()) {
            if (status.getStatusCode() == statusCode)
                return status;
        }

        return OK;
    }
}
