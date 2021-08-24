package by.epam.courierexchange.model.entity;

import java.util.Arrays;

public enum TransportType{
    BIKE((short)1), CAR((short)2), TRUCK((short)3);

    private short id;

    TransportType(short id){
        this.id=id;
    }

    public short getId() {
        return id;
    }


    public static TransportType parseType(short id) {
        return Arrays.stream(TransportType.values())
                .filter(status -> status.id == id)
                .findFirst()
                .orElse(BIKE);
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
