package by.epam.courierexchange.model.entity;

public class CourierTransport extends AbstractEntity{
    private long courier;
    private long transport;

    public long getCourier() {
        return courier;
    }

    public void setCourier(long courier) {
        this.courier = courier;
    }

    public long getTransport() {
        return transport;
    }

    public void setTransport(long transport) {
        this.transport = transport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o==null && getClass()!=o.getClass()) {
            return false;
        }

        CourierTransport that = (CourierTransport) o;

        return transport == that.transport && courier==that.courier;
    }

    @Override
    public int hashCode() {
        int result = (int) (courier ^ (courier >>> 32));
        result = 31 * result + (int) (transport ^ (transport >>> 32));
        return result;
    }

    @Override
    public String
    toString() {
        final StringBuilder sb = new StringBuilder("CourierTransport{");
        sb.append("courier=").append(courier);
        sb.append(", transport=").append(transport);
        sb.append('}');
        return sb.toString();
    }
}
