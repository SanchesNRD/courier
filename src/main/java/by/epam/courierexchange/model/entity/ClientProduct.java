package by.epam.courierexchange.model.entity;

public class ClientProduct extends AbstractEntity{
    private long client;
    private long product;


    public long getClient() {
        return client;
    }

    public void setClient(long client) {
        this.client = client;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o==null || getClass()!=o.getClass()) {
            return false;
        }

        ClientProduct that = (ClientProduct) o;

        return product == that.product && client == that.client;
    }

    @Override
    public int hashCode() {
        int result = (int) (client ^ (client >>> 32));
        result = 31 * result + (int) (product ^ (product >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientProduct{");
        sb.append("client=").append(client);
        sb.append(", product=").append(product);
        sb.append('}');
        return sb.toString();
    }
}
