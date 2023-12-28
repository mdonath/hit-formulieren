package nl.scouting.hit.kampinfo.export;

/**
 * Een exportregel uit KampInfoHelper met informatie voor een ScoutsOnline inschrijfformulier voor OUDER is lid.
 */
public class KampInfoOuderFormulierExportRegel extends KampInfoFormulierExportRegel {

    public KampInfoOuderFormulierExportRegel(final KampInfoFormulierExportRegel regel) {
        copy(regel);
    }

    @Override
    protected boolean isOuderFormulier() {
        return true;
    }

    @Override
    public final String getBasisformulierNaam() {
        return String.format("HIT %d Basisformulier Ouder-Kind (optie OUDER) (niet wijzigen)", jaar);
    }

    @Override
    public String getKoppelgroepjeNaam() {
        return super.getKoppelgroepjeNaam() + " (2)";
    }

}
