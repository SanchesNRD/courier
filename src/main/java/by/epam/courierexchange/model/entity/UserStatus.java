package by.epam.courierexchange.model.entity;

import java.util.Arrays;

public enum UserStatus{
    CONFIRMED((short)1),
    NON_CONFIRMED((short)2),
    BANED((short)3),
    ADMIN((short) 4);

    private static final String UNDERSCORE = "_";
    private static final String HYPHEN = "-";
    private final short statusId;

    UserStatus(short statusId) {
        this.statusId = statusId;
    }

    public short getStatusId() {
        return statusId;
    }


    public static UserStatus parseStatus(short statusId) {
        return Arrays.stream(UserStatus.values())
                .filter(status -> status.statusId == statusId)
                .findFirst()
                .orElse(NON_CONFIRMED);
    }

    @Override
    public String toString() {
        return this.name().toLowerCase()
                .replaceAll(UNDERSCORE, HYPHEN);
    }
}
