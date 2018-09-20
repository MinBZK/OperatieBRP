/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.web.bijhouding.VerwerkingsResultaat;
import nl.bzk.brp.web.interceptor.ArchiveringBericht;
import org.apache.cxf.phase.PhaseInterceptorChain;


/**
 * Deze klasse stelt een antwoord bericht voor met alle elementen die terug gecommuniceerd worden via de webservice.
 * Het
 * bevat eventuele meldingen (fouten, waarschuwingen etc.) die zijn opgetreden tijdens de verwerking van het bericht.
 * @param <T> Het type resultaat wat in dit antwoord bericht gaat.
 */
public abstract class AbstractAntwoordBericht<T extends BerichtResultaat> {

    private static final int    TIMESTAMP_LENGTH = 16;
    private static final String TIJD_FORMAAT     = "%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL";

    private final BerichtStuurgegevens berichtStuurgegevens;
    private final VerwerkingsResultaat verwerkingsResultaat;
    private final List<Melding>        meldingen;
    private final String               hoogsteMeldingNiveau;
    private       Long                 tijdstipRegistratie;

    /** Standaard constructor die een lege lijst van meldingen initialiseert. */
    public AbstractAntwoordBericht() {
        this(null);
    }

    /**
     * Standaard constructor die de lijst van meldingen initialiseert naar de meldingen in de opgegeven lijst.
     *
     * @param berichtResultaat het resultaat van het bericht
     */
    public AbstractAntwoordBericht(final T berichtResultaat) {
        berichtStuurgegevens = new BerichtStuurgegevens("Ministerie BZK", "BRP", bepaalReferentieNr(),
            "nog niet geimplementeerd");
        verwerkingsResultaat = bepaalVerwerkingsResultaat(berichtResultaat);
        meldingen = bepaalMeldingen(berichtResultaat);
        hoogsteMeldingNiveau = bepaalHoogsteMeldingNiveau();
    }

    public BerichtStuurgegevens getBerichtStuurgegevens() {
        return berichtStuurgegevens;
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
     * Retourneert een (onaanpasbare) lijst van de opgetreden meldingen.
     *
     * @return een (onaanpasbare) lijst van de opgetreden meldingen.
     */
    public List<Melding> getMeldingen() {
        List<Melding> resultaat;
        if (meldingen == null) {
            resultaat = null;
        } else {
            resultaat = Collections.unmodifiableList(meldingen);
        }
        return resultaat;
    }

    /**
     * Retourneert het hoogste niveau van de opgetreden meldingen.
     *
     * @return het hoogste niveau van de opgetreden meldingen.
     */
    public String getHoogsteMeldingNiveau() {
        return hoogsteMeldingNiveau;
    }

    /**
     * Retourneert het tijdstip van registratie waarop de bijhouding is geregistreerd.
     *
     * @return het tijdstip van registratie waarop de bijhouding is geregistreerd.
     */
    public Long getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * Zet de berichtMeldingen op null als er geen meldingen zijn en anders maak een nieuwe lijst van meldingen.
     *
     * @param berichtResultaat het resultaat van een bericht
     * @return lijst van meldingen
     */
    private List<Melding> bepaalMeldingen(final BerichtResultaat berichtResultaat) {
        List<Melding> berichtMeldingen;

        if (berichtResultaat == null || berichtResultaat.getMeldingen() == null
            || berichtResultaat.getMeldingen().isEmpty())
        {
            berichtMeldingen = null;
        } else {
            berichtMeldingen = new ArrayList<Melding>(berichtResultaat.getMeldingen());
        }

        return berichtMeldingen;
    }

    /**
     * Bepaalt het hoogste melding niveau (op basis van alles meldingen) en retourneert de code van het bijbehorende
     * melding niveau.
     *
     * @return de code van de hoogste melding soort.
     */
    private String bepaalHoogsteMeldingNiveau() {
        String hoogste;
        if (meldingen == null || meldingen.isEmpty()) {
            hoogste = null;
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
     * Haal de unieke id op dat correspondeert met de inkomende bericht.
     *
     * @return refentieId
     */
    private String bepaalReferentieNr() {
        // haal de (unieke) id dat toevallig correspondeert met de inkomend bericht id.
        Long berichtInId = null;
        String referentieNr = "OnbekendeID";
        try {
            berichtInId =
                (Long) PhaseInterceptorChain.getCurrentMessage().getExchange()
                                            .get(ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID);
        } catch (NullPointerException e) {
            berichtInId = null;
            // in geval dat PhaseInterceptorChain.getCurrentMessage().getExchange() niet bestaat
        }
        if (berichtInId != null) {
            referentieNr = berichtInId.toString();
        }

        return referentieNr;
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
        if (berichtResultaat == null) {
            resultaat = null;
        } else if (berichtResultaat.getResultaatCode() == ResultaatCode.GOED) {
            resultaat = VerwerkingsResultaat.GOED;
        } else {
            resultaat = VerwerkingsResultaat.FOUT;
        }
        return resultaat;
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
     * Zet het tijdstip registratie als long value op basis van opgegeven tijdstip. De representatie als getal bevat
     * het jaartal, maand, dag, uur, minuten, secondes en honderdste van secondes (conform StUF standaard).
     */
    protected void setTijdstipRegistratie() {
        String tijdstip = String.format(TIJD_FORMAAT, Calendar.getInstance());
        tijdstipRegistratie = Long.parseLong(tijdstip.substring(0, TIMESTAMP_LENGTH));
    }
}
