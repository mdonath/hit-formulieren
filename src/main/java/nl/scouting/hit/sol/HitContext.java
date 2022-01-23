package nl.scouting.hit.sol;

public class HitContext {
    public String baseUrl;
    public String username;
    public String password;
    public String idEvenement;
    public String naamEvenement;
    public String naamSpeleenheid;
    public String datumDeelnemersinformatie;

    public static class Builder {
        private final HitContext context = new HitContext();

        public static Builder create() {
            return new Builder();
        }

        public HitContext build() {
            return context;
        }

        public Builder baseUrl(final String baseUrl) {
            context.baseUrl = baseUrl;
            return this;
        }

        public Builder username(final String username) {
            context.username = username;
            return this;
        }

        public Builder password(final String password) {
            context.password = password;
            return this;
        }

        public Builder idEvenement(final String idEvenement) {
            context.idEvenement = idEvenement;
            return this;
        }

        public Builder naamEvenement(final String naamEvenement) {
            context.naamEvenement = naamEvenement;
            return this;
        }

        public Builder naamSpeleenheid(final String naamSpeleenheid) {
            context.naamSpeleenheid = naamSpeleenheid;
            return this;
        }

        public Builder datumDeelnemersinformatie(final String datumDeelnemersinformatie) {
            context.datumDeelnemersinformatie = datumDeelnemersinformatie;
            return this;
        }
    }
}
