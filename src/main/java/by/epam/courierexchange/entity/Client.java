package by.epam.courierexchange.entity;

public class Client extends User{
    private long address_id;

    public long getAddress() {
        return address_id;
    }

    public void setAddress(long address_id) {
        this.address_id = address_id;
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

        Client client = (Client) o;

        return address_id == client.address_id;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (address_id ^ (address_id >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append(super.toString());
        sb.append("").append(address_id);
        sb.append('}');
        return sb.toString();
    }
}
