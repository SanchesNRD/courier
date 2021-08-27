package by.epam.courierexchange.model.entity;

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

    public Order(OrderBuilder builder) {
        this.id = builder.id;
        this.client = builder.client;
        this.product = builder.product;
        this.transport = builder.transport;
        this.address = builder.address;
        this.courier = builder.courier;
        this.date = builder.date;
        this.orderStatus = builder.orderStatus;
    }

    public long getId() {
        return id;
    }

    public long getClient() {
        return client;
    }

    public long getProduct() {
        return product;
    }

    public long getTransport() {
        return transport;
    }

    public long getAddress() {
        return address;
    }

    public long getCourier() {
        return courier;
    }

    public Date getDate() {
        return date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
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

    public static class OrderBuilder{

        private long id;
        private long client;
        private long product;
        private long transport;
        private long address;
        private long courier;
        private Date date;
        private OrderStatus orderStatus;

        public OrderBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public OrderBuilder setClient(long client) {
            this.client = client;
            return this;
        }

        public OrderBuilder setProduct(long product) {
            this.product = product;
            return this;
        }

        public OrderBuilder setTransport(long transport) {
            this.transport = transport;
            return this;
        }

        public OrderBuilder setAddress(long address) {
            this.address = address;
            return this;
        }

        public OrderBuilder setCourier(long courier) {
            this.courier = courier;
            return this;
        }

        public OrderBuilder setDate(Date date) {
            this.date = date;
            return this;
        }

        public OrderBuilder setOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
