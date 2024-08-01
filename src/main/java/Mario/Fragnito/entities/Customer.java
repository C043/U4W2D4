package Mario.Fragnito.entities;

public class Customer extends HasNameAndId {
    private final int tier;

    public Customer(String name, int tier) {
        super(name);
        this.tier = tier;
    }

    public int getTier() {
        return this.tier;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "tier=" + tier +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
