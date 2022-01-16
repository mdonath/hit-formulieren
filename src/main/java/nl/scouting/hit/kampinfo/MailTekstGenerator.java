package nl.scouting.hit.kampinfo;

public final class MailTekstGenerator {

    public static String bevestigingAnnuleringVanDeelnemerAanOrganisatie(final Integer hitJaar) {
        return "Beste %per_fullname%\n" +
                "\nDeelnemer %per_fullname% heeft de inschrijving voor %evt_nm% (%frm_nm%) geannuleerd met als reden '%cancel_reason%'. Er kan nu weer opnieuw ingeschreven worden voor een andere activiteit als de inschrijfperiode nog niet is verlopen. Het per iDEAL betaalde bedrag zal binnen 14 dagen teruggestort worden op de gebruikte betaalrekening als aan onderstaande voorwaarden is voldaan:\n\n" +
                "- Als er binnen 10 dagen na de inschrijving is geannuleerd is de annulering kosteloos. Je krijgt dan het volledige bedrag terug.\n" +
                "- Bij een annulering na 10 dagen zal er een annuleringsbedrag van 10 euro worden verrekend.\n" +
                "- Na de sluiting van de inschrijvingsperiode (%frm_book_till_dt%) krijg je geen geld meer terug. Het is dan wel mogelijk om een andere scout in jouw plaats aan de HIT te laten deelnemen zodat je niet de deelnemersbijdrage kwijt bent. Neem hiervoor contact op met de Helpdesk van de HIT. (helpdesk@hit.scouting.nl)\n" +
                "\n" +
                "Wat de reden van annulering ook is, van deze voorwaarden kan helaas niet worden afgeweken. De deelnemersvoorwaarden, waar je bij het inschrijven mee akkoord bent gegaan, vind je op de website van de HIT: https://hit.scouting.nl/startpagina/deelnemersvoorwaarden-hit-" + hitJaar + "\n" +
                "\n" +
                "De HIT organisatie\n" +
                "Scouting Nederland"
                ;
    }

    public static String statusWijzigingNaarKosteloosGeannuleerdHIT2020(final Integer hitJaar) {
        return "Beste %per_fullname%,\n\n" +
                "Zoals je inmiddels al wel gehoord zult hebben is HIT 2020 helaas geannuleerd vanwege het Coronavirus (COVID-19). " +
                "We hebben jouw inschrijving voor %evt_nm% (%frm_nm%) nu kosteloos geannuleerd. " +
                "Het deelnemersbedrag zal uiterlijk 1 mei op de rekening waarmee je je HIT-inschrijving hebt betaald worden teruggestort.\n\n" +
                "We hopen dat je 65 jaar HIT volgend jaar opnieuw met ons meeviert! " +
                "Daarom gaan we ervoor zorgen dat je je volgend jaar opnieuw kunt inschrijven op de HIT die je dit jaar had gekozen. " +
                "Ook de leeftijdsgrenzen passen we daar, indien nodig, op aan. Hoe dat precies in z’n werk gaat, hoor je begin 2021.\n\n" +
                "Heb je vragen? Dan kun je die altijd stellen aan de helpdesk via helpdesk@hit.scouting.nl.\n\n" +
                "Tot in 2021!\n\n" +
                "Met vriendelijke groet,\n\n" +
                "Het HIT organisatieteam\n"
                ;
    }

    public static String bevestigingInschrijvingAanDeelnemer(final String datumDeelnemersInformatie) {
        return "Betreft: Bevestiging inschrijving %evt_nm%\n" +
                "(bewaar dit mailtje goed)\n\n" +
                "Beste %per_fullname%,\n\n" +
                "Wat leuk! Je hebt je opgegeven voor een spectaculair weekend tijdens de %evt_nm% voor het onderdeel %frm_nm%. Deze activiteit start op %frm_from_dt% om %frm_from_time% uur en duurt tot %frm_till_dt% tot %frm_till_time% uur.\n\n" +
                "De deelnamekosten voor deze activiteit zijn %frm_price% euro. Als je deze via iDEAL voldaan hebt, is je inschrijving compleet. Als er iets mis ging met de betaling of je bent door iemand anders, zoals je teamleider, ingeschreven is jouw betaling nog niet compleet. Je ontvangt dan een aparte mail met een betalingsverzoek en een iDEAL-link om je betaling te voldoen. Pas dan ben je officieel ingeschreven.\n\n" +
                "Na je inschrijving heb je 10 dagen bedenktijd. Binnen deze tijd kun je kosteloos annuleren. Je krijgt dan het deelnamegeld helemaal teruggestort. Dit is ook de termijn waarbinnen jouw groepje compleet moet zijn. Na 10 dagen kun je nog wel annuleren maar krijg je het deelnamegeld terug verminderd met € 10 administratiekosten. Na de sluiting van de inschrijving kun je niet meer annuleren en krijg je geen geld meer terug.\n\n" +
                "Annuleren kan alleen door in te loggen op sol.scouting.nl en in het ‘Mijn Scouting’-menu bij ‘Mijn inschrijvingen’ naar de HIT-inschrijving te gaan. Daar kun je in het tabblad ‘deelnamestatus’ jouw inschrijving annuleren. Vul altijd een reden in. Is er sprake van bijzondere omstandigheden waardoor je moet annuleren, neem dan altijd contact op met de HIT-Helpdesk. Na de sluiting vaan de inschrijving is het niet meer mogelijk om te annuleren! Wel is het mogelijk om een andere deelnemer voor jou in de plaats te laten deelnemen.\n\n" +
                "We willen je er ook op attenderen dat het soms voorkomt dat een HIT-onderdeel niet doorgaat. We geven dit dan natuurlijk zo snel mogelijk aan je door. Je krijgt dan een aantal alternatieve activiteiten aangeboden of je kunt kosteloos annuleren.\n\n" +
                "Via Scouts Online (https://sol.scouting.nl) kun je de inschrijving van jouw groepje bekijken. Klik na het inloggen op ‘Mijn Scouting’ – ‘Mijn inschrijvingen’ en kies daar de inschrijving van de HIT. Onder ‘subgroep(en)’ zie je wie zich al voor jouw groepje heeft aangemeld. Dit aanmelden moet binnen 10 dagen. Dus schud je groepje wakker en zorg dat ze zich op tijd inschrijven! \n\n" +
                "Zijn er problemen met inschrijven of heb je een andere vraag? Kijk dan op http://hit.scouting.nl bij het onderdeel ‘Inschrijven’ of mail de helpdesk via helpdesk@hit.scouting.nl. \n" +
                "Rond " + datumDeelnemersInformatie + " zal de deelnemersinformatie van %frm_nm% beschikbaar zijn op de HIT-website. Daarin staat o.a. waar je moet zijn en wat je mee moet nemen. We sturen je een mail als de informatie van jouw HIT onderdeel beschikbaar is.\n\n" +
                "Tot ziens op de HIT.\n\n" +
                "De HIT organisatie\n" +
                "Scouting Nederland\n\n" +
                "PS: Je lidnummer is: %per_id%\n" +
                "En je inschrijfnummer is: %prt_id%";
    }

    public static String bevestigingInschrijvingAanOuders() {
        return "Beste ouder/verzorger van %per_fullname%,\n\n" +
                "%per_fullname% heeft zich opgegeven als deelnemer voor %frm_nm% tijdens de %evt_nm%.\n" +
                "Deze mail dient uitsluitend om u te informeren over deze inschrijving. Alle verdere correspondentie zal via het in Scouts Online geregistreerde e-mailadres %per_email% van %per_fullname% verlopen.\n" +
                "Deze activiteit start op %frm_from_dt% om %frm_from_time% uur en duurt tot %frm_till_dt% tot %frm_till_time% uur.\n" +
                "De deelnamekosten voor deze activiteit zijn %frm_price% euro.\n\n" +
                "De deelnemer heeft na inschrijving 10 dagen bedenktijd en kan binnen deze 10 dagen kosteloos annuleren. Binnen deze tijd kun je kosteloos annuleren. Je krijgt dan het deelnamegeld helemaal teruggestort. Dit is ook de termijn waarbinnen het groepje compleet moet zijn. Na 10 dagen kan er nog wel geannuleerd worden maar krijg de deelnemer het deelnamegeld terug verminderd met € 10 administratiekosten. Na de sluiting van de inschrijving kan er niet meer geannuleerd worden en krijgt de deelnemer geen geld meer terug.\n\n" +
                "Zijn er problemen met inschrijven of heeft u een andere vraag? Kijk dan op http://hit.scouting.nl bij het onderdeel ’inschrijven’.\n\n" +
                "Met vriendelijke groet,\n\n" +
                "De HIT-organisatie\n" +
                "Scouting Nederland";
    }

    public static String statuswijzigingNaarKosteloosGeannuleerd() {
        return "Beste %per_fullname%,\n\n" +
                "Je inschrijving voor het evenement %evt_nm% (%frm_nm%) is kosteloos geannuleerd.\n" +
                "Je bent niet meer ingeschreven voor dit evenement.\n\n" +
                "De HIT organisatie";
    }

}
