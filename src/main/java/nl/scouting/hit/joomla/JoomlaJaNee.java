package nl.scouting.hit.joomla;

import nl.scouting.hit.common.Valuable;

public enum JoomlaJaNee implements Valuable {

    JA("1"),
    NEE("0");

    private final String id;

    JoomlaJaNee(final String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

}
