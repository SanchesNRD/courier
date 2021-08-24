package by.epam.courierexchange.model.entity;

import java.util.Arrays;

public enum OrderStatus {
    TREATMENT((short)1), AGREED((short)2), DELIVERED((short)3), COMPLETED((short)4), CANCELED((short)5);

    private final short id;

    OrderStatus(short id){
        this.id=id;
    }

    public short getStatusId() {
        return id;
    }


    public static OrderStatus parseStatus(short statusId) {
        return Arrays.stream(OrderStatus.values())
                .filter(status -> status.id == statusId)
                .findFirst()
                .orElse(TREATMENT);
    }
}
