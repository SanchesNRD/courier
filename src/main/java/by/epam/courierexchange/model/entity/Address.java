package by.epam.courierexchange.model.entity;

public class Address extends AbstractEntity{
    private long id;
    private String country;
    private String city;
    private String street;
    private int street_number;
    private int apartment;

    public Address(AddressBuilder builder) {
        this.id = builder.id;
        this.country = builder.country;
        this.city = builder.city;
        this.street = builder.street;
        this.street_number = builder.street_number;
        this.apartment = builder.apartment;
    }

    public long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getStreet_number() {
        return street_number;
    }

    public int getApartment() {
        return apartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Address address = (Address) o;

        if (id != address.id) {
            return false;
        }
        if (street_number != address.street_number) {
            return false;
        }
        if (apartment != address.apartment) {
            return false;
        }
        if (country != null ? !country.equals(address.country) : address.country != null) {
            return false;
        }
        if (city != null ? !city.equals(address.city) : address.city != null) {
            return false;
        }
        return street != null ? street.equals(address.street) : address.street == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + street_number;
        result = 31 * result + apartment;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append("id=").append(id);
        sb.append(", country='").append(country).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", street_number=").append(street_number);
        sb.append(", apartment=").append(apartment);
        sb.append('}');
        return sb.toString();
    }

    public static class AddressBuilder{

        private long id;
        private String country;
        private String city;
        private String street;
        private int street_number;
        private int apartment;

        public AddressBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public AddressBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public AddressBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public AddressBuilder setStreet(String street) {
            this.street = street;
            return this;
        }

        public AddressBuilder setStreet_number(int street_number) {
            this.street_number = street_number;
            return this;
        }

        public AddressBuilder setApartment(int apartment) {
            this.apartment = apartment;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
