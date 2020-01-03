package nl.scouting.hit.kampinfo.export;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.scouting.hit.common.Range;

public class KampInfoRange implements Range {
    @JsonProperty("min")
    private int minimum;
    @JsonProperty("max")
    private int maximum;

    public KampInfoRange(final int minimum, final int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }

}
