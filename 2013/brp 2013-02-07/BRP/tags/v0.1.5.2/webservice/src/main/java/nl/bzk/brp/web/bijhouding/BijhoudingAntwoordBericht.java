/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.business.dto.bijhouding.BijhoudingCode;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
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
public class BijhoudingAntwoordBericht extends AbstractAntwoordBericht<BijhoudingResultaat> {

    private static final int    TIMESTAMP_LENGTH = 16;
    private static final String TIJD_FORMAAT     = "%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL";

    private final List<Persoon>  bijgehoudenPersonen;
    private       Long           tijdstipRegistratie;
    private final BijhoudingCode bijhoudingCode;

    /**
     * Standaard constructor die op basis van het opgegeven berichtresultaat uit de business laag de juiste waardes
     * bepaalt/zet in dit antwoord bericht.
     *
     * @param berichtResultaat het bericht resultaat uit de business laag op basis waarvan het antwoord bericht wordt
     * gebouwd.
     */
    public BijhoudingAntwoordBericht(final BijhoudingResultaat berichtResultaat) {
        super(berichtResultaat);
        setTijdstipRegistratieMetDatum(berichtResultaat.getTijdstipRegistratie());
        bijhoudingCode = bepaalBijhoudingCode(berichtResultaat);
        bijgehoudenPersonen = berichtResultaat.getBijgehoudenPersonen();
    }

    /**
     * Bepaalt de bijhouding code op basis van het berichtresultaat. Hiervoor wordt gekeken naar de bijhoudingcode
     * in het berichtresultaat, alsmede naar de meldingen en of het een prevalidatie bericht is of niet.
     *
     * @param berichtResultaat het bericht resultaat waarvoor de bijhoudingcode moet worden bepaald.
     * @return de bijhouding code of <code>null</code> indien deze niet getoond dient te worden.
     */
    private BijhoudingCode bepaalBijhoudingCode(final BijhoudingResultaat berichtResultaat) {
        // ToDo: Onderstaande wijze van bepalen en wel of niet tonen vervangen door juiste code in business laag.
        BijhoudingCode resultaat;

        if (getMeldingen() != null && getBerichtStuurgegevens().isPrevalidatieBericht()) {
            int hoogsteOrdinal = -1;
            for (Melding melding : getMeldingen()) {
                hoogsteOrdinal = Math.max(hoogsteOrdinal, melding.getSoort().ordinal());
            }
            if (hoogsteOrdinal != -1 && SoortMelding.values()[hoogsteOrdinal] != SoortMelding.FOUT_ONOVERRULEBAAR) {
                resultaat = berichtResultaat.getBijhoudingCode();
            } else {
                resultaat = null;
            }
        } else if (getVerwerkingsResultaat().equals(VerwerkingsResultaat.GOED)) {
            resultaat = berichtResultaat.getBijhoudingCode();
        } else {
            resultaat = null;
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
        if (bijgehoudenPersonen == null || bijgehoudenPersonen.isEmpty()) {
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
    public Long getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * Zet het tijdstip van registratie waarop de bijhouding is registreerd.
     *
     * @param tijdstipRegistratie het tijdstip van registratie waarop de bijhouding is geregistreerd.
     */
    public void setTijdstipRegistratie(final Long tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }

    /**
     * Zet het tijdstip registratie als long value op basis van opgegeven tijdstip. De representatie als getal bevat
     * het jaartal, maand, dag, uur, minuten, secondes en honderdste van secondes (conform StUF standaard).
     * @param tijdstipRegistratieDatum de datum
     */
    private void setTijdstipRegistratieMetDatum(final Date tijdstipRegistratieDatum) {
        if (tijdstipRegistratieDatum == null) {
            tijdstipRegistratie = null;
        } else {
            String tijdstip = String.format(TIJD_FORMAAT, tijdstipRegistratieDatum);
            tijdstipRegistratie = Long.parseLong(tijdstip.substring(0, TIMESTAMP_LENGTH));
        }
    }

    /**
     * Retourneert de bijhouding code die aangeeft of een bericht direct is verwerkt of is uitgesteld.
     *
     * @return de bijhouding code die aangeeft of een bericht direct is verwerkt of is uitgesteld.
     */
    public BijhoudingCode getBijhoudingCode() {
        return bijhoudingCode;
    }

    /**
     * Test of de lijst van bijgehoudenPersonen getooned dient te worden. Dit is een method voor Jibx om
     * de lijst op te maken ja of nee. (Bij fout verwerking de lijst dus niet tonen).
     * @return true als de lijst terugggeven moet worden
     */
    public boolean tonenBijgehoudenPersonen() {
        return (getVerwerkingsResultaat() == VerwerkingsResultaat.GOED);

//        if (getVerwerkingsResultaat() != VerwerkingsResultaat.GOED) {
//            // als fout, toon ze niet, blijkbaar hebben we niet gecommit (of nooit zover gekomen).
//            return false;
//        } else {
//            // hier moeten we kijken of 'Goed' + 'Prevalidatie aan'. De vraag is of bij prevalidatie WEL tonen.
//            return true;
//        }
    }
}
