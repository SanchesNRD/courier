package by.epam.courierexchange.model.entity;

import java.math.BigDecimal;

public class Product extends AbstractEntity{
    private long id;
    private int weight;
    private int length;
    private int width;
    private int height;
    private BigDecimal pricePerKilo;
    private ProductType productType;

    public Product(ProductBuilder builder) {
        this.id = builder.id;
        this.weight = builder.weight;
        this.length = builder.length;
        this.width = builder.width;
        this.height = builder.height;
        this.pricePerKilo = builder.pricePerKilo;
        this.productType = builder.productType;
    }

    public long getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BigDecimal getPricePerKilo() {
        return pricePerKilo;
    }

    public ProductType getProductType() {
        return productType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        if (pricePerKilo != null ? !pricePerKilo.equals(product.pricePerKilo) : product.pricePerKilo != null) {
            return false;
        }
        return productType == product.productType && id == product.id &&
                weight == product.weight && length == product.length &&
                width == product.width && height == product.height;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + weight;
        result = 31 * result + length;
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + (pricePerKilo != null ? pricePerKilo.hashCode() : 0);
        result = 31 * result + (productType != null ? productType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(id);
        sb.append(", weight=").append(weight);
        sb.append(", length=").append(length);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", pricePerKilo=").append(pricePerKilo);
        sb.append(", productType=").append(productType);
        sb.append('}');
        return sb.toString();
    }

    public static class ProductBuilder{

        private long id;
        private int weight;
        private int length;
        private int width;
        private int height;
        private BigDecimal pricePerKilo;
        private ProductType productType;

        public ProductBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public ProductBuilder setLength(int length) {
            this.length = length;
            return this;
        }

        public ProductBuilder setWidth(int width) {
            this.width = width;
            return this;
        }

        public ProductBuilder setHeight(int height) {
            this.height = height;
            return this;
        }

        public ProductBuilder setPricePerKilo(BigDecimal pricePerKilo) {
            this.pricePerKilo = pricePerKilo;
            return this;
        }

        public ProductBuilder setProductType(ProductType productType) {
            this.productType = productType;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
