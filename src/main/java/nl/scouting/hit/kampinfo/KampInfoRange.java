package nl.scouting.hit.kampinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.scouting.hit.common.Range;

public class KampInfoRange implements Range {
    @JsonProperty("min")
    private int minimum;
    @JsonProperty("max")
    private int maximum;

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }
}
