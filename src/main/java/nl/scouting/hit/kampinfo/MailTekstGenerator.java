package nl.scouting.hit.kampinfo;

public final class MailTekstGenerator {

    private MailTekstGenerator() {
        // Private Utility Constructor
    }

    public static String annuleringsmailVanDeelnemerAanOrganisatie(final Integer hitJaar) {
        return """
                Beste %per_fullname%

                Deelnemer %per_fullname% heeft de inschrijving voor %evt_nm% (%frm_nm%) geannuleerd met als reden '%cancel_reason%'. Er kan nu weer opnieuw ingeschreven worden voor een andere activiteit als de inschrijfperiode nog niet is verlopen. Het per iDEAL betaalde bedrag zal binnen 14 dagen teruggestort worden op de gebruikte betaalrekening als aan onderstaande voorwaarden is voldaan:

                - Als er binnen 10 dagen na de inschrijving is geannuleerd is de annulering kosteloos. Je krijgt dan het volledige bedrag terug.
                - Bij een annulering na 10 dagen zal er een annuleringsbedrag van 10 euro worden verrekend.
                - Na de sluiting van de inschrijvingsperiode (%frm_book_till_dt%) krijg je geen geld meer terug. Het is dan wel mogelijk om een andere scout in jouw plaats aan de HIT te laten deelnemen zodat je niet de deelnemersbijdrage kwijt bent. Neem hiervoor contact op met de Helpdesk van de HIT. (helpdesk@hit.scouting.nl)

                Wat de reden van annulering ook is, van deze voorwaarden kan helaas niet worden afgeweken. De deelnemersvoorwaarden, waar je bij het inschrijven mee akkoord bent gegaan, vind je op de website van de HIT: https://hit.scouting.nl/startpagina/deelnemersvoorwaarden

                De HIT organisatie
                Scouting Nederland
                """;
    }

    public static String bevestigingVanInschrijvingAanDeelnemer(final String datumDeelnemersInformatie) {
        final String datum = " " + datumDeelnemersInformatie + " ";
        return """
                Betreft: Bevestiging inschrijving %evt_nm%
                                
                (bewaar dit mailtje goed)
                                
                Beste %per_fullname%,
                                
                Goed nieuws! Je bent ingeschreven voor een super tof weekend tijdens de %evt_nm% voor het onderdeel %frm_nm%. Deze activiteit start op %frm_from_dt% om %frm_from_time% uur en duurt tot %frm_till_dt% tot %frm_till_time% uur.
                                
                De deelnamekosten voor deze activiteit zijn %frm_price% euro. Je ontvangt nog een aparte mail met een betalingsverzoek en een iDEAL-link om je betaling te voldoen. Pas als je deze via iDEAL voldaan hebt, is je inschrijving compleet.
                                
                Na je inschrijving heb je 10 dagen bedenktijd. Binnen deze tijd kun je kosteloos annuleren. Je krijgt dan het deelnamegeld helemaal teruggestort. Dit is ook de termijn waarbinnen jouw groepje compleet moet zijn. Na 10 dagen kun je nog wel annuleren maar krijg je het deelnamegeld terug verminderd met € 10 administratiekosten. Na de sluiting van de inschrijving kun je niet meer annuleren en krijg je geen geld meer terug.
                                
                Annuleren kan alleen door in te loggen op sol.scouting.nl en in het ‘Mijn Scouting’-menu bij ‘Mijn inschrijvingen’ naar de HIT-inschrijving te gaan. Daar kun je in het tabblad ‘deelnamestatus’ jouw inschrijving annuleren. Vul altijd een reden in. Is er sprake van bijzondere omstandigheden waardoor je moet annuleren, neem dan altijd contact op met de HIT-Helpdesk. Na de sluiting vaan de inschrijving is het niet meer mogelijk om te annuleren! Wel is het mogelijk om een andere deelnemer voor jou in de plaats te laten deelnemen.
                                
                We willen je er ook op attenderen dat het soms voorkomt dat een HIT-onderdeel niet doorgaat. We geven dit dan natuurlijk zo snel mogelijk aan je door. Je krijgt dan een aantal alternatieve activiteiten aangeboden of je kunt kosteloos annuleren.
                                
                Via Scouts Online (https://sol.scouting.nl) kun je de inschrijving van jouw groepje bekijken. Klik na het inloggen op ‘Mijn Scouting’ – ‘Mijn inschrijvingen’ en kies daar de inschrijving van de HIT. Onder ‘subgroep(en)’ zie je wie zich al voor jouw groepje heeft aangemeld. Dit aanmelden moet binnen 10 dagen. Dus schud je groepje wakker en zorg dat ze zich op tijd inschrijven! 
                                
                Zijn er problemen met inschrijven of heb je een andere vraag? Kijk dan op http://hit.scouting.nl bij het onderdeel ‘Inschrijven’ of mail de helpdesk via helpdesk@hit.scouting.nl. 
                                
                Rond"""
                + datum +
                """
                zal de deelnemersinformatie van %frm_nm% beschikbaar zijn op de HIT-website. Daarin staat o.a. waar je moet zijn en wat je mee moet nemen. We sturen je een mail als de informatie van jouw HIT onderdeel beschikbaar is.
                                
                Tot snel bij de %evt_nm%!
                                
                De HIT organisatie
                Scouting Nederland
                                
                PS: Je lidnummer is: %per_id%
                En je inschrijfnummer is: %prt_id%
                """;
    }

    public static String bevestigingVanInschrijvingAanOudersVerzorgers() {
        return """
                Beste ouder/verzorger van %per_fullname%,

                %per_fullname% heeft zich opgegeven als deelnemer voor %frm_nm% tijdens de %evt_nm%.
                Deze mail dient uitsluitend om u te informeren over deze inschrijving. Alle verdere correspondentie zal via het in Scouts Online geregistreerde e-mailadres %per_email% van %per_fullname% verlopen.
                Deze activiteit start op %frm_from_dt% om %frm_from_time% uur en duurt tot %frm_till_dt% tot %frm_till_time% uur.
                De deelnamekosten voor deze activiteit zijn %frm_price% euro.

                De deelnemer heeft na inschrijving 10 dagen bedenktijd en kan binnen deze 10 dagen kosteloos annuleren. Binnen deze tijd kun je kosteloos annuleren. Je krijgt dan het deelnamegeld helemaal teruggestort. Dit is ook de termijn waarbinnen het groepje compleet moet zijn. Na 10 dagen kan er nog wel geannuleerd worden maar krijg de deelnemer het deelnamegeld terug verminderd met € 10 administratiekosten. Na de sluiting van de inschrijving kan er niet meer geannuleerd worden en krijgt de deelnemer geen geld meer terug.

                Zijn er problemen met inschrijven of heeft u een andere vraag? Kijk dan op http://hit.scouting.nl bij het onderdeel ’inschrijven’.

                Met vriendelijke groet,

                De HIT-organisatie
                Scouting Nederland
                """;
    }

    public static String bevestigingVanWachtlijstAanDeelnemer(final Integer hitJaar) {
        return """
                Betreft: Bevestiging voorlopige inschrijving %evt_nm%
                (bewaar dit mailtje goed)
                
                Beste %per_fullname%
                                
                Je hebt je net aangemeld voor een spectaculair weekend tijdens de %evt_nm% voor het onderdeel %frm_nm%.
                
                Alle deelnemers worden gedurende een inschrijfweek standaard op een wachtlijst geplaatst waarna eventueel een loting zal plaatsvinden als er meer deelnemers dan beschikbare plekken zijn.
                Uiterlijk een week na de sluiting van een inschrijfweek ontvang je daarover via e-mail bericht. Kijk voor meer informatie over de inschrijfprocedure op de website van de HIT: https://hit.scouting.nl.

                LET OP: Als je hebt aangegeven dat je met een subgroepje komt, zorg er dan voor dat iedereen van je subgroepje deze week ingeschreven is, anders doet je groepje niet mee met de loting!

                De laatste informatie over jouw inschrijving kun je zo vinden
                - log in op https://sol.scouting.nl
                - ga vervolgens naar menu 'mijn scouting'.
                - onder kolom 'Spel' vind je vervolgens 'Mijn inschrijvingen'
                Je krijgt dan alle inschrijvingen te zien.
                                
                Of gebruik de volgende link:
                https://sol.scouting.nl/as/participant/%prt_id%/registrationform
                                
                De HIT organisatie
                Scouting Nederland

                PS: Je lidnummer is: %per_id%
                En je inschrijfnummer is: %prt_id%
                """;
    }

    public static String mailBijStatuswijzigingNaarKosteloosGeannuleerd() {
        return """
                Beste %per_fullname%,

                Je inschrijving voor het evenement %evt_nm% (%frm_nm%) is kosteloos geannuleerd.
                Je bent niet meer ingeschreven voor dit evenement.

                De HIT organisatie
                """;
    }

    public static String hit2020MailBijStatuswijzigingNaarKosteloosGeannuleerd(final Integer hitJaar) {
        return """
                Beste %per_fullname%,

                Zoals je inmiddels al wel gehoord zult hebben is HIT 2020 helaas geannuleerd vanwege het Coronavirus (COVID-19). We hebben jouw inschrijving voor %evt_nm% (%frm_nm%) nu kosteloos geannuleerd. Het deelnemersbedrag zal uiterlijk 1 mei op de rekening waarmee je je HIT-inschrijving hebt betaald worden teruggestort.

                We hopen dat je 65 jaar HIT volgend jaar opnieuw met ons meeviert! Daarom gaan we ervoor zorgen dat je je volgend jaar opnieuw kunt inschrijven op de HIT die je dit jaar had gekozen. Ook de leeftijdsgrenzen passen we daar, indien nodig, op aan. Hoe dat precies in z’n werk gaat, hoor je begin 2021.

                Heb je vragen? Dan kun je die altijd stellen aan de helpdesk via helpdesk@hit.scouting.nl.

                Tot in 2021!

                Met vriendelijke groet,

                Het HIT organisatieteam
                """;
    }

}
