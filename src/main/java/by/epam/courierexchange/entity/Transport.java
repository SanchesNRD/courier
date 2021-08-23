package by.epam.courierexchange.entity;

import java.io.InputStream;

public class Transport extends AbstractEntity{
    private long id;
    private String name;
    private int averageSpeed;
    private InputStream image;
    private int maxProductWeight;
    private TransportType transportType;

    public Transport(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public int getMaxProductWeight() {
        return maxProductWeight;
    }

    public void setMaxProductWeight(int maxProductWeight) {
        this.maxProductWeight = maxProductWeight;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Transport transport = (Transport) o;

        if (id != transport.id) {
            return false;
        }
        if (averageSpeed != transport.averageSpeed){
            return false;
        }
        if (maxProductWeight != transport.maxProductWeight){
            return false;
        }
        if (name != null ? !name.equals(transport.name) : transport.name != null){
            return false;
        }
        if (image != null ? !image.equals(transport.image) : transport.image != null){
            return false;
        }
        return transportType == transport.transportType;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + averageSpeed;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + maxProductWeight;
        result = 31 * result + (transportType != null ? transportType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transport{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", averageSpeed=").append(averageSpeed);
        sb.append(", image=").append(image);
        sb.append(", maxProductWeight=").append(maxProductWeight);
        sb.append(", transportType=").append(transportType);
        sb.append('}');
        return sb.toString();
    }
}
