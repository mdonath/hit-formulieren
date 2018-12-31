package nl.scouting.hit.sol;

public enum JaNee implements Valuable {
    JA(1), NEE(0);

    public final String id;

    JaNee(final int id) {
        this.id = String.valueOf(id);
    }

    public String getId() {
        return id;
    }
}
