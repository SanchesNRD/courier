package by.epam.courierexchange.model.entity;

public class Courier extends User{
    private double rating;

    public Courier(CourierBuilder courierBuilder) {
        super(courierBuilder.builder);
        this.rating = courierBuilder.rating;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Courier courier = (Courier) o;

        return Double.compare(courier.rating, rating) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Courier{");
        sb.append(super.toString());
        sb.append(", rating=").append(rating);
        sb.append('}');
        return sb.toString();
    }

    public static class CourierBuilder{

        private UserBuilder builder;
        private double rating;

        public CourierBuilder setBuilder(UserBuilder builder) {
            this.builder = builder;
            return this;
        }

        public CourierBuilder setRating(double rating) {
            this.rating = rating;
            return this;
        }

        public Courier build() {
            return new Courier(this);
        }
    }
}
