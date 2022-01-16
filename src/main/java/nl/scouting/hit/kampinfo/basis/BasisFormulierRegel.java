package nl.scouting.hit.kampinfo.basis;

import nl.scouting.hit.kampinfo.export.KampInfoDatum;
import nl.scouting.hit.kampinfo.export.KampInfoFormulierExportRegel;
import nl.scouting.hit.kampinfo.export.KampInfoRange;

public class BasisFormulierRegel extends KampInfoFormulierExportRegel {

    // Builder pattern
    public static class BasisFormulierRegelBuilder {

        private final BasisFormulierRegel instance;

        public BasisFormulierRegelBuilder() {
            this.instance = new BasisFormulierRegel();
        }

        public BasisFormulierRegelBuilder withJaar(final int jaar) {
            instance.setJaar(jaar);
            return this;
        }

        public BasisFormulierRegelBuilder withKampID(final int kampID) {
            instance.setKampID(kampID);
            return this;
        }

        public BasisFormulierRegelBuilder withShantiID(final int shantiID) {
            instance.setShantiID(shantiID);
            return this;
        }

        public BasisFormulierRegelBuilder withLocatie(final String locatie) {
            instance.setLocatie(locatie);
            return this;
        }

        public BasisFormulierRegelBuilder withProjectcode(final String projectcode) {
            instance.setProjectcode(projectcode);
            return this;
        }

        public BasisFormulierRegelBuilder withKampnaam(final String kampnaam) {
            instance.setKampnaam(kampnaam);
            return this;
        }

        public BasisFormulierRegelBuilder withIsOuderKindKamp(final boolean isOuderKindKamp) {
            instance.setIsOuderKindKamp(isOuderKindKamp);
            return this;
        }


        public BasisFormulierRegelBuilder withIsOuderKindKamp(final int deelnemersprijs) {
            instance.setDeelnemersprijs(deelnemersprijs);
            return this;
        }

        public BasisFormulierRegelBuilder withLeeftijd(final int min, final int max) {
            instance.setLeeftijd(new KampInfoRange(min, max));
            return this;
        }

        public BasisFormulierRegelBuilder withLeeftijdMarge(final int min, final int max) {
            instance.setLeeftijdMarge(new KampInfoRange(min, max));
            return this;
        }

        public BasisFormulierRegelBuilder withAantalDeelnemers(final int min, final int max) {
            instance.setAantalDeelnemers(new KampInfoRange(min, max));
            return this;
        }

        public BasisFormulierRegelBuilder withMaximumAantalUitEenGroep(final int maximumAantalUitEenGroep) {
            instance.setMaximumAantalUitEenGroep(maximumAantalUitEenGroep);
            return this;
        }

        public BasisFormulierRegelBuilder withAantalSubgroepen(final int min, final int max) {
            instance.setAantalSubgroepen(new KampInfoRange(min, max));
            return this;
        }

        public BasisFormulierRegelBuilder withAantalDeelnemersInSubgroep(final int min, final int max) {
            instance.setAantalDeelnemersInSubgroep(new KampInfoRange(min, max));
            return this;
        }

        public BasisFormulierRegelBuilder withDeelbaarDoor(final int deelbaarDoor) {
            instance.setDeelbaarDoor(deelbaarDoor);
            return this;
        }

        public BasisFormulierRegelBuilder withKosteloosAnnulerenTot(final int dag, final String maand, final int jaar) {
            instance.setKosteloosAnnulerenTot(new KampInfoDatum(dag, maand, jaar));
            return this;
        }

        public BasisFormulierRegelBuilder withProjectInningsdatum(final int dag, final String maand, final int jaar) {
            instance.setProjectInningsdatum(new KampInfoDatum(dag, maand, jaar));
            return this;
        }

        public BasisFormulierRegelBuilder withVolledigeKostenAnnulerenVanaf(final int dag, final String maand, final int jaar) {
            instance.setVolledigeKostenAnnulerenVanaf(new KampInfoDatum(dag, maand, jaar));
            return this;
        }

        public BasisFormulierRegelBuilder withEvenementStart(final int dag, final String maand, final int jaar, final String tijd) {
            instance.setEvenementStart(new KampInfoDatum(dag, maand, jaar));
            instance.setEvenementStartTijd(tijd);
            return this;
        }

        public BasisFormulierRegelBuilder withEvenementEind(final int dag, final String maand, final int jaar, final String tijd) {
            instance.setEvenementEind(new KampInfoDatum(dag, maand, jaar));
            instance.setEvenementEindTijd(tijd);
            return this;
        }

        public BasisFormulierRegelBuilder withInschrijvingStart(final int dag, final String maand, final int jaar) {
            instance.setInschrijvingStart(new KampInfoDatum(dag, maand, jaar));
            return this;
        }

        public BasisFormulierRegelBuilder withInschrijvingEind(final int dag, final String maand, final int jaar) {
            instance.setInschrijvingEind(new KampInfoDatum(dag, maand, jaar));
            return this;
        }


        public BasisFormulierRegel build() {
            return instance;
        }
    }

    public String getFormulierNaam() {
        return String.format("HIT %d %s (niet wijzigen)", this.jaar, this.kampnaam);
    }

    private void setJaar(final int jaar) {
        this.jaar = jaar;
    }

    private void setKampID(final int kampID) {
        this.kampID = kampID;
    }

    private void setShantiID(final int shantiID) {
        this.shantiID = shantiID;
    }

    private void setLocatie(final String locatie) {
        this.locatie = locatie;
    }

    private void setProjectcode(final String projectcode) {
        this.projectcode = projectcode;
    }

    private void setKampnaam(final String kampnaam) {
        this.kampnaam = kampnaam;
    }

    private void setIsOuderKindKamp(final boolean isOuderKindKamp) {
        this.isOuderKindKamp = isOuderKindKamp;
    }

    private void setDeelnemersprijs(final int deelnemersprijs) {
        this.deelnemersprijs = deelnemersprijs;
    }

    private void setLeeftijd(final KampInfoRange leeftijd) {
        this.leeftijd = leeftijd;
    }

    private void setLeeftijdMarge(final KampInfoRange leeftijdMarge) {
        this.leeftijdMarge = leeftijdMarge;
    }

    private void setAantalDeelnemers(final KampInfoRange aantalDeelnemers) {
        this.aantalDeelnemers = aantalDeelnemers;
    }

    private void setMaximumAantalUitEenGroep(final int maximumAantalUitEenGroep) {
        this.maximumAantalUitEenGroep = maximumAantalUitEenGroep;
    }

    private void setAantalSubgroepen(final KampInfoRange aantalSubgroepen) {
        this.aantalSubgroepen = aantalSubgroepen;
    }

    private void setAantalDeelnemersInSubgroep(final KampInfoRange aantalDeelnemersInSubgroep) {
        this.aantalDeelnemersInSubgroep = aantalDeelnemersInSubgroep;
    }

    private void setDeelbaarDoor(final int deelbaarDoor) {
        this.deelbaarDoor = deelbaarDoor;
    }

    private void setKosteloosAnnulerenTot(final KampInfoDatum kosteloosAnnulerenTot) {
        this.kosteloosAnnulerenTot = kosteloosAnnulerenTot;
    }

    private void setVolledigeKostenAnnulerenVanaf(final KampInfoDatum volledigeKostenAnnulerenVanaf) {
        this.volledigeKostenAnnulerenVanaf = volledigeKostenAnnulerenVanaf;
    }

    private void setProjectInningsdatum(final KampInfoDatum projectInningsdatum) {
        this.projectInningsdatum = projectInningsdatum;
    }

    private void setEvenementStart(final KampInfoDatum evenementStart) {
        this.evenementStart = evenementStart;
    }

    private void setEvenementStartTijd(final String evenementStartTijd) {
        this.evenementStartTijd = evenementStartTijd;
    }

    private void setEvenementEind(final KampInfoDatum evenementEind) {
        this.evenementEind = evenementEind;
    }

    private void setEvenementEindTijd(final String evenementEindTijd) {
        this.evenementEindTijd = evenementEindTijd;
    }

    private void setInschrijvingStart(final KampInfoDatum inschrijvingStart) {
        this.inschrijvingStart = inschrijvingStart;
    }

    private void setInschrijvingEind(final KampInfoDatum inschrijvingEind) {
        this.inschrijvingEind = inschrijvingEind;
    }

}
