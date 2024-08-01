package Mario.Fragnito.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class Order {
    private long id;
    private String status;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private List<Product> products;
    private Customer customer;

    public Order(String status, List<Product> products, Customer customer) {
        Random rand = new Random();
        this.id = rand.nextLong(100000, 100000000);
        this.status = status;
        this.orderDate = LocalDate.now();
        this.deliveryDate = LocalDate.now().plusDays(1);
        this.products = products;
        this.customer = customer;
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", products=" + products +
                ", customer=" + customer +
                '}';
    }
}
