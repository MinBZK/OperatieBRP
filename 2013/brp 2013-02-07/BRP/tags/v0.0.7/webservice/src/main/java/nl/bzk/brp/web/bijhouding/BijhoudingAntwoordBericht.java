/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.web.AbstractAntwoordBericht;

/**
 * Deze klasse stelt een antwoord bericht voor in het kader van bijhoudingen. Het antwoord bericht bevat alle elementen
 * die terug gecommuniceerd worden via de webservice. Zo bevat het eventuele meldingen (fouten, waarschuwingen etc.)
 * die zijn opgetreden tijdens de verwerking van het bericht, maar ook een lijst van personen die tijdens de bijhouding
 * zijn aangepast/geraakt.
 */
public class BijhoudingAntwoordBericht extends AbstractAntwoordBericht {

    private final List<Persoon>        bijgehoudenPersonen;
    private final Date                 tijdstipRegistratie;
    private final VerwerkingsResultaat verwerkingsResultaat;
    private final String               hoogsteMeldingNiveau;

    /**
     * Standaard constructor die op basis van het opgegeven berichtresultaat uit de business laag de juiste waardes
     * bepaalt/zet in dit antwoord bericht.
     *
     * @param berichtResultaat het bericht resultaat uit de business laag op basis waarvan het antwoord bericht wordt
     * gebouwd.
     */
    public BijhoudingAntwoordBericht(final BerichtResultaat berichtResultaat) {
        super(berichtResultaat.getMeldingen());
        bijgehoudenPersonen = null;
        tijdstipRegistratie = Calendar.getInstance().getTime();
        verwerkingsResultaat = bepaalVerwerkingsResultaat(berichtResultaat);
        hoogsteMeldingNiveau = bepaalHoogsteMeldingNiveau(getMeldingen());
    }

    /**
     * Bepaalt het uiteindelijke verwerkingsresultaat op basis van het bericht resultaat. Dit geeft dus aan of de
     * verwerking goed is gegaan of fout.
     *
     * @param berichtResultaat het bericht resultaat uit de business laag.
     * @return het verwerkingsresultaat van het bericht.
     */
    private VerwerkingsResultaat bepaalVerwerkingsResultaat(final BerichtResultaat berichtResultaat) {
        VerwerkingsResultaat resultaat;
        if (berichtResultaat.getResultaatCode() == ResultaatCode.GOED) {
            resultaat = VerwerkingsResultaat.GOED;
        } else {
            resultaat = VerwerkingsResultaat.FOUT;
        }
        return resultaat;
    }

    /**
     * Bepaalt het hoogste melding niveau (op basis van alles meldingen) en retourneert de code van het bijbehorende
     * melding niveau.
     *
     * @param meldingen alle opgetreden meldingen.
     * @return de code van de hoogste melding soort.
     */
    private String bepaalHoogsteMeldingNiveau(final List<Melding> meldingen) {
        String hoogste;
        if (meldingen == null || meldingen.isEmpty()) {
            hoogste = "G";
        } else {
            int hoogsteOrdinal = -1;
            for (Melding melding : meldingen) {
                hoogsteOrdinal = Math.max(hoogsteOrdinal, melding.getSoort().ordinal());
            }
            hoogste = vertaalSoortMeldingNaarNiveau(SoortMelding.values()[hoogsteOrdinal]);
        }
        return hoogste;
    }

    /**
     * Vertaalt de {@link SoortMelding} naar de voor het antwoord bericht benodigde code.
     *
     * @param soortMelding de soort van de melding.
     * @return de code behorende bij de opgegeven soort melding.
     */
    private String vertaalSoortMeldingNaarNiveau(final SoortMelding soortMelding) {
        String resultaat;
        switch (soortMelding) {
            case FOUT_ONOVERRULEBAAR:
                resultaat = "F";
                break;
            case FOUT_OVERRULEBAAR:
                resultaat = "O";
                break;
            case WAARSCHUWING:
                resultaat = "W";
                break;
            case INFO:
                resultaat = "I";
                break;
            default:
                throw new IllegalArgumentException("Onbekende meldingsoort gedetecteerd: " + soortMelding);
        }
        return resultaat;
    }

    /**
     * Retourneert een (onaanpasbare) lijst van de bijgehouden/aangepast personen.
     *
     * @return een (onaanpasbare) lijst van de bijgehouden/aangepast personen.
     */
    public List<Persoon> getBijgehoudenPersonen() {
        List<Persoon> resultaat;
        if (bijgehoudenPersonen == null) {
            resultaat = null;
        } else {
            resultaat = Collections.unmodifiableList(bijgehoudenPersonen);
        }
        return resultaat;
    }

    /**
     * Retourneert het tijdstip van registratie waarop de bijhouding is geregistreerd.
     *
     * @return het tijdstip van registratie waarop de bijhouding is geregistreerd.
     */
    public Date getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * Retourneert het verwerkingsresultaat van de bijhouding, dus of de bijhouding geslaagd is of niet.
     *
     * @return het verwerkingsresultaat van de bijhouding, dus of de bijhouding geslaagd is of niet.
     */
    public VerwerkingsResultaat getVerwerkingsResultaat() {
        return verwerkingsResultaat;
    }

    /**
     * Retourneert het hoogste niveau van de opgetreden meldingen.
     *
     * @return het hoogste niveau van de opgetreden meldingen.
     */
    public String getHoogsteMeldingNiveau() {
        return hoogsteMeldingNiveau;
    }

}
