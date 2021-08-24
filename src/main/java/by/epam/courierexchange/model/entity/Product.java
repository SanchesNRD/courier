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

    public Product(){
        productType = ProductType.DEFAULT;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BigDecimal getPricePerKilo() {
        return pricePerKilo;
    }

    public void setPricePerKilo(BigDecimal pricePerKilo) {
        this.pricePerKilo = pricePerKilo;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
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
}
