package by.epam.courierexchange.entity;

import java.util.Arrays;

public enum ProductType{
    DEFAULT((short)1,1), FRAGILE((short)2,2), EXPLOSIVE((short)3,3), POISONOUS((short)4,4);

    private short id;
    private int multiplier;

    ProductType(short id, int multiplier){
        this.id = id;
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public short getId() {
        return id;
    }


    public static ProductType parseType(short id) {
        return Arrays.stream(ProductType.values())
                .filter(status -> status.id == id)
                .findFirst()
                .orElse(DEFAULT);
    }
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
