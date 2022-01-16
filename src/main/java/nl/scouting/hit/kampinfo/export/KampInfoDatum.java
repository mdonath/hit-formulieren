package nl.scouting.hit.kampinfo.export;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.scouting.hit.common.Datum;

import java.util.Arrays;
import java.util.List;

public class KampInfoDatum implements Datum {

    public static final List<String> MAANDEN = Arrays.asList("januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober", "november", "december");

    @JsonProperty("day")
    private int dag;

    @JsonProperty("month")
    private String maand;

    @JsonProperty("year")
    private int jaar;

    public int getDag() {
        return dag;
    }

    public KampInfoDatum() {
        super();
    }

    public KampInfoDatum(final int dag, final String maand, final int jaar) {
        super();
        this.dag = dag;
        this.maand = maand;
        this.jaar = jaar;
    }


    public String getMaandAsString() {
        return maand;
    }

    public int getMaand() {
        return MAANDEN.indexOf(maand) + 1;
    }

    public int getJaar() {
        return jaar;
    }

}
