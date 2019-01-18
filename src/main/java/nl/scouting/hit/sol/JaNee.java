package nl.scouting.hit.sol;

import nl.scouting.hit.common.Valuable;

public enum JaNee implements Valuable {
    JA(1), NEE(0);

    public final String id;

    JaNee(final int id) {
        this.id = String.valueOf(id);
    }

    public String getId() {
        return id;
    }

    public boolean asBoolean() {
        return id.equals("1");
    }
}
