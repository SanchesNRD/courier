package by.epam.courierexchange.entity;

import java.sql.Date;

public class Order extends AbstractEntity{
    private long id;
    private long client;
    private long product;
    private long transport;
    private long address;
    private long courier;
    private Date date;
    private OrderStatus orderStatus;

    public Order(){
        //orderStatus = OrderStatus.;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getTransport() {
        return transport;
    }

    public void setTransport(long transport) {
        this.transport = transport;
    }

    public long getAddress() {
        return address;
    }

    public void setAddress(long address) {
        this.address = address;
    }

    public long getCourier() {
        return courier;
    }

    public void setCourier(long courier) {
        this.courier = courier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o==null || getClass()!=o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        if (date != null ? !date.equals(order.date) : order.date != null){
            return false;
        }
        return orderStatus == order.orderStatus && id==order.id &&
                client == order.client && product==order.product &&
                transport == order.transport && address == order.address &&
                courier == order.courier;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (client ^ (client >>> 32));
        result = 31 * result + (int) (product ^ (product >>> 32));
        result = 31 * result + (int) (transport ^ (transport >>> 32));
        result = 31 * result + (int) (address ^ (address >>> 32));
        result = 31 * result + (int) (courier ^ (courier >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", client=").append(client);
        sb.append(", product=").append(product);
        sb.append(", transport=").append(transport);
        sb.append(", address=").append(address);
        sb.append(", courier=").append(courier);
        sb.append(", date=").append(date);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append('}');
        return sb.toString();
    }
}
