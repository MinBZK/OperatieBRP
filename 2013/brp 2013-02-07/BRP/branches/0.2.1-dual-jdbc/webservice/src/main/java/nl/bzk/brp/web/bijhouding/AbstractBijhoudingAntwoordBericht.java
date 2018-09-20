/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verwerkingsresultaat;
import nl.bzk.brp.web.AbstractAntwoordBericht;


/**
 * Deze klasse stelt een antwoord bericht voor in het kader van bijhoudingen. Het antwoord bericht bevat alle elementen
 * die terug gecommuniceerd worden via de webservice. Zo bevat het eventuele meldingen (fouten, waarschuwingen etc.)
 * die zijn opgetreden tijdens de verwerking van het bericht, maar ook een lijst van personen die tijdens de bijhouding
 * zijn aangepast/geraakt.
 */
public abstract class AbstractBijhoudingAntwoordBericht extends AbstractAntwoordBericht {

    private static final int    TIMESTAMP_LENGTH = 16;
    private static final String TIJD_FORMAAT     = "%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL";

    private List<Persoon>  bijgehoudenPersonen;
    private Long           tijdstipRegistratie;

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

    public void setBijgehoudenPersonen(final List<Persoon> bijgehoudenPersonen) {
        this.bijgehoudenPersonen = bijgehoudenPersonen;
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
     * Test of de lijst van bijgehoudenPersonen getooned dient te worden. Dit is een method voor Jibx om
     * de lijst op te maken ja of nee. (Bij fout verwerking de lijst dus niet tonen).
     * @return true als de lijst terugggeven moet worden
     */
    public boolean tonenBijgehoudenPersonen() {
        return getBerichtResultaatGroep().getVerwerkingsresultaat() == Verwerkingsresultaat.GOED;
    }
}
