package nl.scouting.hit.joomla;

import nl.scouting.hit.common.Valuable;

public enum JoomlaPublished implements Valuable {

    PUBLISHED("1"),
    UNPUBLISHED("0"),
    NOT_SPECIFIED("");

    private final String id;

    JoomlaPublished(final String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

}
