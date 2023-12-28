package nl.scouting.hit.kampinfo.export;

/**
 * Een exportregel uit KampInfoHelper met informatie voor een ScoutsOnline inschrijfformulier voor KIND is lid.
 */
public class KampInfoKindFormulierExportRegel extends KampInfoFormulierExportRegel {

    public KampInfoKindFormulierExportRegel(final KampInfoFormulierExportRegel regel) {
        copy(regel);
    }

    @Override
    public final String getBasisformulierNaam() {
        return String.format("HIT %d Basisformulier Ouder-Kind (niet wijzigen)", jaar);
    }

    @Override
    public String getKoppelgroepjeNaam() {
        return super.getKoppelgroepjeNaam() + " (2)";
    }

}
