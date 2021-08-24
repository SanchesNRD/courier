package by.epam.courierexchange.model.entity;

import java.util.Arrays;

public enum UserStatus{
    CONFIRMED(1), NON_CONFIRMED(2), BANED(3);

    private static final String UNDERSCORE = "_";
    private static final String HYPHEN = "-";
    private final int statusId;

    UserStatus(int statusId) {
        this.statusId = statusId;
    }

    public long getStatusId() {
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
