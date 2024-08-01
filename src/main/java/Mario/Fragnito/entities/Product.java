package entities;

public class Product extends HasNameAndId {
    private final String category;
    private double price;

    public Product(String name, String category, double price) {
        super(name);
        this.category = category;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "category='" + category + '\'' +
                ", price=" + price +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
