package Mario.Fragnito.entities;

import java.util.Random;

public abstract class HasNameAndId {
    protected long id;
    protected String name;

    public HasNameAndId(String name) {
        Random rand = new Random();
        this.id = rand.nextLong(100000, 100000000);
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
